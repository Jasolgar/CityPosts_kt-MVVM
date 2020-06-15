package es.jasolgar.cityposts_kt.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import es.jasolgar.cityposts_kt.data.model.others.Address
import es.jasolgar.cityposts_kt.data.model.others.Company

@Entity(tableName = "users", primaryKeys =["id"] )
data class User(
    @SerializedName("id") @ColumnInfo(name = "id") @Expose  var id: Long,
    @SerializedName("name") @ColumnInfo(name = "name") @Expose var name: String,
    @SerializedName("username") @ColumnInfo(name = "user_name") @Expose var username: String,
    @SerializedName("email") @ColumnInfo(name = "email") @Expose var email: String,
    @SerializedName("phone") @ColumnInfo(name = "phone") @Expose var phone: String,
    @SerializedName("website") @Expose var website: String,
    @SerializedName("company") @Expose @Embedded var company: Company,
    @SerializedName("address") @Expose @Embedded var address: Address,
    @Expose @ColumnInfo(name = "avatar_url") var avatarUrl: String)