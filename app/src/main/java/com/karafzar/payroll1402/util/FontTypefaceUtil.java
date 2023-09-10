package com.karafzar.payroll1402.util;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;
import androidx.annotation.NonNull;

public class FontTypefaceUtil
{
    public static class customizeFontTypeface extends TypefaceSpan
    {
        Typeface typeface;

        public customizeFontTypeface(String string, Typeface typeface)
        {
            super(string);
            this.typeface = typeface;
        }

        @Override
        public void updateDrawState(@NonNull TextPaint textPaint)
        {
            applyCustomTypeFace(textPaint, typeface);
        }

        void applyCustomTypeFace(Paint paint, Typeface typeface)
        {
            paint.setTypeface(typeface);
        }
    }
}
