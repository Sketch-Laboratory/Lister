package com.sasarinomari.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sasarinomari.dialog.BaseDialogAdapter
import com.sasarinomari.dialog.MaterialDialogAdapter

abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var da: BaseDialogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        da = MaterialDialogAdapter(this)
    }

    open fun onFinish() { }
    override fun finish() {
        onFinish()
        super.finish()
    }

    //region FirstRun
    protected fun isFirstRunThisActivity() : Boolean {
        val prefs = getSharedPreferences("fr${this::class.java.name}", Context.MODE_PRIVATE)
        val flag = prefs.getInt("flag", 0)
        return flag == 0
    }
    protected fun setNotFirstrun(){
        val prefs = getSharedPreferences("fr${this::class.java.name}", Context.MODE_PRIVATE).edit()
        prefs.putInt("flag", 1)
        prefs.apply()
    }
    //endregion

    // region Backpress Jail
    private val FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0
    fun backPressJail(warningText: String): Boolean {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime

        return when (intervalTime) {
            in 0..FINISH_INTERVAL_TIME -> false
            else -> {
                Toast.makeText(this, warningText, Toast.LENGTH_SHORT).show()
                backPressedTime = tempTime
                true
            }
        }
    }
    // endregion

    fun hideKeyboard() {
        val windowToken = currentFocus?.windowToken
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    // region Action Bar
    fun setActionbarVisibility(show: Boolean) {
        if(show) actionBar?.show() else actionBar?.hide()
    }
    fun setActionbarTitle(titleText: String) {
        actionBar?.title = titleText
    }
    // endregion
}