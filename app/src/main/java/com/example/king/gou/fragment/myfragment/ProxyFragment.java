package com.example.king.gou.fragment.myfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.ui.proxyfragment.ActivityTeamActivity;
import com.example.king.gou.ui.proxyfragment.GameReWardActivity;
import com.example.king.gou.ui.proxyfragment.ProxyHomeActivity;
import com.example.king.gou.ui.proxyfragment.ShareDataActivity;
import com.example.king.gou.ui.proxyfragment.TeamActivityActivity;
import com.example.king.gou.ui.proxyfragment.TeamBaoBiaoActivity;
import com.example.king.gou.ui.proxyfragment.TeamCunQuActivity;
import com.example.king.gou.ui.proxyfragment.TeamLotteryLossActivity;
import com.example.king.gou.ui.proxyfragment.TeamZBJLActivity;
import com.example.king.gou.ui.proxyfragment.VIPManActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProxyFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.proxy_home)
    LinearLayout proxyHome;
    @BindView(R.id.proxy_userCenter)
    LinearLayout proxyUserCenter;
    @BindView(R.id.proxy_TeamBaoBiao)
    LinearLayout proxyTeamBaoBiao;
    @BindView(R.id.proxy_VipMan)
    LinearLayout proxyVipMan;
    @BindView(R.id.proxy_TeamZhangBian)
    LinearLayout proxyTeamZhangBian;
    @BindView(R.id.proxy_GameJl)
    LinearLayout proxyGameJl;
    @BindView(R.id.GameMoney)
    LinearLayout GameMoney;
    @BindView(R.id.proxy_TeamCunQu)
    LinearLayout proxyTeamCunQu;
    @BindView(R.id.proxy_TeamBjl)
    LinearLayout proxyTeamBjl;
    @BindView(R.id.proxy_TeamZhuiHao)
    TextView proxyTeamZhuiHao;
    Unbinder unbinder;

    public static ProxyFragment newInstance() {

        Bundle args = new Bundle();

        ProxyFragment fragment = new ProxyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_proxy, container, false);
        unbinder = ButterKnife.bind(this, view);
       /* Drawable dlsy = getResources().getDrawable(R.drawable.ic_dailishouye);
        dlsy.setBounds(0, 0, 200, 200);
        Drawable khzx = getResources().getDrawable(R.drawable.ic_kaihuzhongxin);
        khzx.setBounds(0, 0, 200, 200);
        Drawable tdtz = getResources().getDrawable(R.drawable.ic_tuanduibaobiao);
        tdtz.setBounds(0, 0, 200, 200);
        Drawable hygl = getResources().getDrawable(R.drawable.ic_huiyuanguanli);
        hygl.setBounds(0, 0, 200, 200);
        Drawable tdzb = getResources().getDrawable(R.drawable.ic_tuanduizbjl);
        tdzb.setBounds(0, 0, 200, 200);
        Drawable tdyk = getResources().getDrawable(R.drawable.ic_caipiaobaobiao);
        tdyk.setBounds(0, 0, 200, 200);
        Drawable hdlb = getResources().getDrawable(R.drawable.ic_tuanduizhjl);
        hdlb.setBounds(0, 0, 200, 200);
        Drawable tdcq = getResources().getDrawable(R.drawable.ic_tuanduicunqukuan);
        tdcq.setBounds(0, 0, 200, 200);
        Drawable tdhdbb = getResources().getDrawable(R.drawable.ic_tuanduibaijialebaobiao);
        tdhdbb.setBounds(0, 0, 200, 200);
        Drawable yxjj = getResources().getDrawable(R.drawable.ic_zhuanzhangjilu);
        yxjj.setBounds(0, 0, 200, 200);


        proxyHome.setCompoundDrawables(null, dlsy, null, null);
        proxyUserCenter.setCompoundDrawables(null, khzx, null, null);
        proxyTeamBaoBiao.setCompoundDrawables(null, tdtz, null, null);
        proxyVipMan.setCompoundDrawables(null, hygl, null, null);
        proxyTeamZhangBian.setCompoundDrawables(null, tdzb, null, null);
        proxyGameJl.setCompoundDrawables(null, tdyk, null, null);
        proxyTeamZhuiHao.setCompoundDrawables(null, hdlb, null, null);
        proxyTeamCunQu.setCompoundDrawables(null, tdcq, null, null);
        proxyTeamBjl.setCompoundDrawables(null, tdhdbb, null, null);
        GameMoney.setCompoundDrawables(null, yxjj, null, null);
*/

        proxyHome.setOnClickListener(this);
        proxyUserCenter.setOnClickListener(this);
        proxyTeamBaoBiao.setOnClickListener(this);
        proxyVipMan.setOnClickListener(this);
        proxyTeamZhangBian.setOnClickListener(this);
        proxyGameJl.setOnClickListener(this);
        proxyTeamZhuiHao.setOnClickListener(this);
        proxyTeamCunQu.setOnClickListener(this);
        proxyTeamBjl.setOnClickListener(this);
        GameMoney.setOnClickListener(this);

        return view;
    }


    public void StartA(Class cls) {
        startActivity(new Intent(getActivity(), cls));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proxy_home:
                StartA(ProxyHomeActivity.class);
                break;
            case R.id.proxy_userCenter:
                // StartA(UserCenterActivity.class);
                StartA(ShareDataActivity.class);
                break;
            case R.id.proxy_TeamBaoBiao:
                StartA(TeamBaoBiaoActivity.class);
                break;
            case R.id.proxy_VipMan:
                StartA(VIPManActivity.class);
                break;
            case R.id.proxy_TeamZhangBian:
                StartA(TeamZBJLActivity.class);
                break;
            case R.id.proxy_GameJl:
                // StartA(TeamGamejlActivity.class);
                StartA(TeamLotteryLossActivity.class);
                break;
            case R.id.proxy_TeamZhuiHao:
                //StartA(TeamZhuihaojlActivity.class);
                StartA(TeamActivityActivity.class);
                break;
            case R.id.proxy_TeamCunQu:
                StartA(TeamCunQuActivity.class);
                break;
            case R.id.proxy_TeamBjl:
                StartA(ActivityTeamActivity.class);
                break;
            case R.id.GameMoney:
                StartA(GameReWardActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
