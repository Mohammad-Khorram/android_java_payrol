package com.karafzar.payroll1402.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.TypedValue;

public class OtherUtil
{
    private Activity activity;

    public OtherUtil(Activity activity)
    {
        this.activity = activity;
    }

    // check specified application installed on user device?
    public boolean checkApplicationAvailable(String packageName)
    {
        try
        {
            activity.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

    // convert integer to DP unit
    public int convertIntegerToUnitDIP(int value)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, activity.getResources().getDisplayMetrics());
    }
}
