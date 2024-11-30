package uk.ac.tees.mad.cc.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import uk.ac.tees.mad.cc.AppViewModel

@Composable
fun Home(vm: AppViewModel, navController: NavHostController) {
    vm.fetchLatestRates()
    Scaffold {iv->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(iv)) {
            Row(modifier = Modifier.fillMaxWidth()) {
            }
        }
    }
}