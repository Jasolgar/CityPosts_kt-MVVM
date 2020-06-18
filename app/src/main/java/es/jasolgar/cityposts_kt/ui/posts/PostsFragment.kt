package es.jasolgar.cityposts_kt.ui.posts

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import es.jasolgar.cityposts_kt.BR
import es.jasolgar.cityposts_kt.R
import es.jasolgar.cityposts_kt.ViewModelProviderFactory
import es.jasolgar.cityposts_kt.databinding.FragmentPostsBinding
import es.jasolgar.cityposts_kt.ui.base.BaseFragment
import javax.inject.Inject


class PostsFragment : BaseFragment<FragmentPostsBinding, PostsViewModel >(),  PostsNavigator, PostsItemEmptyViewModel.PostItemEmptyViewModelListener  {

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
    lateinit var  factory: ViewModelProviderFactory

    @Inject
    lateinit var  mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mPostAdapter: PostsAdapter

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
}