package com.example.king.gou.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;


public class LotteryZhuiHaoActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_zhui_hao);
        MyApp.getInstance().addActivitys(this);
    }
}
