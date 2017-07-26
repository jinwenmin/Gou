package com.example.king.gou.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.bean.GameType;
import com.example.king.gou.fragment.FindFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.orderFrmActivity.GameJiluActivity;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GameCenterActivity extends AutoLayoutActivity implements HttpEngine.DataListener {

    @BindView(R.id.vpId)
    ViewPager vpId;
    @BindView(R.id.toolbar_tab)
    TabLayout toolbar_tab;
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
    @BindView(R.id.addGame)
    ImageView addGame;
    @BindView(R.id.gameCenter_coordinatorlayout)
    CoordinatorLayout gameCenterCoordinatorlayout;
    private int TIME = 1000;  //每隔1s执行一次.

    Handler handler = new Handler();
    int i = 100;
    List<GameType> gameTypes1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        ButterKnife.bind(this);
        RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);
        initData();

        ViewPAdapter adapter = new ViewPAdapter(getSupportFragmentManager(), fragmentList, pageTitle);
        vpId.setAdapter(adapter);
        toolbar_tab.setupWithViewPager(vpId);
    }

    private void initData() {
        fragmentList.add(FindFragment.newInstance());
        fragmentList.add(FindFragment.newInstance());
        fragmentList.add(FindFragment.newInstance());
        pageTitle.add("换个");
        pageTitle.add("ziix");
        pageTitle.add("ziix");
        GameCenterProgressBar.setMax(i);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, TIME);

                    GameCenterProgressBar.setProgress(i--);
                    Log.i("print", "1-------------");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, TIME); // 在初始化方法里.

    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_GAME4) {
            if (object != null) {
                gameTypes1 = (List<GameType>) object;
                for (int i1 = 0; i1 < gameTypes1.size(); i1++) {
                    RetrofitService.getInstance().getSwitchGameList(this, gameTypes1.get(i1).getGid());
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
}