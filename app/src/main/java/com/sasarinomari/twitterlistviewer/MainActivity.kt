package com.sasarinomari.twitterlistviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sasarinomari.twitter.TwitterAdapter
import com.sasarinomari.twitter.authenticate.TokenManagementActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TwitterAdapter.TwitterInterface.setOAuthConsumer(
            getString(R.string.consumerKey), getString(R.string.consumerSecret))
        startActivity(Intent(this, TokenManagementActivity::class.java))
    }
}