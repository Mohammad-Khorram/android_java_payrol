package com.karafzar.payroll1402.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karafzar.payroll1402.R;
import com.karafzar.payroll1402.model.MonthModel;
import com.karafzar.payroll1402.util.DeviceUtil;

import java.util.List;

public class MonthAdapter extends  RecyclerView.Adapter<MonthAdapter.MyViewHolder>
{
    private Activity activity;
    private List<MonthModel> list;

    public MonthAdapter(Activity activity, List<MonthModel> list)
    {
        this.activity = activity;
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private RelativeLayout relativeLayout;
        private TextView textView;

        MyViewHolder(View view)
        {
            super(view);
            relativeLayout = view.findViewById(R.id.relativeLayout);
            textView = view.findViewById(R.id.textView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_month, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position)
    {
        MonthModel model = list.get(position);
        myViewHolder.textView.setText(model.getMonth());

        myViewHolder.relativeLayout.setOnClickListener(view ->
        {
            DeviceUtil.closeSoftInput(activity);
            Intent intent = new Intent();
            intent.putExtra("id", model.getId());
            intent.putExtra("month", model.getMonth());
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}