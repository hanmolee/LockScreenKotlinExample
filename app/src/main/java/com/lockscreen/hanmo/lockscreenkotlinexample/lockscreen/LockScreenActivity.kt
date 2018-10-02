package com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.lockscreen.hanmo.lockscreenkotlinexample.R
import com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.util.ButtonUnLock
import com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.util.ViewUnLock
import kotlinx.android.synthetic.main.activty_lockscreen.*

/**
 * 잠금화면 Activity
 * Created by hanmo on 2018. 10. 2..
 */

class LockScreenActivity : AppCompatActivity() {


    companion object {
        fun newIntent(context: Context?) : Intent {
            return Intent(context, LockScreenActivity::class.java)
                    .apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                    }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (hasFocus) window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
        }
    }

    @Suppress("DEPRECATION")
    override fun onAttachedToWindow() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        )

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        super.onAttachedToWindow()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_lockscreen)
    }

    override fun onResume() {
        super.onResume()

        setButtonUnlock()
        setViewUnlock()
    }

    private fun setButtonUnlock() {
        swipeUnLockButton.setOnUnlockListenerRight(object : ButtonUnLock.OnUnlockListener {
            override fun onUnlock() {
                finish()
            }
        })
    }


    private fun setViewUnlock() {
        lockScreenView.x = 0f
        lockScreenView.setOnTouchListener(object : ViewUnLock(this, lockScreenView) {
            override fun onFinish() {
                finish()
                super.onFinish()
            }
        })
    }

    override fun onBackPressed() {

    }
}
