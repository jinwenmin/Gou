package com.example.king.gou.ui;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.ApiInterface;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import it.sephiroth.android.library.picasso.Picasso;


public class RegisterActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener {

    @BindView(R.id.img)
    LinearLayout img;
    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.RegisterTop)
    RelativeLayout RegisterTop;
    @BindView(R.id.Name)
    EditText Name;
    @BindView(R.id.UserNickName)
    EditText UserNickName;
    @BindView(R.id.NewPwd)
    EditText NewPwd;
    @BindView(R.id.CheckPwd)
    EditText CheckPwd;
    @BindView(R.id.TopCode)
    EditText TopCode;
    @BindView(R.id.YZMCheck)
    EditText YZMCheck;
    @BindView(R.id.ImageYZM)
    ImageView ImageYZM;
    @BindView(R.id.UpdataYZM)
    Button UpdataYZM;
    @BindView(R.id.SingUpId)
    Button SingUpId;
    String name;
    String newpwd;
    String userNickname;
    String checkPwd;
    String yzmCheck;
    String topcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this); MyApp.getInstance().addActivitys(this);
        initClick();
        initYZM();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        UpdataYZM.setOnClickListener(this);
        SingUpId.setOnClickListener(this);
        ImageYZM.setOnClickListener(this);
    }

    private void initYZM() {
      /*  long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("t", currentTimeMillis + "");
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        String url = ApiInterface.HOST + "/captcha?AppClient=1&reqkey=" + reqkey + "&t=" + currentTimeMillis + "&t=" + currentTimeMillis;
        Picasso.with(this).load(url).into(ImageYZM);*/
        long currentTimeMillis = System.currentTimeMillis();
        RetrofitService.getInstance().getCaptCha(this, currentTimeMillis);
        ImageView imgs = new ImageView(this);
        Picasso.with(this).load(ApiInterface.HOST + "/captcha?t=" + currentTimeMillis).into(imgs);
        //     ImageYZM.addView(imgs);

    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_IMAGECHECK) {
            final Bitmap imgs = (Bitmap) object;
            Log.d("imgs==", imgs.toString());
            //ImageYZM.addView(imgs);
            // Picasso.with(this).load(imgs).into(ImageYZM);
            new Runnable() {
                @Override
                public void run() {
                    ImageYZM.setImageBitmap(imgs);
                }
            }.run();


        }
        if (apiId == RetrofitService.API_ID_IMAGECHECKS) {
            final RestultInfo restultInfo = (RestultInfo) object;
            if (!restultInfo.isRc()) {
                Toasty.error(this, restultInfo.getMsg(), 2000).show();
            } else {
                checkPwd = RxUtils.getInstance().HMACSHA256(checkPwd, name);
                RetrofitService.getInstance().getSignUp(this, name, userNickname, checkPwd, topcode, yzmCheck);
            }
        }
        if (apiId == RetrofitService.API_ID_SIGNUP) {
            final RestultInfo restultInfo = (RestultInfo) object;
            if (!restultInfo.isRc()) {
                Toasty.error(this, restultInfo.getMsg(), 2000).show();
            } else {
                Toasty.success(this, "注册成功", 2000).show();
                finish();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UpdataYZM:
                initYZM();
                break;
            case R.id._back:
                finish();
                break;
            case R.id.SingUpId:
                name = Name.getText().toString();
                newpwd = NewPwd.getText().toString().trim();
                userNickname = UserNickName.getText().toString().trim();
                checkPwd = CheckPwd.getText().toString().trim();
                yzmCheck = YZMCheck.getText().toString().trim();
                topcode = TopCode.getText().toString().trim();
                if ("".equals(name)) {
                    Toasty.error(RegisterActivity.this, "用户名不可为空", 2000).show();
                    return;
                }
                if ("".equals(newpwd)) {
                    Toasty.error(RegisterActivity.this, "密码不可为空", 2000).show();
                    return;
                }
                if ("".equals(checkPwd)) {
                    Toasty.error(RegisterActivity.this, "确认密码不可为空", 2000).show();
                    return;
                }
                if ("".equals(userNickname)) {
                    Toasty.error(RegisterActivity.this, "用户昵称不可为空", 2000).show();
                    return;
                }
                /*if ("".equals(yzmCheck)) {
                    Toasty.error(RegisterActivity.this, "验证码不可为空", 2000).show();
                    return;
                }*/
                if ("".equals(topcode)) {
                    Toasty.error(RegisterActivity.this, "上级推广码不可为空", 2000).show();
                    return;
                }
                if (!newpwd.equals(checkPwd)) {
                    Toasty.error(RegisterActivity.this, "密码和确认密码不一致", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getCaptChaCheck(RegisterActivity.this, "", yzmCheck);
                break;
            case R.id.ImageYZM:
                initYZM();
                break;

        }

    }
}
