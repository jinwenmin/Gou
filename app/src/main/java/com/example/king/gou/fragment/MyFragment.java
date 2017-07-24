package com.example.king.gou.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.MyApp;

import com.example.king.gou.R;
import com.example.king.gou.adapters.MyFrmPageAdapter;
import com.example.king.gou.bean.UserAmount;
import com.example.king.gou.bean.UserInfo;
import com.example.king.gou.bean.WithDraw;
import com.example.king.gou.fragment.myfragment.OrderFragment;
import com.example.king.gou.fragment.myfragment.ProxyFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.LotteryZhuiHaoActivity;
import com.example.king.gou.ui.SettingActivity;
import com.example.king.gou.ui.frmMyActivity.ChatUserActivity;
import com.example.king.gou.ui.frmMyActivity.MessageActivity;
import com.example.king.gou.ui.frmMyActivity.NoticeActivity;
import com.example.king.gou.ui.frmMyActivity.ReChargeActivity;
import com.example.king.gou.ui.frmMyActivity.WithDrawActivity;
import com.example.king.gou.ui.frmMyActivity.ZhuanZhangActivity;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.utils.L;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
    public MyFrmPageAdapter myFrmPageAdapter;
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
    private Broadcast broad;
    List<List<WithDraw>> ws = new ArrayList<>();
    List<WithDraw> datas = new ArrayList<>();
    List<WithDraw> cards = new ArrayList<>();

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

        broad = new Broadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.NickName");
        getActivity().registerReceiver(broad, intentFilter);
        Map<String, String> map = new HashMap<>();
        map.put("aaa", "111");
        map.put("ccc", "222");
        map.put("bbb", "333");
        map.put("zzz", "444");
        map.put("ppp", "555");
        //  RxUtils.getInstance().getReqkey(map);
        //RetrofitService.getInstance().getGame(this, 1, 0, 0, 0);
        // RetrofitService.getInstance().getGame(this, 2, 0, 0, 0);
        //RetrofitService.getInstance().getGame(this, 3, 0, 0, 0);
        //RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);
        //RetrofitService.getInstance().getGame(this, 5, 1, 0, 0);
        // RetrofitService.getInstance().getGame(this, 6, 2, 0, 0);
        RetrofitService.getInstance().getGame(this, 7, 2, 0, 0);
        SystemClock.sleep(3000);
        //    RetrofitService.getInstance().getGame(this, 8, 2, 62, 0);
        //  RetrofitService.getInstance().getGame(this, 5, 1, 0, 0);
        //RetrofitService.getInstance().getBettingRecord(this, 1, 10, "serial_number", "desc", "2017-01-01 22:01:01", "2017-01-06 22:01:01", 2, 166, -1, "", "");
        RetrofitService.getInstance();

        myFrmPageAdapter = new MyFrmPageAdapter(getChildFragmentManager());
        RetrofitService.getInstance().LoginUserAmount(this);
        RetrofitService.getInstance().GetUserInfo(this);
        RetrofitService.getInstance().getSafeQues(this);
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
        frmMyKeFu.setOnClickListener(this);
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
            case R.id.frmMyKeFu:
                startActivity(new Intent(getActivity(), ChatUserActivity.class));
                break;
            case R.id.ToRecharge:
                startActivity(new Intent(getActivity(), ReChargeActivity.class));
                break;
            case R.id.ToQukuan:
                // startActivity(new Intent(getActivity(), WithDrawActivity.class));
                RetrofitService.getInstance().getWithDrawDatas(this);
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
            MyApp.getInstance().setUserNickName(userInfo.getNname());
            frmMyUserName.setText(userInfo.getUname());
            frmMyMoneyS.setText(userInfo.getSamount() + "");
            frmMyCount.setText(userInfo.getAmount() + "");
            frmMyLottory.setText(userInfo.getRate() + "%");

        }
        if (apiId == RetrofitService.API_ID_WITHDRAW) {
            if (object != null) {
                ws = (List<List<WithDraw>>) object;
                datas = ws.get(0);
                cards = ws.get(1);
                if (datas.size()>0) {
                    Log.d("提现参数：判断是否冻结用户充提",datas.get(0).isFreeze()+"");
                    Log.d("提现参数：判断是否在可提现时间内",datas.get(0).isNotime()+"");
                    Log.d("提现参数：可提现开始时间",datas.get(0).getStart()+"");
                    Log.d("提现参数：可提现结束时间",datas.get(0).getEnd()+"");
                    Log.d("提现参数：剩余可提现次数",datas.get(0).getNums()+"");
                    Log.d("提现参数：可提现金额",datas.get(0).getAmounts()+"");
                }
                for (int i = 0; i < cards.size(); i++) {
                    Log.d("提现参数：收款银行卡id",cards.get(i).getAid()+"");
                    Log.d("提现参数：银行卡信息",cards.get(i).getCardNumber()+"");
                    Log.d("提现参数：持卡人",cards.get(i).getHolders_name()+"");
                }
            }
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }

    private class Broadcast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.NickName")) {

                frmMyNickName.setText(intent.getStringExtra("NickName"));

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broad);
    }
}
