package com.example.king.gou.ui.frmMyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoticeContentActivity extends AppCompatActivity implements HttpEngine.DataListener, View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.NoticeContentTop)
    RelativeLayout NoticeContentTop;
    @BindView(R.id.NoticeContent_text)
    TextView NoticeContentText;
    @BindView(R.id.linear1)
    LinearLayout linear1;

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
            String notice = (String) object;
            NoticeContentText.setText(Html.fromHtml(notice));

         /*   // 示例:演示设置不同文字的字体大小
            TextViewForFullHtml textViewTextSize = new TextViewForFullHtml(this);
            textViewTextSize.loadContent(mContentTextSize);
            // 示例:演示设置不同文字的对齐风格——居中
            TextViewForFullHtml textViewGravityCenter = new TextViewForFullHtml(this);
            textViewGravityCenter.loadContent(mContentGravityCenter);
            // 示例:演示设置不同文字的对齐风格——右对齐
            TextViewForFullHtml textViewGravityRight = new TextViewForFullHtml(this);
            textViewGravityRight.loadContent(mContentGravityRight);
            // 示例:演示设置不同文字的字体风格
            TextViewForFullHtml textViewStyle = new TextViewForFullHtml(this);
            textViewStyle.loadContent(mContentStyle);
            // 示例:演示设置不同文字的超链接
            TextViewForFullHtml textViewUrl = new TextViewForFullHtml(this);
            textViewUrl.loadContent(mContentUrl);

            linear1.addView(textViewTextSize);
            linear1.addView(textViewGravityCenter);
            linear1.addView(textViewGravityRight);
            linear1.addView(textViewStyle);
            linear1.addView(textViewUrl);*/
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
