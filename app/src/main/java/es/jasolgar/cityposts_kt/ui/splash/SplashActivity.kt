package es.jasolgar.cityposts_kt.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import es.jasolgar.cityposts_kt.BR
import es.jasolgar.cityposts_kt.R
import es.jasolgar.cityposts_kt.ViewModelProviderFactory
import es.jasolgar.cityposts_kt.databinding.ActivitySplashBinding
import es.jasolgar.cityposts_kt.ui.base.BaseActivity
import es.jasolgar.cityposts_kt.ui.main.MainActivity
import javax.inject.Inject


class SplashActivity : BaseActivity<ActivitySplashBinding,SplashViewModel>(), SplashNavigator {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private val mSplashViewModel: SplashViewModel by lazy {
        ViewModelProvider(this@SplashActivity, factory).get(SplashViewModel::class.java)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getViewModel(): SplashViewModel { 
        return mSplashViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSplashViewModel.setNavigator(this)
        mSplashViewModel.onFetchDataStarted()
    }

    override fun openMainActivity() {
        val intent: Intent = MainActivity.newIntent(this@SplashActivity)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

}