package com.example.king.gou.ui.proxyfragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserCenterActivity extends AppCompatActivity {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ProxyTop)
    RelativeLayout ProxyTop;
    @BindView(R.id.userCenter_group)
    RadioGroup userCenterGroup;
    @BindView(R.id.User_center_linear)
    LinearLayout UserCenterLinear;
    @BindView(R.id.User_center_linear2)
    LinearLayout UserCenterLinear2;
    @BindView(R.id.User_center_LinkTime)
    LinearLayout UserCenterLinkTime;
    @BindView(R.id.Relative)
    RelativeLayout Relative;
    @BindView(R.id.userCenter_EditLinear2)
    LinearLayout userCenterEditLinear2;
    @BindView(R.id.Linear1)
    LinearLayout Linear1;
    @BindView(R.id.Linear2)
    LinearLayout Linear2;
    @BindView(R.id.userCenter_EditLinear1)
    LinearLayout userCenterEditLinear1;
    @BindView(R.id.radioBtn1)
    RadioButton radioBtn1;
    @BindView(R.id.radioBtn2)
    RadioButton radioBtn2;
    @BindView(R.id.radioBtn3)
    RadioButton radioBtn3;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.userCenter_BtnTo)
    Button userCenterBtnTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        ButterKnife.bind(this);

        userCenterGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.radioBtn1) {
                    UserCenterLinkTime.setVisibility(View.GONE);
                    Linear1.setVisibility(View.VISIBLE);
                    Linear2.setVisibility(View.GONE);
                    text1.setVisibility(View.VISIBLE);
                    text2.setVisibility(View.VISIBLE);
                    text3.setVisibility(View.GONE);
                    userCenterBtnTo.setText("确认");
                }
                if (checkedId == R.id.radioBtn2) {
                    UserCenterLinkTime.setVisibility(View.VISIBLE);
                    Linear2.setVisibility(View.VISIBLE);
                    Linear1.setVisibility(View.GONE);
                    text1.setVisibility(View.GONE);
                    text2.setVisibility(View.GONE);
                    text3.setVisibility(View.VISIBLE);
                    userCenterBtnTo.setText("生成链接");
                }
            }
        });
        radioBtn1.setChecked(true);

    }
}
