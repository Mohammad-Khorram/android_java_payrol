package com.karafzar.payroll1402.util;

import android.app.Activity;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

public class SessionUtil
{
    public static SharedPreferences getSharedPreferences(Activity activity)
    {
        return activity.getSharedPreferences("sharedPreferences", MODE_PRIVATE);
    }
}