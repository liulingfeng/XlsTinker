package com.mistong.ewttinker

import android.Manifest.permission.READ_PHONE_STATE
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.core.app.ActivityCompat

/**
 * @author liuxiaoshuai
 * @date 2020-03-11
 * @desc
 * @email liulingfeng@mistong.com
 */
object Utils {
    fun getIMEI(context: Context): String {
        if (ActivityCompat.checkSelfPermission(
                context,
                READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val imeI = tm.imei
                if (!TextUtils.isEmpty(imeI)) return imeI
                val meId = tm.meid
                return if (TextUtils.isEmpty(meId)) "" else meId

            }
            return tm.deviceId
        }
        return ""
    }
}