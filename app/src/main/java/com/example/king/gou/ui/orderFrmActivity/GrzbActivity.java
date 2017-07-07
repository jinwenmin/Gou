package com.example.king.gou.ui.orderFrmActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import com.example.king.gou.bean.GameType;
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


public class GrzbActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id.gamejl_back)
    ImageView gamejlBack;
    @BindView(R.id.RechargeFirmTop)
    RelativeLayout RechargeFirmTop;
    @BindView(R.id.timetext)
    TextView timetext;
    @BindView(R.id.relateTime1)
    RelativeLayout relateTime1;
    @BindView(R.id.timetext2)
    TextView timetext2;
    @BindView(R.id.relateTime2)
    RelativeLayout relateTime2;
    @BindView(R.id.SpinnerType)
    Spinner SpinnerType;
    @BindView(R.id.SpinnerStype)
    Spinner SpinnerStype;
    @BindView(R.id.SpinnerModel)
    Spinner SpinnerModel;
    @BindView(R.id.GRZBListView)
    ListView GRZBListView;
    String date1;
    String date2;
    @BindView(R.id.SpinnerGame)
    Spinner SpinnerGame;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> adapter3;
    int Type;
    int Stype;
    int Model;
    int Game;
    List<GameType> ListgameTypes = new ArrayList<GameType>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grzb);
        ButterKnife.bind(this);
        initClick();
        initDateDialog();
        initSpinner();
        RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);

        Type = SpinnerType.getSelectedItemPosition();
        Stype = SpinnerGame.getSelectedItemPosition();
        Model = SpinnerModel.getSelectedItemPosition();
        RetrofitService.getInstance().getAccountChangeList(GrzbActivity.this, 1, 100, "atid", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), Game, Type, Stype, Model);

    }

    private void initSpinnerSelect() {
        SpinnerType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int gid = ListgameTypes.get(SpinnerGame.getSelectedItemPosition()).getGid();
                int selectedItemPosition = SpinnerType.getSelectedItemPosition();
                int selectedItemPosition1 = SpinnerModel.getSelectedItemPosition();
                Log.d("这些内容", gid + "   " + selectedItemPosition + "   " + selectedItemPosition1);
                RetrofitService.getInstance().getAccountChangeList(GrzbActivity.this, 1, 100, "atid", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), gid, selectedItemPosition, i, selectedItemPosition1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SpinnerStype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getAccountChangeList(GrzbActivity.this, 1, 100, "atid", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), ListgameTypes.get(SpinnerGame.getSelectedItemPosition()).getGid(), SpinnerType.getSelectedItemPosition(), i, SpinnerModel.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SpinnerModel.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getAccountChangeList(GrzbActivity.this, 1, 100, "atid", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), ListgameTypes.get(SpinnerGame.getSelectedItemPosition()).getGid(), SpinnerType.getSelectedItemPosition(), SpinnerStype.getSelectedItemPosition(), i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SpinnerGame.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getAccountChangeList(GrzbActivity.this, 1, 100, "atid", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), ListgameTypes.get(i).getGid(), SpinnerType.getSelectedItemPosition(), SpinnerStype.getSelectedItemPosition(), SpinnerModel.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSpinner() {
        List<String> list1 = new ArrayList<>();
        list1.add("个人报表");
        list1.add("团队报表");
        List<String> list2 = new ArrayList<>();
        list2.add("所有类型");
        list2.add("加入游戏");
        list2.add("投注返点");
        list2.add("奖金派送");
        list2.add("追号扣款");
        list2.add("当期追号返款");
        list2.add("游戏扣款");
        list2.add("撤单返款");
        list2.add("撤销返点");
        list2.add("撤销派奖");
        List<String> list3 = new ArrayList<>();
        list3.add("所有模式");
        list3.add("元");
        list3.add("角");
        list3.add("分");
        list3.add("厘");
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerType.setAdapter(adapter1);

        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        //第四步：将适配器添加到下拉列表上
        SpinnerStype.setAdapter(adapter1);

        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list3);
        //第四步：将适配器添加到下拉列表上
        SpinnerModel.setAdapter(adapter1);
        //
    }

    private void initClick() {
        relateTime1.setOnClickListener(this);
        relateTime2.setOnClickListener(this);
        gamejlBack.setOnClickListener(this);
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
        System.out.println();
        Log.e("times", sdf.format(date));
        timetext2.setText(sdf.format(date));
        //第一个日期选择器
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                timetext.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                Long getdaytime = DateUtil.getInstance().toDate(year + "-" + (month + 1) + "-" + dayOfMonth) / 1000;
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(timetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(timetext.getText().toString()) / 1000;
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
                timetext2.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(timetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(timetext.getText().toString()) / 1000;
                if (l2 - l1 > 864000) {
                    Toasty.warning(getApplicationContext(), "你的选择期限超过了10天", Toast.LENGTH_SHORT, true).show();
                }
            }
        }, year, month, day);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relateTime1:
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
                        date1 = formatDate;
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        timetext.setText(substring);
                        RetrofitService.getInstance().getAccountChangeList(GrzbActivity.this, 1, 100, "atid", "desc", formatDate, timetext2.getText().toString().trim(), ListgameTypes.get(SpinnerGame.getSelectedItemPosition()).getGid(), SpinnerType.getSelectedItemPosition(), SpinnerStype.getSelectedItemPosition(), SpinnerModel.getSelectedItemPosition());
                    }
                });
                dialog.show();
                break;
            case R.id.relateTime2:
                // datePickerDialog2.show();
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
                        timetext2.setText(substring);
                        RetrofitService.getInstance().getAccountChangeList(GrzbActivity.this, 1, 100, "atid", "desc", timetext.getText().toString().trim(), formatDate, ListgameTypes.get(SpinnerGame.getSelectedItemPosition()).getGid(), SpinnerType.getSelectedItemPosition(), SpinnerStype.getSelectedItemPosition(), SpinnerModel.getSelectedItemPosition());
                    }
                });
                dialog2.show();
                break;
            case R.id.gamejl_back:
                finish();
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_GAME4) {
            ListgameTypes = (List<GameType>) object;
            List<String> list = new ArrayList<>();
            for (int i = 0; i < ListgameTypes.size(); i++) {
                list.add(ListgameTypes.get(i).getName());
            }
            adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            //第三步：为适配器设置下拉列表下拉时的菜单样式。
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //第四步：将适配器添加到下拉列表上
            SpinnerGame.setAdapter(adapter2);
            Game = ListgameTypes.get(SpinnerGame.getSelectedItemPosition()).getGid();
            initSpinnerSelect();
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
