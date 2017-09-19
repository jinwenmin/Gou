package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.TeamUserInfoAdapter;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.SetRate;
import com.example.king.gou.bean.SreCharge;
import com.example.king.gou.bean.TeamUserInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class ProxyHomeActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener, OnItemClickListener {


    List<List<TeamUserInfo>> ts = new ArrayList<List<TeamUserInfo>>();
    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ProxyTop)
    RelativeLayout ProxyTop;
    @BindView(R.id.Proxy_UserCounts)
    TextView ProxyUserCounts;
    @BindView(R.id.Activity_ProxyListView)
    ListView ActivityProxyListView;
    TeamUserInfoAdapter adapter;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.TeamUserBtn)
    Button TeamUserBtn;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.ProxyHomeSpinner)
    Spinner ProxyHomeSpinner;
    ArrayAdapter<String> adapter2;
    List<Integer> tId = new ArrayList<>();
    List<String> tName = new ArrayList<>();
    int uid;
    String name;
    private AlertView alertView;
    // 一个自定义的布局，作为显示的内容
    View contentView;
    private AlertView alertView1;
    // 一个自定义的布局，作为显示的内容
    View contentView1;
    private AlertView alertView2;
    // 一个自定义的布局，作为显示的内容
    View contentView2;


    EditText proxySetRate;
    double max;
    double min;
    double SreChargeMin;
    double SreChargeMax;
    private double amounts1;
    String isS = "";
    private TextView proxyName;
    private EditText setTrans;
    SreCharge sreCharge;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        alertView = new AlertView(null, null, null, new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentView = LayoutInflater.from(this).inflate(
                R.layout.item_homenotice, null);
        alertView.addExtView(contentView);

        alertView1 = new AlertView(null, null, "取消", new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentView1 = LayoutInflater.from(this).inflate(
                R.layout.proxy_setrate, null);
        alertView1.addExtView(contentView1);

        alertView2 = new AlertView(null, null, "取消", new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentView2 = LayoutInflater.from(this).inflate(
                R.layout.proxy_owntransfer, null);
        alertView2.addExtView(contentView2);


        adapter = new TeamUserInfoAdapter(this);
        ActivityProxyListView.setAdapter(adapter);
        initSpinner();
        // RetrofitService.getInstance().getShareData(this);
        // RetrofitService.getInstance().getAddUserData(this);4

        initClick();
    }

    private void initSpinner() {
        tId.add(0);
        tId.add(2);
        tId.add(3);
        tId.add(-1);
        tName.add("所有用户");
        tName.add("代理用户");
        tName.add("普通会员");
        tName.add("在线用户");
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tName);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        ProxyHomeSpinner.setAdapter(adapter2);
        ProxyHomeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getTeamUserInfo(ProxyHomeActivity.this, 1, 100, "uid", "desc", MyApp.getInstance().getUserUid(), "", tId.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void ItemOnLongClick1() {
//注：setOnCreateContextMenuListener是与下面onContextItemSelected配套使用的
        ActivityProxyListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "查询团队余额");
                menu.add(0, 1, 0, "设置返点");
                //  menu.add(0, 3, 0, "获得上级充值数据");
                menu.add(0, 2, 0, "保存上级充值");
                menu.add(0, 3, 0, "保存日工资充值");
                menu.add(0, 4, 0, "帐变记录");

            }
        });

    }    // 长按菜单响应函数

    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();


        switch (item.getItemId()) {
            case 0:
                RetrofitService.getInstance().getTeamBalanceView(this, uid);
                break;

            case 1:
                RetrofitService.getInstance().getUreBateData(this, uid);
                break;

            case 2:
               /* Toast.makeText(ProxyHomeActivity.this,
                        "获得上级充值数据",
                        Toast.LENGTH_SHORT).show();*/
                RetrofitService.getInstance().getSreChargeData(this, uid);
                break;
            case 3:
                //  RetrofitService.getInstance().getTquotaData(this, uid);
                RetrofitService.getInstance().getTeamUsersParent(this, uid);
                RetrofitService.getInstance().getSreChargeData2(this, uid);
                /*proxyName = ((TextView) contentView2.findViewById(R.id.proxy_name));
                proxyName.setText(name);
                setTrans = ((EditText) contentView2.findViewById(R.id.Proxy_setTrans));
                alertView2.show();*/

                break;
            case 4:
                Intent intent = new Intent(ProxyHomeActivity.this, VIPAccountChangeActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
                break;

            default:
                break;
        }
        return false;
    }

    private void initClick() {
        Back.setOnClickListener(this);
        TeamUserBtn.setOnClickListener(this);
        ActivityProxyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (ts.size() > 1) {
                    uid = ts.get(1).get(i).getUid();
                    name = ts.get(1).get(i).getName();
                    ItemOnLongClick1();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.TeamUserBtn:
                RetrofitService.getInstance().getAddUserData(this);
                break;
        }

    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_TEAMUSERINFO) {
            ts = (List<List<TeamUserInfo>>) object;
            ProxyUserCounts.setText("总会员数：" + ts.get(0).get(0).getTotalElements() + "人");
            if (ts.size() > 1) {
                List<TeamUserInfo> teamUserInfos = ts.get(1);
                adapter.addList(teamUserInfos);
            }
        }
        if (apiId == RetrofitService.API_ID_ADDVIPCODE) {
            String Code = (String) object;
            Intent intent = new Intent(getApplicationContext(), AddNewTeamUserActivity.class);
            intent.putExtra("code", Code);
            startActivity(intent);
            finish();
        }
        if (apiId == RetrofitService.API_ID_TEAMBALANCEVIEW) {
            if (object != null) {
                String TeamAmount = (String) object;
                TextView homeNotice = (TextView) contentView.findViewById(R.id.homeNoticeText);
                homeNotice.setText(TeamAmount);
                alertView.show();
            }

        }
        if (apiId == RetrofitService.API_ID_UREBATEDATA) {
            if (object != null) {
                SetRate setRate = (SetRate) object;
                max = setRate.getMxrate();
                min = setRate.getMnrate();
                proxySetRate = (EditText) contentView1.findViewById(R.id.Proxy_setRate);
                proxySetRate.setHint("范围:" + min + "~" + max);
                alertView1.show();
                isS = "Show1";
            }

        }
        if (apiId == RetrofitService.API_ID_SETRATESAVE) {

            RestultInfo re = (RestultInfo) object;
            if (re.isRc()) {
                RetrofitService.getInstance().getTeamUserInfo(ProxyHomeActivity.this, 1, 100, "uid", "desc", MyApp.getInstance().getUserUid(), "", tId.get(ProxyHomeSpinner.getSelectedItemPosition()));
            }
            Toasty.success(this, re.getMsg(), 2000).show();
        }
        if (apiId == RetrofitService.API_ID_SRECHARGE) {
            sreCharge = (SreCharge) object;
            if (sreCharge.getStype() == 0) {
                Toasty.error(this, "没有充值权限", 2000).show();
                return;
            }
            if (sreCharge.getStype() == 1) {
                Toasty.success(this, "直属下级可充值", 2000).show();
            }
            if (sreCharge.getStype() == 2) {
                Toasty.success(this, "所有下级可充值", 2000).show();
            }

            proxyName = ((TextView) contentView2.findViewById(R.id.proxy_name));
            username = ((TextView) contentView2.findViewById(R.id.UserTitle));
            username.setText("保存上级充值");
            proxyName.setText(sreCharge.getRuser());
            setTrans = ((EditText) contentView2.findViewById(R.id.Proxy_setTrans));
            SreChargeMin = sreCharge.getMin1();
            SreChargeMax = sreCharge.getMax1();
            amounts1 = sreCharge.getAmounts1();
            if (amounts1 < SreChargeMax) {
                SreChargeMax = amounts1;
            }
            setTrans.setHint("充值范围:" + SreChargeMin + "~" + SreChargeMax);
            alertView2.show();
            isS = "Show2";
        }
        if (apiId == RetrofitService.API_ID_SRECHARGE2) {
            sreCharge = (SreCharge) object;
            if (sreCharge.isDtype()==false) {
                Toasty.error(this, "没有权限日工资充值", 2000).show();
                return;
            }
            if (sreCharge.isDtype() ==true) {
                Toasty.success(this, "有权限日工资充值", 2000).show();
            }
            proxyName = ((TextView) contentView2.findViewById(R.id.proxy_name));
            username = ((TextView) contentView2.findViewById(R.id.UserTitle));
            username.setText("保存日工资充值");
            proxyName.setText(sreCharge.getRuser());
            setTrans = ((EditText) contentView2.findViewById(R.id.Proxy_setTrans));
            SreChargeMin = sreCharge.getMin1();
            SreChargeMax = sreCharge.getMax1();
            amounts1 = sreCharge.getAmounts1();
            if (amounts1 < SreChargeMax) {
                SreChargeMax = amounts1;
            }
            setTrans.setHint("充值范围:" + SreChargeMin + "~" + SreChargeMax);
            alertView2.show();
            isS = "Show3";
        }
        if (apiId == RetrofitService.API_ID_OWNTRANSFER) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                }
                if (!restultInfo.isRc()) {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                }

            }
        }
        if (apiId == RetrofitService.API_ID_DAILYRECHARGE) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                }
                if (!restultInfo.isRc()) {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
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
        if ("Show1".equals(isS)) {
            if (position == AlertView.CANCELPOSITION) {
                Log.d("返点测试AlertView", "取消");
                alertView1.dismiss();
            } else {
                Log.d("返点测试AlertView", "确认");
                String trim = proxySetRate.getText().toString().trim();
                if (!"".equals(trim)) {
                    double editV = Double.parseDouble(trim);
                    if (editV > max || editV < min) {
                        Toasty.error(this, "返点不在范围内", 2000).show();
                        return;
                    }
                    RetrofitService.getInstance().getTeamUserRebateSave(this, editV, uid);
                    proxySetRate.setText("");
                }
            }
        }
        if ("Show2".equals(isS)) {
            if (position == AlertView.CANCELPOSITION) {
                Log.d("充值测试AlertView", "取消");
                alertView2.dismiss();
            } else {
                Log.d("充值测试AlertView", "确认");
                String trim = setTrans.getText().toString().trim();
                if (!"".equals(trim)) {
                    double editV = Double.parseDouble(trim);

                    if (editV > SreChargeMax || editV < SreChargeMin) {
                        Toasty.error(this, "充值金额不在范围内", 2000).show();
                        return;
                    }
                    RetrofitService.getInstance().getOwnReansferTrans(this, editV, proxyName.getText().toString().trim());
                    setTrans.setText("");
                }
            }
        }
        if ("Show3".equals(isS)) {
            if (position == AlertView.CANCELPOSITION) {
                Log.d("工资充值测试AlertView", "取消");
                alertView2.dismiss();
            } else {
                Log.d("工资充值测试AlertView", "确认");
                String trim = setTrans.getText().toString().trim();
                if (!"".equals(trim)) {
                    double editV = Double.parseDouble(trim);
                    if (editV > SreChargeMax || editV < SreChargeMin) {
                        Toasty.error(this, "充值金额不在范围内", 2000).show();
                        return;
                    }
                    RetrofitService.getInstance().getDailyRechargeTrans(this, editV, proxyName.getText().toString().trim());
                    setTrans.setText("");
                }
            }
        }
    }
}
