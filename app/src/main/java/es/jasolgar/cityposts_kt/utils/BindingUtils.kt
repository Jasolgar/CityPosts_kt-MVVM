package es.jasolgar.cityposts_kt.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.internal.managers.ViewComponentManager
import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.data.model.others.PostInfo
import es.jasolgar.cityposts_kt.ui.base.BaseActivity
import es.jasolgar.cityposts_kt.ui.details.CommentsAdapter
import es.jasolgar.cityposts_kt.ui.main.MainActivity
import es.jasolgar.cityposts_kt.ui.posts.PostsAdapter
import javax.inject.Inject


class BindingUtils {



    companion object {

        @BindingAdapter("postsAdapter")
        @JvmStatic
        fun addPostItems(recyclerView: RecyclerView?, postItemViewModels: MutableList<PostInfo>?) {
            val adapter = recyclerView?.adapter as PostsAdapter?
            adapter?.clearItems()
            adapter?.addItems(postItemViewModels!!)
        }

        @BindingAdapter("avatar")
        @JvmStatic
        fun loadAvatar(imageView: ImageView, avatarUrl: String?) {
            Glide.with(imageView.context)
                .setDefaultRequestOptions(RequestOptions().circleCrop())
                .asDrawable()
                .load(avatarUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(object : CustomViewTarget<ImageView?, Drawable?>(imageView) {
                    override fun onLoadFailed(@Nullable errorDrawable: Drawable?) {}

                    override fun onResourceCleared(@Nullable placeholder: Drawable?) {
                        imageView.setImageDrawable(null)
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                        imageView.setImageDrawable(resource)
                    }
                })
        }

        @BindingAdapter("image")
        @JvmStatic
        fun loadImage(intoView: ImageView, imageUrl: String? ) {
            Glide.with(intoView.context)
                .asDrawable()
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .fitCenter()
                .into(object : CustomViewTarget<ImageView?, Drawable?>(intoView) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        startPostponedEnterTransition(intoView.context)
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                        intoView.setImageDrawable(null)
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>? ) {
                        intoView.setImageDrawable(resource)
                        startPostponedEnterTransition(intoView.context)
                    }
                })
        }

        fun startPostponedEnterTransition(context: Context) {
            if (context is AppCompatActivity)
                context.supportStartPostponedEnterTransition()
            else if (context is ViewComponentManager.FragmentContextWrapper)
                (context.fragment.activity as AppCompatActivity).supportStartPostponedEnterTransition()
        }
    }

}