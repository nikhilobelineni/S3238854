package uk.ac.tees.mad.cc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.cc.AppViewModel
import uk.ac.tees.mad.cc.NavigationItems
import uk.ac.tees.mad.cc.R
import uk.ac.tees.mad.cc.ui.theme.poppins

@Composable
fun LogIn(vm: AppViewModel, navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val isSignedIn = vm.isSignedIn

    if (isSignedIn.value){
        navController.navigate(NavigationItems.Home.route){
            popUpTo(0)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Image(
            painter = painterResource(id = R.drawable.applogo), contentDescription = null,
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(13.dp))
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Welcome back!",
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Log in to existing account",
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(value = email, onValueChange = { email = it },
            label = {
                Text(
                    text = "Email",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp
                )
            })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = password, onValueChange = { password = it },
            label = {
                Text(
                    text = "Password",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp
                )
            })
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { vm.logIn(context = context, email, password) },
            modifier = Modifier.width(280.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                Color.Black.copy(alpha = 0.3f)
            )
        ) {
            Text(text = "LOG IN")
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Don't have an account? Sign Up", fontFamily = poppins, modifier = Modifier.padding(bottom = 70.dp).clickable {
            navController.navigate(NavigationItems.SignUp.route)
        })
    }
}