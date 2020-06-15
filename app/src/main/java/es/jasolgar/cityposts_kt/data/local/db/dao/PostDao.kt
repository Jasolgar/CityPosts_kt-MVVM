package es.jasolgar.cityposts_kt.data.local.db.dao

import androidx.room.*
import es.jasolgar.cityposts_kt.data.model.db.Post
import io.reactivex.Single


@Dao
interface PostDao {

    @Query("DELETE FROM posts")
    fun deleteAll()

    @Delete
    fun delete(comment: Post?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(comment: Post)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(comments: List<Post>)

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPostById(id: Long): Single<Post>

    @Query("SELECT * FROM posts")
    fun loadAll(): Single<List<Post>>

}