package com.sasarinomari.twitterlistviewer

import android.app.Activity
import android.util.TypedValue
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog

class DialogAdapter(private val activity: Activity) {
    fun message(title: String?, content: String, callback: (()->Unit)? = null): MaterialDialog {
        val d = MaterialDialog(activity)
        initialize(d, title, content, callback)
        return d
    }

    fun error(title: String?, content: String, callback: (()->Unit)? = null): MaterialDialog {
        val d = MaterialDialog(activity)
        initialize(d, title, content, callback)
        return d
    }

    fun warning(title: String?, content: String, callback: (()->Unit)? = null): MaterialDialog {
        val d = MaterialDialog(activity)
        initialize(d, title, content, callback)
        return d
    }

    fun progress(title: String?, content: String, callback: (()->Unit)? = null): MaterialDialog {
        val d = MaterialDialog(activity)
        initialize(d, title, content, callback)
        d.setCancelable(false)
        return d
    }

    fun success(title: String?, content: String, callback: (()->Unit)? = null): MaterialDialog {
        val d = MaterialDialog(activity)
        initialize(d, title, content, callback)
        return d
    }

    private fun initialize(d: MaterialDialog, title: String?, content: String, callback: (()->Unit)? = null) {
        if(title != null) d.title(text = title)
        d.message(text = content)
        if(callback!=null) d.positiveButton {
            callback()
        }
    }

}