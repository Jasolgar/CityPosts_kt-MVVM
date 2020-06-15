package es.jasolgar.cityposts_kt.data.local.db.dao

import androidx.room.*
import es.jasolgar.cityposts_kt.data.model.db.Comment
import io.reactivex.Single


@Dao
interface CommentDao {

    @Query("DELETE FROM comments")
    fun deleteAll()

    @Delete
    fun delete(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comments: List<Comment>)

    @Query("SELECT * FROM comments WHERE post_id = :postId")
    fun getCommentsByPostId(postId: Long): Single<List<Comment>>

}