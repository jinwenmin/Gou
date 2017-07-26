package com.example.king.gou.ui.settingfragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class ResetPwdActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.MyBJLTop)
    RelativeLayout MyBJLTop;
    @BindView(R.id.ResetPwdRadioB1)
    RadioButton ResetPwdRadioB1;
    @BindView(R.id.ResetPwdRadioB2)
    RadioButton ResetPwdRadioB2;
    @BindView(R.id.ResetPwdRadioG)
    RadioGroup ResetPwdRadioG;
    @BindView(R.id.ResetNewPwd)
    EditText ResetNewPwd;
    @BindView(R.id.ResetCheckNewPwd)
    EditText ResetCheckNewPwd;
    @BindView(R.id.ResetClick)
    Button ResetClick;
    int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ButterKnife.bind(this);
        initClick();
        ResetPwdRadioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == ResetPwdRadioB1.getId()) {
                    type = 1;
                }
                if (checkedId == ResetPwdRadioB2.getId()) {
                    type = 2;
                }
            }
        });
        ResetPwdRadioB1.setSelected(true);
    }

    private void initClick() {
        Back.setOnClickListener(this);
        ResetClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.ResetClick:
                String pwd = ResetNewPwd.getText().toString().trim();
                String pwdCheck = ResetCheckNewPwd.getText().toString().trim();
                if ("".equals(pwd) || "".equals(pwdCheck)) {
                    Toasty.error(this, "密码不可为空", 2000).show();
                    return;
                }
                if (!pwd.equals(pwdCheck)) {
                    Toasty.error(this, "新密码和确认密码不相同", 2000).show();
                    return;
                }
                if ("a123456".equals(pwd)) {
                    Toasty.error(this, "密码不可为初始密码", 2000).show();
                    return;
                }
                if (pwd.length() > 14 || pwd.length() < 6) {
                    Toasty.error(this, "密码长度不正确", 2000).show();
                    return;
                }
                pwd = RxUtils.getInstance().HMACSHA256(pwd, MyApp.getInstance().getUserName());
                Log.d("重置安全密码或者登陆密码Type", type + "");
                RetrofitService.getInstance().getResetPwd(this, type, pwd);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_RESETPWD) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
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
