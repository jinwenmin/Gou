package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.UserRate;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.ShowRates)
    LinearLayout ShowRates;
    @BindView(R.id.default_show)
    LinearLayout defaultShow;

    List<UserRate> rates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_team_user);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        AddUserTopCode.setText(code);
        RetrofitService.getInstance().getAddUserData(this);
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


        if ("".equals(addUserTopCode)) {
            Toasty.error(AddNewTeamUserActivity.this, "上级推广码不可为空", 2000).show();
            return;
        }
        Double addUserR = Double.parseDouble(addUserRate);
        if (addUserR < 0 || addUserR > 12.6) {
            Toasty.error(AddNewTeamUserActivity.this, "返点不符合规则", 2000).show();
            return;
        }
        String pwd = RxUtils.getInstance().HMACSHA256("a123456", addUserName);
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
        if (apiId == RetrofitService.API_ID_ADDVIPCODE) {
            if (object != null) {

                List<Object> AddDatas = (List<Object>) object;
                Map<String, Object> rs = (Map) AddDatas.get(1);
                Log.d("ShowRates", rs.size() + "");
                if (rs.size() > 0) {
                    defaultShow.setVisibility(View.GONE);
                }
                rates = new ArrayList<>();

                for (Map.Entry<String, Object> entry : rs.entrySet()) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_rates, null);
                    TextView Rate = (TextView) view.findViewById(R.id.Rate);
                    TextView RateNum = (TextView) view.findViewById(R.id.RateNum);
                    Log.d("ShowRates", entry.getKey() + "  " + entry.getValue());
                    UserRate userRate = new UserRate();
                    userRate.setRate(entry.getKey());
                    userRate.setRateNum((String) entry.getValue());
                    rates.add(userRate);
                }

                for (int i = 0; i < rates.size(); i++) {
                    if (rates.get(i).getRate().equals("13.00")) {
                        SetLinear(i, 0);
                    }
                    if (rates.get(i).getRate().equals("12.90")) {
                        SetLinear(i, 1);
                    }
                    if (rates.get(i).getRate().equals("12.80")) {
                        SetLinear(i, 2);
                    }
                    if (rates.get(i).getRate().equals("12.70")) {
                        SetLinear(i, 3);
                    }
                    if (rates.get(i).getRate().equals("12.60")) {
                        SetLinear(i, 4);
                    }
                    if (rates.get(i).getRate().equals("12.50")) {
                        SetLinear(i, 5);
                    }
                }

            }

        }
    }

    private void SetLinear(int i, int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_rates, null);
        TextView Rate = (TextView) view.findViewById(R.id.Rate);
        TextView RateNum = (TextView) view.findViewById(R.id.RateNum);
        Rate.setText(rates.get(i).getRate());
        RateNum.setText(rates.get(i).getRateNum());
        ShowRates.addView(view);
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
