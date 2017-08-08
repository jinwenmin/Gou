package com.example.king.gou.ui.settingfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


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
    private ArrayAdapter<String> adapter;

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
                String SaveEditeText = SavePwdProtectEdittext.getText().toString().trim();
                if (SaveEditeText == null || "".equals(SaveEditeText)) {
                    Toasty.error(MoneyProtectActivity.this, "答案不能为空", 2000).show();
                    return;
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
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
