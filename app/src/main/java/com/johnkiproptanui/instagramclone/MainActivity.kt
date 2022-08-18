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
import com.johnkiproptanui.instagramclone.auth.ProfileScreen
import com.johnkiproptanui.instagramclone.auth.SignUpScreen
import com.johnkiproptanui.instagramclone.base.InstagramViewModel
import com.johnkiproptanui.instagramclone.main.*
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
    object Feed: DestinationScreen("feed")
    object Search: DestinationScreen("search")
    object MyPosts:DestinationScreen("myposts")
    object Profile:DestinationScreen("profile")
    object NewPost:DestinationScreen("newpost/{imageUri}"){
        fun createRoute(uri: String) = "newpost/$uri"
    }

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
       composable(DestinationScreen.Feed.route){
           FeedScreen(navController = navController, vm = viewModel)
       }
       composable(DestinationScreen.Search.route){
           SearchScreen(navController = navController, vm = viewModel)
       }
       composable(DestinationScreen.MyPosts.route){
           MyPostsScreen(navController = navController, vm = viewModel)
       }
       composable(DestinationScreen.Profile.route){
           ProfileScreen(navController = navController, vm =viewModel )
       }
       composable(DestinationScreen.NewPost.route){navBackStackEntry ->
           val imageUri = navBackStackEntry.arguments?.getString("imageUri")
           imageUri?.let {
               NewPostScreen(navController = navController, vm =viewModel, encodedUri =it )
           }
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