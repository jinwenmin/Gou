package com.example.king.gou.ui.gameAcVpFrms;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.king.gou.R;
import com.example.king.gou.adapters.TouZhuAdapter;
import com.example.king.gou.bean.TouZhu;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.fragment.MyFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends BaseFragment implements HttpEngine.DataListener {

    @BindView(R.id.AllList)
    ListView AllList;
    Unbinder unbinder;
    List<TouZhu> ts;
    TouZhuAdapter adapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new TouZhuAdapter(getActivity());
        AllList.setAdapter(adapter);
        Log.d("AllFragment===", "运行过了 ");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_TOUZHUSEAR) {
            if (object != null) {
                ts = (List<TouZhu>) object;
                Log.d("ALLFragment==", ts.get(0).getName());
                adapter.addListView(ts);
            }
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }

    public void getList(List<TouZhu> ts) {
        adapter.addListView(ts);

    }

}
