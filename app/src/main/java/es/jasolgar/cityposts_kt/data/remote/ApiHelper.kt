package es.jasolgar.cityposts_kt.data.remote

import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.data.model.db.Post
import es.jasolgar.cityposts_kt.data.model.db.User
import io.reactivex.Single


interface ApiHelper {

    fun doPostRequest(): Single<List<Post>>

    fun doPostRequest(userId: Long): Single<List<Post>>

    fun doUsersRequest(): Single<List<User>>

    fun doCommentRequest(postId: Long): Single<List<Comment>>

    fun provideImageRandomUrl(): String

    fun provideAvatarRandomUrl(): String
    
}