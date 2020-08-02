package com.sasarinomari.dialog

import android.app.Activity
import android.content.Context

class MaterialDialog(context: Context) : Dialog() {
    private val dialog =  com.afollestad.materialdialogs.MaterialDialog(context)
    override fun show() : Dialog {
        dialog.show { }
        return this
    }

    override fun setTitle(titleText: String) : Dialog {
        dialog.title(text = titleText)
        return this
    }

    override fun setContent(contentText: String) : Dialog {
        dialog.message(text = contentText)
        return this
    }

    override fun setCancelable(cancelable: Boolean) : Dialog {
        dialog.cancelable(cancelable)
        return this
    }

    override fun setPositiveButton(buttonText: String?, callback: (() -> Unit)?) : Dialog {
        if(callback!=null) {
            dialog.positiveButton(
                text = if(buttonText.isNullOrEmpty()) Dialog.positiveButtonText else buttonText
            ) { callback() }
        }
        return this
    }

    override fun setNegativeButton(buttonText: String?, callback: (() -> Unit)?) : Dialog {
        if(callback!=null) {
            dialog.negativeButton(
                text = if(buttonText.isNullOrEmpty()) Dialog.negativeButtonText else buttonText
            ) { callback() }
        }
        return this
    }
}

class MaterialDialogAdapter(private val content: Context) : BaseDialogAdapter() {
    override fun message(title: String?, content: String, callback: (()->Unit)?): Dialog {
        val d = MaterialDialog(this.content)
        if(!title.isNullOrEmpty()) d.setTitle(title)
        d.setContent(content)
        if(callback!=null) d.setPositiveButton { callback() }
        return d
    }

    override fun error(title: String?, content: String, callback: (()->Unit)?): Dialog {
        val d = MaterialDialog(this.content)
        if(!title.isNullOrEmpty()) d.setTitle(title)
        d.setContent(content)
        if(callback!=null) d.setPositiveButton { callback() }
        return d
    }

    override fun warning(title: String?, content: String, callback: (()->Unit)?): Dialog {
        val d = MaterialDialog(this.content)
        if(!title.isNullOrEmpty()) d.setTitle(title)
        d.setContent(content)
        if(callback!=null) d.setPositiveButton { callback() }
        return d
    }

    override fun progress(title: String?, content: String, callback: (()->Unit)?): Dialog {
        val d = MaterialDialog(this.content)
        if(!title.isNullOrEmpty()) d.setTitle(title)
        d.setContent(content)
        if(callback!=null) d.setPositiveButton { callback() }
        return d
    }

    override fun success(title: String?, content: String, callback: (()->Unit)?): Dialog {
        val d = MaterialDialog(this.content)
        if(!title.isNullOrEmpty()) d.setTitle(title)
        d.setContent(content)
        if(callback!=null) d.setPositiveButton { callback() }
        return d
    }
}