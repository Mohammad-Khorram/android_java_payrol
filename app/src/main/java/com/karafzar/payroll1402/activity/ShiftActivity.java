package com.karafzar.payroll1402.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import com.karafzar.payroll1402.R;
import com.karafzar.payroll1402.util.AnimationUtil;
import com.karafzar.payroll1402.util.DeviceUtil;
import com.karafzar.payroll1402.util.DigitUtil;
import com.karafzar.payroll1402.util.VisualMessageUtil;
import java.text.DecimalFormat;
import java.util.ArrayList;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class ShiftActivity extends AppCompatActivity
{
    private RelativeLayout relativeLayout;
    private Toolbar toolbar;
    private TextView lblSubtitle;
    private TextInputLayout inpWages;
    private EditText txtWages;
    private CheckBox chkWages;
    private TextView lblWagesDesc, lblShift, lblResult;
    private CardView crdShift;
    private int dayOfMonth, wages = 139325, currentWages, selectedShift = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

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
        chkWages = findViewById(R.id.chkWages);
        lblWagesDesc = findViewById(R.id.lblWagesDesc);
        crdShift = findViewById(R.id.crdShift);
        lblShift = findViewById(R.id.lblShift);
        lblResult = findViewById(R.id.lblResult);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        chkWages.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            AnimationUtil.animationableViewGroup(relativeLayout);

            selectedShift = -1;
            lblShift.setText("انتخاب نشده");
            AnimationUtil.valueAnimator(lblResult, "حق شیفت: ", "0");

            if(isChecked)
            {
                txtWages.setText("");
                inpWages.setVisibility(View.GONE);
                lblWagesDesc.setVisibility(View.VISIBLE);
                DeviceUtil.closeSoftInput(this);
            }
            else
            {
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
                    selectedShift = -1;
                    lblShift.setText("انتخاب نشده");
                    AnimationUtil.valueAnimator(lblResult, "حق شیفت: ", "0");
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

        crdShift.setOnClickListener(v -> ShowShift());
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
        currentWages = dayOfMonth * wages;
        lblWagesDesc.setText("حداقل حقوق پایه در " + new PersianCalendar().getPersianMonthName() + "1402 برابر با " + new DecimalFormat().format(dayOfMonth * wages) + " تومان می\u200cباشد.");
    }

    private void ShowShift()
    {
        ArrayList<String> shift = new ArrayList<>();
        ArrayList<Float> shiftValue = new ArrayList<>();

        shift.add("شیفت کاری ندارم"); shiftValue.add(0f);
        shift.add("صبح، عصر"); shiftValue.add(10f);
        shift.add("صبح، عصر، شب"); shiftValue.add(15f);
        shift.add("صبح، شب"); shiftValue.add(22.5f);
        shift.add("شب"); shiftValue.add(22.5f);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setAdapter(new VisualMessageUtil.dialogMenu(this, shift, selectedShift), (dialog, which) ->
                {
                    if(chkWages.isChecked())
                        currentWages = dayOfMonth * wages;
                    else
                        currentWages = Integer.parseInt(txtWages.getText().toString().replace(",", "").replace("٬", ""));

                    selectedShift = which;
                    lblShift.setText(shift.get(which));
                    AnimationUtil.valueAnimator(lblResult, "حق شیفت: ", String.valueOf((int)(currentWages * shiftValue.get(which) / 100)));
                }).show();

        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_roundedcorner_primary);
        alertDialog.getWindow().setLayout((int)(getResources().getDisplayMetrics().widthPixels * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}