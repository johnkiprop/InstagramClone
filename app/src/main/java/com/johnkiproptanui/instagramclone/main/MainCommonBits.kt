package com.johnkiproptanui.instagramclone.main

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.johnkiproptanui.instagramclone.base.InstagramViewModel

@Composable
fun NotificationMessage(viewModel: InstagramViewModel){
    val notificationState = viewModel.popUpNotification.value
    val notificationMessage = notificationState?.getContentOrNull()
    if(notificationMessage != null){
        Toast.makeText(LocalContext.current, notificationMessage, Toast.LENGTH_LONG).show()
    }
}