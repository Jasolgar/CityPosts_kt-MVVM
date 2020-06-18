package es.jasolgar.cityposts_kt.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import es.jasolgar.cityposts_kt.BR
import es.jasolgar.cityposts_kt.R
import es.jasolgar.cityposts_kt.ViewModelProviderFactory
import es.jasolgar.cityposts_kt.databinding.ActivityMainBinding
import es.jasolgar.cityposts_kt.ui.base.BaseActivity
import es.jasolgar.cityposts_kt.ui.posts.PostsFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : BaseActivity<ActivityMainBinding,MainViewModel>(), MainNavigator {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private val mMainViewModel: MainViewModel by lazy {
        ViewModelProvider(this@MainActivity, factory).get(MainViewModel::class.java)
    }

    private lateinit var mActivityMainBinding: ActivityMainBinding
    private lateinit var mToolbar: Toolbar

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewModel {
        return mMainViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivityMainBinding = getViewDataBinding()
        mMainViewModel.setNavigator(this)

        mToolbar = mActivityMainBinding.toolbar

        setSupportActionBar(mToolbar)

        setUpViewBinding()
    }

    private fun setUpViewBinding() {}

    override fun loadPostFragment() {
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .add(R.id.frame_main_container, PostsFragment.newInstance(), PostsFragment.TAG)
            .commit()
    }

    override fun handleError(throwable: Throwable) {

    }

    override fun onBackPressed() {
        val fts: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount >= 2) {
            fragmentManager.popBackStackImmediate()
            fts.commit()
        } else {
            super.onBackPressed()
        }
    }


}