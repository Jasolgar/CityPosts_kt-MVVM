package es.jasolgar.cityposts_kt.ui.posts

import androidx.databinding.ObservableField
import es.jasolgar.cityposts_kt.data.model.others.PostInfo


class PostsItemViewModel(postInfo: PostInfo, listener: PostItemViewModelListener) {

    var imageUrl = ObservableField<String>()

    var avatarUrl = ObservableField<String>()

    var title = ObservableField<String>()

    var author = ObservableField<String>()

    var body = ObservableField<String>()

    var postId: Long = postInfo.id!!

    private val mListener: PostItemViewModelListener = listener

    init {
        this.imageUrl.set(postInfo.imageUrl)
        this.avatarUrl.set(postInfo.avatar)
        this.title.set(postInfo.title)
        this.author.set(postInfo.authorName)
        this.body.set(postInfo.body)
    }

    fun onItemClick() {
        mListener?.onItemClick(postId)
    }

    interface PostItemViewModelListener {
        fun onItemClick(postId: Long)
    }
}