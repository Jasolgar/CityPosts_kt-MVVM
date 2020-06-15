package es.jasolgar.cityposts_kt.data.local.db.dao

import androidx.room.*
import es.jasolgar.cityposts_kt.data.model.db.User
import io.reactivex.Single


@Dao
interface UserDao {

    @Query("DELETE FROM users")
    fun deleteAll()

    @Delete
    fun delete(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(users: List<User>)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Long): Single<User>
    
}