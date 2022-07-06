package com.johnkiproptanui.instagramclone.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.johnkiproptanui.instagramclone.base.InstagramViewModel
import androidx.compose.material.Text

@Composable
 fun FeedScreen(navController: NavController, vm:InstagramViewModel){
     Text(text="Feed Screen")
}