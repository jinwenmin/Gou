package com.example.king.gou.fragment.gamefragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.king.gou.R;
import com.example.king.gou.adapters.MyAdapter;
import com.example.king.gou.bean.AccountChange;
import com.example.king.gou.bean.GameIm;
import com.example.king.gou.bean.GameImages;
import com.example.king.gou.bean.GameType;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.GameCenterActivity;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class LotteryFragment extends BaseFragment implements HttpEngine.DataListener {

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
    List<GameType> ListgameTypes = new ArrayList<GameType>();
    List<List<AccountChange>> acs;
    List<List<GameIm>> gids = new ArrayList<>();

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
        RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);
       /* getdatas();
        // 配置适配器
        MyAdapter adapter = new MyAdapter(getActivity(), imgs); // 布局里的控件id
        // 添加并且显示
        classifyMorelist.setAdapter(adapter);
        classifyMorelist.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                startActivity(new Intent(getActivity(), GameCenterActivity.class));
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });*/
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

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_GAME4) {
            ListgameTypes = (List<GameType>) object;
            GameImages gameTypes1 = new GameImages();
            GameImages gameTypes2 = new GameImages();
            GameImages gameTypes3 = new GameImages();
            GameImages gameTypes4 = new GameImages();
            GameImages gameTypes5 = new GameImages();
            GameImages gameTypes6 = new GameImages();
            List<GameIm> gameIms1 = new ArrayList<>();
            List<GameIm> gameIms2 = new ArrayList<>();
            List<GameIm> gameIms3 = new ArrayList<>();
            List<GameIm> gameIms4 = new ArrayList<>();
            List<GameIm> gameIms5 = new ArrayList<>();
            List<GameIm> gameIms6 = new ArrayList<>();

            for (int i = 0; i < ListgameTypes.size(); i++) {
                int group_id = ListgameTypes.get(i).getGroup_id();

                if (group_id == 1) {
                    gameTypes1.setType("时时彩");
                    if (ListgameTypes.get(i).getGid() == 2) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo2);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms1.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 3) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo3);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms1.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 21) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo21);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms1.add(gameIm1);

                    }
                    gameTypes1.setGameIms(gameIms1);

                }
                if (group_id == 2) {
                    gameTypes2.setType("11选5");
                    List<GameIm> gs = new ArrayList<>();
                    if (ListgameTypes.get(i).getGid() == 8) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo8);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms2.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 12) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo12);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms2.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 14) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo14);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms2.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 25) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo25);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms2.add(gameIm1);

                    }
                    gameTypes2.setGameIms(gameIms2);

                }
                if (group_id == 3) {
                    gameTypes3.setType("高频彩");
                    if (ListgameTypes.get(i).getGid() == 11) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo11);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms3.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 7) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo7);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms3.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 19) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo19);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms3.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 20) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo20);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms3.add(gameIm1);

                    }
                    gameTypes3.setGameIms(gameIms3);

                }
                if (group_id == 4) {

                    gameTypes4.setType("低频彩");

                    if (ListgameTypes.get(i).getGid() == 10) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo10);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms4.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 9) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo9);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms4.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 28) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo28);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms4.add(gameIm1);

                    }

                    gameTypes4.setGameIms(gameIms4);
                }

                if (group_id == 5) {

                    gameTypes5.setType("快乐彩");

                    if (ListgameTypes.get(i).getGid() == 15) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo15);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms5.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 13) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo13);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms5.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 27) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo27);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms5.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 26) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo26);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms5.add(gameIm1);

                    }

                    gameTypes5.setGameIms(gameIms5);
                }

                if (group_id == 6) {

                    gameTypes6.setType("境外时时彩");

                    if (ListgameTypes.get(i).getGid() == 23) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo23);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms6.add(gameIm1);

                    }
                    if (ListgameTypes.get(i).getGid() == 24) {
                        GameIm gameIm1 = new GameIm();
                        gameIm1.setGameimg(R.drawable.logo24);
                        gameIm1.setImgText(ListgameTypes.get(i).getName());
                        gameIms6.add(gameIm1);

                    }
                    gameTypes6.setGameIms(gameIms6);
                }


            }
            Log.d("GidsSize", gids.size() + "");

            imgs.add(gameTypes1);
            imgs.add(gameTypes2);
            imgs.add(gameTypes3);
            imgs.add(gameTypes4);
            imgs.add(gameTypes5);
            imgs.add(gameTypes6);
            // 配置适配器
            MyAdapter adapter = new MyAdapter(getActivity(), imgs); // 布局里的控件id
            // 添加并且显示0
            classifyMorelist.setAdapter(adapter);
            classifyMorelist.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                    Intent intent = new Intent(getActivity(), GameCenterActivity.class);
                    intent.putExtra("gid", gids.get(section).get(position).getGid());
                    intent.putExtra("name", gids.get(section).get(position).getName());
                    intent.putExtra("position", position);
                    intent.putExtra("section", section);
                    startActivity(intent);
                }

                @Override
                public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

                }
            });
            List<GameIm> gs1 = new ArrayList<>();
            List<GameIm> gs2 = new ArrayList<>();
            List<GameIm> gs3 = new ArrayList<>();
            List<GameIm> gs4 = new ArrayList<>();
            List<GameIm> gs5 = new ArrayList<>();
            List<GameIm> gs6 = new ArrayList<>();
            for (int i = 0; i < ListgameTypes.size(); i++) {
                int group_id = ListgameTypes.get(i).getGroup_id();
                if (group_id == 1) {
                    GameIm gameIm = new GameIm();
                    int gid = ListgameTypes.get(i).getGid();
                    String name = ListgameTypes.get(i).getName();
                    gameIm.setGid(gid);
                    gameIm.setName(name);
                    gs1.add(gameIm);
                }
                if (group_id == 2) {
                    GameIm gameIm = new GameIm();
                    int gid = ListgameTypes.get(i).getGid();
                    String name = ListgameTypes.get(i).getName();
                    gameIm.setGid(gid);
                    gameIm.setName(name);
                    gs2.add(gameIm);
                }
                if (group_id == 3) {
                    GameIm gameIm = new GameIm();
                    int gid = ListgameTypes.get(i).getGid();
                    String name = ListgameTypes.get(i).getName();
                    gameIm.setGid(gid);
                    gameIm.setName(name);
                    gs3.add(gameIm);
                }
                if (group_id == 4) {
                    GameIm gameIm = new GameIm();
                    int gid = ListgameTypes.get(i).getGid();
                    String name = ListgameTypes.get(i).getName();
                    gameIm.setGid(gid);
                    gameIm.setName(name);
                    gs4.add(gameIm);
                }
                if (group_id == 5) {
                    GameIm gameIm = new GameIm();
                    int gid = ListgameTypes.get(i).getGid();
                    String name = ListgameTypes.get(i).getName();
                    gameIm.setGid(gid);
                    gameIm.setName(name);
                    gs5.add(gameIm);
                }
                if (group_id == 6) {
                    GameIm gameIm = new GameIm();
                    int gid = ListgameTypes.get(i).getGid();
                    String name = ListgameTypes.get(i).getName();
                    gameIm.setGid(gid);
                    gameIm.setName(name);
                    gs6.add(gameIm);
                }
            }
            gids.add(gs1);
            gids.add(gs2);
            gids.add(gs3);
            gids.add(gs4);
            gids.add(gs5);
            gids.add(gs6);

        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
