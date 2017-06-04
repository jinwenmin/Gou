package com.example.king.gou.fragment.gamefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.example.king.gou.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import it.sephiroth.android.library.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class EleGameFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.elegame_Imageview1)
    ImageView elegameImageview1;
    @BindView(R.id.elegame_game2)
    RelativeLayout elegameGame2;


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
        initImageView();
        return view;
    }

    private void initImageView() {

        Picasso.with(getContext()).load("file:///android_asset/ic_electric_pt.webp").into(elegameImageview1);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
