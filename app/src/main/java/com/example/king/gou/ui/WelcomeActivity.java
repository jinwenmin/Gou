package com.example.king.gou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.gou.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

public class WelcomeActivity extends BaseActivity {


    @BindView(R.id.welcome_bg)
    ImageView welcomeBg;
    @BindView(R.id.passToAc)
    TextView passToAc;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        try {
            Picasso.with(this).load("file:///android_asset/bg_loading_page2.webp").into(welcomeBg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler();
        handler.postDelayed(runnable, 4000);
        passToAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                Toast.makeText(WelcomeActivity.this, "准备跳转", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //2s后跳到主页
    private void handler() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                Toast.makeText(WelcomeActivity.this, "准备跳转", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
