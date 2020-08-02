package com.sasarinomari.twitterlistviewer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.sasarinomari.dialog.MaterialDialog
import com.sasarinomari.dialog.MaterialDialogAdapter
import com.sasarinomari.dialog.SweetDialogAdapter
import com.sasarinomari.twitter.TwitterAdapter
import com.sasarinomari.twitter.authenticate.TokenManagementActivity

class MainActivity : AppCompatActivity() {
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
                    TwitterAdapter.TwitterInterface.setOAuthConsumer(
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