package com.example.king.gou.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.king.gou.R;
import com.example.king.gou.adapters.HomeGameAdapter;
import com.example.king.gou.adapters.PageAdapter;
import com.example.king.gou.utils.FixGridLayout;
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
    @BindView(R.id.home_ralative)
    RelativeLayout homeRalative;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.header)
    RecyclerViewHeader header;

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

        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        header.attachTo(recycler, true);
        initImgs();
        HomeGameAdapter adapter = new HomeGameAdapter(getActivity());
        recycler.setAdapter(adapter);
        PageAdapter pageAdapter = new PageAdapter(imgs);
        homeViewpager.setAdapter(pageAdapter);
        initScrollView();
        return view;
    }

    private void initImgs() {

        ImageView img1 = new ImageView(getContext());
        Picasso.with(getActivity()).load("file:///android_asset/banner1.webp").into(img1);
        ImageView img2 = new ImageView(getContext());
        Picasso.with(getActivity()).load("file:///android_asset/banner2.webp").into(img2);
        ImageView img3 = new ImageView(getContext());
        Picasso.with(getActivity()).load("file:///android_asset/banner3.webp").into(img3);
        ImageView img4 = new ImageView(getContext());
        Picasso.with(getActivity()).load("file:///android_asset/banner4.webp").into(img4);
        img1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img4.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgs.add(img1);
        imgs.add(img2);
        imgs.add(img3);
        imgs.add(img4);

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
