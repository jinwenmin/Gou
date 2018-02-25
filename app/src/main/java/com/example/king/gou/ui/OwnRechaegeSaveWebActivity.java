package com.example.king.gou.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OwnRechaegeSaveWebActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id._Back)
    ImageView Back;
    @BindView(R.id.SaveWeb)
    WebView SaveWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_rechaege_save_web);
        ButterKnife.bind(this);
        Back.setOnClickListener(this);
        String web = getIntent().getStringExtra("web");
        WebSettings settings = SaveWeb.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        SaveWeb.setWebChromeClient(new WebChromeClient());
        SaveWeb.loadData(web,"text/html;charset=utf-8",null);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._Back:
                finish();
                break;
        }
    }
}
