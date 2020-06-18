package es.jasolgar.cityposts_kt.ui.posts

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.jasolgar.cityposts_kt.BR
import es.jasolgar.cityposts_kt.R
import es.jasolgar.cityposts_kt.ViewModelProviderFactory
import es.jasolgar.cityposts_kt.databinding.FragmentPostsBinding
import es.jasolgar.cityposts_kt.ui.base.BaseFragment
import es.jasolgar.cityposts_kt.ui.details.DetailsActivity
import javax.inject.Inject

@AndroidEntryPoint
class PostsFragment : BaseFragment<FragmentPostsBinding, PostsViewModel >(),  PostsNavigator, PostsItemEmptyViewModel.PostItemEmptyViewModelListener,
    PostsAdapter.PostItemEvents  {

    companion object{
        val TAG : String? = PostsFragment::class.simpleName

        fun newInstance(): PostsFragment {
            val args = Bundle()
            val fragment = PostsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val mPostViewModel: PostsViewModel by lazy {
        ViewModelProvider(this@PostsFragment, factory).get(PostsViewModel::class.java)
    }

    private lateinit var mFragmentPostSourceBinding: FragmentPostsBinding

    @Inject
    lateinit var factory: ViewModelProviderFactory

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    private val mPostAdapter: PostsAdapter by lazy { PostsAdapter(context as Context,this@PostsFragment) }

    override fun getBindingVariable(): Int { return BR.viewModel }

    override fun getLayoutId(): Int { return R.layout.fragment_posts  }

    override fun getViewModel(): PostsViewModel {
        return mPostViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getBaseActivity()?.supportPostponeEnterTransition();

        mPostViewModel.setNavigator(this);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFragmentPostSourceBinding = getViewDataBinding();

        setUp();
    }

    override fun onBackPressed(): Boolean { return false }

    private fun setUp() {
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mFragmentPostSourceBinding.recyclerPosts.layoutManager = mLayoutManager
        mFragmentPostSourceBinding.recyclerPosts.itemAnimator = DefaultItemAnimator()
        mFragmentPostSourceBinding.recyclerPosts.adapter = mPostAdapter
        mPostAdapter.setListener(this)
        mPostViewModel.seedData()
    }

    override fun onRetryClick() {
        mPostViewModel.requestRepoAndFetchData()
    }

    override fun showRemoveDataDialog() {
       getBaseActivity().let {
           val builder = AlertDialog.Builder(it!!)
           builder.setMessage(R.string.remove_data_message)
           builder.apply {
               setPositiveButton(android.R.string.ok) { _, _ -> mPostViewModel.removeData() }
               setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
               setOnCancelListener { dialog -> dialog.dismiss() }
           }
           builder.create().show()
       }
    }




    override fun onItemClick(postId: Long, arrays: Array<View>?) {
        when(arrays){
            null -> startActivity(DetailsActivity.newIntent(activity as Activity, postId))
            arrays -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val par1 = Pair.create(arrays[0], arrays[0].transitionName )
                    val par2 = Pair.create(arrays[1], arrays[1].transitionName )

                    val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, par1  , par2  )
                    startActivity(
                        DetailsActivity.newIntent(activity as Activity, postId),
                        options.toBundle())
                } else {
                    startActivity(DetailsActivity.newIntent(activity as Activity, postId))
                }
            }
        }

    }
}