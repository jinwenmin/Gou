package com.example.king.gou.ui.orderFrmActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.king.gou.R;
import com.example.king.gou.adapters.MyFrmPageAdapter;
import com.example.king.gou.bean.GameType;
import com.example.king.gou.bean.TouZhu;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.gameAcVpFrms.AllFragment;
import com.example.king.gou.ui.gameAcVpFrms.KillOrderFragment;
import com.example.king.gou.ui.gameAcVpFrms.ManKillOrderFragment;
import com.example.king.gou.ui.gameAcVpFrms.NoBuyFragment;
import com.example.king.gou.ui.gameAcVpFrms.NoOpenFragment;
import com.example.king.gou.ui.gameAcVpFrms.NoWinFragment;
import com.example.king.gou.ui.gameAcVpFrms.OverDueFragment;
import com.example.king.gou.ui.gameAcVpFrms.PaiJiangFragment;
import com.example.king.gou.ui.gameAcVpFrms.TerraceKillFragment;
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


public class GameJiluActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

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
    @BindView(R.id.timetext3)
    EditText timetext3;
    @BindView(R.id.relateBank3)
    RelativeLayout relateBank3;
    @BindView(R.id.gamejl_tablayout)
    TabLayout gamejlTablayout;
    @BindView(R.id.gamejl_viewpager)
    ViewPager gamejlViewpager;
    MyFrmPageAdapter myFrmPageAdapter;
    @BindView(R.id.SearchQiHao)
    Button SearchQiHao;
    @BindView(R.id.gameType1)
    Spinner gameType1;
    @BindView(R.id.gameType2)
    Spinner gameType2;

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    String date1;
    String date2;
    List<GameType> gameTypes1 = new ArrayList<>();
    List<GameType> gameTypes2 = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    int Gid = 0;
    ArrayList<TouZhu> ts;
    AllFragment allFragment;
    private NoBuyFragment noBuyFragment;
    private NoOpenFragment noOpenFragment;
    private KillOrderFragment killOrderFragment;
    private ManKillOrderFragment manKillOrderFragment;
    private OverDueFragment overDueFragment;
    private NoWinFragment nowinFragment;
    private TerraceKillFragment terraceKillFragment;
    private PaiJiangFragment paiJiangFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_jilu);
        ButterKnife.bind(this);
        RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);
        myFrmPageAdapter = new MyFrmPageAdapter(getSupportFragmentManager());
        gamejlTablayout.setupWithViewPager(gamejlViewpager);
        gamejlViewpager.setAdapter(myFrmPageAdapter);
        relateTime1.setClickable(true);
        initFragment();
        initClick();
        initViewpager();
        initDateDialog();
        initSpinner();
    }

    private void initFragment() {
        allFragment = new AllFragment();
        noBuyFragment = new NoBuyFragment();
        noOpenFragment = new NoOpenFragment();
        killOrderFragment = new KillOrderFragment();
        manKillOrderFragment = new ManKillOrderFragment();
        overDueFragment = new OverDueFragment();
        nowinFragment = new NoWinFragment();
        terraceKillFragment = new TerraceKillFragment();
        paiJiangFragment = new PaiJiangFragment();
    }


    private void initSpinner() {
        gameType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getGame(GameJiluActivity.this, 7, gameTypes1.get(i).getGid(), 0, 0);
                Gid = gameTypes1.get(i).getGid();
                RetrofitService.getInstance().getSwitchGameList(GameJiluActivity.this, gameTypes1.get(3).getGid());
                // RetrofitService.getInstance().getBettingSync(GameJiluActivity.this,gameTypes1.get(i).getGid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        gameType2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String time1;
                String time2;
                if (date1 == null || date2 == null) {
                    time1 = timetext.getText().toString().trim() + " 00:00:01";
                    time2 = timetext2.getText().toString().trim() + " 23:59:59";
                } else {
                    time1 = date1;
                    time2 = date2;
                }

                RetrofitService.getInstance().getBettingRecord(GameJiluActivity.this, 1, 100, "serial_number", "desc", time1, time2, Gid, gameTypes2.get(i).getTid(), -1, "", "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void initClick() {
        relateTime1.setOnClickListener(this);
        relateTime2.setOnClickListener(this);
        relateBank3.setOnClickListener(this);
        gamejlBack.setOnClickListener(this);
        SearchQiHao.setOnClickListener(this);
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

    //实例化 Viewpager
    private void initViewpager() {
        List<BaseFragment> fragments = new ArrayList<>();
        List<String> tits = new ArrayList<>();
        fragments.add(allFragment);
        fragments.add(noBuyFragment);
        fragments.add(noOpenFragment);
        fragments.add(killOrderFragment);
        fragments.add(manKillOrderFragment);
        fragments.add(overDueFragment);
        fragments.add(nowinFragment);
        fragments.add(terraceKillFragment);
        fragments.add(paiJiangFragment);
        tits.add("全部");
        tits.add("未购买");
        tits.add("未开奖");
        tits.add("本人撤单");
        tits.add("管理员撤单");
        tits.add("已过期");
        tits.add("未中奖");
        tits.add("平台撤单");
        tits.add("已派奖");
        myFrmPageAdapter.addFrmList(fragments, tits);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
                        date1 = formatDate;
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        timetext.setText(substring);
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
                        date2 = formatDate;
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        timetext2.setText(substring);
                    }
                });
                dialog2.show();
                break;
            case R.id.gamejl_back:
                finish();
                break;
            case R.id.SearchQiHao:
                String time1;
                String time2;
                if (date1 == null || date2 == null) {
                    time1 = timetext.getText().toString().trim() + " 00:00:01";
                    time2 = timetext2.getText().toString().trim() + " 23:59:59";
                } else {
                    time1 = date1;
                    time2 = date2;
                }
                String searText = timetext3.getText().toString().trim();
                int gid = gameTypes1.get(gameType1.getSelectedItemPosition()).getGid();
                int tid = gameTypes2.get(gameType2.getSelectedItemPosition()).getTid();
                RetrofitService.getInstance().getBettingRecord(GameJiluActivity.this, 1, 100, "serial_number", "desc", time1, time2, gid, tid, -1, "", searText);
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
                gameType1.setAdapter(adapter);
                RetrofitService.getInstance().getGame(GameJiluActivity.this, 7, gameTypes1.get(0).getGid(), 0, 0);
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
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                gameType2.setAdapter(adapter);
            }
        }
        if (apiId == RetrofitService.API_ID_TOUZHUSEAR) {
            if (object != null) {
                ts = (ArrayList<TouZhu>) object;
                List<TouZhu> t1 = new ArrayList<>();
                List<TouZhu> t2 = new ArrayList<>();
                List<TouZhu> t3 = new ArrayList<>();
                List<TouZhu> t4 = new ArrayList<>();
                List<TouZhu> t5 = new ArrayList<>();
                List<TouZhu> t6 = new ArrayList<>();
                List<TouZhu> t7 = new ArrayList<>();
                List<TouZhu> t8 = new ArrayList<>();
                if (ts.size() > 0) {
                    Log.d("游戏数据", ts.get(0).getName());
                    for (int i = 0; i < ts.size(); i++) {
                        if (ts.get(i).getStatus() == 0) {
                            t1.add(ts.get(i));
                        }
                        if (ts.get(i).getStatus() == 1) {
                            t2.add(ts.get(i));
                        }
                        if (ts.get(i).getStatus() == 2) {
                            t3.add(ts.get(i));
                        }
                        if (ts.get(i).getStatus() == 3) {
                            t4.add(ts.get(i));
                        }
                        if (ts.get(i).getStatus() == 4) {
                            t5.add(ts.get(i));
                        }
                        if (ts.get(i).getStatus() == 5) {
                            t6.add(ts.get(i));
                        }
                        if (ts.get(i).getStatus() == 6) {
                            t7.add(ts.get(i));
                        }
                        if (ts.get(i).getStatus() == 7) {
                            t8.add(ts.get(i));
                        }

                    }
                }
                allFragment.getList(ts);
                noBuyFragment.getList(t1);
                noOpenFragment.getList(t2);
                killOrderFragment.getList(t3);
                manKillOrderFragment.getList(t4);
                overDueFragment.getList(t5);
                nowinFragment.getList(t6);
                terraceKillFragment.getList(t7);
                paiJiangFragment.getList(t8);
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
