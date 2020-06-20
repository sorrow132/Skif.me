package yuresko.skifme

import android.app.Application
import yuresko.skifme.di.components.AppComponent
import yuresko.skifme.di.components.DaggerAppComponent

class SkiffApplication : Application() {
    val component: AppComponent = DaggerAppComponent
        .builder()
        .build()
}