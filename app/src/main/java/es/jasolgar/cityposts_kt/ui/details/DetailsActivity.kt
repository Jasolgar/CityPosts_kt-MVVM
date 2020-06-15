package es.jasolgar.cityposts_kt.ui.details

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import es.jasolgar.cityposts_kt.BR
import es.jasolgar.cityposts_kt.R
import es.jasolgar.cityposts_kt.ViewModelProviderFactory
import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.databinding.ActivityDetailsBinding
import es.jasolgar.cityposts_kt.ui.base.BaseActivity
import es.jasolgar.cityposts_kt.utils.AppLogger
import javax.inject.Inject


class DetailsActivity : BaseActivity<ActivityDetailsBinding,DetailsViewModel>() , DetailsNavigator {

    companion object{
        private const val BUNDLE_POST_ID = "bundle_post_id"
        private const val REQUEST_PERMISSION_PHONE_CALL = 0

        fun newIntent(context: Context, postId: Long): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(BUNDLE_POST_ID, postId)
            return intent
        }
    }

    @Inject
    lateinit var mCommentsAdapter: CommentsAdapter

    @Inject
    lateinit var factory: ViewModelProviderFactory

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    private lateinit var mActivityDetailsBinding: ActivityDetailsBinding

    private lateinit var mDetailsViewModel: DetailsViewModel
    private lateinit var mToolbar: Toolbar
    private lateinit var pendingPhone: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportPostponeEnterTransition()

        mActivityDetailsBinding = getViewDataBinding()
        mDetailsViewModel.setNavigator(this)

        mToolbar = mActivityDetailsBinding.toolbar

        setUp()
    }

    private fun setUp() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mActivityDetailsBinding.cardPostImage.transitionName = "cardPostImage" + " " + getBundlePostId().toString()
            mActivityDetailsBinding.cardAvatarImage.transitionName = "cardAvatarImage" + " " + getBundlePostId().toString()
        }

        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mActivityDetailsBinding.recyclewComments.layoutManager = mLayoutManager
        mActivityDetailsBinding.recyclewComments.itemAnimator = DefaultItemAnimator()
        mActivityDetailsBinding.recyclewComments.adapter = mCommentsAdapter

        mDetailsViewModel.getCommentListMutableLiveData().observe(this, Observer { comments ->
            mCommentsAdapter.addItems(comments as MutableList<Comment>)
        })

        mDetailsViewModel.notifyBundlePostId(getBundlePostId()!!)
    }

    private fun getBundlePostId(): Long? {
        return if (intent.extras != null && intent.hasExtra(BUNDLE_POST_ID))
            intent.extras?.getLong(BUNDLE_POST_ID) else 0
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel;
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_details
    }

    override fun getViewModel(): DetailsViewModel {
        mDetailsViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        return mDetailsViewModel
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_PHONE_CALL -> if (grantResults.isNotEmpty() && grantResults[0] ==  PackageManager.PERMISSION_GRANTED) {
                launchPhoneCall(pendingPhone)
            } else {
                AppLogger.d("TAG", "Call Permission Not Granted")
            }
            else -> {
            }
        }
    }

    override fun launchMail(mail: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, mail)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.send_mail_extra_text_sample))
        startActivity(Intent.createChooser(intent, "Send Email"))
    }

    override fun launchGeoMaps(lat: String?, lng: String?) {
        loadWebUrl(
            String.format(
                "https://www.google.com/maps/search/?api=1&query=%s,%s",
                lat,
                lng
            )
        )
    }

    override fun launchPhoneCall(phone: String?) {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            pendingPhone = phone!!
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_PERMISSION_PHONE_CALL
            )
        } else {
            pendingPhone = ""
            startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:$phone")))
        }
    }

    override fun loadWebUrl(webUrl: String?) {
        val i = Intent(Intent.ACTION_VIEW)
        val web: Uri = Uri.parse(webUrl)
        i.data = Uri.parse(
            if (web.getHost() == null) String.format(
                "http://www.google.com/search?q=%s",
                webUrl
            ) else webUrl
        )
        startActivity(i)
    }
}