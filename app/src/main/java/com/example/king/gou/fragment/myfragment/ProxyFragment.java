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
import com.example.king.gou.ui.proxyfragment.UserCenterActivity;

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
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proxy_home:
                startActivity(new Intent(getActivity(), ProxyHomeActivity.class));
                break;
            case R.id.proxy_userCenter:
                startActivity(new Intent(getActivity(), UserCenterActivity.class));
                break;

        }
    }
}
