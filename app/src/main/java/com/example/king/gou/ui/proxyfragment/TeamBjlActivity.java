package com.example.king.gou.ui.proxyfragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
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


public class TeamBjlActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.TeamBJLTop)
    RelativeLayout TeamBJLTop;
    @BindView(R.id.TeamBJLtimetext)
    TextView TeamBJLtimetext;
    @BindView(R.id.TeamBJLrelateTime1)
    RelativeLayout TeamBJLrelateTime1;
    @BindView(R.id.TeamBJLtimetext2)
    TextView TeamBJLtimetext2;
    @BindView(R.id.TeamBJLrelateTime2)
    RelativeLayout TeamBJLrelateTime2;
    @BindView(R.id.TeamBJLtimetext4)
    TextView TeamBJLtimetext4;
    @BindView(R.id.TeamBJLgameLotteryType4)
    Spinner TeamBJLgameLotteryType4;
    @BindView(R.id.TeamBJLrelateBank4)
    RelativeLayout TeamBJLrelateBank4;
    @BindView(R.id.TeamBJLtimetext3)
    TextView TeamBJLtimetext3;
    @BindView(R.id.TeamBJLgameLotteryType)
    Spinner TeamBJLgameLotteryType;
    @BindView(R.id.TeamBJLrelateBank3)
    RelativeLayout TeamBJLrelateBank3;
    @BindView(R.id.TeamBJLtimetext5)
    TextView TeamBJLtimetext5;
    @BindView(R.id.TeamBJLgameLotteryType2)
    Spinner TeamBJLgameLotteryType2;
    @BindView(R.id.TeamBJLrelateBank5)
    RelativeLayout TeamBJLrelateBank5;

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_bjl);
        ButterKnife.bind(this);
        initClick();
        initDateDialog();
    }

    private void initClick() {
        TeamBJLrelateTime1.setOnClickListener(this);
        TeamBJLrelateTime2.setOnClickListener(this);
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
        TeamBJLtimetext.setText(year + "-" + month + "-" + day);
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println();
        Log.e("times", sdf.format(date));
        TeamBJLtimetext2.setText(sdf.format(date));
        //第一个日期选择器
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TeamBJLtimetext.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                Long getdaytime = DateUtil.getInstance().toDate(year + "-" + (month + 1) + "-" + dayOfMonth) / 1000;
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(TeamBJLtimetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(TeamBJLtimetext.getText().toString()) / 1000;
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
                TeamBJLtimetext2.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(TeamBJLtimetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(TeamBJLtimetext.getText().toString()) / 1000;
                if (l2 - l1 > 864000) {
                    Toasty.warning(getApplicationContext(), "你的选择期限超过了10天", Toast.LENGTH_SHORT, true).show();
                }
            }
        }, year, month, day);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TeamBJLrelateTime1:
                datePickerDialog.show();
                break;
            case R.id.TeamBJLrelateTime2:
                datePickerDialog2.show();
                break;
            case R.id._back:
                finish();
                break;
        }
    }
}
