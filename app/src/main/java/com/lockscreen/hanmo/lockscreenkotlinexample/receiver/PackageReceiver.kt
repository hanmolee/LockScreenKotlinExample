package com.lockscreen.hanmo.lockscreenkotlinexample.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.lockscreen.hanmo.lockscreenkotlinexample.lockscreen.util.LockScreen

/**
 * Created by hanmo on 2018. 10. 2..
 *
 * 설치, 제거, 업데이트 시 캐치
 * 업데이트 시 잠금화면 서비스를 켜주어야 하기 때문
 * 업데이트 경우 ACTION_PACKAGE_REPLACED 를 사용하면
 * 오레오에서 암시적 브로드캐스트를 받을수 없으므로 ACTION_MY_PACKAGE_REPLACED 로 대체
 */
class PackageReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.run {
            //내 앱에서만 설치 및 삭제가 캐치 되도록
            //if (dataString.contains(BuildConfig.APPLICATION_ID)) { }
            when (action) {
                //Intent.ACTION_PACKAGE_ADDED -> { }
                //Intent.ACTION_PACKAGE_REMOVED -> { }
                Intent.ACTION_MY_PACKAGE_REPLACED -> {
                    if (LockScreen.getLockScreenStatus()) {
                        LockScreen.active()
                    }
                }
            }
        }
    }
}