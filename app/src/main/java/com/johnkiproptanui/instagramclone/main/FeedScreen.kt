package com.johnkiproptanui.instagramclone.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.johnkiproptanui.instagramclone.base.InstagramViewModel
import androidx.compose.material.Text
import androidx.compose.ui.Modifier

@Composable
 fun FeedScreen(navController: NavController, vm:InstagramViewModel){
     Column(modifier = Modifier.fillMaxSize()) {
         Column(modifier = Modifier.weight(1f)) {
             Text(text = "Feed Screen")
         }
         
         BottomNavigationMenu(selectedItem = BottomNavigationItem.FEED, 
             navController =navController )
         
     }
}