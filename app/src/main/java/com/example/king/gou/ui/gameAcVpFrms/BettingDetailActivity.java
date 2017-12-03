package com.example.king.gou.ui.gameAcVpFrms;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BettingDetailActivity extends AutoLayoutActivity implements HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.serial_number)
    TextView serialNumber;
    @BindView(R.id.buy_time)
    TextView buyTime;
    @BindView(R.id.gname)
    TextView gname;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.price_unit)
    TextView priceUnit;
    @BindView(R.id.rate)
    TextView rate;
    @BindView(R.id.bstatus)
    TextView bstatus;
    @BindView(R.id.picked_text)
    TextView pickedText;
    @BindView(R.id.uname)
    TextView uname;
    @BindView(R.id.period)
    TextView period;
    @BindView(R.id.rname)
    TextView rname;
    @BindView(R.id.multiple)
    TextView multiple;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.winning_numbers)
    TextView winningNumbers;
    @BindView(R.id.prize)
    TextView prize;
    private int bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting_detail);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        bid = getIntent().getIntExtra("bid", 0);
        RetrofitService.getInstance().getBettingDetails(this, bid);
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
