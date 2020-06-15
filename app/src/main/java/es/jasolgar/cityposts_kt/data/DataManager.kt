package es.jasolgar.cityposts_kt.data

import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.data.model.db.Post
import es.jasolgar.cityposts_kt.data.model.db.User
import es.jasolgar.cityposts_kt.data.model.others.PostInfo
import io.reactivex.Observable


interface DataManager {

    fun fetchPostDataByUsers(): Observable<Boolean>

    fun retrieveAllPostsInfo(): Observable<List<PostInfo>>

    fun isEmptyCards(): Observable<Boolean>

    fun retrievePostsById(postId: Long): Observable<Post>

    fun retrieveUserById(userId: Long): Observable<User>

    fun requestCommentsByPostId(postId: Long): Observable<MutableList<Comment>>

    fun clearPostData(): Observable<Boolean>
    
}