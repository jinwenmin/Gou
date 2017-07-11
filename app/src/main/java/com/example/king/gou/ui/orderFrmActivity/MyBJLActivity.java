package com.example.king.gou.ui.orderFrmActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    @BindView(R.id.ActivityListrelateBank4)
    RelativeLayout ActivityListrelateBank4;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        initClick();
        initDateDialog();
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
