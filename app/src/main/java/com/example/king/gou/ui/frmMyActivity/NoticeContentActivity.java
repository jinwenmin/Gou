package com.example.king.gou.ui.frmMyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.NoticeContent;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoticeContentActivity extends AppCompatActivity implements HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.NoticeContentTop)
    RelativeLayout NoticeContentTop;
    @BindView(R.id.NoticeContent_text)
    TextView NoticeContentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_content);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int uid = intent.getIntExtra("uid", 0);
        RetrofitService.getInstance().getNoticesContent(this, uid);
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_NOTICECONTENT2) {
            NoticeContent notice = (NoticeContent) object;
            if (notice.getRc() == false) {
                NoticeContentText.setText(notice.getOthers().get(0).getContent());
            }
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
