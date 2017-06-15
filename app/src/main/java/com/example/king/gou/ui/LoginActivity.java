package com.example.king.gou.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;
import com.chaychan.viewlib.PowerfulEditText;
import com.example.king.gou.R;
import com.example.king.gou.bean.Login;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.RxUtils;

import com.google.gson.Gson;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.POST;

public class LoginActivity extends AutoLayoutActivity {
    String rekey;
    @BindView(R.id.login_icon)
    ImageView loginIcon;
    @BindView(R.id.login_user)
    PowerfulEditText loginUser;
    @BindView(R.id.relativelayuout1)
    RelativeLayout relativelayuout1;
    @BindView(R.id.login_pwd)
    PowerfulEditText loginPwd;
    @BindView(R.id.relativelayout2)
    RelativeLayout relativelayout2;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.forget_pwd)
    TextView forgetPwd;
    private String Login_UserName;
    private String Login_Pwd;
    Login login;
    private SharedPreferences login_userinfo;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(this);

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNameAndPwd();

                Login();
                //  Login2();
                //   logout();

            }
        });


    }

    /*
    * ToDo:用户的cookie截取,
    * */
    public static String getSessionCookie(String cookieString) {
        if (!TextUtils.isEmpty(cookieString)) {
            String[] splitCookie = cookieString.split(";");
            String[] splitSessionId = splitCookie[0].split("=");
            cookieString = splitSessionId[1];
            return cookieString;
        }
        return "";
    }

    private void Login2() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url("http://vipfacaiflvbceshi.com/chat-message?luid=2047&uonline=1&type=1");
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        Call mcall = okHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String sessionCookie = getSessionCookie(response.headers().get("Set-Cookie"));
                System.out.println("SessionCook==" + sessionCookie);
            }
        });
    }


    private void initNameAndPwd() {
        Login_UserName = loginUser.getText().toString().trim();
        Login_Pwd = loginPwd.getText().toString().trim();
        long currentTimeMillis = System.currentTimeMillis();
        String text = "AppClient=1&ipwd=false&p=" + Login_Pwd + "&u=" + Login_UserName + "+&t=" + currentTimeMillis;
        rekey = RxUtils.getInstance().md5(text);
        Log.i("MD5", rekey);
    }

    private void logout() {
        RetrofitService.getInstance()
                .getLogOut()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i("Object消息", o.toString());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Error消息", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Success消息", "");
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        login_userinfo = getSharedPreferences("login_userinfo", Activity.MODE_PRIVATE);
        String login_username = login_userinfo.getString("login_username", "");
        String login_userpwd = login_userinfo.getString("login_userpwd", "");
        loginUser.setText(login_username);
        loginPwd.setText(login_userpwd);
    }

    public void Login() {
        //String s = RxUtils.getInstance().SHA256("a12345678");
        if (Login_UserName.isEmpty() || Login_Pwd.isEmpty()) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = RxUtils.getInstance().HMACSHA256(Login_Pwd, Login_UserName);
        password = password.toLowerCase();
        Log.i("小写的密码消息", password);
        long timeMillis = System.currentTimeMillis();
        String s1 = RxUtils.getInstance().HMACSHA256(String.valueOf(timeMillis), Login_UserName);
        Log.i("时间消息:", timeMillis + "");
        Log.i("密码SHA256消息", password);
        Log.i("时间戳SHA256消息", s1);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url("http://vipfacaiflvbceshi.com/signin?AppClient=1&u=" + Login_UserName + "&p=" + password + "&ipwd=" + false + "&reqkey=" + rekey + "&t=" + timeMillis);
        // Request.Builder requestBuilder = new Request.Builder().url("http://vipfacaiflvbceshi.com/logout");
        // requestBuilder.addHeader( "XMLHttpRequest","X-Requested-With");
        //可以省略，默认是GET请求
        final Request request = requestBuilder.build();
        requestBuilder.method("GET", null);
        Call mcall = okHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ResponseBody responseBody = response.body();
                System.out.println("数据是==" + responseBody);
                String sessionCookie = getSessionCookie(response.headers().get("Set-Cookie"));
                System.out.println("SessionCook==" + sessionCookie);
            }
        });

/*
        RetrofitService.getInstance()
                .getLoginInfo(1, "testapp", password, false, rekey, timeMillis)
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("Disposeable消息", d.toString());
                    }

                    @Override
                    public void onNext(Login o) {

                        Log.i("Object消息", o.toString());
                        if (o.getStatus() == 1 && o.isState() == true && o.isUnsignin() == true && o.getFreeze() == 0) {
                            SharedPreferences login_userinfo = getSharedPreferences("login_userinfo", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor edit = login_userinfo.edit();
                            edit.putString("login_username", Login_UserName);
                            edit.putString("login_userpwd", Login_Pwd);
                            edit.putInt("login_uid", o.getUid());
                            edit.commit();
                            startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        if (o.isState() == false) {
                            Toast.makeText(LoginActivity.this, "登录失败" + o.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (o.isUnsignin() == false) {
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                        if (o.getFreeze() == -1) {
                            Toast.makeText(LoginActivity.this, "用户名密码错误次数达到5次，请30分钟后再试或联系客服", Toast.LENGTH_SHORT).show();
                        }
                        if (o.getFreeze() == 1) {
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                        if (o.getUid() == -1) {
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }

                       *//* Log.i("Object消息", o.toString());
                        Gson gson=new GsonBuilder().setLenient().create();
                         login = gson.fromJson(o.toString(), Login.class);*//*
                        Log.i("Object消息2", o.getStatus() + "");


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Error消息", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Success消息", "");
                    }
                });*/

    }


    ;
}
