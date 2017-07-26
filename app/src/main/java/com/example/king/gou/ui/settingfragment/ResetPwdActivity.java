package com.example.king.gou.ui.settingfragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResetPwdActivity extends AutoLayoutActivity {

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
    @BindView(R.id.UpDataPwdClick)
    Button UpDataPwdClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ButterKnife.bind(this);
    }
}
