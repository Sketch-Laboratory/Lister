package com.sasarinomari.dialog

abstract class Dialog {
    abstract fun setTitle(titleText: String) : Dialog
    abstract fun setContent(contentText: String) : Dialog
    abstract fun setCancelable(cancelable: Boolean) : Dialog
    abstract fun setPositiveButton(buttonText: String? = null, callback: (() -> Unit)? = null) : Dialog
    abstract fun setNegativeButton(buttonText: String? = null, callback: (() -> Unit)? = null) : Dialog

    abstract fun show() : Dialog

    companion object DialogText {
        var positiveButtonText = "OK"
        var negativeButtonText = "Cancel"
    }
}