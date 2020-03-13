package com.mistong.ewttinker

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author liuxiaoshuai
 * @date 2020/3/13
 * @desc
 * @email liulingfeng@mistong.com
 */
abstract class BaseActivity : AppCompatActivity() {
    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        ActivityStack.pushActivity(this)

        initEventAndData()
    }

    fun showLoadingDialog(msg: String = "正在加载，请稍候…") {
        if (loadingDialog != null) {
            if (loadingDialog!!.isShowing) {
                loadingDialog!!.dismiss()
            }
            loadingDialog = null
        }

        loadingDialog = DialogManger.createLoadingDialog(this, msg)
        loadingDialog?.setCanceledOnTouchOutside(false)
        if (!isFinishing) {
            loadingDialog?.show()
        }
    }

    fun dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
            loadingDialog = null
        }
    }

    abstract fun getLayout(): Int
    abstract fun initEventAndData()
}