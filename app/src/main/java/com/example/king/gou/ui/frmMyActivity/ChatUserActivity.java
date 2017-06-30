package com.example.king.gou.ui.frmMyActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.example.king.gou.adapters.ChatUsersAdapter;
import com.example.king.gou.bean.MapsIdAndValue;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatUserActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ChatUserTop)
    RelativeLayout ChatUserTop;
    @BindView(R.id.CharUserListView)
    ListView CharUserListView;
    List<MapsIdAndValue> CharUser;
    ChatUsersAdapter chatUsersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);
        ButterKnife.bind(this);
        RetrofitService.getInstance().getChatUser(this);
        chatUsersAdapter = new ChatUsersAdapter(this);
        CharUserListView.setAdapter(chatUsersAdapter);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        CharUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String dates = RxUtils.getInstance().Dates(0);
                String dates1 = RxUtils.getInstance().Dates(System.currentTimeMillis());
                RetrofitService.getInstance().getChatList(ChatUserActivity.this, 1, 100, "chat_date", "desc", CharUser.get(i).getId(), dates, dates1);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_CHATUSERS) {
            if (object != null) {
                CharUser = (List<MapsIdAndValue>) object;
                chatUsersAdapter.addListMaps(CharUser);
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
