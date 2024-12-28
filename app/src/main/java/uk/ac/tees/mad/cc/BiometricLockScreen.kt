package uk.ac.tees.mad.cc

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity


class BiometricLockScreen(private val context : Context) {

    private val biometricsManager = BiometricManager.from(context)
    private lateinit var promptInformation : BiometricPrompt.PromptInfo
    private lateinit var biometricsPrompt : BiometricPrompt


    fun isBiometricAvailable() : BiometricAuthStatus {
        return when(biometricsManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)){
            BiometricManager.BIOMETRIC_SUCCESS -> BiometricAuthStatus.READY
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> BiometricAuthStatus.NOT_AVAILABLE
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> BiometricAuthStatus.TEMP_NOT_AVAILABLE
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> BiometricAuthStatus.AVAILABLE_BUT_NOT_ENROLLED
            else -> BiometricAuthStatus.NOT_AVAILABLE
        }
    }

    fun promptBiometricAuth(
        title : String,
        description : String,
        negativeButtonText : String,
        fragmentActivity: FragmentActivity,
        onSuccess : (result : BiometricPrompt.AuthenticationResult) -> Unit,
        onFailed : () -> Unit,
        onError : (errorCode: Int, errorString: String) -> Unit
    ){
        when(isBiometricAvailable()){
            BiometricAuthStatus.NOT_AVAILABLE -> {
                onError(BiometricAuthStatus.NOT_AVAILABLE.id, "Not Available")
                return
            }
            BiometricAuthStatus.TEMP_NOT_AVAILABLE -> {
                onError(BiometricAuthStatus.TEMP_NOT_AVAILABLE.id, "Not Available at this moment")
                return
            }
            BiometricAuthStatus.AVAILABLE_BUT_NOT_ENROLLED -> {
                onError(BiometricAuthStatus.AVAILABLE_BUT_NOT_ENROLLED.id, "Add fingerprint first")
                return
            }
            else -> Unit
        }
        biometricsPrompt = BiometricPrompt(
            fragmentActivity,
            object  : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onFailed()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess(result)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errorCode, errString.toString())
                }
            }
        )
        promptInformation = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(description)
            .setNegativeButtonText(negativeButtonText)
            .build()
        biometricsPrompt.authenticate(promptInformation)
    }
}


enum class BiometricAuthStatus(val id: Int) {
    READY(1),
    NOT_AVAILABLE(-1),
    TEMP_NOT_AVAILABLE(-2),
    AVAILABLE_BUT_NOT_ENROLLED(-3),

}