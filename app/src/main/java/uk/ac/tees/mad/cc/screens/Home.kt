package uk.ac.tees.mad.cc.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.cc.AppViewModel
import uk.ac.tees.mad.cc.ui.theme.poppins

@Composable
fun Home(vm: AppViewModel, navController: NavHostController) {
    vm.fetchLatestRates()
    Scaffold {iv->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(iv)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)) {
                Text(text = "Hello", fontFamily = poppins, fontWeight =  FontWeight.SemiBold, fontSize = 25.sp)
                Spacer(modifier = Modifier.weight(1f))
                androidx.compose.material3.Icon(imageVector = Icons.Rounded.Person, contentDescription = "person")

            }
        }
    }
}