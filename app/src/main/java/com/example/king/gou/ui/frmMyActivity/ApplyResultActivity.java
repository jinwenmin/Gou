package com.example.king.gou.ui.frmMyActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApplyResultActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ApplyResultTop)
    RelativeLayout ApplyResultTop;
    @BindView(R.id.ApplyResultTypeText)
    TextView ApplyResultTypeText;
    @BindView(R.id.ApplyResultZFBnameSum)
    TextView ApplyResultZFBnameSum;
    @BindView(R.id.ApplyResultRe1)
    RelativeLayout ApplyResultRe1;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.textMoney)
    TextView textMoney;
    @BindView(R.id.ApplyResultZFBMoney)
    TextView ApplyResultZFBMoney;
    @BindView(R.id.ApplyResultRe2)
    RelativeLayout ApplyResultRe2;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.textZFBname)
    TextView textZFBname;
    @BindView(R.id.ApplyResultZFBname)
    TextView ApplyResultZFBname;
    @BindView(R.id.ApplyResultRe3)
    RelativeLayout ApplyResultRe3;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.textApplyResultBank)
    TextView textApplyResultBank;
    @BindView(R.id.ApplyResultBank)
    TextView ApplyResultBank;
    @BindView(R.id.ApplyResultRe4)
    RelativeLayout ApplyResultRe4;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.textApplyResultUserName)
    TextView textApplyResultUserName;
    @BindView(R.id.ApplyResultUserName)
    TextView ApplyResultUserName;
    @BindView(R.id.ApplyResultRe5)
    RelativeLayout ApplyResultRe5;
    @BindView(R.id.textApplyResultFuYanId)
    TextView textApplyResultFuYanId;
    @BindView(R.id.ApplyResultFuYanId)
    TextView ApplyResultFuYanId;
    @BindView(R.id.AccountText)
    TextView AccountText;
    @BindView(R.id.ApplyResultRe7)
    RelativeLayout ApplyResultRe7;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.textApplyResultBankNum)
    TextView textApplyResultBankNum;
    @BindView(R.id.ApplyResultBankNum)
    TextView ApplyResultBankNum;
    @BindView(R.id.ApplyResultRe6)
    RelativeLayout ApplyResultRe6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_result);
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
                finish();
                break;
        }
    }
}
