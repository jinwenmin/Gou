package com.example.king.gou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.DrawHistoryAdapter;
import com.example.king.gou.bean.BettingSync;
import com.example.king.gou.bean.RecordList;
import com.example.king.gou.bean.SwitchG;
import com.example.king.gou.fragment.FindFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class GameCenterActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener {

    /*    @BindView(R.id.vpId)
        ViewPager vpId;
        @BindView(R.id.toolbar_tab)
        TabLayout toolbar_tab;*/
    List<FindFragment> fragmentList = new ArrayList<>();
    List<String> pageTitle = new ArrayList<>();
    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.GameCenterTop)
    RelativeLayout GameCenterTop;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.textPeriod)
    TextView textPeriod;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.textTime)
    TextView textTime;
    @BindView(R.id.MoneyCount)
    TextView MoneyCount;
    @BindView(R.id.GameCenterProgressBar)
    ProgressBar GameCenterProgressBar;
    /*  @BindView(R.id.addGame)
      ImageView addGame;*/
    @BindView(R.id.GameCentertitle)
    TextView GameCentertitle;
    @BindView(R.id.GameCenterListView)
    ListView GameCenterListView;
    @BindView(R.id.SpinnerType1)
    Spinner SpinnerType1;
    @BindView(R.id.SpinnerType2)
    Spinner SpinnerType2;
    @BindView(R.id.gameCenter_coordinatorlayout)
    CoordinatorLayout gameCenterCoordinatorlayout;
    @BindView(R.id.gameCenterButtom)
    LinearLayout gameCenterButtom;
    @BindView(R.id.GameCenterLinear)
    LinearLayout GameCenterLinear;
    @BindView(R.id.GameCenterScroll)
    ScrollView GameCenterScroll;
    private int TIME = 1000;  //每隔1s执行一次.

    Handler handler = new Handler();

    private int gid;
    private int position;
    private int section;
    private String name;
    List<RecordList> rcs = new ArrayList<RecordList>();
    BettingSync bs = new BettingSync();
    DrawHistoryAdapter drawHistoryAdapter;
    Runnable runnable;

    private Timer timer;
    List<SwitchG> sg = new ArrayList<>();

    private ArrayAdapter<String> adapterType1;
    private ArrayAdapter<String> adapterType2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        Intent intent = getIntent();
        gid = intent.getIntExtra("gid", 0);
        position = intent.getIntExtra("position", 0);
        section = intent.getIntExtra("section", 0);
        name = intent.getStringExtra("name");
        GameCentertitle.setText(name);
        Log.d("GameCenterGid==", gid + "");
        Log.d("GameCenterName==", name + "");
        Log.d("GameCenterPosition==", position + "");
        Log.d("GameCenterSection==", section + "");

        RetrofitService.getInstance().getSwitchGameList(this, gid);
        RetrofitService.getInstance().getBettingSync(this, gid);
        RetrofitService.getInstance().getBettingDrawHistory(this, gid);
        View view = LayoutInflater.from(this).inflate(R.layout.item_g1_2_star_before, null, false);
        GameCenterLinear.addView(view);
        initClick();
        initData();
        initSpinner1();
        ViewPAdapter adapter = new ViewPAdapter(getSupportFragmentManager(), fragmentList, pageTitle);
      /*  vpId.setAdapter(adapter);
        toolbar_tab.setupWithViewPager(vpId);*/
        drawHistoryAdapter = new DrawHistoryAdapter(this);
        GameCenterListView.setAdapter(drawHistoryAdapter);

    }

    private void initSpinner1() {
        SpinnerType1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initSpinnerSelect();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerSelect() {
        List<String> name = new ArrayList<>();
        List<SwitchG.SwitchGa> switchGas = sg.get(SpinnerType1.getSelectedItemPosition()).getSwitchGas();
        for (int i1 = 0; i1 < switchGas.size(); i1++) {

            List<SwitchG.SwitchGa.SwitchGam> switchGams1 = switchGas.get(i1).getSwitchGams();
            Log.d("GameCenterSwSize", switchGams1.size() + "");
            if (switchGams1.size() == 0) {
                Log.d("GameCenterSw", switchGams1.toString());
            }
            if (switchGams1.size() != 0) {
                List<SwitchG.SwitchGa.SwitchGam> switchGams = switchGas.get(i1).getSwitchGams();
                for (int i2 = 0; i2 < switchGams.size(); i2++) {
                    name.add((String) switchGams.get(i2).getName3());
                }
            } else if (switchGams1.size() == 0) {
                name.add((String) switchGas.get(i1).getName2());
            }
        }

        adapterType2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterType2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerType2.setAdapter(adapterType2);
    }

    private void initClick() {
        Back.setOnClickListener(this);
    }

    private void initData() {
        fragmentList.add(FindFragment.newInstance());
        fragmentList.add(FindFragment.newInstance());
        fragmentList.add(FindFragment.newInstance());
        pageTitle.add("QQ");
        pageTitle.add("WW");
        pageTitle.add("EE");


    }

    private void initTimer() {
        timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                RetrofitService.getInstance().getBettingWinningNum(GameCenterActivity.this, gid, bs.getPeriod());
                // RetrofitService.getInstance().getTokenSignin(MainActivity.this);

            }
        };

        timer.schedule(timerTask, 0, 1000);

    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_HISTORYGAME) {
            if (object != null) {
                rcs = (List<RecordList>) object;
                drawHistoryAdapter.addList(rcs);

            }
        }
        if (apiId == RetrofitService.API_ID_BETTINGSYNC) {
            if (object != null) {
                bs = (BettingSync) object;
                final int[] anInt = {RxUtils.getInstance().getInt(bs.getCondownTime())};
                textPeriod.setText(bs.getPeriod());
                textTime.setText(RxUtils.getInstance().getSecToTime(RxUtils.getInstance().getInt(bs.getCondownTime())));
                GameCenterProgressBar.setMax(anInt[0]);
                final int[] anInt1 = {RxUtils.getInstance().getInt(bs.getCondownTime())};
                anInt1[0] = anInt1[0] - 1;
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler.postDelayed(this, TIME);
                            GameCenterProgressBar.setProgress(anInt[0]--);
                            int i = anInt1[0]--;
                            String secToTime = RxUtils.getInstance().getSecToTime(i);
                            textTime.setText(secToTime);
                            Log.i("GameCentprint", anInt[0] + "");
                            Log.i("GameCentTime", secToTime + "");
                            if (anInt[0] < 0) {
                                handler.removeCallbacks(runnable);
                                Toast toast = Toasty.warning(GameCenterActivity.this, "第" + bs.getPeriod() + "期开奖中", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                initTimer();
                                //  RetrofitService.getInstance().getBettingDrawHistory(GameCenterActivity.this, gid);
                                //  RetrofitService.getInstance().getBettingSync(GameCenterActivity.this, gid);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                handler.postDelayed(runnable, TIME); // 在初始化方法里.
            }
        }
        if (apiId == RetrofitService.API_ID_BETTINGWINNUM) {
            if (timer != null) {
                timer.cancel();
            }
            RetrofitService.getInstance().getBettingSync(this, gid);
            RetrofitService.getInstance().getBettingDrawHistory(this, gid);

           /* if (object!=null) {
                BettingSync bettingSync= (BettingSync) object;
                bettingSync
            }*/
        }
        if (apiId == RetrofitService.API_ID_SWITCHGAME) {
            if (object != null) {
                sg = (List<SwitchG>) object;
                List<String> sgName = new ArrayList<>();
                for (int i = 0; i < sg.size(); i++) {
                    sgName.add((String) sg.get(i).getName1());
                }
                adapterType1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sgName);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapterType1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                SpinnerType1.setAdapter(adapterType1);
                initSpinner1();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:

                finish();
                break;
        }
    }

    class ViewPAdapter extends FragmentPagerAdapter {
        List<FindFragment> fragmentList = new ArrayList<>();
        List<String> arg1 = new ArrayList<>();

        public ViewPAdapter(FragmentManager fm, List<FindFragment> frameLayoutList, List<String> aa) {
            super(fm);
            this.fragmentList = frameLayoutList;
            arg1 = aa;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return arg1.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        if (timer != null) {
            timer.cancel();
        }
    }
}