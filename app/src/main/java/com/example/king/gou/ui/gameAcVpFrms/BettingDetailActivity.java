package com.example.king.gou.ui.gameAcVpFrms;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.BettingDetail;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class BettingDetailActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener {

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
    @BindView(R.id.BtnBetRevoke)
    Button BtnBetRevoke;
    private int bid;
    BettingDetail bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting_detail);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        bid = getIntent().getIntExtra("bid", 0);
        initClick();
        RetrofitService.getInstance().getBettingDetails(this, bid);
    }

    private void initClick() {
        Back.setOnClickListener(this);
        BtnBetRevoke.setOnClickListener(this);
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_BETTINGDETAIL) {
            if (object != null) {
                bd = (BettingDetail) object;
                if (bd.isRc()) {
                    serialNumber.setText(bd.getSerial_number());
                    buyTime.setText(bd.getBuy_time());
                    gname.setText(bd.getGname());
                    num.setText(bd.getNum() + "");
                    priceUnit.setText(bd.getPrice_unit() + "");
                    rate.setText(bd.getMinimum());
                    int bs = bd.getBstatus();
                    if (bs == 0) {
                        bstatus.setText("未购买");
                        bstatus.setTextColor(Color.parseColor("#666666"));
                    }
                    if (bs == 1) {
                        bstatus.setText("未开奖");
                        bstatus.setTextColor(Color.parseColor("#0000ff"));
                    }
                    if (bs == 2) {
                        bstatus.setText("本人撤单");
                        bstatus.setTextColor(Color.parseColor("#666666"));
                    }
                    if (bs == 3) {
                        bstatus.setText("管理员撤单");
                        bstatus.setTextColor(Color.parseColor("#666666"));
                    }
                    if (bs == 4) {
                        bstatus.setText("已过期");
                        bstatus.setTextColor(Color.parseColor("#666666"));
                    }
                    if (bs == 5) {
                        bstatus.setText("未中奖");
                        bstatus.setTextColor(Color.parseColor("#00802f"));
                    }
                    if (bs == 6) {
                        bstatus.setText("平台撤单");
                        bstatus.setTextColor(Color.parseColor("#666666"));
                    }
                    if (bs == 7) {
                        bstatus.setText("已派奖");
                        bstatus.setTextColor(Color.parseColor("#ff0000"));
                    }
                    pickedText.setText(Html.fromHtml(bd.getPicked_text()));
                    uname.setText(bd.getUname());
                    period.setText(bd.getPeriod());
                    rname.setText(bd.getRname());
                    multiple.setText(bd.getMultiple() + "");
                    amount.setText(bd.getAmount() + "");
                    winningNumbers.setText(bd.getWinning_numbers());
                    prize.setText(bd.getPrize() + "");
                    if (bd.getBstatus() < 2 && bd.getId() == MyApp.getInstance().getUserUid()) {
                        BtnBetRevoke.setVisibility(View.VISIBLE);
                    } else {
                        BtnBetRevoke.setVisibility(View.GONE);
                    }
                }
            }
        }
        if (apiId== RetrofitService.API_ID_BETREVOKE1) {
            if (object!=null) {
                RestultInfo restultInfo= (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(this,restultInfo.getMsg(),2000).show();
                    finish();
                }else{
                    Toasty.error(this,restultInfo.getMsg(),2000).show();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.BtnBetRevoke:
                RetrofitService.getInstance().getLotteryBetRevoke1(this, bd.getBid());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bd=null;
    }
}
