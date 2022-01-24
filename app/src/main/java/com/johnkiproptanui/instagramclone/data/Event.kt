package com.johnkiproptanui.instagramclone.data

//creating event class to handle any exception by displaying the exception once as opposed to
//leaving it using the mutableStateOf() fun which will display the error every time the viewModel refreshes

 open class Event<out T> (private val content: T) {
  //use setter private to allow only elements of class to alter this variable
    var hasBeenHandled= false
     private set
   fun getContentOrNull(): T?{
       return if(hasBeenHandled){
           null
       }else{
           hasBeenHandled = true
           content
       }
   }
}