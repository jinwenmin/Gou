package com.example.king.gou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.gou.R;
import com.example.king.gou.service.RetrofitService;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import it.sephiroth.android.library.picasso.Picasso;

public class WelcomeActivity extends AutoLayoutActivity {
    String[] path = new String[]{
            "file:///android_asset/welcome1.jpg", "file:///android_asset/welcome2.jpg"
    };

    @BindView(R.id.welcome_bg)
    ImageView welcomeBg;
    @BindView(R.id.passToAc)
    TextView passToAc;
    Handler handler;
    Runnable runnable;
    String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Random random = new Random();
        int i = random.nextInt(path.length);
        try {
            url = path[i];
            Picasso.with(this).load(url).into(welcomeBg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler();
        handler.postDelayed(runnable, 4000);
        passToAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                handler.removeCallbacks(runnable);
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
