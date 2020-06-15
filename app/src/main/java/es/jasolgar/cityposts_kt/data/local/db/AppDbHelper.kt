package es.jasolgar.cityposts_kt.data.local.db

import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.data.model.db.Post
import es.jasolgar.cityposts_kt.data.model.db.User
import io.reactivex.Observable
import javax.inject.Inject


class AppDbHelper @Inject constructor(appDatabase: AppDatabase) : DbHelper {

    private val mAppDatabase: AppDatabase = appDatabase

    override fun insertUsers(users: List<User>): Observable<Boolean> {
       return Observable.fromCallable {
            mAppDatabase.userDao().insertAll(users)
            true
        }
    }

    override fun insertUser(user: User): Observable<Boolean> {
        return Observable.fromCallable {
                mAppDatabase.userDao().insert(user)
                true
        }
    }

    override fun insertComments(comments: List<Comment>): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.commentDao().insertAll(comments)
            true
        }
    }

    override fun insertPosts(posts: List<Post>): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.postDao().insertAll(posts)
            true
        }
    }

    override fun getPostData(): Observable<List<Post>> {
        return mAppDatabase.postDao()
            .loadAll()
            .toObservable()
    }

    override fun getPostsById(postId: Long): Observable<Post> {
        return mAppDatabase.postDao()
            .getPostById(postId)
            .toObservable()
    }

    override fun getUserById(userId: Long): Observable<User> {
        return mAppDatabase.userDao()
            .getUserById(userId)
            .toObservable()
    }

    override fun getCommentsByPostId(postId: Long): Observable<List<Comment>> {
        return mAppDatabase.commentDao()
            .getCommentsByPostId(postId)
            .toObservable()
    }

    override fun removePostData(): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.userDao().deleteAll()
            true
        }
    }


}