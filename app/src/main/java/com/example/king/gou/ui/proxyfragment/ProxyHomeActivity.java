package com.example.king.gou.ui.proxyfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProxyHomeActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ProxyTop)
    RelativeLayout ProxyTop;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.View1)
    View View1;
    @BindView(R.id.tuandui_num)
    TextView tuanduiNum;
    @BindView(R.id.Proxy_num)
    TextView ProxyNum;
    @BindView(R.id.Linear1)
    LinearLayout Linear1;
    @BindView(R.id.wanjia_num)
    TextView wanjiaNum;
    @BindView(R.id.TuanDui_Money)
    TextView TuanDuiMoney;
    @BindView(R.id.Linear2)
    LinearLayout Linear2;
    @BindView(R.id.proxy_home_spinner)
    Spinner proxyHomeSpinner;
    @BindView(R.id.View2)
    View View2;
    @BindView(R.id.Linear3)
    LinearLayout Linear3;
    @BindView(R.id.Linear4)
    LinearLayout Linear4;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.Linear5)
    LinearLayout Linear5;
    @BindView(R.id.Linear6)
    LinearLayout Linear6;
    @BindView(R.id.View3)
    View View3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_home);
        ButterKnife.bind(this);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
        }

    }
}
