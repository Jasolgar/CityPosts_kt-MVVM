package es.jasolgar.cityposts_kt.ui.details

import androidx.databinding.ObservableField
import es.jasolgar.cityposts_kt.data.model.db.Comment


class CommentItemViewModel( comment : Comment) {

    val title = ObservableField<String>()

    val author = ObservableField<String>()

    val body = ObservableField<String>()

    init {
        title.set(comment.name)
        author.set(comment.email)
        body.set(comment.body)
    }
}