package com.example.king.gou.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.R;
import com.example.king.gou.adapters.HomeGameAdapter;
import com.example.king.gou.adapters.HomeGamesAdapters;
import com.example.king.gou.adapters.PageAdapter;
import com.example.king.gou.bean.AdvertisementObject;
import com.example.king.gou.bean.HistoryGames;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.AddGameActivity;
import com.example.king.gou.ui.GameCenterActivity;
import com.example.king.gou.utils.AutoTextView;
import com.example.king.gou.utils.BaseAutoScrollUpTextView;
import com.example.king.gou.utils.DataBaseHelper;
import com.example.king.gou.utils.FingerPrintUtils;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.MainScrollUpAdvertisementView;
import com.example.king.gou.utils.MarqueeText;
import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, HttpEngine.DataListener, OnItemClickListener {

    List imgs = new ArrayList();
    @BindView(R.id.Erweima)
    ImageView Erweima;
    @BindView(R.id.home_top)
    RelativeLayout homeTop;
    @BindView(R.id.home_viewpager)
    RollPagerView homeViewpager;
    @BindView(R.id.MainScrollAd)
    MainScrollUpAdvertisementView MainScrollAd;
    @BindView(R.id.test)
    MarqueeText test;
    @BindView(R.id.HomeFragment_Text)
    TextView HomeFragmentText;
    @BindView(R.id.HomeFragment_addGame)
    TextView HomeFragmentAddGame;
    @BindView(R.id.MyGame)
    RelativeLayout MyGame;
    @BindView(R.id.HomeGridView)
    GridView HomeGridView;
    @BindView(R.id.home_ralative)
    RelativeLayout homeRalative;
    @BindView(R.id.Autotext)
    AutoTextView Autotext;
    private AlertView alertView;
    // 一个自定义的布局，作为显示的内容
    View contentView;
    private AlertView alertView1;
    // 一个自定义的布局，作为显示的内容
    View contentView1;

    private AlertView alertViewNotice;
    // 一个自定义的布局，作为显示的内容
    View contentViewNotice;


    private final static String TAG = "MainActivity";
    private SharedPreferences Finger;
    FingerprintManagerCompat manager;
    KeyguardManager mKeyguardManager;
    private FingerPrintUtils fingerPrintUiHelper;
    private ImageView fingerImg;
    private TextView fingerText;
    private ImageView fingerImg1;
    private TextView fingerText1;
    String show = null;
    boolean finger;
    SQLiteDatabase db;
    HomeGameAdapter adapter;
    HomeGamesAdapters adapters;
    Unbinder unbinder;
    private int GameImgs[] = new int[]{
            R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, 0, 0, 0, R.drawable.logo7, R.drawable.logo8, R.drawable.logo9, R.drawable.logo10,
            R.drawable.logo11, R.drawable.logo12, R.drawable.logo13, R.drawable.logo14, R.drawable.logo15, 0, 0, 0, R.drawable.logo19, R.drawable.logo20,
            R.drawable.logo21, 0, R.drawable.logo23, R.drawable.logo24, R.drawable.logo25, R.drawable.logo26, R.drawable.logo27, R.drawable.logo28

    };
    private List<String> arrList = new ArrayList<String>();
    private Handler handler = new Handler();
    private int count = 0;
    private TextView home_notice;
    String notice = "";

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        db = dataBaseHelper.getWritableDatabase();
        // getData();


        RetrofitService.getInstance().GetUserInfo(this);
        mKeyguardManager = (KeyguardManager) getActivity().getSystemService(Context.KEYGUARD_SERVICE);
        manager = FingerprintManagerCompat.from(getActivity());
        Finger = getActivity().getSharedPreferences("Finger", Activity.MODE_PRIVATE);
        finger = Finger.getBoolean("finger", false);
        Log.d("Finger==", finger + "");
        initView();

        initImgs();
        initSql();
        initScrollView();
        initClick();
        return view;
    }

    private void initClick() {
        HomeFragmentAddGame.setOnClickListener(this);
        Autotext.setOnClickListener(this);
    }

    private void initSql() {
        Cursor cursor = db.rawQuery("select * from games order by count desc", null);
        List<HistoryGames> hs = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            int gid = cursor.getInt(2);
            int img = cursor.getInt(3);
            int count = cursor.getInt(4);
            HistoryGames h = new HistoryGames();
            h.setName(name);
            h.setGid(gid);
            h.setImg(img);
            h.setCount(count);
            hs.add(h);
        }
        adapters = new HomeGamesAdapters(getContext());
        HomeGridView.setNumColumns(4);
        HomeGridView.setAdapter(adapters);
        HomeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GameCenterActivity.class);
                HistoryGames item = (HistoryGames) adapters.getItem(position);
                int gid = item.getGid();
                String name = item.getName();
                int count1 = 0;
                Log.d("游戏中心Gid", gid + "");
                Cursor cursor1 = db.rawQuery("select * from games where name=?", new String[]{name});

                while (cursor1.moveToNext()) {
                    count1 = cursor1.getInt(4);
                }
                Log.d("游戏的数据库Count", count1 + "");
                if (count1 == 0) {
                    db.execSQL("insert into games(name,gid,img,count) values(?,?,?,?)", new Object[]{name, gid, GameImgs[gid - 1], 1});
                } else {
                    db.execSQL("update games set count=? where name=?", new Object[]{count1 + 1, name});
                }
                intent.putExtra("gid", gid);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        adapters.addList(hs);
        PageAdapter pageAdapter = new PageAdapter(imgs);
        homeViewpager.setAdapter(pageAdapter);

    }

    private void initView() {
        alertView1 = new AlertView(null, null, "取消", null, null, getContext(), AlertView.Style.Alert, this);
        contentView1 = LayoutInflater.from(getContext()).inflate(R.layout.item_finger, null);
        alertView1.addExtView(contentView1);

        alertViewNotice = new AlertView(null, null, "确认", null, null, getContext(), AlertView.Style.Alert, this);
        contentViewNotice = LayoutInflater.from(getContext()).inflate(R.layout.home_notice, null);
        alertViewNotice.addExtView(contentViewNotice);
        home_notice = ((TextView) contentViewNotice.findViewById(R.id.home_notice));

        alertView = new AlertView(null, null, "确认", null, null, getContext(), AlertView.Style.Alert, this);
        contentView = LayoutInflater.from(getContext()).inflate(
                R.layout.item_homenotice, null);
        alertView.addExtView(contentView);


    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, 3000);

                Autotext.next();
                Autotext.setText(arrList.get(count % arrList.size()));
                notice = arrList.get(count % arrList.size());
                count++;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    /**
     * 判断是否满足设置指纹的条件
     *
     * @return true 满足 false 不满足
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean isSatisfactionFingerprint() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "请开启指纹识别权限", Toast.LENGTH_LONG).show();
            return false;
        }
        //硬件是否支持指纹识别
        if (!manager.isHardwareDetected()) {
            Toast.makeText(getActivity(), "您手机不支持指纹识别功能", Toast.LENGTH_LONG).show();
            return false;
        }

        //手机是否开启锁屏密码
        if (!mKeyguardManager.isKeyguardSecure()) {
            Toast.makeText(getActivity(), "请开启开启锁屏密码，并录入指纹后再尝试", Toast.LENGTH_LONG).show();
            return false;
        }
        //是否有指纹录入
        if (!manager.hasEnrolledFingerprints()) {
            Toast.makeText(getActivity(), "您还未录入指纹", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void notice() {
        RetrofitService.getInstance().getHomeNotice(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initFingerPrint() {
        fingerPrintUiHelper = new FingerPrintUtils(getActivity());
        fingerPrintUiHelper.setFingerPrintListener(new FingerprintManagerCompat.AuthenticationCallback() {
            /**
             * 指纹识别成功
             *
             * @param result
             */
            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                fingerText1.setText("指纹识别成功");
                fingerText1.setTextColor(Color.rgb(0, 150, 136));
                fingerImg1.setImageResource(R.drawable.ic_fingerprint_success);
                Toast.makeText(getActivity(), "指纹识别成功", Toast.LENGTH_SHORT).show();
                alertView1.dismiss();
                SystemClock.sleep(2000);
                notice();
            }

            /**
             * 指纹识别失败调用
             */
            @Override
            public void onAuthenticationFailed() {
                fingerText1.setText("指纹验证失败,请重试");
                fingerText1.setTextColor(Color.rgb(244, 81, 30));
                fingerImg1.setImageResource(R.drawable.ic_fingerprint_error);
                Toast.makeText(getActivity(), "指纹识别失败", Toast.LENGTH_SHORT).show();
                alertView.dismiss();

            }

            /**
             *
             * @param helpMsgId
             * @param helpString
             *
             */
            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                Toast.makeText(getActivity(), helpString, Toast.LENGTH_SHORT).show();
            }

            /**
             * 多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
             *
             * @param errMsgId  最多的错误次数
             * @param errString 错误的信息反馈
             */
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                //具体等多长时间为测试
                Log.i(TAG, "errMsgId=" + errMsgId + "-----errString" + errString);
                Toast.makeText(getActivity(), "指纹识别出错次数过多，稍后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initImgs() {

        ImageView img1 = new ImageView(getContext());
        img1.setBackground(getResources().getDrawable(R.drawable.banner_0));
        ImageView img2 = new ImageView(getContext());
        img2.setBackground(getResources().getDrawable(R.drawable.banner_1));
        imgs.add(img1);
        imgs.add(img2);


    }

    private void initScrollView() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("重新显示", hidden + "");
        if (!hidden) {
            Cursor cursor = db.rawQuery("select * from games order by count desc", null);
            List<HistoryGames> hs = new ArrayList<>();
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                int gid = cursor.getInt(2);
                int img = cursor.getInt(3);
                int count = cursor.getInt(4);
                HistoryGames h = new HistoryGames();
                h.setName(name);
                h.setGid(gid);
                h.setImg(img);
                h.setCount(count);
                hs.add(h);
            }
            adapters.addList(hs);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.HomeFragment_addGame:
                startActivity(new Intent(getActivity(), AddGameActivity.class));
                break;
            case R.id.Autotext:
                home_notice.setText(notice);
                alertViewNotice.show();
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_HOMENOTICE) {
            if (object != null) {
                final List<String> listnotice = (List<String>) object;
                if ("0".equals(listnotice.get(1))) {
                    TextView homeNotice = (TextView) contentView.findViewById(R.id.homeNoticeText);
                    homeNotice.setText(Html.fromHtml(listnotice.get(0)));
                    alertView.show();
                    show = "0";
                    MainScrollAd.setVisibility(View.GONE);
                } else {
                    TextView textView = new TextView(getActivity());
                    textView.setText(Html.fromHtml(listnotice.get(0)));
                    Log.d("这个是首页的公告==", Html.fromHtml(listnotice.get(0)) + "");
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    arrList.add(textView.getText().toString());
                    Autotext.setVisibility(View.VISIBLE);
                    handler.postDelayed(runnable, 0);
                   /* ArrayList<AdvertisementObject> notices = new ArrayList<AdvertisementObject>();
                    AdvertisementObject advertisementObject = new AdvertisementObject();
                    advertisementObject.info = Html.fromHtml(listnotice.get(0)) + "";
                    notices.add(advertisementObject);
                    notices.add(advertisementObject);
                    MainScrollAd.setData(notices);
                    MainScrollAd.setTextSize(15);
                    MainScrollAd.setTimer(3000);
                    MainScrollAd.setOnItemClickListener(new BaseAutoScrollUpTextView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Toast.makeText(getActivity(), Html.fromHtml(listnotice.get(0)) + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                    MainScrollAd.start();*/

                }
            }
        }
        if (apiId == RetrofitService.API_ID_USERINFO) {
            if (object != null) {
                if (finger) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (isSatisfactionFingerprint()) {
                            fingerImg1 = ((ImageView) contentView1.findViewById(R.id.fingerImg));
                            fingerText1 = ((TextView) contentView1.findViewById(R.id.fingerText));
                            alertView1.show();
                            show = "1";
                            initFingerPrint();
                        }
                    } else {
                        Toasty.info(getActivity(), "系统版本过低不支持指纹识别...", Toast.LENGTH_SHORT).show();
                    }
                }
                if (!finger) {
                    notice();
                }
            }
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        if ("0".equals(show)) {
            if (AlertView.CANCELPOSITION == position) {
                alertView.dismiss();
            }
        }
        if ("1".equals(show)) {
            if (position == AlertView.CANCELPOSITION) {
                alertView1.dismiss();
            }
        }
    }


}
