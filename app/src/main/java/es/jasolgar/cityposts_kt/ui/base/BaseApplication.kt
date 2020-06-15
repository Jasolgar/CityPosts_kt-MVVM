package es.jasolgar.cityposts_kt.ui.base

import android.app.Activity
import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import es.jasolgar.cityposts_kt.BuildConfig
import es.jasolgar.cityposts_kt.di.component.DaggerAppComponent
import es.jasolgar.cityposts_kt.utils.AppLogger
import okhttp3.OkHttpClient
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


class BaseApplication : Application(), HasActivityInjector  {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
        AppLogger.init()
        AndroidNetworking.initialize(applicationContext,okHttpClient)
        if (BuildConfig.DEBUG) AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
        Timber.plant(DebugTree())
    }
}