package com.johnkiproptanui.instagramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.johnkiproptanui.instagramclone.auth.LoginScreen
import com.johnkiproptanui.instagramclone.auth.SignUpScreen
import com.johnkiproptanui.instagramclone.base.InstagramViewModel
import com.johnkiproptanui.instagramclone.main.NotificationMessage
import com.johnkiproptanui.instagramclone.ui.theme.InstagramCloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                  InstagramApp()
                }
            }
        }
    }
}

sealed class DestinationScreen(val route : String){
    object SignUp : DestinationScreen("signup")
    object Login: DestinationScreen("login")
}

@Composable
fun InstagramApp() {
    val viewModel = hiltViewModel<InstagramViewModel>()
    val navController = rememberNavController()

    NotificationMessage(viewModel = viewModel)

   NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route ){
       composable(DestinationScreen.SignUp.route){
           SignUpScreen(navController = navController, viewModel = viewModel)
       }
       composable(DestinationScreen.Login.route){
            LoginScreen(navController = navController, vm =viewModel )
       }
   }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InstagramCloneTheme {
        InstagramApp()
    }
}