package com.example.king.gou.ui.gameAcVpFrms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.ZhuiHao;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ZhuiHaoDetailActivity extends AutoLayoutActivity implements HttpEngine.DataListener {

    @BindView(R.id.gamejl_back)
    ImageView gamejlBack;
    @BindView(R.id.ZhuiHaoTop)
    RelativeLayout ZhuiHaoTop;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.purchase_date)
    TextView purchaseDate;
    @BindView(R.id.gname)
    TextView gname;
    @BindView(R.id.rname)
    TextView rname;
    @BindView(R.id.start_period)
    TextView startPeriod;
    @BindView(R.id.periods)
    TextView periods;
    @BindView(R.id.purchase_periods)
    TextView purchasePeriods;
    @BindView(R.id.picked_numbers)
    TextView pickedNumbers;
    @BindView(R.id.mode)
    TextView mode;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.purchase_amount)
    TextView purchaseAmount;
    @BindView(R.id.cancel_amount)
    TextView cancelAmount;
    @BindView(R.id.prize_num)
    TextView prizeNum;
    @BindView(R.id.prize_amount)
    TextView prizeAmount;
    @BindView(R.id.bids)
    TextView bids;
    @BindView(R.id.status)
    TextView status;
    ZhuiHao zh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhui_hao_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        zh = (ZhuiHao) intent.getSerializableExtra("ZH");
        id.setText(zh.getId() + "");
        RetrofitService.getInstance().getKeepNumDeTails(this, zh.getId());
        number.setText(zh.getNumber());
        purchaseDate.setText(zh.getPurchase_date());
        gname.setText(zh.getGname());
        rname.setText(zh.getRname());
        startPeriod.setText(zh.getStart_period());
        periods.setText(zh.getPeriods() + "");
        purchasePeriods.setText(zh.getPurchase_periods() + "");
        pickedNumbers.setText(zh.getPicked_numbers());
        mode.setText(zh.getMode() + "");
        amount.setText(zh.getAmount() + "");
        purchaseAmount.setText(zh.getPurchase_amount() + "");
        cancelAmount.setText(zh.getCancel_amount() + "");
        prizeNum.setText(zh.getPrize_num() + "");
        prizeAmount.setText(zh.getPrize_amount() + "");
        bids.setText(zh.getBids());
        status.setText(zh.getStatus() + "");
        initClick();
    }

    private void initClick() {

        gamejlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {

    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
