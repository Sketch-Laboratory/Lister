package com.sasarinomari.twitterlistviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.sasarinomari.dialog.MaterialDialogAdapter
import com.sasarinomari.dialog.SweetDialogAdapter
import com.sasarinomari.twitter.Twitter
import com.sasarinomari.twitter.TwitterAdapter
import com.sasarinomari.twitter.authenticate.TokenManagementActivity

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = ListView(this)
        setContentView(list)

        val values = arrayOf(
            "Open Twitter.TokenManagementActivity",
            "Material Dialog Test",
            "Sweet Alert Dialog Test"
        )

        list.adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, values
        )
        list.setOnItemClickListener { _, _, position, _ ->
            when(position) {
                0 -> {
                    Twitter.setOAuthConsumer(
                        getString(R.string.consumerKey), getString(R.string.consumerSecret))
                    startActivity(Intent(this, TokenManagementActivity::class.java))
                }
                1 -> {
                    MaterialDialogAdapter(this)
                        .message("Hello, World!")
                        .show()
                }
                2 -> {
                    SweetDialogAdapter(this)
                        .message("Hello, World!")
                        .show()
                }
            }
        }
    }
}