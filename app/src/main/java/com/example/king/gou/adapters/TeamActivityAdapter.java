package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.UserActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/7/19.
 */

public class TeamActivityAdapter extends BaseAdapter {
    List<UserActivity> uc;
    Context context;
    LayoutInflater inflater;

    public TeamActivityAdapter(Context context) {
        this.context = context;
        uc = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return uc.size();
    }

    @Override
    public Object getItem(int position) {
        return uc.get(position);
    }
public void addList(List<UserActivity> u){
    uc=u;
    notifyDataSetChanged();
}
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_teamac, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ACAid.setText(uc.get(position).getAid() + "");
        if (!"".equals(uc.get(position).getName())) {
            viewHolder.ACName.setText(uc.get(position).getName());
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ACAid)
        TextView ACAid;
        @BindView(R.id.ACName)
        TextView ACName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
