package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.VIPAccountChange;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/8/3.
 */

public class VIPAccountChangeAdapter extends BaseAdapter {
    List<VIPAccountChange> vc;
    Context context;
    LayoutInflater inflater;

    public VIPAccountChangeAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        vc = new ArrayList<>();
    }

    public void getList(List<VIPAccountChange> v) {
        vc = v;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return vc.size();
    }

    @Override
    public Object getItem(int position) {
        return vc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_vipaccchange, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        VIPAccountChange v = vc.get(position);
        if (v.getSerial_number() != null) {
            viewHolder.AccountChangeOrder.setText(v.getSerial_number());
        }
        if (v.getDraw_period() != null) {
            viewHolder.AccountChangePeriod.setText(v.getDraw_period());
        }
        if (v.getTime() != null) {
            viewHolder.AccountChangeDate.setText(v.getTime());
        }
        if (v.getGame() != null || !"".equals(v.getGame())) {
            viewHolder.AccountChangeGame.setText(v.getGame());
        }
        int type = v.getType();
        if (type == 0) {
            viewHolder.AccountChangeType.setText("所有类型");
        }
        if (type == 1) {
            viewHolder.AccountChangeType.setText("加入游戏");
        }
        if (type == 2) {
            viewHolder.AccountChangeType.setText("投注返点");
        }
        if (type == 3) {
            viewHolder.AccountChangeType.setText("奖金派送");
        }
        if (type == 4) {
            viewHolder.AccountChangeType.setText("追号扣款");
        }
        if (type == 5) {
            viewHolder.AccountChangeType.setText("当期追号返款");
        }
        if (type == 6) {
            viewHolder.AccountChangeType.setText("游戏扣款");
        }
        if (type == 7) {
            viewHolder.AccountChangeType.setText("撤单返款");
        }
        if (type == 8) {
            viewHolder.AccountChangeType.setText("撤销返点");
        }
        if (type == 9) {
            viewHolder.AccountChangeType.setText("撤销派奖");
        }
        if (type == 10) {
            viewHolder.AccountChangeType.setText("上级充值");
        }
        if (type == 11) {
            viewHolder.AccountChangeType.setText("充值扣费");
        }
        if (type == 14) {
            viewHolder.AccountChangeType.setText("理赔充值");
        }
        if (type == 16) {
            viewHolder.AccountChangeType.setText("提款申请");
        }
        if (type == 17) {
            viewHolder.AccountChangeType.setText("提款失败");
        }
        if (type == 18) {
            viewHolder.AccountChangeType.setText("提款成功");
        }
        if (type == 19) {
            viewHolder.AccountChangeType.setText("在线充值");
        }
        if (type == 20) {
            viewHolder.AccountChangeType.setText("现金充值");
        }
        if (type == 21) {
            viewHolder.AccountChangeType.setText("充值手续费");
        }
        if (type == 22) {
            viewHolder.AccountChangeType.setText("促销充值");
        }
        if (type == 26) {
            viewHolder.AccountChangeType.setText("支付宝充值");
        }
        if (type == 31) {
            viewHolder.AccountChangeType.setText("转账汇款");
        }
        int price_unit = v.getPrice_unit();
        if (price_unit == 1) {
            viewHolder.AccountChangeModel.setText("元");
        }
        if (price_unit == 2) {
            viewHolder.AccountChangeModel.setText("角");
        }
        if (price_unit == 3) {
            viewHolder.AccountChangeModel.setText("分");
        }
        if (price_unit == 4) {
            viewHolder.AccountChangeModel.setText("厘");
        }
        viewHolder.ChangeAmount.setText(v.getAmount() + "");
        viewHolder.MyAmount.setText(v.getBalance() + "");
        viewHolder.Detail.setText(v.getRemark());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.AccountChangeOrder)
        TextView AccountChangeOrder;
        @BindView(R.id.AccountChangePeriod)
        TextView AccountChangePeriod;
        @BindView(R.id.AccountChangeDate)
        TextView AccountChangeDate;
        @BindView(R.id.AccountChangeGame)
        TextView AccountChangeGame;
        @BindView(R.id.AccountChangeType)
        TextView AccountChangeType;
        @BindView(R.id.AccountChangeModel)
        TextView AccountChangeModel;
        @BindView(R.id.ChangeAmount)
        TextView ChangeAmount;
        @BindView(R.id.MyAmount)
        TextView MyAmount;
        @BindView(R.id.Detail)
        TextView Detail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
