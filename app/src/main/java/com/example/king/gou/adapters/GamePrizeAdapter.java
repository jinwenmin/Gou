package com.example.king.gou.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.GamePrize;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/20.
 */

public class GamePrizeAdapter extends BaseAdapter {
    List<GamePrize> gp;
    Context context;
    LayoutInflater inflater;

    public GamePrizeAdapter(Context context) {
        this.context = context;
        gp = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return gp.size();
    }

    @Override
    public Object getItem(int i) {
        return gp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addList(List<GamePrize> g) {
        gp = g;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_gameprize, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (!"".equals(gp.get(i).getGameName())) {
            viewHolder.gameName.setText(gp.get(i).getGameName());
        }
        if (!"".equals(gp.get(i).getRuleName())) {
            viewHolder.ruleName.setText(gp.get(i).getRuleType() + "-" + gp.get(i).getRuleName());
        }
        if (!"".equals(gp.get(i).getPrize())) {
            viewHolder.prize.setText(Html.fromHtml(gp.get(i).getPrize()));
        }
        viewHolder.rate.setText(gp.get(i).getUserRate() + "");
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.gameName)
        TextView gameName;
        @BindView(R.id.ruleName)
        TextView ruleName;
        @BindView(R.id.prize)
        TextView prize;
        @BindView(R.id.rate)
        TextView rate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
