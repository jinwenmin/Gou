package com.example.king.gou.ui.frmMyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.king.gou.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TixianAppliActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.TixianAppliTop)
    RelativeLayout TixianAppliTop;
    @BindView(R.id.TixianAppliSumText)
    TextView TixianAppliSumText;
    @BindView(R.id.TixianAppliZFBnameSum)
    TextView TixianAppliZFBnameSum;
    @BindView(R.id.TixianAppliRe1)
    RelativeLayout TixianAppliRe1;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.textMoney)
    TextView textMoney;
    @BindView(R.id.TixianAppliZFBname)
    EditText TixianAppliZFBname;
    @BindView(R.id.TixianAppliRe2)
    RelativeLayout TixianAppliRe2;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.textMoneyPwd)
    TextView textMoneyPwd;
    @BindView(R.id.MoneyPwd)
    EditText MoneyPwd;
    @BindView(R.id.TixianAppliRe3)
    RelativeLayout TixianAppliRe3;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.textMoneyBank)
    TextView textMoneyBank;
    @BindView(R.id.MoneyBankSpinner)
    Spinner MoneyBankSpinner;
    @BindView(R.id.tixianBankName)
    TextView tixianBankName;
    @BindView(R.id.TixianAppliRe4)
    RelativeLayout TixianAppliRe4;
    @BindView(R.id.TiJiao)
    Button TiJiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian_appli);
        ButterKnife.bind(this);
        TiJiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TiJiao:
                startActivity(new Intent(TixianAppliActivity.this,ApplyResultActivity.class));
                break;
        }

    }
}
