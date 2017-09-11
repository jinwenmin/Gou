package com.example.king.gou.ui;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.Login;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.UserInfo;
import com.example.king.gou.service.RetrofitService;

import com.example.king.gou.ui.settingfragment.AboutUsActivity;
import com.example.king.gou.ui.settingfragment.BankCardManActivity;
import com.example.king.gou.ui.settingfragment.MoneyProtectActivity;
import com.example.king.gou.ui.settingfragment.ResetPwdActivity;
import com.example.king.gou.ui.settingfragment.UpdateMoneyPwdActivity;
import com.example.king.gou.ui.settingfragment.UpdateNickNameActivity;
import com.example.king.gou.ui.settingfragment.UpdatePwdActivity;

import com.example.king.gou.utils.FingerPrintUtils;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class SettingActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener, OnItemClickListener {

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
    private final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0;
    @BindView(R.id.LinearCheck1)
    RelativeLayout LinearCheck1;
    @BindView(R.id.getCardData)
    RelativeLayout getCardData;
    @BindView(R.id.updateSafePwd)
    RelativeLayout updateSafePwd;
    @BindView(R.id.ResetPwd)
    RelativeLayout ResetPwd;
    @BindView(R.id.Setting_img22)
    ImageView SettingImg22;
    @BindView(R.id.Setting_img23)
    ImageView SettingImg23;
    @BindView(R.id.Aboutus)
    RelativeLayout Aboutus;
    @BindView(R.id.LogOut)
    Button LogOut;
    private AlertView alertView;
    ; // 一个自定义的布局，作为显示的内容
    View contentView;
    private ImageView fingerImg;
    private TextView fingerText;
    private AlertView alertViewAnswer;
    // 一个自定义的布局，作为显示的内容
    View contentViewAnswer;

    private AlertView alertViewSafe;
    // 一个自定义的布局，作为显示的内容
    View contentViewSafe;


    List<UserInfo> userInfos;
    TextView SafeQues;
    private EditText answerQues;
    FingerprintManagerCompat manager;
    KeyguardManager mKeyguardManager;
    private FingerPrintUtils fingerPrintUiHelper;
    private final static int REQUEST_CODE_FINGER = 0;
    private final static String TAG = "MainActivity";
    private SharedPreferences Finger;
    String show = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        manager = FingerprintManagerCompat.from(this);
        mKeyguardManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        initDataHelper();

        alertViewAnswer = new AlertView(null, null, "取消", new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentViewAnswer = LayoutInflater.from(this).inflate(
                R.layout.reset_pwdcheck, null);
        alertViewAnswer.addExtView(contentViewAnswer);

        alertViewSafe = new AlertView(null, null, "取消", new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentViewSafe = LayoutInflater.from(this).inflate(
                R.layout.item_safepwd, null);
        alertViewSafe.addExtView(contentViewSafe);
        initClick();

    }

    private void initClick() {

        Back.setOnClickListener(this);
        SettingUpdataPwd.setOnClickListener(this);
        updateMoneyPwd.setOnClickListener(this);
        updateNickName.setOnClickListener(this);
        PwdProtect.setOnClickListener(this);
        LinearCheck1.setOnClickListener(this);
        getCardData.setOnClickListener(this);
        updateSafePwd.setOnClickListener(this);
        ResetPwd.setOnClickListener(this);
        Aboutus.setOnClickListener(this);
        LogOut.setOnClickListener(this);
    }

    private void initDataHelper() {
        Finger = getSharedPreferences("Finger", Activity.MODE_PRIVATE);
        boolean finger = Finger.getBoolean("finger", false);
        if (finger) {
            switch1.setChecked(true);
        }
        if (!finger) {
            switch1.setChecked(false);
        }
    }

    public void StartA(Class cls) {
        startActivity(new Intent(this, cls));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Setting_UpdataPwd:
                StartA(UpdatePwdActivity.class);
                //StartA(CheckSafePwdActivity.class);
                break;
            case R.id.Pwd_protect:
                alertViewSafe.show();
                show = "3";

                break;
            case R.id.update_NickName:
                StartA(UpdateNickNameActivity.class);
                break;
            case R.id.updateMoneyPwd:
                StartA(UpdateMoneyPwdActivity.class);
                break;
            case R.id.LinearCheck1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (isSatisfactionFingerprint()) {
                        alertView = new AlertView(null, null, "取消", null, null, SettingActivity.this, AlertView.Style.Alert, SettingActivity.this);
                        contentView = LayoutInflater.from(getApplicationContext()).inflate(
                                R.layout.item_finger, null);
                        alertView.addExtView(contentView);
                        fingerImg = ((ImageView) contentView.findViewById(R.id.fingerImg));
                        fingerText = ((TextView) contentView.findViewById(R.id.fingerText));
                        alertView.show();
                        show = "1";
                        initFingerPrint();
                    }
                } else {
                    Toasty.info(SettingActivity.this, "系统版本过低不支持指纹识别...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.getCardData:
                StartA(BankCardManActivity.class);
                break;
            case R.id.updateSafePwd:
                StartA(UpDataSafePwdActivity.class);
                break;
            case R.id._back:
                finish();
                break;
            case R.id.ResetPwd:
                RetrofitService.getInstance().GetUserInfo(this);
                break;
            case R.id.Aboutus:
                StartA(AboutUsActivity.class);
                break;
            case R.id.LogOut:
                Log.d("退出Sett=", "退出Sett");
                RetrofitService.getInstance().LogOut();
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.putExtra("LogOut", "logout");
                startActivity(intent);
                MyApp.getInstance().finishActivity();
             /*   Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);

                System.exit(0);*/
                //Intent intent = new Intent("action.Finish");
                // this.sendBroadcast(intent);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_USERINFO) {
            if (object != null) {
                userInfos = (List<UserInfo>) object;
                SafeQues = (TextView) contentViewAnswer.findViewById(R.id.SafeQues);
                answerQues = ((EditText) contentViewAnswer.findViewById(R.id.AnswerQues));
                if (userInfos.get(0).isHasQuestions()) {
                    SafeQues.setText(userInfos.get(0).getQuestion());
                }
                if (!userInfos.get(0).isHasQuestions()) {
                    Toasty.error(this, "未设置安全问题", 2000).show();
                    return;
                }
                alertViewAnswer.show();
            }

        }
        if (apiId == RetrofitService.API_ID_SAFEPWDCHECK) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    startActivity(new Intent(SettingActivity.this, ResetPwdActivity.class));
                }
                if (!restultInfo.isRc()) {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                    return;
                }
            }
        }
        if (apiId == RetrofitService.API_ID_SAFEPWD) {
            if (object != null) {
                RestultInfo    restultInfo = (RestultInfo) object;
                if (restultInfo.isRc() == true) {
                    StartA(MoneyProtectActivity.class);
                    return;
                }if (restultInfo.isRc() == false) {
                    Toasty.error(this,restultInfo.getMsg(),2000).show();
                    return;
                }
            }
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        if ("1".equals(show)) {

        }
        if ("2".equals(show)) {
            if (position != AlertView.CANCELPOSITION) {
                String Answer = answerQues.getText().toString().trim();
                if (Answer == null || "".equals(Answer)) {
                    Toasty.error(this, "问题答案不能为空", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getSecurityQuestionCheck(this, Answer);
            }
        }
        if ("3".equals(show)) {
            if (position != AlertView.CANCELPOSITION) {
                EditText safepwd = (EditText) contentViewSafe.findViewById(R.id.AnswerQues);
                String pwd = safepwd.getText().toString().trim();
                if ("".equals(pwd)) {
                    Toasty.error(this,"安全密码不可为空",2000).show();
                    return;
                }
                String hmacsha256 = RxUtils.getInstance().HMACSHA256(pwd, MyApp.getInstance().getUserName());
                RetrofitService.getInstance().getCheckSafePwd(this, hmacsha256);
            }
        }

        if (position == AlertView.CANCELPOSITION) {
            alertViewAnswer.dismiss();
        }

    }

    /**
     * 判断是否满足设置指纹的条件
     *
     * @return true 满足 false 不满足
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean isSatisfactionFingerprint() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "请开启指纹识别权限", Toast.LENGTH_LONG).show();
            return false;
        }
        //硬件是否支持指纹识别
        if (!manager.isHardwareDetected()) {
            Toast.makeText(this, "您手机不支持指纹识别功能", Toast.LENGTH_LONG).show();
            return false;
        }

        //手机是否开启锁屏密码
        if (!mKeyguardManager.isKeyguardSecure()) {
            Toast.makeText(this, "请开启开启锁屏密码，并录入指纹后再尝试", Toast.LENGTH_LONG).show();
            return false;
        }
        //是否有指纹录入
        if (!manager.hasEnrolledFingerprints()) {
            Toast.makeText(this, "您还未录入指纹", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initFingerPrint() {
        fingerPrintUiHelper = new FingerPrintUtils(this);
        fingerPrintUiHelper.setFingerPrintListener(new FingerprintManagerCompat.AuthenticationCallback() {
            /**
             * 指纹识别成功
             *
             * @param result
             */
            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                Toasty.success(SettingActivity.this, "指纹识别成功", Toast.LENGTH_SHORT).show();
                fingerText.setText("指纹识别成功");
                fingerText.setTextColor(Color.rgb(0, 150, 136));
                fingerImg.setImageResource(R.drawable.ic_fingerprint_success);
                SharedPreferences f = getSharedPreferences("Finger", Activity.MODE_PRIVATE);
                SharedPreferences.Editor edit = f.edit();


                if (switch1.isChecked()) {
                    switch1.setChecked(false);
                    edit.putBoolean("finger", false);
                } else {
                    switch1.setChecked(true);
                    edit.putBoolean("finger", true);
                }
                edit.commit();
                alertView.dismiss();
            }

            /**
             * 指纹识别失败调用
             */
            @Override
            public void onAuthenticationFailed() {
                fingerText.setText("指纹验证失败,请重试");
                fingerText.setTextColor(Color.rgb(244, 81, 30));
                fingerImg.setImageResource(R.drawable.ic_fingerprint_error);
                Toast.makeText(SettingActivity.this, "指纹识别失败", Toast.LENGTH_SHORT).show();
                alertView.dismiss();
            }

            /**
             *
             * @param helpMsgId
             * @param helpString
             *
             */
            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                Toast.makeText(SettingActivity.this, helpString, Toast.LENGTH_SHORT).show();
            }

            /**
             * 多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
             *
             * @param errMsgId  最多的错误次数
             * @param errString 错误的信息反馈
             */
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                //具体等多长时间为测试
                Log.i(TAG, "errMsgId=" + errMsgId + "-----errString" + errString);
                Toast.makeText(SettingActivity.this, "指纹识别出错次数过多，稍后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
