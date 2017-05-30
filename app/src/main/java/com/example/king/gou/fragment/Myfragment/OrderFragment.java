package com.example.king.gou.fragment.Myfragment;


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
public class OrderFragment extends BaseFragment {


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
        return view;
    }

}
