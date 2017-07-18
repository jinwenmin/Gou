package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
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


public class ProxyHomeActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener, OnItemClickListener {


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
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.ProxyHomeSpinner)
    Spinner ProxyHomeSpinner;
    ArrayAdapter<String> adapter2;
    List<Integer> tId = new ArrayList<>();
    List<String> tName = new ArrayList<>();
    int uid;
    private AlertView alertView;
    // 一个自定义的布局，作为显示的内容
    View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        ButterKnife.bind(this);
        alertView = new AlertView(null, null, "确认", null, null, this, AlertView.Style.Alert, this);
        contentView = LayoutInflater.from(this).inflate(
                R.layout.item_homenotice, null);
        alertView.addExtView(contentView);
        adapter = new TeamUserInfoAdapter(this);
        ActivityProxyListView.setAdapter(adapter);
        initSpinner();
        // RetrofitService.getInstance().getShareData(this);
        // RetrofitService.getInstance().getAddUserData(this);4

        initClick();
    }

    private void initSpinner() {
        tId.add(0);
        tId.add(2);
        tId.add(3);
        tId.add(-1);
        tName.add("所有用户");
        tName.add("代理用户");
        tName.add("普通会员");
        tName.add("在线用户");
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tName);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        ProxyHomeSpinner.setAdapter(adapter2);
        ProxyHomeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getTeamUserInfo(ProxyHomeActivity.this, 1, 100, "uid", "desc", MyApp.getInstance().getUserUid(), "", tId.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void ItemOnLongClick1() {
//注：setOnCreateContextMenuListener是与下面onContextItemSelected配套使用的
        ActivityProxyListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "查询团队余额");
                menu.add(0, 1, 0, "设置返点");
                menu.add(0, 2, 0, "对比");

            }
        });

    }    // 长按菜单响应函数

    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();


        switch (item.getItemId()) {
            case 0:
                // 添加操作
                Toast.makeText(ProxyHomeActivity.this,
                        "查询团队余额",
                        Toast.LENGTH_SHORT).show();
                RetrofitService.getInstance().getTeamBalanceView(this, uid);
                break;

            case 1:
                Toast.makeText(ProxyHomeActivity.this,
                        "设置返点",
                        Toast.LENGTH_SHORT).show();
                RetrofitService.getInstance().getUreBateData(this, uid);
                break;

            case 2:
                Toast.makeText(ProxyHomeActivity.this,
                        "对比",
                        Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return false;
    }

    private void initClick() {
        Back.setOnClickListener(this);
        TeamUserBtn.setOnClickListener(this);
        ActivityProxyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (ts.size() > 1) {
                    uid = ts.get(1).get(i).getUid();
                    ItemOnLongClick1();
                }
                return false;
            }
        });
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
        if (apiId == RetrofitService.API_ID_TEAMBALANCEVIEW) {
            String TeamAmount = (String) object;
            TextView homeNotice = (TextView) contentView.findViewById(R.id.homeNoticeText);
            homeNotice.setText(TeamAmount);
            alertView.show();
        }

    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }

    @Override
    public void onItemClick(Object o, int position) {

    }
}
