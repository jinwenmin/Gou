package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.ShareData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/4.
 */

public class ShareDataAdapter extends BaseAdapter {
    List<ShareData> s;
    Context context;
    LayoutInflater inflater;

    public ShareDataAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        s = new ArrayList<>();

    }

    public void getList(List<ShareData> ss) {
        s = ss;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return s.size();
    }

    @Override
    public Object getItem(int i) {
        return s.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_sharedata, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ShareData ss = s.get(i);
        if (ss.getName() != null) {
            viewHolder.Gname.setText(ss.getName());
        }
        viewHolder.offset.setText(ss.getOffset() + "");
        viewHolder.rate.setText(ss.getGrate() + "");
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.Gname)
        TextView Gname;
        @BindView(R.id.rate)
        TextView rate;
        @BindView(R.id.offset)
        TextView offset;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
