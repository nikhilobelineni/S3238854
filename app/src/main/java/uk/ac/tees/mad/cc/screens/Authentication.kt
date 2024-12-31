package uk.ac.tees.mad.cc.screens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import uk.ac.tees.mad.cc.AppViewModel
import uk.ac.tees.mad.cc.BiometricLockScreen
import uk.ac.tees.mad.cc.NavigationItems
import uk.ac.tees.mad.cc.R
import uk.ac.tees.mad.cc.ui.theme.poppins

@Composable
fun Authentication(
    navController: NavHostController,
    vm: AppViewModel,
    biometricAuth: BiometricLockScreen) {
    val activity = LocalContext.current as FragmentActivity
    var message by remember {
        mutableStateOf("")
    }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
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
        ) {
            Image(painter = painterResource(id = R.drawable.fingerprint), contentDescription = null)
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Unlock using fingerprint to begin using the app",
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Click anywhere to start scanning",
                fontFamily = poppins,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }
        Text(text = "The app is locked", fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,modifier = Modifier.align(Alignment.TopCenter).padding(top = 60.dp))
    }
}