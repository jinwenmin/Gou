package com.example.king.gou.ui.proxyfragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.gou.R;
import com.example.king.gou.utils.DateUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class TeamZhuihaojlActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ProxyTop)
    RelativeLayout ProxyTop;
    @BindView(R.id.TeamZhuiHaojltimetext)
    TextView TeamZhuiHaojltimetext;
    @BindView(R.id.TeamZhuiHaojlrelateTime1)
    RelativeLayout TeamZhuiHaojlrelateTime1;
    @BindView(R.id.TeamZhuiHaojltimetext2)
    TextView TeamZhuiHaojltimetext2;
    @BindView(R.id.TeamZhuiHaojlrelateTime2)
    RelativeLayout TeamZhuiHaojlrelateTime2;
    @BindView(R.id.TeamZhuiHaojltimetext4)
    TextView TeamZhuiHaojltimetext4;
    @BindView(R.id.TeamZhuiHaojlgameLotteryType4)
    Spinner TeamZhuiHaojlgameLotteryType4;
    @BindView(R.id.TeamZhuiHaojlrelateBank4)
    RelativeLayout TeamZhuiHaojlrelateBank4;
    @BindView(R.id.TeamZhuiHaojlTabLayout)
    TabLayout TeamZhuiHaojlTabLayout;
    @BindView(R.id.TeamZhuiHaojlViewpager)
    ViewPager TeamZhuiHaojlViewpager;

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_zhuihaojl);
        ButterKnife.bind(this);
        initDateDialog();
        initClick();
    }

    private void initDateDialog() {
        int year;
        int month;
        int day;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        TeamZhuiHaojltimetext.setText(year + "-" + month + "-" + day);
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println();
        Log.e("times", sdf.format(date));
        TeamZhuiHaojltimetext2.setText(sdf.format(date));
        //第一个日期选择器
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TeamZhuiHaojltimetext.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                Long getdaytime = DateUtil.getInstance().toDate(year + "-" + (month + 1) + "-" + dayOfMonth) / 1000;
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(TeamZhuiHaojltimetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(TeamZhuiHaojltimetext.getText().toString()) / 1000;
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
                TeamZhuiHaojltimetext2.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(TeamZhuiHaojltimetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(TeamZhuiHaojltimetext.getText().toString()) / 1000;
                if (l2 - l1 > 864000) {
                    Toasty.warning(getApplicationContext(), "你的选择期限超过了10天", Toast.LENGTH_SHORT, true).show();
                }
            }
        }, year, month, day);

    }

    private void initClick() {
        TeamZhuiHaojlrelateTime1.setOnClickListener(this);
        TeamZhuiHaojlrelateTime2.setOnClickListener(this);
        Back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TeamBBrelateTime1:
                datePickerDialog.show();
                break;
            case R.id.TeamBBrelateTime2:
                datePickerDialog2.show();
                break;
            case R.id._back:
                finish();
                break;
        }
    }
}
