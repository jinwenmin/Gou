package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.LotteryLoss;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/10.
 */

public class ProfitLossAdapter extends BaseAdapter {
    List<LotteryLoss> ls;
    Context context;
    LayoutInflater inflater;

    public ProfitLossAdapter(Context con) {
        this.context = con;
        ls = new ArrayList<>();
        inflater = LayoutInflater.from(con);
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    public void getList(List<LotteryLoss> l) {
        ls = l;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int i) {
        return ls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_lotteryloss, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.bettingAmount.setText(ls.get(i).getBetting_amount() + "");
        viewHolder.profitLoss.setText(ls.get(i).getProfit_loss() + "");
        viewHolder.rebateAmount.setText(ls.get(i).getRebate_amount() + "");
        viewHolder.winningAmount.setText(ls.get(i).getWinning_amount() + "");
        int type = ls.get(i).getType();
        if (type == 1) {
            viewHolder.bettingType.setText("彩票娱乐场");
        }
        if (type == 2) {
            viewHolder.bettingType.setText("香港六合彩");
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.betting_amount)
        TextView bettingAmount;
        @BindView(R.id.winning_amount)
        TextView winningAmount;
        @BindView(R.id.betting_type)
        TextView bettingType;
        @BindView(R.id.rebate_amount)
        TextView rebateAmount;
        @BindView(R.id.profit_loss)
        TextView profitLoss;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
