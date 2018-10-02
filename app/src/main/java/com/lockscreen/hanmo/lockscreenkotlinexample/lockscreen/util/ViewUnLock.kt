package com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.util

import android.annotation.SuppressLint
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation

/**
 * 잠금화면을 스와이프하면 finish 하도록 하는 touch util
 * Created by hanmo on 2018. 10. 2..
 */

open class ViewUnLock(val context: Context, val lockScreenView: ConstraintLayout) : View.OnTouchListener {

    private var firstTouchX = 0f
    private var layoutPrevX = 0f
    private var lastLayoutX = 0f
    private var layoutInPrevX = 0f
    private var isLockOpen = false
    private var touchMoveX = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                firstTouchX = event.x + 5f
                layoutPrevX = lockScreenView.x
                isLockOpen = true

            }

            MotionEvent.ACTION_MOVE -> {
                if (isLockOpen) {
                    touchMoveX = (event.rawX - firstTouchX).toInt()
                    if (lockScreenView.x >= 0) {
                        lockScreenView.x = (layoutPrevX + touchMoveX).toInt().toFloat()
                        if (lockScreenView.x < 0) {
                            lockScreenView.x = 0f
                        }

                        lastLayoutX = lockScreenView.x
                    }
                } else {
                    return false
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isLockOpen) {
                    lockScreenView.x = lastLayoutX
                    lockScreenView.y = 0f
                    optimizeForground(lastLayoutX)
                }
                isLockOpen = false
                firstTouchX = 0f
                layoutPrevX = 0f
                layoutInPrevX = 0f
                touchMoveX = 0
                lastLayoutX = 0f
            }
            else -> {
            }
        }

        return true
    }


    private fun optimizeForground(forgroundX: Float) {

        val displayMetrics = context.resources.displayMetrics
        val mDeviceWidth = displayMetrics.widthPixels
        val mDevideDeviceWidth = mDeviceWidth / 6

        if (forgroundX < mDevideDeviceWidth) {
            var startPosition = mDevideDeviceWidth
            while (startPosition >= 0) {
                lockScreenView.x = startPosition.toFloat()
                startPosition--
            }
        } else {
            val animation = TranslateAnimation(0f, mDeviceWidth.toFloat(), 0f, 0f)
            animation.duration = 300
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    lockScreenView.x = mDeviceWidth.toFloat()
                    lockScreenView.y = 0f
                    onFinish()
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })

            lockScreenView.startAnimation(animation)
        }
    }

    open fun onFinish() {

    }
}