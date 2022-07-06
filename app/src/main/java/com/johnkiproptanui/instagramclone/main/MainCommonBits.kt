package com.johnkiproptanui.instagramclone.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.johnkiproptanui.instagramclone.DestinationScreen
import com.johnkiproptanui.instagramclone.base.InstagramViewModel

@Composable
fun NotificationMessage(viewModel: InstagramViewModel){
    val notificationState = viewModel.popUpNotification.value
    val notificationMessage = notificationState?.getContentOrNull()
    if(notificationMessage != null){
        Toast.makeText(LocalContext.current, notificationMessage, Toast.LENGTH_LONG).show()
    }
}
@Composable
fun CommonProgressSpinner(){
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        CircularProgressIndicator()
    }
}

fun navigateTo(navController: NavController, dest: DestinationScreen){
    navController.navigate(dest.route){
        popUpTo(dest.route)
        //ensures we recycle screens avoiding a large number in our backstack
        launchSingleTop =true
    }
}

@Composable
fun CheckSignedIn(vm:InstagramViewModel, navController: NavController){
    val alreadySignedIn = remember{ mutableStateOf(false)}
    val signedIn= vm.signedIn.value
    if(signedIn && !alreadySignedIn.value){
        alreadySignedIn.value=true
        navController.navigate(DestinationScreen.Feed.route){
            //to remove every composable from the back stack were do this
            popUpTo(0)
        }
    }
}