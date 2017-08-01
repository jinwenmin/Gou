package com.example.king.gou.ui.settingfragment;

import android.os.Bundle;
import android.text.InputType;
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


public class UpDateFirstPwdActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.UpDatePwdTop)
    RelativeLayout UpDatePwdTop;
    @BindView(R.id.UpDateOldPwd)
    EditText UpDateOldPwd;
    @BindView(R.id.UpDateNewPwd)
    EditText UpDateNewPwd;
    @BindView(R.id.UpDateCheckNewPwd)
    EditText UpDateCheckNewPwd;
    @BindView(R.id.UpDateSafePwd)
    EditText UpDateSafePwd;
    @BindView(R.id.UpDateCheckSafePwd)
    EditText UpDateCheckSafePwd;
    @BindView(R.id.UpDataFirstPwd)
    Button UpDataFirstPwd;
    @BindView(R.id.UpDateEmail)
    EditText UpDateEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_date_first_pwd);
        ButterKnife.bind(this);
        UpDateEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        UpDataFirstPwd.setOnClickListener(this);
        Back.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.UpDataFirstPwd:
                String oldPwd = UpDateOldPwd.getText().toString().trim();
                String newPwd = UpDateNewPwd.getText().toString().trim();
                String CheckNewpwd = UpDateCheckNewPwd.getText().toString().trim();
                String SafePwd = UpDateSafePwd.getText().toString().trim();
                String CheckSafePwd = UpDateCheckSafePwd.getText().toString().trim();
                String email = UpDateEmail.getText().toString().trim();


                if ("".equals(oldPwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "原密码不可为空", 2000).show();
                    return;
                }
                if ("".equals(newPwd) || "".equals(CheckNewpwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "新密码或者确认密码不可为空", 2000).show();
                    return;
                }
                if ("".equals(SafePwd) || "".equals(CheckSafePwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "安全密码或者确认安全密码不可为空", 2000).show();
                    return;
                }

                if (newPwd.equals("a123456")) {
                    Toasty.error(UpDateFirstPwdActivity.this, "新密码不能和初始密码一样", 2000).show();
                    return;
                }
                if (!newPwd.equals(CheckNewpwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "新密码和确认密码不一致", 2000).show();
                    return;
                }
                if (!SafePwd.equals(CheckSafePwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "安全密码和确认安全密码不一致", 2000).show();
                    return;
                }
                if (newPwd.equals(SafePwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "新密码不能和安全密码一样", 2000).show();
                    return;
                }
                if ("".equals(email)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "邮箱不可为空", 2000).show();
                    return;
                }
                boolean b = RxUtils.getInstance().checkEmaile(email);
                if (b == false) {
                    Toasty.error(UpDateFirstPwdActivity.this, "请输入正确的邮箱地址", 2000).show();
                    return;
                }

                if (newPwd.length() > 14 || newPwd.length() < 6) {
                    Toasty.error(this, "登陆密码长度不正确", 2000).show();
                    return;
                }if (SafePwd.length() > 14 || SafePwd.length() < 6) {
                    Toasty.error(this, "安全密码长度不正确", 2000).show();
                    return;
                }

                String oldPwd256 = RxUtils.getInstance().HMACSHA256(oldPwd, MyApp.getInstance().getUserName());
                String newpwd256 = RxUtils.getInstance().HMACSHA256(newPwd, MyApp.getInstance().getUserName());
                String Safe256 = RxUtils.getInstance().HMACSHA256(SafePwd, MyApp.getInstance().getUserName());
                RetrofitService.getInstance().getUpdateFirstPwd(this, oldPwd256, newpwd256, Safe256, email);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_UPDATAFIRSTPWD) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc() == true) {
                    Toasty.success(UpDateFirstPwdActivity.this, restultInfo.getMsg(), 2000).show();
                    finish();
                } else {
                    Toasty.error(UpDateFirstPwdActivity.this, restultInfo.getMsg(), 2000).show();
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
