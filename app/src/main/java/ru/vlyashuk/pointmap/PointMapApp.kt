package ru.vlyashuk.pointmap

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PointMapApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        println("Firebase initialized ${FirebaseApp.getInstance().name}")
    }
}