package com.example.king.gou.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ListView;

import com.example.king.gou.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class MyFrmPageAdapter extends FragmentPagerAdapter {

    public List<BaseFragment> fragmentList;
    private List<String> titles;

    public MyFrmPageAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
        titles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public void addFrmList(List<BaseFragment> fts, List<String> tits) {
        fragmentList = fts;
        titles = tits;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
