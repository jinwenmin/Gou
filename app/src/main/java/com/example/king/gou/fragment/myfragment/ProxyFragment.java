package com.example.king.gou.fragment.myfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.ui.proxyfragment.ProxyHomeActivity;
import com.example.king.gou.ui.proxyfragment.TeamBaoBiaoActivity;
import com.example.king.gou.ui.proxyfragment.ActivityTeamActivity;
import com.example.king.gou.ui.proxyfragment.TeamCunQuActivity;
import com.example.king.gou.ui.proxyfragment.TeamGamejlActivity;
import com.example.king.gou.ui.proxyfragment.TeamZBJLActivity;
import com.example.king.gou.ui.proxyfragment.TeamZhuihaojlActivity;
import com.example.king.gou.ui.proxyfragment.UserCenterActivity;
import com.example.king.gou.ui.proxyfragment.VIPManActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProxyFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.proxy_home)
    TextView proxyHome;
    Unbinder unbinder;
    @BindView(R.id.proxy_userCenter)
    TextView proxyUserCenter;
    @BindView(R.id.proxy_TeamBaoBiao)
    TextView proxyTeamBaoBiao;
    @BindView(R.id.proxy_VipMan)
    TextView proxyVipMan;
    @BindView(R.id.proxy_TeamZhangBian)
    TextView proxyTeamZhangBian;
    @BindView(R.id.proxy_GameJl)
    TextView proxyGameJl;
    @BindView(R.id.proxy_TeamZhuiHao)
    TextView proxyTeamZhuiHao;
    @BindView(R.id.proxy_TeamCunQu)
    TextView proxyTeamCunQu;
    @BindView(R.id.proxy_TeamBjl)
    TextView proxyTeamBjl;

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
        proxyHome.setOnClickListener(this);
        proxyUserCenter.setOnClickListener(this);
        proxyTeamBaoBiao.setOnClickListener(this);
        proxyVipMan.setOnClickListener(this);
        proxyTeamZhangBian.setOnClickListener(this);
        proxyGameJl.setOnClickListener(this);
        proxyTeamZhuiHao.setOnClickListener(this);
        proxyTeamCunQu.setOnClickListener(this);
        proxyTeamBjl.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                StartA(UserCenterActivity.class);
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
                StartA(TeamGamejlActivity.class);
                break;
            case R.id.proxy_TeamZhuiHao:
                StartA(TeamZhuihaojlActivity.class);
                break;
            case R.id.proxy_TeamCunQu:
                StartA(TeamCunQuActivity.class);
                break;
            case R.id.proxy_TeamBjl:
                StartA(ActivityTeamActivity.class);
                break;
        }
    }
}
