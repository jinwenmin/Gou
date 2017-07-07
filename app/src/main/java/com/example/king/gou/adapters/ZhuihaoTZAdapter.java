package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.TouZhu;
import com.example.king.gou.bean.ZhuiHao;
import com.example.king.gou.bean.ZhuiHaoDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/7/6.
 */

public class ZhuihaoTZAdapter extends BaseAdapter {
    List<ZhuiHao> zh;
    Context context;
    LayoutInflater inflater;
    public List<Boolean> mChecked;

    @Override
    public int getCount() {
        return zh.size();
    }

    public ZhuihaoTZAdapter(Context cs) {
        context = cs;
        zh = new ArrayList<>();
        inflater = LayoutInflater.from(cs);
        mChecked = new ArrayList<Boolean>();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_zhtz, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final int p = position;
        viewHolder.TouZhuId.setText(zh.get(position).getId() + "");
        viewHolder.TouZhuQi.setText(zh.get(position).getNumber());
        viewHolder.TouZhuBei.setText(zh.get(position).getPrize_num() + "");

        viewHolder.TouZhuCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mChecked.set(p, isChecked);
            }
        });


        if (zh.get(position).getStatus() == 0) {
            viewHolder.TouZhuStatus.setText("可选");
            viewHolder.TouZhuCheck.setChecked(zh.get(position).isBo());
        } else if (zh.get(position).getStatus() == 1) {
            viewHolder.TouZhuStatus.setText("不可选");
        }
        return convertView;
    }

    public void getList(List<ZhuiHao> ts) {
        zh = ts;

        for (int i = 0; i < ts.size(); i++) {
            mChecked.add(false);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.TouZhuCheck)
        CheckBox TouZhuCheck;
        @BindView(R.id.TouZhuId)
        TextView TouZhuId;
        @BindView(R.id.TouZhuQi)
        TextView TouZhuQi;
        @BindView(R.id.TouZhuBei)
        TextView TouZhuBei;
        @BindView(R.id.TouZhuStatus)
        TextView TouZhuStatus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void getCheck(boolean bs) {
        if (bs == true) {
            for (int i = 0; i < zh.size(); i++) {

            }
        }
        if (bs == false) {
            for (int i = 0; i < zh.size(); i++) {

            }
        }
    }
}
