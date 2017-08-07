package com.example.king.gou.ui.orderFrmActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.ZhuiHaoAdapter;
import com.example.king.gou.bean.ZhuiHao;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.gameAcVpFrms.ZhuiHaoDetailActivity;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ZhuiHaoActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id.gamejl_back)
    ImageView gamejlBack;
    @BindView(R.id.ZhuiHaoTop)
    RelativeLayout ZhuiHaoTop;
    @BindView(R.id.ZhuiHaoListView)
    ListView ZhuiHaoListView;
    @BindView(R.id.ZhuiHaoRe)
    SwipeRefreshLayout ZhuiHaoRe;
    private ZhuiHaoAdapter zhuiHaoAdapter;
    List<ZhuiHao> zh = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhui_hao);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        zhuiHaoAdapter = new ZhuiHaoAdapter(this);
        ZhuiHaoListView.setAdapter(zhuiHaoAdapter);
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        String time = RxUtils.getInstance().FormatDate(date);
        RetrofitService.getInstance().getKeepNum(this, 1, 100, "id", "desc", "1970-01-01 00:00:01", time, 0, 0);
        initClick();
    }

    private void initClick() {
        ZhuiHaoRe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                long currentTimeMillis = System.currentTimeMillis();
                Date date = new Date(currentTimeMillis);
                String time = RxUtils.getInstance().FormatDate(date);
                RetrofitService.getInstance().getKeepNum(ZhuiHaoActivity.this, 1, 100, "id", "desc", "1970-01-01 00:00:01", time, 0, 0);

                zhuiHaoAdapter.notifyDataSetChanged();
            }
        });
        gamejlBack.setOnClickListener(this);
        ZhuiHaoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ZhuiHaoActivity.this, ZhuiHaoDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ZH", zh.get(i));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gamejl_back:
                finish();
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_ZHUIHAO) {
            if (object != null) {
                zh = (List<ZhuiHao>) object;
                zhuiHaoAdapter.getList(zh);
                ZhuiHaoRe.setRefreshing(false);
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
