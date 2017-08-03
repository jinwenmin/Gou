package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.ActivityBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/7/11.
 */

public class ActivityAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<ActivityBean> ab;

    public ActivityAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        ab = new ArrayList<>();
    }

    public void getList(List<ActivityBean> a) {
        ab = a;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ab.size();
    }

    @Override
    public Object getItem(int position) {
        return ab.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_activity, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (ab.get(position).getSerial_number() != null) {
            viewHolder.serialNumber.setText(ab.get(position).getSerial_number());
        }
        if (ab.get(position).getUname() != null) {
            viewHolder.uname.setText(ab.get(position).getUname());
        }
        int stype = ab.get(position).getStype();
        if (stype == 22) {
            viewHolder.stype.setText("活动奖金");
        }
        if (stype == 29) {
            viewHolder.stype.setText("亏损佣金");
        }
        if (stype == 30) {
            viewHolder.stype.setText("消费佣金");
        }
        if (ab.get(position).getDate() != null) {
            viewHolder.date.setText(ab.get(position).getDate());
        }
        viewHolder.amount.setText(ab.get(position).getAmount() + "");
        if (ab.get(position).getDetial() != null) {
            viewHolder.detial.setText(ab.get(position).getDetial());
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.serial_number)
        TextView serialNumber;
        @BindView(R.id.uname)
        TextView uname;
        @BindView(R.id.stype)
        TextView stype;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.detial)
        TextView detial;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
