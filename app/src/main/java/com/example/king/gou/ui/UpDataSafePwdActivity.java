package com.example.king.gou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.UserInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.settingfragment.ResetPwdActivity;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class UpDataSafePwdActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.MyBJLTop)
    RelativeLayout MyBJLTop;
    @BindView(R.id.UpdataPwdOldPwd)
    EditText UpdataPwdOldPwd;
    @BindView(R.id.UpdataSafePwdNewPwd)
    EditText UpdataSafePwdNewPwd;
    @BindView(R.id.UpdataSafePwdCheck)
    EditText UpdataSafePwdCheck;
    @BindView(R.id.UpdataSafepwdEmail)
    EditText UpdataSafepwdEmail;
    @BindView(R.id.UpDataPwdClick)
    Button UpDataPwdClick;
    private String emailINFO;
    private UserInfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_data_safe_pwd);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        initClick();
        userinfo = RetrofitService.getInstance().getUser();
        Log.d("邮箱是", userinfo.toString());

    }

    private void initClick() {
        Back.setOnClickListener(this);
        UpDataPwdClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.UpDataPwdClick:
                String oldPwd = UpdataPwdOldPwd.getText().toString().trim();
                String newpwd = UpdataSafePwdNewPwd.getText().toString().trim();
                String newpwdCheck = UpdataSafePwdCheck.getText().toString().trim();
                String email = UpdataSafepwdEmail.getText().toString().trim();
                String userName = MyApp.getInstance().getUserName();
                String oldpwd256 = RxUtils.getInstance().HMACSHA256(oldPwd, userName);
                String newpwd256 = RxUtils.getInstance().HMACSHA256(newpwd, userName);
                if ("".equals(oldPwd)) {
                    Toasty.error(this, "原密码不能为空", 2000).show();
                    return;
                }
                if ("".equals(newpwd) || "".equals(newpwdCheck)) {
                    Toasty.error(this, "新密码或确认密码不能为空", 2000).show();
                    return;
                }
                if (!newpwd.equals(newpwdCheck)) {
                    Toasty.error(this, "新安全密码跟确认密码不一致", 2000).show();
                    return;
                }
                if ("".equals(email)) {
                    Toasty.error(this, "绑定的邮箱不能为空", 2000).show();
                    return;
                }
                if (!RxUtils.getInstance().checkEmaile(email)) {
                    Toasty.error(this, "邮箱输入有误", 2000).show();
                    return;
                }

                if ("a123456".equals(newpwd)) {
                    Toasty.error(this, "新密码不能为初始密码", 2000).show();
                    return;
                }
                if (newpwd.length() > 14 || newpwd.length() < 6) {
                    Toasty.error(this, "密码长度不正确", 2000).show();
                    return;
                }
                if (email.equals(userinfo.getEmail())) {
                    Toasty.error(this, "邮箱跟当前的邮箱相同", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getUpDataSafePwd(this, oldpwd256, newpwd256, email);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_UPDATASAFEPWD) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                    RetrofitService.getInstance().LogOut();
                    Intent intent = new Intent(UpDataSafePwdActivity.this, LoginActivity.class);
                    intent.putExtra("LogOut", "logout");
                    startActivity(intent);
                    MyApp.getInstance().finishActivity();
                    return;
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
}
