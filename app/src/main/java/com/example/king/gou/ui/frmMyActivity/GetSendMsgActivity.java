package com.example.king.gou.ui.frmMyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.adapters.MessageGetAdapter;
import com.example.king.gou.bean.Message;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GetSendMsgActivity extends AutoLayoutActivity implements HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.Top)
    RelativeLayout Top;
    @BindView(R.id.SendLinear)
    LinearLayout SendLinear;
    @BindView(R.id.getSendMsgListView)
    ListView getSendMsgListView;
    MessageGetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_send_msg);
        ButterKnife.bind(this);
        getSendMsgListView.setDividerHeight(0);
        adapter = new MessageGetAdapter(this);
        getSendMsgListView.setAdapter(adapter);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        title.setText("与" + name + "聊天中");
        String dates = RxUtils.getInstance().Dates(0);
        String dates1 = RxUtils.getInstance().Dates(System.currentTimeMillis());
        RetrofitService.getInstance().getChatList(GetSendMsgActivity.this, 1, 100, "chat_date", "desc", id, dates, dates1);
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_CHATLIST) {
            if (object != null) {
                List<Message> messages = (List<Message>) object;
                adapter.addList(messages);
            }
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
