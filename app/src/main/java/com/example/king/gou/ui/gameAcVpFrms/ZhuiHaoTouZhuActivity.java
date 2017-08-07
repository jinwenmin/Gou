package com.example.king.gou.ui.gameAcVpFrms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.ZhuihaoTZAdapter;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.ZhuiHao;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class ZhuiHaoTouZhuActivity extends AutoLayoutActivity implements HttpEngine.DataListener {

    @BindView(R.id.Zhuihjl_back)
    ImageView ZhuihjlBack;
    @BindView(R.id.ZhuiHaoTop)
    RelativeLayout ZhuiHaoTop;
    @BindView(R.id.ZhuiHaoTZListView)
    ListView ZhuiHaoTZListView;
    @BindView(R.id.ZhuiHaoTZRe)
    SwipeRefreshLayout ZhuiHaoTZRe;
    @BindView(R.id.Cacel)
    TextView Cacel;
    @BindView(R.id.ZHTZCheck)
    CheckBox ZHTZCheck;
    private int id;
    List<ZhuiHao> zh = new ArrayList<>();
    private ZhuihaoTZAdapter zhuihaoTZAdapter;
    RestultInfo restultInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhui_hao_tou_zhu);
        ButterKnife.bind(this); MyApp.getInstance().addActivitys(this);
        zhuihaoTZAdapter = new ZhuihaoTZAdapter(this);
        ZhuiHaoTZListView.setAdapter(zhuihaoTZAdapter);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        initRe();
        RetrofitService.getInstance().getKeepNumBet(this, 1, 100, "serial_number", "desc", id);

    }

    private void initRe() {
        ZhuiHaoTZRe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RetrofitService.getInstance().getKeepNumBet(ZhuiHaoTouZhuActivity.this, 1, 100, "serial_number", "desc", id);
            }
        });
        Cacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = null;
                for (int i = 0; i < zhuihaoTZAdapter.mChecked.size(); i++) {
                    Log.d("判断  ", zhuihaoTZAdapter.mChecked.get(i).booleanValue() + "");

                    if (zhuihaoTZAdapter.mChecked.get(i).booleanValue()) {
                        str = str + zh.get(i).getId();
                    }
                }
                if (str == null) {
                   Toasty.error(ZhuiHaoTouZhuActivity.this,"未选中任何栏目",2000).show();
                    return;
                }
                RetrofitService.getInstance().getLotteryBetRevoke(ZhuiHaoTouZhuActivity.this, str);
            }
        });
        ZhuihjlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ZHTZCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    for (int i = 0; i < zh.size(); i++) {
                        zh.get(i).setBo(true);
                    }
                }
                if (b == false) {
                    for (int i = 0; i < zh.size(); i++) {
                        zh.get(i).setBo(false);
                    }
                }
                zhuihaoTZAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_ZHTZDETAIL) {
            if (object != null) {
                zh = (List<ZhuiHao>) object;
                zhuihaoTZAdapter.getList(zh);
                ZhuiHaoTZRe.setRefreshing(false);
            }
        }
        if (apiId == RetrofitService.API_ID_CACEL) {
            if (object != null) {
                restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                } else {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                }
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
