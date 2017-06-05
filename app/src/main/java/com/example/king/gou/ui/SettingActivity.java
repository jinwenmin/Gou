package com.example.king.gou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.example.king.gou.ui.settingfragment.UpdataPwdActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.Setting_UpdataPwd)
    RelativeLayout SettingUpdataPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        SettingUpdataPwd.setOnClickListener(this);
    }

    public void StartA(Class cls) {
        startActivity(new Intent(this, cls));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Setting_UpdataPwd:
                StartA(UpdataPwdActivity.class);
                break;
        }
    }
}
