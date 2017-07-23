package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.JoinActivity;
import com.example.king.gou.bean.UserActivity;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


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
    int id;
    JoinActivity joinActivity;

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
            uc = (UserActivity) object;
            ActivityDetail.setText(Html.fromHtml(uc.getMsg()));
            id = uc.getId();
            Log.d("ActivityDetailId", id + "");
            Log.d("ActivityDetailgetOthers", uc.getOthers() + "");
            if (uc.getOthers() == 0) {
                if (id == 6 || id == 7 || id == 11 || id == 12) {
                    InjoinAc.setText("报名参加");
                    return;
                }
                if (id == 9 || id == 10) {
                    InjoinAc.setText("");
                    InjoinAc.setVisibility(View.GONE);
                    return;
                } else {
                    InjoinAc.setText("立即领取");
                    return;
                }
            }
            if (uc.getOthers() == 1) {
                if (id == 6) {
                    InjoinAc.setText("立即领取");
                    return;
                }
                if (id == 12) {
                    InjoinAc.setText("立即砸蛋");
                    return;
                }
                if (id == 10 || aid == 9) {
                    InjoinAc.setText("");
                    InjoinAc.setVisibility(View.GONE);
                    return;
                } else {
                    InjoinAc.setText("立即领取");
                    return;
                }
            }
            if (uc.getOthers() == 2) {
                if (id == 9 || id == 10) {
                    InjoinAc.setText("");
                    InjoinAc.setVisibility(View.GONE);
                    return;
                } else {
                    InjoinAc.setText("立即领取");
                    return;
                }
            }
        }
        if (apiId == RetrofitService.API_ID_JOINACTIVITY) {
            if (object != null) {
                joinActivity = (JoinActivity) object;
                if (joinActivity.isRc()) {
                    Toasty.success(this, joinActivity.getMsg(), 2000).show();
                    return;
                } else {
                    Toasty.error(this, joinActivity.getMsg(), 2000).show();
                    return;
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
                    if ("报名参加".equals(InjoinAc.getText().toString().trim())) {
                        RetrofitService.getInstance().getActivityUserApply(this, aid);
                        return;
                    }
                    if ("立即领取".equals(InjoinAc.getText().toString().trim())) {
                        if (id != 6) {
                            RetrofitService.getInstance().getActivityCheck(this, aid);
                        }
                        return;
                    }
                    if (id == 9 || id == 10) {

                        return;
                    } else {
                        return;
                    }
                }
                if (uc.getOthers() == 1) {
                    if (id == 6) {
                        InjoinAc.setText("立即领取");
                        return;
                    }
                    if (id == 12) {
                        InjoinAc.setText("立即砸蛋");
                        return;
                    }
                    if (id == 10 || id == 9) {
                        InjoinAc.setText("");
                        InjoinAc.setVisibility(View.GONE);
                        return;
                    } else {
                        InjoinAc.setText("立即领取");
                    }
                }
                if (uc.getOthers() == 2) {
                    if (id == 9 || id == 10) {
                        InjoinAc.setText("");
                        InjoinAc.setVisibility(View.GONE);
                        return;
                    } else {
                        InjoinAc.setText("立即领取");
                        return;
                    }
                }
                break;
        }
    }
}
