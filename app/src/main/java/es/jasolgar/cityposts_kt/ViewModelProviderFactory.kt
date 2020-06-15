package es.jasolgar.cityposts_kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.jasolgar.cityposts_kt.data.DataManager
import es.jasolgar.cityposts_kt.ui.details.DetailsViewModel
import es.jasolgar.cityposts_kt.ui.main.MainViewModel
import es.jasolgar.cityposts_kt.ui.posts.PostsViewModel
import es.jasolgar.cityposts_kt.ui.splash.SplashViewModel
import es.jasolgar.cityposts_kt.utils.rx.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelProviderFactory @Inject constructor (private val dataManager: DataManager,
                               private val schedulerProvider: SchedulerProvider
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(dataManager, schedulerProvider) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(dataManager, schedulerProvider) as T
            }
            modelClass.isAssignableFrom(PostsViewModel::class.java) -> {
                PostsViewModel(dataManager, schedulerProvider) as T
            }
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                DetailsViewModel(dataManager, schedulerProvider) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

}