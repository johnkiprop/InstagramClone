package com.johnkiproptanui.instagramclone.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.johnkiproptanui.instagramclone.DestinationScreen
import com.johnkiproptanui.instagramclone.main.navigateTo
import com.johnkiproptanui.instagramclone.R
import com.johnkiproptanui.instagramclone.base.InstagramViewModel
import com.johnkiproptanui.instagramclone.main.CheckSignedIn
import com.johnkiproptanui.instagramclone.main.CommonProgressSpinner

@Composable
fun LoginScreen(navController: NavController, vm : InstagramViewModel) {
    //check if already signed in user
    CheckSignedIn(vm = vm, navController =navController )
    //to dismiss keyboard
    val focus = LocalFocusManager.current
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(
                //this allows for the keyboard adjust resize to remain the one added previously to manifest
                rememberScrollState()
            )) {
            val emailState = remember { mutableStateOf(TextFieldValue())}
            val passState = remember { mutableStateOf(TextFieldValue())}
            Image(painter = painterResource(
                id = R.drawable.instagramlogo),
                contentDescription = null,
            modifier = Modifier
                .width(250.dp)
                .padding(top = 16.dp)
                .padding(8.dp))
            Text(text = "Login",
            modifier = Modifier.padding(8.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif
            )
            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text( text = "Email")})

            OutlinedTextField(
                value = passState.value,
                onValueChange = { passState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text( text = "Password")},
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                    //dismiss keyboard
                    focus.clearFocus(force = true)
                    vm.onLogin(emailState.value.text,passState.value.text)
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "LOGIN")
            }
            Text(text = "New here? Go to signup ->",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateTo(navController = navController, DestinationScreen.SignUp)
                    })
        }
        val isLoading = vm.inProgress.value
        if (isLoading){
            CommonProgressSpinner()
        }
    }



}