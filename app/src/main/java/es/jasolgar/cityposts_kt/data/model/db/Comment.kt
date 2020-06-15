package es.jasolgar.cityposts_kt.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "comments",
      primaryKeys =["id"],
      foreignKeys = [ForeignKey(
        entity = Post::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("post_id"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class Comment(
    @SerializedName("id") @Expose @ColumnInfo(name = "id")  var id : Long,
    @SerializedName("postId") @Expose @ColumnInfo(name = "post_id") var postId : Long,
    @SerializedName("name") @Expose @ColumnInfo(name = "name") var name : String,
    @SerializedName("email") @Expose @ColumnInfo(name = "email") var email : String,
    @SerializedName("body") @Expose @ColumnInfo(name = "body") var body : String
)