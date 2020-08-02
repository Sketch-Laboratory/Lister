package com.sasarinomari.twitterlistviewer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.sasarinomari.base.BaseActivity
import com.sasarinomari.dialog.MaterialDialog
import com.sasarinomari.dialog.MaterialDialogAdapter
import com.sasarinomari.dialog.SweetDialogAdapter
import com.sasarinomari.twitter.Twitter
import com.sasarinomari.twitter.TwitterAdapter
import com.sasarinomari.twitter.authenticate.TokenManagementActivity

class MainActivity : BaseActivity() {
    enum class Requests {
        Token, Dashboard
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionbarVisibility(false)
        initialize()
        startActivityForResult(Intent(this, TokenManagementActivity::class.java), Requests.Token.ordinal)
    }

    private fun initialize() {
        Twitter.setOAuthConsumer(
            getString(R.string.consumerKey), getString(R.string.consumerSecret))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode){
            Requests.Token.ordinal ->{
                if(resultCode == Activity.RESULT_OK) {
                    startActivityForResult(Intent(this, DashboardActivity::class.java), Requests.Dashboard.ordinal)
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }
}