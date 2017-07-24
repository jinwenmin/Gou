package com.example.king.gou.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.CardsData;
import com.example.king.gou.bean.MapsIdAndValue;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/29.
 */

public class CardAdapter extends BaseAdapter {
    List<CardsData> cards;
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
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String s = cards.get(position).getAccount_number();
        if (s.length() > 4) {
            String substring = s.substring(0, 4);
            String substring1 = s.substring(s.length() - 4, s.length());
            viewHolder.cardNumber.setText(substring + "**** **** **** " + substring1);
        } else {
            viewHolder.cardNumber.setText(s);
        }
        if (!"".equals(cards.get(position).getBank_name())) {
            viewHolder.cardBankname.setText(cards.get(position).getBank_name());
        }
        if (!"".equals(cards.get(position).getHolders_name())) {
            String name = cards.get(position).getHolders_name();
            Log.d("name.length", name.length() + "");
            if (name.length() > 1) {
                name = name.substring(name.length() - 1, name.length());
                String x = "";
               /* for (int i = 0; i < name.length() - 1; i++) {
                    x = x + "*";
                }*/
                name = "**" + name;
                viewHolder.cardItemUsername.setText(name);
            }


        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.card_bankname)
        TextView cardBankname;
        @BindView(R.id.cardItem_UserName)
        TextView cardItemUsername;
        @BindView(R.id.card_number)
        TextView cardNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void addCards(List<CardsData> ca) {
        cards = ca;
        notifyDataSetChanged();
    }
}
