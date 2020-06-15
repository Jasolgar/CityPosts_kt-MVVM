package es.jasolgar.cityposts_kt.ui.posts

class PostsItemEmptyViewModel(listener: PostItemEmptyViewModelListener) {

    private var mListener: PostItemEmptyViewModelListener = listener

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface PostItemEmptyViewModelListener {
        fun onRetryClick()
    }

}