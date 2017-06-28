package com.example.king.gou.ui;

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
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_data_safe_pwd);
        ButterKnife.bind(this);
        initClick();
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
                }
                if ("".equals(email)) {
                    Toasty.error(this, "绑定的邮箱不能为空", 2000).show();
                    return;
                }
                if ("a123456".equals(newpwd)) {
                    Toasty.error(this, "新密码不能为初始密码", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getUpDataSafePwd(this, oldpwd256, newpwd256, email);
                break;
        }
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
