package com.tzj.tzjcustomview;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：系统功能工具
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2015-12-17 11:17
 */
public class FunctionUtil {

    /**
     * 关闭软键盘
     */
    public static void hideSoftKeyboard(View view, Context context) {
        if (view == null)
            return;
        InputMethodManager imm = (InputMethodManager) context.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 打开软键盘
     */
    public static void showSoftKeyboard(View view, Context context) {
        if (view == null)
            return;
        InputMethodManager imm = (InputMethodManager) context.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }
}
