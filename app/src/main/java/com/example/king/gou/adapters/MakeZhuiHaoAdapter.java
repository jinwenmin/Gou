package com.example.king.gou.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.ZhuiHaoCNum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/9/10.
 */

public class MakeZhuiHaoAdapter extends BaseAdapter {
    List<ZhuiHaoCNum> zh;
    Context context;
    LayoutInflater inflater;

    public MakeZhuiHaoAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        zh = new ArrayList<>();
    }

    public void addList(List<ZhuiHaoCNum> z) {
        zh = z;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return zh.size();
    }

    @Override
    public Object getItem(int position) {
        return zh.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_zhuihao_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ZhuiHaoCNum z = zh.get(position);
        if (!"".equals(z.getPeriod())) {
            viewHolder.ZhuiHaoPeriod.setText(z.getPeriod());
        }
        if (!"".equals(z.getBei())) {
            viewHolder.ZhuiHaoBei.setText(z.getBei() + "");
        }
        if (!"".equals(z.getAmounts())) {
            viewHolder.ZhuiHaoAmount.setText(z.getAmounts() + "");
        }
        viewHolder.ZhuiHaoCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("选择第", position + "个   " + z.isCheck()+"   "+b);
               /* if (b) {
                    z.setCheck(false);
                    viewHolder.ZhuiHaoCheck.setChecked(z.isCheck());

                } else {
                    z.setCheck(true);
                    Log.d("不选择第", position + "个   " + z.isCheck()+"   "+b);
                    viewHolder.ZhuiHaoCheck.setChecked(z.isCheck());
                }*/
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ZhuiHaoCheck)
        CheckBox ZhuiHaoCheck;
        @BindView(R.id.ZhuiHaoPeriod)
        TextView ZhuiHaoPeriod;
        @BindView(R.id.ZhuiHaoBei1)
        EditText ZhuiHaoBei;
        @BindView(R.id.ZhuiHaoAmount)
        TextView ZhuiHaoAmount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }
}
