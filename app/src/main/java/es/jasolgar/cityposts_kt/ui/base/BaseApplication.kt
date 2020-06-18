package es.jasolgar.cityposts_kt.ui.base

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import dagger.hilt.android.HiltAndroidApp
import es.jasolgar.cityposts_kt.BuildConfig
import es.jasolgar.cityposts_kt.utils.AppLogger
import okhttp3.OkHttpClient
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application()  {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()

        AppLogger.init()

        AndroidNetworking.initialize(applicationContext,okHttpClient)

        if (BuildConfig.DEBUG) AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)

        Timber.plant(DebugTree())
    }
}