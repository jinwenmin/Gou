package com.example.king.gou.ui.orderFrmActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ZhuiHaoActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id.gamejl_back)
    ImageView gamejlBack;
    @BindView(R.id.ZhuiHaoTop)
    RelativeLayout ZhuiHaoTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhui_hao);
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
