package yuresko.skifme

import android.app.Application
import yuresko.skifme.di.components.AppComponent
import yuresko.skifme.di.components.DaggerAppComponent

class SkiffApplication : Application() {
    lateinit var userNum: String

    val component: AppComponent = DaggerAppComponent
        .builder()
        .build()
}