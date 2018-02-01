package com.example.king.gou.ui.gameAcVpFrms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    @BindView(R.id.ZhuiHaoTZll)
    LinearLayout ZhuiHaoTZll;
    private int id;
    List<ZhuiHao> zh = new ArrayList<>();
    private ZhuihaoTZAdapter zhuihaoTZAdapter;
    RestultInfo restultInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhui_hao_tou_zhu);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
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
                String str = "";
                for (int i = 0; i < ZhuiHaoTZll.getChildCount(); i++) {
                    LinearLayout linear = (LinearLayout) ZhuiHaoTZll.getChildAt(i);
                    LinearLayout linear1 = (LinearLayout) linear.getChildAt(0);
                    if (((CheckBox) linear1.getChildAt(0)).isChecked()) {
                        if (str == "") {
                            str = zh.get(i).getId()+"";
                        } else {
                            str = str + "," + zh.get(i).getId();
                        }
                    }
                }
                if (str == null) {
                    Toasty.error(ZhuiHaoTouZhuActivity.this, "未选中任何栏目", 2000).show();
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
                    for (int i = 0; i < ZhuiHaoTZll.getChildCount(); i++) {
                        LinearLayout linear1 = (LinearLayout) ZhuiHaoTZll.getChildAt(i);
                        LinearLayout linear2 = (LinearLayout) linear1.getChildAt(0);
                        if (zh.get(i).getStatus() == 1) {
                            ((CheckBox) linear2.getChildAt(0)).setChecked(true);
                        }
                    }
                } else {
                    for (int i = 0; i < ZhuiHaoTZll.getChildCount(); i++) {
                        LinearLayout linear1 = (LinearLayout) ZhuiHaoTZll.getChildAt(i);
                        LinearLayout linear2 = (LinearLayout) linear1.getChildAt(0);
                        ((CheckBox) linear2.getChildAt(0)).setChecked(false);
                    }
                }
            }
        });

    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_ZHTZDETAIL) {
            if (object != null) {
                zh = (List<ZhuiHao>) object;
                for (int i = 0; i < zh.size(); i++) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_zhtz, null);
                    TextView tzId = (TextView) view.findViewById(R.id.TouZhuId);
                    TextView tzq = (TextView) view.findViewById(R.id.TouZhuQi);
                    TextView tzb = (TextView) view.findViewById(R.id.TouZhuBei);
                    TextView tzzt = (TextView) view.findViewById(R.id.TouZhuStatus);
                    final CheckBox tzck = (CheckBox) view.findViewById(R.id.TouZhuCheck);
                    final int finalI = i;
                    tzck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (zh.get(finalI).getStatus() == 0) {
                                tzck.setChecked(false);
                            }
                        }
                    });
                    tzId.setText(zh.get(i).getId() + "");
                    tzq.setText(zh.get(i).getNumber());
                    tzb.setText(zh.get(i).getPrize_num() + "");
                    if (zh.get(i).getStatus() == 0) {
                        tzzt.setText("不可勾选");
                    } else {
                        tzzt.setText("可勾选");
                    }
                    ZhuiHaoTZll.addView(view);
                }
                //zhuihaoTZAdapter.getList(zh);
                //  ZhuiHaoTZRe.setRefreshing(false);
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
