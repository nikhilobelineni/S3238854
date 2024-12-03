package uk.ac.tees.mad.cc.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.cc.AppViewModel
import uk.ac.tees.mad.cc.ui.theme.poppins

@Composable
fun SignUp(vm: AppViewModel, navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Text(
            text = "Let's Get Started!",
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Create an account to get started",
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(value = name, onValueChange = { name = it },
            label = {
                Text(
                    text = "Name",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp
                )
            })
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = email, onValueChange = { email = it },
            label = {
                Text(
                    text = "Email",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp
                )
            })
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = number, onValueChange = { number = it },
            label = {
                Text(
                    text = "Phone number",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp
                )
            })
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = password, onValueChange = { password = it },
            label = {
                Text(
                    text = "Password",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp
                )
            })
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.width(280.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                Color.Black.copy(alpha = 0.3f)
            )
        ) {
            Text(text = "SIGN UP")
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Already have an account? Sign In", fontFamily = poppins, modifier = Modifier.padding(bottom = 70.dp).clickable {
            navController.popBackStack()
        })
    }

}