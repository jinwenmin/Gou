package com.example.king.gou.ui.orderFrmActivity;

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


public class MyBJLActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.MyBJLTop)
    RelativeLayout MyBJLTop;
    @BindView(R.id.MyBJLtimetext)
    TextView MyBJLtimetext;
    @BindView(R.id.MyBJLrelateTime1)
    RelativeLayout MyBJLrelateTime1;
    @BindView(R.id.MyBJLtimetext2)
    TextView MyBJLtimetext2;
    @BindView(R.id.MyBJLrelateTime2)
    RelativeLayout MyBJLrelateTime2;
    @BindView(R.id.MyBJLtimetext4)
    TextView MyBJLtimetext4;
    @BindView(R.id.MyBJLgameLotteryType4)
    Spinner MyBJLgameLotteryType4;
    @BindView(R.id.MyBJLrelateBank4)
    RelativeLayout MyBJLrelateBank4;
    @BindView(R.id.MyBJLtimetext3)
    TextView MyBJLtimetext3;
    @BindView(R.id.MyBJLgameLotteryType)
    Spinner MyBJLgameLotteryType;
    @BindView(R.id.MyBJLrelateBank3)
    RelativeLayout MyBJLrelateBank3;
    @BindView(R.id.MyBJLtimetext5)
    TextView MyBJLtimetext5;
    @BindView(R.id.MyBJLgameLotteryType2)
    Spinner MyBJLgameLotteryType2;
    @BindView(R.id.MyBJLrelateBank5)
    RelativeLayout MyBJLrelateBank5;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bjl);
        ButterKnife.bind(this);
        initClick();
        initDateDialog();
    }

    private void initClick() {
        MyBJLrelateTime1.setOnClickListener(this);
        MyBJLrelateTime2.setOnClickListener(this);
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
        MyBJLtimetext.setText(year + "-" + month + "-" + day);
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println();
        Log.e("times", sdf.format(date));
        MyBJLtimetext2.setText(sdf.format(date));
        //第一个日期选择器
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                MyBJLtimetext.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                Long getdaytime = DateUtil.getInstance().toDate(year + "-" + (month + 1) + "-" + dayOfMonth) / 1000;
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(MyBJLtimetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(MyBJLtimetext.getText().toString()) / 1000;
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
                MyBJLtimetext2.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(MyBJLtimetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(MyBJLtimetext.getText().toString()) / 1000;
                if (l2 - l1 > 864000) {
                    Toasty.warning(getApplicationContext(), "你的选择期限超过了10天", Toast.LENGTH_SHORT, true).show();
                }
            }
        }, year, month, day);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.MyBJLrelateTime1:
                datePickerDialog.show();
                break;
            case R.id.MyBJLrelateTime2:
                datePickerDialog2.show();
                break;
            case R.id._back:
                finish();
                break;
        }
    }
}
