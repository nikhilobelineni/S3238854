package uk.ac.tees.mad.cc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.cc.screens.Home
import uk.ac.tees.mad.cc.screens.LogIn
import uk.ac.tees.mad.cc.screens.Result
import uk.ac.tees.mad.cc.screens.SignUp
import uk.ac.tees.mad.cc.screens.Splash
import uk.ac.tees.mad.cc.ui.theme.CurrencyConverterAppTheme
@AndroidEntryPoint
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
    object LogIn : NavigationItems(route = "login")
    object SignUp : NavigationItems(route = "signup")
    object Home : NavigationItems(route = "home")
    object Result : NavigationItems(route = "result/{currency}"){
        fun createRoute(currency : String) = "result/$currency"
    }
}



@Composable
fun navigate(){
    val navController = rememberNavController()
    val vm : AppViewModel = viewModel()
    NavHost(navController = navController, startDestination = NavigationItems.Splash.route){
        composable(NavigationItems.Splash.route){
            Splash(vm, navController)
        }
        composable(NavigationItems.Home.route){
            Home(vm, navController)
        }
        composable(NavigationItems.LogIn.route){
            LogIn(vm, navController)
        }
        composable(NavigationItems.SignUp.route){
            SignUp(vm, navController)
        }
        composable(NavigationItems.Result.route){
            val currency = it.arguments?.getString("currency")
            Result(vm, navController, currency)
        }
    }
}