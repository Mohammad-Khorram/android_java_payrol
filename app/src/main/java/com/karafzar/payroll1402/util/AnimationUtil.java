package com.karafzar.payroll1402.util;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class AnimationUtil
{
    // show/hide widget with fade effect
    public static void animationableViewGroup(ViewGroup viewGroup)
    {
        TransitionManager.beginDelayedTransition(viewGroup);
    }

    @SuppressLint("SetTextI18n")
    public static void valueAnimator(TextView textView, String prefix, String newString)
    {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(Integer.parseInt(textView.getText().toString().replace(",", "").replace("٬", "").replace(" تومان", "").replace(prefix, "")), Integer.parseInt(newString));
        valueAnimator.setDuration(750);
        valueAnimator.addUpdateListener(animation -> textView.setText(prefix + new DecimalFormat().format(Integer.parseInt(animation.getAnimatedValue().toString())) + " تومان"));
        valueAnimator.start();
    }
}