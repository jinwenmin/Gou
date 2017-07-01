package com.example.king.gou.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.king.gou.R;
import com.example.king.gou.adapters.HomeGameAdapter;
import com.example.king.gou.adapters.PageAdapter;
import com.example.king.gou.bean.AdvertisementObject;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.AddGameActivity;
import com.example.king.gou.utils.BaseAutoScrollUpTextView;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.MainScrollUpAdvertisementView;
import com.example.king.gou.utils.MarqueeText;
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
public class HomeFragment extends BaseFragment implements View.OnClickListener, HttpEngine.DataListener {

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
    @BindView(R.id.HomeFragment_addGame)
    TextView HomeFragmentAddGame;
    @BindView(R.id.MainScrollAd)
    MainScrollUpAdvertisementView MainScrollAd;
    @BindView(R.id.test)
    MarqueeText test;
    @BindView(R.id.home_scroll1)
    HorizontalScrollView homeScroll1;
    @BindView(R.id.HomeFragment_Text)
    TextView HomeFragmentText;
    @BindView(R.id.MyGame)
    RelativeLayout MyGame;

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
        test.startScroll();
        HomeFragmentAddGame.setOnClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        header.attachTo(recycler, true);
        RetrofitService.getInstance().getHomeNotice(this);
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
       /* img1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img4.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));*/
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.HomeFragment_addGame:
                startActivity(new Intent(getActivity(), AddGameActivity.class));
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_HOMENOTICE) {
            if (object != null) {
                final String notice = (String) object;
                TextView textView = new TextView(getActivity());
                textView.setText(Html.fromHtml(notice));
                Log.d("这个是首页的公告==", Html.fromHtml(notice) + "");
                ArrayList<AdvertisementObject> notices = new ArrayList<AdvertisementObject>();
                AdvertisementObject advertisementObject = new AdvertisementObject();
                advertisementObject.info = Html.fromHtml(notice) + "";
                notices.add(advertisementObject);
                notices.add(advertisementObject);
                MainScrollAd.setData(notices);
                MainScrollAd.setTextSize(15);
                MainScrollAd.setTimer(3000);
                MainScrollAd.setOnItemClickListener(new BaseAutoScrollUpTextView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getActivity(), Html.fromHtml(notice) + "", Toast.LENGTH_SHORT).show();
                    }
                });
                MainScrollAd.start();
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
