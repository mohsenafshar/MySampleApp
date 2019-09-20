package ir.mohsenafshar.mysampleapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ir.mohsenafshar.mysampleapp.MyApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        WaybillModule::class,
        MainModule::class,
        DetailModule::class,
        NetworkModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {

    //    UserRepository getUserRepository();

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}
