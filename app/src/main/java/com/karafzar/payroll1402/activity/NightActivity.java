package com.karafzar.payroll1402.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.karafzar.payroll1402.R;
import com.karafzar.payroll1402.util.AnimationUtil;
import com.karafzar.payroll1402.util.DeviceUtil;
import com.karafzar.payroll1402.util.DigitUtil;

import java.text.DecimalFormat;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class NightActivity extends AppCompatActivity
{
    private RelativeLayout relativeLayout;
    private Toolbar toolbar;
    private TextView lblSubtitle;
    private TextInputLayout inpWages;
    private EditText txtWages, txtNight;
    private CheckBox chkWages;
    private TextView lblWagesDesc, lblResult;
    private int dayOfMonth, wages = 139325, currentWages, night;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);

        InitializeComponent();
        DayOfMonth(new PersianCalendar().getPersianMonth());
        SettingVariable();
    }

    private void InitializeComponent()
    {
        relativeLayout = findViewById(R.id.relativeLayout);
        toolbar = findViewById(R.id.toolbar);
        lblSubtitle = findViewById(R.id.lblSubtitle);
        inpWages = findViewById(R.id.inpWages);
        txtWages = findViewById(R.id.txtWages);
        txtNight = findViewById(R.id.txtNight);
        chkWages = findViewById(R.id.chkWages);
        lblWagesDesc = findViewById(R.id.lblWagesDesc);
        lblResult = findViewById(R.id.lblResult);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        chkWages.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            AnimationUtil.animationableViewGroup(relativeLayout);

            if(isChecked)
            {
                if(chkWages.isChecked())
                    currentWages = dayOfMonth * wages;
                else
                    currentWages = Integer.parseInt(txtWages.getText().toString().replace(",", "").replace("٬", ""));

                if(txtNight.length() != 0)
                    night = (int) (currentWages * 0.35 / 220 * Double.parseDouble(txtNight.getText().toString()));
                else
                    night = 0;

                txtWages.setText("");
                AnimationUtil.valueAnimator(lblResult, "میزان شب\u200cکاری: ", String.valueOf(night));
                inpWages.setVisibility(View.GONE);
                lblWagesDesc.setVisibility(View.VISIBLE);
                DeviceUtil.closeSoftInput(this);
            }
            else
            {
                txtNight.setText("");
                AnimationUtil.valueAnimator(lblResult, "میزان شب\u200cکاری: ", "0");
                lblWagesDesc.setVisibility(View.GONE);
                inpWages.setVisibility(View.VISIBLE);
                txtWages.requestFocus();
                DeviceUtil.showSoftInput(this);
            }
        });

        txtWages.addTextChangedListener(new TextWatcher()
        {
            @Override public void onTextChanged(CharSequence charSequence, int start, int before, int count){}
            @Override public void beforeTextChanged(CharSequence charSequence, int start, int count, int after){}

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length() != 0)
                {
                    txtNight.setText("");
                    AnimationUtil.valueAnimator(lblResult, "میزان شب\u200cکاری: ", "0");
                    txtWages.removeTextChangedListener(this);

                    if (!txtWages.getText().toString().equals(""))
                    {
                        txtWages.setText(new DigitUtil().thousandDigitSeparator(txtWages.getText().toString().replaceAll(",", "")));
                        txtWages.setSelection(txtWages.length());
                    }

                    txtWages.addTextChangedListener(this);
                }
            }
        });

        txtNight.addTextChangedListener(new TextWatcher()
        {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count){}
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length() != 0)
                {
                    if(chkWages.isChecked())
                        currentWages = dayOfMonth * wages;
                    else
                        currentWages = Integer.parseInt(txtWages.getText().toString().replace(",", "").replace("٬", ""));
                }

                if(editable.length() == 0 || editable.toString().equals("0"))
                    AnimationUtil.valueAnimator(lblResult, "میزان شب\u200cکاری: ", "0");
                else
                    AnimationUtil.valueAnimator(lblResult, "میزان شب\u200cکاری: ", String.valueOf((int)(currentWages * 0.35 / 220 * Double.parseDouble(editable.toString()))));
            }
        });
    }

    private void DayOfMonth(int month)
    {
        switch (month)
        {
            case 1: case 2: case 3: case 4: case 5: case 6:
            dayOfMonth = 31;
            break;

            case 7: case 8: case 9: case 10: case 11: case 12:
            dayOfMonth = 30;
            break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void SettingVariable()
    {
        DeviceUtil.customizeStatusBarColor(this);
        lblSubtitle.setText("ماه: " + new PersianCalendar().getPersianMonthName());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_right_light_24dp);
        lblWagesDesc.setText("حداقل حقوق پایه در " + new PersianCalendar().getPersianMonthName() + "1402 برابر با " + new DecimalFormat().format(dayOfMonth * wages) + " تومان می\u200cباشد.");
    }
}