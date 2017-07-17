package com.example.king.gou.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.TeamUserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/7/17.
 */

public class TeamUserInfoAdapter extends BaseAdapter {
    List<TeamUserInfo> ts;
    Context context;
    LayoutInflater inflater;

    public TeamUserInfoAdapter(Context context) {
        this.context = context;
        ts = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void addList(List<TeamUserInfo> t) {
        ts = t;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ts.size();
    }

    @Override
    public Object getItem(int position) {
        return ts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_proxy, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.TeamUserId.setText(ts.get(position).getUid() + "");
        if (ts.get(position).getUtype() == 2) {
            viewHolder.TeamUserType.setText("代理用户");
        }
        if (ts.get(position).getUtype() == 3) {
            viewHolder.TeamUserType.setText("普通用户");
        }
        if (!"null".equals(ts.get(position).getLogin())) {
            viewHolder.TeamUserLogin.setText(ts.get(position).getLogin());
        } else {
            viewHolder.TeamUserLogin.setText("从未登陆");
        }
        if (!"".equals(ts.get(position).getName())) {
            viewHolder.TeamUserName.setText(ts.get(position).getName());
        }
        viewHolder.TeamUserRate.setText(ts.get(position).getRate() + "");
        if (ts.get(position).getStatus() == 0) {
            viewHolder.TeamUserStatus.setText("离线");
            viewHolder.TeamUserStatus.setTextColor(Color.parseColor("#000000"));
        }
        if (ts.get(position).getStatus() == 1) {
            viewHolder.TeamUserStatus.setText("在线");
            viewHolder.TeamUserStatus.setTextColor(Color.parseColor("#00FF00"));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.TeamUserId)
        TextView TeamUserId;
        @BindView(R.id.TeamUserType)
        TextView TeamUserType;
        @BindView(R.id.Linear1)
        LinearLayout Linear1;
        @BindView(R.id.TeamUserName)
        TextView TeamUserName;
        @BindView(R.id.TeamUserLogin)
        TextView TeamUserLogin;
        @BindView(R.id.Linear2)
        LinearLayout Linear2;
        @BindView(R.id.TeamUserRate)
        TextView TeamUserRate;
        @BindView(R.id.TeamUserStatus)
        TextView TeamUserStatus;
        @BindView(R.id.Linear3)
        LinearLayout Linear3;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
