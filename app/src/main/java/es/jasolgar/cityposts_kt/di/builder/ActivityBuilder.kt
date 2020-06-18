package es.jasolgar.cityposts_kt.di.builder

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import es.jasolgar.cityposts_kt.ui.details.DetailsActivity
import es.jasolgar.cityposts_kt.ui.main.MainActivity
import es.jasolgar.cityposts_kt.ui.splash.SplashActivity

//@InstallIn(ApplicationComponent::class)
//@Module
abstract class ActivityBuilder {

    //@Binds
    abstract fun bindSplashActivity(): SplashActivity

    //@Binds
    abstract fun bindDetailsActivity(): DetailsActivity

    //@Binds
    abstract fun bindMainActivity(): MainActivity

}