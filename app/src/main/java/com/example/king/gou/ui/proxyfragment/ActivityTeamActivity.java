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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.king.gou.R;
import com.example.king.gou.bean.CunQu;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.orderFrmActivity.CunQuActivity;
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


public class ActivityTeamActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {


    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ProxyTop)
    RelativeLayout ProxyTop;
    @BindView(R.id.ActivityTeamtimetext)
    TextView ActivityTeamtimetext;
    @BindView(R.id.ActivityTeamrelateTime1)
    RelativeLayout ActivityTeamrelateTime1;
    @BindView(R.id.ActivityTeamtimetext2)
    TextView ActivityTeamtimetext2;
    @BindView(R.id.ActivityTeamrelateTime2)
    RelativeLayout ActivityTeamrelateTime2;
    @BindView(R.id.SearchName)
    EditText SearchName;
    @BindView(R.id.ToSearchName)
    Button ToSearchName;
    @BindView(R.id.type)
    Spinner type;
    @BindView(R.id.team)
    Spinner team;
    @BindView(R.id.ActivityTeamList)
    ListView ActivityTeamList;
    List<Integer> typeid = new ArrayList<>();
    List<Integer> teamid = new ArrayList<>();
    List<String> typeName = new ArrayList<>();
    List<String> teamName = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        ButterKnife.bind(this);
        initClick();
        initDateDialog();
        initSpinner();
        initRetrofit();

    }

    private void initSpinnerSelect() {
        type.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // RetrofitService.getInstance().getReChargeWithDrawList(CunQuActivity.this, 1, 100, "atid", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), ids.get(i));
                //List<CunQu> c = new ArrayList<CunQu>();
                //rechargeDrawAdapter.getList(c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSpinner() {
        typeid.add(0);
        typeid.add(22);
        typeid.add(29);
        typeid.add(30);
        typeName.add("所有类型");
        typeName.add("活动奖金");
        typeName.add("亏损佣金");
        typeName.add("消费佣金");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeName);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        type.setAdapter(adapter);

        teamid.add(0);
        teamid.add(2);
        teamName.add("个人");
        teamName.add("团队");
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teamName);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        team.setAdapter(adapter1);
        initSpinnerSelect();
    }

    private void initRetrofit() {
       // RetrofitService.getInstance().getActivityTeamRecordList(this, 1, 100, "serial_number", "desc", ActivityTeamtimetext.getText().toString().trim(), ActivityTeamtimetext2.getText().toString().trim(), SearchName.getText().toString().trim(), typeid.get(type.getSelectedItemPosition()), teamid.get(team.getSelectedItemPosition()));
        RetrofitService.getInstance().getActivityTeamRecordList(this, 1, 100, "serial_number", "desc", "2011-02-02", "2017-07-11","", 0,0);
    }

    private void initClick() {
        ActivityTeamrelateTime1.setOnClickListener(this);
        ActivityTeamrelateTime2.setOnClickListener(this);
        Back.setOnClickListener(this);
    }

    private void initDateDialog() {
        int year;
        int month;
        int day;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        ActivityTeamtimetext.setText(year + "-" + month + "-" + day);
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Log.e("times", sdf.format(date));
        ActivityTeamtimetext2.setText(sdf.format(date));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ActivityTeamrelateTime1:
                //datePickerDialog.show();
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

                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        ActivityTeamtimetext.setText(substring);

                    }
                });
                dialog.show();
                break;
            case R.id.ActivityTeamrelateTime2:
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

                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        ActivityTeamtimetext2.setText(substring);
                        // RetrofitService.getInstance().getReChargeWithDrawList(CunQuActivity.this, 1, 100, "atid", "desc", timetext.getText().toString().trim(), formatDate, ids.get(SpinnerType.getSelectedItemPosition()));

                    }
                });
                dialog2.show();
                break;
            case R.id._back:
                finish();
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {

    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
