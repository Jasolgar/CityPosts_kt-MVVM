package es.jasolgar.cityposts_kt.ui.main

import es.jasolgar.cityposts_kt.data.DataManager
import es.jasolgar.cityposts_kt.ui.base.BaseViewModel
import es.jasolgar.cityposts_kt.utils.AppLogger
import es.jasolgar.cityposts_kt.utils.rx.SchedulerProvider


class MainViewModel(dataManager: DataManager,schedulerProvider: SchedulerProvider) : BaseViewModel<MainNavigator>(dataManager,schedulerProvider) {

    companion object{
        val TAG : String = "MainViewModel"
    }

    init {
        loadPostCards()
    }

    private fun loadPostCards() {
        getCompositeDisposable().add(
            getDataManager()
                .isEmptyCards()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                    { getNavigator()?.loadPostFragment() }
                , { throwable: Throwable ->
                    AppLogger.w(
                        throwable.message,
                        "loadPostCards: %s"
                    )
                })
        )
    }
}