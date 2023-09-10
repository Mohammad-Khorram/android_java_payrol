package com.karafzar.payroll1402.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.karafzar.payroll1402.R;
import java.util.ArrayList;
import java.util.Objects;

public class VisualMessageUtil
{
    private RoundedBottomSheetDialog roundedBottomSheetDialog;
    private BottomSheetBehavior bottomSheetBehavior;

    // show custom alert dialog
    public static void showAlertDialog(Activity activity, String message, String positiveButtonTitle, DialogInterface.OnClickListener onPositiveButtonListener, String negativeButtonTitle, DialogInterface.OnClickListener onNegativeButtonListener, Boolean isCancelable)
    {
        initializeAlertDialog(activity, new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(positiveButtonTitle, (dialogInterface, i) ->
                {
                    if(onPositiveButtonListener != null)
                        onPositiveButtonListener.onClick(dialogInterface, i);
                })
                .setNegativeButton(negativeButtonTitle, (dialogInterface, i) ->
                {
                    if(onNegativeButtonListener != null)
                        onNegativeButtonListener.onClick(dialogInterface, i);
                })
                .setCancelable(isCancelable)
                .show());
    }

    private static void initializeAlertDialog(Activity activity, AlertDialog alertDialog)
    {
        try
        {
            TextView txtMessage = alertDialog.findViewById(android.R.id.message);
            Button btnPositive = alertDialog.findViewById(android.R.id.button1);
            Button btnNegative = alertDialog.findViewById(android.R.id.button2);

            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_roundedcorner_primary);
            alertDialog.getWindow().setLayout((int)(activity.getResources().getDisplayMetrics().widthPixels * 0.7), LinearLayout.LayoutParams.WRAP_CONTENT);
            txtMessage.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorDark));
            txtMessage.setTypeface(FontUtil.getFontTypeface(activity, R.font.vazir));
            txtMessage.setLineSpacing(0, 1.25f);
            Objects.requireNonNull(btnPositive).setTypeface(FontUtil.getFontTypeface(activity, R.font.vazir));
            Objects.requireNonNull(btnNegative).setTypeface(FontUtil.getFontTypeface(activity, R.font.vazir));
            txtMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        }
        catch (Exception ignored){}
    }

    // show dialog with selectable items
    public static class dialogMenu extends ArrayAdapter<String>
    {
        private Activity activity;
        int selected;

        public dialogMenu(Activity activity, ArrayList<String> name, int selected)
        {
            super(activity, android.R.layout.select_dialog_item, name);
            this.activity = activity;
            this.selected = selected;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent)
        {
            View view = super.getView(position, convertView, parent);
            TextView textView = view.findViewById(android.R.id.text1);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, new OtherUtil(activity).convertIntegerToUnitDIP(50));

            textView.setLayoutParams(layoutParams);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setTypeface(FontUtil.getFontTypeface(activity, R.font.vazir));

            if(selected == position)
                textView.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorAccent));
            else
                textView.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorDark));

            return view;
        }
    }

    public void InitializeBottomSheetDialog(Activity activity, View view)
    {
        if(view.getParent() != null)
            ((ViewGroup) view.getParent()).removeView(view);

        roundedBottomSheetDialog = new RoundedBottomSheetDialog(activity);
        roundedBottomSheetDialog.setContentView(view);
        roundedBottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());

        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback()
        {
            @Override public void onSlide(@NonNull View bottomSheet, float slideOffset){}

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState)
            {
                if(newState == BottomSheetBehavior.STATE_HIDDEN)
                {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    CloseBottomSheetDialog();
                }
            }
        };

        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        roundedBottomSheetDialog.show();
    }

    public void CloseBottomSheetDialog()
    {
        try
        {
            if(roundedBottomSheetDialog.isShowing())
                roundedBottomSheetDialog.dismiss();
        }
        catch (Exception ignored){}
    }
}