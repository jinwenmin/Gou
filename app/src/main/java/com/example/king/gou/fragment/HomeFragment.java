package com.example.king.gou.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.king.gou.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.Erweima)
    ImageView Erweima;
    @BindView(R.id.home_top)
    RelativeLayout homeTop;
    @BindView(R.id.home_viewpager)
    ViewPager homeViewpager;
    @BindView(R.id.ScrollImg1)
    ImageView ScrollImg1;
    Unbinder unbinder;
    @BindView(R.id.ScrollImg2)
    ImageView ScrollImg2;
    @BindView(R.id.ScrollImg3)
    ImageView ScrollImg3;
    @BindView(R.id.ScrollImg4)
    ImageView ScrollImg4;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initScrollView();
        return view;
    }

    private void initScrollView() {
        Picasso.with(getActivity()).load("file:///android_asset/ic_sports.webp").into(ScrollImg1);
        Picasso.with(getActivity()).load("file:///android_asset/ic_live_game.webp").into(ScrollImg2);
        Picasso.with(getActivity()).load("file:///android_asset/ic_electric.webp").into(ScrollImg3);
        Picasso.with(getActivity()).load("file:///android_asset/ic_lottery.webp").into(ScrollImg4);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
