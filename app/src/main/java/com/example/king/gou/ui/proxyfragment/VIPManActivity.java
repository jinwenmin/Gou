package com.example.king.gou.ui.proxyfragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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


public class VIPManActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ProxyTop)
    RelativeLayout ProxyTop;
    @BindView(R.id.VipMan_Edit)
    EditText VipManEdit;
    @BindView(R.id.vipMan_searBtn)
    Button vipManSearBtn;
    @BindView(R.id.VipManTeamCount)
    TextView VipManTeamCount;
    @BindView(R.id.VipManLoginCount)
    TextView VipManLoginCount;
    @BindView(R.id.VipManTeamAmount)
    TextView VipManTeamAmount;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.VipManNoLogin)
    TextView VipManNoLogin;
    @BindView(R.id.VipManRegisterToday)
    TextView VipManRegisterToday;
    List<String> userSta = new ArrayList<>();
    @BindView(R.id.VipManSpinnerStype)
    Spinner VipManSpinnerStype;
    ArrayAdapter<String> adapter1;
    @BindView(R.id.VIPManListView)
    ListView VIPManListView;
    TeamUserInfoAdapter adapter;
    List<List<TeamUserInfo>> ts = new ArrayList<List<TeamUserInfo>>();
    @BindView(R.id.VIPManSwipe)
    SwipeRefreshLayout VIPManSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vipman);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        adapter = new TeamUserInfoAdapter(this);
        VIPManListView.setAdapter(adapter);
        RetrofitService.getInstance().getUserStatistics(this);
        initClick();
        initSpinner();
    }

    private void initSpinner() {
        List<String> Stype = new ArrayList<>();
        Stype.add("查询15天内未登录的用户");
        Stype.add("查询今日注册的用户");
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Stype);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        VipManSpinnerStype.setAdapter(adapter1);
        VipManSpinnerStype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getTeamUserList(VIPManActivity.this, 1, 100, "uid", "desc", 1, i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initClick() {
        Back.setOnClickListener(this);
        VIPManSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RetrofitService.getInstance().getTeamUserList(VIPManActivity.this, 1, 100, "uid", "desc", 1, VipManSpinnerStype.getSelectedItemPosition());
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
        if (apiId == RetrofitService.API_ID_USERSTA) {
            userSta = (List<String>) object;
            VipManTeamCount.setText(userSta.get(0));
            VipManLoginCount.setText(userSta.get(1));
            VipManTeamAmount.setText(userSta.get(2));
            VipManNoLogin.setText(userSta.get(3));
            VipManRegisterToday.setText(userSta.get(4));
        }
        if (apiId == RetrofitService.API_ID_TEAMUSERLIST) {
            ts = (List<List<TeamUserInfo>>) object;

            if (ts.size() > 1) {
                List<TeamUserInfo> teamUserInfos = ts.get(1);
                adapter.addList(teamUserInfos);
                VIPManSwipe.setRefreshing(false);
            }else{
                List<TeamUserInfo> teamUserInfos = new ArrayList<>();
                adapter.addList(teamUserInfos);
                VIPManSwipe.setRefreshing(false);
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
