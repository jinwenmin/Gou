package com.example.king.gou.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.TouZhu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/5.
 */

public class TouZhuAdapter extends BaseAdapter {
    List<TouZhu> ts;
    Context context;

    public TouZhuAdapter(Context cx) {
        this.ts = new ArrayList<>();
        context = cx;
    }

    @Override
    public int getCount() {
        return ts.size();
    }

    @Override
    public Object getItem(int i) {
        return ts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_gamejl, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (ts.get(i).getName() != null) {
            viewHolder.lotteryType.setText(ts.get(i).getName());
        }
        if (ts.get(i).getPeriod() != null) {
            viewHolder.lotteryDate.setText(ts.get(i).getPeriod() + "期");
        }
        if (ts.get(i).getRname() != null) {
            viewHolder.lotteryType2.setText(ts.get(i).getRname());
        }
        viewHolder.TouZhuMoney.setText(ts.get(i).getAmount() + "元");
        viewHolder.ZhongJiangMoney.setText(ts.get(i).getPrize() + "元");
        if ("null".equals(ts.get(i).getWinning_numbers())) {
            viewHolder.WinNum.setText("");
        } else if (!"null".equals(ts.get(i).getWinning_numbers())) {
            viewHolder.WinNum.setText(ts.get(i).getWinning_numbers());
        }
        if (ts.get(i).getStatus() == 0) {
            viewHolder.LotteryStatus.setText("未购买");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#666666"));
        }
        if (ts.get(i).getStatus() == 1) {
            viewHolder.LotteryStatus.setText("未开奖");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#0000ff"));
        }
        if (ts.get(i).getStatus() == 2) {
            viewHolder.LotteryStatus.setText("本人撤单");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#666666"));
        }
        if (ts.get(i).getStatus() == 3) {
            viewHolder.LotteryStatus.setText("管理员撤单");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#666666"));
        }
        if (ts.get(i).getStatus() == 4) {
            viewHolder.LotteryStatus.setText("已过期");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#666666"));
        }
        if (ts.get(i).getStatus() == 5) {
            viewHolder.LotteryStatus.setText("未中奖");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#00802f"));
        }
        if (ts.get(i).getStatus() == 6) {
            viewHolder.LotteryStatus.setText("平台撤单");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#666666"));
        }
        if (ts.get(i).getStatus() == 7) {
            viewHolder.LotteryStatus.setText("已派奖");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#ff0000"));
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.lottery_type)
        TextView lotteryType;
        @BindView(R.id.lottery_date)
        TextView lotteryDate;
        @BindView(R.id.lottery_type2)
        TextView lotteryType2;
        @BindView(R.id.TouZhuMoney)
        TextView TouZhuMoney;
        @BindView(R.id.ZhongJiangMoney)
        TextView ZhongJiangMoney;
        @BindView(R.id.lottery_type3)
        LinearLayout lotteryType3;
        @BindView(R.id.WinNum)
        TextView WinNum;
        @BindView(R.id.Lottery_status)
        TextView LotteryStatus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void addListView(List<TouZhu> t) {
        ts = t;
        notifyDataSetChanged();
    }
}
