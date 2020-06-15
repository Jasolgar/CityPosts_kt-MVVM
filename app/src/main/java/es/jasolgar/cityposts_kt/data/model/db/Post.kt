package es.jasolgar.cityposts_kt.data.model.db

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts",
        primaryKeys =["id"],
        indices = [Index(value = ["id", "user_id"], unique = true)],
        foreignKeys = [ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )])
data class Post (
    @Expose @SerializedName("id")  @ColumnInfo(name = "id") var id: Long,
    @Expose @SerializedName("userId") @ColumnInfo(name = "user_id") var userId : Long,
    @Expose @SerializedName("title") @ColumnInfo(name = "title")var title : String,
    @Expose @SerializedName("body") @ColumnInfo(name = "body")var body : String,
    @Expose @ColumnInfo(name = "image_url")var imageUrl : String
)