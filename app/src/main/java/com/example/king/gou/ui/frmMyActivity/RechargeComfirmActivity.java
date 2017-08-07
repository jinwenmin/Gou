package com.example.king.gou.ui.frmMyActivity;

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
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RechargeComfirmActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id.RechargeFirmTop)
    RelativeLayout RechargeFirmTop;
    @BindView(R.id.RechargeFirmTypeText)
    TextView RechargeFirmTypeText;
    @BindView(R.id.RechargeFirmZFBnameSum)
    TextView RechargeFirmZFBnameSum;
    @BindView(R.id.RechargeFirmRe)
    RelativeLayout RechargeFirmRe;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.textMoney)
    TextView textMoney;
    @BindView(R.id.RechargeFirmZFBname)
    EditText RechargeFirmZFBname;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.AccountText)
    TextView AccountText;
    @BindView(R.id.ApplyTo)
    Button ApplyTo;
    @BindView(R.id.TopBack)
    ImageView TopBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_comfirm);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        initCLick();

    }

    private void initCLick() {
        ApplyTo.setOnClickListener(this);
TopBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ApplyTo:
                startActivity(new Intent(RechargeComfirmActivity.this, TixianAppliActivity.class));
                break;
            case R.id.TopBack:
                finish();
                break;
        }
    }
}
