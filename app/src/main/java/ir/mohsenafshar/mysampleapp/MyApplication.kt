package ir.mohsenafshar.mysampleapp

import com.facebook.stetho.Stetho

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ir.mohsenafshar.mysampleapp.di.DaggerAppComponent
import timber.log.Timber

class MyApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)
    }

    /*@Inject
    UserRepository userRepository;*/

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    //    @VisibleForTesting
    //    public UserRepository getTasksRepository() {
    //        return userRepository;
    //    }
}
