package com.johnkiproptanui.instagramclone.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.johnkiproptanui.instagramclone.data.Event
import com.johnkiproptanui.instagramclone.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.sign

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

    init{
        val currentUser = auth.currentUser
        signedIn.value = currentUser !=null
        currentUser?.uid?.let {
            getUserData(it)
        }

    }

    fun onSignUp(username: String, email: String, password: String) {
        //flag for if fields are empty
        if(username.isEmpty() or email.isEmpty() or password.isEmpty()){
            handleException(customMessage = "Please Fill In All Fields")
            return
        }

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
                                createOrUpdateProfile(username = username)
                            }else{
                                handleException(task.exception, "SignUp Failed")
                            }
                            inProgress.value = false
                        }
                }
            }
            .addOnFailureListener {  }
    }

    fun onLogin(email:String, pass:String){
        if( email.isEmpty() or pass.isEmpty()){
            handleException(customMessage = "Please Fill In All Fields")
            return
        }
        inProgress.value = true
        auth.signInWithEmailAndPassword(email,pass)
            .addOnCompleteListener{task->
               if(task.isSuccessful){
                   signedIn.value = true
                   inProgress.value=true
                   auth.currentUser?.uid?.let{ uid->
                    getUserData(uid)
                       handleException(customMessage = "Login Success")
                   }
               } else{
                   handleException(task.exception, "Login Failed")
                   inProgress.value=false
               }

            }
            .addOnFailureListener { exc->
                handleException(exc,"Login Failed")
                inProgress.value = false
            }
    }

    private fun createOrUpdateProfile(
        name:String? =null,
        username: String? =null,
        bio: String? =null,
        imageUrl:String? =null
        ) {
        val uid = auth.currentUser?.uid;
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            username= username ?: userData.value?.username,
            bio = bio ?: userData.value?.bio,
            imageUrl = imageUrl ?: userData.value?.imageUrl,
            following = userData.value?.following
        )
        uid?.let{uid ->
            inProgress.value =true;
            db.collection(USERS).document(uid).get().addOnSuccessListener {
                if(it.exists()){
                    it.reference.update(userData.toMap())
                        .addOnSuccessListener {
                            this.userData.value = userData
                            inProgress.value = false;
                        }
                        .addOnFailureListener {
                            handleException(it, "Cannot update user");
                            inProgress.value = false
                        }
                }
                else{
                    db.collection(USERS).document(uid).set(userData)
                    getUserData(uid)
                    inProgress.value = false
                }
            }
                .addOnFailureListener {
                    handleException(it, "Cannot create user")
                    inProgress.value = false
                }

        }
    }

    private fun getUserData(uid: String) {
        inProgress.value = true
        db.collection(USERS).document(uid).get()
            .addOnSuccessListener {
                val user = it.toObject<UserData>()
                userData.value = user
                inProgress.value = false
                popUpNotification.value = Event("User Data retrieved Successfully")

            }
            .addOnFailureListener {
                handleException(it,"Cannot retrieve user data")
                inProgress.value = false}

    }

    fun handleException(exception: Exception? = null, customMessage:String=""){
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if(customMessage.isEmpty()) errorMsg else "$customMessage : $errorMsg"
        popUpNotification.value = Event(message)
    }
}