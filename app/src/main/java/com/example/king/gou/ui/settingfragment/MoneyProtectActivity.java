package com.example.king.gou.ui.settingfragment;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static com.example.king.gou.service.RetrofitService.API_ID_PWDPROTACT;


public class MoneyProtectActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.MoneyProtectTop)
    RelativeLayout MoneyProtectTop;
    @BindView(R.id.SpinnerQues1)
    Spinner SpinnerQues1;
    @BindView(R.id.SpinnerQues2)
    Spinner SpinnerQues2;
    @BindView(R.id.SpinnerQues3)
    Spinner SpinnerQues3;
    List<String> Safes = new ArrayList<String>();
    @BindView(R.id.SavePwdProtect)
    Button SavePwdProtect;
    @BindView(R.id.SavePwdProtectEdittext)
    EditText SavePwdProtectEdittext;
    @BindView(R.id.CheckSavePwdProtectEdittext)
    EditText CheckSavePwdProtectEdittext;
    private ArrayAdapter<String> adapter;
    private String SaveEditeText;
    private String CheckSaveEditeText;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_protect);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        RetrofitService.getInstance().getSafeQues(this);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        SavePwdProtect.setOnClickListener(this);
        SpinnerQues1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 5) {
                    SavePwdProtectEdittext.setInputType(InputType.TYPE_CLASS_PHONE);
                    CheckSavePwdProtectEdittext.setInputType(InputType.TYPE_CLASS_PHONE);
                } else {
                    SavePwdProtectEdittext.setInputType(InputType.TYPE_CLASS_TEXT);
                    CheckSavePwdProtectEdittext.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                SavePwdProtectEdittext.setText("");
                CheckSavePwdProtectEdittext.setText("");
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
            case R.id.SavePwdProtect:
                String selectedItem = (String) SpinnerQues1.getSelectedItem();
                Log.d("选中的问题", selectedItem);
                SaveEditeText = SavePwdProtectEdittext.getText().toString().trim();
                CheckSaveEditeText = CheckSavePwdProtectEdittext.getText().toString().trim();
                if (SaveEditeText == null || "".equals(SaveEditeText)) {
                    Toasty.error(MoneyProtectActivity.this, "答案不能为空", 2000).show();
                    return;
                }

                if (CheckSaveEditeText == null || "".equals(CheckSaveEditeText)) {
                    Toasty.error(MoneyProtectActivity.this, "确认答案不能为空", 2000).show();
                    return;
                }
                if (!CheckSaveEditeText.equals(SaveEditeText)) {
                    Toasty.error(MoneyProtectActivity.this, "两次答案不一致", 2000).show();
                    return;
                }
                if (SpinnerQues1.getSelectedItemPosition() == 5) {
                    boolean matches = SaveEditeText.matches("\\d+");
                    if (matches) {
                        Log.d("判断是不是数字汉字", "是数字");
                        if (SaveEditeText.length() != 11) {
                            Toasty.error(this, "输入的手机号码长度不为11", 2000).show();
                            return;
                        } else if (SaveEditeText.length() == 11) {
                            String s1 = SaveEditeText.substring(0, 1);
                            String s2 = SaveEditeText.substring(1, 2);
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
                    String s = SaveEditeText;
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
                RetrofitService.getInstance().getSaveSafeQus(this, selectedItem, SaveEditeText);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_SAFEQUES) {
            if (object != null) {
                Safes = (List<String>) object;
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Safes);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                SpinnerQues1.setAdapter(adapter);


            }
        }
        if (apiId == RetrofitService.API_ID_PWDPROTACT) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(MoneyProtectActivity.this, restultInfo.getMsg(), 2000).show();
                    finish();
                    return;
                } else {
                    Toasty.error(MoneyProtectActivity.this, restultInfo.getMsg(), 2000).show();
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
}
