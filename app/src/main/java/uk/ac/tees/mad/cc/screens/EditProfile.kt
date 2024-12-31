package uk.ac.tees.mad.cc.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.cc.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(navController: NavHostController, vm: AppViewModel) {
    val userData = vm.userData.value
    Scaffold(
        topBar = {
            TopAppBar(title = { Box {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "back",
                    modifier = Modifier.size(30.dp).padding(16.dp))
            } })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}