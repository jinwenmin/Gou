package com.example.king.gou.ui.frmMyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;

import com.example.king.gou.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReChargeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.RechargeTypeText)
    TextView RechargeTypeText;
    @BindView(R.id.RechargeZhiFuBao)
    RadioButton RechargeZhiFuBao;
    @BindView(R.id.RechargeZhuanZhang)
    RadioButton RechargeZhuanZhang;
    @BindView(R.id.RechargeRadioGroup)
    RadioGroup RechargeRadioGroup;
    @BindView(R.id.RechargeRe)
    RelativeLayout RechargeRe;
    @BindView(R.id.textMoney)
    TextView textMoney;
    @BindView(R.id.RechargeMoney)
    EditText RechargeMoney;
    @BindView(R.id.AccountText)
    TextView AccountText;
    @BindView(R.id.AccountSum)
    TextView AccountSum;
    @BindView(R.id.ChongZ)
    Button ChongZ;
    @BindView(R.id.TopBack)
    ImageView TopBack;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_charge);
        ButterKnife.bind(this);
        ChongZ.setOnClickListener(this);
        TopBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ChongZ:
                startActivity(new Intent(ReChargeActivity.this, RechargeComfirmActivity.class));
                break;
            case R.id.TopBack:
                finish();
                break;
        }
    }
}
