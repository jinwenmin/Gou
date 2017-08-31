package com.example.king.gou.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.GameIm;
import com.example.king.gou.bean.GameImages;
import com.example.king.gou.utils.SectionedBaseAdapter;

import java.util.List;
import java.util.Map;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Administrator on 2017/6/6.
 */

public class MyAdapter extends SectionedBaseAdapter {

    List<GameImages> gameIms;
    private Context context;
    private LayoutInflater mInflater;


    public MyAdapter(Context context, List<GameImages> gameIm) {
        this.context = context;
        this.gameIms = gameIm;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int section, int position) {
        return gameIms.get(section).getGameIms().get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return gameIms.size();
    }

    @Override
    public int getCountForSection(int section) {
        return gameIms.get(section).getGameIms().size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_game, null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img1);
            viewHolder.name = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int gameimg = gameIms.get(section).getGameIms().get(position).getGameimg();
        viewHolder.img.setBackgroundResource(gameimg);
        // Picasso.with(context).load(gameimg).into(viewHolder.img);
        String imgText = gameIms.get(section).getGameIms().get(position).getImgText();
        viewHolder.name.setText(imgText);
        return convertView;
    }

    class ViewHolder {
        /**
         * 商品图片
         */
        public ImageView img;
        /**
         * 商品名称
         */
        public TextView name;

    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(gameIms.get(section).getType());
        return layout;
    }

}