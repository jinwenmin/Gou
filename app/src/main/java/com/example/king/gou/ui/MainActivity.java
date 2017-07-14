package com.example.king.gou.ui;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.LoginState;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.DataBaseHelper;
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
    private AlertView alertView;
    ; // 一个自定义的布局，作为显示的内容
    View contentView;
    private ImageView fingerImg;
    private TextView fingerText;
    private final static String TAG = "finger_log";
    FingerprintManager manager;
    KeyguardManager mKeyManager;
    private final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0;
    DataBaseHelper dataBaseHelper;
    DataBaseHelper dataBaseFingerHelper;

    String id = null;

    String name = null;
    String isfinger;
    SQLiteDatabase databaseFinger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RetrofitService.getInstance().getChatUser(this);
        dataBaseHelper = new DataBaseHelper(MainActivity.this, "yigou.db", null, 1);
        /* 创建两张表 */
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();
        writableDatabase.execSQL("create table  if not exists student(id INTEGER PRIMARY KEY autoincrement,name text);");
        /* 给表添加数据： *//* 方式1： *//* 增加一条数据 */
        writableDatabase.execSQL("insert into student(name) values('mike')");
        Cursor cursor = writableDatabase.query("student", new String[]{"id", "name"}, "id=?", new String[]{"1"}, null, null, null);
        while (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex("id"));
            name = cursor.getString(cursor.getColumnIndex("name"));
        }
        Toast.makeText(MainActivity.this, "查询数据为：id=" + id + " \n name=" + name, Toast.LENGTH_LONG).show();


    /*    manager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
*/
        dataBaseFingerHelper = new DataBaseHelper(MainActivity.this, "fing.db", null, 1);
        /* 创建两张表 */
        databaseFinger = dataBaseFingerHelper.getWritableDatabase();
        databaseFinger.execSQL("create table  if not exists fingers(id INTEGER PRIMARY KEY autoincrement,isfinger String);");
        databaseFinger.execSQL("insert into fingers(isfinger) values('true')");
        databaseFinger.execSQL("update fingers set isfinger='true' where id=0");
        Cursor cursor2 = databaseFinger.query("fingers", new String[]{"id", "isfinger"}, "id=?", new String[]{"0"}, null, null, null);
        int sdkInt = Build.VERSION.SDK_INT;
        Log.d("手机版本", sdkInt + "");
        while (cursor2.moveToNext()) {
            String isfinger = cursor2.getString(cursor2.getColumnIndex("isfinger"));
            Log.d("Finger===", isfinger);
        }
      /*  while (cursor2.moveToNext()) {
            isfinger = cursor2.getString(cursor2.getColumnIndex("isfinger"));
            Log.d("这个isFInger==", isfinger);
            if ("true".equals(isfinger)) {
                if (isFinger()) {
                    alertView = new AlertView(null, null, "取消", null, null, MainActivity.this, AlertView.Style.Alert, MainActivity.this);
                    contentView = LayoutInflater.from(getApplicationContext()).inflate(
                            R.layout.item_finger, null);
                    alertView.addExtView(contentView);
                    fingerImg = ((ImageView) contentView.findViewById(R.id.fingerImg));
                    fingerText = ((TextView) contentView.findViewById(R.id.fingerText));
                    alertView.show();
                    Toast.makeText(MainActivity.this, "请进行指纹识别", Toast.LENGTH_LONG).show();
                    Log(TAG, "keyi");
                    startListening(null);
                }
            }
        }*/


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
               // RetrofitService.getInstance().getTokenSignin(MainActivity.this);

            }
        };
        timer.schedule(timerTask, 0, 5000);

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }
/*
    public boolean isFinger() {
        if (Build.VERSION.SDK_INT>23) {

        }
        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log(TAG, "有指纹权限");
        //判断硬件是否支持指纹识别
        if (!manager.isHardwareDetected()) {
            Toast.makeText(this, "没有指纹识别模块", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log(TAG, "有指纹模块");
        //判断 是否开启锁屏密码

        if (!mKeyManager.isKeyguardSecure()) {
            Toast.makeText(this, "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log(TAG, "已开启锁屏密码");
        //判断是否有指纹录入
        if (!manager.hasEnrolledFingerprints()) {
            Toast.makeText(this, "没有录入指纹", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log(TAG, "已录入指纹");

        return true;
    }

    CancellationSignal mCancellationSignal = new CancellationSignal();
    //回调方法

    FingerprintManager.AuthenticationCallback mSelfCancelled = new FingerprintManager.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            //但多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
            Toast.makeText(MainActivity.this, errString, Toast.LENGTH_SHORT).show();
            showAuthenticationScreen();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

            Toast.makeText(MainActivity.this, helpString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            fingerText.setText("指纹识别成功");
            fingerText.setTextColor(Color.rgb(0, 150, 136));
            fingerImg.setImageResource(R.drawable.ic_fingerprint_success);
            Toast.makeText(MainActivity.this, "指纹识别成功", Toast.LENGTH_SHORT).show();

            alertView.dismiss();
        }

        @Override
        public void onAuthenticationFailed() {
            fingerText.setText("指纹验证失败,请重试");
            fingerText.setTextColor(Color.rgb(244, 81, 30));
            fingerImg.setImageResource(R.drawable.ic_fingerprint_error);
            Toast.makeText(MainActivity.this, "指纹识别失败", Toast.LENGTH_SHORT).show();
            alertView.dismiss();
        }
    };


    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            return;
        }
        manager.authenticate(cryptoObject, mCancellationSignal, 0, mSelfCancelled, null);


    }

    *//**
     * 锁屏密码
     *//*
    private void showAuthenticationScreen() {

        Intent intent = mKeyManager.createConfirmDeviceCredentialIntent("finger", "测试指纹识别");
        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            // Challenge completed, proceed with using cipher
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "识别成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "识别失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Log(String tag, String msg) {
        Log.d(tag, msg);
    }

    @Override
    public void onItemClick(Object o, int position) {
        if (position == AlertView.CANCELPOSITION) {
            finish();
        }
    }*/
}
