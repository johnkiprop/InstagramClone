package com.johnkiproptanui.instagramclone.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.android.gms.common.internal.service.Common
import com.johnkiproptanui.instagramclone.DestinationScreen
import com.johnkiproptanui.instagramclone.base.InstagramViewModel
import com.johnkiproptanui.instagramclone.R

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

@Composable
fun CommonImage(
    data:String?,
    modifier:Modifier = Modifier.wrapContentSize(),
    contentScale:ContentScale = ContentScale.Crop
){
    val painter = rememberImagePainter(data = data)
    Image(painter = painter, contentDescription =null, modifier=modifier,contentScale=contentScale)
    if(painter.state is ImagePainter.State.Loading){
        CommonProgressSpinner()
    }
}

@Composable
fun UserImageCard(userImage: String?,
                  modifier: Modifier= Modifier
                      .padding(8.dp)
                      .size(64.dp)){
    Card(shape = CircleShape, modifier = modifier) {
       if(userImage.isNullOrEmpty()){
           Image(
               painter = painterResource(id = R.drawable.ic_user),
               contentDescription = null,
               colorFilter = ColorFilter.tint(Color.Gray))
       }else{
           CommonImage(data = userImage)
       }
    }
}

@Composable
fun CommonDivider(){
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.3f)
            .padding(top=8.dp, bottom=8.dp)
    )
}