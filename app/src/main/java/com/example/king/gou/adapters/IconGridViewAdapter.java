package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.king.gou.R;
import com.example.king.gou.bean.Icons;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/8/9.
 */

public class IconGridViewAdapter extends BaseAdapter {
    List<Icons> ics;
    Context context;
    LayoutInflater inflater;

    public void addList(List<Icons> i) {
        ics = i;
        notifyDataSetChanged();
    }

    public IconGridViewAdapter(Context context) {
        this.context = context;
        ics = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ics.size();
    }

    @Override
    public Object getItem(int position) {
        return ics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_icon, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.icon.setBackgroundResource(ics.get(position).getIconImg());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
