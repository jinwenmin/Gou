package com.example.king.gou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CNumberActivity extends AutoLayoutActivity implements HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.GameCertTop)
    RelativeLayout GameCertTop;
    @BindView(R.id.EditPeriod)
    EditText EditPeriod;
    @BindView(R.id.SearchTo)
    Button SearchTo;
    private String period;
    private int gid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cnumber);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        Intent intent = getIntent();
        period = intent.getStringExtra("period");
        gid = intent.getIntExtra("gid", 0);
        SearchTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = EditPeriod.getText().toString().trim();
                if ("".equals(s)) {

                }
                RetrofitService.getInstance().getBettingAutoPurchase(CNumberActivity.this, gid, period, 100);
            }
        });

    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {

    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
