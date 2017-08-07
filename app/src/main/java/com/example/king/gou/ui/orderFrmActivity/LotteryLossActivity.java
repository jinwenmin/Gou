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
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.ProfitLossAdapter;
import com.example.king.gou.bean.LotteryLoss;
import com.example.king.gou.service.RetrofitService;
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


public class LotteryLossActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id.LotteryLoss_back)
    ImageView LotteryLossBack;
    @BindView(R.id.LotteryLossTop)
    RelativeLayout LotteryLossTop;
    @BindView(R.id.timetext)
    TextView timetext;
    @BindView(R.id.relateTime1)
    RelativeLayout relateTime1;
    @BindView(R.id.timetext2)
    TextView timetext2;
    @BindView(R.id.relateTime2)
    RelativeLayout relateTime2;
    @BindView(R.id.SpinnerGtype)
    Spinner SpinnerGtype;
    @BindView(R.id.relateBank3)
    RelativeLayout relateBank3;
    String date1;
    String date2;
    @BindView(R.id.Expend)
    TextView Expend;
    @BindView(R.id.ProfitLoss)
    TextView ProfitLoss;
    @BindView(R.id.Linear1)
    LinearLayout Linear1;
    @BindView(R.id.LotteryLossListView)
    ListView LotteryLossListView;
    private ArrayAdapter<String> adapter;
    ProfitLossAdapter profitLossAdapter;
    List<List<LotteryLoss>> losses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_loss);
        ButterKnife.bind(this); MyApp.getInstance().addActivitys(this);
        profitLossAdapter = new ProfitLossAdapter(this);
        LotteryLossListView.setAdapter(profitLossAdapter);
        initDateDialog();
        initClick();
        initSpinner();
    }

    private void initSpinner() {
        List<String> gtype = new ArrayList<>();
        gtype.add("所有类型");
        gtype.add("香港六合彩");
        gtype.add("彩票娱乐场");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gtype);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerGtype.setAdapter(adapter);
        SpinnerGtype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RetrofitService.getInstance().getProfitLossList(LotteryLossActivity.this, 1, 100, "betting_amount", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        RetrofitService.getInstance().getProfitLossList(LotteryLossActivity.this, 1, 100, "betting_amount", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), SpinnerGtype.getSelectedItemPosition());
    }

    private void initDateDialog() {
        int year;
        int month;
        int day;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        timetext.setText(year + "-" + month + "-" + day);
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        timetext2.setText(sdf.format(date));
        Log.e("times", sdf.format(date));

    }

    private void initClick() {
        relateTime1.setOnClickListener(this);
        relateTime2.setOnClickListener(this);
        LotteryLossBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relateTime1:
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
                        date2 = formatDate;
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        timetext.setText(substring);
                        RetrofitService.getInstance().getProfitLossList(LotteryLossActivity.this, 1, 100, "betting_amount", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), SpinnerGtype.getSelectedItemPosition());
                    }
                });
                dialog.show();
                break;
            case R.id.relateTime2:
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
                        date1 = formatDate;
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        timetext2.setText(substring);
                        RetrofitService.getInstance().getProfitLossList(LotteryLossActivity.this, 1, 100, "betting_amount", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), SpinnerGtype.getSelectedItemPosition());
                    }
                });
                dialog2.show();
                break;
            case R.id.LotteryLoss_back:
                finish();
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_PROFITLOSS) {
            losses = (List<List<LotteryLoss>>) object;
            List<LotteryLoss> lotteryLosses = losses.get(0);
            if (lotteryLosses.size()>0) {
                ProfitLoss.setText(lotteryLosses.get(0).getProfit_loss() + "");
                Expend.setText(lotteryLosses.get(0).getBetting_amounts() + "");
            }
            if (losses.size() == 2) {
                List<LotteryLoss> lotteryLosses1 = losses.get(1);
                profitLossAdapter.getList(lotteryLosses1);
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
