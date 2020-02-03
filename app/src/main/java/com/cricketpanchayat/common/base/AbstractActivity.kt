package com.cricketpanchayat.common.base

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cricketpanchayat.R
import com.cricketpanchayat.common.widgets.CustomProgressDialog
import com.cricketpanchayat.network.ErrorInfo
import com.cricketpanchayat.utils.AppConstants

abstract class AbstractActivity<V : BaseViewModel> : AppCompatActivity() {

    private var mProgressDialog: CustomProgressDialog? = null
    private var mErrorDialog: Dialog? = null
    private var mIsErrorDialogShown: Boolean = false

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        viewModel.errorResponse().observe(this, Observer {
            showError(it)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onOptionsItemSelected(item)
                this.finish()
            }
            else -> {
            }
        }
        return true
    }

//    override fun onBackPressed() {
//        val fragment = supportFragmentManager.findFragmentById(R.id.content_main)
//        if(fragment != null) {
//            if(fragment is IBackPressListener) {
//                (fragment as IBackPressListener).onBackClickHandler()
//                return
//            } else if(fragment is NavHostFragment) {
//                if(fragment.childFragmentManager.fragments[0] is IBackPressListener) {
//                    (fragment.childFragmentManager.fragments[0] as IBackPressListener).onBackClickHandler()
//                    return
//                }
//            }
//        }
//
//        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
//        super.onBackPressed()
//    }

    @JvmOverloads
    fun showProgressDialog(msg: String? = null) {
        if (isDestroyed)
            return
        hideProgressDialog()

        mProgressDialog = CustomProgressDialog(this)
        mProgressDialog!!.setCancelable(false)
        mProgressDialog!!.setMessage(msg)

        mProgressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (isDestroyed)
            return
        if (!isFinishing && mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
            mProgressDialog = null
        }
    }

    fun showError(errorInfo: ErrorInfo?) {
        if(isDestroyed || mIsErrorDialogShown)
            return

        mIsErrorDialogShown = true

        val dialogBuilder = AlertDialog.Builder(this, R.style.AlertDialogTheme)

        dialogBuilder.setMessage(getErrorDescription(errorInfo!!))
        dialogBuilder.setPositiveButton(getString(R.string.lblOk),  { dialogInterface, i ->
            mErrorDialog?.dismiss()
            mErrorDialog = null
            mIsErrorDialogShown = false
        })
        mErrorDialog = dialogBuilder.create()
        mErrorDialog?.setCancelable(false)
        mErrorDialog?.show()

        viewModel.errorResponse().value = null
    }

    private fun getErrorDescription(errorInfo: ErrorInfo): String {
        var desc = errorInfo.desc
        if(TextUtils.isEmpty(desc)) {
            when(errorInfo.code) {
                AppConstants.INTERNET_ERROR -> {
                    desc = getString(R.string.internet_error)
                }
                AppConstants.API_TIMEOUT_ERROR,
                AppConstants.SYS_DEFAULT_ERROR -> {
                    desc = getString(R.string.api_timeout_error)
                }
            }
        }

        return desc!!
    }

}