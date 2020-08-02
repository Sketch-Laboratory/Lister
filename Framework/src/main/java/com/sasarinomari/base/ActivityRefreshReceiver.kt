package com.sasarinomari.base

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ActivityRefreshReceiver(private val activity: Activity): BroadcastReceiver() {
    companion object {
        const val eventName = "Refresh_Activity"
    }
    enum class Parameters {
        Target
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        val target = p1!!.getStringExtra(Parameters.Target.name)
        if(target == activity::class.java.name) activity.recreate()
    }
}