package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.AccountChange;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/7/8.
 */

public class AccountsAdapter extends BaseAdapter {
    List<AccountChange> acs;
    Context context;
    LayoutInflater inflater;

    public AccountsAdapter(Context co) {
        this.context = co;
        acs = new ArrayList<>();
        inflater = LayoutInflater.from(co);
    }

    @Override
    public int getCount() {
        return acs.size();
    }

    @Override
    public Object getItem(int position) {
        return acs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_accountchange, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (acs.get(position).getStype() != -1) {
            int stype = acs.get(position).getStype();
            if (stype == 1) {
                viewHolder.Stype.setText("加入游戏");
            }
            if (stype == 2) {
                viewHolder.Stype.setText("投注返点");
            }
            if (stype == 3) {
                viewHolder.Stype.setText("奖金派送");
            }
            if (stype == 4) {
                viewHolder.Stype.setText("追号扣款");
            }
            if (stype == 5) {
                viewHolder.Stype.setText("当期追号返款");
            }
            if (stype == 6) {
                viewHolder.Stype.setText("游戏扣款");
            }
            if (stype == 7) {
                viewHolder.Stype.setText("撤单返款");
            }
            if (stype == 8) {
                viewHolder.Stype.setText("撤销返点");
            }
            if (stype == 9) {
                viewHolder.Stype.setText("撤销派奖");
            }
            if (acs.get(position).getGname() != null) {
                viewHolder.Gname.setText(acs.get(position).getGname());
            }
            if (acs.get(position).getRname() != null) {
                viewHolder.Rname.setText(acs.get(position).getRname());
            }
            viewHolder.ChangeAmount.setText(acs.get(position).getAmount() + "");
            if (acs.get(position).getDate() != null) {
                viewHolder.ChangeDate.setText(acs.get(position).getDate());
            }
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.Stype)
        TextView Stype;
        @BindView(R.id.Gname)
        TextView Gname;
        @BindView(R.id.Rname)
        TextView Rname;
        @BindView(R.id.ChangeAmount)
        TextView ChangeAmount;
        @BindView(R.id.ChangeDate)
        TextView ChangeDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public void getList(List<AccountChange> ac){
        acs=ac;
        notifyDataSetChanged();
    }
}
