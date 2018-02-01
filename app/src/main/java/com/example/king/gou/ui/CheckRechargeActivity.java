package com.example.king.gou.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.SreCharge;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class CheckRechargeActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener, OnItemClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.safeQues)
    TextView safeQues;
    @BindView(R.id.AnswerQues)
    EditText AnswerQues;
    @BindView(R.id.AnserSafePwd)
    EditText AnserSafePwd;
    @BindView(R.id.BtnCheck)
    Button BtnCheck;
    private String answerSafePwd;
    private int uid;
    private AlertView alertView2;
    // 一个自定义的布局，作为显示的内容
    View contentView2;
    SreCharge sreCharge;
    private TextView proxyName;
    private EditText setTrans;
    private TextView username;
    double SreChargeMin;
    double SreChargeMax;
    private double amounts1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_recharge);
        MyApp.getInstance().addActivitys(this);
        ButterKnife.bind(this);
        uid = getIntent().getIntExtra("uid", 0);
        safeQues.setText(RetrofitService.getInstance().getUser().getQuestion());
        alertView2 = new AlertView(null, null, "取消", new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentView2 = LayoutInflater.from(this).inflate(
                R.layout.proxy_owntransfer, null);
        alertView2.addExtView(contentView2);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        BtnCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.BtnCheck:
                String answerQ = AnswerQues.getText().toString().trim();
                answerSafePwd = AnserSafePwd.getText().toString().trim();
                if ("".equals(answerQ)) {
                    Toasty.error(this, "安全答案不能为空", 2000).show();
                    return;
                }
                if ("".equals(answerSafePwd)) {
                    Toasty.error(this, "安全密码不能为空", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getSecurityQuestionCheck(this, answerQ);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_SAFEPWDCHECK) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    answerSafePwd = RxUtils.getInstance().HMACSHA256(answerSafePwd, RetrofitService.getInstance().getUser().getUname());
                    RetrofitService.getInstance().getCheckSafePwd(this, answerSafePwd);
                } else {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                    return;
                }
            }
        }
        if (apiId == RetrofitService.API_ID_SAFEPWD) {
            if (object != null) {
                RestultInfo info = (RestultInfo) object;
                if (info.isRc()) {
                    RetrofitService.getInstance().getSreChargeData(this, uid);
                } else {

                    Toasty.error(CheckRechargeActivity.this, info.getMsg(), 2000).show();
                    return;
                }
            }
        }
        if (apiId == RetrofitService.API_ID_SRECHARGE) {
            sreCharge = (SreCharge) object;
            if (sreCharge.getStype() == 0) {
                Toasty.error(this, "没有充值权限", 2000).show();
                return;
            }
            if (sreCharge.getStype() == 1) {
                Toasty.success(this, "直属下级可充值", 2000).show();
            }
            if (sreCharge.getStype() == 2) {
                Toasty.success(this, "所有下级可充值", 2000).show();
            }

            proxyName = ((TextView) contentView2.findViewById(R.id.proxy_name));
            username = ((TextView) contentView2.findViewById(R.id.UserTitle));
            proxyName.setText(sreCharge.getRuser());
            setTrans = ((EditText) contentView2.findViewById(R.id.Proxy_setTrans));
            SreChargeMin = sreCharge.getMin1();
            SreChargeMax = sreCharge.getMax1();
            amounts1 = sreCharge.getAmounts1();
            if (amounts1 < SreChargeMax) {
                SreChargeMax = amounts1;
            }
            setTrans.setHint("充值范围:" + SreChargeMin + "~" + SreChargeMax);
            alertView2.show();
        }
        if (apiId == RetrofitService.API_ID_OWNTRANSFER) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                }
                if (!restultInfo.isRc()) {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
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

    @Override
    public void onItemClick(Object o, int position) {
        if (position == AlertView.CANCELPOSITION) {
            Log.d("充值测试AlertView", "取消");
            alertView2.dismiss();
            finish();
        } else {
            Log.d("充值测试AlertView", "确认");
            String trim = setTrans.getText().toString().trim();
            if (!"".equals(trim)) {
                double editV = Double.parseDouble(trim);

                if (editV > SreChargeMax || editV < SreChargeMin) {
                    Toasty.error(this, "充值金额不在范围内", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getOwnReansferTrans(this, editV, proxyName.getText().toString().trim());
                setTrans.setText("");
            }
        }
        finish();
    }
}
