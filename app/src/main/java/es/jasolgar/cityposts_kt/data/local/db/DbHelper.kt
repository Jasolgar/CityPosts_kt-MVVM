package es.jasolgar.cityposts_kt.data.local.db

import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.data.model.db.Post
import es.jasolgar.cityposts_kt.data.model.db.User
import io.reactivex.Observable


interface DbHelper {

    fun insertUsers(users: List<User>): Observable<Boolean>

    fun insertUser(users: User): Observable<Boolean>

    fun insertComments(comments: List<Comment>): Observable<Boolean>

    fun insertPosts(posts: List<Post>): Observable<Boolean>

    fun getPostData(): Observable<List<Post>>

    fun getPostsById(postId: Long): Observable<Post>

    fun getUserById(userId: Long): Observable<User>

    fun getCommentsByPostId(postId: Long): Observable<List<Comment>>

    fun removePostData(): Observable<Boolean>
    
}