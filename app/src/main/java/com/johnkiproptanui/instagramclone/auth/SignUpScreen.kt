package com.johnkiproptanui.instagramclone.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.johnkiproptanui.instagramclone.base.InstagramViewModel
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.johnkiproptanui.instagramclone.DestinationScreen
import com.johnkiproptanui.instagramclone.R
import com.johnkiproptanui.instagramclone.main.CheckSignedIn
import com.johnkiproptanui.instagramclone.main.CommonProgressSpinner
import com.johnkiproptanui.instagramclone.main.navigateTo

@Composable
fun SignUpScreen(navController: NavController, viewModel: InstagramViewModel){
    //check if already signed in user
    CheckSignedIn(vm = viewModel, navController =navController )
    //to dismiss keyboard
    val focus = LocalFocusManager.current
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(
                rememberScrollState()
            ),
            horizontalAlignment = Alignment.CenterHorizontally) {
            val usernameState = remember { mutableStateOf(TextFieldValue())}
            val emailState = remember { mutableStateOf(TextFieldValue())}
            val passState = remember { mutableStateOf(TextFieldValue())}
            
            Image(
                painter = painterResource(id = R.drawable.instagramlogo),
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )
            Text(text = "SignUp",
                modifier = Modifier.padding(8.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif)

            OutlinedTextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text( text = "Username")})

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
                visualTransformation = PasswordVisualTransformation()) 
            
            Button(

                onClick = {
                    focus.clearFocus(force = true)
                          viewModel.onSignUp(
                              usernameState.value.text,
                              emailState.value.text,
                              passState.value.text
                          )
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "SIGN UP")
            }

            Text(text = "Already a user? Go to Login ->",
            color = Color.Blue,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navigateTo(navController = navController, DestinationScreen.Login)
                })

        }
        val isLoading = viewModel.inProgress.value
        if (isLoading){
            CommonProgressSpinner()
        }
    }
}