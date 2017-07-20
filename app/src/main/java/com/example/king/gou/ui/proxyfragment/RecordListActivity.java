package com.example.king.gou.ui.proxyfragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.king.gou.adapters.RecordListAdapter;
import com.example.king.gou.bean.GameType;
import com.example.king.gou.bean.RecordList;
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


public class RecordListActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.Top)
    RelativeLayout Top;
    @BindView(R.id.TeamZBJLtimetext)
    TextView TeamZBJLtimetext;
    @BindView(R.id.TeamZBJLrelateTime1)
    RelativeLayout TeamZBJLrelateTime1;
    @BindView(R.id.TeamZBJLtimetext2)
    TextView TeamZBJLtimetext2;
    @BindView(R.id.TeamZBJLrelateTime2)
    RelativeLayout TeamZBJLrelateTime2;
    @BindView(R.id.SearchPeriod)
    EditText SearchPeriod;
    @BindView(R.id.ToSearchName)
    Button ToSearchName;
    @BindView(R.id.GameName)
    Spinner GameName;
    @BindView(R.id.RecordList)
    ListView RecordList;
    List<GameType> ListgameTypes = new ArrayList<GameType>();
    ArrayAdapter<String> adapter;
    List<RecordList> rc = new ArrayList<>();
    RecordListAdapter recordListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        ButterKnife.bind(this);
        recordListAdapter = new RecordListAdapter(this);
        RecordList.setAdapter(recordListAdapter);
        initCLick();
        initDateDialog();
        RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);
    }

    private void initCLick() {
        Back.setOnClickListener(this);
        TeamZBJLrelateTime1.setOnClickListener(this);
        TeamZBJLrelateTime2.setOnClickListener(this);
        ToSearchName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ToSearchName:
                initRetrofit();
                break;
            case R.id._back:
                finish();
                break;
            case R.id.TeamZBJLrelateTime1:
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
                        TeamZBJLtimetext.setText(substring);
                        initRetrofit();
                    }
                });
                dialog.show();
                break;
            case R.id.TeamZBJLrelateTime2:
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
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        TeamZBJLtimetext2.setText(substring);
                        initRetrofit();

                    }
                });
                dialog2.show();
                break;
        }
    }

    private void initDateDialog() {
        int year;
        int month;
        int day;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        TeamZBJLtimetext.setText(year + "-" + month + "-" + day);
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println();
        Log.e("times", sdf.format(date));
        TeamZBJLtimetext2.setText(sdf.format(date));
        //第一个日期选择器
    }

    private void initRetrofit() {
        RetrofitService.getInstance().getRecordList(RecordListActivity.this, 1, 100, "draw_period", "desc", ListgameTypes.get(GameName.getSelectedItemPosition()).getGid(), SearchPeriod.getText().toString().trim(), TeamZBJLtimetext.getText().toString().trim(), TeamZBJLtimetext2.getText().toString().trim());
    }

    private void initSpinnerSelect() {
        GameName.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_GAME4) {
            ListgameTypes = (List<GameType>) object;
            List<String> list = new ArrayList<>();
            for (int i = 0; i < ListgameTypes.size(); i++) {
                list.add(ListgameTypes.get(i).getName());
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            //第三步：为适配器设置下拉列表下拉时的菜单样式。
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //第四步：将适配器添加到下拉列表上
            GameName.setAdapter(adapter);
            initSpinnerSelect();
        }
        if (apiId == RetrofitService.API_ID_RECORDLIST) {
            if (object != null) {
                rc = (List<RecordList>) object;
                recordListAdapter.addList(rc);
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
