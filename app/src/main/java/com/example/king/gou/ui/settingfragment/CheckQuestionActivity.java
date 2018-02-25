package com.example.king.gou.ui.settingfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.SettingActivity;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class CheckQuestionActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.Question)
    TextView Question;
    @BindView(R.id.CheckQuesEditText)
    EditText CheckQuesEditText;
    @BindView(R.id.ToCheckQues)
    Button ToCheckQues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_question);
        ButterKnife.bind(this);
        Question.setText(RetrofitService.getInstance().getUser().getQuestion());
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        ToCheckQues.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.ToCheckQues:
                String checkQues = CheckQuesEditText.getText().toString().trim();
                if (checkQues.equals("") || checkQues.length() == 0) {
                    Toasty.error(CheckQuestionActivity.this, "密保问题不能为空", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getSecurityQuestionCheck(CheckQuestionActivity.this, checkQues);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_SAFEPWDCHECK) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    startActivity(new Intent(CheckQuestionActivity.this, ResetPwdActivity.class));
                    finish();
                }
                if (!restultInfo.isRc()) {

                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
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
