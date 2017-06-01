package com.example.king.gou.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.example.king.gou.adapters.MyFrmPageAdapter;
import com.example.king.gou.fragment.gamefragments.BetFragment;
import com.example.king.gou.fragment.gamefragments.EleGameFragment;
import com.example.king.gou.fragment.gamefragments.LotteryFragment;
import com.example.king.gou.fragment.gamefragments.SportsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {


    @BindView(R.id.RechargeFirmTop)
    RelativeLayout RechargeFirmTop;
    @BindView(R.id.gameTablayout)
    TabLayout gameTablayout;
    @BindView(R.id.Viewpager)
    ViewPager Viewpager;
    Unbinder unbinder;
    MyFrmPageAdapter adapter;

    public static GameFragment newInstance() {

        Bundle args = new Bundle();

        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new MyFrmPageAdapter(getChildFragmentManager());
        initTieles();
        initViewpager();
        gameTablayout.setupWithViewPager(Viewpager);
        Viewpager.setAdapter(adapter);
        return view;
    }

    private void initViewpager() {

    }

    private void initTieles() {
        List<String> tits = new ArrayList<>();
        List<BaseFragment> fragments = new ArrayList<>();
        tits.add("彩票大厅");
        tits.add("真人娱乐");
        tits.add("电子游艺");
        tits.add("体育博弈");
        fragments.add(LotteryFragment.newInstance());
        fragments.add(BetFragment.newInstance());
        fragments.add(EleGameFragment.newInstance());
        fragments.add(SportsFragment.newInstance());
        adapter.addFrmList(fragments, tits);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
