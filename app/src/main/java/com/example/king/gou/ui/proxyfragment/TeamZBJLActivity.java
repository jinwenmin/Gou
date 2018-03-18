package com.example.king.gou.ui.proxyfragment;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.AccountsAdapter;
import com.example.king.gou.bean.AccountChange;
import com.example.king.gou.bean.GameType;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.orderFrmActivity.AccountChangeDetailActivity;
import com.example.king.gou.ui.orderFrmActivity.GrzbActivity;
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


public class TeamZBJLActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {


    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.ProxyTop)
    RelativeLayout ProxyTop;
    @BindView(R.id.TeamZBJLtimetext)
    TextView TeamZBJLtimetext;
    @BindView(R.id.TeamZBJLrelateTime1)
    RelativeLayout TeamZBJLrelateTime1;
    @BindView(R.id.TeamZBJLtimetext2)
    TextView TeamZBJLtimetext2;
    @BindView(R.id.TeamZBJLrelateTime2)
    RelativeLayout TeamZBJLrelateTime2;
    @BindView(R.id.SpinnerGameId)
    Spinner SpinnerGameId;
    @BindView(R.id.SpinnerType)
    Spinner SpinnerType;
    @BindView(R.id.SpinnerStype)
    Spinner SpinnerStype;
    @BindView(R.id.SpinnerModel)
    Spinner SpinnerModel;
    @BindView(R.id.SearchName)
    EditText SearchName;
    @BindView(R.id.ToSearchName)
    Button ToSearchName;
    @BindView(R.id.ChangeAmount)
    TextView ChangeAmount;
    @BindView(R.id.TeamListView)
    ListView TeamListView;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    String date1;
    String date2;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> adapter3;
    private ArrayAdapter<String> adapter4;
    List<Integer> ids = new ArrayList<>();
    List<String> types = new ArrayList<>();
    List<String> stypes = new ArrayList<>();
    List<String> models = new ArrayList<>();
    List<GameType> ListgameTypes = new ArrayList<GameType>();
    List<List<AccountChange>> acs;
    AccountsAdapter accountsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_zbjl);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);
        accountsAdapter = new AccountsAdapter(this);
        TeamListView.setAdapter(accountsAdapter);
        initClick();
        initDateDialog();
        initSpinner();


    }

    private void initRetrofit() {
        RetrofitService.getInstance().getTeamAccountChangeList(TeamZBJLActivity.this, 1, 100, "atid", "desc", getTime1(), getTime2(), getGameId(), getUserName(), gettypeId(), getStype(), getModel());
    }


    private String getTime2() {
        return TeamZBJLtimetext.getText().toString().trim();
    }

    private String getTime1() {
        return TeamZBJLtimetext2.getText().toString().trim();
    }

    private String getUserName() {
        return SearchName.getText().toString().trim();
    }

    private int getGameId() {
        return ListgameTypes.get(SpinnerGameId.getSelectedItemPosition()).getGid();
    }

    private int gettypeId() {
        return ids.get(SpinnerType.getSelectedItemPosition());
    }

    private int getStype() {
        return SpinnerStype.getSelectedItemPosition();
    }

    private int getModel() {
        return SpinnerModel.getSelectedItemPosition();
    }

    private void initSpinnerSelect() {
        SpinnerType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SpinnerStype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SpinnerModel.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SpinnerGameId.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initSpinner() {
        ids.add(0);
        ids.add(2);
        types.add("个人报表");
        types.add("团队报表");
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerType.setAdapter(adapter1);


        stypes.add("所有类型");
        stypes.add("加入游戏");
        stypes.add("投注返点");
        stypes.add("奖金派送");
        stypes.add("追号扣款");
        stypes.add("当期追号返款");
        stypes.add("游戏扣款");
        stypes.add("撤单返款");
        stypes.add("撤销返点");
        stypes.add("撤销派奖");
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stypes);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerStype.setAdapter(adapter2);


        models.add("所有模式");
        models.add("元");
        models.add("角");
        models.add("分");
        models.add("厘");
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, models);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerModel.setAdapter(adapter3);


    }

    private void initDateDialog() {
        int year;
        int month;
        int day;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        Date date1=new Date(times);
        TeamZBJLtimetext.setText( sdf.format(date1));
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);

        Log.e("times", sdf.format(date));
        TeamZBJLtimetext2.setText(sdf.format(date));


    }

    private void initClick() {
        TeamZBJLrelateTime1.setOnClickListener(this);
        TeamZBJLrelateTime2.setOnClickListener(this);
        Back.setOnClickListener(this);
        ToSearchName.setOnClickListener(this);
        TeamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TeamZBJLActivity.this, AccountChangeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Account", acs.get(1).get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
                        date1 = formatDate;
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
                        date2 = formatDate;
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        TeamZBJLtimetext2.setText(substring);
                        initRetrofit();

                    }
                });
                dialog2.show();
                break;
            case R.id._back:
                finish();
                break;
            case R.id.ToSearchName:
                initRetrofit();
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
            adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            //第三步：为适配器设置下拉列表下拉时的菜单样式。
            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //第四步：将适配器添加到下拉列表上
            SpinnerGameId.setAdapter(adapter4);
            initSpinnerSelect();
        }

        if (apiId == RetrofitService.API_ID_TEAMACCOUNTCHANGE) {

            if (object != null) {
                acs = (List<List<AccountChange>>) object;
                if (acs.size()>0) {
                    accountsAdapter.getList(acs.get(1));
                    if (acs.get(0).size()>0) {
                        ChangeAmount.setText("总计：" + acs.get(0).get(0).getAmountss() + "");
                    }
                }
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
