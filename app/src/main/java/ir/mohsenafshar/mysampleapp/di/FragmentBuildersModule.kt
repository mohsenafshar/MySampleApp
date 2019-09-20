package ir.mohsenafshar.mysampleapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.mohsenafshar.mysampleapp.ui.waybill.WaybillListFragment
import ir.mohsenafshar.mysampleapp.ui.detail.DetailFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): WaybillListFragment

}
