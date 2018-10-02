package com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import com.lockscreen.hanmo.lockscreenkotlinexample.LockScreenApplication
import com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.LockScreenActivity

/**
 * 잠금화면 Activity를 On 해주는 Service
 * Created by hanmo on 2018. 10. 2..
 */

class LockScreenService : Service(){

    private var isPhoneIdleNum : Int? = null
    private val telephonyManager: TelephonyManager? by lazy { getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }

    private val lockScreenReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent) {
            context?.run {
                when(intent.action) {
                    Intent.ACTION_SCREEN_OFF -> {
                        telephonyManager?.run {
                            isPhoneIdleNum = callState
                        } ?: run {
                            getSystemService(TELEPHONY_SERVICE) as TelephonyManager
                            telephonyManager?.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE)
                        }

                        if (isPhoneIdleNum == TelephonyManager.CALL_STATE_IDLE) {
                            startLockScreenActivity()
                        }
                    }
                }
            }
        }
    }

    private val phoneListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, incomingNumber: String?) {
            super.onCallStateChanged(state, incomingNumber)
            isPhoneIdleNum = state
        }
    }

    private fun stateReceiver(isStartReceiver : Boolean) {
        if (isStartReceiver) {
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_SCREEN_OFF)
            registerReceiver(lockScreenReceiver, filter)
        } else {
            unregisterReceiver(lockScreenReceiver)
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //MyNotificationManager.createMainNotificationChannel(this@LockScreenService)
            startForeground(LockScreenApplication.notificationId, createNotificationCompatBuilder().build())
        }
    }

    private fun createNotificationCompatBuilder(): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mBuilder = NotificationCompat.Builder(this@LockScreenService, MyNotificationManager.getMainNotificationId())
            mBuilder
        } else {
            NotificationCompat.Builder(this@LockScreenService, "")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        stateReceiver(true)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        stateReceiver(false)
    }

    private fun startLockScreenActivity() {
        startActivity(LockScreenActivity.newIntent(this@LockScreenService))
    }

}