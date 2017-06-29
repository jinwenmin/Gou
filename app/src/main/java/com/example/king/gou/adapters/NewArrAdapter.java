package com.example.king.gou.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.king.gou.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by king on 2017/6/29.
 */

public class NewArrAdapter extends ArrayAdapter<String> {
    List<String> items = new ArrayList<>();
    Context con;
    int Res;

    public NewArrAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        Res = resource;
        con = context;
        items = objects;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(con).inflate(Res, parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       viewHolder.textSpinner.setText(items.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView textSpinner;

        public ViewHolder(View view) {
            textSpinner = ((TextView) view.findViewById(R.id.textSpinner));
        }
    }

}
