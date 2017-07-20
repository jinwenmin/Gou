package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.RecordList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/20.
 */

public class RecordListAdapter extends BaseAdapter {
    List<RecordList> rc;
    Context context;
    LayoutInflater inflater;

    public RecordListAdapter(Context context) {
        this.context = context;
        rc = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }
public void addList(List<RecordList> r){
    rc=r;
    notifyDataSetChanged();
}
    @Override

    public int getCount() {
        return rc.size();
    }

    @Override
    public Object getItem(int i) {
        return rc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_recordlist, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (!"".equals(rc.get(i).getGameName())) {
            viewHolder.gameName.setText(rc.get(i).getGameName());
        }
        if (!"".equals(rc.get(i).getPeriod())) {
            viewHolder.period.setText(rc.get(i).getPeriod());
        }
        if (!"".equals(rc.get(i).getDrawDate())) {
            viewHolder.drawDate.setText(rc.get(i).getDrawDate());
        }
        if (!"".equals(rc.get(i).getWinningNumber())) {
            viewHolder.winningNumber.setText(rc.get(i).getWinningNumber());
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.gameName)
        TextView gameName;
        @BindView(R.id.period)
        TextView period;
        @BindView(R.id.drawDate)
        TextView drawDate;
        @BindView(R.id.winningNumber)
        TextView winningNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
