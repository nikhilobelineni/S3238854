package uk.ac.tees.mad.cc

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import uk.ac.tees.mad.cc.data.CurrencyApiService
import uk.ac.tees.mad.cc.data.CurrencyDao
import uk.ac.tees.mad.cc.data.CurrencyHistory
import uk.ac.tees.mad.cc.model.CurrencyResponse
import uk.ac.tees.mad.cc.model.Data
import uk.ac.tees.mad.cc.model.User
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val currencyApiService: CurrencyApiService,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val currencyDao: CurrencyDao,
    private val context: Context,
    private val storage : FirebaseStorage
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val isSignedIn = mutableStateOf(false)
    val currencyRates = MutableStateFlow<CurrencyResponse?>(null)
    val currencyHistory = MutableStateFlow<List<CurrencyHistory>>(emptyList())
    val userData = mutableStateOf<User>(User())
    private val sharedPref = context.getSharedPreferences("Currency_Conversion_app", Context.MODE_PRIVATE)
    private val _isDarkModeOn = MutableStateFlow(sharedPref.getBoolean("isDarkModeOn", false))
    val isDarkModeOn: StateFlow<Boolean> = _isDarkModeOn

    fun toggleTheme(isDarkMode: Boolean) {
        _isDarkModeOn.value = isDarkMode
        sharedPref.edit().putBoolean("isDarkModeOn", isDarkMode).apply()
    }

    init {
        fetchhLatestRates()
        getCurrencyHistory()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            isSignedIn.value = true
            fetchUserData()
        }
    }

    fun signUp(context: Context, name: String, email: String, password: String, number: String) {
        isLoading.value = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val userId = authResult.user!!.uid
                val userRef = firestore.collection("user").document(userId)
                val userMap = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "password" to password,
                    "number" to number,
                    "uid" to userId,
                    "profile" to ""
                )

                userRef.set(userMap)
                    .addOnSuccessListener {
                        isLoading.value = false
                        isSignedIn.value = true

                        fetchUserData()

                        Toast.makeText(context, "Sign up successful!", Toast.LENGTH_SHORT).show()

                        Log.d("SignUp", "User successfully signed up: $userId")
                    }
                    .addOnFailureListener { e ->
                        isLoading.value = false

                        Toast.makeText(context, "Failed to save user data.", Toast.LENGTH_SHORT).show()

                        Log.e("SignUp", "Error saving user data", e)
                    }
            }
            .addOnFailureListener { e ->
                isLoading.value = false

                Toast.makeText(context, "Sign up failed: ${e.message}", Toast.LENGTH_SHORT).show()

                Log.e("SignUp", "Sign up failed", e)
            }
    }


    fun logIn(context: Context, email: String, password: String) {
        isLoading.value = true

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                isLoading.value = false
                isSignedIn.value = true

                fetchUserData()

                Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()

                Log.d("Login", "User successfully logged in: ${authResult.user!!.uid}")
            }
            .addOnFailureListener { e ->
                isLoading.value = false

                Toast.makeText(context, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()

                Log.e("Login", "Login failed", e)
            }
    }

    fun fetchUserData(){
        firestore.collection("user").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            val tempData = it.toObject(User::class.java)
            userData.value = tempData!!
            Log.d("fetchUserData", "User data fetched: $tempData")
        }.addOnFailureListener {
            Log.e("fetchUserData", "Error fetching user data", it)
        }
    }

    fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String): Double? {
        val rates = currencyRates.value?.data ?: return null
        val fromValue = rates.getCurrencyValue(fromCurrency)
        val toValue = rates.getCurrencyValue(toCurrency)
        return if (fromValue != null && toValue != null) {
            (amount / fromValue) * toValue
        } else {
            null
        }
    }

    private fun Data.getCurrencyValue(currencyCode: String): Double? {
        return when (currencyCode) {
            "AUD" -> AUD.value
            "BRL" -> BRL.value
            "CAD" -> CAD.value
            "CHF" -> CHF.value
            "CNY" -> CNY.value
            "EUR" -> EUR.value
            "GBP" -> GBP.value
            "INR" -> INR.value
            "JPY" -> JPY.value
            "USD" -> USD.value.toDouble()
            else -> null
        }
    }


    fun fetchhLatestRates() {
        Log.d("AppViewModel", "Fetching latest rates...")
        viewModelScope.launch {
            try {
                val response = currencyApiService.getLatestRates(
                    apiKey = "cur_live_avfsgcK9Axh8wyiJfV8yTwTrcRJtcXWzxNtuV4xL",
                    currencies = "USD,EUR,GBP,JPY,AUD,CAD,CHF,CNY,INR,BRL"
                )
                currencyRates.value = response
                Log.d("AppViewModel", "Response: $response")
            } catch (e: Exception) {
                Log.e("AppViewModel", "Error fetching latest rates", e)
            }
        }
    }

    fun addCurrencyHistory(fromCurrency: String, toCurrency: String, amount: Double, result: Double, date: String) {
        viewModelScope.launch {
            try {
                currencyDao.insertHistory(
                    CurrencyHistory(
                        fromCurrency = fromCurrency,
                        toCurrency = toCurrency,
                        amount = amount,
                        result = result,
                        date = date
                    )
                )
                getCurrencyHistory()
            } catch (e: Exception) {
                Log.e("AddCurrencyHistory", "Error inserting currency history", e)
            }
        }
    }

    fun getCurrencyHistory() {
        viewModelScope.launch {
            currencyHistory.value = currencyDao.getAllHistory()
            Log.d("AppViewModel", "Currency history: ${currencyHistory.value}")
        }
    }

    fun deleteCurrencyHistory() {
        viewModelScope.launch {
            currencyDao.deleteHistory()
            getCurrencyHistory()
        }
    }

    fun uploadProfile(context: Context, imageUri: Uri?) {
        if (imageUri != null) {
            isLoading.value = true
            val profileImageRef = storage.reference.child("profile_images/${auth.currentUser!!.uid}")

            profileImageRef.putFile(imageUri)
                .addOnSuccessListener { uploadTask ->
                    uploadTask.storage.downloadUrl.addOnSuccessListener { downloadUrl ->
                        firestore.collection("user").document(auth.currentUser!!.uid).update("profile", downloadUrl.toString())
                            .addOnSuccessListener {
                                isLoading.value = false
                                Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                isLoading.value = false
                                Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                                Log.e("uploadProfile", "Error updating Firestore with profile URL", e)
                            }
                    }.addOnFailureListener { e ->
                        isLoading.value = false
                        Toast.makeText(context, "Failed to get download URL", Toast.LENGTH_SHORT).show()
                        Log.e("uploadProfile", "Error getting download URL", e)
                    }
                }
                .addOnFailureListener { e ->
                    isLoading.value = false
                    Toast.makeText(context, "Failed to upload profile image", Toast.LENGTH_SHORT).show()
                    Log.e("uploadProfile", "Error uploading profile image", e)
                }
        }
    }

    fun updateUserData(context: Context,name: String, number : String){
        isLoading.value = true
        firestore.collection("user").document(auth.currentUser!!.uid).update("name",name,"number",number).addOnSuccessListener {
            isLoading.value = false
            Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
            fetchUserData()
        }.addOnFailureListener {
            isLoading.value = false
            Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
            Log.e("updateUserData", "Error updating Firestore with profile URL", it)
        }
    }

    fun signOut() {
        auth.signOut()
        isSignedIn.value = false
    }

    fun deleteHistory(item: CurrencyHistory) {
        viewModelScope.launch {
            currencyDao.delete(item)
            getCurrencyHistory()
        }
    }
}