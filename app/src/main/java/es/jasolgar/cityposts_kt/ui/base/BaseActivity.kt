package es.jasolgar.cityposts_kt.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity< T : ViewDataBinding, V : BaseViewModel<*>>  : AppCompatActivity(), BaseFragment.Callback{

    private val mProgressDialog: ProgressDialog? = null
    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    override fun onFragmentAttached() {}

    override fun onFragmentDetached(tag: String?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    open fun getViewDataBinding(): T { return mViewDataBinding!! }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = mViewModel ?: getViewModel()
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val result = super.onOptionsItemSelected(item)
        if (item.itemId == android.R.id.home)
            onBackPressed();
        return result;
    }
}
