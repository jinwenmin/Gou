package com.example.king.gou.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.ZhuiHao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ZhuiHaoAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<ZhuiHao> zh;

    public ZhuiHaoAdapter(Context con) {
        context = con;
        inflater = LayoutInflater.from(con);
        zh = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return zh.size();
    }

    @Override
    public Object getItem(int i) {
        return zh.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_zhuihao, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.ZhuihaoQI.setText(zh.get(i).getPeriods() + "");
        viewHolder.ZHuihaoAmount.setText(zh.get(i).getAmount() + "");
        if (zh.get(i).getStatus() == 0) {
            viewHolder.ZhuiHaoZT.setText("未完成");
            viewHolder.ZhuiHaoZT.setTextColor(Color.parseColor("#666666"));
        }
        if (zh.get(i).getStatus() == 1) {
            viewHolder.ZhuiHaoZT.setText("已完成");
            viewHolder.ZhuiHaoZT.setTextColor(Color.parseColor("#69c361"));
        }
        if (zh.get(i).getGname() != null) {
            viewHolder.ZhuoHaoName.setText(zh.get(i).getGname());
        }

        viewHolder.ZhuiHaoPriAmount.setText(zh.get(i).getPrize_amount() + "");

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.ZhuihaoQI)
        TextView ZhuihaoQI;
        @BindView(R.id.ZHuihaoAmount)
        TextView ZHuihaoAmount;
        @BindView(R.id.ZhuiHaoZT)
        TextView ZhuiHaoZT;
        @BindView(R.id.ZhuoHaoName)
        TextView ZhuoHaoName;
        @BindView(R.id.ZhuiHaoPriAmount)
        TextView ZhuiHaoPriAmount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void getList(List<ZhuiHao> z) {
        zh = z;
        notifyDataSetChanged();
    }
}
