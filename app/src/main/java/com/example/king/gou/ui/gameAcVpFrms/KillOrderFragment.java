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
public class KillOrderFragment extends BaseFragment {


    @BindView(R.id.Gamelist)
    ListView Gamelist;
    Unbinder unbinder;
    public TouZhuAdapter adapter;
    List<TouZhu> touzhu=new ArrayList<>();

    public static KillOrderFragment newInstance() {

        Bundle args = new Bundle();

        KillOrderFragment fragment = new KillOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kill_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new TouZhuAdapter(getActivity());
        Gamelist.setAdapter(adapter);
        Log.d("KillOrderFragment===", "运行过了 ");
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
            // adapter.addListView(ts);
            touzhu = ts;
            adapter.addListView(touzhu);
        }

    }
}
