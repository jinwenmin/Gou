package com.example.king.gou.ui.gameAcVpFrms;


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
public class AllFragment extends BaseFragment {

    public static AllFragment newInstance() {

        Bundle args = new Bundle();

        AllFragment fragment = new AllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all, container, false);
    }

}
