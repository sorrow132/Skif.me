package yuresko.skifme.di.components

import dagger.Component
import yuresko.skifme.authentication.ActivityThree
import yuresko.skifme.di.modules.NetworkModule
import yuresko.skifme.di.modules.RepositoryModule
import yuresko.skifme.registration.ActivityTwo
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface AppComponent {

    fun inject(provider: ActivityTwo)
    fun inject(provider: ActivityThree)
}