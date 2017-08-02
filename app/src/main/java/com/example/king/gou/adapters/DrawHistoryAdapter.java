package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.king.gou.bean.RecordList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */

public class DrawHistoryAdapter extends BaseAdapter {
    List<RecordList> rcs;
    Context context;
    LayoutInflater inflater;

    public DrawHistoryAdapter(Context context) {
        this.context = context;
        rcs = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return rcs.size();
    }

    @Override
    public Object getItem(int i) {
        return rcs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
