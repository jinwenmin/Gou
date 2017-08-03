package com.example.king.gou.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.RecordList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public void addList(List<RecordList> rc) {
        rcs = rc;
        notifyDataSetChanged();
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
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_drawhistory, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (rcs.get(i).getPeriod() != null) {
            viewHolder.GameCenterP.setText("第" + rcs.get(i).getPeriod() + "期");
        }
        if (rcs.get(i).getWinningNumber() != null) {
            String win = rcs.get(i).getWinningNumber();
            String[] sp = win.split(",");
            viewHolder.GameCenterNum.removeAllViews();
            for (int j = 0; j < sp.length; j++) {
                View inflater = LayoutInflater.from(context).inflate(R.layout.item_num, null, false);
                TextView num = (TextView) inflater.findViewById(R.id.num);
                num.setText(sp[j]);
                viewHolder.GameCenterNum.addView(inflater);
            }
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.GameCenterP)
        TextView GameCenterP;
        @BindView(R.id.GameCenterNum)
        LinearLayout GameCenterNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
