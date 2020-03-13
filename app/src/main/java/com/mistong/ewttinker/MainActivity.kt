package com.mistong.ewttinker

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : BaseActivity() {
    var str: String = "德玛西亚皇子"

    override fun getLayout(): Int = R.layout.activity_main
    override fun initEventAndData() {
        tv.setOnClickListener {
            Log.e("德玛", str)
        }

        update.setOnClickListener {
            applyPatch()
        }

        check.setOnClickListener {
            val preferences = getSharedPreferences("ewt_hotfix", Context.MODE_PRIVATE)
            preferences.edit().putBoolean("driving_fix", true).apply()
            Beta.checkUpgrade()
        }

        dawnLoad.setOnClickListener {
            Beta.downloadPatch()
        }
    }

    private fun applyPatch() {
        ///storage/emulated/0/Android/data/com.mistong.ewttinker/files下面
        val path = getExternalFilesDir(null)?.absolutePath + "/patch_signed_7zip.apk"
        val file = File(path)
        if (file.exists()) {
            Beta.canNotifyUserRestart = true //后面测试就没用了
            Beta.applyTinkerPatch(applicationContext, path)
        } else {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show()
        }
    }
}
