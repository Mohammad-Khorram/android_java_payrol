package com.karafzar.payroll1402.util;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;

import androidx.core.content.res.ResourcesCompat;
import com.google.android.material.navigation.NavigationView;
import com.karafzar.payroll1402.R;

public class FontUtil
{
    public static Typeface getFontTypeface(Context context, int font)
    {
        return ResourcesCompat.getFont(context, font);
    }

    // change drawer font typeface
    public void customizeDrawerFontTypeface(Context context, NavigationView navigationView)
    {
        for (int i = 0; i < navigationView.getMenu().size(); i++)
        {
            if (navigationView.getMenu().getItem(i).getSubMenu() != null && navigationView.getMenu().getItem(i).getSubMenu().size() > 0)
            {
                for (int j = 0; j < navigationView.getMenu().getItem(i).getSubMenu().size(); j++)
                {
                    MenuItem menuItem = navigationView.getMenu().getItem(i).getSubMenu().getItem(j);
                    customizeItemTypeface(context, menuItem);
                }
            }

            customizeItemTypeface(context, navigationView.getMenu().getItem(i));
        }
    }

    private void customizeItemTypeface(Context context, MenuItem menuItem)
    {
        SpannableString spannableString = new SpannableString(menuItem.getTitle());
        spannableString.setSpan(new FontTypefaceUtil.customizeFontTypeface("" , FontUtil.getFontTypeface(context, R.font.vazir)), 0 , spannableString.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        menuItem.setTitle(spannableString);
    }
}
