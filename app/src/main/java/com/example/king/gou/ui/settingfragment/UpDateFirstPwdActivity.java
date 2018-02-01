package com.example.king.gou.ui.settingfragment;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class UpDateFirstPwdActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.UpDatePwdTop)
    RelativeLayout UpDatePwdTop;
    @BindView(R.id.UpDateOldPwd)
    EditText UpDateOldPwd;
    @BindView(R.id.UpDateNewPwd)
    EditText UpDateNewPwd;
    @BindView(R.id.UpDateCheckNewPwd)
    EditText UpDateCheckNewPwd;
    @BindView(R.id.UpDateSafePwd)
    EditText UpDateSafePwd;
    @BindView(R.id.UpDateCheckSafePwd)
    EditText UpDateCheckSafePwd;
    @BindView(R.id.UpDataFirstPwd)
    Button UpDataFirstPwd;
    @BindView(R.id.UpDateEmail)
    EditText UpDateEmail;
    @BindView(R.id.SpinnerSafePwd)
    Spinner SpinnerSafePwd;
    @BindView(R.id.EditSafePwd)
    EditText EditSafePwd;
    @BindView(R.id.CheckEditSafePwd)
    EditText CheckEditSafePwd;
    private List<String> safes;
    ArrayAdapter adapter;
    private String safepwd;
    private String checkSafePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_date_first_pwd);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        UpDateEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        RetrofitService.getInstance().getSafeQues(this);
        UpDataFirstPwd.setOnClickListener(this);
        initClick();
    }


    private void initClick() {
        Back.setOnClickListener(this);
        SpinnerSafePwd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                tv.setGravity(Gravity.CENTER_HORIZONTAL);   //设置居中
                if (i == 5) {
                    EditSafePwd.setInputType(InputType.TYPE_CLASS_PHONE);
                    CheckEditSafePwd.setInputType(InputType.TYPE_CLASS_PHONE);
                } else {
                    EditSafePwd.setInputType(InputType.TYPE_CLASS_TEXT);
                    CheckEditSafePwd.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                EditSafePwd.setText("");
                CheckEditSafePwd.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.UpDataFirstPwd:
                String oldPwd = UpDateOldPwd.getText().toString().trim();
                String newPwd = UpDateNewPwd.getText().toString().trim();
                String CheckNewpwd = UpDateCheckNewPwd.getText().toString().trim();
                String SafePwd = UpDateSafePwd.getText().toString().trim();
                String CheckSafePwd = UpDateCheckSafePwd.getText().toString().trim();
                String email = UpDateEmail.getText().toString().trim();
                safepwd = EditSafePwd.getText().toString().trim();
                checkSafePwd = CheckEditSafePwd.getText().toString().trim();


                if ("".equals(oldPwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "原密码不可为空", 2000).show();
                    return;
                }
                if ("".equals(newPwd) || "".equals(CheckNewpwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "新密码或者确认密码不可为空", 2000).show();
                    return;
                }
                if ("".equals(SafePwd) || "".equals(CheckSafePwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "安全密码或者确认安全密码不可为空", 2000).show();
                    return;
                }

                if (newPwd.equals("a123456")) {
                    Toasty.error(UpDateFirstPwdActivity.this, "新密码不能和初始密码一样", 2000).show();
                    return;
                }
                if (!newPwd.equals(CheckNewpwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "新密码和确认密码不一致", 2000).show();
                    return;
                }
                if (!SafePwd.equals(CheckSafePwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "安全密码和确认安全密码不一致", 2000).show();
                    return;
                }
                if (newPwd.equals(SafePwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "新密码不能和安全密码一样", 2000).show();
                    return;
                }
                if ("".equals(email)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "邮箱不可为空", 2000).show();
                    return;
                }
                boolean b = RxUtils.getInstance().checkEmaile(email);
                if (b == false) {
                    Toasty.error(UpDateFirstPwdActivity.this, "请输入正确的邮箱地址", 2000).show();
                    return;
                }

                if (newPwd.length() > 14 || newPwd.length() < 6) {
                    Toasty.error(this, "登陆密码长度不正确", 2000).show();
                    return;
                }
                if (SafePwd.length() > 14 || SafePwd.length() < 6) {
                    Toasty.error(this, "安全密码长度不正确", 2000).show();
                    return;
                }
                if ("".equals(safepwd) || "".equals(checkSafePwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "安全问题答案不能为空", 2000).show();
                    return;
                }
                if (!safepwd.equals(checkSafePwd)) {
                    Toasty.error(UpDateFirstPwdActivity.this, "安全答案和确认安全答案不一致", 2000).show();
                    return;
                }
                if (SpinnerSafePwd.getSelectedItemPosition() == 5) {
                    boolean matches = safepwd.matches("\\d+");
                    if (matches) {
                        Log.d("判断是不是数字汉字", "是数字");
                        if (safepwd.length() != 11) {
                            Toasty.error(this, "输入的手机号码长度不为11", 2000).show();
                            return;
                        } else if (safepwd.length() == 11) {
                            String s1 = safepwd.substring(0, 1);
                            String s2 = safepwd.substring(1, 2);
                            Log.d("判断前两位1", s1);
                            Log.d("判断前两位2", s2);
                            if (!(s1 + s2).equals("13") && !(s1 + s2).equals("14") && !(s1 + s2).equals("15") && !(s1 + s2).equals("17") && !(s1 + s2).equals("18")) {
                                Toasty.error(this, "输入的不为手机号码", 2000).show();
                                return;
                            }
                        }
                    } else {
                        Log.d("判断是不是数字汉字", "不是数字");
                        Toasty.error(this, "该安全问题只能输入数字", 2000).show();
                        return;
                    }

                } else {
                    String s = safepwd;
                    char[] nickchar = s.toCharArray();
                    for (int i3 = 0; i3 < nickchar.length; i3++) {
                        boolean chinese = RxUtils.getInstance().isChinese(nickchar[i3]);
                        if (chinese) {
                            Log.d("判断是不是数字汉字", "是汉字");
                        } else {
                            Log.d("判断是不是数字汉字", "不是汉字");
                            Toasty.error(this, "该安全问题只能输入汉字", 2000).show();
                            return;
                        }
                    }
                }
                String oldPwd256 = RxUtils.getInstance().HMACSHA256(oldPwd, MyApp.getInstance().getUserName());
                String newpwd256 = RxUtils.getInstance().HMACSHA256(newPwd, MyApp.getInstance().getUserName());
                String Safe256 = RxUtils.getInstance().HMACSHA256(SafePwd, MyApp.getInstance().getUserName());
                RetrofitService.getInstance().getUpdateFirstPwd(this, oldPwd256, newpwd256, Safe256, email, safes.get(SpinnerSafePwd.getSelectedItemPosition()), safepwd);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_UPDATAFIRSTPWD) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc() == true) {
                    Toasty.success(UpDateFirstPwdActivity.this, restultInfo.getMsg(), 2000).show();
                    finish();
                } else {
                    Toasty.error(UpDateFirstPwdActivity.this, restultInfo.getMsg(), 2000).show();
                }
            }
        }
        if (apiId == RetrofitService.API_ID_SAFEQUES) {
            if (object != null) {
                safes = (List<String>) object;
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, safes);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                SpinnerSafePwd.setAdapter(adapter);


            }
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
