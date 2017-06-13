package com.example.king.gou.ui;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.king.gou.R;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends AutoLayoutActivity {
    String md5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                finish();

            }
        });
        String text = "AppClient=1&ipwd=false&p=a12345678&u=testapp&t=1497274998360";
        md5 = RxUtils.getInstance().md5(text);
        Log.i("MD5", md5);
    }

    public void Login() {
        String s = RxUtils.getInstance().SHA256("a12345678");
        Log.i("SHA256消息", s);
        RetrofitService.getInstance()
                .getLoginInfo(1, "testapp", "a12345678", false, md5, SystemClock.currentThreadTimeMillis())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i("Object消息", o.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Error消息", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Success消息", "");
                    }
                });


    }

    ;
}
