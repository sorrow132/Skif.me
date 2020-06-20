package yuresko.skifme.di.modules

import dagger.Module
import dagger.Provides
import yuresko.skifme.network.SkiffMeApi
import yuresko.skifme.repository.IRepository
import yuresko.skifme.repository.RepositoryImpl
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(network: SkiffMeApi): IRepository {
        return RepositoryImpl(network)
    }
}