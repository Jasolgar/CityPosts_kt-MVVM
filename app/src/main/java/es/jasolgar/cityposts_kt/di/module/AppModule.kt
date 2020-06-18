package es.jasolgar.cityposts_kt.di.module

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import es.jasolgar.cityposts_kt.data.AppDataManager
import es.jasolgar.cityposts_kt.data.DataManager
import es.jasolgar.cityposts_kt.data.local.db.AppDatabase
import es.jasolgar.cityposts_kt.data.local.db.AppDbHelper
import es.jasolgar.cityposts_kt.data.local.db.DbHelper
import es.jasolgar.cityposts_kt.data.local.prefs.AppPreferencesHelper
import es.jasolgar.cityposts_kt.data.local.prefs.PreferencesHelper
import es.jasolgar.cityposts_kt.data.remote.ApiHelper
import es.jasolgar.cityposts_kt.data.remote.AppApiHelper
import es.jasolgar.cityposts_kt.di.DatabaseInfo
import es.jasolgar.cityposts_kt.di.PreferenceInfo
import es.jasolgar.cityposts_kt.ui.base.BaseActivity
import es.jasolgar.cityposts_kt.ui.posts.PostsFragment
import es.jasolgar.cityposts_kt.utils.AppConstants
import es.jasolgar.cityposts_kt.utils.rx.AppSchedulerProvider
import es.jasolgar.cityposts_kt.utils.rx.SchedulerProvider
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }

    @Provides
    @Singleton
    fun provideApplication(application: Application): Context {
        return application as Context
    }

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    fun provideAppDatabase( @DatabaseInfo dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstants.PREFERENCE_NAME
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideLinearLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    @Provides
    fun provideBaseActivity(fragment: Fragment): BaseActivity<*, *> {
        return fragment.activity as BaseActivity<*, *>
    }


}