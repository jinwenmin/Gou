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
import com.example.king.gou.adapters.PageAdapter;
import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;
import java.util.List;

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
    RollPagerView homeViewpager;
    @BindView(R.id.ScrollImg1)
    ImageView ScrollImg1;
    Unbinder unbinder;
    @BindView(R.id.ScrollImg2)
    ImageView ScrollImg2;
    @BindView(R.id.ScrollImg3)
    ImageView ScrollImg3;
    @BindView(R.id.ScrollImg4)
    ImageView ScrollImg4;
    List imgs = new ArrayList();

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

        ImageView img1 = new ImageView(getContext());

        img1.setImageResource(R.mipmap.ic_launcher);
        ImageView img2 = new ImageView(getContext());
        img2.setImageResource(R.mipmap.ic_baijialebaobiao);
        ImageView img3 = new ImageView(getContext());
        img3.setImageResource(R.mipmap.ic_caipiaobaobiao);
        ImageView img4 = new ImageView(getContext());
        img4.setImageResource(R.mipmap.ic_cunqukuanjilu);
        ImageView img5 = new ImageView(getContext());
        img5.setImageResource(R.mipmap.ic_fandianjilu);
        img1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img4.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img5.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgs.add(img1);
        imgs.add(img2);
        imgs.add(img3);
        imgs.add(img4);
        imgs.add(img5);
        PageAdapter pageAdapter = new PageAdapter(imgs);
        homeViewpager.setAdapter(pageAdapter);
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
