package com.sasarinomari.twitter

import com.sasarinomari.base.BaseActivity

abstract class TwitterAPIActivity : BaseActivity() {
    protected val twitterAdapter = TwitterAdapter()

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