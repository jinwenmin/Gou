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
import com.example.king.gou.adapters.RechargeDrawAdapter;
import com.example.king.gou.bean.CunQu;
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


public class CunQuActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

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
    String date1;
    String date2;
    @BindView(R.id.CunQu_back)
    ImageView CunQuBack;
    @BindView(R.id.RechargeFirmTop)
    RelativeLayout RechargeFirmTop;
    @BindView(R.id.CunQuListView)
    ListView CunQuListView;
    @BindView(R.id.Linear)
    LinearLayout Linear;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.Expend)
    TextView Expend;
    @BindView(R.id.InCome)
    TextView InCome;
    @BindView(R.id.Linear1)
    LinearLayout Linear1;
    private ArrayAdapter<String> adapter;
    List<Integer> ids = new ArrayList<>();
    List<List<CunQu>> cq = new ArrayList<List<CunQu>>();
    RechargeDrawAdapter rechargeDrawAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cun_qu);
        ButterKnife.bind(this);
        rechargeDrawAdapter = new RechargeDrawAdapter(this);
        CunQuListView.setAdapter(rechargeDrawAdapter);
        initClick();
        initDateDialog();
        initSpinner();
        RetrofitService.getInstance().getReChargeWithDrawList(this, 1, 100, "atid", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), ids.get(SpinnerType.getSelectedItemPosition()));
    }

    private void initSpinner() {
        List<String> list = new ArrayList<>();
        ids.add(0);
        ids.add(10);
        ids.add(11);
        ids.add(12);
        ids.add(13);
        ids.add(14);
        ids.add(15);
        ids.add(16);
        ids.add(17);
        ids.add(18);
        ids.add(19);
        ids.add(20);
        ids.add(21);
        ids.add(22);
        ids.add(26);
        ids.add(31);
        ids.add(32);
        ids.add(33);
        ids.add(34);
        list.add("所有类型");
        list.add("[+]上级充值");
        list.add("[-]充值扣费");
        list.add("[-]小额扣费");
        list.add("特殊金额整理");
        list.add("[+]理赔充值");
        list.add("[-]管理员扣减");
        list.add("[-]提款申请");
        list.add("[+]提款失败");
        list.add("[=]提款成功");
        list.add("[+]在线充值");
        list.add("[+]现金充值");
        list.add("[+]充值手续费");
        list.add("[+]活动奖金");
        list.add("[+]支付宝充值");
        list.add("[+]转账汇款");
        list.add("[+]日工资");
        list.add("[-]日工资扣费");
        list.add("[+]日工资充值");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerType.setAdapter(adapter);
        SpinnerType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getReChargeWithDrawList(CunQuActivity.this, 1, 100, "atid", "desc", timetext.getText().toString().trim(), timetext2.getText().toString().trim(), ids.get(i));
                List<CunQu> c = new ArrayList<CunQu>();
                rechargeDrawAdapter.getList(c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initClick() {
        CunQuBack.setOnClickListener(this);
        relateTime1.setOnClickListener(this);
        relateTime2.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CunQu_back:
                finish();
                break;
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
                        RetrofitService.getInstance().getReChargeWithDrawList(CunQuActivity.this, 1, 100, "atid", "desc", formatDate, timetext2.getText().toString().trim(), ids.get(SpinnerType.getSelectedItemPosition()));
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
                        date2 = formatDate;
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        timetext2.setText(substring);
                        RetrofitService.getInstance().getReChargeWithDrawList(CunQuActivity.this, 1, 100, "atid", "desc", timetext.getText().toString().trim(), formatDate, ids.get(SpinnerType.getSelectedItemPosition()));

                    }
                });
                dialog2.show();
                break;

        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_RECHARGEDRAW) {
            if (object != null) {
                cq = (List<List<CunQu>>) object;
                InCome.setText(cq.get(0).get(0).getIncomes() + "");
                Expend.setText(cq.get(0).get(0).getExpengs() + "");
                rechargeDrawAdapter.getList(cq.get(1));
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
