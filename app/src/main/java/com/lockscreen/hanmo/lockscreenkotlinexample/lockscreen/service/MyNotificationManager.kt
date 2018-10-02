package com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.service

import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi


/**
 * Oreo 이상일 경우 Notification Channel 생성
 * Created by hanmo on 2018. 10. 2..
 */
object MyNotificationManager {

    private val CHANNEL_ID = "LockScreen Example ID"
    private val CHANNEL_NAME = "LockScreen Example CHANEL"
    private val CHANNEL_DESCRIPTION = "This is LockScreen Example CHANEL"

    fun getMainNotificationId(): String {
        return CHANNEL_ID
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createMainNotificationChannel(context : Context?) {
        val id = CHANNEL_ID
        val name = CHANNEL_NAME
        val importance = android.app.NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(id, name, importance)

        mChannel.enableVibration(false)
        mChannel.enableLights(false)

        val mNotificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        mNotificationManager.createNotificationChannel(mChannel)
    }

}
