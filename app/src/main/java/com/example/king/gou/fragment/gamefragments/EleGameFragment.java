package com.example.king.gou.fragment.gamefragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.king.gou.R;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.utils.CustomVideoView;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class EleGameFragment extends BaseFragment {

    @BindView(R.id.elegame_Videoview)
    CustomVideoView elegameVideoview;
    Unbinder unbinder;

    public static EleGameFragment newInstance() {

        Bundle args = new Bundle();

        EleGameFragment fragment = new EleGameFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ele_game, container, false);
        unbinder = ButterKnife.bind(this, view);
        //elegameVideoview.playVideo(Uri.fromFile(and));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
