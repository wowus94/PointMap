package ru.vlyashuk.pointmap

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PointMapApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
        /*FirebaseApp.initializeApp(this)
        println("Firebase initialized ${FirebaseApp.getInstance().name}")*/
    }
}