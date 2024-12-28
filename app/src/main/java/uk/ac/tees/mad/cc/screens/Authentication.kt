package uk.ac.tees.mad.cc.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import uk.ac.tees.mad.cc.AppViewModel
import uk.ac.tees.mad.cc.BiometricLockScreen
import uk.ac.tees.mad.cc.NavigationItems

@Composable
fun Authentication(
    navController: NavHostController,
    vm: AppViewModel,
    biometricAuth: BiometricLockScreen) {
    val activity = LocalContext.current as FragmentActivity
    var message by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize().clickable {
            biometricAuth.promptBiometricAuth(
                "App Lock",
                "Scan your fingerprint to continue",
                "Cancel",
                activity,
                onSuccess = {
                    navController.navigate(NavigationItems.Home.route)
                },
                onFailed = {
                    message = "Failed"
                },
                onError = { _, error ->
                    message = error
                }
            )
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Please authenticate with your fingerprint to continue")
    }
}