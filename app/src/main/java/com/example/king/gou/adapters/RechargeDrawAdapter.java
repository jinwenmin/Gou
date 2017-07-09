package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.CunQu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/7/9.
 */

public class RechargeDrawAdapter extends BaseAdapter {
    List<CunQu> cq;
    Context context;
    LayoutInflater inflater;

    public RechargeDrawAdapter(Context co) {
        this.context = co;
        cq = new ArrayList<>();
        inflater = LayoutInflater.from(co);
    }

    @Override
    public int getCount() {
        return cq.size();
    }

    @Override
    public Object getItem(int position) {
        return cq.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cunqukuan, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (cq.get(position).getSerial_number() != null) {
            viewHolder.serialNumber.setText(cq.get(position).getSerial_number());
        }
        if (cq.get(position).getUname() != null) {
            viewHolder.uname.setText(cq.get(position).getUname());
        }
        if (cq.get(position).getDate() != null) {
            viewHolder.date.setText(cq.get(position).getDate());
        }
        if (cq.get(position).getStype() != -1) {
            int st = cq.get(position).getStype();
            if (st == 10) {
                viewHolder.stype.setText("[+]上级充值");
            }
            if (st == 11) {
                viewHolder.stype.setText("[-]充值扣费");
            }
            if (st == 12) {
                viewHolder.stype.setText("[-]小额扣费");
            }
            if (st == 13) {
                viewHolder.stype.setText("特殊金额整理");
            }
            if (st == 14) {
                viewHolder.stype.setText("[+]理赔充值");
            }
            if (st == 15) {
                viewHolder.stype.setText("[-]管理员扣减");
            }
            if (st == 16) {
                viewHolder.stype.setText("[-]提款申请");
            }
            if (st == 17) {
                viewHolder.stype.setText("[+]提款失败");
            }
            if (st == 18) {
                viewHolder.stype.setText("[=]提款成功");
            }
            if (st == 19) {
                viewHolder.stype.setText("[+]在线充值");
            }
            if (st == 20) {
                viewHolder.stype.setText("[+]现金充值");
            }
            if (st == 21) {
                viewHolder.stype.setText("[+]充值手续费");
            }
            if (st == 22) {
                viewHolder.stype.setText("[+]活动奖金");
            }
            if (st == 26) {
                viewHolder.stype.setText("[+]支付宝充值");
            }
            if (st == 31) {
                viewHolder.stype.setText("[+]转账汇款");
            }
            if (st == 32) {
                viewHolder.stype.setText("[+]日工资");
            }
            if (st == 33) {
                viewHolder.stype.setText("[-]日工资扣费");
            }
            if (st == 34) {
                viewHolder.stype.setText("[+]日工资充值");
            }

        }
        viewHolder.income.setText(cq.get(position).getIncome() + "");
        viewHolder.expend.setText(cq.get(position).getExpend() + "");
        int status = cq.get(position).getStatus();
        if (status == 0) {
            viewHolder.status.setText("失败");
        }
        if (status == 1) {
            viewHolder.status.setText("处理中");
        }
        if (status == 2) {
            viewHolder.status.setText("成功");
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.serial_number)
        TextView serialNumber;
        @BindView(R.id.uname)
        TextView uname;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.stype)
        TextView stype;
        @BindView(R.id.income)
        TextView income;
        @BindView(R.id.expend)
        TextView expend;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public void getList(List<CunQu> c){
        cq=c;
        notifyDataSetChanged();
    }
}
