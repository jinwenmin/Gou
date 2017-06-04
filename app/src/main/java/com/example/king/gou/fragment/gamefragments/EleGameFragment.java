package com.example.king.gou.fragment.gamefragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.utils.CustomVideoView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import it.sephiroth.android.library.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class EleGameFragment extends BaseFragment {

    @BindView(R.id.elegame_Videoview)
    CustomVideoView elegameVideoview;
    Unbinder unbinder;
    @BindView(R.id.elegame_game1)
    RelativeLayout elegameGame1;
    @BindView(R.id.elegame_Imageview1)
    ImageView elegameImageview1;
    @BindView(R.id.elegame_game2)
    RelativeLayout elegameGame2;
    @BindView(R.id.elegame_Imageview2)
    ImageView elegameImageview2;
    @BindView(R.id.elegame_game3)
    RelativeLayout elegameGame3;
    @BindView(R.id.elegame_Imageview3)
    ImageView elegameImageview3;
    @BindView(R.id.elegame_game4)
    RelativeLayout elegameGame4;

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
        initPage();

        return view;
    }

    private void initPage() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        elegameVideoview.playVideo(Uri.fromFile(new File("file:///android_raw/ag.mp4")));
        Picasso.with(getContext()).load("file:///android_asset/ic_live_og.webp").into(elegameImageview1);
        Picasso.with(getContext()).load("file:///android_asset/ic_live_ebet.webp").into(elegameImageview2);
        Picasso.with(getContext()).load("file:///android_asset/ic_live_bbin.webp").into(elegameImageview3);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
