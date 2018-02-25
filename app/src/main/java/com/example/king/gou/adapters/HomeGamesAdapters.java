package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.HistoryGames;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by king on 2017/8/26.
 */

public class HomeGamesAdapters extends BaseAdapter {
    List<HistoryGames> hs;
    Context context;
    LayoutInflater inflater;

    public HomeGamesAdapters(Context context) {
        hs = new ArrayList<>();
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return hs.size();
    }
    public void addList(List<HistoryGames> h) {
        hs = h;
        notifyDataSetChanged();
    }
    @Override
    public Object getItem(int position) {
        return hs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_demo, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.HistoryGame.setText(hs.get(position).getName());
        viewHolder.HistroyImg.setImageResource(hs.get(position).getImg());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.HistroyImg)
        ImageView HistroyImg;
        @BindView(R.id.HistoryGame)
        TextView HistoryGame;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
