package com.johnkiproptanui.instagramclone.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.johnkiproptanui.instagramclone.data.Event
import com.johnkiproptanui.instagramclone.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject
const val USERS = "users"
@HiltViewModel
class InstagramViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage
) : ViewModel() {
    val signedIn= mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val popUpNotification = mutableStateOf<Event<String>?>(null)

    fun onSignUp(username: String, email: String, password: String) {
        inProgress.value = true

        db.collection(USERS).whereEqualTo("username", username).get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0){
                    handleException(customMessage = "Username already exists")
                    inProgress.value = false
                }else{
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener{ task->
                            if(task.isSuccessful){
                                signedIn.value = true
                                //Create profile
                            }else{
                                handleException(task.exception, "SignUp Failed")
                            }
                            inProgress.value = false
                        }
                }
            }
            .addOnFailureListener {  }
    }

    fun handleException(exception: Exception? = null, customMessage:String=""){
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if(customMessage.isEmpty()) errorMsg else "$customMessage : $errorMsg"
        popUpNotification.value = Event(message)
    }
}