package com.mistong.ewttinker;

import android.app.Activity;

import java.util.Stack;

/**
 * @Date 2018/8/29
 * @Author chenweida
 * @Email chenweida@mistong.com
 * @Description activity栈管理
 */
public class ActivityStack {

    private static Stack<Activity> stack;

    private ActivityStack() {
        stack = new Stack<>();
    }

    // 退出栈顶
    public static void popActivity(Activity activity) {
        if (stack != null && activity != null) {
            stack.remove(activity);
        }
    }

    // 当前Activity推入栈中
    public static void pushActivity(Activity activity) {
        if (stack == null) {
            stack = new Stack<>();
        }
        if (activity != null) {
            stack.add(activity);
        }
    }

    // 获得当前栈顶
    public static Activity currentActivity() {
        if (stack != null && !stack.empty()) {
            return stack.lastElement();
        }
        return null;
    }

    /**
     * finish 除activity外stack内其余activity
     * @param activity 指定activity
     */
    public static void finishOthers(Activity activity) {
        if (stack == null) return;
        for (Activity sa: stack) {
            if (!sa.equals(activity)) {
                sa.finish();
            }
        }
        stack.clear();
        stack.push(activity);
    }

    public static void finishOthers() {
        Activity currentTop = currentActivity();
        if (currentTop != null) {
            finishOthers(currentTop);
        }
    }

    /**
     * finish 栈中所有activity
     */
    public static void finishAll() {
        if (stack == null) return;
        while (!stack.empty()) {
            Activity activity = stack.pop();
            activity.finish();
        }
    }

}
