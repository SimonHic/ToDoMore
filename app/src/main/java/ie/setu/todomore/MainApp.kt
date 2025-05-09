package ie.setu.todomore

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp : Application(){
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(context = this)
    }
}