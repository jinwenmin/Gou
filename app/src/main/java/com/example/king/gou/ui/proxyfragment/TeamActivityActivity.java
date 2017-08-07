package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.TeamActivityAdapter;
import com.example.king.gou.bean.UserActivity;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TeamActivityActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ActivityTop)
    RelativeLayout ActivityTop;
    @BindView(R.id.TeamActivityListView)
    ListView TeamActivityListView;
    List<UserActivity> uc = new ArrayList<>();
    TeamActivityAdapter activityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_activity);
        ButterKnife.bind(this); MyApp.getInstance().addActivitys(this);
        RetrofitService.getInstance().GetActivityList(this);
        activityAdapter = new TeamActivityAdapter(this);
        TeamActivityListView.setAdapter(activityAdapter);
        initCLick();
    }

    private void initCLick() {
        Back.setOnClickListener(this);
        TeamActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TeamActivityActivity.this, ActivityDetailsActivity.class);
                intent.putExtra("aid", uc.get(position).getAid());
                intent.putExtra("name", uc.get(position).getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_ACTIVITYLIST) {
            uc = (List<UserActivity>) object;
            activityAdapter.addList(uc);
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._back:
                finish();
                break;
        }
    }
}
