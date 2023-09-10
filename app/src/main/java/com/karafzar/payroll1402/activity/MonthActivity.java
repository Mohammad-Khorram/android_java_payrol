package com.karafzar.payroll1402.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.karafzar.payroll1402.R;
import com.karafzar.payroll1402.adapter.MonthAdapter;
import com.karafzar.payroll1402.model.MonthModel;
import com.karafzar.payroll1402.util.DeviceUtil;

import java.util.ArrayList;
import java.util.List;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class MonthActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TextView lblSubtitle;
    private RecyclerView recyclerView;
    private List<MonthModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        InitializeComponent();
        SettingVariable();
        MonthList();
    }

    private void InitializeComponent()
    {
        toolbar = findViewById(R.id.toolbar);
        lblSubtitle = findViewById(R.id.lblSubtitle);
        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @SuppressLint("SetTextI18n")
    private void SettingVariable()
    {
        DeviceUtil.customizeStatusBarColor(this);
        lblSubtitle.setText("ماه فعلی: " + new PersianCalendar().getPersianMonthName());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_right_light_24dp);
    }

    private void MonthList()
    {
        List<MonthModel> model = new ArrayList<>();
        String[] months = new String[]{"فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};

        for (int i = 0; i < 12; i++)
        {
            model.add(new MonthModel());
            model.get(i).setId(i);
            model.get(i).setMonth(months[i]);
        }

        list.addAll(model);
        MonthAdapter adapter = new MonthAdapter(MonthActivity.this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
}