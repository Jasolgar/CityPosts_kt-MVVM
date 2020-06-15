package es.jasolgar.cityposts_kt.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import es.jasolgar.cityposts_kt.BR
import es.jasolgar.cityposts_kt.R
import es.jasolgar.cityposts_kt.ViewModelProviderFactory
import es.jasolgar.cityposts_kt.databinding.ActivityMainBinding
import es.jasolgar.cityposts_kt.ui.base.BaseActivity
import es.jasolgar.cityposts_kt.ui.posts.PostsFragment
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(), MainNavigator, HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var mMainViewModel: MainViewModel
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
        mMainViewModel =  ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        return mMainViewModel
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment?>? {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivityMainBinding = getViewDataBinding()
        mMainViewModel.setNavigator(this)

        mToolbar = mActivityMainBinding.toolbar

        setSupportActionBar(mToolbar)

        setUpViewBinding();
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