package com.example.king.gou.fragment.gamefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.king.gou.R;
import com.example.king.gou.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import it.sephiroth.android.library.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class SportsFragment extends BaseFragment {

    @BindView(R.id.sport_img1)
    ImageView sportImg1;
    Unbinder unbinder;

    public static SportsFragment newInstance() {

        Bundle args = new Bundle();

        SportsFragment fragment = new SportsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sports, container, false);
        unbinder = ButterKnife.bind(this, view);
        initImageView();
        return view;
    }

    private void initImageView() {
        Picasso.with(getContext()).load("file:///android_asset/ic_sports_disable.webp").into(sportImg1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
