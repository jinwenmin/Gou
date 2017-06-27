package com.example.king.gou.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class CheckSafePwdActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.CheckSafePwdTop)
    RelativeLayout CheckSafePwdTop;
    @BindView(R.id.CheckSafePwd)
    EditText CheckSafePwd;
    @BindView(R.id.UpDataPwdClick)
    Button UpDataPwdClick;
    RestultInfo restultInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_safe_pwd);
        ButterKnife.bind(this);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        UpDataPwdClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.UpDataPwdClick:
                String pwd = CheckSafePwd.getText().toString().trim();
                if (pwd == null) {
                    Toasty.info(CheckSafePwdActivity.this, "安全密码不能为空", 2000).show();
                    return;
                }
                String hmacsha256 = RxUtils.getInstance().HMACSHA256(pwd, MyApp.getInstance().getUserName());
                RetrofitService.getInstance().getCheckSafePwd(this, hmacsha256);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_SAFEPWD) {
            if (object != null) {
                restultInfo = (RestultInfo) object;
                if (restultInfo.isRc() == false) {
                    Toasty.info(this, restultInfo.getMsg(), 2000).show();
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
