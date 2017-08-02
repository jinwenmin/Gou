package com.example.king.gou.ui;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.fragment.FindFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GameCenterActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener {

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
    @BindView(R.id.GameCentertitle)
    TextView GameCentertitle;
    @BindView(R.id.GameCenterListView)
    ListView GameCenterListView;
    private int TIME = 1000;  //每隔1s执行一次.

    Handler handler = new Handler();
    int i = 100;
    private int gid;
    private int position;
    private int section;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        ButterKnife.bind(this);
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
        initClick();
        initData();

        ViewPAdapter adapter = new ViewPAdapter(getSupportFragmentManager(), fragmentList, pageTitle);
        vpId.setAdapter(adapter);
        toolbar_tab.setupWithViewPager(vpId);
    }

    private void initClick() {
        Back.setOnClickListener(this);
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
}