package com.example.king.gou.fragment.gamefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.king.gou.R;
import com.example.king.gou.adapters.MyAdapter;
import com.example.king.gou.bean.GameIm;
import com.example.king.gou.bean.GameImages;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.utils.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class LotteryFragment extends BaseFragment {

    @BindView(R.id.classify_morelist)
    PinnedHeaderListView classifyMorelist;
    Unbinder unbinder;
    private List<GameImages> imgs = new ArrayList<>();

    private int ssc[] = new int[]{R.drawable.ic_shishicai_cq, R.drawable.ic_shishicai_tj, R.drawable.ic_shishicai_xj, R.drawable.ic_shishicai_bj};
    private String sscName[] = new String[]{"重庆时时彩", "天津时时彩", "新疆时时彩", "北京时时彩"};
    private int jwssc[] = new int[]{R.drawable.ic_shishicai_hanguo, R.drawable.ic_shishicai_dongjing, R.drawable.ic_shishicai_jnd, R.drawable.ic_shishicai_twwf};
    private String jwsscName[] = new String[]{"韩国1.5分彩", "东京1.5分彩", "加拿大3.5分彩", "台湾5分彩"};
    private int syw[] = new int[]{R.drawable.ic_11x5_gd, R.drawable.ic_11x5_sd, R.drawable.ic_11x5_jx, R.drawable.ic_11x5_jisu};
    private String sywName[] = new String[]{"广东11×5", "山东11×5", "江西11×5", "极速11×5"};
    private int gpc[] = new int[]{R.drawable.ic_pk10_bj, R.drawable.ic_jsk3, R.drawable.ic_bjkl8, R.drawable.ic_twbingo};
    private String gpcName[] = new String[]{"北京PK10", "江苏快3", "北京快乐8", "台湾宾果"};
    private int klc[] = new int[]{R.drawable.ic_yfc, R.drawable.ic_efc, R.drawable.ic_kl8, R.drawable.ic_txffc};
    private String klcName[] = new String[]{"1分彩", "2分彩", "吉祥快乐8", "腾讯分分彩"};
    private int dpc[] = new int[]{R.drawable.ic_35, R.drawable.ic_3d, R.drawable.ic_6hc};
    private String dpcName[] = new String[]{"排列三、五", "福彩3D", "香港六合彩"};

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
        unbinder = ButterKnife.bind(this, view);
        getdatas();
        // 配置适配器
        MyAdapter adapter = new MyAdapter(getActivity(), imgs); // 布局里的控件id
        // 添加并且显示
        classifyMorelist.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public List<GameImages> getdatas() {
        GameImages gameTypes1 = new GameImages();
        gameTypes1.setType("时时彩");
        List<GameIm> gameIms = new ArrayList<>();
        for (int i = 0; i < ssc.length; i++) {
            GameIm gameIm = new GameIm();
            gameIm.setGameimg(ssc[i]);
            gameIm.setImgText(sscName[i]);
            gameIms.add(gameIm);
        }
        GameImages gameTypes2 = new GameImages();
        gameTypes2.setType("境外时时彩");
        List<GameIm> gameIms2 = new ArrayList<>();
        for (int j = 0; j < jwssc.length; j++) {
            GameIm gameIm = new GameIm();
            gameIm.setGameimg(jwssc[j]);
            gameIm.setImgText(jwsscName[j]);
            gameIms2.add(gameIm);
        }
        GameImages gameTypes3 = new GameImages();
        gameTypes3.setType("11选5");
        List<GameIm> gameIms3 = new ArrayList<>();
        for (int j = 0; j < syw.length; j++) {
            GameIm gameIm = new GameIm();
            gameIm.setGameimg(syw[j]);
            gameIm.setImgText(sywName[j]);
            gameIms3.add(gameIm);
        }
        GameImages gameTypes4 = new GameImages();
        gameTypes4.setType("高频彩");
        List<GameIm> gameIms4 = new ArrayList<>();
        for (int j = 0; j < gpc.length; j++) {
            GameIm gameIm = new GameIm();
            gameIm.setGameimg(gpc[j]);
            gameIm.setImgText(gpcName[j]);
            gameIms4.add(gameIm);
        }
        GameImages gameTypes5 = new GameImages();
        gameTypes5.setType("快乐彩");
        List<GameIm> gameIms5 = new ArrayList<>();
        for (int j = 0; j < klc.length; j++) {
            GameIm gameIm = new GameIm();
            gameIm.setGameimg(klc[j]);
            gameIm.setImgText(klcName[j]);
            gameIms5.add(gameIm);
        }
        GameImages gameTypes6 = new GameImages();
        gameTypes6.setType("低频彩");
        List<GameIm> gameIms6 = new ArrayList<>();
        for (int j = 0; j < dpc.length; j++) {
            GameIm gameIm = new GameIm();
            gameIm.setGameimg(dpc[j]);
            gameIm.setImgText(dpcName[j]);
            gameIms6.add(gameIm);
        }
        gameTypes1.setGameIms(gameIms);
        gameTypes2.setGameIms(gameIms2);
        gameTypes3.setGameIms(gameIms3);
        gameTypes4.setGameIms(gameIms4);
        gameTypes5.setGameIms(gameIms5);
        gameTypes6.setGameIms(gameIms6);
        imgs.add(gameTypes1);
        imgs.add(gameTypes2);
        imgs.add(gameTypes3);
        imgs.add(gameTypes4);
        imgs.add(gameTypes5);
        imgs.add(gameTypes6);
        return imgs;
    }
}
