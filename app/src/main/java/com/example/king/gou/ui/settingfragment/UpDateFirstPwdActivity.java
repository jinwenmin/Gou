package com.example.king.gou.ui.settingfragment;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UpDateFirstPwdActivity extends AutoLayoutActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_date_first_pwd);
        ButterKnife.bind(this);
    }


}
