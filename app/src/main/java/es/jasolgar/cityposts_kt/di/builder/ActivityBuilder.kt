package es.jasolgar.cityposts_kt.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.jasolgar.cityposts_kt.ui.details.DetailsActivity
import es.jasolgar.cityposts_kt.ui.details.DetailsActivityModule
import es.jasolgar.cityposts_kt.ui.main.MainActivity
import es.jasolgar.cityposts_kt.ui.posts.PostsFragmentProvider
import es.jasolgar.cityposts_kt.ui.splash.SplashActivity


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity?

    @ContributesAndroidInjector(modules = [DetailsActivityModule::class])
    abstract fun bindDetailsActivity(): DetailsActivity?

    @ContributesAndroidInjector(modules = [PostsFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity?

}