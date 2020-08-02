package com.sasarinomari.twitter

import android.os.Bundle
import com.sasarinomari.base.BaseActivity
import com.sasarinomari.twitter.authenticate.AuthData

abstract class TwitterAPIActivity : BaseActivity() {
    protected val twitterAdapter = TwitterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = AuthData.Recorder(this).getFocusedUser()
        if(user!=null) twitterAdapter.initialize(user.token!!)
    }

    fun onRateLimit(apiName: String, callback: () -> Unit = { }) {
        runOnUiThread {
            da.error(getString(R.string.Error), getString(R.string.RateLimitError, apiName)) {
                callback()
            }.show()
        }
    }

    fun onUncaughtError() {
        runOnUiThread {
            da.error(getString(R.string.Error), getString(R.string.UncaughtError)) {
                finish()
            }.show()
        }
    }

    fun onNetworkError(retry: () -> Unit) {
        runOnUiThread {
            da.error(getString(R.string.Error), getString(R.string.NetworkError))
                .setPositiveButton { retry() }
                .setNegativeButton { finish() }
                .show()
        }
    }
}