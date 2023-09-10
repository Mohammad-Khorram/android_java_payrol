package com.karafzar.payroll1402.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.karafzar.payroll1402.R;
import com.karafzar.payroll1402.util.AnimationUtil;
import com.karafzar.payroll1402.util.DeviceUtil;
import com.karafzar.payroll1402.util.DigitUtil;
import com.karafzar.payroll1402.util.FontUtil;
import com.karafzar.payroll1402.util.SessionUtil;
import com.karafzar.payroll1402.util.VisualMessageUtil;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class PayrollActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private RelativeLayout relativeLayout, relChilds, relOther;
    private LinearLayout lnComputational, lnDate;
    private CheckBox chkWages;
    private TextView lblWagesDesc, lblChildsTitle, lblShift, lblComputationalTitle, lblMonth, lblDate, lblDateTitle, lblOtherTitle, lblInfoBSD;
    private TextInputLayout inpWages;
    private EditText txtWages, txtChilds, txtExtra, txtName;
    private CardView crdShift, crdMonth, crdDate, crdResume, crdCalculate;
    private View viewCalculate;
    private Button btnCalculateBSD, btnEditBSD;
    private ActionBarDrawerToggle toggle;
    private int dayOfMonth, selectedShift = -1, wages = 176942, currentWages, childs, shift, extra, insurance, totalWages;
    ArrayList<String> shiftTitle;
    ArrayList<Float> shiftValue;
    private DigitUtil digitUtil;
    private FontUtil fontUtil;
    private VisualMessageUtil visualMessageUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (savedInstanceState == null)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_payroll);

            InitializeComponent();
            DayOfMonth(new PersianCalendar().getPersianMonth());
            SettingVariable();
        }
    }

    private void InitializeComponent()
    {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        relativeLayout = findViewById(R.id.relativeLayout);
        toolbar = findViewById(R.id.toolbar);
        chkWages = findViewById(R.id.chkWages);
        lblWagesDesc = findViewById(R.id.lblWagesDesc);
        inpWages = findViewById(R.id.inpWages);
        txtWages = findViewById(R.id.txtWages);
        relChilds = findViewById(R.id.relChilds);
        txtChilds = findViewById(R.id.txtChilds);
        lblChildsTitle = findViewById(R.id.lblChildsTitle);
        lnComputational = findViewById(R.id.lnComputational);
        crdShift = findViewById(R.id.crdShift);
        lblShift = findViewById(R.id.lblShift);
        txtExtra = findViewById(R.id.txtExtra);
        lblComputationalTitle = findViewById(R.id.lblComputationalTitle);
        lnDate = findViewById(R.id.lnDate);
        crdMonth = findViewById(R.id.crdMonth);
        lblMonth = findViewById(R.id.lblMonth);
        crdDate = findViewById(R.id.crdDate);
        lblDate = findViewById(R.id.lblDate);
        lblDateTitle = findViewById(R.id.lblDateTitle);
        relOther = findViewById(R.id.relOther);
        txtName = findViewById(R.id.txtName);
        lblOtherTitle = findViewById(R.id.lblOtherTitle);
        crdResume = findViewById(R.id.crdResume);
        crdCalculate = findViewById(R.id.crdCalculate);
        viewCalculate = getLayoutInflater().inflate(R.layout.bsd_calculate, (ViewGroup) viewCalculate);
        lblInfoBSD = viewCalculate.findViewById(R.id.lblInfoBSD);
        btnCalculateBSD = viewCalculate.findViewById(R.id.btnCalculateBSD);
        btnEditBSD = viewCalculate.findViewById(R.id.btnEditBSD);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        shiftTitle = new ArrayList<>();
        shiftValue = new ArrayList<>();
        digitUtil = new DigitUtil();
        fontUtil = new FontUtil();
        visualMessageUtil = new VisualMessageUtil();

        chkWages.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            AnimationUtil.animationableViewGroup(relativeLayout);

            if (isChecked)
            {
                inpWages.setVisibility(View.GONE);
                lblWagesDesc.setVisibility(View.VISIBLE);
                DeviceUtil.closeSoftInput(this);
            } else
            {
                lblWagesDesc.setVisibility(View.GONE);
                inpWages.setVisibility(View.VISIBLE);
                txtWages.requestFocus();
                DeviceUtil.showSoftInput(this);
            }
        });

        txtWages.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.length() != 0)
                {
                    txtWages.removeTextChangedListener(this);

                    if (!txtWages.getText().toString().equals(""))
                    {
                        txtWages.setText(digitUtil.thousandDigitSeparator(txtWages.getText().toString().replaceAll(",", "")));
                        txtWages.setSelection(txtWages.length());
                    }

                    txtWages.addTextChangedListener(this);
                }
            }
        });

        txtWages.setOnEditorActionListener((textView, actionID, keyEvent) ->
        {
            if (actionID == EditorInfo.IME_ACTION_DONE && relChilds.getVisibility() == View.GONE)
                crdResume.performClick();

            return false;
        });

        txtChilds.setOnEditorActionListener((textView, actionID, keyEvent) ->
        {
            if (actionID == EditorInfo.IME_ACTION_DONE)
                crdResume.performClick();

            return false;
        });

        txtExtra.setOnEditorActionListener((textView, actionID, keyEvent) ->
        {
            if (actionID == EditorInfo.IME_ACTION_DONE)
                crdResume.performClick();

            return false;
        });

        txtName.setOnEditorActionListener((textView, actionID, keyEvent) ->
        {
            if (actionID == EditorInfo.IME_ACTION_DONE)
                crdCalculate.performClick();

            return false;
        });

        crdShift.setOnClickListener(this);
        crdMonth.setOnClickListener(this);
        crdDate.setOnClickListener(this);
        crdResume.setOnClickListener(this);
        crdCalculate.setOnClickListener(this);
        btnCalculateBSD.setOnClickListener(this);
        btnEditBSD.setOnClickListener(this);
    }

    private void DayOfMonth(int month)
    {
        switch (month)
        {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                dayOfMonth = 31;
                break;

            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                dayOfMonth = 30;
                break;

            case 12:
                dayOfMonth = 29;
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void SettingVariable()
    {
        System.setProperty("https.protocols", "TLSv1.1");
        DeviceUtil.customizeStatusBarColor(this);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerSlideAnimationEnabled(false);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        fontUtil.customizeDrawerFontTypeface(getApplicationContext(), navigationView);
        lblWagesDesc.setText("حداقل حقوق پایه در " + new PersianCalendar().getPersianMonthName() + "1402 برابر با " + new DecimalFormat().format(dayOfMonth * wages) + " تومان می\u200cباشد.");

        if (SessionUtil.getSharedPreferences(this).getBoolean("is_first_payroll", true))
            ShowHintActivity();
        else
        {
            crdResume.setVisibility(View.VISIBLE);
            chkWages.setClickable(true);
        }
    }

    private void ShowHintActivity()
    {
        if (SessionUtil.getSharedPreferences(this).getBoolean("is_first_payroll", true))
        {
            if (relChilds.getVisibility() == View.GONE)
            {
                TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.chkWages), "حقوق پایه", "حقوق پایه عبارت است از مجموع حقوق اصلی و مزایای ثابت پرداختی به تبع شغل.").tintTarget(false).outerCircleColor(R.color.colorAccent).outerCircleAlpha(1).titleTextColor(R.color.colorPrimary).titleTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).titleTextSize(19).descriptionTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).descriptionTextSize(15).dimColor(R.color.colorDark).targetCircleColor(R.color.colorPrimary),
                        new TapTargetView.Listener()
                        {
                            @Override
                            public void onOuterCircleClick(TapTargetView view)
                            {
                                tapTargetWagesPerformClick(view);
                            }

                            @Override
                            public void onTargetClick(TapTargetView view)
                            {
                                tapTargetWagesPerformClick(view);
                            }

                            @Override
                            public void onTargetCancel(TapTargetView view)
                            {
                                tapTargetWagesPerformClick(view);
                            }
                        });
            } else if (lnComputational.getVisibility() == View.GONE)
            {
                TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.txtChilds), "حق اولاد", "تعداد فرزندانی که کمتر از 18سال سن داشته باشند یا منحصراً به تحصیل اشتغال داشته باشند و یا در اثر بیماری و یا نقص عضو تأیید شده توسط کمیسیون\u200cهای پزشکی قادر به کار نباشد، مشروط بر آن\u200cکه بیمه شده حداقل 720روز سابقه داشته باشد.").tintTarget(false).outerCircleColor(R.color.colorAccent).outerCircleAlpha(1).titleTextColor(R.color.colorPrimary).titleTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).titleTextSize(19).descriptionTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).descriptionTextSize(15).dimColor(R.color.colorDark).targetCircleColor(R.color.colorPrimary),
                        new TapTargetView.Listener()
                        {
                            @Override
                            public void onOuterCircleClick(TapTargetView view)
                            {
                                tapTargetChildsPerformClick(view);
                            }

                            @Override
                            public void onTargetClick(TapTargetView view)
                            {
                                tapTargetChildsPerformClick(view);
                            }

                            @Override
                            public void onTargetCancel(TapTargetView view)
                            {
                                tapTargetChildsPerformClick(view);
                            }
                        });
            } else if (lnDate.getVisibility() == View.GONE)
            {
                if (lblShift.getText().equals("انتخاب نشده"))
                {
                    TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.crdShift), "شیفت کاری", "طبق ماده 55 قانون کار، کارگرانی شیفت کار محسوب می\u200cشوند که کارشان در طول ماه گردش داشته باشد.\n(به نحوی که در نوبت\u200cهای صبح و عصر و شب واقع شود)").tintTarget(false).outerCircleColor(R.color.colorAccent).outerCircleAlpha(1).titleTextColor(R.color.colorPrimary).titleTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).titleTextSize(19).descriptionTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).descriptionTextSize(15).dimColor(R.color.colorDark).targetCircleColor(R.color.colorPrimary),
                            new TapTargetView.Listener()
                            {
                                @Override
                                public void onOuterCircleClick(TapTargetView view)
                                {
                                    tapTargetShiftPerformClick(view);
                                }

                                @Override
                                public void onTargetClick(TapTargetView view)
                                {
                                    tapTargetShiftPerformClick(view);
                                }

                                @Override
                                public void onTargetCancel(TapTargetView view)
                                {
                                    tapTargetShiftPerformClick(view);
                                }
                            });
                } else
                {
                    TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.txtExtra), "اضافه کاری", "ارجاع کار اضافه بر ساعات کار عادی (44ساعت در هفته برای کارگران غیر شیفت کار و 176ساعت در 4هفته متوالی برای کارگران شیفت کار) اضافه کاری می\u200cباشد. (اختیاری)").tintTarget(false).outerCircleColor(R.color.colorAccent).outerCircleAlpha(1).titleTextColor(R.color.colorPrimary).titleTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).titleTextSize(19).descriptionTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).descriptionTextSize(15).dimColor(R.color.colorDark).targetCircleColor(R.color.colorPrimary),
                            new TapTargetView.Listener()
                            {
                                @Override
                                public void onOuterCircleClick(TapTargetView view)
                                {
                                    tapTargetExtraPerformClick(view);
                                }

                                @Override
                                public void onTargetClick(TapTargetView view)
                                {
                                    tapTargetExtraPerformClick(view);
                                }

                                @Override
                                public void onTargetCancel(TapTargetView view)
                                {
                                    tapTargetExtraPerformClick(view);
                                }
                            });
                }
            } else if (relOther.getVisibility() == View.GONE)
            {
                if (crdDate.getVisibility() == View.GONE)
                {
                    TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.crdMonth), "ماه مورد محاسبه", "تعیین ماه موردنظر جهت محاسبۀ فیش حقوقی.").tintTarget(false).outerCircleColor(R.color.colorAccent).outerCircleAlpha(1).titleTextColor(R.color.colorPrimary).titleTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).titleTextSize(19).descriptionTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).descriptionTextSize(15).dimColor(R.color.colorDark).targetCircleColor(R.color.colorPrimary),
                            new TapTargetView.Listener()
                            {
                                @Override
                                public void onOuterCircleClick(TapTargetView view)
                                {
                                    tapTargetMonthPerformClick(view);
                                }

                                @Override
                                public void onTargetClick(TapTargetView view)
                                {
                                    tapTargetMonthPerformClick(view);
                                }

                                @Override
                                public void onTargetCancel(TapTargetView view)
                                {
                                    tapTargetMonthPerformClick(view);
                                }
                            });
                } else
                {
                    TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.crdDate), "تاریخ محاسبه", "جهت درج تاریخ محاسبه در فیش حقوقی. (اختیاری)").tintTarget(false).outerCircleColor(R.color.colorAccent).outerCircleAlpha(1).titleTextColor(R.color.colorPrimary).titleTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).titleTextSize(19).descriptionTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).descriptionTextSize(15).dimColor(R.color.colorDark).targetCircleColor(R.color.colorPrimary),
                            new TapTargetView.Listener()
                            {
                                @Override
                                public void onOuterCircleClick(TapTargetView view)
                                {
                                    tapTargetDatePerformClick(view);
                                }

                                @Override
                                public void onTargetClick(TapTargetView view)
                                {
                                    tapTargetDatePerformClick(view);
                                }

                                @Override
                                public void onTargetCancel(TapTargetView view)
                                {
                                    tapTargetDatePerformClick(view);
                                }
                            });
                }
            } else if (relOther.getVisibility() == View.VISIBLE)
            {
                TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.txtName), "نام و نام خانوادگی", "جهت درج نام و نام خانوادگی فرد در فیش حقوقی. (اختیاری)").tintTarget(false).outerCircleColor(R.color.colorAccent).outerCircleAlpha(1).titleTextColor(R.color.colorPrimary).titleTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).titleTextSize(19).descriptionTypeface(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir)).descriptionTextSize(15).dimColor(R.color.colorDark).targetCircleColor(R.color.colorPrimary),
                        new TapTargetView.Listener()
                        {
                            @Override
                            public void onOuterCircleClick(TapTargetView view)
                            {
                                tapTargetOtherPerformClick(view);
                            }

                            @Override
                            public void onTargetClick(TapTargetView view)
                            {
                                tapTargetOtherPerformClick(view);
                            }

                            @Override
                            public void onTargetCancel(TapTargetView view)
                            {
                                tapTargetOtherPerformClick(view);
                            }
                        });
            }
        }
    }

    private void tapTargetWagesPerformClick(TapTargetView view)
    {
        view.dismiss(false);

        new Handler().postDelayed(() ->
        {
            chkWages.setChecked(false);
            DeviceUtil.closeSoftInput(this);

            new Handler().postDelayed(() ->
            {
                chkWages.setChecked(true);
                crdResume.setVisibility(View.VISIBLE);
                chkWages.setClickable(true);
            }, 1); //1500
        }, 7); //750
    }

    private void tapTargetChildsPerformClick(TapTargetView view)
    {
        view.dismiss(false);
        DeviceUtil.showSoftInput(this);
        txtChilds.requestFocus();
    }

    private void tapTargetShiftPerformClick(TapTargetView view)
    {
        view.dismiss(false);
        ShowShift();
    }

    private void tapTargetExtraPerformClick(TapTargetView view)
    {
        view.dismiss(false);
        txtExtra.requestFocus();
        DeviceUtil.showSoftInput(this);
    }

    private void tapTargetMonthPerformClick(TapTargetView view)
    {
        view.dismiss(false);
        startActivityForResult(new Intent(PayrollActivity.this, MonthActivity.class), 1001);
    }

    private void tapTargetDatePerformClick(TapTargetView view)
    {
        view.dismiss(false);
        ShowDatePicker();
    }

    private void tapTargetOtherPerformClick(TapTargetView view)
    {
        AnimationUtil.animationableViewGroup(drawerLayout);
        view.dismiss(false);
        txtName.requestFocus();
        crdCalculate.setVisibility(View.VISIBLE);
        SessionUtil.getSharedPreferences(this).edit().putBoolean("is_first_payroll", false).apply();
        DeviceUtil.showSoftInput(this);
    }

    private void ShowShift()
    {
        shiftTitle.clear();
        shiftValue.clear();

        shiftTitle.add("شیفت کاری ندارم");
        shiftValue.add(0f);

        shiftTitle.add("صبح، عصر");
        shiftValue.add(10f);

        shiftTitle.add("صبح، عصر، شب");
        shiftValue.add(15f);

        shiftTitle.add("صبح، شب");
        shiftValue.add(22.5f);

        shiftTitle.add("شب");
        shiftValue.add(22.5f);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setAdapter(new VisualMessageUtil.dialogMenu(this, shiftTitle, selectedShift), (dialog, which) ->
                {
                    selectedShift = which;
                    lblShift.setText(shiftTitle.get(which));

                    if (SessionUtil.getSharedPreferences(this).getBoolean("is_first_payroll", true))
                        ShowHintActivity();
                    else
                    {
                        if (txtExtra.length() == 0)
                        {
                            txtExtra.requestFocus();
                            DeviceUtil.showSoftInput(this);
                        }
                    }
                }).show();

        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_roundedcorner_primary);
        alertDialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @SuppressLint("SetTextI18n")
    private void ShowDatePicker()
    {
        PersianDatePickerDialog persianDatePickerDialog = new PersianDatePickerDialog(this)
                .setMinYear(1402)
                .setShowInBottomSheet(true)
                .setNegativeButton("انصراف")
                .setPositiveButtonString("انتخاب تاریخ")
                .setTypeFace(FontUtil.getFontTypeface(getApplicationContext(), R.font.vazir))
                .setMaxYear(1402)
                .setTitleColor(getResources().getColor(R.color.color100))
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setListener(new Listener()
                {
                    @Override
                    public void onDismissed()
                    {
                    }

                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar)
                    {
                        lblDate.setText(persianCalendar.getPersianYear() + "/" + String.format(Locale.ENGLISH, "%02d", persianCalendar.getPersianMonth()) + "/" + String.format(Locale.ENGLISH, "%02d", persianCalendar.getPersianDay()));
                        AnimationUtil.animationableViewGroup(drawerLayout);
                        crdResume.setVisibility(View.GONE);
                        relOther.setVisibility(View.VISIBLE);
                        lblOtherTitle.setVisibility(View.VISIBLE);

                        if (SessionUtil.getSharedPreferences(PayrollActivity.this).getBoolean("is_first_payroll", true))
                            ShowHintActivity();
                        else
                        {
                            crdCalculate.setVisibility(View.VISIBLE);

                            if (txtName.length() == 0)
                            {
                                txtName.requestFocus();
                                DeviceUtil.showSoftInput(PayrollActivity.this);
                            }
                        }
                    }
                });

        persianDatePickerDialog.show();
    }

    private boolean RequirementData()
    {
        if (!chkWages.isChecked())
        {
            if (txtWages.length() > 6)
                return txtChilds.length() != 0;
            else
                return false;
        } else
            return txtChilds.length() != 0;
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.crdShift:
                ShowShift();
                break;

            case R.id.crdMonth:
                startActivityForResult(new Intent(PayrollActivity.this, MonthActivity.class), 1001);
                break;

            case R.id.crdDate:
                ShowDatePicker();
                break;

            case R.id.crdResume:
                AnimationUtil.animationableViewGroup(relativeLayout);

                if (relChilds.getVisibility() == View.GONE)
                {
                    if (!chkWages.isChecked())
                    {
                        if (txtWages.length() > 6)
                        {
                            DeviceUtil.closeSoftInput(this);
                            relChilds.setVisibility(View.VISIBLE);
                            lblChildsTitle.setVisibility(View.VISIBLE);

                            if (SessionUtil.getSharedPreferences(this).getBoolean("is_first_payroll", true))
                                ShowHintActivity();
                            else
                            {
                                txtChilds.requestFocus();
                                DeviceUtil.showSoftInput(this);
                            }
                        } else
                            Toast.makeText(getApplicationContext(), "میزان حقوق پایه را مشخص کنید", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        relChilds.setVisibility(View.VISIBLE);
                        lblChildsTitle.setVisibility(View.VISIBLE);

                        if (SessionUtil.getSharedPreferences(this).getBoolean("is_first_payroll", true))
                            ShowHintActivity();
                        else
                        {
                            txtChilds.requestFocus();
                            DeviceUtil.showSoftInput(this);
                        }
                    }
                } else if (lnComputational.getVisibility() == View.GONE)
                {
                    if (txtChilds.length() != 0)
                    {
                        DeviceUtil.closeSoftInput(this);
                        lnComputational.setVisibility(View.VISIBLE);
                        lblComputationalTitle.setVisibility(View.VISIBLE);

                        if (SessionUtil.getSharedPreferences(this).getBoolean("is_first_payroll", true))
                            ShowHintActivity();
                        else
                            VisualMessageUtil.showAlertDialog(this, "شیفت کاری خود را مشخص کنید", "انتخاب شیفت کاری", (dialog, which) -> ShowShift(), null, null, false);
                    } else
                    {
                        Toast.makeText(getApplicationContext(), "تعداد فرزندان را مشخص کنید", Toast.LENGTH_SHORT).show();
                        DeviceUtil.showSoftInput(this);
                        txtChilds.requestFocus();
                    }
                } else if (lnDate.getVisibility() == View.GONE)
                {
                    if (!lblShift.getText().equals("انتخاب نشده"))
                    {
                        DeviceUtil.closeSoftInput(this);
                        lnDate.setVisibility(View.VISIBLE);
                        lblDateTitle.setVisibility(View.VISIBLE);

                        if (SessionUtil.getSharedPreferences(this).getBoolean("is_first_payroll", true))
                            ShowHintActivity();
                        else
                            VisualMessageUtil.showAlertDialog(this, "ماه موردنظر جهت محاسبه فیش حقوقی را مشخص کنید", "انتخاب ماه", (dialog, which) -> startActivityForResult(new Intent(PayrollActivity.this, MonthActivity.class), 1001), null, null, false);
                    } else
                    {
                        ShowShift();
                        Toast.makeText(getApplicationContext(), "شیفت کاری را مشخص کنید", Toast.LENGTH_SHORT).show();
                    }
                } else if (relOther.getVisibility() == View.GONE)
                {
                    if (!lblMonth.getText().equals("انتخاب نشده"))
                    {
                        crdDate.setVisibility(View.VISIBLE);
                    } else
                    {
                        Toast.makeText(getApplicationContext(), "ماه موردنظر را جهت محاسبۀ فیش حقوقی مشخص کنید", Toast.LENGTH_SHORT).show();
                        startActivityForResult(new Intent(PayrollActivity.this, MonthActivity.class), 1001);
                    }
                }
                break;

            case R.id.crdCalculate:
                if (RequirementData())
                {
                    if (chkWages.isChecked())
                        currentWages = dayOfMonth * wages;
                    else
                        currentWages = Integer.parseInt(txtWages.getText().toString().replace(",", "").replace("٬", ""));

                    childs = currentWages * Integer.parseInt(txtChilds.getText().toString()) / 10;

                    if (txtExtra.length() != 0)
                        extra = (int) (currentWages * 1.4 / 220 * Double.parseDouble(txtExtra.getText().toString()));

                    if (chkWages.isChecked())
                        this.shift = (int) ((dayOfMonth * wages) * shiftValue.get(selectedShift) / 100);
                    else
                        this.shift = (int) (Integer.parseInt(txtWages.getText().toString().replace(",", "").replace("٬", "")) * shiftValue.get(selectedShift) / 100);

                    insurance = (int) ((currentWages + shift + extra) * 0.07);
                    totalWages = (currentWages + 900000 + 1100000 + childs + shift + extra) - insurance;
                    //900,000 haqe_maskan
                    //1,100,000 bone_kargari

                    if (txtName.length() == 0)
                        lblInfoBSD.setText("حقوق پایه: " + new DecimalFormat().format(currentWages) + " تومان\nحق اولاد: " + new DecimalFormat().format(childs) + " تومان\nحق شیفت: " + new DecimalFormat().format(shift) + " تومان\nمیزان اضافه کاری: " + new DecimalFormat().format(extra) + " تومان\nماه مورد محاسبه: " + lblMonth.getText() + "\nتاریخ محاسبه: " + lblDate.getText());
                    else
                        lblInfoBSD.setText("حقوق پایه: " + new DecimalFormat().format(currentWages) + " تومان\nحق اولاد: " + new DecimalFormat().format(childs) + " تومان\nحق شیفت: " + new DecimalFormat().format(shift) + " تومان\nمیزان اضافه کاری: " + new DecimalFormat().format(extra) + " تومان\nماه مورد محاسبه: " + lblMonth.getText() + "\nتاریخ محاسبه: " + lblDate.getText() + "\nنام و نام خانوادگی: " + txtName.getText());

                    visualMessageUtil.InitializeBottomSheetDialog(this, viewCalculate);
                } else
                {
                    String errorMessage = "";

                    if (!chkWages.isChecked() && txtWages.length() < 7)
                        errorMessage += "\n● تعیین میزان حقوق پایه الزامی می\u200cباشد.";

                    if (txtChilds.length() == 0)
                        errorMessage += "\n● تعیین تعداد فرزندان الزامی می\u200cباشد.";

                    VisualMessageUtil.showAlertDialog(this, ("اطلاعات ناقص است!\n\n" + errorMessage).replaceAll("\n\n\n", "\n\n"), "متوجه شدم", null, null, null, true);
                }
                break;

            case R.id.btnCalculateBSD:
                finish();
                Intent intent = new Intent(PayrollActivity.this, ResultActivity.class);
                intent.putExtra("name", txtName.getText().toString());
                intent.putExtra("date", lblDate.getText().toString());
                intent.putExtra("month", lblMonth.getText().toString());
                intent.putExtra("wages", currentWages);
                intent.putExtra("childs", childs);
                intent.putExtra("shift", shift);
                intent.putExtra("extra", extra);
                intent.putExtra("insurance", insurance);
                intent.putExtra("totalWages", totalWages);
                startActivity(intent);
                break;

            case R.id.btnEditBSD:
                visualMessageUtil.CloseBottomSheetDialog();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        try
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        } catch (Exception ignored)
        {
        }

        switch (menuItem.getItemId())
        {
            case R.id.navChilds:
                Intent childsIntent = new Intent(PayrollActivity.this, ChildActivity.class);
                childsIntent.putExtra("totalWages", (dayOfMonth * wages));
                startActivity(childsIntent);
                break;

            case R.id.navShift:
                Intent shiftIntent = new Intent(PayrollActivity.this, ShiftActivity.class);
                shiftIntent.putExtra("totalWages", (dayOfMonth * wages));
                startActivity(shiftIntent);
                break;

            case R.id.navNight:
                Intent nightIntent = new Intent(PayrollActivity.this, NightActivity.class);
                nightIntent.putExtra("totalWages", (dayOfMonth * wages));
                startActivity(nightIntent);
                break;

            case R.id.navExtra:
                Intent extraIntent = new Intent(PayrollActivity.this, ExtraActivity.class);
                extraIntent.putExtra("totalWages", (dayOfMonth * wages));
                startActivity(extraIntent);
                break;
        }

        return false;
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == 1001) // Monthlist
            {
                AnimationUtil.animationableViewGroup(drawerLayout);
                DayOfMonth(Objects.requireNonNull(data).getIntExtra("id", -1) + 1);
                lblWagesDesc.setText("حداقل حقوق پایه در " + data.getStringExtra("month") + "1402 برابر با " + new DecimalFormat().format(dayOfMonth * wages) + " تومان می\u200cباشد.");
                lblMonth.setText(data.getStringExtra("month"));
                crdDate.setVisibility(View.VISIBLE);

                if (SessionUtil.getSharedPreferences(this).getBoolean("is_first_payroll", true))
                    ShowHintActivity();
                else
                {
                    if (lblDate.getText().toString().equals("تعیین نشده"))
                        VisualMessageUtil.showAlertDialog(this, "تاریخ محاسبه فیش حقوقی را مشخص کنید", "انتخاب تاریخ", (dialog, which) -> ShowDatePicker(), null, null, false);
                }
            } else
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e)
    {
        if (keyCode == KeyEvent.KEYCODE_MENU)
        {
            try
            {
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            } catch (Exception ignored)
            {
            }
        }

        return super.onKeyDown(keyCode, e);
    }

    @Override
    public void onBackPressed()
    {
        try
        {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else
                super.onBackPressed();
        } catch (Exception ignored)
        {
        }
    }
}