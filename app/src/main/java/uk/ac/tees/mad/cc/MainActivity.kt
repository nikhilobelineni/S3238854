package uk.ac.tees.mad.cc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.cc.screens.Splash
import uk.ac.tees.mad.cc.ui.theme.CurrencyConverterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterAppTheme {
                navigate()
            }
        }
    }
}


sealed class NavigationItems(val route : String){
    object Splash : NavigationItems(route = "splash")
}



@Composable
fun navigate(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationItems.Splash.route){
        composable(NavigationItems.Splash.route){
            Splash()
        }

    }
}