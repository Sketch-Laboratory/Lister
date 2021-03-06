package com.sasarinomari.base

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.*

abstract class BaseService: Service() {
    companion object {
        private var innerRunningFlag = false
        private var ACTION_STOP_SERVICE = "StopService4425"

        @Suppress("DEPRECATION")
        fun checkServiceRunning(context: Context, serviceName: String): Boolean {
            var flag1 = false
            val flag2 = innerRunningFlag

            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceName == service.service.className) {
                    Log.i(serviceName, "service '$serviceName' is already running.")
                    flag1 = true
                    break
                }
            }

            return flag1 and flag2
        }
    }

    protected val ChannelName: String = "Service"
    protected val NotificationId:Int = Date().time.toInt()

    private lateinit var silentChannelBuilder: NotificationCompat.Builder
    private lateinit var defaultChannelBuilder: NotificationCompat.Builder

    @Suppress("DEPRECATION")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // region 서비스 중단 분기 코드
        if(intent!=null && intent.action != null && intent.action!! == ACTION_STOP_SERVICE) {
            stopForeground(true)
            stopSelf()
            return START_NOT_STICKY
        }
        // endregion
        innerRunningFlag = true
        // region 알림 채널 빌드 코드
        silentChannelBuilder = if (Build.VERSION.SDK_INT >= 26) {
            NotificationCompat.Builder(this, ChannelName)
        } else {
            NotificationCompat.Builder(this)
        }
        silentChannelBuilder.setSound(null)
        defaultChannelBuilder = if (Build.VERSION.SDK_INT >= 26) {
            NotificationCompat.Builder(this, "General")
        } else {
            NotificationCompat.Builder(this)
        }
        // endregion
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.i(this::class.java.name, "onDestroy")
        innerRunningFlag = false
        stopAllManagedThreads()
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.i(this::class.java.name, "onBind")
        return null
    }

    // region 알림 관련 코드
    protected  fun createNotification(
        title: String,
        text: String,
        silent: Boolean = true,
        cancelable: Boolean = false,
        redirect: Intent = Intent()
    ): Notification {
        val pendingIntent = PendingIntent.getActivity(this, 0, redirect, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = if (silent) silentChannelBuilder else defaultChannelBuilder

        // builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle(title)
        builder.setContentText(text)
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(cancelable)

        if(!cancelable) {
            val cls = this::class.java
            val cancelIntent = Intent(this, cls)
            cancelIntent.action = ACTION_STOP_SERVICE
            val cancelPendingIntent = PendingIntent.getService(this, 0, cancelIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            builder.mActions.clear()
            builder.addAction(0, "중지", cancelPendingIntent)
        }

        return builder.build()!!
    }

    protected fun sendNotification(
        title: String,
        text: String,
        silent: Boolean = true,
        cancelable: Boolean = false,
        redirect: Intent = Intent(),
        id: Int = NotificationId
    ) {
        val notification = createNotification(title, text, silent, cancelable, redirect)
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(id, notification)
    }

    private var _lastTick: Long = -1
    protected fun restrainedNotification(title: String, desc: String){
        val currentTick: Long = System.currentTimeMillis()
        if (currentTick > _lastTick + (1000 * 3)) {
            sendNotification(title, desc)
            _lastTick = currentTick
        }
    }
    // endregion

    protected fun sendActivityRefreshNotification(targetActivityClassName: String) {
        val intent = Intent(ActivityRefreshReceiver.eventName)
        intent.putExtra(ActivityRefreshReceiver.Parameters.Target.name, targetActivityClassName)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    // region 서비스 종속 스레드 관리 코드
    private var threads = ArrayList<Thread>()
    protected fun runOnManagedThread(runnable: ()-> Unit) {
        val thread = Thread(runnable)
        threads.add(thread)
        thread.start()
    }
    private fun stopAllManagedThreads() {
        for (t in threads) {
            if(t.isAlive) {
                t.interrupt()
                // t.stop()
            }
        }
    }
    // endregion

    protected open fun finish() {
        stopForeground(true)
        stopSelf()
    }
}