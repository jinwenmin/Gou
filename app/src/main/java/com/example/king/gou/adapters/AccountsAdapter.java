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
            if (acs.get(position).getUname() != null) {
                viewHolder.ChangeUser.setText(acs.get(position).getUname());
            }
            if (acs.get(position).getModel() != 0) {
                if (acs.get(position).getModel() == 1) {
                    viewHolder.ChangeModel.setText("元");
                }
                if (acs.get(position).getModel() == 2) {
                    viewHolder.ChangeModel.setText("角");
                }
                if (acs.get(position).getModel() == 3) {
                    viewHolder.ChangeModel.setText("分");
                }
                if (acs.get(position).getModel() == 4) {
                    viewHolder.ChangeModel.setText("厘");
                }
            }
            if (acs.get(position).getPeriod()!=null) {
                viewHolder.ChangePeriod.setText(acs.get(position).getPeriod());
            }
        }
        return convertView;
    }


    public void getList(List<AccountChange> ac) {
        acs = ac;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.ChangeUser)
        TextView ChangeUser;
        @BindView(R.id.ChangeModel)
        TextView ChangeModel;
        @BindView(R.id.textView9)
        TextView textView9;
        @BindView(R.id.Stype)
        TextView Stype;
        @BindView(R.id.ChangePeriod)
        TextView ChangePeriod;
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
}
