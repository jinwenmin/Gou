package com.example.king.gou.ui.settingfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.UserInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class UpdateNickNameActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.MoneyProtectTop)
    RelativeLayout MoneyProtectTop;
    @BindView(R.id.OldUserNickName)
    TextView OldUserNickName;
    @BindView(R.id.NewUserNickName)
    EditText NewUserNickName;
    @BindView(R.id.UpDateCheck)
    Button UpDateCheck;
    String nickName;
    List<UserInfo> userInfos;
    @BindView(R.id.CheckNewUserNickName)
    EditText CheckNewUserNickName;
    private String checkNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick_name);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        RetrofitService.getInstance().GetUserInfo(this);
        // OldUserNickName.setText(MyApp.getInstance().getUserNickName());
        initCLick();
    }

    private void initCLick() {
        Back.setOnClickListener(this);
        UpDateCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.UpDateCheck:
                nickName = NewUserNickName.getText().toString().trim();
                checkNickName = CheckNewUserNickName.getText().toString().trim();
                if (nickName == null || "".equals(nickName)) {
                    Toasty.error(UpdateNickNameActivity.this, "用户昵称不可为空", 2000).show();
                    return;
                }
                if (checkNickName == null || "".equals(checkNickName)) {
                    Toasty.error(UpdateNickNameActivity.this, "确认昵称不可为空", 2000).show();
                    return;
                }
                if (!checkNickName.equals(nickName)) {
                    Toasty.error(UpdateNickNameActivity.this, "两次昵称不相同", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getUpdateNickName(this, nickName);
                finish();
                break;

        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_UPDATENICKNAME) {
            if (object != null) {
                RestultInfo upDateNickName = (RestultInfo) object;
                if (upDateNickName.isRc() == true) {
                    Toasty.info(UpdateNickNameActivity.this, upDateNickName.getMsg(), 2000).show();
                    Intent intent = new Intent("action.NickName");
                    intent.putExtra("NickName", nickName);
                    this.sendBroadcast(intent);
                }
            }
        }
        if (RetrofitService.API_ID_USERINFO == apiId) {
            userInfos = (List<UserInfo>) object;
            UserInfo userInfo = userInfos.get(0);
            OldUserNickName.setText(userInfo.getNname());


        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
