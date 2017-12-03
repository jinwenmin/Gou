package com.example.king.gou.ui.orderFrmActivity;

import android.content.Intent;
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
import com.example.king.gou.adapters.MyFrmPageAdapter;
import com.example.king.gou.adapters.TouZhuAdapter;
import com.example.king.gou.bean.GameType;
import com.example.king.gou.bean.TouZhu;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.gameAcVpFrms.AllFragment;
import com.example.king.gou.ui.gameAcVpFrms.BettingDetailActivity;
import com.example.king.gou.ui.gameAcVpFrms.KillOrderFragment;
import com.example.king.gou.ui.gameAcVpFrms.ManKillOrderFragment;
import com.example.king.gou.ui.gameAcVpFrms.NoBuyFragment;
import com.example.king.gou.ui.gameAcVpFrms.NoOpenFragment;
import com.example.king.gou.ui.gameAcVpFrms.NoWinFragment;
import com.example.king.gou.ui.gameAcVpFrms.OverDueFragment;
import com.example.king.gou.ui.gameAcVpFrms.PaiJiangFragment;
import com.example.king.gou.ui.gameAcVpFrms.TerraceKillFragment;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.PinnedHeaderListView;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    MyFrmPageAdapter myFrmPageAdapter;
    @BindView(R.id.SearchQiHao)
    Button SearchQiHao;
    @BindView(R.id.gameType1)
    Spinner gameType1;
    @BindView(R.id.gameType2)
    Spinner gameType2;
    @BindView(R.id.GameJiluSpinner)
    Spinner GameJiluSpinner;
    List<List<TouZhu>> tss;
    @BindView(R.id.GameJiLuListView)
    ListView GameJiLuListView;
    @BindView(R.id.relateBank3)
    LinearLayout relateBank3;
    @BindView(R.id.RebuySpinner)
    Spinner RebuySpinner;

    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapterRebuy;
    String date1;
    String date2;
    List<GameType> gameTypes1 = new ArrayList<>();
    List<GameType> gameTypes2 = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    int Gid = 0;
    List<TouZhu> ts;

    List<String> tits = new ArrayList<>();
    List<Integer> titsname = new ArrayList<>();
    public TouZhuAdapter touZhuAdapter;
    List<String> rebuys = new ArrayList<>();
    List<String> rebuyName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_jilu);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        touZhuAdapter = new TouZhuAdapter(this);
        GameJiLuListView.setAdapter(touZhuAdapter);
        RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);
        relateTime1.setClickable(true);
        initClick();
        initDateDialog();
        initSpinner();

    }

    private void initSpinnerSelect() {
        gameType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getGame(GameJiluActivity.this, 7, gameTypes1.get(i).getGid(), 0, 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gameType2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        RebuySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRetrofit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        GameJiluSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<TouZhu> touZhus = tss.get(position);
                touZhuAdapter.addListView(touZhus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initRetrofit() {
        String time1 = timetext.getText().toString().trim();
        String time2 = timetext2.getText().toString().trim();
        String searString = timetext3.getText().toString().trim();
        int status = titsname.get(GameJiluSpinner.getSelectedItemPosition());
        String rebuy = rebuyName.get(RebuySpinner.getSelectedItemPosition());
        int gid = gameTypes1.get(gameType1.getSelectedItemPosition()).getGid();
        int rid = gameTypes2.get(gameType2.getSelectedItemPosition()).getGrid();
        RetrofitService.getInstance().getBettingRecord(GameJiluActivity.this, 1, 100, "serial_number", "desc", time1, time2, gid, rid, status, "", searString);
        //RetrofitService.getInstance().getBettingRecord(GameJiluActivity.this, 1, 100, "serial_number", "desc", time1, time2, gid, tid, -1, "", searText);
    }


    private void initSpinner() {
        rebuys.add("");
        rebuys.add("0");
        rebuys.add("1");
        rebuyName.add("全部");
        rebuyName.add("不是追号");
        rebuyName.add("是追号");
        adapterRebuy = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rebuyName);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterRebuy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        RebuySpinner.setAdapter(adapterRebuy);
        titsname.add(-1);
        titsname.add(0);
        titsname.add(1);
        titsname.add(2);
        titsname.add(3);
        titsname.add(4);
        titsname.add(5);
        titsname.add(6);
        titsname.add(7);
        tits.add("全部");
        tits.add("未购买");
        tits.add("未开奖");
        tits.add("本人撤单");
        tits.add("管理员撤单");
        tits.add("已过期");
        tits.add("未中奖");
        tits.add("平台撤单");
        tits.add("已派奖");
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tits);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        GameJiluSpinner.setAdapter(adapter1);
    }


    private void initClick() {
        relateTime1.setOnClickListener(this);
        relateTime2.setOnClickListener(this);
        gamejlBack.setOnClickListener(this);
        SearchQiHao.setOnClickListener(this);
        GameJiLuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GameJiluActivity.this, BettingDetailActivity.class);
                TouZhu t = (TouZhu) touZhuAdapter.getItem(i);
                intent.putExtra("bid", t.getBid());
                startActivity(intent);
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
                        String searText = timetext3.getText().toString().trim();
                        int gid = gameTypes1.get(gameType1.getSelectedItemPosition()).getGid();
                        int tid = gameTypes2.get(gameType2.getSelectedItemPosition()).getTid();
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
                        date2 = formatDate;
                        String substring = formatDate.substring(0, 10);
                        Log.d("Date===", substring);
                        timetext2.setText(substring);
                        String searText = timetext3.getText().toString().trim();
                        int gid = gameTypes1.get(gameType1.getSelectedItemPosition()).getGid();
                        int tid = gameTypes2.get(gameType2.getSelectedItemPosition()).getTid();
                        initRetrofit();

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
                gameType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        RetrofitService.getInstance().getGame(GameJiluActivity.this, 7, gameTypes1.get(i).getGid(), 0, 0);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

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
                initSpinnerSelect();
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
                Log.d("游戏数据Size", ts.size() + "");
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
                tss = new ArrayList<>();
                tss.add(ts);
                for (int i = 0; i < ts.size(); i++) {
                    Log.d("GameTs", ts.get(i).toString() + "");
                }
                Log.d("SpinerTouZhuTsSize", ts.size() + "");
                tss.add(t1);
                tss.add(t2);
                tss.add(t3);
                tss.add(t4);
                tss.add(t5);
                tss.add(t6);
                tss.add(t7);
                tss.add(t8);
                List<TouZhu> touZhus = tss.get(0);

                Log.d("SpinerTouZhuSize", tss.get(0).size() + "");
                for (int i = 0; i < touZhus.size(); i++) {
                    Log.d("SpinerTouZhu", touZhus.get(i).toString());
                }
                touZhuAdapter.addListView(touZhus);


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
