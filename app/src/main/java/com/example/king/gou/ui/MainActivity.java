package com.example.king.gou.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.example.king.gou.bean.LoginState;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AutoLayoutActivity implements HttpEngine.DataListener {
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
    long TIME = 1000;
    private Timer timer;
    private String login_sessionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        login_userinfo = getSharedPreferences("login_userinfo", Activity.MODE_PRIVATE);
        login_uid = login_userinfo.getInt("login_uid", 0);
        login_sessionid = login_userinfo.getString("login_sessionid", "");
        Log.i("测试信息==", login_sessionid);

        initTimer();
        //  RetrofitService.getInstance().LogOut();
        Log.i("测试信息==", "走过2");
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

    private void initTimer() {
        timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                 RetrofitService.getInstance().LoginState(MainActivity.this, login_uid, 1, 0, new String[]{String.valueOf(login_uid)}, 1);

            }
        };
        timer.schedule(timerTask, 0, 3000);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_LOGINSTATE) {
            LoginState loginState = (LoginState) object;
            System.out.println("用户状态信息==" + loginState.toString());
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
