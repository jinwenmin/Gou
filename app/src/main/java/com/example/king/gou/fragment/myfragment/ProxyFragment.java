package com.example.king.gou.fragment.myfragment;


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
public class ProxyFragment extends BaseFragment {


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
        return view;
    }

}
