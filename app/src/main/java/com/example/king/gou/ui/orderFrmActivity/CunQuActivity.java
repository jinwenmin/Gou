package com.example.king.gou.ui.orderFrmActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CunQuActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id.gamejl_back)
    ImageView gamejlBack;
    @BindView(R.id.CunJl)
    RadioButton CunJl;
    @BindView(R.id.Qujl)
    RadioButton Qujl;
    @BindView(R.id.RechargeFirmTop)
    RelativeLayout RechargeFirmTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cun_qu);
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
