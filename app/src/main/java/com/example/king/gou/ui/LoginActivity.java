package com.example.king.gou.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

public class LoginActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
            }
        });
    }
}
