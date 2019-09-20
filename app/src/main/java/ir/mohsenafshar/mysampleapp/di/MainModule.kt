package ir.mohsenafshar.mysampleapp.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ir.mohsenafshar.mysampleapp.ui.main.MainActivity
import ir.mohsenafshar.mysampleapp.ui.main.MainViewModel
import ir.mohsenafshar.mysampleapp.ui.waybill.WaybillListFragment
import ir.mohsenafshar.mysampleapp.ui.waybill.WaybillListViewModel
import ir.mohsenafshar.myspotify.di.ViewModelKey

/**
 * Dagger module for the tasks list feature.
 */
@Module
abstract class MainModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}
