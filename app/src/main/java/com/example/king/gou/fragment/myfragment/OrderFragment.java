package com.example.king.gou.fragment.myfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.king.gou.R;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.ui.orderFrmActivity.CunQuActivity;
import com.example.king.gou.ui.orderFrmActivity.FanDianActivity;
import com.example.king.gou.ui.orderFrmActivity.GameJiluActivity;
import com.example.king.gou.ui.orderFrmActivity.GrzbActivity;
import com.example.king.gou.ui.orderFrmActivity.LotteryBaoBiaoActivity;
import com.example.king.gou.ui.orderFrmActivity.LotteryLossActivity;
import com.example.king.gou.ui.orderFrmActivity.MyActivityActivity;
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
    LinearLayout YouXijl;
    @BindView(R.id.ZhuiHaojl)
    LinearLayout ZhuiHaojl;
    @BindView(R.id.CaiPiaobb)
    LinearLayout CaiPiaobb;
    @BindView(R.id.CunQujl)
    LinearLayout CunQujl;
    @BindView(R.id.GeRenzb)
    LinearLayout GeRenzb;
    @BindView(R.id.FanDianjl)
    LinearLayout FanDianjl;
    @BindView(R.id.GeRenbjl)
    LinearLayout GeRenbjl;
    @BindView(R.id.ZhuanZhangjl)
    LinearLayout ZhuanZhangjl;
    @BindView(R.id.CaiPiaoyk)
    LinearLayout CaiPiaoyk;
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
       /* Drawable yxjl = getResources().getDrawable(R.drawable.ic_youxijilu);
        yxjl.setBounds(0, 0, 200, 200);
        Drawable zhjl = getResources().getDrawable(R.drawable.ic_zhuihaojilu);
        zhjl.setBounds(0, 0, 200, 200);
        Drawable cpbb = getResources().getDrawable(R.drawable.ic_caipiaobaobiao);
        cpbb.setBounds(0, 0, 200, 200);
        Drawable cqjl = getResources().getDrawable(R.drawable.ic_cunqukuanjilu);
        cqjl.setBounds(0, 0, 200, 200);
        Drawable zbjl = getResources().getDrawable(R.drawable.ic_zhangbianjilu);
        zbjl.setBounds(0, 0, 200, 200);
        Drawable fdjl = getResources().getDrawable(R.drawable.ic_fandianjilu);
        fdjl.setBounds(0, 0, 200, 200);
        Drawable bjlbb = getResources().getDrawable(R.drawable.ic_baijialebaobiao);
        bjlbb.setBounds(0, 0, 200, 200);
        Drawable zzjl = getResources().getDrawable(R.drawable.ic_zhuanzhangjilu);
        zzjl.setBounds(0, 0, 200, 200);
        Drawable cpyk = getResources().getDrawable(R.drawable.ic_caipiaobaobiao);
        cpyk.setBounds(0, 0, 200, 200);

        YouXijl.setCompoundDrawables(null, yxjl, null, null);
        ZhuiHaojl.setCompoundDrawables(null, zhjl, null, null);
        CaiPiaobb.setCompoundDrawables(null, cpbb, null, null);
        CunQujl.setCompoundDrawables(null, cqjl, null, null);
        GeRenzb.setCompoundDrawables(null, zbjl, null, null);
        FanDianjl.setCompoundDrawables(null, fdjl, null, null);
        GeRenbjl.setCompoundDrawables(null, bjlbb, null, null);
        ZhuanZhangjl.setCompoundDrawables(null, zzjl, null, null);
        CaiPiaoyk.setCompoundDrawables(null, cpyk, null, null);

*/
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
                StartA(MyActivityActivity.class);
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
