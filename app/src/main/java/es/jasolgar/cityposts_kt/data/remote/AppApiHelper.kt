package es.jasolgar.cityposts_kt.data.remote

import com.rx2androidnetworking.Rx2AndroidNetworking
import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.data.model.db.Post
import es.jasolgar.cityposts_kt.data.model.db.User
import es.jasolgar.cityposts_kt.utils.CommonUtils
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppApiHelper @Inject constructor(): ApiHelper {

    override fun doPostRequest(): Single<List<Post>> {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_POST)
            .build()
            .getObjectListSingle(Post::class.java)
    }

    override fun doPostRequest(userId: Long): Single<List<Post>> {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_POST)
            .addQueryParameter("userId", userId.toString())
            .build()
            .getObjectListSingle(Post::class.java)
    }

    override fun doUsersRequest(): Single<List<User>> {
        return Rx2AndroidNetworking
            .get(ApiEndPoint.ENDPOINT_USERS)
            .build()
            .getObjectListSingle(User::class.java)
    }

    override fun doCommentRequest(postId: Long): Single<List<Comment>> {
        return Rx2AndroidNetworking
            .get(ApiEndPoint.ENDPOINT_COMMENTS)
            .addQueryParameter("postId", postId.toString())
            .build()
            .getObjectListSingle(Comment::class.java)
    }

    override fun provideImageRandomUrl(): String {
        return String.format(
            ApiEndPoint.ENDPOINT_RANDOM_IMAGE,
            java.lang.String.valueOf(Random().nextInt(1000))
        )
    }

    override fun provideAvatarRandomUrl(): String {
        val eyes = java.lang.String.valueOf(Random().nextInt(10))
        val noise = java.lang.String.valueOf(Random().nextInt(10))
        val mouth = java.lang.String.valueOf(Random().nextInt(10))
        val color: String = CommonUtils.generateRandomColorHex()
        return String.format(ApiEndPoint.ENDPOINT_RANDOM_AVATAR, eyes, noise, mouth, color)
    }
}