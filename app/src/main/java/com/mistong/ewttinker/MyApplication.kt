package com.mistong.ewttinker

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.interfaces.BetaPatchListener
import java.util.*

/**
 * @author liuxiaoshuai
 * @date 2020-03-06
 * @desc
 * @email liulingfeng@mistong.com
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //提示用户重启，默认为false
//        Beta.canNotifyUserRestart = true
        Beta.betaPatchListener = object : BetaPatchListener {
            override fun onPatchReceived(patchFile: String) {
                //转菊花
                val preferences = getSharedPreferences("ewt_hotfix", Context.MODE_PRIVATE)
                val drivingFix = preferences.getBoolean("driving_fix", false)
                if (drivingFix) {
                    if (ActivityStack.currentActivity() is BaseActivity) {
                        (ActivityStack.currentActivity() as BaseActivity).showLoadingDialog("合成中，请稍候…")
                    }
                }
            }

            override fun onDownloadReceived(savedLength: Long, totalLength: Long) {
                Log.e("德玛", "总长度${totalLength}现在长度$savedLength")
                Toast.makeText(
                    applicationContext,
                    String.format(
                        Locale.getDefault(), "%s %d%%",
                        Beta.strNotificationDownloading,
                        (if (totalLength == 0L) 0 else savedLength * 100 / totalLength).toInt()
                    ),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDownloadSuccess(msg: String) {
                Log.e("德玛","补丁下载成功")
            }

            override fun onDownloadFailure(msg: String) {
                Log.e("德玛","补丁下载失败")
            }

            override fun onApplySuccess(msg: String) {
                Log.e("德玛","补丁应用成功")
                //停止菊花
                val preferences = getSharedPreferences("ewt_hotfix", Context.MODE_PRIVATE)
                val drivingFix = preferences.getBoolean("driving_fix", false)
                if (drivingFix) {
                    if (ActivityStack.currentActivity() is BaseActivity) {
                        (ActivityStack.currentActivity() as BaseActivity).dismissLoadingDialog()
                    }
//                    Toast.makeText(applicationContext, "补丁应用成功", Toast.LENGTH_SHORT).show()
                    if (ActivityStack.currentActivity() is AppCompatActivity) {
                        RestartDialog.newInstance().show(
                            (ActivityStack.currentActivity() as AppCompatActivity).supportFragmentManager,
                            "restartDialog"
                        )
                    }
                    preferences.edit().putBoolean("driving_fix", false).apply()
                }
            }

            override fun onApplyFailure(msg: String) {
                Toast.makeText(applicationContext, "补丁应用失败", Toast.LENGTH_SHORT).show()
            }

            override fun onPatchRollback() {
                //补丁撤回
            }
        }

        //设置是否是开发设备
        if ("863976041784312" == Utils.getIMEI(this)) {
            Bugly.setIsDevelopmentDevice(applicationContext, true)
        }
        // 调试时，将第三个参数改为true
        Bugly.init(this, "fb5e873eb3", true)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(base)
        Beta.installTinker()
    }
}