package com.example.king.gou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.SreCharge;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class CheckRechargeActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener, OnItemClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.BtnCheck)
    Button BtnCheck;
    @BindView(R.id.RechargeMon)
    EditText RechargeMon;
    @BindView(R.id.RechargeUser)
    TextView RechargeUser;
    private String answerSafePwd;
    private AlertView alertView2;
    // 一个自定义的布局，作为显示的内容
    View contentView2;
    private Intent intent;


    private String ruser;
    private int uid;
    private double sreChargeMin;
    private double sreChargeMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_recharge);
        MyApp.getInstance().addActivitys(this);
        ButterKnife.bind(this);
        intent = getIntent();
        uid = intent.getIntExtra("uid", 0);
        ruser = intent.getStringExtra("ruser");
        sreChargeMin = intent.getDoubleExtra("SreChargeMin", 0);
        sreChargeMax = intent.getDoubleExtra("SreChargeMax", 0);

        alertView2 = new AlertView(null, null, "取消", new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentView2 = LayoutInflater.from(this).inflate(
                R.layout.proxy_owntransfer, null);
        alertView2.addExtView(contentView2);

        RechargeMon.setHint("充值范围:" + sreChargeMin + "~" + sreChargeMax);
        RechargeUser.setText(ruser);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        BtnCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.BtnCheck:
                String Rechargem = RechargeMon.getText().toString().trim();

                if ("".equals(Rechargem)) {
                    Toasty.error(this, "充值金额不能为空", 2000).show();
                    return;
                }
                double editV = Double.parseDouble(Rechargem);

                if (editV > sreChargeMax || editV < sreChargeMin) {
                    Toasty.error(this, "充值金额不在范围内", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getOwnReansferTrans(this, editV, ruser);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_OWNTRANSFER) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                    finish();
                }
                if (!restultInfo.isRc()) {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                    return;
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

    @Override
    public void onItemClick(Object o, int position) {
    }
}
