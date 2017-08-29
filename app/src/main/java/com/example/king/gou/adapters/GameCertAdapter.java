package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.Ids;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/29.
 */

public class GameCertAdapter extends BaseAdapter {
    List<Ids> idses;
    Context context;
    LayoutInflater inflater;

    public GameCertAdapter(Context context, List<Ids> idses) {
        this.idses = idses;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return idses.size();
    }

    @Override
    public Object getItem(int i) {
        return idses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_gamecert, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (!"".equals(idses.get(i).getPickedNumber())) {
            viewHolder.GameTypeContent.setText(idses.get(i).getPickedNumber());
        }
        if (0 != idses.get(i).getNum()) {
            viewHolder.GameTypeZhu.setText("共" + idses.get(i).getNum() + "注");
        }
        if (0 != idses.get(i).getMultiple()) {
            viewHolder.GameTypeBei.setText("共" + idses.get(i).getMultiple() + "倍");
        }
        if (0 != idses.get(i).getAmount()) {
            viewHolder.GameTypeAmount.setText("共" + idses.get(i).getAmount() + "元");
        }
        if (!"".equals(idses.get(i).getGamename())) {
            viewHolder.GameTypeName.setText(idses.get(i).getGamename());
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.GameTypeName)
        TextView GameTypeName;
        @BindView(R.id.GameTypeContent)
        TextView GameTypeContent;
        @BindView(R.id.GameTypeZhu)
        TextView GameTypeZhu;
        @BindView(R.id.GameTypeBei)
        TextView GameTypeBei;
        @BindView(R.id.GameTypeAmount)
        TextView GameTypeAmount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
