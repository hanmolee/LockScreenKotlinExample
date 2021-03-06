package com.lockscreen.hanmo.lockscreenkotlinexample

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.LockScreenActivity
import com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.service.LockScreenService
import com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.util.LockScreen
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Activity
 * Created by hanmo on 2018. 10. 2..
 */

class MainActivity : AppCompatActivity() {

    private val lockScreenStatusPreferences by lazy { getSharedPreferences("LockScreenStatus", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLockScreenSwitch()
        setChangeLockScreenSwitch()
        setShowLockScreenButton()

    }

    private fun setShowLockScreenButton() {
        showLockScreenViewButton.setOnClickListener {
            startActivity(LockScreenActivity.newIntent(this@MainActivity))
        }
    }

    private fun setLockScreenStatus(lockScreenStatus : Boolean) {
        lockScreenStatusPreferences.edit()?.run {
            putBoolean("LockScreenStatus", lockScreenStatus)
            apply()
        }
    }

    private fun setChangeLockScreenSwitch() {
        lockScreeSwitch.setOnCheckedChangeListener { _, isChecked ->
            setLockScreenStatus(isChecked)
            if (isChecked) {
                LockScreen.active()
                Snackbar.make(lockScreeSwitch, getString(R.string.lockscrenOn), Snackbar.LENGTH_LONG).show()
            } else {
                LockScreen.deActivate()
                Snackbar.make(lockScreeSwitch, getString(R.string.lockscrenOff), Snackbar.LENGTH_LONG).show()
            }
        }
    }


    private fun initLockScreenSwitch() {
        val hasLockScreen = LockScreen.getLockScreenStatus()
        lockScreeSwitch.isChecked = hasLockScreen
        if (hasLockScreen) {
            LockScreen.active()
        }
        else { LockScreen.deActivate() }
    }

}
