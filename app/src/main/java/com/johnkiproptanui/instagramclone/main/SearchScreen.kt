package com.johnkiproptanui.instagramclone.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.johnkiproptanui.instagramclone.base.InstagramViewModel

@Composable
fun SearchScreen(navController: NavController, vm:InstagramViewModel){
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Search Screen")
        }

        BottomNavigationMenu(selectedItem = BottomNavigationItem.SEARCH,
            navController =navController )

    }
}