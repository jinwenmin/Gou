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
import com.example.king.gou.bean.UserAmount;
import com.example.king.gou.bean.UserInfo;
import com.example.king.gou.fragment.myfragment.OrderFragment;
import com.example.king.gou.fragment.myfragment.ProxyFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.GameCenterActivity;
import com.example.king.gou.ui.LotteryZhuiHaoActivity;
import com.example.king.gou.ui.MainActivity;
import com.example.king.gou.ui.SettingActivity;
import com.example.king.gou.ui.frmMyActivity.MessageActivity;
import com.example.king.gou.ui.frmMyActivity.NoticeActivity;
import com.example.king.gou.ui.frmMyActivity.ReChargeActivity;
import com.example.king.gou.ui.frmMyActivity.ZhuanZhangActivity;
import com.example.king.gou.utils.HttpEngine;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener, HttpEngine.DataListener {


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
    List<UserAmount> userAmount;
    List<UserInfo> userInfos;

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
        RetrofitService.getInstance().getGame(this, 1, 0, 0, 0);
        RetrofitService.getInstance().getGame(this, 2, 0, 0, 0);
        RetrofitService.getInstance().getGame(this, 3, 0, 0, 0);
        RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);
        RetrofitService.getInstance().getGame(this, 5, 0, 0, 0);
        RetrofitService.getInstance().getGame(this, 6, 0, 0, 0);
        RetrofitService.getInstance().getGame(this, 7, 0, 0, 0);
        RetrofitService.getInstance().getGame(this, 8, 0, 0, 0);
        myFrmPageAdapter = new MyFrmPageAdapter(getChildFragmentManager());
        RetrofitService.getInstance().LoginUserAmount(this);
        RetrofitService.getInstance().GetUserInfo(this);
      //  RetrofitService.getInstance().GetPrizeDetails(this, 100, 1);
        // RetrofitService.getInstance().getGametype(this);
        initFrms();
        frmMyViewpager.setAdapter(myFrmPageAdapter);
        frmMyTablayout.setupWithViewPager(frmMyViewpager);

        initClick();
        return view;
    }

    private void initClick() {
        ToRecharge.setOnClickListener(this);
        ToQukuan.setOnClickListener(this);
        ToZhuanZhang.setOnClickListener(this);
        frmMySetting.setOnClickListener(this);
        frmMyMsg.setOnClickListener(this);
        frmMyNotice.setOnClickListener(this);
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
                startActivity(new Intent(getActivity(), LotteryZhuiHaoActivity.class));
                break;
            case R.id.ToZhuanZhang:
                startActivity(new Intent(getActivity(), ZhuanZhangActivity.class));

                break;
            case R.id.frmMySetting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.frmMyMsg:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.frmMyNotice:
                startActivity(new Intent(getActivity(), NoticeActivity.class));
                break;
        }


    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (RetrofitService.API_ID_USERAMOUNT == apiId) {
            userAmount = (List<UserAmount>) object;
            if (userAmount.get(0).isRc() == true) {
                frmMyCount.setText(userAmount.get(0).getOthers());
            }
        }
        if (RetrofitService.API_ID_USERINFO == apiId) {
            userInfos = (List<UserInfo>) object;
            UserInfo userInfo = userInfos.get(0);
            frmMyNickName.setText(userInfo.getNname());
            frmMyUserName.setText(userInfo.getUname());
            frmMyMoneyS.setText(userInfo.getSamount() + "");
            frmMyCount.setText(userInfo.getAmount() + "");

        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
