package es.jasolgar.cityposts_kt.data.model.others

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MailEmoji (
    @SerializedName("ends_with")
    @Expose val endsWith : String,
    @SerializedName("emoji")
    @Expose val emoji : String
)