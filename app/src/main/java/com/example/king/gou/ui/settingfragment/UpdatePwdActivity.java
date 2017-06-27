package com.example.king.gou.ui.settingfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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


public class UpdatePwdActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.MyBJLTop)
    RelativeLayout MyBJLTop;
    @BindView(R.id.UpdataPwdOldPwd)
    EditText UpdataPwdOldPwd;
    @BindView(R.id.UpdataPwdNewPwd)
    EditText UpdataPwdNewPwd;
    @BindView(R.id.UpdataPwdCheckNewPwd)
    EditText UpdataPwdCheckNewPwd;
    @BindView(R.id.UpDataPwdClick)
    Button UpDataPwdClick;
    RestultInfo restultInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_pwd);
        ButterKnife.bind(this);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        UpDataPwdClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.UpDataPwdClick:
                String oldPwd = UpdataPwdOldPwd.getText().toString().trim();
                String newPwd = UpdataPwdNewPwd.getText().toString().trim();
                String newPwdCheck = UpdataPwdCheckNewPwd.getText().toString().trim();
                if (oldPwd != null && newPwd != null && newPwdCheck != null && newPwd.equals(newPwdCheck)) {
                    Log.d("修改密码界面用户名", MyApp.getInstance().getUserName());
                    String hmacshaOld = RxUtils.getInstance().HMACSHA256(oldPwd, MyApp.getInstance().getUserName());
                    Log.d("加密后的原密码", hmacshaOld);
                    String pwd = RxUtils.getInstance().HMACSHA256(newPwd, MyApp.getInstance().getUserName());
                    Log.d("加密后的修改过的密码", pwd);
                    RetrofitService.getInstance().getUpDataPwd(this, hmacshaOld, pwd);

                }


                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_UPDATAPWD) {
            if (object != null) {
                restultInfo = (RestultInfo) object;
                if (restultInfo.isRc() == true) {
                    Toasty.info(UpdatePwdActivity.this, restultInfo.getMsg(), 2000).show();
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
