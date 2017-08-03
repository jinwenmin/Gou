package com.example.king.gou.ui.orderFrmActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.king.gou.R;
import com.example.king.gou.adapters.ActivityAdapter;
import com.example.king.gou.bean.ActivityBean;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.utils.L;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyActivityActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {


    List<Integer> ids = new ArrayList<>();
    List<String> types = new ArrayList<>();
    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ActivityListTop)
    RelativeLayout ActivityListTop;
    @BindView(R.id.ActivityListtimetext)
    TextView ActivityListtimetext;
    @BindView(R.id.ActivityListrelateTime1)
    RelativeLayout ActivityListrelateTime1;
    @BindView(R.id.ActivityListtimetext2)
    TextView ActivityListtimetext2;
    @BindView(R.id.ActivityListrelateTime2)
    RelativeLayout ActivityListrelateTime2;
    @BindView(R.id.SpinnerType)
    Spinner SpinnerType;
    @BindView(R.id.ActivityListrelateBank4)
    RelativeLayout ActivityListrelateBank4;
    @BindView(R.id.Activity_listView)
    ListView ActivityListView;
    @BindView(R.id.samount)
    TextView samount;
    @BindView(R.id.L1)
    LinearLayout L1;
    private ArrayAdapter<String> adapter1;
    ActivityAdapter activityAdapter;
    List<List<ActivityBean>> abs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        activityAdapter = new ActivityAdapter(this);
        ActivityListView.setAdapter(activityAdapter);
        initClick();
        initDateDialog();
        initSpinner();
    }

    private void initSpinner() {
        ids.add(0);
        ids.add(22);
        ids.add(29);
        ids.add(30);

        types.add("所有类型");
        types.add("活动奖金");
        types.add("亏损佣金");
        types.add("消费佣金");
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerType.setAdapter(adapter1);
        SpinnerType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getActivityRecordList(MyActivityActivity.this, 1, 100, "atid", "desc", ActivityListtimetext.getText().toString().trim(), ActivityListtimetext2.getText().toString().trim(), ids.get(SpinnerType.getSelectedItemPosition()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initClick() {
        ActivityListrelateTime1.setOnClickListener(this);
        ActivityListrelateTime2.setOnClickListener(this);
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
        ActivityListtimetext.setText(year + "-" + month + "-" + day);
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println();
        Log.e("times", sdf.format(date));
        ActivityListtimetext2.setText(sdf.format(date));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ActivityListrelateTime1:
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
                        ActivityListtimetext.setText(substring);
                        RetrofitService.getInstance().getActivityRecordList(MyActivityActivity.this, 1, 100, "atid", "desc", formatDate, ActivityListtimetext2.getText().toString().trim(), ids.get(SpinnerType.getSelectedItemPosition()));
                    }
                });
                dialog.show();
                break;
            case R.id.ActivityListrelateTime2:
                //datePickerDialog2.show();
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
                        // date2 = formatDate;
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        ActivityListtimetext2.setText(substring);
                        RetrofitService.getInstance().getActivityRecordList(MyActivityActivity.this, 1, 100, "atid", "desc", formatDate, formatDate, ids.get(SpinnerType.getSelectedItemPosition()));
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
        if (apiId == RetrofitService.API_ID_MYACTIVITYLIST) {
            if (object != null) {
                abs = (List<List<ActivityBean>>) object;
                if (abs.size() > 0) {
                    List<ActivityBean> activityBeen = abs.get(0);
                    samount.setText(activityBeen.get(0).getSamount() + "");
                }
                if (abs.size() == 2) {
                    List<ActivityBean> activityBeen = abs.get(1);
                    activityAdapter.getList(activityBeen);
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
