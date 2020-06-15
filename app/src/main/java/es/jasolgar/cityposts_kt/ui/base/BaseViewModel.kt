package es.jasolgar.cityposts_kt.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import es.jasolgar.cityposts_kt.data.DataManager
import es.jasolgar.cityposts_kt.utils.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference


open class BaseViewModel<N>() : ViewModel() {

    lateinit var mDataManager : DataManager

    lateinit var  mSchedulerProvider: SchedulerProvider

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var isLoading: ObservableBoolean? = ObservableBoolean()

    private var mNavigator: WeakReference<N?>? = null

    constructor( dataManager: DataManager,schedulerProvider: SchedulerProvider) : this() {
        mDataManager = dataManager
        mSchedulerProvider = schedulerProvider
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    open fun getCompositeDisposable(): CompositeDisposable { return compositeDisposable }

    open fun getDataManager(): DataManager {   return mDataManager  }

    open fun getIsLoading(): ObservableBoolean? { return isLoading  }

    fun setIsLoading(isLoading: Boolean) { this.isLoading?.set(isLoading)  }

    fun requestIsLoading(): Observable<Boolean> {
        return Observable.fromCallable { isLoading?.get() }
    }

    fun notifyIsLoading(isLoading: Boolean): Observable<Boolean?>? {
        return Observable.fromCallable {
            setIsLoading(isLoading)
            true
        }
    }

    open fun getNavigator(): N? {
        return mNavigator?.get()
    }

    open fun setNavigator(navigator: N) {
        mNavigator = WeakReference(navigator)
    }

    open fun getSchedulerProvider(): SchedulerProvider {
        return mSchedulerProvider
    }
}
