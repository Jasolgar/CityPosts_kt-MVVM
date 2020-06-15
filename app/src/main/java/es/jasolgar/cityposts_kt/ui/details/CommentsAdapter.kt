package es.jasolgar.cityposts_kt.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.data.model.others.PostInfo
import es.jasolgar.cityposts_kt.databinding.ItemCommentBinding
import es.jasolgar.cityposts_kt.ui.base.BaseViewHolder
import javax.inject.Inject


class CommentsAdapter @Inject constructor() : RecyclerView.Adapter<BaseViewHolder>() {

    private val commentsList: MutableList<Comment> = mutableListOf<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemCommentBinding: ItemCommentBinding =  ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentItemViewHolder(itemCommentBinding)
    }

    override fun onBindViewHolder(@NonNull holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    fun clearItems() {
        commentsList.clear()
    }

    fun addItems(commentList: MutableList<Comment>) {
        commentsList.addAll(commentList)
        notifyDataSetChanged()
    }

    inner class CommentItemViewHolder(binding : ItemCommentBinding) : BaseViewHolder(binding.root){
        private var mBinding: ItemCommentBinding = binding

        override fun onBind(position: Int) {
            val comment = commentsList[position]
            val commentItemViewModel = CommentItemViewModel(comment)
            mBinding.viewModel = commentItemViewModel

            mBinding.executePendingBindings()
        }

    }
}