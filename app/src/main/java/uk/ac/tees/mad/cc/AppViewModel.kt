package uk.ac.tees.mad.cc

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.cc.data.CurrencyApiService
import uk.ac.tees.mad.cc.model.CurrencyResponse
import uk.ac.tees.mad.cc.model.Data
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val currencyApiService: CurrencyApiService,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val isSignedIn = mutableStateOf(false)

    val currencyRates = MutableStateFlow<CurrencyResponse?>(null)


    init {
        fetchLatestRates()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            isSignedIn.value = true
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
                    "uid" to userId
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


    fun fetchLatestRates() {
        Log.d("AppViewModel", "Fetching latest rates...")
        viewModelScope.launch {
            try {
                val response = currencyApiService.getLatestRates(
                    apiKey = "cur_live_5sd2N8xnqJyI8ozbrxWkHqrbzHeFvMlSEANNMEtM",
                    currencies = "USD,EUR,GBP,JPY,AUD,CAD,CHF,CNY,INR,BRL"
                )
                currencyRates.value = response
                Log.d("AppViewModel", "Response: $response")
            } catch (e: Exception) {
                Log.e("AppViewModel", "Error fetching latest rates", e)
            }
        }
    }
}