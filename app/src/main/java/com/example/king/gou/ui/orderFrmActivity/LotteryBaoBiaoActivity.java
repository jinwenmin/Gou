package com.example.king.gou.ui.orderFrmActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.TeamBettingAdapter;
import com.example.king.gou.bean.GameType;
import com.example.king.gou.bean.UserTeamBetting;
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


public class LotteryBaoBiaoActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {


    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.LotteryBBTop)
    RelativeLayout LotteryBBTop;
    @BindView(R.id.LotteryBBtimetext)
    TextView LotteryBBtimetext;
    @BindView(R.id.LotteryBBrelateTime1)
    RelativeLayout LotteryBBrelateTime1;
    @BindView(R.id.LotteryBBtimetext2)
    TextView LotteryBBtimetext2;
    @BindView(R.id.LotteryBBrelateTime2)
    RelativeLayout LotteryBBrelateTime2;
    @BindView(R.id.SpinnerGameid)
    Spinner SpinnerGameid;
    @BindView(R.id.SpinnerGameRid)
    Spinner SpinnerGameRid;
    @BindView(R.id.SpinnerStatus)
    Spinner SpinnerStatus;
    String date1;
    String date2;
    List<GameType> gameTypes1 = new ArrayList<>();
    List<GameType> gameTypes2 = new ArrayList<>();
    @BindView(R.id.UserBettingList)
    ListView UserBettingList;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    List<Integer> ints;
    List<String> status;
    TeamBettingAdapter teamBettingAdapter;
    List<UserTeamBetting> userTeamBettingLis = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_bao_biao);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);
        teamBettingAdapter = new TeamBettingAdapter(this);
        UserBettingList.setAdapter(teamBettingAdapter);
        initS();
        initClick();
        initDateDialog();

    }

    private void initS() {
        ints = new ArrayList<>();
        ints.add(-2);
        ints.add(-1);
        ints.add(0);
        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);
        ints.add(5);
        ints.add(6);
        status = new ArrayList<>();
        status.add("所有状态");
        status.add("未开奖");
        status.add("未中奖");
        status.add("已派奖");
        status.add("本人撤单");
        status.add("管理员撤单");
        status.add("已过期");
        status.add("平台撤单");
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerStatus.setAdapter(adapter2);

    }

    private void initSpinnerSelect() {
        RetrofitService.getInstance().getBettingList(LotteryBaoBiaoActivity.this, 1, 100, "bid", "desc", LotteryBBtimetext.getText().toString().trim(), LotteryBBtimetext2.getText().toString().trim(), gameTypes1.get(SpinnerGameid.getSelectedItemPosition()).getGid(), gameTypes2.get(SpinnerGameRid.getSelectedItemPosition()).getTid(), ints.get(SpinnerStatus.getSelectedItemPosition()));
    }

    private void initSpinner() {
        SpinnerGameid.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RetrofitService.getInstance().getGame(LotteryBaoBiaoActivity.this, 7, gameTypes1.get(position).getGid(), 0, 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpinnerGameRid.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initSpinnerSelect();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpinnerStatus.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initSpinnerSelect();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initClick() {
        LotteryBBrelateTime1.setOnClickListener(this);
        LotteryBBrelateTime2.setOnClickListener(this);
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
        LotteryBBtimetext.setText(year + "-" + month + "-" + day);
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println();
        LotteryBBtimetext2.setText(sdf.format(date));
        Log.e("times", sdf.format(date));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LotteryBBrelateTime1:
                // Toast.makeText(this, "点击了第一个", Toast.LENGTH_SHORT).show();
                //  datePickerDialog.show();
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
                        LotteryBBtimetext.setText(substring);
                        initSpinnerSelect();
                    }
                });

                dialog.show();
                break;
            case R.id.LotteryBBrelateTime2:
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
                        LotteryBBtimetext2.setText(substring);
                        initSpinnerSelect();
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
        if (apiId == RetrofitService.API_ID_GAME4) {
            if (object != null) {
                gameTypes1 = (List<GameType>) object;
                List<String> types = new ArrayList<>();
                for (int i = 0; i < gameTypes1.size(); i++) {
                    types.add(gameTypes1.get(i).getName());
                }
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                SpinnerGameid.setAdapter(adapter);
                RetrofitService.getInstance().getGame(LotteryBaoBiaoActivity.this, 7, gameTypes1.get(0).getGid(), 0, 0);
            }
        }
        if (apiId == RetrofitService.API_ID_GAME7) {
            if (object != null) {
                gameTypes2 = (List<GameType>) object;
                List<String> types = new ArrayList<>();
                for (int i = 0; i < gameTypes2.size(); i++) {
                    types.add(gameTypes2.get(i).getName());
                    Log.d("gameTypes2", gameTypes2.get(i).getName());
                }
                adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                SpinnerGameRid.setAdapter(adapter1);
                initSpinner();

            }
        }
        if (apiId == RetrofitService.API_ID_USERBETTING) {
            if (object != null) {
                userTeamBettingLis = (List<UserTeamBetting>) object;
                teamBettingAdapter.addList(userTeamBettingLis);
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
