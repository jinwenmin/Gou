package com.example.king.gou.ui.gameAcVpFrms;


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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManKillOrderFragment extends BaseFragment {


    @BindView(R.id.Gamelist)
    ListView Gamelist;
    Unbinder unbinder;
    public  TouZhuAdapter adapter;
List<TouZhu> touzhu=new ArrayList<>();
    public ManKillOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_man_kill_order, container, false);
        unbinder = ButterKnife.bind(this, view);

        Log.d("ManKillOrderFragment===", "运行过了 ");
        //adapter.addListView(touzhu);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getList(List<TouZhu> ts) {
        if (ts.size() != 0) {
            //adapter.addListView(ts);
            adapter = new TouZhuAdapter(getActivity());
            Gamelist.setAdapter(adapter);
            touzhu=ts;
            adapter.addListView(touzhu);
        }
    }
}
