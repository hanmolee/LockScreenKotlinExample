package com.lockscreen.hanmo.lockscreenkotlinexample.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.util.LockScreen

/**
 * 디바이스를 재시작해도 잠금화면 서비스가 시작되도록 하기위해
 * Created by hanmo on 2018. 10. 2..
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.run {
            if (action == Intent.ACTION_BOOT_COMPLETED) {

                val hasLockScreen = context?.run {
                    getSharedPreferences("LockScreenStatus", Context.MODE_PRIVATE)
                }

                if (hasLockScreen?.getBoolean("LockScreenStatus", false)!!) {
                    LockScreen.active()
                }
            }
        }
    }
}