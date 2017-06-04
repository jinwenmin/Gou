package com.example.king.gou.fragment.gamefragments;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.example.king.gou.R;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.utils.CustomVideoView;

import java.io.File;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import it.sephiroth.android.library.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class BetFragment extends BaseFragment {
    @BindView(R.id.betgame_Videoview)
    VideoView betgameVideoview;
    @BindView(R.id.betgame_game1)
    RelativeLayout betgameGame1;
    @BindView(R.id.betgame_Imageview1)
    ImageView betgameImageview1;
    @BindView(R.id.betgame_game2)
    RelativeLayout betgameGame2;
    @BindView(R.id.betgame_Imageview2)
    ImageView betgameImageview2;
    @BindView(R.id.betgame_game3)
    RelativeLayout betgameGame3;
    @BindView(R.id.betgame_Imageview3)
    ImageView betgameImageview3;
    @BindView(R.id.betgame_game4)
    RelativeLayout betgameGame4;
    Unbinder unbinder;

    public static BetFragment newInstance() {

        Bundle args = new Bundle();

        BetFragment fragment = new BetFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bet, container, false);
        unbinder = ButterKnife.bind(this, view);
        initPage();
        return view;
    }

    private void initPage() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        //用来设置要播放的mp4文件
        betgameVideoview.setMediaController(new MediaController(getContext()));
        betgameVideoview.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.ag));
//用来设置控制台样式
        betgameVideoview.setMediaController(new MediaController(getContext()));
//用来设置起始播放位置，为0表示从开始播放
        betgameVideoview.seekTo(0);
//用来设置mp4播放器是否可以聚焦
        betgameVideoview.requestFocus();
//开始播放
        betgameVideoview.start();
        betgameVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
        betgameVideoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                betgameVideoview.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.ag));
                betgameVideoview.start();
            }
        });
        Picasso.with(getContext()).load("file:///android_asset/ic_live_og.webp").into(betgameImageview1);
        Picasso.with(getContext()).load("file:///android_asset/ic_live_ebet.webp").into(betgameImageview2);
        Picasso.with(getContext()).load("file:///android_asset/ic_live_bbin.webp").into(betgameImageview3);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
