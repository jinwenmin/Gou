package com.example.king.gou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.example.king.gou.ui.settingfragment.MoneyProtectActivity;
import com.example.king.gou.ui.settingfragment.UpdateMoneyPwdActivity;
import com.example.king.gou.ui.settingfragment.UpdateNickNameActivity;
import com.example.king.gou.ui.settingfragment.UpdatePwdActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.Setting_UpdataPwd)
    RelativeLayout SettingUpdataPwd;
    @BindView(R.id.updateMoneyPwd)
    RelativeLayout updateMoneyPwd;
    @BindView(R.id.Pwd_protect)
    RelativeLayout PwdProtect;
    @BindView(R.id.update_NickName)
    RelativeLayout updateNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        SettingUpdataPwd.setOnClickListener(this);
        updateMoneyPwd.setOnClickListener(this);
        updateNickName.setOnClickListener(this);
        PwdProtect.setOnClickListener(this);
    }

    public void StartA(Class cls) {
        startActivity(new Intent(this, cls));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Setting_UpdataPwd:
                StartA(UpdatePwdActivity.class);
                break;
            case R.id.Pwd_protect:
                StartA(MoneyProtectActivity.class);
                break;
            case R.id.update_NickName:
                StartA(UpdateNickNameActivity.class);
                break;
            case R.id.updateMoneyPwd:
                StartA(UpdateMoneyPwdActivity.class);
                break;

        }
    }
}
