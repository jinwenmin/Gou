package com.example.king.gou.ui.settingfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UpdateMoneyPwdActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.MyBJLTop)
    RelativeLayout MyBJLTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_money_pwd);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();break;
        }
    }
}
