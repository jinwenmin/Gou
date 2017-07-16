package com.example.king.gou.ui.proxyfragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
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


public class TeamBaoBiaoActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {


    @BindView(R.id.TeamBetting_back)
    ImageView TeamBettingBack;
    @BindView(R.id.TeamBettingTop)
    RelativeLayout TeamBettingTop;
    @BindView(R.id.TeamBettingtimetext)
    TextView TeamBettingtimetext;
    @BindView(R.id.TeamBettingrelateTime1)
    RelativeLayout TeamBettingrelateTime1;
    @BindView(R.id.TeamBettingtimetext2)
    TextView TeamBettingtimetext2;
    @BindView(R.id.TeamBettingrelateTime2)
    RelativeLayout TeamBettingrelateTime2;
    @BindView(R.id.SearchName)
    EditText SearchName;
    @BindView(R.id.ToSearchName)
    Button ToSearchName;
    @BindView(R.id.SpinnerStatus)
    Spinner SpinnerStatus;
    @BindView(R.id.SpinnerType)
    Spinner SpinnerType;
    @BindView(R.id.SpinnerGameId)
    Spinner SpinnerGameId;
    @BindView(R.id.SpinnerGameRid)
    Spinner SpinnerGameRid;
    List<Integer> statusId = new ArrayList<>();
    List<String> statusName = new ArrayList<>();
    List<Integer> typeId = new ArrayList<>();
    List<String> typeName = new ArrayList<>();
    List<GameType> gameTypes = new ArrayList<>();
    List<GameType> gameTypes2 = new ArrayList<>();
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    ArrayAdapter<String> adapter4;
    List<UserTeamBetting> userTeamBettingLis = new ArrayList<>();
    @BindView(R.id.TeamBettingListView)
    ListView TeamBettingListView;
    TeamBettingAdapter teamBettingAdapter;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_bettinglist);
        ButterKnife.bind(this);
        teamBettingAdapter = new TeamBettingAdapter(this);
        TeamBettingListView.setAdapter(teamBettingAdapter);
        initClick();
        initDateDialog();
        initSpinner();
        RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);
    }

    private void initRetrofit() {
        RetrofitService.getInstance().getTeamBettingList(this, 1, 100, "bid", "desc", TeamBettingtimetext.getText().toString().trim() + " 00:00:01", TeamBettingtimetext2.getText().toString().trim() + " 23:59:59", SearchName.getText().toString().trim(), statusId.get(SpinnerStatus.getSelectedItemPosition()), gameTypes.get(SpinnerGameId.getSelectedItemPosition()).getGid(), gameTypes2.get(SpinnerGameRid.getSelectedItemPosition()).getTid(), typeId.get(SpinnerType.getSelectedItemPosition()));

    }

    private void initSpinnerSelect() {
        SpinnerGameId.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getGame(TeamBaoBiaoActivity.this, 7, gameTypes.get(i).getGid(), 0, 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SpinnerType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SpinnerStatus.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SpinnerGameRid.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
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
        statusId.add(-2);
        statusId.add(1);
        statusId.add(2);
        statusId.add(3);
        statusId.add(4);
        statusId.add(5);
        statusId.add(6);
        statusName.add("所有状态");
        statusName.add("未开奖");
        statusName.add("用户撤单");
        statusName.add("管理员撤单");
        statusName.add("未中奖");
        statusName.add("已中奖");
        statusName.add("系统撤单");
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusName);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerStatus.setAdapter(adapter1);
        typeId.add(0);
        typeId.add(2);
        typeName.add("个人查询");
        typeName.add("团队查询");
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeName);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerType.setAdapter(adapter2);
    }

    private void initClick() {
        TeamBettingrelateTime1.setOnClickListener(this);
        TeamBettingrelateTime2.setOnClickListener(this);
        TeamBettingBack.setOnClickListener(this);
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<UserTeamBetting> uu = new ArrayList<UserTeamBetting>();
                teamBettingAdapter.addList(uu);
                initRetrofit();
            }
        });
    }

    private void initDateDialog() {
        int year;
        int month;
        int day;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        TeamBettingtimetext.setText(year + "-" + month + "-" + day);
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println();
        Log.e("times", sdf.format(date));
        TeamBettingtimetext2.setText(sdf.format(date));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TeamBettingrelateTime1:
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
                        TeamBettingtimetext.setText(substring);
                        initRetrofit();
                    }
                });
                dialog.show();
                break;
            case R.id.TeamBettingrelateTime2:
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
                        TeamBettingtimetext2.setText(substring);
                        initRetrofit();
                    }
                });
                dialog2.show();
                break;
            case R.id.TeamBetting_back:
                finish();
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_GAME4) {
            if (object != null) {
                gameTypes = (List<GameType>) object;
                List<String> types = new ArrayList<>();
                for (int i = 0; i < gameTypes.size(); i++) {
                    types.add(gameTypes.get(i).getName());
                }
                adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                SpinnerGameId.setAdapter(adapter3);
                RetrofitService.getInstance().getGame(TeamBaoBiaoActivity.this, 7, gameTypes.get(0).getGid(), 0, 0);
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
                adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                SpinnerGameRid.setAdapter(adapter4);
                initSpinnerSelect();

            }
        }
        if (apiId == RetrofitService.API_ID_TEAMBETTING) {
            userTeamBettingLis = (List<UserTeamBetting>) object;
            teamBettingAdapter.addList(userTeamBettingLis);
            Swipe.setRefreshing(false);
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
