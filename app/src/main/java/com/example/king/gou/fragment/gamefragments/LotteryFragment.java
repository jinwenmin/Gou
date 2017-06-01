package com.example.king.gou.fragment.gamefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.king.gou.R;
import com.example.king.gou.fragment.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LotteryFragment extends BaseFragment {

    public static LotteryFragment newInstance() {

        Bundle args = new Bundle();

        LotteryFragment fragment = new LotteryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lottery, container, false);
        return view;
    }

}
