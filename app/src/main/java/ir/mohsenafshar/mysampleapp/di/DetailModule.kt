package ir.mohsenafshar.mysampleapp.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ir.mohsenafshar.mysampleapp.ui.detail.DetailFragment
import ir.mohsenafshar.mysampleapp.ui.detail.DetailViewModel
import ir.mohsenafshar.mysampleapp.ui.waybill.WaybillListFragment
import ir.mohsenafshar.mysampleapp.ui.waybill.WaybillListViewModel
import ir.mohsenafshar.myspotify.di.ViewModelKey

/**
 * Dagger module for the tasks list feature.
 */
@Module
abstract class DetailModule {

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
        ])
    internal abstract fun detailFragment(): DetailFragment


    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel
}
