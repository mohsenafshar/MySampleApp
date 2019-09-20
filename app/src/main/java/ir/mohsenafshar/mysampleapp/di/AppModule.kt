package ir.mohsenafshar.mysampleapp.di

import android.app.Application
import android.content.Context

import dagger.Binds
import dagger.Module
import dagger.Provides
import ir.mohsenafshar.mysampleapp.data.DataSource
import ir.mohsenafshar.mysampleapp.data.Repository
import ir.mohsenafshar.mysampleapp.data.local.LocalDataProvider
import ir.mohsenafshar.mysampleapp.data.remote.RemoteDataProvider
import ir.mohsenafshar.mysampleapp.data.repository.RepositoryImpl
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {


    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

    @JvmStatic
    @Singleton
    @RemoteDataSource
    @Provides
    fun provideRemoteDataSource(): DataSource {
        return RemoteDataProvider()
    }

    @JvmStatic
    @Singleton
    @LocalDataSource
    @Provides
    fun provideLocalDataSource(): DataSource {
        return LocalDataProvider()
    }


}

@Module
abstract class ApplicationModuleBinds {
    @Singleton
    @Binds
    abstract fun bindRepository(repo: RepositoryImpl): Repository

    @Binds
    abstract fun provideContext(application: Application): Context
}
