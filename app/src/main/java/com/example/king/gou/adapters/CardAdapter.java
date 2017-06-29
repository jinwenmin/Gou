package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.king.gou.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/29.
 */

public class CardAdapter extends BaseAdapter {
    List<String> cards;
    Context context;
    LayoutInflater layoutInflater;

    public CardAdapter(Context con) {
        this.context = con;
        cards = new ArrayList<>();
        layoutInflater = LayoutInflater.from(con);
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int i) {
        return cards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_card, null, false);
            viewHolder = new ViewHolder(convertView );

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String s = cards.get(position);
        if (s.length()>4) {
            String substring = s.substring(0, 4);
            String substring1 = s.substring(s.length() - 4, s.length());
            viewHolder.cardNumber.setText(substring+"**** **** **** " +substring1);
        }else
        {
            viewHolder.cardNumber.setText(s);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.card_bankname)
        TextView cardBankname;
        @BindView(R.id.cardItem_CardId)
        TextView cardItemCardId;
        @BindView(R.id.card_number)
        TextView cardNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void addCards(List<String> ca) {
        cards = ca;
        notifyDataSetChanged();
    }
}
