package es.jasolgar.cityposts_kt.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.jasolgar.cityposts_kt.data.local.db.DbHelper
import es.jasolgar.cityposts_kt.data.local.prefs.PreferencesHelper
import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.data.model.db.Post
import es.jasolgar.cityposts_kt.data.model.db.User
import es.jasolgar.cityposts_kt.data.model.others.MailEmoji
import es.jasolgar.cityposts_kt.data.model.others.PostInfo
import es.jasolgar.cityposts_kt.data.remote.ApiHelper
import es.jasolgar.cityposts_kt.utils.AppConstants
import es.jasolgar.cityposts_kt.utils.AppLogger
import es.jasolgar.cityposts_kt.utils.CommonUtils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import java.io.IOException
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppDataManager @Inject constructor(context : Context, dbHelper : DbHelper, preferencesHelper : PreferencesHelper, apiHelper : ApiHelper, gson : Gson): DataManager{

    private val mContext: Context = context
    private val  mDbHelper: DbHelper = dbHelper
    private val mPreferencesHelper: PreferencesHelper = preferencesHelper
    private val mApiHelper: ApiHelper = apiHelper
    private val mGson: Gson = gson


    override fun fetchPostDataByUsers(): Observable<Boolean> {
        return requestUsers()
            .flatMap { users -> Observable.fromIterable(users)
                .flatMap { user ->  mDbHelper.insertUser(user)
                    .flatMap { requestPosts(user.id) } } }
            .flatMap { posts -> mDbHelper.insertPosts(posts) }
            .toList().toObservable()
            .flatMap { results ->
                for (result in results)
                    if(result)   Observable.just(true)
                  Observable.just(false)
            }
            .onErrorReturn { throwable: Throwable ->
                AppLogger.w(throwable.message, "fetchPostDataByUsers: %s")
                false
            }
    }

    private fun requestPosts(userId: Long): Observable<List<Post>> {
        return mApiHelper
            .doPostRequest(userId)
            .map { postList ->
                for (post in postList) post.imageUrl = (mApiHelper.provideImageRandomUrl())
                postList
            }.toObservable()
    }

    private fun requestUsers(): Observable<List<User>> {
        return mApiHelper
            .doUsersRequest()
            .map { users ->
                for (user in users) user.avatarUrl =
                    mApiHelper.provideAvatarRandomUrl()
                users
            }.toObservable()
    }

    override fun requestCommentsByPostId(postId: Long): Observable<MutableList<Comment>> {
        return mApiHelper
            .doCommentRequest(postId)
            .toObservable()
            .flatMap { comments ->
                for (comment in comments) comment.email = (buildEmailWithEmoji(comment.email))
                mDbHelper.insertComments(comments).map {  comments.toMutableList() }
            }
            .onErrorReturn { throwable ->
                AppLogger.w(throwable.message, "getCommentsByPostId: %s")
                mDbHelper.getCommentsByPostId(postId).blockingFirst().toMutableList()
            }
    }

    private fun buildEmailWithEmoji(email: String): String {
        try {
            val type: Type = object : TypeToken<List<MailEmoji>>() {}.type
            val emojiList: List<MailEmoji> = mGson.fromJson(
                CommonUtils.loadJSONFromAsset(
                    mContext,
                    AppConstants.JSON_MAIL_EMOJI_OPTIONS
                ), type
            )
            for (mailEmoji in emojiList) if (email.toLowerCase()
                    .endsWith(mailEmoji.endsWith.toLowerCase())
            ) return email + " " + mailEmoji.emoji
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return email
    }

    override fun retrieveAllPostsInfo(): Observable<List<PostInfo>> {
        return mDbHelper.getPostData()
            .flatMap {
                    posts ->  Observable.fromIterable(posts)
                             .flatMap { post ->mDbHelper.getUserById(post.userId)
                                 .flatMap { user -> Observable.just(PostInfo(post,user)) } }
                }
            .toList()
            .onErrorReturn {  throwable: Throwable ->
                AppLogger.w(throwable.message, "getAllPostsInfo: %s")
                mutableListOf<PostInfo>()
            }
            .toObservable()

    }

    override fun isEmptyCards(): Observable<Boolean> {
        return mDbHelper.getPostData()
            .flatMap { postList ->
                Observable.just(
                    postList.isEmpty()
                )
            }
            .onErrorReturn { throwable ->
                AppLogger.w(throwable.message, "isEmptyCards: %s")
                true
            }
    }

    override fun retrievePostsById(postId: Long): Observable<Post> {
        return mDbHelper.getPostsById(postId)
    }

    override fun retrieveUserById(userId: Long): Observable<User> {
        return mDbHelper.getUserById(userId)
    }

    override fun clearPostData(): Observable<Boolean> {
        return mDbHelper.removePostData()
    }

}