package com.sasarinomari.dialog

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog.*

class SweetAlertDialog(context: Context, dialogType: Int) : Dialog() {
    private val dialog =  cn.pedant.SweetAlert.SweetAlertDialog(context, dialogType)
    override fun show() : Dialog {
        dialog.show()
        return this
    }

    override fun setTitle(titleText: String) : Dialog {
        dialog.setTitle(titleText)
        return this
    }

    override fun setContent(contentText: String) : Dialog {
        dialog.contentText = contentText
        return this
    }

    override fun setCancelable(cancelable: Boolean) : Dialog {
        dialog.setCancelable(cancelable)
        return this
    }

    override fun setPositiveButton(buttonText: String?, callback: (() -> Unit)?) : Dialog {
        if(callback!=null) {
            dialog.setConfirmButton(
                if(buttonText.isNullOrEmpty()) Dialog.positiveButtonText else buttonText
            ) { callback() }
        }
        return this
    }

    override fun setNegativeButton(buttonText: String?, callback: (() -> Unit)?) : Dialog {
        if(callback!=null) {
            dialog.setCancelButton(
                if(buttonText.isNullOrEmpty()) Dialog.negativeButtonText else buttonText
            ) { callback() }
        }
        return this
    }
}

class SweetDialogAdapter(private val context: Context) : BaseDialogAdapter() {
    private fun initialize(d: Dialog, title: String?, content: String, callback: (() -> Unit)?) {
        if(!title.isNullOrEmpty()) d.setTitle(title)
        d.setContent(content)
        if(callback!=null) d.setPositiveButton { callback() }
    }

    override fun message(title: String?, content: String, callback: (()->Unit)?): SweetAlertDialog {
        val d = SweetAlertDialog(context, NORMAL_TYPE)
        initialize(d, title, content, callback)
        return d
    }

    override fun error(title: String?, content: String, callback: (()->Unit)?): SweetAlertDialog {
        val d = SweetAlertDialog(context, ERROR_TYPE)
        initialize(d, title, content, callback)
        return d
    }

    override fun warning(title: String?, content: String, callback: (()->Unit)?): SweetAlertDialog {
        val d = SweetAlertDialog(context, WARNING_TYPE)
        initialize(d, title, content, callback)
        return d
    }

    override fun progress(title: String?, content: String, callback: (()->Unit)?): SweetAlertDialog {
        val d = SweetAlertDialog(context, PROGRESS_TYPE)
        initialize(d, title, content, callback)
        d.setCancelable(false)
        return d
    }

    override fun success(title: String?, content: String, callback: (()->Unit)?): SweetAlertDialog {
        val d = SweetAlertDialog(context, SUCCESS_TYPE)
        initialize(d, title, content, callback)
        return d
    }
}