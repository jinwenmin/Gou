package com.example.king.gou.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.example.king.gou.adapters.TeamActivityAdapter;
import com.example.king.gou.bean.UserActivity;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.proxyfragment.ActivityDetailsActivity;
import com.example.king.gou.ui.proxyfragment.TeamActivityActivity;
import com.example.king.gou.utils.HttpEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment implements HttpEngine.DataListener, View.OnClickListener {


    @BindView(R.id.ActivityTop)
    RelativeLayout ActivityTop;
    @BindView(R.id.TeamActivityListView)
    ListView TeamActivityListView;
    Unbinder unbinder;
    List<UserActivity> uc = new ArrayList<>();
    TeamActivityAdapter activityAdapter;
    public static FindFragment newInstance() {

        Bundle args = new Bundle();

        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        unbinder = ButterKnife.bind(this, view);
        RetrofitService.getInstance().GetActivityList(this);
        activityAdapter = new TeamActivityAdapter(getContext());
        TeamActivityListView.setAdapter(activityAdapter);
        initCLick();
        return view;
    }

    private void initCLick() {

        TeamActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ActivityDetailsActivity.class);
                intent.putExtra("aid", uc.get(position).getAid());
                intent.putExtra("name", uc.get(position).getName());
                startActivity(intent);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_ACTIVITYLIST) {
            uc = (List<UserActivity>) object;
            activityAdapter.addList(uc);
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }

    @Override
    public void onClick(View v) {

    }
}
