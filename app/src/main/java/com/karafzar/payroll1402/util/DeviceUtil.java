package com.karafzar.payroll1402.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class DeviceUtil
{
    public static void customizeStatusBarColor(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= 21)
        {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(Color.rgb(15, 155, 85));
        }
    }

    // show keyboard
    public static void showSoftInput(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.showSoftInput(null, 0);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    // hide keyboard
    public static void closeSoftInput(Activity activity)
    {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (manager != null)
            manager.hideSoftInputFromWindow(activity.findViewById(android.R.id.content).getWindowToken(), 0);
    }
}
