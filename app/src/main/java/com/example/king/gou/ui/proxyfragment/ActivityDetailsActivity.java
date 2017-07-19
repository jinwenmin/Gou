package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.UserActivity;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityDetailsActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener {

    @BindView(R.id.ActivityDetail)
    TextView ActivityDetail;
    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ActivityTop)
    RelativeLayout ActivityTop;
    @BindView(R.id.InjoinAc)
    TextView InjoinAc;
    int aid;
    UserActivity uc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        initClick();
        Intent intent = getIntent();
        aid = intent.getIntExtra("aid", 0);
        RetrofitService.getInstance().getActivityNoticesView(ActivityDetailsActivity.this, aid);
    }

    private void initClick() {
        Back.setOnClickListener(this);
        InjoinAc.setOnClickListener(this);
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_ACTIVITYDETAIL) {
            uc= (UserActivity) object;
            ActivityDetail.setText(Html.fromHtml(uc.getMsg()));
            if (uc.getOthers() == 0) {
                if (aid == 6 || aid == 7 || aid == 11 || aid == 12) {
                    InjoinAc.setText("报名参加");
                }
                if (aid == 9 || aid == 10) {
                    InjoinAc.setText("");
                    InjoinAc.setVisibility(View.GONE);
                } else {
                    InjoinAc.setText("立即领取");
                }
            }
            if (uc.getOthers() == 1) {
                if (aid == 6) {
                    InjoinAc.setText("立即领取");
                }
                if (aid == 12) {
                    InjoinAc.setText("立即砸蛋");
                }
                if (aid == 10 || aid == 9) {
                    InjoinAc.setText("");
                    InjoinAc.setVisibility(View.GONE);
                } else {
                    InjoinAc.setText("立即领取");
                }
            }
            if (uc.getOthers() == 2) {
                if (aid == 9 || aid == 10) {
                    InjoinAc.setText("");
                    InjoinAc.setVisibility(View.GONE);
                } else {
                    InjoinAc.setText("立即领取");
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.InjoinAc:
                if (uc.getOthers() == 0) {
                    if (aid == 6 || aid == 7 || aid == 11 || aid == 12) {
                       RetrofitService.getInstance().getActivityUserApply(this,aid);
                    }
                    if (aid == 9 || aid == 10) {
                        InjoinAc.setText("");
                        InjoinAc.setVisibility(View.GONE);
                    } else {
                        InjoinAc.setText("立即领取");
                    }
                }
                if (uc.getOthers() == 1) {
                    if (aid == 6) {
                        InjoinAc.setText("立即领取");
                    }
                    if (aid == 12) {
                        InjoinAc.setText("立即砸蛋");
                    }
                    if (aid == 10 || aid == 9) {
                        InjoinAc.setText("");
                        InjoinAc.setVisibility(View.GONE);
                    } else {
                        InjoinAc.setText("立即领取");
                    }
                }
                if (uc.getOthers() == 2) {
                    if (aid == 9 || aid == 10) {
                        InjoinAc.setText("");
                        InjoinAc.setVisibility(View.GONE);
                    } else {
                        InjoinAc.setText("立即领取");
                    }
                }
                break;
        }
    }
}
