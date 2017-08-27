package com.example.king.gou.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.HistoryGames;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/2.
 */

public class HomeGameAdapter extends RecyclerView.Adapter<HomeGameAdapter.ViewHolder> {
    List<HistoryGames> hs;
    Context context;
    LayoutInflater inflater;

    public HomeGameAdapter(Context con,List<HistoryGames> historyGames) {
        hs = historyGames;
        inflater = LayoutInflater.from(con);
        this.context = con;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.item_demo, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.HistGame.setText(hs.get(position).getName());
        holder.img.setBackgroundResource(hs.get(position).getImg());
    }



    @Override
    public int getItemCount() {
        return hs.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.HistoryGame)
        TextView HistGame;
        @BindView(R.id.HistroyImg)
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
