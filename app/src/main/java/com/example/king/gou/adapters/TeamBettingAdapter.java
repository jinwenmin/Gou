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
import com.example.king.gou.bean.UserTeamBetting;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/7/15.
 */

public class TeamBettingAdapter extends BaseAdapter {
    List<UserTeamBetting> us;
    Context context;
    LayoutInflater inflater;

    public TeamBettingAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        us = new ArrayList<>();
    }

    public void addList(List<UserTeamBetting> u) {
        us = u;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return us.size();
    }

    @Override
    public Object getItem(int position) {
        return us.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lottery_betting, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        UserTeamBetting u = us.get(position);
        if (!"".equals(u.getGname())) {
            viewHolder.lotteryType.setText(u.getGname());
        }

        if (!"".equals(u.getRname())) {
            viewHolder.lotteryType2.setText(u.getRname());
        }
        if (!"".equals(u.getPeriod())) {
            viewHolder.lotteryDate.setText(u.getDate());
        }
        if (!"".equals(u.getUname())) {
            viewHolder.username.setText(u.getUname());
        }
        viewHolder.TouZhuMoney.setText(u.getAmount() + "");
        viewHolder.ZhongJiangMoney.setText(u.getPrize() + "");
        if (!"".equals(u.getWinning_number())) {
            viewHolder.WinNum.setText(u.getWinning_number());
        }


        if (u.getStatus() == -1) {
            viewHolder.LotteryStatus.setText("未开奖");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#0000ff"));
        }
        if (u.getStatus() == 0) {
            viewHolder.LotteryStatus.setText("未中奖");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#00802f"));
        }
        if (u.getStatus() == 1) {
            viewHolder.LotteryStatus.setText("已派奖");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#ff0000"));
        }
        if (u.getStatus() == 2) {
            viewHolder.LotteryStatus.setText("本人撤单");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#666666"));
        }
        if (u.getStatus() == 3) {
            viewHolder.LotteryStatus.setText("管理员撤单");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#666666"));
        }
        if (u.getStatus() == 4) {
            viewHolder.LotteryStatus.setText("已过期");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#666666"));
        }

        if (u.getStatus() == 6) {
            viewHolder.LotteryStatus.setText("平台撤单");
            viewHolder.LotteryStatus.setTextColor(Color.parseColor("#666666"));
        }


        return convertView;
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
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.Lottery_status)
        TextView LotteryStatus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
