package com.example.king.gou.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.king.gou.R;
import com.example.king.gou.adapters.MyFrmPageAdapter;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.fragment.FindFragment;
import com.example.king.gou.fragment.myfragment.OrderFragment;
import com.example.king.gou.fragment.myfragment.ProxyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GameCenterActivity extends AppCompatActivity {

    @BindView(R.id.vpId)
    ViewPager vpId;
    @BindView(R.id.toolbar_tab)
    TabLayout toolbar_tab;
    List<FindFragment> fragmentList = new ArrayList<>();
    List<String> pageTitle = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        ButterKnife.bind(this);
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