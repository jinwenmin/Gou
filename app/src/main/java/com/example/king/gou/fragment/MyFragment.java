package com.example.king.gou.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.adapters.MyFrmPageAdapter;
import com.example.king.gou.fragment.myfragment.OrderFragment;
import com.example.king.gou.fragment.myfragment.ProxyFragment;
import com.example.king.gou.ui.frmMyActivity.ReChargeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.frmMyKeFu)
    ImageView frmMyKeFu;
    @BindView(R.id.frmMyMsg)
    ImageView frmMyMsg;
    @BindView(R.id.frmMySetting)
    ImageView frmMySetting;
    @BindView(R.id.frmMyNotice)
    ImageView frmMyNotice;
    @BindView(R.id.frmMyNickName)
    TextView frmMyNickName;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.frmMyUserName)
    TextView frmMyUserName;
    @BindView(R.id.linear2)
    LinearLayout linear2;
    @BindView(R.id.frmMyLottory)
    TextView frmMyLottory;
    @BindView(R.id.linear3)
    LinearLayout linear3;
    @BindView(R.id.frmMyCount)
    TextView frmMyCount;
    @BindView(R.id.linear4)
    LinearLayout linear4;
    @BindView(R.id.frmMyMoneyS)
    TextView frmMyMoneyS;
    @BindView(R.id.linear5)
    LinearLayout linear5;
    @BindView(R.id.frmMyTopInfo)
    RelativeLayout frmMyTopInfo;
    @BindView(R.id.frmMyTablayout)
    TabLayout frmMyTablayout;
    @BindView(R.id.frmMyViewpager)
    ViewPager frmMyViewpager;
    Unbinder unbinder;
    MyFrmPageAdapter myFrmPageAdapter;
    @BindView(R.id.ToRecharge)
    LinearLayout ToRecharge;
    @BindView(R.id.frmMyTop)
    RelativeLayout frmMyTop;
    @BindView(R.id.ToQukuan)
    LinearLayout ToQukuan;
    @BindView(R.id.ToZhuanZhang)
    LinearLayout ToZhuanZhang;

    public static MyFragment newInstance() {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        myFrmPageAdapter = new MyFrmPageAdapter(getChildFragmentManager());
        frmMyTablayout.setupWithViewPager(frmMyViewpager);
        frmMyViewpager.setAdapter(myFrmPageAdapter);

        initClick();
        initFrms();
        return view;
    }

    private void initClick() {
        ToRecharge.setOnClickListener(this);
        ToQukuan.setOnClickListener(this);
        ToZhuanZhang.setOnClickListener(this);
    }

    //加载订单报表 和管理 两个fragment
    private void initFrms() {
        List<BaseFragment> fragments = new ArrayList<>();
        List<String> titls = new ArrayList<>();
        fragments.add(OrderFragment.newInstance());
        fragments.add(ProxyFragment.newInstance());
        titls.add("订单报表");
        titls.add("代理管理");
        myFrmPageAdapter.addFrmList(fragments, titls);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ToRecharge:
                startActivity(new Intent(getActivity(), ReChargeActivity.class));
                break;
            case R.id.ToQukuan:
                break;
            case R.id.ToZhuanZhang:
                break;
        }


    }
}
