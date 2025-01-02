package uk.ac.tees.mad.cc

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.cc.screens.Authentication
import uk.ac.tees.mad.cc.screens.EditProfile
import uk.ac.tees.mad.cc.screens.History
import uk.ac.tees.mad.cc.screens.Home
import uk.ac.tees.mad.cc.screens.LogIn
import uk.ac.tees.mad.cc.screens.Profile
import uk.ac.tees.mad.cc.screens.Result
import uk.ac.tees.mad.cc.screens.SignUp
import uk.ac.tees.mad.cc.screens.Splash
import uk.ac.tees.mad.cc.ui.theme.CurrencyConverterAppTheme
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val biometricAuthentication = BiometricLockScreen(this)
        val viewModel: AppViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme = viewModel.isDarkModeOn.collectAsState()
            CurrencyConverterAppTheme(darkTheme = isDarkTheme.value) {
                navigate(biometricAuthentication)
            }
        }
    }
}


sealed class NavigationItems(val route : String){
    object Splash : NavigationItems(route = "splash")
    object LogIn : NavigationItems(route = "login")
    object SignUp : NavigationItems(route = "signup")
    object Home : NavigationItems(route = "home")
    object Result : NavigationItems(route = "result/{currency}/{fromCurrency}"){
        fun createRoute(currency : String, fromCurrency : String) = "result/$currency/$fromCurrency"
    }
    object Authentication : NavigationItems(route = "authentication")
    object Profile : NavigationItems(route = "profile")
    object EditProfile : NavigationItems(route = "editProfile")
    object History : NavigationItems(route = "history")
}



@Composable
fun navigate(biometricAuthentication : BiometricLockScreen) {
    val navController = rememberNavController()
    val vm: AppViewModel = viewModel()
    Surface {
        NavHost(navController = navController, startDestination = NavigationItems.Splash.route) {
            composable(NavigationItems.Splash.route) {
                Splash(vm, navController)
            }
            composable(NavigationItems.Home.route) {
                Home(vm, navController)
            }
            composable(NavigationItems.LogIn.route) {
                LogIn(vm, navController)
            }
            composable(NavigationItems.SignUp.route) {
                SignUp(vm, navController)
            }
            composable(NavigationItems.Result.route) {
                val currency = it.arguments?.getString("currency")
                val fromCurrency = it.arguments?.getString("fromCurrency")
                Result(vm, navController, currency, fromCurrency)
            }
            composable(NavigationItems.Authentication.route) {
                Authentication(navController, vm, biometricAuthentication)
            }
            composable(NavigationItems.Profile.route) {
                Profile(navController, vm)
            }
            composable(NavigationItems.EditProfile.route){
                EditProfile(navController, vm)
            }
            composable(NavigationItems.History.route){
                History(navController, vm)
            }
        }
    }
}