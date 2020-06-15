package es.jasolgar.cityposts_kt.ui.posts

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.jasolgar.cityposts_kt.data.DataManager
import es.jasolgar.cityposts_kt.data.model.others.PostInfo
import es.jasolgar.cityposts_kt.ui.base.BaseViewModel
import es.jasolgar.cityposts_kt.utils.AppLogger
import es.jasolgar.cityposts_kt.utils.rx.SchedulerProvider
import io.reactivex.Observable


class PostsViewModel(dataManager: DataManager,schedulerProvider: SchedulerProvider) : BaseViewModel<PostsNavigator>(dataManager,schedulerProvider) {

    private val postItemsLiveData: MutableLiveData<List<PostInfo>> =  MutableLiveData<List<PostInfo>>()

    private val mIsEmptyList = ObservableBoolean()

    fun getPostItemsLiveData(): MutableLiveData<List<PostInfo>> {
        return postItemsLiveData
    }

    fun requestRepoAndFetchData() {
        getCompositeDisposable().add(
            requestIsLoading()
                .flatMap { result ->
                    when(result){
                        true ->  Observable.just(false)
                        false ->  notifyIsLoading(true)?.concatMap{ getDataManager().fetchPostDataByUsers() }
                    }
                }
                .flatMap{ fetchData() }
                .flatMap{ notifyIsLoading(false) }
                .subscribeOn(getSchedulerProvider().io())
                .subscribe( {

                },{
                   throwable ->  AppLogger.w(throwable.message, "removeData: %s")
                }))
    }

   private fun fetchData(): Observable<Boolean>? {
        return getDataManager().retrieveAllPostsInfo()
            .observeOn(getSchedulerProvider().ui())
            .flatMap {
                postInfoList -> Observable.fromCallable {
                    postItemsLiveData.value = postInfoList
                    setIsEmptyList(postInfoList.isEmpty())
                    true
                }
            }
    }

    fun onRemoveDataClick() {
        getNavigator()?.showRemoveDataDialog()
    }

    fun removeData() {
        getCompositeDisposable().add(
            getDataManager().clearPostData()
                .observeOn(getSchedulerProvider().ui())
                .flatMap {
                    mIsEmptyList.set(true)
                    postItemsLiveData.value=emptyList()
                    Observable.just(true)
                }.flatMap{  notifyIsLoading(false) }
                .subscribeOn(getSchedulerProvider().io())
                .subscribe({

                },{
                    throwable ->  AppLogger.w(throwable.message, "removeData: %s")
                })

        )
    }

    fun getIsEmptyList(): ObservableBoolean? {
        return mIsEmptyList
    }

    private fun setIsEmptyList(isLoading: Boolean) {
        mIsEmptyList.set(isLoading)
    }

    fun seedData() {
        getCompositeDisposable().add(
            fetchData()!!.subscribe()
        )
    }
}