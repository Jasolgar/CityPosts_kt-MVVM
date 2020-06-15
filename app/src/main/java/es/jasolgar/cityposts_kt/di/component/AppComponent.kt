package es.jasolgar.cityposts_kt.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import es.jasolgar.cityposts_kt.di.builder.ActivityBuilder
import es.jasolgar.cityposts_kt.di.module.AppModule
import es.jasolgar.cityposts_kt.ui.base.BaseApplication
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent {

    fun inject(app: BaseApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
    
}