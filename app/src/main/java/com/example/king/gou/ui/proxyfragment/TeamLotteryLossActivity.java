package com.example.king.gou.ui.proxyfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.king.gou.bean.TeamUserInfo;
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


public class TeamLotteryLossActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

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
    @BindView(R.id.UserName)
    EditText UserName;
    @BindView(R.id.SearchUserName)
    Button SearchUserName;
    @BindView(R.id.TLotteryLossUid)
    Spinner TLotteryLossUid;
    @BindView(R.id.TLotteryLossGtype)
    Spinner TLotteryLossGtype;
    @BindView(R.id.TLotteryLossStype)
    Spinner TLotteryLossStype;
    @BindView(R.id.TLotteryLossTeam)
    Spinner TLotteryLossTeam;
    @BindView(R.id.Expend)
    TextView Expend;
    @BindView(R.id.InCome)
    TextView InCome;
    @BindView(R.id.Linear1)
    LinearLayout Linear1;
    @BindView(R.id.teamProfitLossList)
    ListView teamProfitLossList;
    private List<TeamUserInfo> uids = new ArrayList<>();
    List<Integer> UidIds = new ArrayList<>();
    List<String> UserNickNames = new ArrayList<>();
    List<String> Gtypes = new ArrayList<>();
    List<String> Stype = new ArrayList<>();
    List<Integer> Teamids = new ArrayList<>();
    List<String> TeamName = new ArrayList<>();
    private ArrayAdapter<String> adapterUid;
    private ArrayAdapter<String> adapterGtype;
    private ArrayAdapter<String> adapterStype;
    private ArrayAdapter<String> adapterTeam;
    List<List<LotteryLoss>> loss = new ArrayList<>();

    ProfitLossAdapter profitLossAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_lottery_loss);
        ButterKnife.bind(this); MyApp.getInstance().addActivitys(this);
        profitLossAdapter = new ProfitLossAdapter(this);
        teamProfitLossList.setAdapter(profitLossAdapter);
        uids = MyApp.getInstance().getUids();
        initClick();
        initSpinner();
        initDateDialog();
        initSPinnerSelect();
    }

    private void initSPinnerSelect() {
        TLotteryLossUid.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        TLotteryLossGtype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        TLotteryLossStype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        TLotteryLossTeam.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initRetrofit() {
        String t1 = timetext.getText().toString().trim();
        String t2 = timetext2.getText().toString().trim();
        String searName = UserName.getText().toString().trim();
        int uid = UidIds.get(TLotteryLossUid.getSelectedItemPosition());
        int Gtypeid = TLotteryLossGtype.getSelectedItemPosition();
        int StypeId = TLotteryLossStype.getSelectedItemPosition();
        int TeamId = Teamids.get(TLotteryLossTeam.getSelectedItemPosition());
        RetrofitService.getInstance().getTeamProfitLossList(this, 1, 100, "betting_amount", "desc", t1, t2, searName, uid, Gtypeid, StypeId, TeamId);
    }

    private void initClick() {
        relateTime1.setOnClickListener(this);
        relateTime2.setOnClickListener(this);
        SearchUserName.setOnClickListener(this);
    }

    private void initSpinner() {
        for (int i = 0; i < uids.size(); i++) {
            UidIds.add(uids.get(i).getUid());
            UserNickNames.add(uids.get(i).getName());
        }

        //UId
        adapterUid = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, UserNickNames);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterUid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        TLotteryLossUid.setAdapter(adapterUid);

        Gtypes.add("所有类型");
        Gtypes.add("彩票娱乐场");
        Gtypes.add("香港时时彩");
        //Gtype
        adapterGtype = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Gtypes);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterGtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        TLotteryLossGtype.setAdapter(adapterGtype);

        Stype.add("当日盈亏");
        Stype.add("历史盈亏");
        //Stype
        adapterStype = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Stype);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterStype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        TLotteryLossStype.setAdapter(adapterStype);
        Teamids.add(0);
        Teamids.add(2);
        TeamName.add("个人");
        TeamName.add("团队");
        //Team
        adapterTeam = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TeamName);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterTeam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        TLotteryLossTeam.setAdapter(adapterTeam);


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
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        timetext.setText(substring);
                        initRetrofit();
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
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        timetext2.setText(substring);
                        initRetrofit();
                    }
                });
                dialog2.show();
                break;
            case R.id.SearchUserName:
                initRetrofit();
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_LOTTERYLOSS) {
            if (object != null) {
                loss = (List<List<LotteryLoss>>) object;
                if (loss.size() > 0) {
                    List<LotteryLoss> lotteryLosses = loss.get(0);
                    Expend.setText(lotteryLosses.get(0).getBetting_amount() + "");
                    InCome.setText(lotteryLosses.get(0).getProfit_loss() + "");
                }
                if (loss.size() == 2) {
                    List<LotteryLoss> lotteryLosses = loss.get(1);
                    profitLossAdapter.getList(lotteryLosses);
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
