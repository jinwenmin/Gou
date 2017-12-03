package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class AddNewTeamUserActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.AddUserTop)
    RelativeLayout AddUserTop;
    @BindView(R.id.AddUserName)
    EditText AddUserName;
    @BindView(R.id.AddUserNickName)
    EditText AddUserNickName;
    @BindView(R.id.AddUserNewPwd)
    EditText AddUserNewPwd;
    @BindView(R.id.AddUserCheckPwd)
    EditText AddUserCheckPwd;
    @BindView(R.id.AddUserTopCode)
    EditText AddUserTopCode;
    @BindView(R.id.radioTeam1)
    RadioButton radioTeam1;
    @BindView(R.id.radioTeam2)
    RadioButton radioTeam2;
    @BindView(R.id.AddUserBtn)
    Button AddUserBtn;
    @BindView(R.id.AddRadioGroup)
    RadioGroup AddRadioGroup;
    int t = 2;
    @BindView(R.id.AddUserRate)
    EditText AddUserRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_team_user);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        AddUserTopCode.setText(code);
        AddRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.radioTeam1) {
                    t = 2;
                }
                if (checkedId == R.id.radioTeam2) {
                    t = 3;
                }
            }
        });
        ((RadioButton) AddRadioGroup.getChildAt(0)).setChecked(true);
        initClick();

    }

    private void initCheck() {
        String addUserName = AddUserName.getText().toString().trim();
        String addUserNickName = AddUserNickName.getText().toString().trim();
        String addUserNewPwd = AddUserNewPwd.getText().toString().trim();
        String addUserCheckPwd = AddUserCheckPwd.getText().toString().trim();
        String addUserTopCode = AddUserTopCode.getText().toString().trim();
        String addUserRate = AddUserRate.getText().toString().trim();

        if ("".equals(addUserName)) {
            Toasty.error(AddNewTeamUserActivity.this, "用户名不可为空", 2000).show();
            return;
        }

        if ("".equals(addUserNickName)) {
            Toasty.error(AddNewTeamUserActivity.this, "用户昵称不可为空", 2000).show();
            return;
        }
        if ("".equals(addUserRate)) {
            Toasty.error(AddNewTeamUserActivity.this, "会员返点不可为空", 2000).show();
            return;
        }

        if ("".equals(addUserNewPwd)) {
            Toasty.error(AddNewTeamUserActivity.this, "密码不可为空", 2000).show();
            return;
        }
        if ("".equals(addUserCheckPwd)) {
            Toasty.error(AddNewTeamUserActivity.this, "确认密码不可为空", 2000).show();
            return;
        }
        if ("".equals(addUserTopCode)) {
            Toasty.error(AddNewTeamUserActivity.this, "上级推广码不可为空", 2000).show();
            return;
        }
        if (!addUserNewPwd.equals(addUserCheckPwd)) {
            Toasty.error(AddNewTeamUserActivity.this, "密码和确认密码不一致", 2000).show();
            return;
        }
        Double addUserR = Double.parseDouble(addUserRate);
        if (addUserR < 0 || addUserR > 12.6) {
            Toasty.error(AddNewTeamUserActivity.this, "返点不符合规则", 2000).show();
            return;
        }
        String pwd = RxUtils.getInstance().HMACSHA256(addUserNewPwd, addUserName);
        RetrofitService.getInstance().getVIPSignUpSave(AddNewTeamUserActivity.this, addUserName, addUserNickName, Double.parseDouble(addUserRate), addUserTopCode, t, pwd, "addnewuser");
    }

    private void initClick() {
        Back.setOnClickListener(this);
        AddUserBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.AddUserBtn:
                initCheck();
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_ADDCIPSAVE) {
            RestultInfo restultInfo = (RestultInfo) object;
            if (restultInfo.isRc()) {

                AddUserName.setText("");
                AddUserNickName.setText("");
                AddUserNewPwd.setText("");
                AddUserCheckPwd.setText("");
                AddUserRate.setText("");
                if (t == 2) {
                    Toasty.success(this, "添加代理用户成功", 2000).show();
                } else {
                    Toasty.success(this, "添加普通会员成功", 2000).show();
                }
            } else if (!restultInfo.isRc()) {
                Toasty.error(this, restultInfo.getMsg(), 2000).show();
            }
            finish();
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
