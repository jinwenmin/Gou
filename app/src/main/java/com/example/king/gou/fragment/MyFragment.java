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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;

import com.example.king.gou.R;
import com.example.king.gou.adapters.MyFrmPageAdapter;
import com.example.king.gou.bean.RestultInfo;
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
import com.example.king.gou.ui.settingfragment.BankCardManActivity;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.utils.L;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener, HttpEngine.DataListener, OnItemClickListener {


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
    private AlertView alertViewSafe;
    // 一个自定义的布局，作为显示的内容
    View contentViewSafe;

    private AlertView alertView;
    ; // 一个自定义的布局，作为显示的内容
    View contentView;
    String show = "0";

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
        alertView = new AlertView(null, null, "取消", new String[]{"确认"}, null, getContext(), AlertView.Style.Alert, this);
        contentView = LayoutInflater.from(getContext()).inflate(
                R.layout.item_homenotice, null);
        alertView.addExtView(contentView);

        alertViewSafe = new AlertView(null, null, "取消", new String[]{"确认"}, null, getContext(), AlertView.Style.Alert, this);
        contentViewSafe = LayoutInflater.from(getContext()).inflate(
                R.layout.item_safepwd, null);
        alertViewSafe.addExtView(contentViewSafe);


        broad = new Broadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.NickName");
        getActivity().registerReceiver(broad, intentFilter);

        myFrmPageAdapter = new MyFrmPageAdapter(getChildFragmentManager());
        RetrofitService.getInstance().LoginUserAmount(this);
        RetrofitService.getInstance().GetUserInfo(this);
        RetrofitService.getInstance().getSafeQues(this);

        //   initFrms();
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
                // startActivity(new Intent(getActivity(), ReChargeActivity.class));
                break;
            case R.id.ToQukuan:
                //
                alertViewSafe.show();
                show = "2";
                //RetrofitService.getInstance().getWithDrawDatas(this);
                break;
            case R.id.ToZhuanZhang:
                //   startActivity(new Intent(getActivity(), ZhuanZhangActivity.class));

                break;
            case R.id.frmMySetting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.frmMyMsg:
                //startActivity(new Intent(getActivity(), MessageActivity.class));
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
            List<BaseFragment> fragments = new ArrayList<>();
            List<String> titls = new ArrayList<>();
            fragments.add(OrderFragment.newInstance());
            titls.add("订单报表");
            if (userInfo.isShares()) {
                fragments.add(ProxyFragment.newInstance());
                titls.add("代理管理");
            }
            myFrmPageAdapter.addFrmList(fragments, titls);

        }
        if (apiId == RetrofitService.API_ID_WITHDRAW) {
            if (object != null) {
                ws = (List<List<WithDraw>>) object;
                datas = ws.get(0);
                cards = ws.get(1);
                BigDecimal amounts = null;
                if (datas.size() > 0) {
                    boolean freeze = datas.get(0).isFreeze();
                    boolean notime = datas.get(0).isNotime();
                    String start = datas.get(0).getStart();
                    String end = datas.get(0).getEnd();
                    int nums = datas.get(0).getNums();
                    amounts = datas.get(0).getAmounts();
                    Log.d("提现参数：判断是否冻结用户充提", freeze + "");
                    Log.d("提现参数：判断是否在可提现时间内", notime + "");
                    Log.d("提现参数：可提现开始时间", start + "");
                    Log.d("提现参数：可提现结束时间", end + "");
                    Log.d("提现参数：剩余可提现次数", nums + "");
                    Log.d("提现参数：可提现金额", amounts + "");
                    if (!freeze) {
                        Toasty.error(getContext(), "用户被冻结，无法提现", 2000).show();
                        return;
                    }
                    if (notime) {
                        Toasty.error(getContext(), "不在提现时间段内，无法提现", 2000).show();
                        Toasty.info(getContext(), "提现时间为" + start + "-" + end, 2000).show();
                        return;
                    }
                    if (nums <= 0) {
                        Toasty.error(getContext(), "提现次数用完，无法提现", 2000).show();
                        return;
                    }
                    if (Double.parseDouble(amounts + "") <= 0) {
                        Toasty.error(getContext(), "可提取金额不大于零，无法提现", 2000).show();
                        return;
                    }
                }
                ArrayList<WithDraw> banks = new ArrayList<>();
                for (int i = 0; i < cards.size(); i++) {

                    Log.d("提现参数：收款银行卡id", cards.get(i).getAid() + "");
                    Log.d("提现参数：银行卡信息", cards.get(i).getCardNumber() + "");
                    Log.d("提现参数：持卡人", cards.get(i).getHolders_name() + "");
                    WithDraw withDraw = new WithDraw();
                    withDraw.setAid(cards.get(i).getAid());
                    withDraw.setCardNumber(cards.get(i).getCardNumber());
                    withDraw.setHolders_name(cards.get(i).getHolders_name());
                    banks.add(withDraw);
                }
                if (banks.size() == 0) {
                    TextView homeNotice = (TextView) contentView.findViewById(R.id.homeNoticeText);
                    homeNotice.setText(Html.fromHtml("用户还未绑定银行卡,是否前往绑定银行卡"));
                    alertView.show();
                    show = "1";
                } else {
                    Intent intent = new Intent(getActivity(), WithDrawActivity.class);
                    intent.putExtra("amounts", amounts + "");
                    intent.putExtra("banks", (Serializable) banks);
                    startActivity(intent);
                }
            }
        }
        if (apiId == RetrofitService.API_ID_SAFEPWD) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc() == true) {
                    RetrofitService.getInstance().getWithDrawDatas(this);
                }
                if (restultInfo.isRc() == false) {
                    Toasty.error(getActivity(), restultInfo.getMsg(), 2000).show();
                    return;
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

    @Override
    public void onResume() {
        super.onResume();
        RetrofitService.getInstance().LoginUserAmount(this);
        RetrofitService.getInstance().GetUserInfo(this);
        RetrofitService.getInstance().getSafeQues(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            RetrofitService.getInstance().LoginUserAmount(this);
            RetrofitService.getInstance().GetUserInfo(this);
            RetrofitService.getInstance().getSafeQues(this);
        }
    }

    @Override
    public void onItemClick(Object o, int position) {
        if ("1".equals(show)) {
            if (position != AlertView.CANCELPOSITION) {
                startActivity(new Intent(getActivity(), BankCardManActivity.class));

            }
        }
        if ("2".equals(show)) {
            EditText safepwd = (EditText) contentViewSafe.findViewById(R.id.AnswerQues);
            if (position != AlertView.CANCELPOSITION) {
                String pwd = safepwd.getText().toString().trim();
                if ("".equals(pwd)) {
                    Toasty.error(getActivity(), "安全密码不可为空", 2000).show();
                    return;
                }
                String hmacsha256 = RxUtils.getInstance().HMACSHA256(pwd, MyApp.getInstance().getUserName());
                RetrofitService.getInstance().getCheckSafePwd(this, hmacsha256);
            }
            safepwd.setText("");

        }

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
