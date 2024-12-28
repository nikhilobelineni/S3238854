package uk.ac.tees.mad.cc.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.cc.AppViewModel
import uk.ac.tees.mad.cc.R

@Composable
fun Profile(navController: NavHostController, vm: AppViewModel) {
    val userData = vm.userData
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("Currency_Conversion_app", Context.MODE_PRIVATE)
    var isAppLockOn by remember {
        mutableStateOf(sharedPref.getBoolean("isAppLockOn", false))
    }

    Column(Modifier.fillMaxSize()) {
        if (userData.value.profile.isNotEmpty()) {
            AsyncImage(
                model = userData.value.profile, contentDescription = "profile_photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp), contentScale = ContentScale.FillBounds
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.contact), contentDescription = "null",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp), contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.height(50.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF066666))
            ) {
                Text(text = "Edit Profile")
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${userData.value.name}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Email: ${userData.value.email}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Number: ${userData.value.number}")
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = "App Lock: ")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = isAppLockOn,
                    onCheckedChange = {
                        isAppLockOn = it
                        sharedPref.edit().putBoolean("isAppLockOn", it).apply()
                    })
            }
            Row {
                Text(text = "Dark Mode")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = vm.isDarkTheme.value,
                    onCheckedChange = {
                        vm.toggleTheme(it)
                    })
            }
        }
    }
}