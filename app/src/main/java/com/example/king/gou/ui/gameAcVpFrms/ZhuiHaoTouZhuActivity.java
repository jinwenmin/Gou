package com.example.king.gou.ui.gameAcVpFrms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.adapters.ZhuihaoTZAdapter;
import com.example.king.gou.bean.ZhuiHao;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    private int id;
    List<ZhuiHao> zh = new ArrayList<>();
    private ZhuihaoTZAdapter zhuihaoTZAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhui_hao_tou_zhu);
        ButterKnife.bind(this);
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
                for (int i = 0; i < zhuihaoTZAdapter.mChecked.size(); i++) {
                    Log.d("判断  ", zhuihaoTZAdapter.mChecked.get(i).booleanValue() + "");
                }
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
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
