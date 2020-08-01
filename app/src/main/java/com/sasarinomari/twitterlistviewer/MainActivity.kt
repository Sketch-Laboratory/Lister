package com.sasarinomari.twitterlistviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sasarinomari.twitterlistviewer.Authenticate.TokenManagementActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TwitterAdapter.TwitterInterface.setOAuthConsumer(this)
        startActivity(Intent(this, TokenManagementActivity::class.java))


    }
}