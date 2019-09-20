package ir.mohsenafshar.mysampleapp.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ir.mohsenafshar.mysampleapp.ui.waybill.WaybillListFragment
import ir.mohsenafshar.mysampleapp.ui.waybill.WaybillListViewModel
import ir.mohsenafshar.myspotify.di.ViewModelKey

/**
 * Dagger module for the tasks list feature.
 */
@Module
abstract class WaybillModule {

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
        ])
    internal abstract fun waybillFragment(): WaybillListFragment


    @Binds
    @IntoMap
    @ViewModelKey(WaybillListViewModel::class)
    abstract fun bindWaybillViewModel(waybillListViewModel: WaybillListViewModel): ViewModel
}
