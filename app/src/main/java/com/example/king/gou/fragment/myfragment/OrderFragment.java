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
import com.example.king.gou.ui.orderFrmActivity.CunQuActivity;
import com.example.king.gou.ui.orderFrmActivity.FanDianActivity;
import com.example.king.gou.ui.orderFrmActivity.GameJiluActivity;
import com.example.king.gou.ui.orderFrmActivity.GrzbActivity;
import com.example.king.gou.ui.orderFrmActivity.LotteryBaoBiaoActivity;
import com.example.king.gou.ui.orderFrmActivity.LotteryLossActivity;
import com.example.king.gou.ui.orderFrmActivity.MyBJLActivity;
import com.example.king.gou.ui.orderFrmActivity.ZhuanZhangjlActivity;
import com.example.king.gou.ui.orderFrmActivity.ZhuiHaoActivity;

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
    @BindView(R.id.CaiPiaoyk)
    TextView CaiPiaoyk;

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
        ZhuiHaojl.setOnClickListener(this);
        CaiPiaobb.setOnClickListener(this);
        CunQujl.setOnClickListener(this);
        GeRenzb.setOnClickListener(this);
        FanDianjl.setOnClickListener(this);
        GeRenbjl.setOnClickListener(this);
        ZhuanZhangjl.setOnClickListener(this);
        CaiPiaoyk.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void StartA(Class cl) {
        startActivity(new Intent(getActivity(), cl));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.YouXijl:
                StartA(GameJiluActivity.class);
                break;
            case R.id.ZhuiHaojl:
                StartA(ZhuiHaoActivity.class);
                break;
            case R.id.CaiPiaobb:
                StartA(LotteryBaoBiaoActivity.class);
                break;
            case R.id.CunQujl:
                StartA(CunQuActivity.class);
                break;
            case R.id.GeRenzb:
                StartA(GrzbActivity.class);
                break;
            case R.id.FanDianjl:
                StartA(FanDianActivity.class);
                break;
            case R.id.GeRenbjl:
                StartA(MyBJLActivity.class);
                break;
            case R.id.ZhuanZhangjl:
                StartA(ZhuanZhangjlActivity.class);
                break;
            case R.id.CaiPiaoyk:
                StartA(LotteryLossActivity.class);
                break;
        }
    }
}
