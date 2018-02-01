package com.example.king.gou.ui.proxyfragment;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.ShareDataAdapter;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.ShareData;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.MainActivity;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class ShareDataActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener, OnItemClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ProxyTop)
    RelativeLayout ProxyTop;
    @BindView(R.id.Stype)
    TextView Stype;
    @BindView(R.id.uid)
    TextView uid;
    @BindView(R.id.rebate_id)
    TextView rebateId;
    @BindView(R.id.mxrate)
    TextView mxrate;
    @BindView(R.id.rate)
    TextView rate;
    List<List<ShareData>> sd = new ArrayList<>();
    ShareDataAdapter adapter;
    @BindView(R.id.ShareList)
    ListView ShareList;
    @BindView(R.id.ShareCode)
    TextView ShareCode;
    @BindView(R.id.SaveShare)
    Button SaveShare;
    @BindView(R.id.copyText)
    TextView copyText;
    private AlertView alertView;
    // 一个自定义的布局，作为显示的内容
    View contentView;
    private EditText rateFW;
    private RadioGroup ragoup;
    int t = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_data);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        alertView = new AlertView(null, null, "取消", new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentView = LayoutInflater.from(this).inflate(
                R.layout.item_share, null);
        alertView.addExtView(contentView);
        rateFW = ((EditText) contentView.findViewById(R.id.rateFW));
        ragoup = ((RadioGroup) contentView.findViewById(R.id.rateType));
        ragoup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.rateType1) {
                    t = 2;
                }
                if (i == R.id.rateType2) {
                    t = 3;
                }
            }
        });
        adapter = new ShareDataAdapter(this);
        ShareList.setAdapter(adapter);
        initClick();
        RetrofitService.getInstance().getShareData(this);

    }

    private void initClick() {
        Back.setOnClickListener(this);
        SaveShare.setOnClickListener(this);
        copyText.setOnClickListener(this);
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_SHAREDATA) {
            if (object != null) {
                sd = (List<List<ShareData>>) object;
                if (sd.size() > 0) {
                    List<ShareData> shareDatas = sd.get(0);
                    uid.setText(shareDatas.get(0).getUid() + "");
                    ShareCode.setText(shareDatas.get(0).getShareCode());
                    if (shareDatas.get(0).isChecked()) {
                        Stype.setText("代理用户");
                        ((RadioButton) ragoup.getChildAt(0)).setChecked(true);
                    }
                    if (!shareDatas.get(0).isChecked()) {
                        Stype.setText("非代理用户");
                        ((RadioButton) ragoup.getChildAt(1)).setChecked(true);
                    }
                    mxrate.setText(shareDatas.get(0).getMxrate() + "");
                    rate.setText(shareDatas.get(0).getRate() + "");
                    rateFW.setText(shareDatas.get(0).getRate() + "");
                    rebateId.setText(shareDatas.get(0).getRebate_id() + "");

                }
                if (sd.size() == 2) {
                    adapter.getList(sd.get(1));
                }
            }
        }
        if (apiId == RetrofitService.API_ID_GENERALIZESAVE) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    RetrofitService.getInstance().getShareData(this);
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                } else {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
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
            case R.id.SaveShare:
                alertView.show();
                break;
            case R.id.copyText:
                ClipboardManager copy = (ClipboardManager) ShareDataActivity.this
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText(ShareCode.getText().toString() + "");
                Toasty.success(ShareDataActivity.this, "复制成功", 2000).show();
                break;
        }
    }

    @Override
    public void onItemClick(Object o, int position) {
        if (position == AlertView.CANCELPOSITION) {
            alertView.dismiss();
        } else {
            String ltext = rateFW.getText().toString().trim();
            if (ltext.length() == 0) {
                Toasty.error(this, "请输入推广返点", 2000).show();
                return;
            }
            RetrofitService.getInstance().getGeneralizeSave(this, Double.parseDouble(ltext), t);
        }
    }
}
