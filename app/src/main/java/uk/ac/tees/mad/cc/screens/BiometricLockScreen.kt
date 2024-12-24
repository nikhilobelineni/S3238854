package uk.ac.tees.mad.cc.screens

import androidx.compose.runtime.Composable

@Composable
fun BiometricLockScreen(onAuthenticationSuccess: () -> Unit, onAuthenticationError: () -> Unit) {
    val context = LocalContext.current
    val executor = ContextCompat.getMainExecutor(context)
    var authenticationSucceeded by remember { mutableStateOf(false) }

    val biometricPrompt = remember {
        BiometricPrompt(context as ComponentActivity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onAuthenticationError()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                authenticationSucceeded = true
                onAuthenticationSuccess()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // Handle failed authentication attempts here
            }
        })
    }

    LaunchedEffect(Unit) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock Your App")
            .setSubtitle("Use your fingerprint to unlock")
            .setNegativeButtonText("Cancel")
            .build()

        if (BiometricManager.from(context).canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            biometricPrompt.authenticate(promptInfo)
        } else {
            onAuthenticationError()
        }
    }

    // UI that will be shown while biometric authentication is taking place
    if (!authenticationSucceeded) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Authenticating...", fontWeight = FontWeight.Bold)
        }
    }
}