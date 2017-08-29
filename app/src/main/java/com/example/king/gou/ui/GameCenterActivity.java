package com.example.king.gou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.DrawHistoryAdapter;
import com.example.king.gou.bean.Arrays;
import com.example.king.gou.bean.BettingSync;
import com.example.king.gou.bean.Ids;
import com.example.king.gou.bean.RecordList;
import com.example.king.gou.bean.SwitchG;
import com.example.king.gou.fragment.FindFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.gameAcVpFrms.GameCartActivity;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static com.example.king.gou.R.id.wan;


public class GameCenterActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener, OnItemClickListener {


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
    NestedScrollView GameCenterScroll;
    @BindView(R.id.cut)
    TextView cut;
    @BindView(R.id.edit1)
    TextView edit1;
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.radion1)
    RadioButton radion1;
    @BindView(R.id.radion2)
    RadioButton radion2;
    @BindView(R.id.radion3)
    RadioButton radion3;
    @BindView(R.id.radion4)
    RadioButton radion4;
    @BindView(R.id.SpinnerMoney)
    Spinner SpinnerMoney;
    @BindView(R.id.GameCentertitle)
    TextView GameCentertitle;
    @BindView(R.id.AddGameNumBtn)
    RelativeLayout AddGameNumBtn;
    @BindView(R.id.SendGameNum)
    TextView SendGameNum;
    @BindView(R.id.RadioGroupGameCenter)
    RadioGroup RadioGroupGameCenter;
    @BindView(R.id.ToGameCert)
    RelativeLayout ToGameCert;

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
    private AlertView alertView;
    // 一个自定义的布局，作为显示的内容
    View contentView;
    View inte;
    String code;
    String GameTypeName = null;
    int PriceUnit = 1;
    List<Ids> listIds = new ArrayList<>();

    String pickedNumber = "";//投注内容，如果是单式选号，需要过滤掉不合法的投注内容
    String pickedText = "";//投注文本，大部分时候等于pickedNumber，只有有中文的投注，才会不一样，如果相同，提交的时候为了节省提交数据大小，可以传空字符串，单式加密的时候必须是原内容
    String location = "";//数字字符串，五个0或1组成，代表时时彩或分分彩任选玩法勾选的位置，0代表不勾选，1代表勾选
    String locationText = "";//由"万千百十个"五个字其中几个组成，代表勾选的位置的文本
    int Nums;//投注内容注数
    String classCode = null;//玩法编码
    int priceUnit = PriceUnit;//投注模式
    //1：元；对应单注单价为￥2.000
    //    2：角；对应单注单价为￥0.200
    //  3：分；对应单注单价为￥0.020
    //  4：厘；对应单注单价为￥0.002
    int priceType = 0;//奖金类型
    //0：最低奖
    //1：最高奖
    double amount = 0;//投注金额；计算方法为：单价[2/0.2/0.02/0.002] * 注数[num] * 倍数[multiple]
    int multiple = 0;//投注倍数
    double amounts = 0;//总投注金额，大都等于amount
    int multiples = 0;//投注总倍数，大都等于multiple
    String vcode = "";//每一单投注单加密秘钥
    int nums = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        inte = new View(GameCenterActivity.this);
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
        alertView = new AlertView(null, null, "取消", new String[]{"确认添加"}, null, this, AlertView.Style.Alert, this);
        contentView = LayoutInflater.from(this).inflate(
                R.layout.item_add_sendgame, null);
        alertView.addExtView(contentView);

        RetrofitService.getInstance().getSwitchGameList(this, gid);
        RetrofitService.getInstance().getBettingSync(this, gid);
        RetrofitService.getInstance().getBettingDrawHistory(this, gid);
        initClick();
        initSpinner1();
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

    public void Select9(LinearLayout view1, final LinearLayout view2) {
        for (int i = 0; i < view1.getChildCount(); i++) {
            final int finalI = i;
            view1.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int start = 0;
                    int length = 0;
                    if (finalI == 0) {
                        start = 1;
                        length = view2.getChildCount();
                        for (int i1 = start; i1 < length; i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(true);
                        }
                    }
                    if (finalI == 1) {
                        for (int i1 = 1; i1 < view2.getChildCount(); i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(false);
                        }
                        start = 6;
                        length = view2.getChildCount();
                        for (int i1 = start; i1 < length; i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(true);
                        }
                    }
                    if (finalI == 2) {
                        for (int i1 = 1; i1 < view2.getChildCount(); i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(false);
                        }
                        start = 1;
                        length = 6;
                        for (int i1 = start; i1 < length; i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(true);
                        }
                    }

                    if (finalI == 3) {
                        for (int i1 = 1; i1 < view2.getChildCount(); i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(false);
                        }
                        CheckBox cs = (CheckBox) view2.getChildAt(1);
                        if ("01".equals(cs.getText().toString().trim())) {
                            start = 1;
                        } else {
                            start = 2;
                        }
                        length = view2.getChildCount();
                        for (int i1 = start; i1 < length; i1 = i1 + 2) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(true);
                        }
                    }
                    if (finalI == 4) {
                        for (int i1 = 1; i1 < view2.getChildCount(); i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(false);
                        }
                        CheckBox cs = (CheckBox) view2.getChildAt(1);
                        if ("01".equals(cs.getText().toString().trim())) {
                            start = 2;
                        } else {
                            start = 1;
                        }

                        length = view2.getChildCount();
                        for (int i1 = start; i1 < length; i1 = i1 + 2) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(true);
                        }
                    }
                    if (finalI == 5) {
                        for (int i1 = 1; i1 < view2.getChildCount(); i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(false);
                        }
                    }
                }
            });
        }

    }

    public void Select10(LinearLayout view1, final LinearLayout view2) {
        for (int i = 0; i < view1.getChildCount(); i++) {
            final int finalI = i;
            view1.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int start = 0;
                    int length = 0;
                    if (finalI == 0) {
                        start = 1;
                        length = view2.getChildCount();
                        for (int i1 = start; i1 < length; i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(true);
                        }
                    }
                    if (finalI == 1) {
                        for (int i1 = 1; i1 < view2.getChildCount(); i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(false);
                        }
                        start = 6;
                        length = view2.getChildCount();
                        for (int i1 = start; i1 < length; i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(true);
                        }
                    }
                    if (finalI == 2) {
                        for (int i1 = 1; i1 < view2.getChildCount(); i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(false);
                        }
                        start = 1;
                        length = 6;
                        for (int i1 = start; i1 < length; i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(true);
                        }
                    }
                    if (finalI == 3) {
                        for (int i1 = 1; i1 < view2.getChildCount(); i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(false);
                        }
                        start = 1;
                        length = view2.getChildCount();
                        for (int i1 = start; i1 < length; i1 = i1 + 2) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(true);
                        }
                    }
                    if (finalI == 4) {
                        for (int i1 = 1; i1 < view2.getChildCount(); i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(false);
                        }
                        start = 2;
                        length = view2.getChildCount();
                        for (int i1 = start; i1 < length; i1 = i1 + 2) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(true);
                        }
                    }
                    if (finalI == 5) {
                        for (int i1 = 1; i1 < view2.getChildCount(); i1++) {
                            ((CheckBox) view2.getChildAt(i1)).setChecked(false);
                        }
                    }
                }
            });
        }

    }

    public void ClearCheckBox(LinearLayout lincheckBox) {
        for (int i = 0; i < lincheckBox.getChildCount(); i++) {
            ((CheckBox) lincheckBox.getChildAt(i)).setChecked(false);
        }
    }

    public void Select80(LinearLayout linear, List<Integer> nums) {

        for (int i = 0; i < nums.size(); i++) {
            Log.d("随机数循环", nums.get(i) + "");
        }

        LinearLayout linearOne = (LinearLayout) linear.getChildAt(0);
        LinearLayout linear1 = (LinearLayout) linearOne.getChildAt(1);
        LinearLayout linearTwo = (LinearLayout) linear.getChildAt(1);
        LinearLayout linear2 = (LinearLayout) linearTwo.getChildAt(1);

        LinearLayout linear11 = (LinearLayout) linear1.getChildAt(0);
        LinearLayout linear12 = (LinearLayout) linear1.getChildAt(1);
        LinearLayout linear13 = (LinearLayout) linear1.getChildAt(2);
        LinearLayout linear14 = (LinearLayout) linear1.getChildAt(3);
        LinearLayout linear15 = (LinearLayout) linear1.getChildAt(4);

        LinearLayout linear21 = (LinearLayout) linear2.getChildAt(0);
        LinearLayout linear22 = (LinearLayout) linear2.getChildAt(1);
        LinearLayout linear23 = (LinearLayout) linear2.getChildAt(2);
        LinearLayout linear24 = (LinearLayout) linear2.getChildAt(3);
        LinearLayout linear25 = (LinearLayout) linear2.getChildAt(4);
        ClearCheckBox(linear11);
        ClearCheckBox(linear12);
        ClearCheckBox(linear13);
        ClearCheckBox(linear14);
        ClearCheckBox(linear15);
        ClearCheckBox(linear21);
        ClearCheckBox(linear22);
        ClearCheckBox(linear23);
        ClearCheckBox(linear24);
        ClearCheckBox(linear25);
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);

            if (num < 9) {
                Log.d("随机数<9", num + "");
                ((CheckBox) linear11.getChildAt(num - 1)).setChecked(true);
            }
            if (num > 8 && num < 17) {
                Log.d("随机数<17", num + "");
                ((CheckBox) linear12.getChildAt(num - 9)).setChecked(true);
            }
            if (num > 16 && num < 25) {
                Log.d("随机数<25", num + "");
                ((CheckBox) linear13.getChildAt(num - 17)).setChecked(true);
            }
            if (num > 24 && num < 33) {
                Log.d("随机数<33", num + "");
                ((CheckBox) linear14.getChildAt(num - 25)).setChecked(true);
            }
            if (num > 32 && num < 41) {
                Log.d("随机数<41", num + "");
                ((CheckBox) linear15.getChildAt(num - 33)).setChecked(true);
            }
            if (num > 40 && num < 49) {
                Log.d("随机数<49", num + "");
                ((CheckBox) linear21.getChildAt(num - 41)).setChecked(true);
            }
            if (num > 48 && num < 57) {
                ((CheckBox) linear22.getChildAt(num - 49)).setChecked(true);
            }
            if (num > 56 && num < 65) {
                Log.d("随机数<65", num + "");
                ((CheckBox) linear23.getChildAt(num - 57)).setChecked(true);
            }
            if (num > 64 && num < 73) {
                Log.d("随机数<73", num + "");
                ((CheckBox) linear24.getChildAt(num - 65)).setChecked(true);
            }
            if (num > 72 && num < 81) {
                Log.d("随机数<81", num + "");
                ((CheckBox) linear25.getChildAt(num - 73)).setChecked(true);
            }
        }
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
                code = class_code.get(position);
                GameTypeName = name.get(position);
              /*  Map<String, Object> map = new HashMap();
                map.put("pickedNumber", "125,268,1,3,4");
                map.put("multiples", 1);
                map.put("locationText", "");
                map.put("priceUnit", PriceUnit);
                map.put("amount", 18);
                map.put("priceType", 0);
                map.put("amounts", 18);
                map.put("pickedText", "");
                map.put("multiple", 1);
                map.put("classCode", code);
                map.put("location", "");
                map.put("num", 9);
                map.put("vcode", "");


                String s = RxUtils.getInstance().randomHexString(6);
                Log.d("16进制==", s);


                String vtext = ";" + "1,2,3,4,5,6" +
                        ";" + "1,2,3,4,5,6" +
                        ";" + "" +
                        ";" + "" +
                        ";" + "10" +
                        ";" + code +
                        ";" + "1" +
                        ";" + "1" +
                        ";" + "20" +
                        ";" + "10" +
                        ";" + "20" +
                        ";" + "10";
                String vcode = RxUtils.getInstance().SHA256(vtext);

                List<Map<String, Object>> ids = new ArrayList<>();
                ids.add(map);
                Map<String, Object> maps = new HashMap<String, Object>();
                maps.put("period", bs.getPeriod());
                maps.put("multiple", 2);
                List<Map<String, Object>> array = new ArrayList<Map<String, Object>>();
                array.add(maps);
                Object[] arrays = new Object[]{maps};
                List<Arrays> arrays1 = new ArrayList<Arrays>();
                Arrays as = new Arrays();
                as.setPeriod(bs.getPeriod());
                as.setMultiple(2);
                arrays1.add(as);
                Gson gson = new Gson();
                String s1 = gson.toJson(arrays1).toString();
                String s2 = gson.toJson(ids);
                Log.d("提交购彩单Array", s1);
                Log.d("提交购彩单Ids", s2);*/
                // RetrofitService.getInstance().getSendBetting(GameCenterActivity.this, gid, "", s2, bs.getPeriod(), "", 0, 0);


                GameCenterLinear.removeAllViews();
                Log.d("Class_Code=", code);

                if ("star_5_duplex".equals(code)
                        || "star_2_any_duplex".equals(code)
                        || "star_4_any_duplex".equals(code)
                        || "2min_star_5_duplex".equals(code)
                        || "2min_star_2_any_duplex".equals(code)
                        || "2min_star_4_any_duplex".equals(code)
                        || "star_3_any_duplex".equals(code)
                        || "2min_star_3_any_duplex".equals(code)
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
                    Select9(select_1, LinearWan);
                    Select9(select_2, LinearQian);
                    Select9(select_3, LinearBai);
                    Select9(select_4, LinearShi);
                    Select9(select_5, LinearGe);
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
                    LinearLayout Linear120 = (LinearLayout) inte.findViewById(R.id.Linear120);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, Linear120);
                }
                if (
                        "star_5_group_60".equals(code)
                                || "star_5_group_30".equals(code) || "2min_star_5_group_60".equals(code)
                                || "2min_star_5_group_30".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_60_30, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                }
                if (
                        "star_5_group_20".equals(code)
                                || "2min_star_5_group_20".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_20, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                }
                if (
                        "star_5_group_10".equals(code)
                                || "2min_star_5_group_10".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_10, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                }
                if (
                        "star_5_group_5".equals(code)
                                || "2min_star_5_group_5".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_5, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                }
                if (
                        "star_4_duplex".equals(code)
                                || "2min_star_4_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_4_star_after, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    LinearLayout LinearFour = (LinearLayout) inte.findViewById(R.id.LinearFour);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    LinearLayout select_4 = (LinearLayout) inte.findViewById(R.id.select_4);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                    Select9(select_3, LinearThree);
                    Select9(select_4, LinearFour);
                }
                if (
                        "star_4_group_24".equals(code)
                                || "2min_star_4_group_24".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_4_star_after_24, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                }
                if (
                        "star_4_group_12".equals(code)
                                || "2min_star_4_group_12".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_4_star_after_12, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                }
                if (
                        "star_4_group_6".equals(code)
                                || "2min_star_4_group_6".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_4_star_after_6, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);

                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);

                    Select9(select_1, LinearOne);
                }
                if ("star_4_group_4".equals(code) || "2min_star_4_group_4".equals(code)) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_4_star_after_4, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                }
                if ("star_3_next_duplex".equals(code)
                        || "2min_star_3_next_duplex".equals(code)
                        || "3D_star_3_duplex".equals(code)
                        || "3D_fix".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_after, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                    Select9(select_3, LinearThree);
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                    Select9(select_3, LinearThree);
                }
                if (
                        "star_3_midd_duplex".equals(code)
                                || "2min_star_3_midd_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_mid, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                    Select9(select_3, LinearThree);
                }
                if (
                        "star_2_next_duplex".equals(code)
                                || "2min_star_2_next_duplex".equals(code)
                                || "sequence_star_2_next_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_after, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
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
                                || "star_2_any_duplex".equals(code)
                                || "2min_star_2_any_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_location, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    LinearLayout LinearFour = (LinearLayout) inte.findViewById(R.id.LinearFour);
                    LinearLayout LinearFive = (LinearLayout) inte.findViewById(R.id.LinearFive);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    LinearLayout select_4 = (LinearLayout) inte.findViewById(R.id.select_4);
                    LinearLayout select_5 = (LinearLayout) inte.findViewById(R.id.select_5);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                    Select9(select_3, LinearThree);
                    Select9(select_4, LinearFour);
                    Select9(select_5, LinearFive);
                }
                /*if (

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_location, null, false);

                }*/
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
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
                    TextView names = (TextView) inte.findViewById(R.id.name);
                    names.setText(name.get(position));

                }
                if (
                        "star_4_any_group_12".equals(code)
                                || "2min_star_4_any_group_12".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional4_12, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                }
                if (
                        "star_4_any_group_4".equals(code)
                                || "2min_star_4_any_group_4".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional4_4, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                    Select9(select_3, LinearThree);
                }
                if (
                        "eleven_star_3_prev_group_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_11_5_zu_fu, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                }
                if (
                        "eleven_star_2_prev_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_11_5_zhi_fu, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                }
                if (
                        "eleven_star_2_prev_group_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_11_5_zu_fu, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                }
                if (
                        "eleven_star_3_prev_no_fix".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_nolocation_11_5, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                }
                if (
                        "eleven_fix".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_11_5_zhi_fu, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                    Select9(select_3, LinearThree);
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);

                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);

                    Select9(select_1, LinearOne);

                }
                if (
                        "PK10_1st_2nd_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_champion2_fu, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                }
                if (
                        "PK10_1st_2nd_3th_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_champion3_fu, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                    Select9(select_3, LinearThree);

                }
                if (
                        "PK10_1to5_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_location_1_5, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    LinearLayout LinearFour = (LinearLayout) inte.findViewById(R.id.LinearFour);
                    LinearLayout LinearFive = (LinearLayout) inte.findViewById(R.id.LinearFive);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    LinearLayout select_4 = (LinearLayout) inte.findViewById(R.id.select_4);
                    LinearLayout select_5 = (LinearLayout) inte.findViewById(R.id.select_5);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                    Select9(select_3, LinearThree);
                    Select9(select_4, LinearFour);
                    Select9(select_5, LinearFive);

                }
                if (
                        "PK10_6to10_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_location_6_10, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    LinearLayout LinearFour = (LinearLayout) inte.findViewById(R.id.LinearFour);
                    LinearLayout LinearFive = (LinearLayout) inte.findViewById(R.id.LinearFive);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    LinearLayout select_4 = (LinearLayout) inte.findViewById(R.id.select_4);
                    LinearLayout select_5 = (LinearLayout) inte.findViewById(R.id.select_5);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                    Select9(select_3, LinearThree);
                    Select9(select_4, LinearFour);
                    Select9(select_5, LinearFive);

                }
                if (
                        "PK10_1to10_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_location_1_10, null, false);
                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.Linear1);
                    LinearLayout Linear2 = (LinearLayout) inte.findViewById(R.id.Linear2);
                    LinearLayout Linear3 = (LinearLayout) inte.findViewById(R.id.Linear3);
                    LinearLayout Linear4 = (LinearLayout) inte.findViewById(R.id.Linear4);
                    LinearLayout Linear5 = (LinearLayout) inte.findViewById(R.id.Linear5);
                    LinearLayout Linear6 = (LinearLayout) inte.findViewById(R.id.Linear6);
                    LinearLayout Linear7 = (LinearLayout) inte.findViewById(R.id.Linear7);
                    LinearLayout Linear8 = (LinearLayout) inte.findViewById(R.id.Linear8);
                    LinearLayout Linear9 = (LinearLayout) inte.findViewById(R.id.Linear9);
                    LinearLayout Linear10 = (LinearLayout) inte.findViewById(R.id.Linear10);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    LinearLayout select_3 = (LinearLayout) inte.findViewById(R.id.select_3);
                    LinearLayout select_4 = (LinearLayout) inte.findViewById(R.id.select_4);
                    LinearLayout select_5 = (LinearLayout) inte.findViewById(R.id.select_5);
                    LinearLayout select_6 = (LinearLayout) inte.findViewById(R.id.select_6);
                    LinearLayout select_7 = (LinearLayout) inte.findViewById(R.id.select_7);
                    LinearLayout select_8 = (LinearLayout) inte.findViewById(R.id.select_8);
                    LinearLayout select_9 = (LinearLayout) inte.findViewById(R.id.select_9);
                    LinearLayout select_10 = (LinearLayout) inte.findViewById(R.id.select_10);
                    Select9(select_1, Linear1);
                    Select9(select_2, Linear2);
                    Select9(select_3, Linear3);
                    Select9(select_4, Linear4);
                    Select9(select_5, Linear5);
                    Select9(select_6, Linear6);
                    Select9(select_7, Linear7);
                    Select9(select_8, Linear8);
                    Select9(select_9, Linear9);
                    Select9(select_10, Linear10);

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
                                || "kl8_parity_disk_2".equals(code)) {
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final LinearLayout Linear = (LinearLayout) inte.findViewById(R.id.Linear);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) Linear.getChildAt(2);
                    LinearThree.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Random rand = new Random();
                            List<Integer> nums = new ArrayList<Integer>();
                            while (nums.size() < 8) {
                                int num = rand.nextInt(40) + 1;
                                if (!nums.contains(num)) {
                                    nums.add(num);
                                }
                            }
                            Select80(Linear, nums);
                        }
                    });
                    LinearThree.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Random rand = new Random();
                            List<Integer> nums = new ArrayList<Integer>();
                            while (nums.size() < 8) {
                                int num = rand.nextInt(80) + 1;
                                if (num % 2 == 1) {
                                    if (!nums.contains(num)) {
                                        nums.add(num);
                                    }
                                }
                            }
                            Select80(Linear, nums);
                        }
                    });
                    LinearThree.getChildAt(2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Random rand = new Random();
                            List<Integer> nums = new ArrayList<Integer>();
                            while (nums.size() < 8) {
                                int num = rand.nextInt(40) + 1;
                                if (num % 2 == 1) {
                                    if (!nums.contains(num)) {
                                        nums.add(num);
                                    }
                                }
                            }
                            Select80(Linear, nums);
                        }
                    });
                    LinearThree.getChildAt(3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Random rand = new Random();
                            List<Integer> nums = new ArrayList<Integer>();
                            while (nums.size() < 8) {
                                int num = rand.nextInt(40) + 1;
                                if (num % 2 == 0) {
                                    if (!nums.contains(num)) {
                                        nums.add(num);
                                    }
                                }
                            }
                            Select80(Linear, nums);
                        }
                    });
                    LinearThree.getChildAt(4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Random rand = new Random();
                            List<Integer> nums = new ArrayList<Integer>();
                            while (nums.size() < 8) {
                                int num = rand.nextInt(80) + 1;
                                if (!nums.contains(num)) {
                                    nums.add(num);
                                }
                            }
                            Select80(Linear, nums);
                        }
                    });
                    LinearThree.getChildAt(5).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Random rand = new Random();
                            List<Integer> nums = new ArrayList<Integer>();
                            while (nums.size() < 8) {
                                int num = rand.nextInt(41) + 41;
                                if (!nums.contains(num)) {
                                    nums.add(num);
                                }
                            }
                            Select80(Linear, nums);
                        }
                    });
                    LinearThree.getChildAt(6).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Random rand = new Random();
                            List<Integer> nums = new ArrayList<Integer>();
                            while (nums.size() < 8) {
                                int num = rand.nextInt(80) + 1;
                                if (num % 2 == 0) {
                                    if (!nums.contains(num)) {
                                        nums.add(num);
                                    }
                                }
                            }
                            Select80(Linear, nums);
                        }
                    });
                    LinearThree.getChildAt(7).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Random rand = new Random();
                            List<Integer> nums = new ArrayList<Integer>();
                            while (nums.size() < 8) {
                                int num = rand.nextInt(41) + 41;
                                if (num % 2 == 1) {
                                    if (!nums.contains(num)) {
                                        nums.add(num);
                                    }
                                }
                            }
                            Select80(Linear, nums);
                        }
                    });
                    LinearThree.getChildAt(8).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Random rand = new Random();
                            List<Integer> nums = new ArrayList<Integer>();
                            while (nums.size() < 8) {
                                int num = rand.nextInt(41) + 41;
                                if (num % 2 == 0) {
                                    if (!nums.contains(num)) {
                                        nums.add(num);
                                    }
                                }
                            }
                            Select80(Linear, nums);
                        }
                    });
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);

                }
                if (
                        "sequence_star_3_group_6_duplex".equals(code)
                                || "3D_star_3_group_6_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_3_5_3star_36, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                }
                if (
                        "sequence_choose_2_no_fix".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_5_nolocation2, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                }
                if (
                        "3D_star_2_next_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3d_2_star_after, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
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
        cut.setOnClickListener(this);
        add.setOnClickListener(this);
        AddGameNumBtn.setOnClickListener(this);
        RadioGroupGameCenter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.radion1) {
                    PriceUnit = 1;
                }
                if (i == R.id.radion2) {
                    PriceUnit = 2;
                }
                if (i == R.id.radion3) {
                    PriceUnit = 3;
                }
                if (i == R.id.radion4) {
                    PriceUnit = 4;
                }
                Log.d("PriceUnit", PriceUnit + "");
            }
        });
        ToGameCert.setOnClickListener(this);
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
            case R.id.cut:
                String num = edit1.getText().toString().trim();
                int intnum = Integer.parseInt(num);
                if (intnum > 1) {
                    intnum = intnum - 1;
                    edit1.setText(intnum + "");
                }
                break;
            case R.id.add:
                String addnum = edit1.getText().toString().trim();
                int addintnum = Integer.parseInt(addnum);
                addintnum = addintnum + 1;
                edit1.setText(addintnum + "");
                break;
            case R.id.ToGameCert:
                Intent intent = new Intent(GameCenterActivity.this, GameCartActivity.class);
                intent.putExtra("listids", (Serializable) listIds);
                intent.putExtra("gid", gid);
                intent.putExtra("period", bs.getPeriod());
                startActivity(intent);
                break;
            case R.id.AddGameNumBtn:
                if (code.equals("star_5_duplex")) {
                    final int[] count1 = {0};
                    final int[] count2 = {0};
                    final int[] count3 = {0};
                    final int[] count4 = {0};
                    final int[] count5 = {0};
                    String wan = null;
                    String qian = null;
                    String bai = null;
                    String shi = null;
                    String ge = null;

                    LinearLayout lin1 = (LinearLayout) inte.findViewById(R.id.LinearWan);
                    LinearLayout lin2 = (LinearLayout) inte.findViewById(R.id.LinearQian);
                    LinearLayout lin3 = (LinearLayout) inte.findViewById(R.id.LinearBai);
                    LinearLayout lin4 = (LinearLayout) inte.findViewById(R.id.LinearShi);
                    LinearLayout lin5 = (LinearLayout) inte.findViewById(R.id.LinearGe);
                    for (int i = 1; i < lin1.getChildCount(); i++) {
                        if (((CheckBox) lin1.getChildAt(i)).isChecked()) {
                            count1[0]++;
                            if (wan == null) {
                                wan = ((CheckBox) lin1.getChildAt(i)).getText().toString();
                            } else {
                                wan = wan + ((CheckBox) lin1.getChildAt(i)).getText().toString();
                            }
                        }
                    }
                    for (int i = 1; i < lin2.getChildCount(); i++) {
                        if (((CheckBox) lin2.getChildAt(i)).isChecked()) {
                            count2[0]++;
                            if (qian == null) {
                                qian = ((CheckBox) lin2.getChildAt(i)).getText().toString();
                            } else {
                                qian = qian + ((CheckBox) lin2.getChildAt(i)).getText().toString();
                            }
                        }
                    }
                    for (int i = 1; i < lin3.getChildCount(); i++) {
                        if (((CheckBox) lin3.getChildAt(i)).isChecked()) {
                            count3[0]++;
                            if (bai == null) {
                                bai = ((CheckBox) lin3.getChildAt(i)).getText().toString();
                            } else {
                                bai = bai + ((CheckBox) lin3.getChildAt(i)).getText().toString();
                            }
                        }
                    }
                    for (int i = 1; i < lin4.getChildCount(); i++) {
                        if (((CheckBox) lin4.getChildAt(i)).isChecked()) {
                            count4[0]++;
                            if (shi == null) {
                                shi = ((CheckBox) lin4.getChildAt(i)).getText().toString();
                            } else {
                                shi = shi + ((CheckBox) lin4.getChildAt(i)).getText().toString();
                            }
                        }
                    }
                    for (int i = 1; i < lin5.getChildCount(); i++) {
                        if (((CheckBox) lin5.getChildAt(i)).isChecked()) {
                            count5[0]++;
                            if (ge == null) {
                                ge = ((CheckBox) lin5.getChildAt(i)).getText().toString();
                            } else {
                                ge = ge + ((CheckBox) lin5.getChildAt(i)).getText().toString();
                            }
                        }
                    }
                    Log.d("GameCenterrCount==", count1[0] + "   " + count2[0] + "   " + count3[0] + "   " + count4[0] + "   " + count5[0]);
                    pickedNumber = wan + "," + qian + "," + bai + "," + shi + "," + ge;
                    Log.d("GameCenterrText==", pickedNumber);
                    nums = count1[0] * count2[0] * count3[0] * count4[0] * count5[0];




                }
                if (code.equals("star_5_group_120")) {
                    int[] count120 = {0};
                    String text120 = null;
                    LinearLayout linear120 = (LinearLayout) inte.findViewById(R.id.Linear120);
                    for (int i = 1; i < linear120.getChildCount(); i++) {
                        if (((CheckBox) linear120.getChildAt(i)).isChecked()) {
                            count120[0]++;
                            if (text120 == null) {
                                text120 = ((CheckBox) linear120.getChildAt(i)).getText().toString();
                            } else {
                                text120 = text120 + "," + ((CheckBox) linear120.getChildAt(i)).getText().toString();
                            }
                        }
                    }
                    Log.d("GameCenter120", text120);
                    pickedNumber = text120;
                    if (count120[0] > 4) {
                        int nns = RxUtils.getInstance().JieCheng(count120[0]) / (RxUtils.getInstance().JieCheng(5) * RxUtils.getInstance().JieCheng(count120[0] - 5));
                        Log.d("GameCenter120注", nns + "");
                        nums = nns;
                    }

                }
                if (nums == 0) {
                    Toasty.error(GameCenterActivity.this, "投注注数为0,请重新投注", 2000).show();
                    return;
                }
                Nums = Integer.parseInt(edit1.getText().toString().trim());
                amount = nums * 2;
                if (PriceUnit == 2) {
                    amount = amount / 10;
                }
                if (PriceUnit == 3) {
                    amount = amount / 100;
                }
                if (PriceUnit == 4) {
                    amount = amount / 1000;
                }
                amount = amount * Nums;
                classCode = code;
                multiple = Nums;
                TextView Zhu = (TextView) contentView.findViewById(R.id.Zhu);
                TextView Amounts = (TextView) contentView.findViewById(R.id.GameAmounts);
                TextView TouZhuContent = (TextView) contentView.findViewById(R.id.TouZhuContent);
                TextView GameType = (TextView) contentView.findViewById(R.id.GameType);
                GameType.setText(GameTypeName);
                TouZhuContent.setText(pickedNumber);
                Zhu.setText(nums + "");
                Amounts.setText(amount + "");
                if (nums>0) {
                    alertView.show();
                }
                break;
        }
    }

    @Override
    public void onItemClick(Object o, int position) {
        if (position != AlertView.CANCELPOSITION) {
            Ids ids = new Ids();
            ids.setPickedNumber(pickedNumber);
            ids.setPickedText(pickedText);
            ids.setLocation(location);
            ids.setLocationText(locationText);
            ids.setNum(nums);
            ids.setClassCode(classCode);
            ids.setPriceUnit(PriceUnit);
            ids.setPriceType(1);
            ids.setAmount(amount);
            ids.setMultiple(multiple);
            ids.setAmounts(amount);
            ids.setMultiples(multiple);
            ids.setVcode(vcode);
            ids.setGamename(GameTypeName);
            listIds.add(ids);
            for (int i = 0; i < listIds.size(); i++) {
                Log.d("购彩单的数据=", listIds.get(i).toString());
            }
            String s = SendGameNum.getText().toString();
            int num = Integer.parseInt(s);
            SendGameNum.setText(num + 1 + "");

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