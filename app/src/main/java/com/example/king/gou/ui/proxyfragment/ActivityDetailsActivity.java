package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.R;
import com.example.king.gou.bean.JoinActivity;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.UserActivity;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import tyrantgit.explosionfield.ExplosionField;


public class ActivityDetailsActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener, OnItemClickListener {

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
    String name;
    JoinActivity joinActivity;
    @BindView(R.id.ActivityTitle)
    TextView ActivityTitle;
    @BindView(R.id.Bigegg)
    ImageView Bigegg;
    @BindView(R.id.Re1)
    RelativeLayout Re1;
    private AlertView alertView;
    // 一个自定义的布局，作为显示的内容
    View contentView;
    final int[] alid = {0};
    private ExplosionField mExplosionField;
    private AlertView alertView1;
    // 一个自定义的布局，作为显示的内容
    View contentView1;

    private AlertView alertView2;
    // 一个自定义的布局，作为显示的内容
    View contentView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        mExplosionField = ExplosionField.attach2Window(this);
        initClick();
        Intent intent = getIntent();
        aid = intent.getIntExtra("aid", 0);
        name = intent.getStringExtra("name");
        ActivityTitle.setText(name);
        alertView = new AlertView(null, null, "取消", new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentView = LayoutInflater.from(this).inflate(
                R.layout.select_alid, null);
        alertView.addExtView(contentView);

        alertView1 = new AlertView(null, null, null, null, null, this, AlertView.Style.Alert, this);
        contentView1 = LayoutInflater.from(this).inflate(
                R.layout.item_reward, null);
        alertView1.addExtView(contentView1);
        ImageView egg = (ImageView) contentView1.findViewById(R.id.egg);
        addListener(Bigegg);
        //  alertView1.show();

        alertView2 = new AlertView(null, null, null, new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentView2 = LayoutInflater.from(this).inflate(
                R.layout.item_reward_money, null);
        TextView money = (TextView) contentView2.findViewById(R.id.reward_money);
        money.setText("获得奖金99999元");
        alertView2.addExtView(contentView2);


        RetrofitService.getInstance().getActivityNoticesView(ActivityDetailsActivity.this, aid);
    }

    private void initClick() {
        Back.setOnClickListener(this);
        InjoinAc.setOnClickListener(this);
    }

    //砸蛋效果
    private void addListener(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                addListener(parent.getChildAt(i));
            }
        } else {
            root.setClickable(true);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertView1.dismiss();
                    Re1.setVisibility(View.GONE);
                    RetrofitService.getInstance().getActivityCheck(ActivityDetailsActivity.this, aid);
                    mExplosionField.explode(v);
                    v.setOnClickListener(null);
                }
            });
        }
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
            if (uc.getOthers() == -1) {
                InjoinAc.setText("");
                InjoinAc.setVisibility(View.GONE);
                return;
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
                    InjoinAc.setText("已完成");
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
        if (apiId == RetrofitService.API_ID_DRAWMONEY) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                    return;
                }
                if (!restultInfo.isRc()) {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
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
                if ("立即砸蛋".equals(InjoinAc.getText().toString().trim())) {

                    Re1.setVisibility(View.VISIBLE);
                    Bigegg.setVisibility(View.VISIBLE);
                    return;
                }
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
                }
                if (uc.getOthers() == 1) {
                    if ("立即领取".equals(InjoinAc.getText().toString().trim())) {
                        if (id != 6) {
                            RetrofitService.getInstance().getActivityCheck(this, aid);
                        } else if (id == 6) {
                            RadioGroup radioGroup = (RadioGroup) contentView.findViewById(R.id.select_radiogroup);
                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                                    if (i == R.id.Radio1) {
                                        alid[0] = 20;
                                    }
                                    if (i == R.id.Radio2) {
                                        alid[0] = 21;
                                    }
                                    if (i == R.id.Radio3) {
                                        alid[0] = 22;
                                    }
                                    if (i == R.id.Radio4) {
                                        alid[0] = 23;
                                    }
                                    if (i == R.id.Radio5) {
                                        alid[0] = 24;
                                    }
                                    if (i == R.id.Radio6) {
                                        alid[0] = 25;
                                    }
                                }
                            });
                            alertView.show();
                        }
                        return;
                    }

                }
                break;
        }
    }

    @Override
    public void onItemClick(Object o, int position) {
        if (position != AlertView.CANCELPOSITION) {
            RetrofitService.getInstance().getActivityCheckAid(this, aid, alid[0]);
        }
    }
}
