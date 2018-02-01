package com.example.king.gou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.UserInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.settingfragment.SaveCardActivity;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class CheckBankCardActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.UserName)
    EditText UserName;
    @BindView(R.id.BankCardNum)
    EditText BankCardNum;
    @BindView(R.id.CheckBankCard)
    Button CheckBankCard;
    private String username;
    private String bankNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bank_card);
        ButterKnife.bind(this);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        CheckBankCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.CheckBankCard:
                username = UserName.getText().toString().trim();
                bankNum = BankCardNum.getText().toString().trim();
                if (username.equals("")) {
                    Toasty.error(CheckBankCardActivity.this, "用户名不可为空", 2000).show();
                    return;
                }
                if (bankNum.equals("")) {
                    Toasty.error(CheckBankCardActivity.this, "银行卡号不可为空", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getCheckCardNum(this, username, bankNum);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_CHECKCAEDNUM) {
            if (object != null) {
                RestultInfo restult = (RestultInfo) object;
                if (!restult.isRc()) {
                    startActivity(new Intent(CheckBankCardActivity.this, SaveCardActivity.class));
                    finish();
                } else {
                    Toasty.error(CheckBankCardActivity.this, restult.getMsg(), 2000).show();
                }
            }
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
