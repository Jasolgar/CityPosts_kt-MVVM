package es.jasolgar.cityposts_kt.data.model.others

import es.jasolgar.cityposts_kt.data.model.db.Post
import es.jasolgar.cityposts_kt.data.model.db.User

data class PostInfo (
    var id : Long?,
    var userId : Long,
    var title : String,
    var imageUrl : String,
    var body : String,
    var authorName : String,
    var avatar : String
){
    
    constructor(post : Post, user : User) : this(post.id,post.userId,post.title,post.imageUrl,post.body,user.name,user.avatarUrl)

}