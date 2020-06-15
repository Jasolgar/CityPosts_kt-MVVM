package es.jasolgar.cityposts_kt.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.jasolgar.cityposts_kt.data.local.db.converter.GeoValueModelConverter
import es.jasolgar.cityposts_kt.data.local.db.dao.CommentDao
import es.jasolgar.cityposts_kt.data.local.db.dao.PostDao
import es.jasolgar.cityposts_kt.data.local.db.dao.UserDao
import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.data.model.db.Post
import es.jasolgar.cityposts_kt.data.model.db.User


@Database(entities = [Post::class, User::class, Comment::class], version = 1)
@TypeConverters(GeoValueModelConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    abstract fun userDao(): UserDao

    abstract fun commentDao(): CommentDao

}