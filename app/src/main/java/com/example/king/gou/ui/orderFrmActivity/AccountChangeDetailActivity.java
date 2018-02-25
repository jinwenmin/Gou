package com.example.king.gou.ui.orderFrmActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.AccountChange;
import com.example.king.gou.bean.ZhuiHao;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AccountChangeDetailActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id.gamejl_back)
    ImageView gamejlBack;
    @BindView(R.id.ZhuiHaoTop)
    RelativeLayout ZhuiHaoTop;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.stype)
    TextView Stype;
    @BindView(R.id.gname)
    TextView gname;
    @BindView(R.id.rname)
    TextView rname;
    @BindView(R.id.period)
    TextView period;
    @BindView(R.id.model)
    TextView model;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.amounts)
    TextView amounts;
    AccountChange ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_change_detail);
        ButterKnife.bind(this); MyApp.getInstance().addActivitys(this);
        gamejlBack.setOnClickListener(this);
        Intent intent = getIntent();
        ac = (AccountChange) intent.getSerializableExtra("Account");
        id.setText(ac.getId() + "");
        date.setText(ac.getDate() + "");
        if (ac.getStype() != -1) {
            int stype = ac.getStype();
            if (stype == 1) {
                Stype.setText("加入游戏");
            }
            if (stype == 2) {
                Stype.setText("投注返点");
            }
            if (stype == 3) {
                Stype.setText("奖金派送");
            }
            if (stype == 4) {
                Stype.setText("追号扣款");
            }
            if (stype == 5) {
                Stype.setText("当期追号返款");
            }
            if (stype == 6) {
                Stype.setText("游戏扣款");
            }
            if (stype == 7) {
                Stype.setText("撤单返款");
            }
            if (stype == 8) {
                Stype.setText("撤销返点");
            }
            if (stype == 9) {
                Stype.setText("撤销派奖");
            }
            gname.setText(ac.getGname() + "");
            rname.setText(ac.getRname() + "");
            period.setText(ac.getPeriod() + "");
            int model1 = ac.getModel();
            if (model1 != 0) {
                if (model1 == 1) {
                    model.setText("元");
                }
                if (model1 == 2) {
                    model.setText("角");
                }
                if (model1 == 3) {
                    model.setText("分");
                }
                if (model1 == 4) {
                    model.setText("厘");
                }
            }
            amount.setText(ac.getAmount() + "");
            amounts.setText(ac.getAmounts() + "");

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gamejl_back:
                finish();
                break;
        }
    }
}