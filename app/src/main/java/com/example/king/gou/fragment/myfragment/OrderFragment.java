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
import com.example.king.gou.ui.orderFrmActivity.GameJiluActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.YouXijl)
    TextView YouXijl;
    @BindView(R.id.ZhuiHaojl)
    TextView ZhuiHaojl;
    @BindView(R.id.CaiPiaobb)
    TextView CaiPiaobb;
    @BindView(R.id.CunQujl)
    TextView CunQujl;
    @BindView(R.id.GeRenzb)
    TextView GeRenzb;
    @BindView(R.id.FanDianjl)
    TextView FanDianjl;
    @BindView(R.id.GeRenbjl)
    TextView GeRenbjl;
    @BindView(R.id.ZhuanZhangjl)
    TextView ZhuanZhangjl;
    Unbinder unbinder;

    public static OrderFragment newInstance() {

        Bundle args = new Bundle();

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        YouXijl.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.YouXijl:
                startActivity(new Intent(getActivity(), GameJiluActivity.class));
                break;
        }
    }
}
