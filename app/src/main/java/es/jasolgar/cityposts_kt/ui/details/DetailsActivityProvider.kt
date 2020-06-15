package es.jasolgar.cityposts_kt.ui.details

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class DetailsActivityProvider {
    @ContributesAndroidInjector(modules = [DetailsActivityModule::class])
    abstract fun provideDetailsActivityFactory(): DetailsActivity?
}
