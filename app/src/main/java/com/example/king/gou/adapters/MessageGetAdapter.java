package com.example.king.gou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.Message;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/8.
 */

public class MessageGetAdapter extends BaseAdapter {
    List<Message> msgs;
    Context context;
    LayoutInflater inflater;
    String[] icon = new String[]{"[/大笑]", "[/抓狂]", "[/大哭]", "[/拜托]", "[/鄙视]", "[/不屑]", "[/愕然]", "[/眼红]", "[/很拽]", "[/好色]",
            "[/开心]", "[/窃喜]", "[/钦羡]", "[/难过]", "[/喜极]", "[/香吻]", "[/睡觉]", "[/失落]", "[/大骂]", "[/亲亲]",
            "[/悲哀]", "[/惊愕]", "[/害羞]", "[/流泪]", "[/调皮]", "[/闭嘴]", "[/失望]"};

    public void addList(List<Message> ms) {
        msgs = ms;
        notifyDataSetChanged();

    }


    public MessageGetAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        msgs = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int i) {
        return msgs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_msg_get, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Message m = msgs.get(i);
        if (m.getReaded() == 0) {
            viewHolder.Status.setText("未读");
        }
        if (m.getReaded() == 1) {
            viewHolder.Status.setText("已读");
        }
        if (!"".equals(m.getIs_alert())) {
            viewHolder.MsgTimer.setText(m.getChat_date());
        }
        if (!"".equals(m.getContent())) {
            for (int i1 = 0; i1 < icon.length; i1++) {
                //icon[i1]
            }
            viewHolder.GetMsg.setText(m.getContent());
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.Status)
        TextView Status;
        @BindView(R.id.GetMsgTimer)
        TextView MsgTimer;
        @BindView(R.id.View)
        android.view.View View;
        @BindView(R.id.GetMsg)
        TextView GetMsg;

        ViewHolder(android.view.View view) {
            ButterKnife.bind(this, view);
        }
    }
}
