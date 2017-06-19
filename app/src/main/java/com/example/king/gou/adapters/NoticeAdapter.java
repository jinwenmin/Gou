package com.example.king.gou.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/6/19.
 */

public class NoticeAdapter extends BaseAdapter {
    List<String[]> Notices;
    private Context context;

    @Override
    public int getCount() {
        return Notices.size();
    }

    public NoticeAdapter(Context con) {
        context = con;
        Notices = new ArrayList<String[]>();
    }

    @Override
    public Object getItem(int position) {
        return Notices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Notices.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notice, parent, false);
            viewHolder = new ViewHolder(convertView);
            Log.d("数据一==",Notices.get(position)[1]);
            Log.d("数据二==",Notices.get(position)[2]);
            viewHolder.itemNoticeNameId.setText(Notices.get(position)[1]);
            viewHolder.itemNoticeTimeId.setText(Notices.get(position)[2]);
            convertView.setTag(viewHolder);
        }
        return convertView;
    }

    public void addNotice(List<String[]> strs) {
        Notices = strs;
        notifyDataSetChanged();

    }

    static class ViewHolder {
        @BindView(R.id.item_notice_nameId)
        TextView itemNoticeNameId;
        @BindView(R.id.item_notice_TimeId)
        TextView itemNoticeTimeId;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
