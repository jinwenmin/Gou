package com.example.king.gou.ui.orderFrmActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TouZhuResultActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id.gamejl_back)
    ImageView gamejlBack;
    @BindView(R.id.RechargeFirmTop)
    RelativeLayout RechargeFirmTop;
    @BindView(R.id.orderName)
    TextView orderName;
    @BindView(R.id.orderTime)
    TextView orderTime;
    @BindView(R.id.TouZiMoney)
    TextView TouZiMoney;
    @BindView(R.id.JiangJin)
    TextView JiangJin;
    @BindView(R.id.TouZiQihao)
    TextView TouZiQihao;
    @BindView(R.id.TouZhiMoshi)
    TextView TouZhiMoshi;
    @BindView(R.id.TouZiBeiShu)
    TextView TouZiBeiShu;
    @BindView(R.id.TouZiZhuangT)
    TextView TouZiZhuangT;
    @BindView(R.id.TouZiZJMoney)
    TextView TouZiZJMoney;
    @BindView(R.id.TouZiKJNum)
    TextView TouZiKJNum;
    @BindView(R.id.TouZhuNeirong)
    TextView TouZhuNeirong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tou_zhu_result);
        ButterKnife.bind(this);
        initClick();
    }

    private void initClick() {
        gamejlBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gamejl_back:
                finish();
                break;
        }


    }
}
