package com.sasarinomari.twitterlistviewer

import android.os.Bundle
import android.util.Log
import com.sasarinomari.twitter.FetchObjectInterface
import com.sasarinomari.twitter.TwitterAPIActivity
import com.sasarinomari.twitter.simplizatedClass.User

class DashboardActivity : TwitterAPIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val listId = 1275284652475858944
        Thread {
            twitterAdapter.getMe(object : FetchObjectInterface {
                override fun onFinished(obj: Any) {
                    val me = obj as User
                    Log.i("Dashboard", "Okay ${me.screenName}.")
                }

                override fun onRateLimit() {
                    this@DashboardActivity.onRateLimit("getMe")
                }

                override fun onStart() {}

                override fun onUncaughtError() {
                    this@DashboardActivity.onUncaughtError()
                }

                override fun onNetworkError(retrySelf: () -> Unit) {
                    this@DashboardActivity.onNetworkError(retrySelf)
                }

            })
        }.start()

    }
}