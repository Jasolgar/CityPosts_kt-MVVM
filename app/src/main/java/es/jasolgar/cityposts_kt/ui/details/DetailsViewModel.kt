package es.jasolgar.cityposts_kt.ui.details

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import es.jasolgar.cityposts_kt.data.DataManager
import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.data.model.db.Post
import es.jasolgar.cityposts_kt.data.model.db.User
import es.jasolgar.cityposts_kt.data.model.others.Geo
import es.jasolgar.cityposts_kt.ui.base.BaseViewModel
import es.jasolgar.cityposts_kt.utils.AppLogger
import es.jasolgar.cityposts_kt.utils.rx.SchedulerProvider
import io.reactivex.Observable


class DetailsViewModel(dataManager: DataManager,schedulerProvider: SchedulerProvider) : BaseViewModel<DetailsNavigator>(dataManager,schedulerProvider) {

    val imageUrl = ObservableField<String>()

    val avatarUrl = ObservableField<String>()

    val userFullName = ObservableField<String>()

    val userMail = ObservableField<String>()

    val userAddress = ObservableField<String>()

    val userPhone = ObservableField<String>()

    val userWeb = ObservableField<String>()

    val userCompany = ObservableField<String>()

    val commentsCount = ObservableField<String>()

    val postsBody = ObservableField<String>()

    private val commentListMutableLiveData: MutableLiveData<List<Comment>> = MutableLiveData<List<Comment>>()

    private var mGeo: Geo? = null

    fun notifyBundlePostId(postId : Long){
        setIsLoading(true)

        getCompositeDisposable()
            .add(
                getDataManager().retrievePostsById(postId)
                    .flatMap { post ->   imageUrl.set(post.imageUrl)
                        postsBody.set(post.body)
                        getDataManager().retrieveUserById(post.userId)
                    }
                    .observeOn(getSchedulerProvider().io())
                    .flatMap { user ->    mGeo = user.address.geo

                        avatarUrl.set(user.avatarUrl);

                        userFullName.set(user.username +" - " + user.name)

                        userMail.set(user.email);

                        userAddress.set(user.avatarUrl);

                        userPhone.set(user.phone);

                        userWeb.set(user.website);

                        userCompany.set(user.company.toString());

                        getDataManager().requestCommentsByPostId(postId)
                    }
                    .observeOn(getSchedulerProvider().ui())
                    .flatMap { comments ->   setCommentListMutableLiveData(comments)
                        setIsLoading(false)
                        Observable.just(true) }
                    .subscribe(
                        { },
                     { throwable: Throwable ->
                        AppLogger.w(
                            throwable.message,
                            "notifyBundlePostId: %s"
                        )
                    }))
    }

    fun getCommentListMutableLiveData(): MutableLiveData<List<Comment>> {
        return commentListMutableLiveData
    }

    private fun setCommentListMutableLiveData(commentList: List<Comment>) {
        commentListMutableLiveData.postValue(commentList)
        commentsCount.set(commentList.size.toString())
    }

    fun onMailClick() {
        getNavigator()?.launchMail(userMail.get())
    }

    fun onAddressClick() {
        getNavigator()?.launchGeoMaps(mGeo?.lat, mGeo?.lng)
    }

    fun onPhoneClick() {
        getNavigator()?.launchPhoneCall(userPhone.get())
    }

    fun onWebClick() {
        getNavigator()?.loadWebUrl(userWeb.get())
    }
}