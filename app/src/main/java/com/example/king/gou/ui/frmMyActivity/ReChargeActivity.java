package com.example.king.gou.ui.frmMyActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.AliPay;
import com.example.king.gou.bean.AliPayBean;
import com.example.king.gou.bean.EBao;
import com.example.king.gou.bean.Remittance;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class ReChargeActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener, OnItemClickListener {

    @BindView(R.id.RechargeTypeText)
    TextView RechargeTypeText;
    @BindView(R.id.RechargeZhiFuBao)
    RadioButton RechargeZhiFuBao;
    @BindView(R.id.RechargeYB)
    RadioButton RechargeYB;
    @BindView(R.id.RechargeRadioGroup)
    RadioGroup RechargeRadioGroup;

    @BindView(R.id.textMoney)
    TextView textMoney;
    @BindView(R.id.RechargeMoney)
    EditText RechargeMoney;
    @BindView(R.id.ChongZ)
    Button ChongZ;
    @BindView(R.id.TopBack)
    ImageView TopBack;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.BankSpinner)
    Spinner BankSpinner;
    @BindView(R.id.RechargeRe)
    LinearLayout RechargeRe;
    @BindView(R.id.RechargeHolders_name)
    EditText RechargeHoldersName;
    @BindView(R.id.LinearHolders_name)
    LinearLayout LinearHoldersName;
    @BindView(R.id.RechargeAccount_number)
    EditText RechargeAccountNumber;
    @BindView(R.id.LinearAccount_number)
    LinearLayout LinearAccountNumber;
    @BindView(R.id.RechargeZhiFuBaoRemit)
    RadioButton RechargeZhiFuBaoRemit;
    private ArrayAdapter<String> adapterType2;
    EBao eBao;
    String type = "zfb";
    double min1;
    double min2;
    double max1;
    double max2;
    double minYB;
    double maxYB;
    private AlertView alertView;
    // 一个自定义的布局，作为显示的内容
    View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_charge);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        alertView = new AlertView(null, null, null, new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentView = LayoutInflater.from(this).inflate(
                R.layout.show_recharge, null);
        alertView.addExtView(contentView);
        RetrofitService.getInstance().getYeePay(this);
        RetrofitService.getInstance().getAliPay(this);
        RetrofitService.getInstance().getRemittance(this);
        initClick();

    }

    private void initClick() {
        ChongZ.setOnClickListener(this);
        TopBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ChongZ:
                String money = RechargeMoney.getText().toString().trim();
                if (money.equals("")) {
                    Toasty.error(this, "金额不可为空", 2000).show();
                    return;
                }

                Integer m = Integer.parseInt(money);

                if (type.equals("zfb") || type.equals("remit")) {
                    int types = 0;
                    if (type.equals("zfb")) {
                        types = 0;
                    } else if (type.equals("remit")) {
                        types = 1;
                    }
                    String HoldersName = RechargeHoldersName.getText().toString();
                    String AccountNumber = RechargeAccountNumber.getText().toString();
                    if (HoldersName.equals("")) {
                        Toasty.error(this, "真实姓名为空", 2000).show();
                        return;
                    }
                    if (AccountNumber.equals("")) {
                        Toasty.error(this, "银行卡号或支付宝账号为空", 2000).show();
                        return;
                    }
                    RetrofitService.getInstance().getAliPlayReChargeSave(this, types, m, HoldersName, AccountNumber);

                } else if (type.equals("yb")) {

                    RetrofitService.getInstance().getOwnRechargeSave(this, "YTX_Payment", eBao.getOthers().getBanks().get(BankSpinner.getSelectedItemPosition()).getCode(), m);
                }
                ///startActivity(new Intent(ReChargeActivity.this, RechargeComfirmActivity.class));
                break;
            case R.id.TopBack:
                finish();
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_YEEPAY) {

            if (object != null) {
                eBao = (EBao) object;
                minYB = eBao.getOthers().getMin();
                maxYB = eBao.getOthers().getMax();
                if (eBao.getOthers().isFreeze()) {
                    alertView.show();
                    return;
                }
                List<String> name = new ArrayList<>();
                for (int i = 0; i < eBao.getOthers().getBanks().size(); i++) {
                    name.add(eBao.getOthers().getBanks().get(i).getName());
                }

                adapterType2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapterType2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                BankSpinner.setAdapter(adapterType2);
            }

        }
        if (apiId == RetrofitService.API_ID_OWNRECHARGESAVE) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Intent intent = new Intent();
//Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(restultInfo.getMsg());
                    intent.setData(content_url);
                    startActivity(intent);
                } else {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                    return;
                }
            }
        }
        if (apiId == RetrofitService.API_ID_ALIPAYRECHARGESAVE) {
            if (object != null) {
                AliPayBean aliPayBean = (AliPayBean) object;
                if (aliPayBean.isRc()) {
                    RetrofitService.getInstance().getAliPlayReChargeSubmit(this, aliPayBean.getId(), aliPayBean.getOthers().getNote());
                }
            }
        }

        if (apiId == RetrofitService.API_ID_ALIPAYSUBMIT) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                } else {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                    return;
                }
            }
        }
        if (apiId == RetrofitService.API_ID_ALIPAY) {
            if (object != null) {
                AliPay aliPay = (AliPay) object;
                min1 = aliPay.getOthers().getMin();
                max1 = aliPay.getOthers().getMax();
                RechargeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (i == R.id.RechargeZhiFuBao) {
                            type = "zfb";
                            BankSpinner.setVisibility(View.GONE);
                            LinearAccountNumber.setVisibility(View.VISIBLE);
                            LinearHoldersName.setVisibility(View.VISIBLE);
                            RechargeMoney.setHint("充值范围:" + min1 + "-" + max1);
                        } else if (i == R.id.RechargeZhiFuBaoRemit) {
                            type = "remit";
                            BankSpinner.setVisibility(View.GONE);
                            LinearAccountNumber.setVisibility(View.VISIBLE);
                            LinearHoldersName.setVisibility(View.VISIBLE);
                            RechargeMoney.setHint("充值范围:" + min2 + "-" + max2);
                        } else if (i == R.id.RechargeYB) {
                            type = "yb";
                            BankSpinner.setVisibility(View.VISIBLE);
                            LinearAccountNumber.setVisibility(View.GONE);
                            LinearHoldersName.setVisibility(View.GONE);
                            RechargeMoney.setHint("充值范围:" + minYB + "-" + maxYB);
                        }
                    }
                });
                RechargeZhiFuBao.setChecked(true);
            }
        }
        if (apiId == RetrofitService.API_ID_REMITTANCE) {
            if (object != null) {
                Remittance remittance = (Remittance) object;
                min2 = remittance.getOthers().getMin();
                max2 = remittance.getOthers().getMax();
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
    protected void onDestroy() {
        super.onDestroy();
        adapterType2 = null;
        eBao = null;
    }

    @Override
    public void onItemClick(Object o, int position) {
        finish();
    }
}
