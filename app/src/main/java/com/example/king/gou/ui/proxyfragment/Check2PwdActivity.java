package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.Remittance;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.CheckRechargeActivity;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class Check2PwdActivity extends AutoLayoutActivity implements HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.Question)
    TextView Question;
    @BindView(R.id.CheckQuesEditText)
    EditText CheckQuesEditText;
    @BindView(R.id.CheckSafePwd)
    EditText CheckSafePwd;
    @BindView(R.id.ToCheckQues)
    Button ToCheckQues;
    private Intent intent;
    private String ruser;
    private int uid;
    private double sreChargeMin;
    private double sreChargeMax;
    String Checksafe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check2_pwd);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        intent = getIntent();
        uid = intent.getIntExtra("uid", 0);
        ruser = intent.getStringExtra("ruser");
        sreChargeMin = intent.getDoubleExtra("SreChargeMin", 0);
        sreChargeMax = intent.getDoubleExtra("SreChargeMax", 0);
        Question.setText(RetrofitService.getInstance().getUser().getQuestion());

    }

    @OnClick(R.id.ToCheckQues)
    public void onViewClicked() {
        String AnswerQuestion = CheckQuesEditText.getText().toString().trim();
         Checksafe = CheckSafePwd.getText().toString().trim();
        if (AnswerQuestion.equals("")) {
            Toasty.error(this, "安全答案不可为空", 2000).show();
            return;
        }
        if (Checksafe.equals("")) {
            Toasty.error(this, "安全密码不可为空", 2000).show();
            return;
        }
        RetrofitService.getInstance().getSecurityQuestionCheck(this, AnswerQuestion);
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_SAFEPWDCHECK) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc() == true) {
                    RetrofitService.getInstance().getCheckSafePwd(this,RxUtils.getInstance().HMACSHA256(Checksafe, RetrofitService.getInstance().getUser().getUname()));
                    return;
                }
                if (restultInfo.isRc() == false) {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                    return;
                }
            }
        }
        if (apiId == RetrofitService.API_ID_SAFEPWD) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc() == true) {
                    Intent inte = new Intent(Check2PwdActivity.this, CheckRechargeActivity.class);
                    inte.putExtra("uid", uid);
                    inte.putExtra("ruser", ruser);
                    inte.putExtra("SreChargeMin", sreChargeMin);
                    inte.putExtra("SreChargeMax", sreChargeMax);
                    startActivity(inte);
                    finish();
                }
                if (restultInfo.isRc() == false) {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                    return;
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
