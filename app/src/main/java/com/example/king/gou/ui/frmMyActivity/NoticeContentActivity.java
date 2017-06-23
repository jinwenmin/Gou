package com.example.king.gou.ui.frmMyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoticeContentActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.NoticeContentTop)
    RelativeLayout NoticeContentTop;
    @BindView(R.id.NoticeContent_text)
    TextView NoticeContentText;
    @BindView(R.id.NoticeUser_text)
    TextView NoticeUserText;
    @BindView(R.id.NoticeTime_text)
    TextView NoticeTimeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_content);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int uid = intent.getIntExtra("uid", 0);
        RetrofitService.getInstance().getNoticesContent(this, uid);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_NOTICECONTENT2) {
            String[] notice = (String[]) object;
            NoticeContentText.setText(Html.fromHtml(notice[0]));
            NoticeTimeText.setText(notice[1]);
            NoticeUserText.setText(notice[2]);
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


        }
    }
}
