package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.TeamUserInfoAdapter;
import com.example.king.gou.bean.TeamUserInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProxyHomeActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {


    List<List<TeamUserInfo>> ts = new ArrayList<List<TeamUserInfo>>();
    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ProxyTop)
    RelativeLayout ProxyTop;
    @BindView(R.id.Proxy_UserCounts)
    TextView ProxyUserCounts;
    @BindView(R.id.Activity_ProxyListView)
    ListView ActivityProxyListView;
    TeamUserInfoAdapter adapter;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.TeamUserBtn)
    Button TeamUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        ButterKnife.bind(this);
        adapter = new TeamUserInfoAdapter(this);
        ActivityProxyListView.setAdapter(adapter);
        // RetrofitService.getInstance().getShareData(this);
        // RetrofitService.getInstance().getAddUserData(this);4
        RetrofitService.getInstance().getTeamUserInfo(this, 1, 100, "uid", "desc", MyApp.getInstance().getUserUid(), "", 0);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        TeamUserBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.TeamUserBtn:
                RetrofitService.getInstance().getAddUserData(this);
                break;
        }

    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_TEAMUSERINFO) {
            ts = (List<List<TeamUserInfo>>) object;
            ProxyUserCounts.setText("总会员数：" + ts.get(0).get(0).getTotalElements() + "人");
            if (ts.size() > 1) {
                List<TeamUserInfo> teamUserInfos = ts.get(1);
                adapter.addList(teamUserInfos);
            }
        }
        if (apiId == RetrofitService.API_ID_ADDVIPCODE) {
            String Code = (String) object;
            Intent intent = new Intent(getApplicationContext(), AddNewTeamUserActivity.class);
            intent.putExtra("code", Code);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
