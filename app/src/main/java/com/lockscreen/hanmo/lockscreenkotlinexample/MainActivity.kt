package com.lockscreen.hanmo.lockscreenkotlinexample

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.util.LockScreen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val lockScreenStatusPreferences by lazy { getSharedPreferences("LockScreenStatus", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initLockScreenSwitch()
        setChangeLockscreenSwitch()
    }

    private fun setLockScreenStatus(lockScreenStatus : Boolean) {
        lockScreenStatusPreferences.edit()?.run {
            putBoolean("LockScreenStatus", lockScreenStatus)
            apply()
        }
    }

    private fun setChangeLockscreenSwitch() {
        lockScreeSwitch.setOnCheckedChangeListener { _, isChecked ->
            setLockScreenStatus(isChecked)
            if (isChecked) {
                LockScreen.active()
                Snackbar.make(lockScreeSwitch, getString(R.string.lockscrenOn), Snackbar.LENGTH_LONG).show()
            } else {
                LockScreen.deactivate()
                Snackbar.make(lockScreeSwitch, getString(R.string.lockscrenOff), Snackbar.LENGTH_LONG).show()
            }
        }
    }


    private fun initLockScreenSwitch() {
        val hasLockScreen = lockScreenStatusPreferences.getBoolean("LockScreenStatus", false)
        lockScreeSwitch.isChecked = hasLockScreen
        if (hasLockScreen) {
            LockScreen.active()
        }
        else { LockScreen.deactivate() }
    }

}
