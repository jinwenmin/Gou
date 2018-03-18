package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class SetNewRateActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.Rate)
    EditText Rate;
    @BindView(R.id.SetRate)
    Button SetRate;
    private String min;
    private String max;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_rate);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        init();
        inclick();

    }

    private void inclick() {
        Back.setOnClickListener(this);
        SetRate.setOnClickListener(this);
    }

    private void init() {
        Intent intent = getIntent();
        min = intent.getStringExtra("min");

        max = intent.getStringExtra("max");
        uid = intent.getIntExtra("uid", 0);

        Rate.setHint("返点范围" + min + "~" + max);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.SetRate:
                String trim = Rate.getText().toString().trim();
                if (!"".equals(trim)) {
                    double editV = Double.parseDouble(trim);
                    if (editV > Double.parseDouble(max) || editV < Double.parseDouble(min)) {
                        Toasty.error(this, "返点不在范围内", 2000).show();
                        return;
                    }
                    RetrofitService.getInstance().getTeamUserRebateSave(this, editV, uid);
                } else {
                    Toasty.error(this, "请输入返点", 2000).show();
                }
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_SETRATESAVE) {
            RestultInfo re = (RestultInfo) object;
            if (re.isRc()) {
                Toasty.success(this, re.getMsg(), 2000).show();
                finish();
            } else {
                Toasty.error(this, re.getMsg(), 2000).show();
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
