package es.jasolgar.cityposts_kt.ui.splash

import es.jasolgar.cityposts_kt.data.DataManager
import es.jasolgar.cityposts_kt.ui.base.BaseViewModel
import es.jasolgar.cityposts_kt.utils.AppLogger
import es.jasolgar.cityposts_kt.utils.rx.SchedulerProvider


class SplashViewModel(dataManager: DataManager,schedulerProvider: SchedulerProvider) : BaseViewModel<SplashNavigator>(dataManager,schedulerProvider) {

    fun onFetchDataStarted() {
        AppLogger.d("startPosts")
        getCompositeDisposable().add(
            getDataManager()
                .fetchPostDataByUsers()
                .subscribeOn(getSchedulerProvider().io())
                .subscribe( {
                    result ->
                        AppLogger.d("fetch Posts Info -> %s", (result))

                        getNavigator()?.openMainActivity()
                },{
                   throwable -> AppLogger.w(throwable.message, "startPosts: %s")
                })
        )
    }
}