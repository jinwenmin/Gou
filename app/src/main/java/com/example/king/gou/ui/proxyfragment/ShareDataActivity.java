package com.example.king.gou.ui.proxyfragment;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.ShareDataAdapter;
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
        adapter = new ShareDataAdapter(this);
        ShareList.setAdapter(adapter);
        initClick();
        RetrofitService.getInstance().getShareData(this);
        RetrofitService.getInstance().getGeneralizeSave(this, 10.0, 3);
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
                    }
                    if (!shareDatas.get(0).isChecked()) {
                        Stype.setText("非代理用户");
                    }
                    mxrate.setText(shareDatas.get(0).getMxrate() + "");
                    rate.setText(shareDatas.get(0).getRate() + "");
                    rebateId.setText(shareDatas.get(0).getRebate_id() + "");

                }
                if (sd.size() == 2) {
                    adapter.getList(sd.get(1));
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
                Toasty.success(ShareDataActivity.this,"复制成功",2000).show();
                break;
        }
    }

    @Override
    public void onItemClick(Object o, int position) {

    }
}
