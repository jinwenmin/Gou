package com.example.king.gou.ui.settingfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AboutUsWebActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.AboutUsTitle)
    TextView AboutUsTitle;
    @BindView(R.id.Top)
    RelativeLayout Top;
    @BindView(R.id.aboutWeb)
    WebView aboutWeb;
    private String title;
    private String web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_web);
        ButterKnife.bind(this); MyApp.getInstance().addActivitys(this);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        web = intent.getStringExtra("web");

        Back.setOnClickListener(this);
        AboutUsTitle.setText(title);
        aboutWeb.setWebChromeClient(new WebChromeClient());
        aboutWeb.loadUrl(web);
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
