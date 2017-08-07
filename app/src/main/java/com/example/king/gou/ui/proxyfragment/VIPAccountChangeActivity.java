package com.example.king.gou.ui.proxyfragment;

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
import com.example.king.gou.adapters.VIPAccountChangeAdapter;
import com.example.king.gou.bean.GameType;
import com.example.king.gou.bean.VIPAccountChange;
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


public class VIPAccountChangeActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.LotteryLoss_back)
    ImageView LotteryLossBack;
    @BindView(R.id.VIPACTop)
    RelativeLayout VIPACTop;
    @BindView(R.id.timetext)
    TextView timetext;
    @BindView(R.id.relateTime1)
    RelativeLayout relateTime1;
    @BindView(R.id.timetext2)
    TextView timetext2;
    @BindView(R.id.relateTime2)
    RelativeLayout relateTime2;
    @BindView(R.id.SpinnerGame)
    Spinner SpinnerGame;
    @BindView(R.id.SpinnerStype)
    Spinner SpinnerStype;
    @BindView(R.id.SpinnerModel)
    Spinner SpinnerModel;

    List<Integer> stypeId = new ArrayList<>();
    List<String> stype = new ArrayList<>();

    List<String> model = new ArrayList<>();
    ArrayAdapter<String> adapterStype;
    ArrayAdapter<String> adapterGame;
    ArrayAdapter<String> adapterModel;
    List<GameType> gameTypes1 = new ArrayList<>();
    @BindView(R.id.vipAccountChangeListView)
    ListView vipAccountChangeListView;
    private int uid;
    VIPAccountChangeAdapter vipAccountChangeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vipaccount_change);
        ButterKnife.bind(this); MyApp.getInstance().addActivitys(this);
        vipAccountChangeAdapter = new VIPAccountChangeAdapter(this);
        vipAccountChangeListView.setAdapter(vipAccountChangeAdapter);
        uid = getIntent().getIntExtra("uid", 0);
        initDateDialog();
        initClick();
        initSpinner();


    }

    private void initSinnerSelect() {
        SpinnerGame.setOnItemSelectedListener(this);
        SpinnerStype.setOnItemSelectedListener(this);
        SpinnerModel.setOnItemSelectedListener(this);

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
    }

    private void initSpinner() {
        stypeId.add(0);
        stypeId.add(1);
        stypeId.add(2);
        stypeId.add(3);
        stypeId.add(4);
        stypeId.add(5);
        stypeId.add(6);
        stypeId.add(7);
        stypeId.add(8);
        stypeId.add(9);
        stypeId.add(10);
        stypeId.add(11);
        stypeId.add(14);
        stypeId.add(16);
        stypeId.add(17);
        stypeId.add(18);
        stypeId.add(19);
        stypeId.add(20);
        stypeId.add(21);
        stypeId.add(22);
        stypeId.add(26);
        stypeId.add(31);


        stype.add("所有类型");
        stype.add("加入游戏");
        stype.add("投注返点");
        stype.add("奖金派送");
        stype.add("追号扣款");
        stype.add("当期追号返款");
        stype.add("游戏扣款");
        stype.add("撤单返款");
        stype.add("撤销返点");
        stype.add("撤销派奖");
        stype.add("上级充值");
        stype.add("充值扣费");
        stype.add("理赔充值");
        stype.add("提款申请");
        stype.add("提款失败");
        stype.add("提款成功");
        stype.add("在线充值");
        stype.add("现金充值");
        stype.add("充值手续费");
        stype.add("促销充值");
        stype.add("支付宝充值");
        stype.add("转账汇款");

        model.add("全部模式");
        model.add("元");
        model.add("角");
        model.add("分");
        model.add("厘");
        adapterStype = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stype);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterStype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerStype.setAdapter(adapterStype);


        adapterModel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, model);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerModel.setAdapter(adapterModel);

        RetrofitService.getInstance().getGame(VIPAccountChangeActivity.this, 4, 0, 0, 0);
    }

    private void initClick() {
        LotteryLossBack.setOnClickListener(this);
        relateTime1.setOnClickListener(this);
        relateTime2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LotteryLoss_back:
                finish();
                break;
            case R.id.relateTime1:
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
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        timetext.setText(substring);

                        initRetrofit();


                    }
                });
                dialog.show();

                break;
            case R.id.relateTime2:
                //  datePickerDialog2.show();
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
                        timetext2.setText(substring);
                        initRetrofit();

                    }
                });
                dialog2.show();
                break;
        }
    }

    private void initRetrofit() {
        String time1 = timetext.getText().toString().trim();
        String time2 = timetext2.getText().toString().trim();
        RetrofitService.getInstance().getCapitalChangeList(this, 1, 100, "atid", "desc", uid, time1, time2, gameTypes1.get(SpinnerGame.getSelectedItemPosition()).getGid(), stypeId.get(SpinnerStype.getSelectedItemPosition()), SpinnerModel.getSelectedItemPosition());
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
                adapterGame = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapterGame.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                SpinnerGame.setAdapter(adapterGame);
                initSinnerSelect();

            }
        }
        if (apiId == RetrofitService.API_ID_VIPACCCHANGE) {
            if (object != null) {
                List<VIPAccountChange> vp = (List<VIPAccountChange>) object;
                vipAccountChangeAdapter.getList(vp);
            }
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        initRetrofit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
