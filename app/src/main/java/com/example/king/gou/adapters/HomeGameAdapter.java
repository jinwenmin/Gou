package com.example.king.gou.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.king.gou.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class HomeGameAdapter extends RecyclerView.Adapter<HomeGameAdapter.ViewHolder> {
    List<String> String;
    Context context;
    LayoutInflater inflater;

    public HomeGameAdapter(Context con) {
        String = new ArrayList<>();
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        String.add("111");
        inflater = LayoutInflater.from(con);
        this.context = con;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gamejl, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return String.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
