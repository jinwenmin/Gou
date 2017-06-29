package com.example.king.gou.ui;

import android.app.KeyguardManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.example.king.gou.R;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.settingfragment.BankCardManActivity;
import com.example.king.gou.ui.settingfragment.MoneyProtectActivity;
import com.example.king.gou.ui.settingfragment.UpdateMoneyPwdActivity;
import com.example.king.gou.ui.settingfragment.UpdateNickNameActivity;
import com.example.king.gou.utils.DataBaseHelper;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.Setting_UpdataPwd)
    RelativeLayout SettingUpdataPwd;
    @BindView(R.id.updateMoneyPwd)
    RelativeLayout updateMoneyPwd;
    @BindView(R.id.Pwd_protect)
    RelativeLayout PwdProtect;
    @BindView(R.id.update_NickName)
    RelativeLayout updateNickName;
    @BindView(R.id.MyBJLTop)
    RelativeLayout MyBJLTop;
    @BindView(R.id.Setting_img1)
    ImageView SettingImg1;
    @BindView(R.id.Setting_img2)
    ImageView SettingImg2;
    @BindView(R.id.Setting_img3)
    ImageView SettingImg3;
    @BindView(R.id.Setting_img4)
    ImageView SettingImg4;
    @BindView(R.id.switch1)
    Switch switch1;
    @BindView(R.id.Setting_img5)
    ImageView SettingImg5;
    @BindView(R.id.Setting_img6)
    ImageView SettingImg6;
    @BindView(R.id.Setting_img7)
    ImageView SettingImg7;
    @BindView(R.id.Setting_img8)
    ImageView SettingImg8;
    @BindView(R.id.switch2)
    Switch switch2;
    @BindView(R.id.Setting_img9)
    ImageView SettingImg9;
    @BindView(R.id.switch3)
    Switch switch3;
    @BindView(R.id.Setting_img10)
    ImageView SettingImg10;
    @BindView(R.id.switch4)
    Switch switch4;
    @BindView(R.id.Setting_img11)
    ImageView SettingImg11;
    @BindView(R.id.switch5)
    Switch switch5;
    @BindView(R.id.Setting_img12)
    ImageView SettingImg12;
    @BindView(R.id.Setting_img13)
    ImageView SettingImg13;
    @BindView(R.id.Left)
    ImageView Left;
    @BindView(R.id.Setting_img14)
    ImageView SettingImg14;
    private final static String TAG = "finger_log";
    FingerprintManager manager;
    KeyguardManager mKeyManager;
    private final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0;
    @BindView(R.id.LinearCheck1)
    RelativeLayout LinearCheck1;
    @BindView(R.id.getCardData)
    RelativeLayout getCardData;
    @BindView(R.id.updateSafePwd)
    RelativeLayout updateSafePwd;
    private AlertView alertView;
    ; // 一个自定义的布局，作为显示的内容
    View contentView;
    private ImageView fingerImg;
    private TextView fingerText;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase writableDatabase;
    String isfinger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        //  initDataHelper();
        SettingUpdataPwd.setOnClickListener(this);
        updateMoneyPwd.setOnClickListener(this);
        updateNickName.setOnClickListener(this);
        PwdProtect.setOnClickListener(this);
        LinearCheck1.setOnClickListener(this);
        getCardData.setOnClickListener(this);
        updateSafePwd.setOnClickListener(this);
       /* manager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    writableDatabase.execSQL("insert into fingers(isfinger) values('true')");
                } else {
                    writableDatabase.execSQL("insert into fingers(isfinger) values('false')");
                }
            }
        });
        Cursor cursor = writableDatabase.query("fingers", new String[]{"id", "isfinger"}, "id=?", new String[]{"1"}, null, null, null);
        while (cursor.moveToNext()) {
            isfinger = cursor.getString(cursor.getColumnIndex("isfinger"));
            Log.d("这个isFInger==",isfinger);
            if ("true".equals(isfinger)) {
                switch1.setChecked(true);
            }
            else{
                switch1.setChecked(false);
            }
        }*/
    }

    private void initDataHelper() {
        dataBaseHelper = new DataBaseHelper(SettingActivity.this, "fingers.db", null, 1);
        /* 创建两张表 */
        writableDatabase = dataBaseHelper.getWritableDatabase();
        writableDatabase.execSQL("create table  if not exists fingers(id INTEGER PRIMARY KEY autoincrement,isfinger String);");
        writableDatabase.execSQL("insert into fingers(isfinger) values('false')");

    }

    public void StartA(Class cls) {
        startActivity(new Intent(this, cls));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Setting_UpdataPwd:
                //   StartA(UpdatePwdActivity.class);
                StartA(CheckSafePwdActivity.class);
                break;
            case R.id.Pwd_protect:
                StartA(MoneyProtectActivity.class);
                break;
            case R.id.update_NickName:
                StartA(UpdateNickNameActivity.class);
                break;
            case R.id.updateMoneyPwd:
                StartA(UpdateMoneyPwdActivity.class);
                break;
            case R.id.LinearCheck1:
               /* if (isFinger()) {
                    alertView = new AlertView(null, null, "取消", null, null, SettingActivity.this, AlertView.Style.Alert, SettingActivity.this);
                    contentView = LayoutInflater.from(getApplicationContext()).inflate(
                            R.layout.item_finger, null);
                    alertView.addExtView(contentView);
                    fingerImg = ((ImageView) contentView.findViewById(R.id.fingerImg));
                    fingerText = ((TextView) contentView.findViewById(R.id.fingerText));
                    alertView.show();
                    Toast.makeText(SettingActivity.this, "请进行指纹识别", Toast.LENGTH_LONG).show();
                    Log(TAG, "keyi");
                    startListening(null);
                }*/
                break;
            case R.id.getCardData:
                //RetrofitService.getInstance().getCardDatas(this);
                StartA(BankCardManActivity.class);
                break;
            case R.id.updateSafePwd:
                StartA(UpDataSafePwdActivity.class);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {

    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
/*
    public boolean isFinger() {

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
            Toast.makeText(SettingActivity.this, errString, Toast.LENGTH_SHORT).show();
            showAuthenticationScreen();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

            Toast.makeText(SettingActivity.this, helpString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            fingerText.setText("指纹识别成功");
            fingerText.setTextColor(Color.rgb(0, 150, 136));
            fingerImg.setImageResource(R.drawable.ic_fingerprint_success);
            Toast.makeText(SettingActivity.this, "指纹识别成功", Toast.LENGTH_SHORT).show();
            if (switch1.isChecked()) {
                writableDatabase.execSQL("update fingers set isfinger = true where id = 1");
                switch1.setChecked(false);
            } else {
                writableDatabase.execSQL("update fingers set isfinger = false where id = 1");
                switch1.setChecked(true);
            }
            alertView.dismiss();
        }

        @Override
        public void onAuthenticationFailed() {
            fingerText.setText("指纹验证失败,请重试");
            fingerText.setTextColor(Color.rgb(244, 81, 30));
            fingerImg.setImageResource(R.drawable.ic_fingerprint_error);
            Toast.makeText(SettingActivity.this, "指纹识别失败", Toast.LENGTH_SHORT).show();
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

    }*/
}
