package com.lockscreen.hanmo.lockscreenkotlinexample

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication

/**
 * 잠금화면 Example Application
 * Created by hanmo on 2018. 10. 2..
 */
class LockScreenApplication : MultiDexApplication() {

    init {
        instance = this@LockScreenApplication
    }

    companion object {
        private var instance: LockScreenApplication? = null
        const val notificationId: Int = 1

        fun applicationContext() : Context? {
            return instance?.applicationContext
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this@LockScreenApplication)
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }
}