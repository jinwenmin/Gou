package com.example.king.gou.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AutoLayoutActivity {
    @BindView(R.id.HomeFrmRadioBtn)
    RadioButton HomeFrmRadioBtn;
    @BindView(R.id.GameFrmRadioBtn)
    RadioButton GameFrmRadioBtn;
    @BindView(R.id.FindFrmRadioBtn)
    RadioButton FindFrmBtn;
    @BindView(R.id.MyFrmRadioBtn)
    RadioButton MyFrmBtn;
    @BindView(R.id.MainActivity_group)
    RadioGroup MainActivityGroup;
    private Fragment[] mFragments = new Fragment[4];
    private FragmentManager supportFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private SharedPreferences login_userinfo;
    private int login_uid;
    private Handler handler;
    long TIME = 1000;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        login_userinfo = getSharedPreferences("login_userinfo", Activity.MODE_PRIVATE);
        login_uid = login_userinfo.getInt("login_uid", 0);
        initTime();
        handler.postDelayed(runnable, TIME); //每隔1s执行
        supportFragmentManager = getSupportFragmentManager();
        mFragments[0] = supportFragmentManager.findFragmentById(R.id.fragment_home);
        mFragments[1] = supportFragmentManager.findFragmentById(R.id.fragment_game);
        mFragments[2] = supportFragmentManager.findFragmentById(R.id.fragment_find);
        mFragments[3] = supportFragmentManager.findFragmentById(R.id.fragment_my);


        mFragmentTransaction = supportFragmentManager.beginTransaction();
        mFragmentTransaction.show(mFragments[0]);
        mFragmentTransaction.hide(mFragments[1]);
        mFragmentTransaction.hide(mFragments[2]);
        mFragmentTransaction.hide(mFragments[3]);
        mFragmentTransaction.commit();
        MainActivityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mFragmentTransaction = supportFragmentManager.beginTransaction();
                mFragmentTransaction.hide(mFragments[0]);
                mFragmentTransaction.hide(mFragments[1]);
                mFragmentTransaction.hide(mFragments[2]);
                mFragmentTransaction.hide(mFragments[3]);


                if (checkedId == R.id.HomeFrmRadioBtn) {
                    mFragmentTransaction.show(mFragments[0]).commit();
                } else if (checkedId == R.id.GameFrmRadioBtn) {
                    mFragmentTransaction.show(mFragments[1]).commit();
                } else if (checkedId == R.id.FindFrmRadioBtn) {
                    mFragmentTransaction.show(mFragments[2]).commit();
                } else if (checkedId == R.id.MyFrmRadioBtn) {
                    mFragmentTransaction.show(mFragments[3]).commit();
                }
            }
        });
    }

    private void initTime() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, TIME);
                Log.i("循环请求", "");
            }
        };
    }
}
