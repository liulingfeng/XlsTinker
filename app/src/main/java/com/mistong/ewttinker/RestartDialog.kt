package com.mistong.ewttinker

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


/**
 * @author liuxiaoshuai
 * @date 2020/3/13
 * @desc
 * @email liulingfeng@mistong.com
 */
class RestartDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("热修复")
        builder.setMessage("补丁应用成功，重启生效。现在就重启？")
        builder.setPositiveButton("确定") { _, _ ->
            dismiss()
            ActivityStack.finishAll()
            android.os.Process.killProcess(android.os.Process.myPid())
        }
        builder.setNegativeButton("取消") { _, _ ->
           dismiss()
        }
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        return dialog
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            if (manager.isDestroyed) {
                return
            }
            manager.beginTransaction().remove(this).commit()
            super.show(manager, tag)
        } catch (e: Exception) {
            //防止onSaveInstanceState之后执行add fragment
            e.printStackTrace()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): RestartDialog {
            return RestartDialog()
        }
    }
}