package com.example.king.gou.ui.proxyfragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.king.gou.R;
import com.example.king.gou.adapters.RechargeDrawAdapter;
import com.example.king.gou.bean.CunQu;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.DateUtil;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class TeamCunQuActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {


    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.LotteryBBTop)
    RelativeLayout LotteryBBTop;
    @BindView(R.id.TeamCunQutimetext)
    TextView TeamCunQutimetext;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.TeamCunQurelateTime1)
    RelativeLayout TeamCunQurelateTime1;
    @BindView(R.id.TeamCunQutimetext2)
    TextView TeamCunQutimetext2;
    @BindView(R.id.TeamCunQurelateTime2)
    RelativeLayout TeamCunQurelateTime2;
    @BindView(R.id.TeamCunquLinear1)
    LinearLayout TeamCunquLinear1;

    @BindView(R.id.Expend)
    TextView Expend;
    @BindView(R.id.InCome)
    TextView InCome;
    @BindView(R.id.Linear1)
    LinearLayout Linear1;
    @BindView(R.id.CunQuListView)
    ListView CunQuListView;
    @BindView(R.id.Spinnertype)
    Spinner Spinnertype;
    @BindView(R.id.Spinnerteam)
    Spinner Spinnerteam;
    @BindView(R.id.UserName)
    EditText UserName;
    @BindView(R.id.SearchUserName)
    Button SearchUserName;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    List<Integer> ids = new ArrayList<>();
    List<Integer> teamid = new ArrayList<>();
    List<String> teamName = new ArrayList<>();
    List<List<CunQu>> cq = new ArrayList<List<CunQu>>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    String date1;
    String date2;
    RechargeDrawAdapter rechargeDrawAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_cun_qu);
        ButterKnife.bind(this);
        RetrofitService.getInstance().getUserStatistics(this);
        RetrofitService.getInstance().getTeamUserList(this,1,100,"uid","desc",1,0);
        rechargeDrawAdapter = new RechargeDrawAdapter(this);
        CunQuListView.setAdapter(rechargeDrawAdapter);
        initClick();
        initDateDialog();
        initSpinner();
    }

    private void initClick() {
        TeamCunQurelateTime1.setOnClickListener(this);
        TeamCunQurelateTime2.setOnClickListener(this);
        Back.setOnClickListener(this);
        SearchUserName.setOnClickListener(this);
    }

    private void initSpinner() {
        List<String> list = new ArrayList<>();
        ids.add(0);
        ids.add(10);
        ids.add(11);
        ids.add(12);
        ids.add(13);
        ids.add(14);
        ids.add(15);
        ids.add(16);
        ids.add(17);
        ids.add(18);
        ids.add(19);
        ids.add(20);
        ids.add(21);
        ids.add(22);
        ids.add(26);
        ids.add(31);
        ids.add(32);
        ids.add(33);
        ids.add(34);
        list.add("所有类型");
        list.add("[+]上级充值");
        list.add("[-]充值扣费");
        list.add("[-]小额扣费");
        list.add("特殊金额整理");
        list.add("[+]理赔充值");
        list.add("[-]管理员扣减");
        list.add("[-]提款申请");
        list.add("[+]提款失败");
        list.add("[=]提款成功");
        list.add("[+]在线充值");
        list.add("[+]现金充值");
        list.add("[+]充值手续费");
        list.add("[+]活动奖金");
        list.add("[+]支付宝充值");
        list.add("[+]转账汇款");
        list.add("[+]日工资");
        list.add("[-]日工资扣费");
        list.add("[+]日工资充值");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        Spinnertype.setAdapter(adapter);
        Spinnertype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getTeamReChargeWithDrawList(TeamCunQuActivity.this, 1, 100, "atid", "desc", TeamCunQutimetext.getText().toString().trim(), TeamCunQutimetext2.getText().toString().trim(), UserName.getText().toString().trim(), ids.get(i), teamid.get(Spinnerteam.getSelectedItemPosition()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        teamid.add(0);
        teamid.add(2);
        teamName.add("个人报表");
        teamName.add("团队报表");
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teamName);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        Spinnerteam.setAdapter(adapter1);
        Spinnerteam.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getTeamReChargeWithDrawList(TeamCunQuActivity.this, 1, 100, "atid", "desc", TeamCunQutimetext.getText().toString().trim(), TeamCunQutimetext2.getText().toString().trim(), UserName.getText().toString().trim(), ids.get(Spinnertype.getSelectedItemPosition()), teamid.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initDateDialog() {
        int year;
        int month;
        int day;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        TeamCunQutimetext.setText(year + "-" + month + "-" + day);
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println();
        Log.e("times", sdf.format(date));
        TeamCunQutimetext2.setText(sdf.format(date));
        //第一个日期选择器
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TeamCunQutimetext.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                Long getdaytime = DateUtil.getInstance().toDate(year + "-" + (month + 1) + "-" + dayOfMonth) / 1000;
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(TeamCunQutimetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(TeamCunQutimetext.getText().toString()) / 1000;
                if (l2 - l1 > 864000) {
                    Toasty.warning(getApplicationContext(), "你的选择期限超过了10天", Toast.LENGTH_SHORT, true).show();
                }
                Log.e("现在时长", l2 + "  " + l1);
            }
        }, year, month, day);
        //第二个日期选择器
        datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TeamCunQutimetext2.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(TeamCunQutimetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(TeamCunQutimetext.getText().toString()) / 1000;
                if (l2 - l1 > 864000) {
                    Toasty.warning(getApplicationContext(), "你的选择期限超过了10天", Toast.LENGTH_SHORT, true).show();
                }
            }
        }, year, month, day);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TeamCunQurelateTime1:
                DatePickDialog dialog = new DatePickDialog(this);
                //设置上下年分限制
                dialog.setYearLimt(5);
                //设置标题
                dialog.setTitle("选择时间");
                //设置类型
                dialog.setType(DateType.TYPE_ALL);
                //设置消息体的显示格式，日期格式
                dialog.setMessageFormat("yyyy-MM-dd HH:mm:ss");
                //设置选择回调
                dialog.setOnChangeLisener(null);
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        String formatDate = RxUtils.getInstance().FormatDate(date);
                        date1 = formatDate;
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        TeamCunQutimetext.setText(substring);
                        RetrofitService.getInstance().getTeamReChargeWithDrawList(TeamCunQuActivity.this, 1, 100, "atid", "desc", TeamCunQutimetext.getText().toString().trim(), TeamCunQutimetext2.getText().toString().trim(), UserName.getText().toString().trim(), ids.get(Spinnertype.getSelectedItemPosition()), teamid.get(Spinnerteam.getSelectedItemPosition()));
                    }
                });
                dialog.show();
                break;
            case R.id.TeamCunQurelateTime2:
                DatePickDialog dialog2 = new DatePickDialog(this);
                //设置上下年分限制
                dialog2.setYearLimt(5);
                //设置标题
                dialog2.setTitle("选择时间");
                //设置类型
                dialog2.setType(DateType.TYPE_ALL);
                //设置消息体的显示格式，日期格式
                dialog2.setMessageFormat("yyyy-MM-dd HH:mm:ss");
                //设置选择回调
                dialog2.setOnChangeLisener(null);
                //设置点击确定按钮回调
                dialog2.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        String formatDate = RxUtils.getInstance().FormatDate(date);
                        date2 = formatDate;
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        TeamCunQutimetext2.setText(substring);
                        RetrofitService.getInstance().getTeamReChargeWithDrawList(TeamCunQuActivity.this, 1, 100, "atid", "desc", TeamCunQutimetext.getText().toString().trim(), formatDate, UserName.getText().toString().trim(), ids.get(Spinnertype.getSelectedItemPosition()), teamid.get(Spinnerteam.getSelectedItemPosition()));
                    }
                });
                dialog2.show();
                break;
            case R.id._back:
                finish();
                break;
            case R.id.SearchUserName:
                RetrofitService.getInstance().getTeamReChargeWithDrawList(TeamCunQuActivity.this, 1, 100, "atid", "desc", TeamCunQutimetext.getText().toString().trim(), TeamCunQutimetext2.getText().toString().trim(), UserName.getText().toString().trim(), ids.get(Spinnertype.getSelectedItemPosition()), teamid.get(Spinnerteam.getSelectedItemPosition()));
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_TEAMCQ) {
            if (object != null) {
                cq = (List<List<CunQu>>) object;
                InCome.setText(cq.get(0).get(0).getIncomes() + "");
                Expend.setText(cq.get(0).get(0).getExpengs() + "");
                if (cq.size() > 1) {
                    rechargeDrawAdapter.getList(cq.get(1));
                }
            }
            CunQuListView.setAdapter(rechargeDrawAdapter);
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
