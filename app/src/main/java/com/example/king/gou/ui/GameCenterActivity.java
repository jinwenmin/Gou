package com.example.king.gou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.DrawHistoryAdapter;
import com.example.king.gou.bean.BettingSync;
import com.example.king.gou.bean.RecordList;
import com.example.king.gou.bean.SwitchG;
import com.example.king.gou.fragment.FindFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class GameCenterActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener {

    /*    @BindView(R.id.vpId)
        ViewPager vpId;
        @BindView(R.id.toolbar_tab)
        TabLayout toolbar_tab;*/
    List<FindFragment> fragmentList = new ArrayList<>();
    List<String> pageTitle = new ArrayList<>();
    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.GameCenterTop)
    RelativeLayout GameCenterTop;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.textPeriod)
    TextView textPeriod;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.textTime)
    TextView textTime;
    @BindView(R.id.MoneyCount)
    TextView MoneyCount;
    @BindView(R.id.GameCenterProgressBar)
    ProgressBar GameCenterProgressBar;
    /*  @BindView(R.id.addGame)
      ImageView addGame;*/
    @BindView(R.id.GameCentertitle)
    TextView GameCentertitle;
    @BindView(R.id.GameCenterListView)
    ListView GameCenterListView;
    @BindView(R.id.SpinnerType1)
    Spinner SpinnerType1;
    @BindView(R.id.SpinnerType2)
    Spinner SpinnerType2;
    @BindView(R.id.gameCenter_coordinatorlayout)
    CoordinatorLayout gameCenterCoordinatorlayout;
    @BindView(R.id.gameCenterButtom)
    LinearLayout gameCenterButtom;
    @BindView(R.id.GameCenterLinear)
    LinearLayout GameCenterLinear;
    @BindView(R.id.GameCenterScroll)
    ScrollView GameCenterScroll;
    private int TIME = 1000;  //每隔1s执行一次.

    Handler handler = new Handler();

    private int gid;
    private int position;
    private int section;
    private String name;
    List<RecordList> rcs = new ArrayList<RecordList>();
    BettingSync bs = new BettingSync();
    DrawHistoryAdapter drawHistoryAdapter;
    Runnable runnable;

    private Timer timer;
    List<SwitchG> sg = new ArrayList<>();

    private ArrayAdapter<String> adapterType1;
    private ArrayAdapter<String> adapterType2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        Intent intent = getIntent();
        gid = intent.getIntExtra("gid", 0);
        position = intent.getIntExtra("position", 0);
        section = intent.getIntExtra("section", 0);
        name = intent.getStringExtra("name");
        GameCentertitle.setText(name);
        Log.d("GameCenterGid==", gid + "");
        Log.d("GameCenterName==", name + "");
        Log.d("GameCenterPosition==", position + "");
        Log.d("GameCenterSection==", section + "");

        RetrofitService.getInstance().getSwitchGameList(this, gid);
        RetrofitService.getInstance().getBettingSync(this, gid);
        RetrofitService.getInstance().getBettingDrawHistory(this, gid);

        initClick();
        initData();
        initSpinner1();
        ViewPAdapter adapter = new ViewPAdapter(getSupportFragmentManager(), fragmentList, pageTitle);
      /*  vpId.setAdapter(adapter);
        toolbar_tab.setupWithViewPager(vpId);*/
        drawHistoryAdapter = new DrawHistoryAdapter(this);
        GameCenterListView.setAdapter(drawHistoryAdapter);

    }

    private void initSpinner1() {
        SpinnerType1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initSpinnerSelect();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initSpinnerSelect() {
        final List<String> name = new ArrayList<>();
        final List<String> class_code = new ArrayList<>();
        List<SwitchG.SwitchGa> switchGas = sg.get(SpinnerType1.getSelectedItemPosition()).getSwitchGas();
        for (int i1 = 0; i1 < switchGas.size(); i1++) {

            List<SwitchG.SwitchGa.SwitchGam> switchGams1 = switchGas.get(i1).getSwitchGams();
            Log.d("GameCenterSwSize", switchGams1.size() + "");
            if (switchGams1.size() == 0) {
                Log.d("GameCenterSw", switchGams1.toString());
            }
            if (switchGams1.size() != 0) {
                List<SwitchG.SwitchGa.SwitchGam> switchGams = switchGas.get(i1).getSwitchGams();
                for (int i2 = 0; i2 < switchGams.size(); i2++) {
                    name.add((String) switchGams.get(i2).getName3());
                    class_code.add(switchGams.get(i2).getClass_code3());
                }
            } else if (switchGams1.size() == 0) {
                name.add((String) switchGas.get(i1).getName2());
                class_code.add(switchGas.get(i1).getClass_code2());
            }
        }

        adapterType2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterType2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerType2.setAdapter(adapterType2);
        SpinnerType2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String code = class_code.get(position);
                GameCenterLinear.removeAllViews();
                Log.d("Class_Code=", code);
                View inte = new View(GameCenterActivity.this);
                if ("star_5_duplex".equals(code)
                        || "star_2_any_duplex".equals(code)
                        || "star_4_any_duplex".equals(code)
                        || "2min_star_5_duplex".equals(code)
                        || "2min_star_2_any_duplex".equals(code)
                        || "2min_star_4_any_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_5_star, null, false);
                    final LinearLayout LinearWan = (LinearLayout) inte.findViewById(R.id.LinearWan);
                    LinearLayout LinearQian = (LinearLayout) inte.findViewById(R.id.LinearQian);
                    LinearLayout LinearBai = (LinearLayout) inte.findViewById(R.id.LinearBai);
                    LinearLayout LinearShi = (LinearLayout) inte.findViewById(R.id.LinearShi);
                    LinearLayout LinearGe = (LinearLayout) inte.findViewById(R.id.LinearGe);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    LinearLayout select_4 = (LinearLayout) inte.findViewById(R.id.select_4);
                    LinearLayout select_5 = (LinearLayout) inte.findViewById(R.id.select_5);

                    select_1.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int i1 = 1; i1 < LinearWan.getChildCount(); i1++) {
                                ((CheckBox) LinearWan.getChildAt(i1)).setChecked(true);
                            }
                        }
                    });
                    select_1.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int i1 = 1; i1 < LinearWan.getChildCount(); i1++) {
                                ((CheckBox) LinearWan.getChildAt(i1)).setChecked(false);
                            }
                            for (int i1 = 6; i1 < LinearWan.getChildCount(); i1++) {
                                ((CheckBox) LinearWan.getChildAt(i1)).setChecked(true);
                            }
                        }
                    });
                    select_1.getChildAt(2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int i1 = 1; i1 < LinearWan.getChildCount(); i1++) {
                                ((CheckBox) LinearWan.getChildAt(i1)).setChecked(false);
                            }
                            for (int i1 = 1; i1 < 6; i1++) {
                                ((CheckBox) LinearWan.getChildAt(i1)).setChecked(true);
                            }
                        }
                    });

                }
                if ("star_5_single".equals(code)
                        || "star_4_single".equals(code)
                        || "star_3_next_single".equals(code)
                        || "star_3_next_group_single".equals(code)
                        || "star_3_next_group_single_6".equals(code)
                        || "star_3_next_group_diverse".equals(code)
                        || "star_3_prev_single".equals(code)
                        || "star_3_prev_group_single".equals(code)
                        || "star_3_prev_group_single_6".equals(code)
                        || "star_3_prev_group_diverse".equals(code)
                        || "star_3_midd_single".equals(code)
                        || "star_3_midd_group_single".equals(code)
                        || "star_3_midd_group_single_6".equals(code)
                        || "star_3_midd_group_diverse".equals(code)
                        || "star_2_next_single".equals(code)
                        || "star_2_next_group_single".equals(code)
                        || "star_2_prev_single".equals(code)
                        || "star_2_prev_group_single".equals(code)
                        || "star_3_any_duplex".equals(code)
                        || "eleven_star_3_prev_single".equals(code)
                        || "eleven_star_3_prev_group_single".equals(code)
                        || "eleven_star_2_prev_single".equals(code)
                        || "eleven_star_2_prev_group_single".equals(code)
                        || "PK10_1st_2nd_single".equals(code)
                        || "PK10_1st_2nd_3th_single".equals(code)
                        || "k3_double_single".equals(code)
                        || "k3_different_3_single".equals(code)
                        || "k3_different_2_single".equals(code)
                        || "sequence_star_3_single".equals(code)
                        || "sequence_star_3_group_3_single".equals(code)
                        || "sequence_star_3_group_6_single".equals(code)
                        || "sequence_star_3_group_diverse".equals(code)
                        || "sequence_star_2_prev_single".equals(code)
                        || "sequence_star_2_next_single".equals(code)
                        || "sequence_star_2_prev_group_single".equals(code)
                        || "sequence_star_2_next_group_single".equals(code)
                        || "3D_star_3_single".equals(code)
                        || "3D_star_3_group_3_single".equals(code)
                        || "3D_star_3_group_6_single".equals(code)
                        || "3D_star_3_group_diverse".equals(code)
                        || "3D_star_2_prev_single".equals(code)
                        || "3D_star_2_next_single".equals(code)
                        || "3D_star_2_prev_group_single".equals(code)
                        || "3D_star_2_next_group_single".equals(code)

                        || "eleven_any_one_single".equals(code)
                        || "eleven_any_two_single".equals(code)
                        || "eleven_any_three_single".equals(code)
                        || "eleven_any_four_single".equals(code)
                        || "eleven_any_five_single".equals(code)
                        || "eleven_any_six_single".equals(code)
                        || "eleven_any_seven_single".equals(code)
                        || "eleven_any_eight_single".equals(code)


                        || "2min_star_5_single".equals(code)
                        || "2min_star_4_single".equals(code)
                        || "2min_star_3_next_single".equals(code)
                        || "2min_star_3_next_group_single".equals(code)
                        || "2min_star_3_next_group_single_6".equals(code)
                        || "2min_star_3_next_group_diverse".equals(code)
                        || "2min_star_3_prev_single".equals(code)
                        || "2min_star_3_prev_group_single".equals(code)
                        || "2min_star_3_prev_group_single_6".equals(code)
                        || "2min_star_3_prev_group_diverse".equals(code)
                        || "2min_star_3_midd_single".equals(code)
                        || "2min_star_3_midd_group_single".equals(code)
                        || "2min_star_3_midd_group_single_6".equals(code)
                        || "2min_star_3_midd_group_diverse".equals(code)
                        || "2min_star_2_next_single".equals(code)
                        || "2min_star_2_next_group_single".equals(code)
                        || "2min_star_2_prev_single".equals(code)
                        || "2min_star_2_prev_group_single".equals(code)
                        || "2min_star_3_any_duplex".equals(code)


                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2, null, false);
                    final EditText g2_editText = (EditText) inte.findViewById(R.id.g2_EditText);
                    final TextView num0 = (TextView) inte.findViewById(R.id.num0);
                    TextView num1 = (TextView) inte.findViewById(R.id.num1);
                    TextView num2 = (TextView) inte.findViewById(R.id.num2);
                    TextView num3 = (TextView) inte.findViewById(R.id.num3);
                    TextView num4 = (TextView) inte.findViewById(R.id.num4);
                    TextView num5 = (TextView) inte.findViewById(R.id.num5);
                    TextView num6 = (TextView) inte.findViewById(R.id.num6);
                    TextView num7 = (TextView) inte.findViewById(R.id.num7);
                    TextView num8 = (TextView) inte.findViewById(R.id.num8);
                    TextView num9 = (TextView) inte.findViewById(R.id.num9);
                    TextView numd = (TextView) inte.findViewById(R.id.numd);
                    TextView numDelete = (TextView) inte.findViewById(R.id.numDelete);
                    g2_editText.setInputType(InputType.TYPE_NULL);

                    num0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString().trim() + "0");
                        }
                    });
                    num1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString().trim() + "1");
                        }
                    });
                    num2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString().trim() + "2");
                        }
                    });
                    num3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString().trim() + "3");
                        }
                    });
                    num4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString().trim() + "4");
                        }
                    });
                    num5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString().trim() + "5");
                        }
                    });
                    num6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString().trim() + "6");
                        }
                    });
                    num7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString().trim() + "7");
                        }
                    });
                    num8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString().trim() + "8");
                        }
                    });
                    num9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString().trim() + "9");
                        }
                    });
                    numd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString().trim();

                            if (!",".equals(text.substring(text.length() - 1))) {
                                g2_editText.setText(g2_editText.getText().toString().trim() + ",");
                            }
                        }
                    });
                    numDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString().trim();
                            if (text.length() > 0) {
                                text = text.substring(0, text.length() - 1);
                                g2_editText.setText(text);
                            }
                        }
                    });
                }
                if ("star_5_group_120".equals(code) || "2min_star_5_group_120".equals(code)) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_120, null, false);
                }
                if (
                        "star_5_group_60".equals(code)
                                || "star_5_group_30".equals(code) || "2min_star_5_group_60".equals(code)
                                || "2min_star_5_group_30".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_60_30, null, false);
                }
                if (
                        "star_5_group_20".equals(code)
                                || "2min_star_5_group_20".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_20, null, false);
                }
                if (
                        "star_5_group_10".equals(code)
                                || "2min_star_5_group_10".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_10, null, false);
                }
                if (
                        "star_5_group_5".equals(code)
                                || "2min_star_5_group_5".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_5, null, false);
                }
                if (
                        "star_5_one".equals(code)
                                || "star_5_two".equals(code)
                                || "star_5_three".equals(code)
                                || "star_5_four".equals(code) || "2min_star_5_one".equals(code)
                                || "2min_star_5_two".equals(code)
                                || "2min_star_5_three".equals(code)
                                || "2min_star_5_four".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g4, null, false);
                    TextView g4_name = (TextView) inte.findViewById(R.id.g4_name);
                    g4_name.setText(name.get(position));
                }
                if (
                        "star_4_duplex".equals(code)
                                || "2min_star_4_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_4_star_after, null, false);
                }
                if (
                        "star_4_group_24".equals(code)
                                || "2min_star_4_group_24".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_4_star_after_24, null, false);
                }
                if (
                        "star_4_group_12".equals(code)
                                || "2min_star_4_group_12".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_4_star_after_12, null, false);
                }
                if (
                        "star_4_group_6".equals(code)
                                || "2min_star_4_group_6".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_4_star_after_6, null, false);
                }
                if ("star_4_group_4".equals(code) || "2min_star_4_group_4".equals(code)) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_4_star_after_4, null, false);
                }
                if ("star_3_next_duplex".equals(code)
                        || "2min_star_3_next_duplex".equals(code)
                        || "3D_star_3_duplex".equals(code)
                        || "3D_fix".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_after, null, false);
                }
                if (
                        "star_3_next_sum".equals(code)
                                || "star_3_prev_sum".equals(code)
                                || "star_3_midd_sum".equals(code) || "2min_star_3_next_sum".equals(code)
                                || "2min_star_3_prev_sum".equals(code)
                                || "2min_star_3_midd_sum".equals(code)
                                || "3D_star_3_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_he, null, false);
                }
                if (
                        "star_3_next_sub".equals(code)
                                || "star_3_prev_sub".equals(code)
                                || "star_3_midd_sub".equals(code)
                                || "star_2_next_sub".equals(code)
                                || "star_2_prev_sub".equals(code) || "2min_star_3_next_sub".equals(code)
                                || "2min_star_3_prev_sub".equals(code)
                                || "2min_star_3_midd_sub".equals(code)
                                || "2min_star_2_next_sub".equals(code)
                                || "2min_star_2_prev_sub".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_kua, null, false);
                }
                if (
                        "star_3_next_group_duplex".equals(code)
                                || "star_3_next_group_duplex_6".equals(code)
                                || "star_3_prev_group_duplex".equals(code)
                                || "star_3_prev_group_duplex_6".equals(code)
                                || "star_3_midd_group_duplex".equals(code)
                                || "star_3_midd_group_duplex_6".equals(code)
                                || "star_2_next_sub".equals(code)
                                || "star_2_prev_group_duplex".equals(code)
                                || "star_3_next_one_no_fix".equals(code)
                                || "star_3_prev_one_no_fix".equals(code)
                                || "star_3_next_two_no_fix".equals(code)
                                || "star_3_prev_two_no_fix".equals(code)
                                || "star_4_one_no_fix".equals(code)
                                || "star_4_two_no_fix".equals(code)
                                || "star_5_two_no_fix".equals(code)
                                || "star_5_three_no_fix".equals(code)

                                || "2min_star_3_next_group_duplex".equals(code)
                                || "2min_star_3_next_group_duplex_6".equals(code)
                                || "2min_star_3_prev_group_duplex".equals(code)
                                || "2min_star_3_prev_group_duplex_6".equals(code)
                                || "2min_star_3_midd_group_duplex".equals(code)
                                || "2min_star_3_midd_group_duplex_6".equals(code)
                                || "2min_star_2_next_sub".equals(code)
                                || "2min_star_2_prev_group_duplex".equals(code)
                                || "2min_star_3_next_one_no_fix".equals(code)
                                || "2min_star_3_prev_one_no_fix".equals(code)
                                || "2min_star_3_next_two_no_fix".equals(code)
                                || "2min_star_3_prev_two_no_fix".equals(code)
                                || "2min_star_4_one_no_fix".equals(code)
                                || "2min_star_4_two_no_fix".equals(code)
                                || "2min_star_5_two_no_fix".equals(code)
                                || "2min_star_5_three_no_fix".equals(code)


                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g4, null, false);
                    TextView g4_name = (TextView) inte.findViewById(R.id.g4_name);
                    g4_name.setText(name.get(position));
                }
                if (
                        "star_3_next_group_sum".equals(code)
                                || "star_3_prev_group_sum".equals(code)
                                || "star_3_midd_group_sum".equals(code)
                                || "2min_star_3_next_group_sum".equals(code)
                                || "2min_star_3_prev_group_sum".equals(code)
                                || "2min_star_3_midd_group_sum".equals(code)
                                || "3D_star_3_group_sum".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_3_star_he, null, false);
                }
                if (
                        "star_3_next_group_any".equals(code)
                                || "star_3_prev_group_any".equals(code)
                                || "star_3_midd_group_any".equals(code)
                                || "star_2_next_group_any".equals(code)
                                || "star_2_prev_group_any".equals(code)

                                || "2min_star_3_next_group_any".equals(code)
                                || "2min_star_3_prev_group_any".equals(code)
                                || "2min_star_3_midd_group_any".equals(code)
                                || "2min_star_2_next_group_any".equals(code)
                                || "2min_star_2_prev_group_any".equals(code)


                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_3_star_after_baodan, null, false);
                }
                if (
                        "star_3_prev_duplex".equals(code)
                                || "2min_star_3_prev_duplex".equals(code)
                                || "sequence_star_3_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_before, null, false);
                }
                if (
                        "star_3_midd_duplex".equals(code)
                                || "2min_star_3_midd_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_mid, null, false);
                }
                if (
                        "star_2_next_duplex".equals(code)
                                || "2min_star_2_next_duplex".equals(code)
                                || "sequence_star_2_next_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_after, null, false);
                }
                if (
                        "star_2_next_sum".equals(code)
                                || "2min_star_2_next_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_he1, null, false);
                }
                if (
                        "star_2_next_group_sum".equals(code)
                                || "star_2_prev_group_sum".equals(code)
                                || "2min_star_2_next_group_sum".equals(code)
                                || "2min_star_2_prev_group_sum".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_2_star_he2, null, false);
                }
                if (
                        "star_2_prev_duplex".equals(code)
                                || "2min_star_2_prev_duplex".equals(code)
                                || "sequence_star_2_prev_duplex".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_before, null, false);
                }
                if (
                        "star_2_prev_sum".equals(code)
                                || "2min_star_2_prev_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_he1, null, false);
                }
                if (
                        "fix".equals(code)
                                || "2min_fix".equals(code)
                                || "sequence_fix".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_location, null, false);
                }
                if (
                        "star_2_any_duplex".equals(code)
                                || "2min_star_2_any_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_location, null, false);
                }
                if (
                        "star_2_any_single".equals(code)
                                || "2min_star_2_any_single".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_optional2, null, false);
                }
                if (
                        "star_2_any_sum".equals(code)
                                || "2min_star_2_any_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_optional2_he, null, false);
                }
                if (
                        "star_2_any_group_duplex".equals(code)
                                || "2min_star_2_any_group_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional2_fu_3_6, null, false);
                }
                if (
                        "star_2_any_group_single".equals(code)
                                || "2min_star_2_any_group_single".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional3_dan_3_6_hun, null, false);
                }
                if (
                        "star_2_any_group_sum".equals(code)
                                || "2min_star_2_any_group_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional2_he, null, false);
                }
                if (
                        "star_3_any_single".equals(code)
                                || "2min_star_3_any_single".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_optional3_dan, null, false);
                }
                if (
                        "star_3_any_sum".equals(code)
                                || "2min_star_3_any_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_optional3_he, null, false);
                }
                if (
                        "star_3_any_group_duplex".equals(code)
                                || "star_3_any_group_duplex_6".equals(code) || "2min_star_3_any_group_duplex".equals(code)
                                || "2min_star_3_any_group_duplex_6".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional3_fu_3_6, null, false);
                }
                if (
                        "star_3_any_group_single".equals(code)
                                || "star_3_any_group_single_6".equals(code)
                                || "star_3_any_group_diverse".equals(code)
                                || "2min_star_3_any_group_single".equals(code)
                                || "2min_star_3_any_group_single_6".equals(code)
                                || "2min_star_3_any_group_diverse".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional3_dan, null, false);
                }
                if (
                        "star_3_any_group_sum".equals(code)
                                || "2min_star_3_any_group_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional3_he, null, false);
                }
                if (
                        "star_4_any_single".equals(code)
                                || "2min_star_4_any_single".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_optional4_dan, null, false);
                }
                if (
                        "star_4_any_group_24".equals(code)
                                || "star_4_any_group_6".equals(code)
                                || "2min_star_4_any_group_24".equals(code)
                                || "2min_star_4_any_group_6".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional4_24_6, null, false);
                }
                if (
                        "star_4_any_group_12".equals(code)
                                || "2min_star_4_any_group_12".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional4_12, null, false);
                }
                if (
                        "star_4_any_group_4".equals(code)
                                || "2min_star_4_any_group_4".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional4_4, null, false);
                }
                if (
                        "star_2_prev_special".equals(code)
                                || "2min_star_2_prev_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_2_before, null, false);
                }
                if (
                        "star_2_next_special".equals(code)
                                || "2min_star_2_next_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_2_after, null, false);
                }
                if (
                        "star_3_prev_special".equals(code)
                                || "2min_star_3_prev_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_3_before, null, false);
                }
                if (
                        "star_3_next_special".equals(code)
                                || "2min_star_3_next_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_3_after, null, false);
                }
                if (
                        "banker_player".equals(code)
                                || "2min_banker_player".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_ldle, null, false);
                }
                if (
                        "equal".equals(code)
                                || "2min_equal".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_flat, null, false);
                }
                if (
                        "same_two".equals(code)
                                || "2min_same_two".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_sub, null, false);
                }
                if (
                        "same_three".equals(code)
                                || "2min_same_three".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_leopard, null, false);
                }
                if (
                        "heavenly_king".equals(code)
                                || "2min_heavenly_king".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_king, null, false);
                }
                if (
                        "sum_special".equals(code)
                                || "2min_sum_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_sum_big_dan, null, false);
                }
                if (
                        "toradora_wq".equals(code)
                                || "toradora_wb".equals(code)
                                || "toradora_ws".equals(code)
                                || "toradora_wg".equals(code)
                                || "toradora_qb".equals(code)
                                || "toradora_qs".equals(code)
                                || "toradora_qg".equals(code)
                                || "toradora_bs".equals(code)
                                || "toradora_bg".equals(code)
                                || "toradora_sg".equals(code)
                                || "2min_toradora_wq".equals(code)
                                || "2min_toradora_wb".equals(code)
                                || "2min_toradora_ws".equals(code)
                                || "2min_toradora_wg".equals(code)
                                || "2min_toradora_qb".equals(code)
                                || "2min_toradora_qs".equals(code)
                                || "2min_toradora_qg".equals(code)
                                || "2min_toradora_bs".equals(code)
                                || "2min_toradora_bg".equals(code)
                                || "2min_toradora_sg".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_dragon_tiger, null, false);
                }
                if (
                        "eleven_star_3_prev_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_11_5_zhi_fu, null, false);
                }
                if (
                        "eleven_star_3_prev_group_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_11_5_zu_fu, null, false);
                }
                if (
                        "eleven_star_2_prev_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_11_5_zhi_fu, null, false);
                }
                if (
                        "eleven_star_2_prev_group_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_11_5_zu_fu, null, false);
                }
                if (
                        "eleven_star_3_prev_no_fix".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_nolocation_11_5, null, false);
                }
                if (
                        "eleven_fix".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_11_5_zhi_fu, null, false);
                }
                if (
                        "eleven_even_or_odd".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_11_5_single_double, null, false);
                }
                if (
                        "eleven_middle".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_11_5_caizhong, null, false);
                }
                if (
                        "eleven_any_one_duplex".equals(code)
                                || "eleven_any_two_duplex".equals(code)
                                || "eleven_any_three_duplex".equals(code)
                                || "eleven_any_four_duplex".equals(code)
                                || "eleven_any_five_duplex".equals(code)
                                || "eleven_any_six_duplex".equals(code)
                                || "eleven_any_seven_duplex".equals(code)
                                || "eleven_any_eight_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_11_5_optional_fu, null, false);
                    TextView names = (TextView) inte.findViewById(R.id.name);
                    names.setText(name.get(position));
                }
                if (
                        "PK10_1st_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_champion1, null, false);

                }
                if (
                        "PK10_1st_2nd_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_champion2_fu, null, false);

                }
                if (
                        "PK10_1st_2nd_3th_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_champion3_fu, null, false);

                }
                if (
                        "PK10_1to5_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_location_1_5, null, false);

                }
                if (
                        "PK10_6to10_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_location_6_10, null, false);

                }
                if (
                        "PK10_1to10_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_location_1_10, null, false);

                }
                if (
                        "PK10_1st_special".equals(code)
                                || "PK10_2st_special".equals(code)
                                || "PK10_3st_special".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_big_small, null, false);
                    TextView names = (TextView) inte.findViewById(R.id.name);
                    names.setText(name.get(position));

                }
                if (
                        "PK10_1st_odd_even".equals(code)
                                || "PK10_2nd_odd_even".equals(code)
                                || "PK10_3th_odd_even".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_dan_shuang, null, false);
                    TextView names = (TextView) inte.findViewById(R.id.name);
                    names.setText(name.get(position));

                }
                if (
                        "PK10_1st_2nd_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_champion2_he, null, false);
                }
                if (
                        "PK10_1st_2nd_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_beijing10_big_small_dan_shuang, null, false);
                }
                if (
                        "PK10_toradora".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_dragon_tiger, null, false);
                }
                if (
                        "k3_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_he, null, false);
                }
                if (
                        "k3_triple_all".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_same_tong, null, false);
                }
                if (
                        "k3_triple".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_same_dan, null, false);
                }
                if (
                        "k3_double_simple".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_2_same_fu, null, false);
                }
                if (
                        "k3_double_standard".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_2_same_bzxh, null, false);
                }
                if (
                        "k3_different_3_standard".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_nosame_num_bzxh, null, false);
                }
                if (
                        "k3_different_3_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_nosame_num_hzxh, null, false);
                }
                if (
                        "k3_different_2_standard".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_nosame_num_bzxh, null, false);
                }
                if (
                        "k3_consecutives_3_all".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_same_tong, null, false);
                }
                if (
                        "kl8_sum_even_odd".equals(code)
                                || "kl8_sum_even_odd_2".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_quwei_dan_shuang, null, false);
                }
                if (
                        "kl8_sum_max_min".equals(code)
                                || "kl8_sum_max_min_2".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_quwei_big_small, null, false);
                }
                if (
                        "kl8_parity_disk".equals(code)
                                || "kl8_parity_disk_2".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_quwei_ji_ou, null, false);
                }
                if (
                        "kl8_up_down".equals(code)
                                || "kl8_up_down_2".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_quwei_top_down, null, false);
                }
                if (
                        "kl8_special".equals(code)
                                || "kl8_special_2".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_quwei_he, null, false);
                }
                if (
                        "kl8_any_one".equals(code)
                                || "kl8_any_two".equals(code)
                                || "kl8_any_three".equals(code)
                                || "kl8_any_four".equals(code)
                                || "kl8_any_five".equals(code)
                                || "kl8_any_six".equals(code)
                                || "kl8_any_seven".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_optional1234567, null, false);
                }
                if (
                        "kl8_five_elements".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_wuxing, null, false);
                }
                if (
                        "sequence_star_3_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_5_3star_he, null, false);
                }
                if (
                        "sequence_star_3_group_3_duplex".equals(code)
                                || "3D_star_3_group_3_duplex".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_3_5_3star_33, null, false);

                }
                if (
                        "sequence_star_3_group_6_duplex".equals(code)
                                || "3D_star_3_group_6_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_3_5_3star_36, null, false);

                }
                if (
                        "sequence_star_3_group_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_3_5_3star_he, null, false);

                }
                if (
                        "sequence_star_2_prev_group_duplex".equals(code)
                                || "sequence_star_2_next_group_duplex".equals(code)
                                || "3D_star_2_prev_group_duplex".equals(code)
                                || "3D_star_2_next_group_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g4, null, false);
                    TextView names = (TextView) inte.findViewById(R.id.g4_name);
                    names.setText("组选");
                }
                if (
                        "sequence_choose_1_no_fix".equals(code)
                                || "3D_choose_1_no_fix".equals(code)
                                || "3D_choose_2_no_fix".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_5_nolocation1, null, false);

                }
                if (
                        "sequence_choose_2_no_fix".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_5_nolocation2, null, false);

                }
                if (
                        "sequence_prev_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_5_before2_big_small, null, false);
                }
                if (
                        "sequence_next_special".equals(code)
                                || "3D_prev_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_5_after2_big_small, null, false);
                }
                if (
                        "3D_star_2_prev_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3d_2_star_before, null, false);
                }
                if (
                        "3D_star_2_next_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3d_2_star_after, null, false);
                }
                if (
                        "3D_next_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3d2_big_small, null, false);
                }
                if (
                        "lhc_orthocode".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_zheng, null, false);
                }
                if (
                        "lhc_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_te, null, false);
                }
                if (
                        "lhc_special_max_min".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_big_small, null, false);
                }
                if (
                        "lhc_special_odd_even".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_dan_shuang, null, false);
                }
                if (
                        "lhc_special_max_min_odd_even".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_big_small_dan_shuang, null, false);
                }
                if (
                        "lhc_special_sum_max_min".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_he_big_small, null, false);
                }
                if (
                        "lhc_special_sum_odd_even".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_he_dan_shuang, null, false);
                }
                if (
                        "lhc_special_last_max_min".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_wei_big_small, null, false);
                }
                if (
                        "lhc_special_zodiacs".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_wei_big_zodiac, null, false);
                }
                if (
                        "lhc_special_color".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_tb, null, false);
                }
                if (
                        "lhc_special_color_max_min".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_tb_big_small, null, false);
                }
                if (
                        "lhc_special_color_odd_even".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_tb_dan_shuang, null, false);
                }
                if (
                        "lhc_special_color_max_min_odd_even".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_tb_big_small_dan_shuang, null, false);
                }
                GameCenterLinear.addView(inte);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initClick() {
        Back.setOnClickListener(this);
    }

    private void initData() {
        fragmentList.add(FindFragment.newInstance());
        fragmentList.add(FindFragment.newInstance());
        fragmentList.add(FindFragment.newInstance());
        pageTitle.add("QQ");
        pageTitle.add("WW");
        pageTitle.add("EE");


    }

    private void initTimer() {
        timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                RetrofitService.getInstance().getBettingWinningNum(GameCenterActivity.this, gid, bs.getPeriod());
                // RetrofitService.getInstance().getTokenSignin(MainActivity.this);

            }
        };

        timer.schedule(timerTask, 0, 1000);

    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_HISTORYGAME) {
            if (object != null) {
                rcs = (List<RecordList>) object;
                drawHistoryAdapter.addList(rcs);

            }
        }
        if (apiId == RetrofitService.API_ID_BETTINGSYNC) {
            if (object != null) {
                bs = (BettingSync) object;
                final int[] anInt = {RxUtils.getInstance().getInt(bs.getCondownTime())};
                textPeriod.setText(bs.getPeriod());
                textTime.setText(RxUtils.getInstance().getSecToTime(RxUtils.getInstance().getInt(bs.getCondownTime())));
                GameCenterProgressBar.setMax(anInt[0]);
                final int[] anInt1 = {RxUtils.getInstance().getInt(bs.getCondownTime())};
                anInt1[0] = anInt1[0] - 1;
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler.postDelayed(this, TIME);
                            GameCenterProgressBar.setProgress(anInt[0]--);
                            int i = anInt1[0]--;
                            String secToTime = RxUtils.getInstance().getSecToTime(i);
                            textTime.setText(secToTime);
                            Log.i("GameCentprint", anInt[0] + "");
                            Log.i("GameCentTime", secToTime + "");
                            if (anInt[0] < 0) {
                                handler.removeCallbacks(runnable);
                                Toast toast = Toasty.warning(GameCenterActivity.this, "第" + bs.getPeriod() + "期开奖中", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                initTimer();
                                //  RetrofitService.getInstance().getBettingDrawHistory(GameCenterActivity.this, gid);
                                //  RetrofitService.getInstance().getBettingSync(GameCenterActivity.this, gid);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                handler.postDelayed(runnable, TIME); // 在初始化方法里.
            }
        }
        if (apiId == RetrofitService.API_ID_BETTINGWINNUM) {
            if (timer != null) {
                timer.cancel();
            }
            RetrofitService.getInstance().getBettingSync(this, gid);
            RetrofitService.getInstance().getBettingDrawHistory(this, gid);

           /* if (object!=null) {
                BettingSync bettingSync= (BettingSync) object;
                bettingSync
            }*/
        }
        if (apiId == RetrofitService.API_ID_SWITCHGAME) {
            if (object != null) {
                sg = (List<SwitchG>) object;
                List<String> sgName = new ArrayList<>();
                for (int i = 0; i < sg.size(); i++) {
                    sgName.add((String) sg.get(i).getName1());
                }
                adapterType1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sgName);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapterType1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                SpinnerType1.setAdapter(adapterType1);
                initSpinner1();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:

                finish();
                break;
        }
    }

    class ViewPAdapter extends FragmentPagerAdapter {
        List<FindFragment> fragmentList = new ArrayList<>();
        List<String> arg1 = new ArrayList<>();

        public ViewPAdapter(FragmentManager fm, List<FindFragment> frameLayoutList, List<String> aa) {
            super(fm);
            this.fragmentList = frameLayoutList;
            arg1 = aa;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return arg1.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        if (timer != null) {
            timer.cancel();
        }
    }
}