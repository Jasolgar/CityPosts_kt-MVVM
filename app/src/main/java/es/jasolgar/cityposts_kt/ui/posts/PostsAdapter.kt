package es.jasolgar.cityposts_kt.ui.posts

import android.app.ActivityOptions
import android.os.Build
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.jasolgar.cityposts_kt.data.model.others.PostInfo
import es.jasolgar.cityposts_kt.databinding.ItemPostBinding
import es.jasolgar.cityposts_kt.databinding.ItemPostEmptyViewBinding
import es.jasolgar.cityposts_kt.ui.base.BaseViewHolder
import es.jasolgar.cityposts_kt.ui.details.DetailsActivity
import es.jasolgar.cityposts_kt.ui.main.MainActivity
import es.jasolgar.cityposts_kt.ui.posts.PostsItemEmptyViewModel.PostItemEmptyViewModelListener
import es.jasolgar.cityposts_kt.utils.AppConstants

import es.jasolgar.cityposts_kt.utils.CommonUtils
import java.io.IOException
import javax.inject.Inject


class PostsAdapter @Inject constructor(context: MainActivity) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object{
        const val VIEW_TYPE_EMPTY : Int = 0
        const val VIEW_TYPE_NORMAL : Int = 1
    }

    var postInfoList: MutableList<PostInfo> = mutableListOf<PostInfo>()

    lateinit var mListener: PostItemEmptyViewModelListener

    val mContext: MainActivity = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val itemPostBinding: ItemPostBinding =
                    ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PostItemViewHolder(itemPostBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding: ItemPostEmptyViewBinding = ItemPostEmptyViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding: ItemPostEmptyViewBinding = ItemPostEmptyViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    fun setListener(listener: PostItemEmptyViewModelListener) {
        this.mListener = listener
    }

    override fun getItemCount(): Int {
        return if (postInfoList.isNotEmpty()) {
            postInfoList.size
        } else {
            1
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (postInfoList.isNotEmpty()) {
            VIEW_TYPE_NORMAL;
        } else {
            VIEW_TYPE_EMPTY;
        }
    }

    fun clearItems() {
        postInfoList.clear()
    }

    fun addItems(repoList: MutableList<PostInfo>) {
        postInfoList.addAll(repoList)
        notifyDataSetChanged()
    }

    inner class PostItemViewHolder(binding: ItemPostBinding) : BaseViewHolder(binding.root) ,  PostsItemViewModel.PostItemViewModelListener{

        private val mBinding: ItemPostBinding = binding

        override fun onBind(position: Int) {
            val postInfo: PostInfo = postInfoList[position]
            val postItemViewHolder = PostsItemViewModel(postInfo, this)
            mBinding.viewModel = postItemViewHolder

            mBinding.executePendingBindings()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBinding.cardPostImage.transitionName = "cardPostImage" + " " + postInfo.id
                mBinding.cardAvatarImage.transitionName = "cardAvatarImage" + " " + postInfo.id
            }
        }

        override fun onItemClick(postId: Long) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(mContext,
                    Pair.create<View,String>(mBinding.cardPostImage, mBinding.cardPostImage.transitionName ),
                    Pair.create<View,String>(mBinding.cardAvatarImage, mBinding.cardAvatarImage.transitionName ))

                mContext.startActivity(
                    DetailsActivity.newIntent(mContext, postId),
                    options.toBundle())
            } else
                mContext.startActivity(DetailsActivity.newIntent(mContext, postId))
        }

    }

    inner class EmptyViewHolder(binding: ItemPostEmptyViewBinding) : BaseViewHolder(binding.root) , PostItemEmptyViewModelListener{

        private val mBinding: ItemPostEmptyViewBinding? = binding

        override fun onBind(position: Int) {
            val emptyItemViewModel = PostsItemEmptyViewModel(this)
            mBinding?.viewModel = emptyItemViewModel

            try {
                mBinding?.imvEmpty?.setImageBitmap(
                    CommonUtils.loadFileFromAsset(
                        mContext,
                        AppConstants.ASSETS_EMPTY_IMAGE
                    )
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun onRetryClick() {
            mListener.onRetryClick()
        }

    }
}