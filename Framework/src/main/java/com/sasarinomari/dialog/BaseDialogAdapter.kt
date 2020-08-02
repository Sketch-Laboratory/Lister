package com.sasarinomari.dialog

abstract class BaseDialogAdapter {
    abstract fun message(title: String?, content: String, callback: (()->Unit)? = null): Dialog
    fun message(content: String, callback: (()->Unit)? = null) : Dialog {
        return message(null, content, callback)
    }

    abstract fun warning(title: String?, content: String, callback: (()->Unit)? = null) : Dialog
    fun warning(content: String, callback: (()->Unit)? = null) : Dialog {
        return warning(null, content, callback)
    }

    abstract fun error(title: String?, content: String, callback: (()->Unit)? = null) : Dialog
    fun error(content: String, callback: (()->Unit)? = null) : Dialog {
        return error(null, content, callback)
    }

    abstract fun progress(title: String?, content: String, callback: (()->Unit)? = null) : Dialog
    fun progress(content: String, callback: (()->Unit)? = null) : Dialog {
        return progress(null, content, callback)
    }

    abstract fun success(title: String?, content: String, callback: (()->Unit)? = null) : Dialog
    fun success(content: String, callback: (()->Unit)? = null) : Dialog {
        return success(null, content, callback)
    }
}