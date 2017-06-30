package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.MapsIdAndValue;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ChatUsersAdapter extends BaseAdapter {
    Context context;
    List<MapsIdAndValue> ChatUsers;

    public ChatUsersAdapter(Context con) {
        context = con;
        ChatUsers = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return ChatUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return ChatUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chatuser, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (!"".equals(ChatUsers.get(position).getValues())) {
            viewHolder.TextCharUser.setText(ChatUsers.get(position).getValues());
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.TextCharUser)
        TextView TextCharUser;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void addListMaps(List<MapsIdAndValue> maps) {
        ChatUsers = maps;
        notifyDataSetChanged();
    }
}
