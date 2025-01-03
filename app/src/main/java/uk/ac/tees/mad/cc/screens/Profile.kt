package uk.ac.tees.mad.cc.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.cc.AppViewModel
import uk.ac.tees.mad.cc.NavigationItems
import uk.ac.tees.mad.cc.R
import uk.ac.tees.mad.cc.ui.theme.poppins

@Composable
fun Profile(navController: NavHostController, vm: AppViewModel) {
    val userData = vm.userData
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("Currency_Conversion_app", Context.MODE_PRIVATE)
    var isAppLockOn by remember {
        mutableStateOf(sharedPref.getBoolean("isAppLockOn", false))
    }
    val isDarkModeOn by vm.isDarkModeOn.collectAsState()


    Box {
        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))
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
                    onClick = { navController.navigate(NavigationItems.EditProfile.route) },
                    modifier = Modifier.height(50.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF066666))
                ) {
                    Text(text = "Edit Profile")
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Name: ${userData.value.name}", fontSize = 20.sp, fontFamily = poppins)
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Email: ${userData.value.email}", fontFamily = poppins)
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Number: ${userData.value.number}", fontFamily = poppins)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = "App Lock: ",
                        fontFamily = poppins,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = isAppLockOn,
                        onCheckedChange = {
                            isAppLockOn = it
                            sharedPref.edit().putBoolean("isAppLockOn", it).apply()
                        })
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = "Dark Mode: ",
                        fontFamily = poppins,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = isDarkModeOn,
                        onCheckedChange = { vm.toggleTheme(it) })
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.clickable {
                    navController.navigate(NavigationItems.History.route)
                }) {
                    Text(
                        text = "Goto History",
                        fontFamily = poppins,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.clickable {
                    vm.deleteCurrencyHistory()
                    Toast.makeText(context, "History Deleted", Toast.LENGTH_SHORT).show()
                }) {
                    Text(
                        text = "Delete History",
                        fontFamily = poppins,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier.clickable {
                    vm.signOut()
                    sharedPref.edit().putBoolean("isAppLockOn", false).apply()
                    sharedPref.edit().putBoolean("isDarkModeOn", false).apply()
                    navController.navigate(NavigationItems.LogIn.route){
                        popUpTo(0)
                    }
                }) {
                    Text(
                        text = "Log Out",
                        fontFamily = poppins,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
                }
            }
        }
        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null,
            modifier = Modifier.padding(26.dp).size(30.dp).clickable {
                navController.popBackStack()
            }, tint = Color.White)
    }
}