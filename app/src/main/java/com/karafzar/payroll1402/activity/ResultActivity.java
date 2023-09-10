package com.karafzar.payroll1402.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.karafzar.payroll1402.R;
import com.karafzar.payroll1402.util.DeviceUtil;
import com.karafzar.payroll1402.util.VisualMessageUtil;

import java.text.DecimalFormat;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView lblName, lblDate, lblMonth, lblWages, lblChilds, lblShift, lblExtra, lblInsurance, lblTotalWages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        InitializeComponent();
        SettingVariable();
    }

    private void InitializeComponent() {
        toolbar = findViewById(R.id.toolbar);
        lblName = findViewById(R.id.lblName);
        lblDate = findViewById(R.id.lblDate);
        lblMonth = findViewById(R.id.lblMonth);
        lblWages = findViewById(R.id.lblWages);
        lblChilds = findViewById(R.id.lblChilds);
        lblShift = findViewById(R.id.lblShift);
        lblExtra = findViewById(R.id.lblExtra);
        lblInsurance = findViewById(R.id.lblInsurance);
        lblTotalWages = findViewById(R.id.lblTotalWages);

        toolbar.setOnMenuItemClickListener(item ->
        {
            finish();
            startActivity(new Intent(ResultActivity.this, PayrollActivity.class));

            return false;
        });
    }

    @SuppressLint("SetTextI18n")
    private void SettingVariable() {
        DeviceUtil.customizeStatusBarColor(this);
        toolbar.inflateMenu(R.menu.result);

        if (Objects.requireNonNull(getIntent().getStringExtra("name")).equals(""))
            lblName.setText("درج نشده!");
        else
            lblName.setText(getIntent().getStringExtra("name"));

        if (Objects.requireNonNull(getIntent().getStringExtra("date")).equals(""))
            lblDate.setText("درج نشده!");
        else
            lblDate.setText(getIntent().getStringExtra("date"));

        if (getIntent().getIntExtra("childs", 0) == 0)
            lblChilds.setText("0 تومان");
        else
            lblChilds.setText(new DecimalFormat().format(getIntent().getIntExtra("childs", 0)) + " تومان");

        if (getIntent().getIntExtra("extra", 0) == 0)
            lblExtra.setText("0 تومان");
        else
            lblExtra.setText(new DecimalFormat().format(getIntent().getIntExtra("extra", 0)) + " تومان");

        lblMonth.setText(getIntent().getStringExtra("month"));
        lblWages.setText(new DecimalFormat().format(getIntent().getIntExtra("wages", 0)) + " تومان");
        lblShift.setText(new DecimalFormat().format(getIntent().getIntExtra("shift", 0)) + " تومان");
        lblInsurance.setText(new DecimalFormat().format(getIntent().getIntExtra("insurance", 0)) + " تومان");
        lblTotalWages.setText(new DecimalFormat().format(getIntent().getIntExtra("totalWages", 0)) + " تومان");
    }

    @Override
    public void onBackPressed() {
        VisualMessageUtil.showAlertDialog(this, "قصد انجام چه کاری را دارید؟", "خروج از نرم\u200cافزار", (dialog, which) ->
        {
            finishAffinity();
            System.exit(0);
        }, "محاسبه مجدد", (dialog, which) ->
        {
            finish();
            startActivity(new Intent(ResultActivity.this, PayrollActivity.class));
        }, false);
    }
}