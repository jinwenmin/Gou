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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.example.king.gou.bean.BettingSync;
import com.example.king.gou.bean.Ids;
import com.example.king.gou.bean.Rates;
import com.example.king.gou.bean.RecordList;
import com.example.king.gou.bean.SwitchG;
import com.example.king.gou.fragment.FindFragment;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.gameAcVpFrms.GameCartActivity;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


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
    EditText edit1;
    @BindView(R.id.add)
    TextView add;
    /* @BindView(R.id.radion1)
     RadioButton radion1;
     @BindView(R.id.radion2)
     RadioButton radion2;
     @BindView(R.id.radion3)
     RadioButton radion3;
     @BindView(R.id.radion4)
     RadioButton radion4;*/
    @BindView(R.id.SpinnerMoney)
    Spinner SpinnerMoney;
    @BindView(R.id.GameCentertitle)
    TextView GameCentertitle;
    @BindView(R.id.AddGameNumBtn)
    RelativeLayout AddGameNumBtn;
    @BindView(R.id.SendGameNum)
    TextView SendGameNum;
    /*@BindView(R.id.RadioGroupGameCenter)
    RadioGroup RadioGroupGameCenter;*/
    @BindView(R.id.ToGameCert)
    RelativeLayout ToGameCert;
    @BindView(R.id.LinearMoneyGetCheck)
    LinearLayout LinearMoneyGetCheck;
    @BindView(R.id.GameZhu)
    TextView GameZhu;
    @BindView(R.id.GameYuan)
    TextView GameYuan;

    private int TIME = 1000;  //每隔1s执行一次.

    Handler handler = new Handler();
    Handler handler1 = new Handler();

    private int gid;
    private int position;
    private int section;
    private String namess;
    List<RecordList> rcs = new ArrayList<RecordList>();
    BettingSync bs = new BettingSync();
    DrawHistoryAdapter drawHistoryAdapter;
    Runnable runnable;

    private Timer timer;
    private Timer timerSelect;
    List<SwitchG> sg = new ArrayList<>();

    private ArrayAdapter<String> adapterType1;
    private ArrayAdapter<String> adapterType2;
    private ArrayAdapter<String> adapterTypeMoney;
    private AlertView alertView;
    // 一个自定义的布局，作为显示的内容
    View contentView;
    View inte;
    String code;
    String GameTypeName = "";
    int PriceUnit = 1;
    List<Ids> listIds = new ArrayList<>();

    String pickedNumber = "";//投注内容，如果是单式选号，需要过滤掉不合法的投注内容
    String pickedText = "";//投注文本，大部分时候等于pickedNumber，只有有中文的投注，才会不一样，如果相同，提交的时候为了节省提交数据大小，可以传空字符串，单式加密的时候必须是原内容
    String location = "";//数字字符串，五个0或1组成，代表时时彩或分分彩任选玩法勾选的位置，0代表不勾选，1代表勾选
    String locationText = "";//由"万千百十个"五个字其中几个组成，代表勾选的位置的文本
    int Nums;//投注内容注数
    String classCode = "";//玩法编码
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
    List<String> name = null;
    List<Rates> MinAndMaxs;
    int REQUESTCODE = 1;
    double mons = 1;

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
        namess = intent.getStringExtra("name");
        GameCentertitle.setText(namess);
        Log.d("GameCenterGid==", gid + "");
        Log.d("GameCenterName==", namess + "");
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
        SpinnerMoney.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MyApp.getInstance().setMoneySpinnerPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SpinnerMoney.setSelection(MyApp.getInstance().getMoneySpinnerPosition());
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

    public void setGameMoney(int nums) {
        GameZhu.setText(nums + "");
        int i = Integer.parseInt(edit1.getText().toString().trim());
        GameYuan.setText(nums * mons * 2 * i + "");
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
        name = new ArrayList<>();
        MinAndMaxs = new ArrayList<>();
        final List<String> class_code = new ArrayList<>();
        List<SwitchG.SwitchGa> switchGas = sg.get(SpinnerType1.getSelectedItemPosition()).getSwitchGas();
        for (int i1 = 0; i1 < switchGas.size(); i1++) {
            List<Double> ms = new ArrayList<>();
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
                    Log.d("ClassCode", switchGams.get(i2).getClass_code3());
                    Log.d("Min==", switchGams.get(i2).getMinimum3() + "  Coe==" + switchGams.get(i2).getCoefficient3() + "  Rate==" + switchGams.get(i2).getRate3());
                    Rates rates = new Rates();
                    rates.setRate(switchGams.get(i2).getRate3());
                    rates.setCoefficient(switchGams.get(i2).getCoefficient3());
                    rates.setMinimum(switchGams.get(i2).getMinimum3());
                   /* ms.add(switchGams.get(i2).getMinimum3());
                    ms.add(switchGams.get(i2).getCoefficient3());
                    ms.add();*/
                    MinAndMaxs.add(rates);
                }
            } else if (switchGams1.size() == 0) {
                name.add((String) switchGas.get(i1).getName2());
                class_code.add(switchGas.get(i1).getClass_code2());
                Log.d("ClassCode", switchGas.get(i1).getClass_code2());
                Log.d("Min==", switchGas.get(i1).getMinimum2() + "Coe" + switchGas.get(i1).getCoefficient2() + "  Rate==" + switchGas.get(i1).getRate2());
             /*   ms.add(switchGas.get(i1).getMinimum2());
                ms.add(switchGas.get(i1).getCoefficient2());
                ms.add(switchGas.get(i1).getRate2());*/
                Rates rates = new Rates();
                rates.setRate(switchGas.get(i1).getRate2());
                rates.setCoefficient(switchGas.get(i1).getCoefficient2());
                rates.setMinimum(switchGas.get(i1).getMinimum2());
                MinAndMaxs.add(rates);
            }
            // MinAndMaxs.add(ms);
            Log.d("MinAndMaxsSize", MinAndMaxs.size() + "");
        }

        adapterType2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterType2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerType2.setAdapter(adapterType2);
        SpinnerType2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AddMoneySpinner(position);
                code = class_code.get(position);
                GameTypeName = name.get(position);
                nums = 0;
                setGameMoney(0);
                edit1.setText(1 + "");

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

                        || "2min_star_5_duplex".equals(code)


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
                    final int[] count1 = {0};
                    final int[] count2 = {0};
                    final int[] count3 = {0};
                    final int[] count4 = {0};
                    final int[] count5 = {0};
                    final int[] s = {0};
                    for (int i = 1; i < LinearWan.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearWan.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count1[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0] * count5[0];
                                    setGameMoney(s[0]);
                                    Log.d("五星直选注数", String.valueOf(s[0]));
                                }
                                if (!b) {
                                    count1[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0] * count5[0];
                                    setGameMoney(s[0]);
                                    Log.d("五星直选注数", String.valueOf(s[0]));
                                }
                            }
                        });

                    }
                    for (int i = 1; i < LinearQian.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearQian.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count2[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0] * count5[0];
                                    Log.d("五星直选注数", String.valueOf(s[0]));
                                    setGameMoney(s[0]);
                                }
                                if (!b) {
                                    count2[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0] * count5[0];
                                    Log.d("五星直选注数", String.valueOf(s[0]));
                                    setGameMoney(s[0]);
                                }
                            }
                        });

                    }
                    for (int i = 1; i < LinearBai.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearBai.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count3[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0] * count5[0];
                                    Log.d("五星直选注数", String.valueOf(s[0]));
                                    setGameMoney(s[0]);
                                }
                                if (!b) {
                                    count3[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0] * count5[0];
                                    Log.d("五星直选注数", String.valueOf(s[0]));
                                    setGameMoney(s[0]);
                                }
                            }
                        });

                    }
                    for (int i = 1; i < LinearShi.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearShi.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count4[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0] * count5[0];
                                    Log.d("五星直选注数", String.valueOf(s[0]));
                                    setGameMoney(s[0]);
                                }
                                if (!b) {
                                    count4[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0] * count5[0];
                                    Log.d("五星直选注数", String.valueOf(s[0]));
                                    setGameMoney(s[0]);
                                }
                            }
                        });

                    }
                    for (int i = 1; i < LinearGe.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearGe.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count5[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0] * count5[0];
                                    Log.d("五星直选注数", String.valueOf(s[0]));
                                    setGameMoney(s[0]);
                                }
                                if (!b) {
                                    count5[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0] * count5[0];
                                    Log.d("五星直选注数", String.valueOf(s[0]));
                                    setGameMoney(s[0]);
                                }
                            }
                        });

                    }
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
                    g2_editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String s = charSequence.toString();
                            Log.d("单式模式的", s + "   " + i + "    " + i1 + "   " + i2);
                            int seleNums = 0;
                            List<String> NS;
                            if (
                                    "star_5_single".equals(code)
                                            || "2min_star_5_single".equals(code)
                                    ) {
                                seleNums = 5;
                            }
                            if (
                                    "star_4_single".equals(code)
                                            || "2min_star_4_single".equals(code)
                                    ) {
                                seleNums = 4;
                            }
                            if (
                                    "star_3_next_single".equals(code)
                                            || "2min_star_3_next_single".equals(code)
                                            || "star_3_next_group_single".equals(code)
                                            || "2min_star_3_next_group_single".equals(code)
                                            || "star_3_next_group_single_6".equals(code)
                                            || "2min_star_3_next_group_single_6".equals(code)
                                            || "star_3_next_group_diverse".equals(code)
                                            || "2min_star_3_next_group_diverse".equals(code)

                                            || "star_3_prev_single".equals(code)
                                            || "2min_star_3_prev_single".equals(code)
                                            || "star_3_prev_group_single".equals(code)
                                            || "2min_star_3_prev_group_single".equals(code)
                                            || "star_3_prev_group_single_6".equals(code)
                                            || "2min_star_3_prev_group_single_6".equals(code)
                                            || "star_3_prev_group_diverse".equals(code)
                                            || "2min_star_3_prev_group_diverse".equals(code)

                                            || "star_3_midd_single".equals(code)
                                            || "2min_star_3_midd_single".equals(code)
                                            || "star_3_midd_group_single".equals(code)
                                            || "2min_star_3_midd_group_single".equals(code)
                                            || "star_3_midd_group_single_6".equals(code)
                                            || "2min_star_3_midd_group_single_6".equals(code)
                                            || "star_3_midd_group_diverse".equals(code)
                                            || "2min_star_3_midd_group_diverse".equals(code)

                                    ) {
                                seleNums = 3;
                            }
                            if (
                                    "star_2_next_single".equals(code)
                                            || "2min_star_2_next_single".equals(code)
                                            || "star_2_next_group_single".equals(code)
                                            || "2min_star_2_next_group_single".equals(code)

                                            || "star_2_prev_single".equals(code)
                                            || "2min_star_2_prev_single".equals(code)
                                            || "star_2_prev_group_single".equals(code)
                                            || "2min_star_2_prev_group_single".equals(code)

                                    ) {
                                seleNums = 2;
                            }
                            if (s.length() > seleNums - 1) {
                                if (s.contains(",")) {
                                    NS = new ArrayList<String>();
                                    String[] sp = s.split(",");
                                    Log.d("单式模式的Sp", sp.length + "");
                                    int c = 0;
                                    for (int i3 = 0; i3 < sp.length; i3++) {
                                        String s1 = sp[i3];
                                        if (s1.length() == seleNums) {
                                            for (int i4 = 0; i4 < s1.length(); i4++) {
                                                String sub = s1.substring(i4, i4 + 1);
                                                Log.d("判断是不是数字", sub.matches("\\d+") + "");
                                                if (sub.matches("\\d+")) {
                                                    if (i4 == seleNums - 1) {
                                                        if (s.substring(i4, i4 + 1).matches("\\d+")) {
                                                            NS.add(s1);
                                                            c++;
                                                        }
                                                    }
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (
                                            "star_3_next_group_single".equals(code)
                                                    || "2min_star_3_next_group_single".equals(code)
                                                    || "star_3_prev_group_single".equals(code)
                                                    || "2min_star_3_prev_group_single".equals(code) || "star_3_midd_group_single".equals(code)
                                                    || "2min_star_3_midd_group_single".equals(code)
                                            ) {
                                        int cc = 0;
                                        for (int i3 = 0; i3 < NS.size(); i3++) {
                                            String ss1 = NS.get(i3);
                                            Log.d("组三单式Nums", ss1);
                                            int s1 = Integer.parseInt(ss1.substring(0, 1));
                                            int s2 = Integer.parseInt(ss1.substring(1, 2));
                                            int s3 = Integer.parseInt(ss1.substring(2, 3));
                                            if (s1 == s2 && s1 == s3 && s2 == s3) {

                                            } else if (s1 != s2 && s1 != s3 && s2 != s3) {

                                            } else {
                                                cc++;
                                            }

                                        }
                                        Log.d("组三单式NumsCC", cc + "");
                                        setGameMoney(cc);
                                    } else if (
                                            "star_3_next_group_single_6".equals(code)
                                                    || "2min_star_3_next_group_single_6".equals(code)
                                                    || "star_3_prev_group_single_6".equals(code)
                                                    || "2min_star_3_prev_group_single_6".equals(code) || "star_3_midd_group_single_6".equals(code)
                                                    || "2min_star_3_midd_group_single_6".equals(code)
                                            ) {
                                        int cc6 = 0;
                                        for (int i3 = 0; i3 < NS.size(); i3++) {
                                            String ss1 = NS.get(i3);
                                            int s1 = Integer.parseInt(ss1.substring(0, 1));
                                            int s2 = Integer.parseInt(ss1.substring(1, 2));
                                            int s3 = Integer.parseInt(ss1.substring(2, 3));
                                            if (s1 == s2 || s1 == s3 || s2 == s3) {

                                            } else {
                                                cc6++;
                                            }
                                        }
                                        setGameMoney(cc6);
                                    } else if (
                                            "star_3_next_group_diverse".equals(code)
                                                    || "2min_star_3_next_group_diverse".equals(code)
                                                    || "star_3_prev_group_diverse".equals(code)
                                                    || "2min_star_3_prev_group_diverse".equals(code) || "star_3_midd_group_diverse".equals(code)
                                                    || "2min_star_3_midd_group_diverse".equals(code)
                                            ) {
                                        int ccHe = 0;
                                        for (int i3 = 0; i3 < NS.size(); i3++) {
                                            String ss1 = NS.get(i3);
                                            int s1 = Integer.parseInt(ss1.substring(0, 1));
                                            int s2 = Integer.parseInt(ss1.substring(1, 2));
                                            int s3 = Integer.parseInt(ss1.substring(2, 3));
                                            if (s1 == s2 && s1 == s3 && s2 == s3) {

                                            } else {
                                                ccHe++;
                                            }
                                        }
                                        setGameMoney(ccHe);
                                    } else if (
                                            "star_2_prev_group_single".equals(code)
                                                    || "2min_star_2_prev_group_single".equals(code)
                                                    || "star_2_next_group_single".equals(code)
                                                    || "2min_star_2_next_group_single".equals(code)

                                            ) {
                                        int cc2 = 0;
                                        for (int i3 = 0; i3 < NS.size(); i3++) {
                                            String ss1 = NS.get(i3);
                                            int s1 = Integer.parseInt(ss1.substring(0, 1));
                                            int s2 = Integer.parseInt(ss1.substring(1, 2));

                                            if (s1 == s2) {
                                            } else {
                                                cc2++;
                                            }
                                        }
                                        setGameMoney(cc2);
                                    } else {
                                        Log.d("组三单式Nums注", c + "");
                                        setGameMoney(c);
                                    }

                                } else {
                                    NS = new ArrayList<String>();
                                    if (s.length() == seleNums) {
                                        for (int i3 = 0; i3 < s.length(); i3++) {
                                            String sub = s.substring(i3, i3 + 1);
                                            Log.d("判断是不是数字", sub.matches("\\d+") + "");
                                            if (sub.matches("\\d+")) {
                                                if (i3 == seleNums - 1) {
                                                    if (s.substring(i3, i3 + 1).matches("\\d+")) {
                                                        NS.add(s);
                                                    }
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                    if (NS.size() != 0) {
                                        if (
                                                "star_5_single".equals(code)
                                                        || "2min_star_5_single".equals(code)
                                                        || "star_4_single".equals(code)
                                                        || "2min_star_4_single".equals(code)

                                                        || "star_3_next_single".equals(code)
                                                        || "2min_star_3_next_single".equals(code)
                                                        || "star_3_prev_single".equals(code)
                                                        || "2min_star_3_prev_single".equals(code)
                                                        || "star_3_midd_single".equals(code)
                                                        || "2min_star_3_midd_single".equals(code)

                                                        || "star_2_prev_single".equals(code)
                                                        || "2min_star_2_prev_single".equals(code)
                                                        || "star_2_next_single".equals(code)
                                                        || "2min_star_2_next_single".equals(code)
                                                ) {
                                            Log.d("执行这一步", "为1");
                                            setGameMoney(1);
                                        }
                                    }

                                    if (
                                            "star_3_next_group_single".equals(code)
                                                    || "2min_star_3_next_group_single".equals(code)
                                                    || "star_3_prev_group_single".equals(code)
                                                    || "2min_star_3_prev_group_single".equals(code) || "star_3_midd_group_single".equals(code)
                                                    || "2min_star_3_midd_group_single".equals(code)
                                            ) {
                                        int cc = 0;
                                        for (int i3 = 0; i3 < NS.size(); i3++) {
                                            String ss1 = NS.get(i3);
                                            Log.d("组三单式Nums", ss1);
                                            int s1 = Integer.parseInt(ss1.substring(0, 1));
                                            int s2 = Integer.parseInt(ss1.substring(1, 2));
                                            int s3 = Integer.parseInt(ss1.substring(2, 3));
                                            if (s1 == s2 && s1 == s3 && s2 == s3) {

                                            } else if (s1 != s2 && s1 != s3 && s2 != s3) {

                                            } else {
                                                setGameMoney(1);
                                            }

                                        }


                                    } else if (
                                            "star_3_next_group_single_6".equals(code)
                                                    || "2min_star_3_next_group_single_6".equals(code)
                                                    || "star_3_prev_group_single_6".equals(code)
                                                    || "2min_star_3_prev_group_single_6".equals(code) || "star_3_midd_group_single_6".equals(code)
                                                    || "2min_star_3_midd_group_single_6".equals(code)
                                            ) {
                                        int cc6 = 0;
                                        for (int i3 = 0; i3 < NS.size(); i3++) {
                                            String ss1 = NS.get(i3);
                                            int s1 = Integer.parseInt(ss1.substring(0, 1));
                                            int s2 = Integer.parseInt(ss1.substring(1, 2));
                                            int s3 = Integer.parseInt(ss1.substring(2, 3));
                                            if (s1 == s2 || s1 == s3 || s2 == s3) {

                                            } else {
                                                setGameMoney(1);
                                            }
                                        }

                                    } else if (
                                            "star_3_next_group_diverse".equals(code)
                                                    || "2min_star_3_next_group_diverse".equals(code)
                                                    || "star_3_prev_group_diverse".equals(code)
                                                    || "2min_star_3_prev_group_diverse".equals(code) || "star_3_midd_group_diverse".equals(code)
                                                    || "2min_star_3_midd_group_diverse".equals(code)
                                            ) {
                                        int ccHe = 0;
                                        for (int i3 = 0; i3 < NS.size(); i3++) {
                                            String ss1 = NS.get(i3);
                                            int s1 = Integer.parseInt(ss1.substring(0, 1));
                                            int s2 = Integer.parseInt(ss1.substring(1, 2));
                                            int s3 = Integer.parseInt(ss1.substring(2, 3));
                                            if (s1 == s2 && s1 == s3 && s2 == s3) {

                                            } else {
                                                setGameMoney(1);
                                            }
                                        }

                                    } else if (
                                            "star_2_prev_group_single".equals(code)
                                                    || "2min_star_2_prev_group_single".equals(code)
                                                    || "star_2_next_group_single".equals(code)
                                                    || "2min_star_2_next_group_single".equals(code)

                                            ) {
                                        int cc2 = 0;
                                        for (int i3 = 0; i3 < NS.size(); i3++) {
                                            String ss1 = NS.get(i3);
                                            int s1 = Integer.parseInt(ss1.substring(0, 1));
                                            int s2 = Integer.parseInt(ss1.substring(1, 2));

                                            if (s1 == s2) {
                                            } else {
                                                setGameMoney(1);
                                            }
                                        }

                                    }
                                    if (NS.size() == 0) {
                                        setGameMoney(0);
                                    }
                                }

                            } else {
                                setGameMoney(0);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
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
                    TextView kong = (TextView) inte.findViewById(R.id.kong);

                    TextView numDelete = (TextView) inte.findViewById(R.id.numDelete);
                    // g2_editText.setInputType(InputType.TYPE_NULL);

                    num0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "0");
                        }
                    });
                    num1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "1");
                        }
                    });
                    num2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "2");
                        }
                    });
                    num3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "3");
                        }
                    });
                    num4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "4");
                        }
                    });
                    num5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "5");
                        }
                    });
                    num6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "6");
                        }
                    });
                    num7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "7");
                        }
                    });
                    num8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "8");
                        }
                    });
                    num9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "9");
                        }
                    });
                    numd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (!",".equals(text.substring(text.length() - 1))) {
                                g2_editText.setText(g2_editText.getText().toString() + ",");
                            }
                        }
                    });
                    numDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (text.length() > 0) {
                                text = text.substring(0, text.length() - 1);
                                g2_editText.setText(text);
                            }
                        }
                    });

                    kong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (!" ".equals(text.substring(text.length() - 1))) {
                                g2_editText.setText(g2_editText.getText().toString().trim() + " ");
                            }
                        }
                    });
                }
                if ("star_5_group_120".equals(code) || "2min_star_5_group_120".equals(code)) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_120, null, false);
                    LinearLayout Linear120 = (LinearLayout) inte.findViewById(R.id.Linear120);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    final int[] count = {0};
                    for (int i = 1; i < Linear120.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear120.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count[0]++;
                                    if (count[0] > 4) {
                                        int nns = RxUtils.getInstance().JieCheng(count[0]) / (RxUtils.getInstance().JieCheng(5) * RxUtils.getInstance().JieCheng(count[0] - 5));
                                        setGameMoney(nns);
                                    }
                                } else {
                                    count[0]--;
                                    if (count[0] > 4) {
                                        int nns = RxUtils.getInstance().JieCheng(count[0]) / (RxUtils.getInstance().JieCheng(5) * RxUtils.getInstance().JieCheng(count[0] - 5));
                                        setGameMoney(nns);
                                    }
                                }
                            }
                        });
                    }
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
                    final List<String> s1 = new ArrayList<String>();
                    final List<String> s2 = new ArrayList<String>();
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] k = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c1[0]++;
                                    s1.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }

                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);

                                } else {
                                    c1[0]--;
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        if (s1.get(i1).equals(at.getText().toString())) {
                                            s1.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);
                                }
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c2[0]++;
                                    s2.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);
                                } else {
                                    c2[0]--;
                                    for (int i1 = 0; i1 < s2.size(); i1++) {
                                        if (s2.get(i1).equals(at.getText().toString())) {
                                            s2.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);
                                }
                            }
                        });
                    }
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
                    final List<String> s1 = new ArrayList<String>();
                    final List<String> s2 = new ArrayList<String>();
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] k = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c1[0]++;
                                    s1.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }

                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);

                                } else {
                                    c1[0]--;
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        if (s1.get(i1).equals(at.getText().toString())) {
                                            s1.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);
                                }
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c2[0]++;
                                    s2.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);
                                } else {
                                    c2[0]--;
                                    for (int i1 = 0; i1 < s2.size(); i1++) {
                                        if (s2.get(i1).equals(at.getText().toString())) {
                                            s2.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);
                                }
                            }
                        });
                    }
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
                    final List<String> s1 = new ArrayList<String>();
                    final List<String> s2 = new ArrayList<String>();
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] k = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c1[0]++;
                                    s1.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }

                                    setGameMoney((c2[0] - 1) * (c2[0] - 2) * (c1[0] * c2[0] - 3 * k[0]) / 6);

                                } else {
                                    c1[0]--;
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        if (s1.get(i1).equals(at.getText().toString())) {
                                            s1.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney((c2[0] - 1) * (c2[0] - 2) * (c1[0] * c2[0] - 3 * k[0]) / 6);
                                }
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c2[0]++;
                                    s2.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney((c2[0] - 1) * (c2[0] - 2) * (c1[0] * c2[0] - 3 * k[0]) / 6);
                                } else {
                                    c2[0]--;
                                    for (int i1 = 0; i1 < s2.size(); i1++) {
                                        if (s2.get(i1).equals(at.getText().toString())) {
                                            s2.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney((c2[0] - 1) * (c2[0] - 2) * (c1[0] * c2[0] - 3 * k[0]) / 6);
                                }
                            }
                        });
                    }
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
                    final List<String> s1 = new ArrayList<String>();
                    final List<String> s2 = new ArrayList<String>();
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] k = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c1[0]++;
                                    s1.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }

                                    setGameMoney(c1[0] * c2[0] - k[0]);

                                } else {
                                    c1[0]--;
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        if (s1.get(i1).equals(at.getText().toString())) {
                                            s1.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c1[0] * c2[0] - k[0]);
                                }
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c2[0]++;
                                    s2.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c1[0] * c2[0] - k[0]);
                                } else {
                                    c2[0]--;
                                    for (int i1 = 0; i1 < s2.size(); i1++) {
                                        if (s2.get(i1).equals(at.getText().toString())) {
                                            s2.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c1[0] * c2[0] - k[0]);
                                }
                            }
                        });
                    }
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
                    final int[] count = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count[0]++;
                                } else {
                                    count[0]--;
                                }
                                setGameMoney(count[0]);
                            }
                        });
                    }
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
                    final int[] count1 = {0};
                    final int[] count2 = {0};
                    final int[] count3 = {0};
                    final int[] count4 = {0};

                    final int[] s = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count1[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0];

                                }
                                if (!b) {
                                    count1[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0];


                                }
                                setGameMoney(s[0]);
                            }
                        });

                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count2[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0];

                                }
                                if (!b) {
                                    count2[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0];

                                }
                                setGameMoney(s[0]);
                            }
                        });

                    }
                    for (int i = 1; i < LinearThree.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count3[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0];

                                }
                                if (!b) {
                                    count3[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0];


                                }
                                setGameMoney(s[0]);
                            }
                        });

                    }
                    for (int i = 1; i < LinearFour.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearFour.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count4[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0];

                                }
                                if (!b) {
                                    count4[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0] * count4[0];


                                }
                                setGameMoney(s[0]);
                            }
                        });

                    }
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
                    final int[] count = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count[0]++;
                                } else {
                                    count[0]--;
                                }
                                if (count[0] > 3) {
                                    setGameMoney(RxUtils.getInstance().JieCheng(count[0]) / (RxUtils.getInstance().JieCheng(4) * RxUtils.getInstance().JieCheng(count[0] - 4)));
                                }
                            }
                        });
                    }
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
                    final List<String> s1 = new ArrayList<String>();
                    final List<String> s2 = new ArrayList<String>();
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] k = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c1[0]++;
                                    s1.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }

                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);

                                } else {
                                    c1[0]--;
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        if (s1.get(i1).equals(at.getText().toString())) {
                                            s1.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);
                                }
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c2[0]++;
                                    s2.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);
                                } else {
                                    c2[0]--;
                                    for (int i1 = 0; i1 < s2.size(); i1++) {
                                        if (s2.get(i1).equals(at.getText().toString())) {
                                            s2.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c2[0] * (c2[0] - 1) * c1[0] / 2 - (c2[0] - 1) * k[0]);
                                }
                            }
                        });
                    }
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
                    final int[] count = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count[0]++;
                                } else {
                                    count[0]--;
                                }

                                setGameMoney(count[0] * (count[0] - 1) / 2);

                            }
                        });
                    }
                    Select9(select_1, LinearOne);
                }
                if ("star_4_group_4".equals(code) || "2min_star_4_group_4".equals(code)) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_4_star_after_4, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    final List<String> s1 = new ArrayList<String>();
                    final List<String> s2 = new ArrayList<String>();
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] k = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c1[0]++;
                                    s1.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }

                                    setGameMoney(c1[0] * c2[0] - k[0]);

                                } else {
                                    c1[0]--;
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        if (s1.get(i1).equals(at.getText().toString())) {
                                            s1.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c1[0] * c2[0] - k[0]);
                                }
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                k[0] = 0;
                                if (b) {
                                    c2[0]++;
                                    s2.add(at.getText().toString());
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c1[0] * c2[0] - k[0]);
                                } else {
                                    c2[0]--;
                                    for (int i1 = 0; i1 < s2.size(); i1++) {
                                        if (s2.get(i1).equals(at.getText().toString())) {
                                            s2.remove(i1);
                                        }
                                    }
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        for (int i2 = 0; i2 < s2.size(); i2++) {
                                            if (s1.get(i1).equals(s2.get(i2))) {
                                                k[0]++;
                                                Log.d("重复个数", k[0] + "");
                                            }
                                        }
                                    }
                                    setGameMoney(c1[0] * c2[0] - k[0]);
                                }
                            }
                        });
                    }
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
                    final int[] count1 = {0};
                    final int[] count2 = {0};
                    final int[] count3 = {0};
                    final int[] countSum = {0};

                    final int[] s = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count1[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }
                                if (!b) {
                                    count1[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0];


                                }

                                if ("3D_fix".equals(code)) {
                                    setGameMoney(countSum[0]);
                                } else {

                                    setGameMoney(s[0]);
                                }
                            }
                        });

                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count2[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }
                                if (!b) {
                                    count2[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }
                                if ("3D_fix".equals(code)) {
                                    setGameMoney(countSum[0]);
                                } else {

                                    setGameMoney(s[0]);
                                }
                            }
                        });

                    }
                    for (int i = 1; i < LinearThree.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count3[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }
                                if (!b) {
                                    count3[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0];


                                }
                                if ("3D_fix".equals(code)) {
                                    setGameMoney(countSum[0]);
                                } else {

                                    setGameMoney(s[0]);
                                }
                            }
                        });

                    }

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
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    final int[] counts = {0};
                    for (int i = 0; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    counts[0] = counts[0] + ThreeStarHe(Integer.parseInt(at.getText().toString().trim()));
                                }
                                if (!b) {
                                    counts[0] = counts[0] - ThreeStarHe(Integer.parseInt(at.getText().toString().trim()));
                                }
                                setGameMoney(counts[0]);
                            }
                        });


                    }
                    for (int i = 0; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    counts[0] = counts[0] + ThreeStarHe(Integer.parseInt(at.getText().toString().trim()));
                                }
                                if (!b) {
                                    counts[0] = counts[0] - ThreeStarHe(Integer.parseInt(at.getText().toString().trim()));
                                }
                                setGameMoney(counts[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearThree.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    counts[0] = counts[0] + ThreeStarHe(Integer.parseInt(at.getText().toString().trim()));
                                }
                                if (!b) {
                                    counts[0] = counts[0] - ThreeStarHe(Integer.parseInt(at.getText().toString().trim()));
                                }
                                setGameMoney(counts[0]);
                            }
                        });
                    }
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
                    final int[] count = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count[0] = count[0] + ThreeKua(Integer.parseInt(at.getText().toString().trim()));
                                }
                                if (!b) {
                                    count[0] = count[0] - ThreeKua(Integer.parseInt(at.getText().toString().trim()));
                                }
                                setGameMoney(count[0]);
                            }
                        });
                    }
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
                    final int[] count = {0};
                    final int[] countSum = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count[0]++;
                                    countSum[0] = countSum[0] + TwoKua(Integer.parseInt(at.getText().toString().trim()));
                                } else {
                                    count[0]--;
                                    countSum[0] = countSum[0] - TwoKua(Integer.parseInt(at.getText().toString().trim()));
                                }
                                if (
                                        "star_3_next_one_no_fix".equals(code)
                                                || "2min_star_3_next_one_no_fix".equals(code)
                                                || "star_3_prev_one_no_fix".equals(code)
                                                || "2min_star_3_prev_one_no_fix".equals(code)
                                                || "star_4_one_no_fix".equals(code)
                                                || "2min_star_4_one_no_fix".equals(code)
                                        ) {
                                    setGameMoney(count[0]);
                                }
                                if (
                                        "star_3_next_two_no_fix".equals(code)
                                                || "2min_star_3_next_two_no_fix".equals(code)
                                                || "star_3_prev_two_no_fix".equals(code)
                                                || "2min_star_3_prev_two_no_fix".equals(code)
                                                || "star_4_two_no_fix".equals(code)
                                                || "2min_star_4_two_no_fix".equals(code)
                                                || "star_5_two_no_fix".equals(code)
                                                || "2min_star_5_two_no_fix".equals(code)

                                        ) {
                                    setGameMoney(count[0] * (count[0] - 1) / 2);
                                }
                                if (
                                        "star_2_next_sub".equals(code)
                                                || "2min_star_2_next_sub".equals(code)
                                        ) {
                                    setGameMoney(countSum[0]);
                                }
                                if (
                                        "star_3_next_group_duplex".equals(code)
                                                || "2min_star_3_next_group_duplex".equals(code)
                                                || "star_3_prev_group_duplex".equals(code)
                                                || "2min_star_3_prev_group_duplex".equals(code)
                                        ) {
                                    setGameMoney(count[0] * (count[0] - 1));
                                }
                                if (
                                        "star_3_next_group_duplex_6".equals(code)
                                                || "2min_star_3_next_group_duplex_6".equals(code)
                                                || "star_3_prev_group_duplex_6".equals(code)
                                                || "2min_star_3_prev_group_duplex_6".equals(code)
                                                || "star_3_midd_group_duplex_6".equals(code)
                                                || "2min_star_3_midd_group_duplex_6".equals(code)
                                                || "star_5_three_no_fix".equals(code)
                                                || "2min_star_5_three_no_fix".equals(code)
                                        ) {
                                    if (count[0] > 2) {
                                        int n1 = RxUtils.getInstance().JieCheng(count[0]);
                                        int n2 = RxUtils.getInstance().JieCheng(3);
                                        int n3 = RxUtils.getInstance().JieCheng(count[0] - 3);
                                        setGameMoney((n2 * n3 <= 0 ? 0 : n1 / (n2 * n3)));
                                    }
                                }
                            }
                        });
                    }
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
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.Linear1);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.Linear2);
                    LinearLayout linear3 = (LinearLayout) inte.findViewById(R.id.Linear3);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0] = c[0] + ThreeZuHe(Integer.parseInt(at.getText().toString().trim()));
                                } else if (!b) {
                                    c[0] = c[0] - ThreeZuHe(Integer.parseInt(at.getText().toString().trim()));
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0] = c[0] + ThreeZuHe(Integer.parseInt(at.getText().toString().trim()));
                                } else if (!b) {
                                    c[0] = c[0] - ThreeZuHe(Integer.parseInt(at.getText().toString().trim()));
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear3.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) linear3.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0] = c[0] + ThreeZuHe(Integer.parseInt(at.getText().toString().trim()));
                                } else if (!b) {
                                    c[0] = c[0] - ThreeZuHe(Integer.parseInt(at.getText().toString().trim()));
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }

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
                    if ("star_3_next_group_any".equals(code)
                            || "star_3_prev_group_any".equals(code)
                            || "star_3_midd_group_any".equals(code)

                            || "2min_star_3_next_group_any".equals(code)
                            || "2min_star_3_prev_group_any".equals(code)
                            || "2min_star_3_midd_group_any".equals(code)) {
                        setGameMoney(54);
                    } else {
                        setGameMoney(9);
                    }

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
                    final int[] count1 = {0};
                    final int[] count2 = {0};
                    final int[] count3 = {0};
                    final int[] countSum = {0};

                    final int[] s = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count1[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }
                                if (!b) {
                                    count1[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0];


                                }


                                setGameMoney(s[0]);

                            }
                        });

                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count2[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }
                                if (!b) {
                                    count2[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }

                                setGameMoney(s[0]);

                            }
                        });

                    }
                    for (int i = 1; i < LinearThree.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count3[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }
                                if (!b) {
                                    count3[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0];


                                }

                                setGameMoney(s[0]);

                            }
                        });

                    }
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
                    final int[] count1 = {0};
                    final int[] count2 = {0};
                    final int[] count3 = {0};
                    final int[] countSum = {0};

                    final int[] s = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count1[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }
                                if (!b) {
                                    count1[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0];


                                }


                                setGameMoney(s[0]);

                            }
                        });

                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count2[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }
                                if (!b) {
                                    count2[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }

                                setGameMoney(s[0]);

                            }
                        });

                    }
                    for (int i = 1; i < LinearThree.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count3[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0] * count3[0];

                                }
                                if (!b) {
                                    count3[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0] * count3[0];


                                }

                                setGameMoney(s[0]);

                            }
                        });

                    }
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                    Select9(select_3, LinearThree);
                }
                if (
                        "star_2_next_duplex".equals(code)
                                || "2min_star_2_next_duplex".equals(code)
                                || "sequence_star_2_next_duplex".equals(code)
                                || "3D_star_2_next_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_after, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    LinearLayout select_2 = (LinearLayout) inte.findViewById(R.id.select_2);
                    final int[] count1 = {0};
                    final int[] count2 = {0};

                    final int[] countSum = {0};

                    final int[] s = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count1[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0];

                                }
                                if (!b) {
                                    count1[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0];


                                }

                                setGameMoney(s[0]);

                            }
                        });

                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count2[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0];

                                }
                                if (!b) {
                                    count2[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0];

                                }
                                setGameMoney(s[0]);
                            }
                        });
                    }
                    Select9(select_1, LinearOne);
                    Select9(select_2, LinearTwo);
                }
                if (
                        "star_2_next_sum".equals(code)
                                || "2min_star_2_next_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_he1, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    final int[] counts = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    counts[0] = counts[0] + TwoHe1(Integer.parseInt(at.getText().toString().trim()));
                                }
                                if (!b) {
                                    counts[0] = counts[0] - TwoHe1(Integer.parseInt(at.getText().toString().trim()));
                                }
                                setGameMoney(counts[0]);
                            }
                        });


                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    counts[0] = counts[0] + TwoHe1(Integer.parseInt(at.getText().toString().trim()));
                                }
                                if (!b) {
                                    counts[0] = counts[0] - TwoHe1(Integer.parseInt(at.getText().toString().trim()));
                                }
                                setGameMoney(counts[0]);
                            }
                        });
                    }
                }
                if (
                        "star_2_next_group_sum".equals(code)
                                || "star_2_prev_group_sum".equals(code)
                                || "2min_star_2_next_group_sum".equals(code)
                                || "2min_star_2_prev_group_sum".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_2_star_he2, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    final int[] cSum = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    cSum[0] = cSum[0] + TwoZuHe(Integer.parseInt(at.getText().toString().trim()));
                                } else if (!b) {
                                    cSum[0] = cSum[0] - TwoZuHe(Integer.parseInt(at.getText().toString().trim()));
                                }
                                setGameMoney(cSum[0]);
                            }

                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    cSum[0] = cSum[0] + TwoZuHe(Integer.parseInt(at.getText().toString().trim()));
                                } else if (!b) {
                                    cSum[0] = cSum[0] - TwoZuHe(Integer.parseInt(at.getText().toString().trim()));
                                }
                                setGameMoney(cSum[0]);
                            }

                        });
                    }


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

                    final int[] count1 = {0};
                    final int[] count2 = {0};
                    final int[] countSum = {0};

                    final int[] s = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count1[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0];

                                }
                                if (!b) {
                                    count1[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0];
                                }
                                setGameMoney(s[0]);

                            }
                        });

                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count2[0]++;
                                    countSum[0]++;
                                    s[0] = count1[0] * count2[0];

                                }
                                if (!b) {
                                    count2[0]--;
                                    countSum[0]--;
                                    s[0] = count1[0] * count2[0];

                                }
                                setGameMoney(s[0]);
                            }
                        });
                    }

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
                                || "star_3_any_duplex".equals(code)
                                || "2min_star_3_any_duplex".equals(code)
                                || "star_4_any_duplex".equals(code)
                                || "2min_star_4_any_duplex".equals(code)
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
                    final int[] c = {0};
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] c3 = {0};
                    final int[] c4 = {0};
                    final int[] c5 = {0};

                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                    c1[0]++;
                                } else if (!b) {
                                    c[0]--;
                                    c1[0]--;
                                }
                                if ("sequence_fix".equals(code)) {
                                    setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                                }
                                if (
                                        "star_2_any_duplex".equals(code)
                                                || "2min_star_2_any_duplex".equals(code)
                                        ) {
                                    setGameMoney(c1[0] * (c2[0] + c3[0] + c4[0] + c5[0]) + c2[0] * (c3[0] + c4[0] + c5[0]) + c3[0] * (c4[0] + c5[0]) + c4[0] * c5[0]);
                                }
                                if ("fix".equals(code)
                                        || "2min_fix".equals(code)) {
                                    setGameMoney(c[0]);
                                }
                                if (
                                        "star_3_any_duplex".equals(code)
                                                || "2min_star_3_any_duplex".equals(code)
                                        ) {
                                    setGameMoney(c1[0] * c2[0] * c3[0] + c1[0] * c2[0] * c4[0] + c1[0] * c2[0] * c5[0] + c1[0] * c3[0] * c4[0] + c1[0] * c3[0] * c5[0] + c1[0] * c4[0] * c5[0] + c2[0] * c3[0] * c4[0] + c2[0] * c3[0] * c5[0] + c2[0] * c4[0] * c5[0] + c3[0] * c4[0] * c5[0]);
                                }
                                if (
                                        "star_4_any_duplex".equals(code)
                                                || "2min_star_4_any_duplex".equals(code)
                                        ) {
                                    nums = c1[0] * c2[0] * c3[0] * c4[0] + c1[0] * c2[0] * c3[0] * c5[0] + c1[0] * c2[0] * c4[0] * c5[0] + c1[0] * c3[0] * c4[0] * c5[0] + c2[0] * c3[0] * c4[0] * c5[0];
                                }
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                    c2[0]++;
                                } else if (!b) {
                                    c[0]--;
                                    c2[0]--;
                                }
                                if ("sequence_fix".equals(code)) {
                                    setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                                }
                                if (
                                        "star_2_any_duplex".equals(code)
                                                || "2min_star_2_any_duplex".equals(code)
                                        ) {
                                    setGameMoney(c1[0] * (c2[0] + c3[0] + c4[0] + c5[0]) + c2[0] * (c3[0] + c4[0] + c5[0]) + c3[0] * (c4[0] + c5[0]) + c4[0] * c5[0]);
                                }
                                if ("fix".equals(code)
                                        || "2min_fix".equals(code)) {
                                    setGameMoney(c[0]);
                                }
                                if (
                                        "star_3_any_duplex".equals(code)
                                                || "2min_star_3_any_duplex".equals(code)
                                        ) {
                                    setGameMoney(c1[0] * c2[0] * c3[0] + c1[0] * c2[0] * c4[0] + c1[0] * c2[0] * c5[0] + c1[0] * c3[0] * c4[0] + c1[0] * c3[0] * c5[0] + c1[0] * c4[0] * c5[0] + c2[0] * c3[0] * c4[0] + c2[0] * c3[0] * c5[0] + c2[0] * c4[0] * c5[0] + c3[0] * c4[0] * c5[0]);
                                }
                                if (
                                        "star_4_any_duplex".equals(code)
                                                || "2min_star_4_any_duplex".equals(code)
                                        ) {
                                    nums = c1[0] * c2[0] * c3[0] * c4[0] + c1[0] * c2[0] * c3[0] * c5[0] + c1[0] * c2[0] * c4[0] * c5[0] + c1[0] * c3[0] * c4[0] * c5[0] + c2[0] * c3[0] * c4[0] * c5[0];
                                }
                            }
                        });
                    }
                    for (int i = 1; i < LinearThree.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                    c3[0]++;
                                } else if (!b) {
                                    c[0]--;
                                    c3[0]--;
                                }
                                if ("sequence_fix".equals(code)) {
                                    setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                                }
                                if (
                                        "star_2_any_duplex".equals(code)
                                                || "2min_star_2_any_duplex".equals(code)
                                        ) {
                                    setGameMoney(c1[0] * (c2[0] + c3[0] + c4[0] + c5[0]) + c2[0] * (c3[0] + c4[0] + c5[0]) + c3[0] * (c4[0] + c5[0]) + c4[0] * c5[0]);
                                }
                                if ("fix".equals(code)
                                        || "2min_fix".equals(code)) {
                                    setGameMoney(c[0]);
                                }
                                if (
                                        "star_3_any_duplex".equals(code)
                                                || "2min_star_3_any_duplex".equals(code)
                                        ) {
                                    setGameMoney(c1[0] * c2[0] * c3[0] + c1[0] * c2[0] * c4[0] + c1[0] * c2[0] * c5[0] + c1[0] * c3[0] * c4[0] + c1[0] * c3[0] * c5[0] + c1[0] * c4[0] * c5[0] + c2[0] * c3[0] * c4[0] + c2[0] * c3[0] * c5[0] + c2[0] * c4[0] * c5[0] + c3[0] * c4[0] * c5[0]);
                                }
                                if (
                                        "star_4_any_duplex".equals(code)
                                                || "2min_star_4_any_duplex".equals(code)
                                        ) {
                                    nums = c1[0] * c2[0] * c3[0] * c4[0] + c1[0] * c2[0] * c3[0] * c5[0] + c1[0] * c2[0] * c4[0] * c5[0] + c1[0] * c3[0] * c4[0] * c5[0] + c2[0] * c3[0] * c4[0] * c5[0];
                                }
                            }
                        });
                    }
                    for (int i = 1; i < LinearFour.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearFour.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                    c4[0]++;
                                } else if (!b) {
                                    c[0]--;
                                    c4[0]--;
                                }
                                if ("sequence_fix".equals(code)) {
                                    setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                                }
                                if (
                                        "star_2_any_duplex".equals(code)
                                                || "2min_star_2_any_duplex".equals(code)
                                        ) {
                                    setGameMoney(c1[0] * (c2[0] + c3[0] + c4[0] + c5[0]) + c2[0] * (c3[0] + c4[0] + c5[0]) + c3[0] * (c4[0] + c5[0]) + c4[0] * c5[0]);
                                }
                                if ("fix".equals(code)
                                        || "2min_fix".equals(code)) {
                                    setGameMoney(c[0]);
                                }
                                if (
                                        "star_3_any_duplex".equals(code)
                                                || "2min_star_3_any_duplex".equals(code)
                                        ) {
                                    setGameMoney(c1[0] * c2[0] * c3[0] + c1[0] * c2[0] * c4[0] + c1[0] * c2[0] * c5[0] + c1[0] * c3[0] * c4[0] + c1[0] * c3[0] * c5[0] + c1[0] * c4[0] * c5[0] + c2[0] * c3[0] * c4[0] + c2[0] * c3[0] * c5[0] + c2[0] * c4[0] * c5[0] + c3[0] * c4[0] * c5[0]);
                                }
                                if (
                                        "star_4_any_duplex".equals(code)
                                                || "2min_star_4_any_duplex".equals(code)
                                        ) {
                                    nums = c1[0] * c2[0] * c3[0] * c4[0] + c1[0] * c2[0] * c3[0] * c5[0] + c1[0] * c2[0] * c4[0] * c5[0] + c1[0] * c3[0] * c4[0] * c5[0] + c2[0] * c3[0] * c4[0] * c5[0];
                                }
                            }
                        });
                    }
                    for (int i = 1; i < LinearFive.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearFive.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                    c5[0]++;
                                } else if (!b) {
                                    c[0]--;
                                    c5[0]--;
                                }
                                if ("sequence_fix".equals(code)) {
                                    setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                                }
                                if (
                                        "star_2_any_duplex".equals(code)
                                                || "2min_star_2_any_duplex".equals(code)
                                        ) {
                                    setGameMoney(c1[0] * (c2[0] + c3[0] + c4[0] + c5[0]) + c2[0] * (c3[0] + c4[0] + c5[0]) + c3[0] * (c4[0] + c5[0]) + c4[0] * c5[0]);
                                }
                                if ("fix".equals(code)
                                        || "2min_fix".equals(code)) {
                                    setGameMoney(c[0]);
                                }
                                if (
                                        "star_3_any_duplex".equals(code)
                                                || "2min_star_3_any_duplex".equals(code)
                                        ) {
                                    setGameMoney(c1[0] * c2[0] * c3[0] + c1[0] * c2[0] * c4[0] + c1[0] * c2[0] * c5[0] + c1[0] * c3[0] * c4[0] + c1[0] * c3[0] * c5[0] + c1[0] * c4[0] * c5[0] + c2[0] * c3[0] * c4[0] + c2[0] * c3[0] * c5[0] + c2[0] * c4[0] * c5[0] + c3[0] * c4[0] * c5[0]);
                                }
                                if (
                                        "star_4_any_duplex".equals(code)
                                                || "2min_star_4_any_duplex".equals(code)
                                        ) {
                                    nums = c1[0] * c2[0] * c3[0] * c4[0] + c1[0] * c2[0] * c3[0] * c5[0] + c1[0] * c2[0] * c4[0] * c5[0] + c1[0] * c3[0] * c4[0] * c5[0] + c2[0] * c3[0] * c4[0] * c5[0];
                                }
                            }
                        });
                    }
                }
                /*if (

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_location, null, false);

                }*/
                if (
                        "star_2_any_single".equals(code)
                                || "2min_star_2_any_single".equals(code)
                                || "star_2_any_group_single".equals(code)
                                || "2min_star_2_any_group_single".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_optional2, null, false);
                    final EditText g2_editText = (EditText) inte.findViewById(R.id.optional2_editText);
                    LinearLayout linearCheck = (LinearLayout) inte.findViewById(R.id.LinearCheck);
                    final TextView checkNum = (TextView) inte.findViewById(R.id.CheckNum);
                    final TextView FangAnNum = (TextView) inte.findViewById(R.id.FangAnNum);
                    final int[] cc2 = {0};
                    g2_editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String s = charSequence.toString();
                            int seleNums = 2;
                            List<String> NS;
                            if (s.length() > seleNums - 1) {
                                if (s.contains(",")) {
                                    NS = new ArrayList<String>();
                                    String[] sp = s.split(",");
                                    Log.d("单式模式的Sp", sp.length + "");
                                    int c = 0;
                                    for (int i3 = 0; i3 < sp.length; i3++) {
                                        String s1 = sp[i3];
                                        if (s1.length() == seleNums) {
                                            for (int i4 = 0; i4 < s1.length(); i4++) {
                                                String sub = s1.substring(i4, i4 + 1);
                                                Log.d("判断是不是数字", sub.matches("\\d+") + "");
                                                if (sub.matches("\\d+")) {
                                                    if (i4 == seleNums - 1) {
                                                        if (s.substring(i4, i4 + 1).matches("\\d+")) {
                                                            NS.add(s1);
                                                            c++;
                                                        }
                                                    }
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if ("star_2_any_group_single".equals(code)
                                            || "2min_star_2_any_group_single".equals(code)) {
                                        cc2[0] = 0;
                                        for (int i3 = 0; i3 < NS.size(); i3++) {
                                            String ss1 = NS.get(i3);
                                            int s1 = Integer.parseInt(ss1.substring(0, 1));
                                            int s2 = Integer.parseInt(ss1.substring(1, 2));

                                            if (s1 == s2) {
                                            } else {
                                                cc2[0]++;
                                            }
                                        }
                                        setGameMoney(cc2[0] * Integer.parseInt(FangAnNum.getText().toString()));
                                    } else {
                                        setGameMoney(NS.size() * Integer.parseInt(FangAnNum.getText().toString()));
                                    }


                                } else {
                                    NS = new ArrayList<String>();
                                    if (s.length() == seleNums) {
                                        for (int i3 = 0; i3 < s.length(); i3++) {
                                            String sub = s.substring(i3, i3 + 1);
                                            Log.d("判断是不是数字", sub.matches("\\d+") + "");
                                            if (sub.matches("\\d+")) {
                                                if (i3 == seleNums - 1) {
                                                    if (s.substring(i3, i3 + 1).matches("\\d+")) {
                                                        cc2[0] = 1;
                                                        NS.add(s);
                                                    }
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                        if ("star_2_any_group_single".equals(code)
                                                || "2min_star_2_any_group_single".equals(code)) {

                                            for (int i3 = 0; i3 < NS.size(); i3++) {
                                                String ss1 = NS.get(i3);
                                                int s1 = Integer.parseInt(ss1.substring(0, 1));
                                                int s2 = Integer.parseInt(ss1.substring(1, 2));

                                                if (s1 == s2) {
                                                    setGameMoney(0);
                                                } else {
                                                    setGameMoney(1 * Integer.parseInt(FangAnNum.getText().toString()));
                                                    cc2[0] = 1;
                                                }
                                            }

                                        } else {
                                            cc2[0] = 1;
                                            setGameMoney(1 * Integer.parseInt(FangAnNum.getText().toString()));
                                        }
                                    } else {
                                        setGameMoney(0);
                                    }
                                }

                            } else {
                                setGameMoney(0);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    final int[] c = {0};
                    for (int i = 0; i < linearCheck.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linearCheck.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                    checkNum.setText(c[0] + "");
                                    FangAnNum.setText(c[0] * (c[0] - 1) / 2 + "");
                                    setGameMoney(c[0] * (c[0] - 1) / 2 * cc2[0]);
                                } else {
                                    c[0]--;
                                    checkNum.setText(c[0] + "");
                                    FangAnNum.setText(c[0] * (c[0] - 1) / 2 + "");
                                    setGameMoney(c[0] * (c[0] - 1) / 2 * cc2[0]);
                                }
                            }
                        });
                    }
                    ((CheckBox) linearCheck.getChildAt(3)).setChecked(true);
                    ((CheckBox) linearCheck.getChildAt(4)).setChecked(true);
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
                    //   g2_editText.setInputType(InputType.TYPE_NULL);

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
                            if (text.length() == 0) {
                                return;
                            }
                            if (!",".equals(text.substring(text.length() - 1))) {
                                g2_editText.setText(g2_editText.getText().toString().trim() + ",");
                            }
                        }
                    });
                    numDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString().trim();
                            if (text.length() == 0) {
                                return;
                            }
                            if (text.length() > 0) {
                                text = text.substring(0, text.length() - 1);
                                g2_editText.setText(text);
                            }
                        }
                    });

                }
                if (
                        "star_2_any_sum".equals(code)
                                || "2min_star_2_any_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_optional2_he, null, false);
                    LinearLayout linearCheck = (LinearLayout) inte.findViewById(R.id.LinearCheck);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    final TextView checkNum = (TextView) inte.findViewById(R.id.CheckNum);
                    final TextView FangAnNum = (TextView) inte.findViewById(R.id.FangAnNum);
                    final int[] c = {0};
                    final int[] sum = {0};
                    for (int i = 0; i < linearCheck.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linearCheck.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;

                                } else {
                                    c[0]--;

                                }
                                checkNum.setText(c[0] + "");
                                FangAnNum.setText(c[0] * (c[0] - 1) / 2 + "");
                                setGameMoney(Integer.parseInt(FangAnNum.getText().toString()) * sum[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    sum[0] = sum[0] + getNumHe2Zhi(Integer.parseInt(at.getText().toString()));
                                } else {
                                    sum[0] = sum[0] - getNumHe2Zhi(Integer.parseInt(at.getText().toString()));
                                }
                                setGameMoney(Integer.parseInt(FangAnNum.getText().toString()) * sum[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    sum[0] = sum[0] + getNumHe2Zhi(Integer.parseInt(at.getText().toString()));
                                } else {
                                    sum[0] = sum[0] - getNumHe2Zhi(Integer.parseInt(at.getText().toString()));
                                }
                                setGameMoney(Integer.parseInt(FangAnNum.getText().toString()) * sum[0]);
                            }
                        });
                    }
                    ((CheckBox) linearCheck.getChildAt(3)).setChecked(true);
                    ((CheckBox) linearCheck.getChildAt(4)).setChecked(true);

                }
                if (
                        "star_2_any_group_duplex".equals(code)
                                || "2min_star_2_any_group_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional2_fu_3_6, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout linearCheck = (LinearLayout) inte.findViewById(R.id.LinearCheck);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    final TextView checkNum = (TextView) inte.findViewById(R.id.CheckNum);
                    final TextView FangAnNum = (TextView) inte.findViewById(R.id.FangAnNum);
                    final int[] c = {0};
                    final int[] sum = {0};
                    for (int i = 0; i < linearCheck.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linearCheck.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                checkNum.setText(c[0] + "");
                                FangAnNum.setText(c[0] * (c[0] - 1) / 2 + "");
                                setGameMoney(Integer.parseInt(FangAnNum.getText().toString()) * (sum[0] * (sum[0] - 1) / 2));
                            }
                        });
                    }
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    sum[0]++;
                                } else {
                                    sum[0]--;
                                }

                                setGameMoney(Integer.parseInt(FangAnNum.getText().toString()) * (sum[0] * (sum[0] - 1) / 2));
                            }
                        });
                    }

                    Select9(select_1, LinearOne);
                }
               /* if (
                        "star_2_any_group_single".equals(code)
                                || "2min_star_2_any_group_single".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional3_dan_3_6_hun, null, false);
                    final EditText g2_editText = (EditText) inte.findViewById(R.id.optional2_editText);
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
                            if (text.length() == 0) {
                                return;
                            }
                            if (!",".equals(text.substring(text.length() - 1))) {
                                g2_editText.setText(g2_editText.getText().toString().trim() + ",");
                            }
                        }
                    });
                    numDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString().trim();
                            if (text.length() == 0) {
                                return;
                            }
                            if (text.length() > 0) {
                                text = text.substring(0, text.length() - 1);
                                g2_editText.setText(text);
                            }
                        }
                    });
                }*/
                if (
                        "star_2_any_group_sum".equals(code)
                                || "2min_star_2_any_group_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional2_he, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout linearCheck = (LinearLayout) inte.findViewById(R.id.LinearCheck);
                    final TextView checkNum = (TextView) inte.findViewById(R.id.CheckNum);
                    final TextView FangAnNum = (TextView) inte.findViewById(R.id.FangAnNum);
                    final int[] c = {0};
                    final int[] sum = {0};
                    for (int i = 0; i < linearCheck.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linearCheck.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;

                                } else {
                                    c[0]--;

                                }
                                checkNum.setText(c[0] + "");
                                FangAnNum.setText(c[0] * (c[0] - 1) / 2 + "");
                                setGameMoney(Integer.parseInt(FangAnNum.getText().toString()) * sum[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    sum[0] = sum[0] + getNumHe22Fu(Integer.parseInt(at.getText().toString()));
                                } else {
                                    sum[0] = sum[0] - getNumHe22Fu(Integer.parseInt(at.getText().toString()));
                                }
                                setGameMoney(Integer.parseInt(FangAnNum.getText().toString()) * sum[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    sum[0] = sum[0] + getNumHe22Fu(Integer.parseInt(at.getText().toString()));
                                } else {
                                    sum[0] = sum[0] - getNumHe22Fu(Integer.parseInt(at.getText().toString()));
                                }
                                setGameMoney(Integer.parseInt(FangAnNum.getText().toString()) * sum[0]);
                            }
                        });
                    }
                    ((CheckBox) linearCheck.getChildAt(3)).setChecked(true);
                    ((CheckBox) linearCheck.getChildAt(4)).setChecked(true);
                }
                if (
                        "star_3_any_single".equals(code)
                                || "2min_star_3_any_single".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_optional3_dan, null, false);
                    final EditText g2_editText = (EditText) inte.findViewById(R.id.optional2_editText);
                    LinearLayout linearCheck = (LinearLayout) inte.findViewById(R.id.LinearCheck);
                    final TextView checkNum = (TextView) inte.findViewById(R.id.CheckNum);
                    final TextView FANum = (TextView) inte.findViewById(R.id.FangAnNum);

                    final int[] c = {0};
                    final int[] count = {0};
                    for (int i = 0; i < linearCheck.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linearCheck.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                checkNum.setText(c[0] + "");
                                int c1 = c[0] * (c[0] - 1) * (c[0] - 2);
                                FANum.setText(c1 / 6 + "");
                            }
                        });
                    }
                    ((CheckBox) linearCheck.getChildAt(2)).setChecked(true);
                    ((CheckBox) linearCheck.getChildAt(3)).setChecked(true);
                    ((CheckBox) linearCheck.getChildAt(4)).setChecked(true);
                    g2_editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String s = charSequence.toString();
                            int seleNums = 3;
                            List<String> NS;
                            if (s.length() > seleNums - 1) {
                                if (s.contains(",")) {
                                    NS = new ArrayList<String>();
                                    String[] sp = s.split(",");
                                    Log.d("单式模式的Sp", sp.length + "");
                                    int c = 0;
                                    count[0] = 0;
                                    for (int i3 = 0; i3 < sp.length; i3++) {
                                        String s1 = sp[i3];
                                        if (s1.length() == seleNums) {
                                            for (int i4 = 0; i4 < s1.length(); i4++) {
                                                String sub = s1.substring(i4, i4 + 1);
                                                Log.d("判断是不是数字", sub.matches("\\d+") + "");
                                                if (sub.matches("\\d+")) {
                                                    if (i4 == seleNums - 1) {
                                                        if (s.substring(i4, i4 + 1).matches("\\d+")) {
                                                            NS.add(s1);
                                                            c++;
                                                            count[0]++;
                                                        }
                                                    }
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    Log.d("组三单式Nums注", c + "");
                                    setGameMoney(c * Integer.parseInt(FANum.getText().toString()));


                                } else {
                                    count[0] = 0;
                                    NS = new ArrayList<String>();
                                    if (s.length() == seleNums) {
                                        for (int i3 = 0; i3 < s.length(); i3++) {
                                            String sub = s.substring(i3, i3 + 1);
                                            Log.d("判断是不是数字", sub.matches("\\d+") + "");
                                            if (sub.matches("\\d+")) {
                                                if (i3 == seleNums - 1) {
                                                    if (s.substring(i3, i3 + 1).matches("\\d+")) {
                                                        NS.add(s);
                                                        count[0] = 1;
                                                    }
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                    if (NS.size() == 0) {
                                        setGameMoney(0 * Integer.parseInt(FANum.getText().toString()));
                                    } else {
                                        setGameMoney(1 * Integer.parseInt(FANum.getText().toString()));
                                    }
                                }

                            } else {
                                setGameMoney(0 * Integer.parseInt(FANum.getText().toString()));
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });


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
                    TextView kong = (TextView) inte.findViewById(R.id.kong);

                    TextView numDelete = (TextView) inte.findViewById(R.id.numDelete);
                    // g2_editText.setInputType(InputType.TYPE_NULL);
                    num0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "0");

                        }
                    });
                    num1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "1");
                        }
                    });
                    num2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "2");
                        }
                    });
                    num3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "3");
                        }
                    });
                    num4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "4");
                        }
                    });
                    num5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "5");
                        }
                    });
                    num6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "6");
                        }
                    });
                    num7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "7");
                        }
                    });
                    num8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "8");
                        }
                    });
                    num9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "9");
                        }
                    });
                    numd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (!",".equals(text.substring(text.length() - 1))) {
                                g2_editText.setText(g2_editText.getText().toString() + ",");
                            }
                        }
                    });

                    kong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (!" ".equals(text.substring(text.length() - 1))) {
                                g2_editText.setText(g2_editText.getText().toString().trim() + " ");
                            }
                        }
                    });
                    numDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (text.length() > 0) {
                                text = text.substring(0, text.length() - 1);
                                g2_editText.setText(text);
                            }
                        }
                    });
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
                    final EditText g2_editText = (EditText) inte.findViewById(R.id.optional2_editText);
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
                    TextView kong = (TextView) inte.findViewById(R.id.kong);

                    TextView numDelete = (TextView) inte.findViewById(R.id.numDelete);
                    g2_editText.setInputType(InputType.TYPE_NULL);
                    int c = 0;
                    num0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "0");

                        }
                    });
                    num1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "1");
                        }
                    });
                    num2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "2");
                        }
                    });
                    num3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "3");
                        }
                    });
                    num4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "4");
                        }
                    });
                    num5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "5");
                        }
                    });
                    num6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "6");
                        }
                    });
                    num7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "7");
                        }
                    });
                    num8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "8");
                        }
                    });
                    num9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "9");
                        }
                    });
                    numd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (!",".equals(text.substring(text.length() - 1))) {
                                g2_editText.setText(g2_editText.getText().toString() + ",");
                            }
                        }
                    });

                    kong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (!" ".equals(text.substring(text.length() - 1))) {
                                g2_editText.setText(g2_editText.getText().toString().trim() + " ");
                            }
                        }
                    });
                    numDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (text.length() > 0) {
                                text = text.substring(0, text.length() - 1);
                                g2_editText.setText(text);
                            }
                        }
                    });
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
                    final EditText g2_editText = (EditText) inte.findViewById(R.id.optional2_editText);
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
                    TextView kong = (TextView) inte.findViewById(R.id.kong);

                    TextView numDelete = (TextView) inte.findViewById(R.id.numDelete);
                    g2_editText.setInputType(InputType.TYPE_NULL);
                    int c = 0;
                    num0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "0");

                        }
                    });
                    num1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "1");
                        }
                    });
                    num2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "2");
                        }
                    });
                    num3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "3");
                        }
                    });
                    num4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "4");
                        }
                    });
                    num5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "5");
                        }
                    });
                    num6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "6");
                        }
                    });
                    num7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "7");
                        }
                    });
                    num8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "8");
                        }
                    });
                    num9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            g2_editText.setText(g2_editText.getText().toString() + "9");
                        }
                    });
                    numd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (!",".equals(text.substring(text.length() - 1))) {
                                g2_editText.setText(g2_editText.getText().toString() + ",");
                            }
                        }
                    });

                    kong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (!" ".equals(text.substring(text.length() - 1))) {
                                g2_editText.setText(g2_editText.getText().toString().trim() + " ");
                            }
                        }
                    });
                    numDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = g2_editText.getText().toString();
                            if (text.length() == 0) {
                                return;
                            }
                            if (text.length() > 0) {
                                text = text.substring(0, text.length() - 1);
                                g2_editText.setText(text);
                            }
                        }
                    });
                }
                if (
                        "star_4_any_group_24".equals(code)
                                || "star_4_any_group_6".equals(code)
                                || "2min_star_4_any_group_6".equals(code)
                                || "2min_star_4_any_group_24".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_optional4_24_6, null, false);
                    TextView names = (TextView) inte.findViewById(R.id.name);
                    names.setText(name.get(position));
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, linear1);

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
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else if (!b) {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else if (!b) {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                }
                if (
                        "star_2_next_special".equals(code)
                                || "2min_star_2_next_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_2_after, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else if (!b) {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else if (!b) {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                }
                if (
                        "star_3_prev_special".equals(code)
                                || "2min_star_3_prev_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_3_before, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout linear3 = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] c3 = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else if (!b) {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] * c2[0] * c3[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else if (!b) {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] * c2[0] * c3[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear3.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear3.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c3[0]++;
                                } else if (!b) {
                                    c3[0]--;
                                }
                                setGameMoney(c1[0] * c2[0] * c3[0]);
                            }
                        });
                    }
                }
                if (
                        "star_3_next_special".equals(code)
                                || "2min_star_3_next_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_3_after, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout linear3 = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] c3 = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else if (!b) {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] * c2[0] * c3[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else if (!b) {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] * c2[0] * c3[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear3.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear3.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c3[0]++;
                                } else if (!b) {
                                    c3[0]--;
                                }
                                setGameMoney(c1[0] * c2[0] * c3[0]);
                            }
                        });
                    }
                }
                if (
                        "banker_player".equals(code)
                                || "2min_banker_player".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_ldle, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};

                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else if (!b) {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }

                }
                if (
                        "equal".equals(code)
                                || "2min_equal".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_flat, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};

                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else if (!b) {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "same_two".equals(code)
                                || "2min_same_two".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_sub, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};

                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else if (!b) {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "same_three".equals(code)
                                || "2min_same_three".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_leopard, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};

                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else if (!b) {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "heavenly_king".equals(code)
                                || "2min_heavenly_king".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_king, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};

                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else if (!b) {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "sum_special".equals(code)
                                || "2min_sum_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g3_sum_big_dan, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};

                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else if (!b) {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
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
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};

                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else if (!b) {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
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
                    final List<String> s1 = new ArrayList<String>();
                    final List<String> s2 = new ArrayList<String>();
                    final List<String> s3 = new ArrayList<String>();

                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    s1.add(at.getText().toString().trim());
                                } else if (!b) {
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        if (s1.get(i1).equals(at.getText().toString().trim())) {
                                            s1.remove(i1);
                                        }
                                    }
                                }

                                int coun = 0;
                                for (int m = 0; m < s1.size(); m++) {
                                    for (int j = 0; j < s2.size(); j++) {
                                        if (Integer.parseInt(s1.get(m)) == Integer.parseInt(s2.get(j)))
                                            continue;
                                        else {
                                            for (int k = 0; k < s3.size(); k++) {
                                                if (Integer.parseInt(s1.get(m)) == Integer.parseInt(s3.get(k)) || Integer.parseInt(s2.get(j)) == Integer.parseInt(s3.get(k)))
                                                    continue;
                                                else coun++;
                                            }
                                        }
                                    }
                                }
                                setGameMoney(coun);
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    s2.add(at.getText().toString().trim());
                                } else if (!b) {
                                    for (int i1 = 0; i1 < s2.size(); i1++) {
                                        if (s2.get(i1).equals(at.getText().toString().trim())) {
                                            s2.remove(i1);
                                        }
                                    }
                                }

                                int coun = 0;
                                for (int m = 0; m < s1.size(); m++) {
                                    for (int j = 0; j < s2.size(); j++) {
                                        if (Integer.parseInt(s1.get(m)) == Integer.parseInt(s2.get(j)))
                                            continue;
                                        else {
                                            for (int k = 0; k < s3.size(); k++) {
                                                if (Integer.parseInt(s1.get(m)) == Integer.parseInt(s3.get(k)) || Integer.parseInt(s2.get(j)) == Integer.parseInt(s3.get(k)))
                                                    continue;
                                                else coun++;
                                            }
                                        }
                                    }
                                }
                                setGameMoney(coun);
                            }
                        });
                    }
                    for (int i = 1; i < LinearThree.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    s3.add(at.getText().toString().trim());
                                } else if (!b) {
                                    for (int i1 = 0; i1 < s3.size(); i1++) {
                                        if (s3.get(i1).equals(at.getText().toString().trim())) {
                                            s3.remove(i1);
                                        }
                                    }
                                }

                                int coun = 0;
                                for (int m = 0; m < s1.size(); m++) {
                                    for (int j = 0; j < s2.size(); j++) {
                                        if (Integer.parseInt(s1.get(m)) == Integer.parseInt(s2.get(j)))
                                            continue;
                                        else {
                                            for (int k = 0; k < s3.size(); k++) {
                                                if (Integer.parseInt(s1.get(m)) == Integer.parseInt(s3.get(k)) || Integer.parseInt(s2.get(j)) == Integer.parseInt(s3.get(k)))
                                                    continue;
                                                else coun++;
                                            }
                                        }
                                    }
                                }
                                setGameMoney(coun);
                            }
                        });
                    }
                }
                if (
                        "eleven_star_3_prev_group_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_star_11_5_zu_fu, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                    final int[] n = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    n[0]++;
                                } else if (!b) {
                                    n[0]--;
                                }
                                setGameMoney(n[0] * (n[0] - 1) * (n[0] - 2) / 6);
                            }
                        });
                    }
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
                    final List<String> s1 = new ArrayList<String>();
                    final List<String> s2 = new ArrayList<String>();
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    s1.add(at.getText().toString().trim());
                                } else if (!b) {
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        if (s1.get(i1).equals(at.getText().toString().trim())) {
                                            s1.remove(i1);
                                        }
                                    }
                                }
                                int coun = 0;
                                for (int m = 0; m < s1.size(); m++) {
                                    for (int j = 0; j < s2.size(); j++) {
                                        if (Integer.parseInt(s1.get(m)) == Integer.parseInt(s2.get(j)))
                                            continue;
                                        coun += 1;
                                    }
                                }
                                setGameMoney(coun);
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    s2.add(at.getText().toString().trim());
                                } else if (!b) {
                                    for (int i1 = 0; i1 < s2.size(); i1++) {
                                        if (s2.get(i1).equals(at.getText().toString().trim())) {
                                            s2.remove(i1);
                                        }
                                    }
                                }
                                int coun = 0;
                                for (int m = 0; m < s1.size(); m++) {
                                    for (int j = 0; j < s2.size(); j++) {
                                        if (Integer.parseInt(s1.get(m)) == Integer.parseInt(s2.get(j)))
                                            continue;
                                        coun += 1;
                                    }
                                }
                                setGameMoney(coun);

                            }
                        });
                    }
                }
                if (
                        "eleven_star_2_prev_group_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_2_star_11_5_zu_fu, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                    final int[] c = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else if (!b) {
                                    c[0]--;
                                }
                                setGameMoney(c[0] * (c[0] - 1) / 2);
                            }
                        });
                    }
                }
                if (
                        "eleven_star_3_prev_no_fix".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_nolocation_11_5, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);

                    final int[] c = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else if (!b) {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
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
                    final int[] count1 = {0};
                    final int[] count2 = {0};
                    final int[] count3 = {0};


                    final int[] s = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count1[0]++;
                                    s[0] = count1[0] + count2[0] + count3[0];
                                }
                                if (!b) {
                                    count1[0]--;
                                    s[0] = count1[0] + count2[0] + count3[0];
                                }


                                setGameMoney(s[0]);

                            }
                        });

                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count2[0]++;
                                    s[0] = count1[0] + count2[0] + count3[0];

                                }
                                if (!b) {
                                    count2[0]--;
                                    s[0] = count1[0] + count2[0] + count3[0];

                                }

                                setGameMoney(s[0]);

                            }
                        });

                    }
                    for (int i = 1; i < LinearThree.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count3[0]++;
                                    s[0] = count1[0] + count2[0] + count3[0];
                                }
                                if (!b) {
                                    count3[0]--;
                                    s[0] = count1[0] + count2[0] + count3[0];
                                }

                                setGameMoney(s[0]);

                            }
                        });

                    }
                }
                if (
                        "eleven_even_or_odd".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_11_5_single_double, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);


                    final int[] count1 = {0};
                    final int[] count2 = {0};


                    final int[] s = {0};
                    for (int i = 0; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count1[0]++;
                                    s[0] = count1[0] + count2[0];
                                }
                                if (!b) {
                                    count1[0]--;
                                    s[0] = count1[0] + count2[0];
                                }


                                setGameMoney(s[0]);

                            }
                        });

                    }
                    for (int i = 0; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count2[0]++;
                                    s[0] = count1[0] + count2[0];

                                }
                                if (!b) {
                                    count2[0]--;
                                    s[0] = count1[0] + count2[0];
                                }
                                setGameMoney(s[0]);

                            }
                        });

                    }

                }
                if (
                        "eleven_middle".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_quwei_11_5_caizhong, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);

                    final int[] count1 = {0};

                    final int[] s = {0};
                    for (int i = 0; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count1[0]++;
                                }
                                if (!b) {
                                    count1[0]--;
                                }
                                setGameMoney(count1[0]);

                            }
                        });

                    }
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
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    TextView names = (TextView) inte.findViewById(R.id.name);
                    names.setText(name.get(position));
                    final int[] count = {0};
                    for (int i = 1; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count[0]++;
                                } else if (!b) {
                                    count[0]--;
                                }
                                AnyOne(code, count[0]);
                            }
                        });
                    }
                    for (int i = 1; i < linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count[0]++;
                                } else if (!b) {
                                    count[0]--;
                                }
                                AnyOne(code, count[0]);
                            }
                        });
                    }
                }
                if (
                        "PK10_1st_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_champion1, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                    final int[] count = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    count[0]++;
                                } else if (!b) {
                                    count[0]--;
                                }
                                setGameMoney(count[0]);
                            }
                        });
                    }

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
                    final int[] count = {0};
                    final int[] count2 = {0};

                    final List<String> s1 = new ArrayList<String>();
                    final List<String> s2 = new ArrayList<String>();
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                final int[] k = {0};
                                if (b) {
                                    s1.add(at.getText().toString().trim());
                                    count[0]++;
                                } else if (!b) {
                                    count[0]--;
                                    for (int i1 = 0; i1 < s1.size(); i1++) {
                                        if (at.getText().toString().trim().equals(s1.get(i1))) {
                                            s1.remove(i1);
                                        }
                                    }
                                }
                                for (int i1 = 0; i1 < s1.size(); i1++) {
                                    for (int i2 = 0; i2 < s2.size(); i2++) {
                                        if (s1.get(i1).equals(s2.get(i2))) {
                                            k[0]++;
                                        }
                                    }
                                }
                                Log.d("Count Count2 k", count[0] + "  " + count2[0] + "  " + k[0]);
                                setGameMoney(count[0] * count2[0] - k[0]);
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                final int[] k = {0};
                                if (b) {
                                    count2[0]++;
                                    s2.add(at.getText().toString().trim());
                                } else if (!b) {
                                    count2[0]--;
                                    for (int i1 = 0; i1 < s2.size(); i1++) {
                                        if (at.getText().toString().trim().equals(s2.get(i1))) {
                                            s2.remove(i1);
                                        }
                                    }
                                }
                                for (int i1 = 0; i1 < s1.size(); i1++) {
                                    for (int i2 = 0; i2 < s2.size(); i2++) {
                                        if (s1.get(i1).equals(s2.get(i2))) {
                                            k[0]++;
                                        }
                                    }
                                }
                                Log.d("Count Count2 k", count[0] + "  " + count2[0] + "  " + k[0]);
                                setGameMoney(count[0] * count2[0] - k[0]);
                            }
                        });
                    }
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
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] c3 = {0};

                    final List<Integer> int1 = new ArrayList<Integer>();
                    final List<Integer> int2 = new ArrayList<Integer>();
                    final List<Integer> int3 = new ArrayList<Integer>();
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                    int1.add(Integer.parseInt(at.getText().toString()));
                                }
                                if (!b) {
                                    c1[0]--;
                                    for (int j = 0; j < int1.size(); j++) {
                                        if (Integer.parseInt(at.getText().toString()) == int1.get(j)) {
                                            int1.remove(j);
                                        }
                                    }
                                }
                                PK10_deplex(c1[0], c2[0], c3[0], int1, int2, int3);
                            }
                        });

                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                    int2.add(Integer.parseInt(at.getText().toString()));
                                }
                                if (!b) {
                                    c2[0]--;
                                    for (int j = 0; j < int2.size(); j++) {
                                        if (Integer.parseInt(at.getText().toString()) == int2.get(j)) {
                                            int2.remove(j);
                                        }
                                    }
                                }
                                PK10_deplex(c1[0], c2[0], c3[0], int1, int2, int3);
                            }
                        });

                    }
                    for (int i = 1; i < LinearThree.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c3[0]++;
                                    int3.add(Integer.parseInt(at.getText().toString()));
                                }
                                if (!b) {
                                    c3[0]--;
                                    for (int j = 0; j < int3.size(); j++) {
                                        if (Integer.parseInt(at.getText().toString()) == int3.get(j)) {
                                            int3.remove(j);
                                        }
                                    }
                                }
                                PK10_deplex(c1[0], c2[0], c3[0], int1, int2, int3);
                            }
                        });

                    }

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
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] c3 = {0};
                    final int[] c4 = {0};
                    final int[] c5 = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                            }
                        });
                    }
                    for (int i = 1; i < LinearThree.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c3[0]++;
                                } else {
                                    c3[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                            }
                        });
                    }
                    for (int i = 1; i < LinearFour.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearFour.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c4[0]++;
                                } else {
                                    c4[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                            }
                        });
                    }
                    for (int i = 1; i < LinearFive.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearFive.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c5[0]++;
                                } else {
                                    c5[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                            }
                        });
                    }

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
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] c3 = {0};
                    final int[] c4 = {0};
                    final int[] c5 = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                            }
                        });
                    }
                    for (int i = 1; i < LinearThree.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c3[0]++;
                                } else {
                                    c3[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                            }
                        });
                    }
                    for (int i = 1; i < LinearFour.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearFour.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c4[0]++;
                                } else {
                                    c4[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                            }
                        });
                    }
                    for (int i = 1; i < LinearFive.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearFive.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c5[0]++;
                                } else {
                                    c5[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0]);
                            }
                        });
                    }
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
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] c3 = {0};
                    final int[] c4 = {0};
                    final int[] c5 = {0};
                    final int[] c6 = {0};
                    final int[] c7 = {0};
                    final int[] c8 = {0};
                    final int[] c9 = {0};
                    final int[] c10 = {0};
                    for (int i = 1; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear3.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear3.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c3[0]++;
                                } else {
                                    c3[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear4.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear4.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c4[0]++;
                                } else {
                                    c4[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear5.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear5.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c5[0]++;
                                } else {
                                    c5[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear6.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear6.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c6[0]++;
                                } else {
                                    c6[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear7.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear7.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c7[0]++;
                                } else {
                                    c7[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear8.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear8.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c8[0]++;
                                } else {
                                    c8[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear9.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear9.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c9[0]++;
                                } else {
                                    c9[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear10.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear10.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c10[0]++;
                                } else {
                                    c10[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                }
                if (
                        "PK10_1st_special".equals(code)
                                || "PK10_2nd_special".equals(code)
                                || "PK10_3th_special".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_big_small, null, false);
                    TextView names = (TextView) inte.findViewById(R.id.name);
                    names.setText(name.get(position));
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "PK10_1st_odd_even".equals(code)
                                || "PK10_2nd_odd_even".equals(code)
                                || "PK10_3th_odd_even".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_dan_shuang, null, false);
                    TextView names = (TextView) inte.findViewById(R.id.name);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    names.setText(name.get(position));
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "PK10_1st_2nd_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_champion2_he, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);

                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "PK10_1st_2nd_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_beijing10_big_small_dan_shuang, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);

                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "PK10_toradora".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing10_dragon_tiger, null, false);
                    RadioGroup rg1 = (RadioGroup) inte.findViewById(R.id.Rg1);
                    RadioGroup rg2 = (RadioGroup) inte.findViewById(R.id.Rg2);
                    RadioGroup rg3 = (RadioGroup) inte.findViewById(R.id.Rg3);
                    RadioGroup rg4 = (RadioGroup) inte.findViewById(R.id.Rg4);
                    RadioGroup rg5 = (RadioGroup) inte.findViewById(R.id.Rg5);
                    final int[] ins = new int[]{0, 0, 0, 0, 0};
                    for (int i = 0; i < rg1.getChildCount(); i++) {
                        RadioButton at = (RadioButton) rg1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    ins[0] = 1;
                                }
                                int count = 0;
                                for (int i1 = 0; i1 < ins.length; i1++) {
                                    if (ins[i1] == 1) {
                                        count++;
                                    }
                                }
                                setGameMoney(count);
                            }
                        });
                    }
                    for (int i = 0; i < rg2.getChildCount(); i++) {
                        RadioButton at = (RadioButton) rg2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    ins[1] = 1;
                                }
                                int count = 0;
                                for (int i1 = 0; i1 < ins.length; i1++) {
                                    if (ins[i1] == 1) {
                                        count++;
                                    }
                                }
                                setGameMoney(count);
                            }
                        });
                    }
                    for (int i = 0; i < rg3.getChildCount(); i++) {
                        RadioButton at = (RadioButton) rg3.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    ins[2] = 1;
                                }
                                int count = 0;
                                for (int i1 = 0; i1 < ins.length; i1++) {
                                    if (ins[i1] == 1) {
                                        count++;
                                    }
                                }
                                setGameMoney(count);
                            }
                        });
                    }
                    for (int i = 0; i < rg4.getChildCount(); i++) {
                        RadioButton at = (RadioButton) rg4.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    ins[3] = 1;
                                }
                                int count = 0;
                                for (int i1 = 0; i1 < ins.length; i1++) {
                                    if (ins[i1] == 1) {
                                        count++;
                                    }
                                }
                                setGameMoney(count);
                            }
                        });
                    }
                    for (int i = 0; i < rg5.getChildCount(); i++) {
                        RadioButton at = (RadioButton) rg5.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    ins[4] = 1;
                                }
                                int count = 0;
                                for (int i1 = 0; i1 < ins.length; i1++) {
                                    if (ins[i1] == 1) {
                                        count++;
                                    }
                                }
                                setGameMoney(count);
                            }
                        });
                    }

                }
                if (
                        "k3_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_he, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);

                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "k3_triple_all".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_same_tong, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);

                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "k3_triple".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_same_dan, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);

                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "k3_double_simple".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_2_same_fu, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);

                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "k3_double_standard".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_2_same_bzxh, null, false);
                    final LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        final int finalI = i;
                        ((CheckBox) linear1.getChildAt(i)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                if (b) {
                                    ((CheckBox) linear2.getChildAt(finalI)).setChecked(false);
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }

                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        final int finalI = i;
                        ((CheckBox) linear2.getChildAt(i)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    ((CheckBox) linear1.getChildAt(finalI)).setChecked(false);
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }

                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }


                }
                if (
                        "k3_different_3_standard".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_nosame_num_bzxh, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);

                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                if (c[0] > 2) {
                                    setGameMoney(c[0] * (c[0] - 1) * (c[0] - 2) / 6);
                                }
                            }
                        });
                    }
                }
                if (
                        "k3_different_3_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_nosame_num_hzxh, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0] = c[0] + ThreeDifferentHe(at.getText().toString().trim());
                                } else {
                                    c[0] = c[0] - ThreeDifferentHe(at.getText().toString().trim());
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "k3_different_2_standard".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_nosame_num_bzxh, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0] * (c[0] - 1) / 2);
                            }
                        });
                    }
                }
                if (
                        "k3_consecutives_3_all".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_jiangsu_3_same_tong, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "kl8_sum_even_odd".equals(code)
                                || "kl8_sum_even_odd_2".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_quwei_dan_shuang, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "kl8_sum_max_min".equals(code)
                                || "kl8_sum_max_min_2".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_quwei_big_small, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "kl8_parity_disk".equals(code)
                                || "kl8_parity_disk_2".equals(code)) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_quwei_ji_ou, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "kl8_up_down".equals(code)
                                || "kl8_up_down_2".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_quwei_top_down, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "kl8_special".equals(code)
                                || "kl8_special_2".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_quwei_he, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
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
                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] c3 = {0};
                    final int[] c4 = {0};
                    final int[] c5 = {0};
                    final int[] c6 = {0};
                    final int[] c7 = {0};
                    final int[] c8 = {0};
                    final int[] c9 = {0};
                    final int[] c10 = {0};
                    for (int i = 1; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear3.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear3.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c3[0]++;
                                } else {
                                    c3[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear4.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear4.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c4[0]++;
                                } else {
                                    c4[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear5.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear5.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c5[0]++;
                                } else {
                                    c5[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear6.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear6.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c6[0]++;
                                } else {
                                    c6[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear7.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear7.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c7[0]++;
                                } else {
                                    c7[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear8.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear8.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c8[0]++;
                                } else {
                                    c8[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear9.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear9.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c9[0]++;
                                } else {
                                    c9[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear10.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear10.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c10[0]++;
                                } else {
                                    c10[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0] + c7[0] + c8[0] + c9[0] + c10[0]);
                            }
                        });
                    }
                }
                if (
                        "kl8_five_elements".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_beijing8_wuxing, null, false);
                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout Linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    final int[] c1 = {0};
                    final int[] c2 = {0};

                    for (int i = 1; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] + c2[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] + c2[0]);
                            }
                        });
                    }
                }
                if (
                        "sequence_star_3_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_5_3star_he, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    final int[] c = {0};
                    for (int i = 0; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0] = c[0] + get3_5Num(at.getText().toString().trim());
                                } else {
                                    c[0] = c[0] - get3_5Num(at.getText().toString().trim());
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0] = c[0] + get3_5Num(at.getText().toString().trim());
                                } else {
                                    c[0] = c[0] - get3_5Num(at.getText().toString().trim());
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearThree.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0] = c[0] + get3_5Num(at.getText().toString().trim());
                                } else {
                                    c[0] = c[0] - get3_5Num(at.getText().toString().trim());
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "sequence_star_3_group_3_duplex".equals(code)
                                || "3D_star_3_group_3_duplex".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_3_5_3star_33, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                    final int[] c = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0] * (c[0] - 1));
                            }
                        });
                    }
                }
                if (
                        "sequence_star_3_group_6_duplex".equals(code)
                                || "3D_star_3_group_6_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_3_5_3star_36, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                    final int[] c = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0] * (c[0] - 1) * (c[0] - 2) / 6);
                            }
                        });
                    }

                }
                if (
                        "sequence_star_3_group_sum".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g2_3_5_3star_he, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.Linear1);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.Linear2);
                    LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.Linear3);
                    final int[] c = {0};
                    for (int i = 0; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0] = c[0] + get3_5NumZuHe(at.getText().toString().trim());
                                } else {
                                    c[0] = c[0] - get3_5NumZuHe(at.getText().toString().trim());
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0] = c[0] + get3_5NumZuHe(at.getText().toString().trim());
                                } else {
                                    c[0] = c[0] - get3_5NumZuHe(at.getText().toString().trim());
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearThree.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0] = c[0] + get3_5NumZuHe(at.getText().toString().trim());
                                } else {
                                    c[0] = c[0] - get3_5NumZuHe(at.getText().toString().trim());
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "sequence_star_2_prev_group_duplex".equals(code)
                                || "star_2_next_group_duplex".equals(code)
                                || "2min_star_2_next_group_duplex".equals(code)

                                || "sequence_star_2_next_group_duplex".equals(code)
                                || "3D_star_2_prev_group_duplex".equals(code)
                                || "3D_star_2_next_group_duplex".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g4, null, false);
                    TextView names = (TextView) inte.findViewById(R.id.g4_name);
                    names.setText("组选");
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    final int[] c = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else if (!b) {
                                    c[0]--;
                                }
                                if ("star_2_next_group_duplex".equals(code)
                                        || "2min_star_2_next_group_duplex".equals(code)
                                        || "sequence_star_2_prev_group_duplex".equals(code)
                                        || "sequence_star_2_next_group_duplex".equals(code)
                                        || "3D_star_2_prev_group_duplex".equals(code)
                                        || "3D_star_2_next_group_duplex".equals(code)
                                        ) {
                                    setGameMoney(c[0] * (c[0] - 1) / 2);
                                }
                            }
                        });
                    }
                    Select9(select_1, LinearOne);
                }
                if (
                        "sequence_choose_1_no_fix".equals(code)
                                || "3D_choose_1_no_fix".equals(code)
                                || "3D_choose_2_no_fix".equals(code)

                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_5_nolocation1, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    TextView name = (TextView) inte.findViewById(R.id.name);
                    if ("3D_choose_2_no_fix".equals(code)) {
                        name.setText("二码");
                    }
                    Select9(select_1, LinearOne);
                    final int[] c = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else if (!b) {
                                    c[0]--;
                                }
                                if ("3D_choose_2_no_fix".equals(code)) {
                                    setGameMoney(c[0] * (c[0] - 1) / 2);
                                } else {
                                    setGameMoney(c[0]);
                                }

                            }
                        });
                    }
                }
                if (
                        "sequence_choose_2_no_fix".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_5_nolocation2, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout select_1 = (LinearLayout) inte.findViewById(R.id.select_1);
                    Select9(select_1, LinearOne);
                    final int[] c = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else if (!b) {
                                    c[0]--;
                                }
                                setGameMoney(c[0] * (c[0] - 1) / 2);

                            }
                        });
                    }
                }
                if (
                        "sequence_prev_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_5_before2_big_small, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);

                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    for (int i = 0; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                }
                if (
                        "sequence_next_special".equals(code)
                                || "3D_prev_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3_5_after2_big_small, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);

                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    for (int i = 0; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
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


                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
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

                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    for (int i = 1; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                    for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                }
                if (
                        "3D_next_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_3d2_big_small, null, false);
                    LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);

                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    for (int i = 0; i < LinearOne.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                    for (int i = 0; i < LinearTwo.getChildCount(); i++) {
                        final CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] * c2[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_orthocode".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_zheng, null, false);
                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.Linear1);
                    LinearLayout Linear2 = (LinearLayout) inte.findViewById(R.id.Linear2);
                    LinearLayout Linear3 = (LinearLayout) inte.findViewById(R.id.Linear3);
                    LinearLayout Linear4 = (LinearLayout) inte.findViewById(R.id.Linear4);
                    LinearLayout Linear5 = (LinearLayout) inte.findViewById(R.id.Linear5);
                    LinearLayout Linear6 = (LinearLayout) inte.findViewById(R.id.Linear6);

                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] c3 = {0};
                    final int[] c4 = {0};
                    final int[] c5 = {0};
                    final int[] c6 = {0};

                    for (int i = 1; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear3.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear3.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c3[0]++;
                                } else {
                                    c3[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear4.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear4.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c4[0]++;
                                } else {
                                    c4[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear5.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear5.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c5[0]++;
                                } else {
                                    c5[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }
                    for (int i = 1; i < Linear6.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear6.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c6[0]++;
                                } else {
                                    c6[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_special".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_te, null, false);

                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.Linear1);
                    LinearLayout Linear2 = (LinearLayout) inte.findViewById(R.id.Linear2);
                    LinearLayout Linear3 = (LinearLayout) inte.findViewById(R.id.Linear3);
                    LinearLayout Linear4 = (LinearLayout) inte.findViewById(R.id.Linear4);
                    LinearLayout Linear5 = (LinearLayout) inte.findViewById(R.id.Linear5);
                    LinearLayout Linear6 = (LinearLayout) inte.findViewById(R.id.Linear6);


                    final int[] c1 = {0};
                    final int[] c2 = {0};
                    final int[] c3 = {0};
                    final int[] c4 = {0};
                    final int[] c5 = {0};
                    final int[] c6 = {0};

                    for (int i = 0; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }
                    for (int i = 0; i < Linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c2[0]++;
                                } else {
                                    c2[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }
                    for (int i = 0; i < Linear3.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear3.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c3[0]++;
                                } else {
                                    c3[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }
                    for (int i = 0; i < Linear4.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear4.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c4[0]++;
                                } else {
                                    c4[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }
                    for (int i = 0; i < Linear5.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear5.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c5[0]++;
                                } else {
                                    c5[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }
                    for (int i = 0; i < Linear6.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear6.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c6[0]++;
                                } else {
                                    c6[0]--;
                                }
                                setGameMoney(c1[0] + c2[0] + c3[0] + c4[0] + c5[0] + c6[0]);
                            }
                        });
                    }


                    final List<View> vs = new ArrayList<View>();
                    vs.add(Linear1);
                    vs.add(Linear2);
                    vs.add(Linear3);
                    vs.add(Linear4);
                    vs.add(Linear5);
                    vs.add(Linear6);
                    LinearLayout lin1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout lin2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    LinearLayout lin3 = (LinearLayout) inte.findViewById(R.id.LinearThree);
                    LinearLayout lin4 = (LinearLayout) inte.findViewById(R.id.LinearFour);
                    LinearLayout lin5 = (LinearLayout) inte.findViewById(R.id.LinearFive);
                    LinearLayout lin6 = (LinearLayout) inte.findViewById(R.id.LinearSix);
                    for (int i = 0; i < lin1.getChildCount(); i++) {
                        lin1.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                List<String> str = new ArrayList<String>();
                                if (((TextView) view).getText().equals("0头")) {

                                    str.add("01");
                                    str.add("02");
                                    str.add("03");
                                    str.add("04");
                                    str.add("05");
                                    str.add("06");
                                    str.add("07");
                                    str.add("08");
                                    str.add("09");


                                }
                                if (((TextView) view).getText().equals("1头")) {

                                    str.add("10");
                                    str.add("11");
                                    str.add("12");
                                    str.add("13");
                                    str.add("14");
                                    str.add("15");
                                    str.add("16");
                                    str.add("17");
                                    str.add("18");
                                    str.add("19");

                                }
                                if (((TextView) view).getText().equals("2头")) {

                                    str.add("20");
                                    str.add("21");
                                    str.add("22");
                                    str.add("23");
                                    str.add("24");
                                    str.add("25");
                                    str.add("26");
                                    str.add("27");
                                    str.add("28");
                                    str.add("29");

                                }
                                if (((TextView) view).getText().equals("3头")) {

                                    str.add("30");
                                    str.add("31");
                                    str.add("32");
                                    str.add("33");
                                    str.add("34");
                                    str.add("35");
                                    str.add("36");
                                    str.add("37");
                                    str.add("38");
                                    str.add("39");

                                }
                                if (((TextView) view).getText().equals("4头")) {

                                    str.add("40");
                                    str.add("41");
                                    str.add("42");
                                    str.add("43");
                                    str.add("44");
                                    str.add("45");
                                    str.add("46");
                                    str.add("47");
                                    str.add("48");
                                    str.add("49");

                                }


                                setCheckLin(vs, str);
                            }
                        });


                    }
                    for (int i = 0; i < lin2.getChildCount(); i++) {
                        ((TextView) lin2.getChildAt(i)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                List<String> str = new ArrayList<String>();
                                if (((TextView) view).getText().equals("红波")) {

                                    str.add("01");
                                    str.add("02");
                                    str.add("07");
                                    str.add("08");
                                    str.add("12");
                                    str.add("13");
                                    str.add("18");
                                    str.add("19");
                                    str.add("23");
                                    str.add("24");
                                    str.add("29");
                                    str.add("30");
                                    str.add("34");
                                    str.add("35");
                                    str.add("40");
                                    str.add("45");
                                    str.add("46");

                                }
                                if (((TextView) view).getText().equals("绿波")) {

                                    str.add("05");
                                    str.add("06");
                                    str.add("11");
                                    str.add("16");
                                    str.add("17");
                                    str.add("21");
                                    str.add("22");
                                    str.add("27");
                                    str.add("28");
                                    str.add("32");
                                    str.add("33");
                                    str.add("38");
                                    str.add("39");
                                    str.add("43");
                                    str.add("44");
                                    str.add("49");
                                }
                                if (((TextView) view).getText().equals("蓝波")) {

                                    str.add("03");
                                    str.add("04");
                                    str.add("09");
                                    str.add("10");
                                    str.add("14");
                                    str.add("15");
                                    str.add("20");
                                    str.add("25");
                                    str.add("26");
                                    str.add("31");
                                    str.add("36");
                                    str.add("37");
                                    str.add("41");
                                    str.add("42");
                                    str.add("47");
                                    str.add("48");
                                }
                                if (((TextView) view).getText().equals("机选五注")) {
                                    Random rand = new Random();
                                    List<Integer> nums = new ArrayList<Integer>();
                                    while (nums.size() < 5) {
                                        String s = "";
                                        int num = rand.nextInt(49) + 1;
                                        if (!nums.contains(num)) {
                                            nums.add(num);
                                            if (num < 10) {
                                                s = "0" + num;
                                            } else {
                                                s = num + "";
                                            }
                                            str.add(s + "");
                                        }
                                    }

                                }
                                if (((TextView) view).getText().equals("机选十注")) {

                                    Random rand = new Random();
                                    List<Integer> nums = new ArrayList<Integer>();
                                    while (nums.size() < 10) {
                                        String s = "";
                                        int num = rand.nextInt(49) + 1;
                                        Log.d("随机数1", num + "");
                                        if (!nums.contains(num)) {
                                            nums.add(num);
                                            Log.d("随机数2", num + "");
                                            if (num < 10) {
                                                s = "0" + num;
                                            } else {
                                                s = num + "";
                                            }
                                            str.add(s + "");
                                        }
                                    }

                                }
                                setCheckLin(vs, str);
                            }
                        });
                    }
                    for (int i = 0; i < lin3.getChildCount(); i++) {
                        lin3.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                List<String> str = new ArrayList<String>();
                                if (((TextView) view).getText().equals("0尾")) {

                                    str.add("10");
                                    str.add("20");
                                    str.add("30");
                                    str.add("40");
                                }
                                if (((TextView) view).getText().equals("1尾")) {

                                    str.add("01");
                                    str.add("11");
                                    str.add("21");
                                    str.add("31");
                                    str.add("41");
                                }
                                if (((TextView) view).getText().equals("2尾")) {

                                    str.add("02");
                                    str.add("12");
                                    str.add("22");
                                    str.add("32");
                                    str.add("42");
                                }
                                if (((TextView) view).getText().equals("3尾")) {

                                    str.add("03");
                                    str.add("13");
                                    str.add("23");
                                    str.add("33");
                                    str.add("43");
                                }
                                if (((TextView) view).getText().equals("4尾")) {

                                    str.add("04");
                                    str.add("14");
                                    str.add("24");
                                    str.add("34");
                                    str.add("44");
                                }
                                setCheckLin(vs, str);
                            }
                        });
                    }
                    for (int i = 0; i < lin4.getChildCount(); i++) {
                        lin4.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                List<String> str = new ArrayList<String>();
                                if (((TextView) view).getText().equals("5尾")) {

                                    str.add("05");
                                    str.add("15");
                                    str.add("25");
                                    str.add("35");
                                    str.add("45");
                                }
                                if (((TextView) view).getText().equals("6尾")) {

                                    str.add("06");
                                    str.add("16");
                                    str.add("26");
                                    str.add("36");
                                    str.add("46");
                                }
                                if (((TextView) view).getText().equals("7尾")) {

                                    str.add("07");
                                    str.add("17");
                                    str.add("27");
                                    str.add("37");
                                    str.add("47");
                                }
                                if (((TextView) view).getText().equals("8尾")) {

                                    str.add("08");
                                    str.add("18");
                                    str.add("28");
                                    str.add("38");
                                    str.add("48");
                                }
                                if (((TextView) view).getText().equals("9尾")) {

                                    str.add("09");
                                    str.add("19");
                                    str.add("29");
                                    str.add("39");
                                    str.add("49");
                                }
                                setCheckLin(vs, str);
                            }
                        });
                    }
                    for (int i = 0; i < lin5.getChildCount(); i++) {
                        lin5.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                List<String> str = new ArrayList<String>();
                                if (((TextView) view).getText().equals("鼠")) {

                                    str.add("10");
                                    str.add("22");
                                    str.add("34");

                                    str.add("46");
                                }
                                if (((TextView) view).getText().equals("牛")) {

                                    str.add("09");
                                    str.add("21");
                                    str.add("33");
                                    str.add("45");

                                }
                                if (((TextView) view).getText().equals("虎")) {

                                    str.add("08");
                                    str.add("20");
                                    str.add("32");
                                    str.add("44");

                                }
                                if (((TextView) view).getText().equals("兔")) {

                                    str.add("07");
                                    str.add("19");
                                    str.add("31");
                                    str.add("43");

                                }
                                if (((TextView) view).getText().equals("龙")) {

                                    str.add("06");
                                    str.add("18");
                                    str.add("30");
                                    str.add("42");
                                }
                                if (((TextView) view).getText().equals("蛇")) {

                                    str.add("05");
                                    str.add("17");
                                    str.add("29");
                                    str.add("41");
                                }
                                setCheckLin(vs, str);
                            }
                        });
                    }
                    for (int i = 0; i < lin6.getChildCount(); i++) {
                        lin6.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                List<String> str = new ArrayList<String>();
                                if (((TextView) view).getText().equals("马")) {

                                    str.add("04");
                                    str.add("16");
                                    str.add("28");
                                    str.add("40");
                                }
                                if (((TextView) view).getText().equals("羊")) {

                                    str.add("03");
                                    str.add("15");
                                    str.add("27");
                                    str.add("39");

                                }
                                if (((TextView) view).getText().equals("猴")) {

                                    str.add("02");
                                    str.add("14");
                                    str.add("26");
                                    str.add("38");

                                }
                                if (((TextView) view).getText().equals("鸡")) {

                                    str.add("01");
                                    str.add("13");
                                    str.add("25");
                                    str.add("37");
                                    str.add("49");

                                }
                                if (((TextView) view).getText().equals("狗")) {

                                    str.add("12");
                                    str.add("24");
                                    str.add("36");
                                    str.add("48");
                                }
                                if (((TextView) view).getText().equals("猪")) {

                                    str.add("11");
                                    str.add("23");
                                    str.add("35");
                                    str.add("47");
                                }
                                setCheckLin(vs, str);
                            }
                        });
                    }
                }
                if (
                        "lhc_special_max_min".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_big_small, null, false);
                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};
                    for (int i = 0; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_special_odd_even".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_dan_shuang, null, false);
                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};
                    for (int i = 0; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_special_max_min_odd_even".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_big_small_dan_shuang, null, false);
                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout Linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    final int[] c1 = {0};
                    for (int i = 0; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                    for (int i = 0; i < Linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_special_sum_max_min".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_he_big_small, null, false);
                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};
                    for (int i = 0; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_special_sum_odd_even".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_he_dan_shuang, null, false);
                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};
                    for (int i = 0; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_special_last_max_min".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_wei_big_small, null, false);
                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};
                    for (int i = 0; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_special_zodiacs".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_tm_wei_big_zodiac, null, false);
                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    LinearLayout Linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
                    final int[] c1 = {0};
                    for (int i = 0; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                    for (int i = 0; i < Linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_special_color".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_tb, null, false);
                    LinearLayout Linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
                    final int[] c1 = {0};
                    for (int i = 0; i < Linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) Linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c1[0]++;
                                } else {
                                    c1[0]--;
                                }
                                setGameMoney(c1[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_special_color_max_min".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_tb_big_small, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.Linear1);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.Linear2);
                    LinearLayout linear3 = (LinearLayout) inte.findViewById(R.id.Linear3);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear3.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear3.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_special_color_odd_even".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_tb_dan_shuang, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.Linear1);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.Linear2);
                    LinearLayout linear3 = (LinearLayout) inte.findViewById(R.id.Linear3);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear3.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear3.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                if (
                        "lhc_special_color_max_min_odd_even".equals(code)
                        ) {
                    inte = LayoutInflater.from(GameCenterActivity.this).inflate(R.layout.item_g1_xg_tb_big_small_dan_shuang, null, false);
                    LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.Linear1);
                    LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.Linear2);
                    LinearLayout linear3 = (LinearLayout) inte.findViewById(R.id.Linear3);
                    final int[] c = {0};
                    for (int i = 0; i < linear1.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear1.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear2.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear2.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                    for (int i = 0; i < linear3.getChildCount(); i++) {
                        CheckBox at = (CheckBox) linear3.getChildAt(i);
                        at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    c[0]++;
                                } else {
                                    c[0]--;
                                }
                                setGameMoney(c[0]);
                            }
                        });
                    }
                }
                GameCenterLinear.addView(inte);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void AddMoneySpinner(int position) {
        Rates rates = MinAndMaxs.get(position);

        Double min = rates.getMinimum();
        Double coe = rates.getCoefficient();
        Double rate = rates.getRate();
        List<String> list = new ArrayList<>();
        String mins = min + "";
        String maxs = min + coe * rate + "";
        if (min == 0) {
            mins = "最低奖";
        }
        if (min + coe * rate == 0) {
            maxs = "最高奖";
        }
        list.add(mins);
        list.add(maxs);
        adapterTypeMoney = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterTypeMoney.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SpinnerMoney.setAdapter(adapterTypeMoney);
        SpinnerMoney.setSelection(MyApp.getInstance().getMoneySpinnerPosition());
    }

    private void initClick() {
        Back.setOnClickListener(this);
        cut.setOnClickListener(this);
        add.setOnClickListener(this);
        AddGameNumBtn.setOnClickListener(this);
        for (int i = 0; i < LinearMoneyGetCheck.getChildCount(); i++) {
            CheckBox at = (CheckBox) LinearMoneyGetCheck.getChildAt(i);
            final int finalI = i;
            at.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (finalI == 0) {
                            PriceUnit = 1;
                            mons = 1;
                            GameYuan.setText(Double.parseDouble(GameZhu.getText().toString()) * 2 * mons + "");
                        }
                        if (finalI == 1) {
                            PriceUnit = 2;
                            mons = 0.1;
                            GameYuan.setText(Double.parseDouble(GameZhu.getText().toString()) * 2 * mons + "");
                        }
                        if (finalI == 2) {
                            PriceUnit = 3;
                            mons = 0.01;
                            GameYuan.setText(Double.parseDouble(GameZhu.getText().toString()) * 2 * mons + "");
                        }
                        if (finalI == 3) {
                            PriceUnit = 4;
                            mons = 0.001;
                            GameYuan.setText(Double.parseDouble(GameZhu.getText().toString()) * 2 * mons + "");
                        }
                        for (int i1 = 0; i1 < LinearMoneyGetCheck.getChildCount(); i1++) {
                            if (i1 != finalI) {
                                ((CheckBox) LinearMoneyGetCheck.getChildAt(i1)).setChecked(false);
                            }
                        }
                    }
                }
            });
        }
        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("输入过程中执行该方法", charSequence.toString());
                if ("".equals(charSequence.toString())) {
                    edit1.setText(1 + "");
                }
                int zhu = Integer.parseInt(GameZhu.getText().toString());
                int bei = Integer.parseInt(edit1.getText().toString());
                GameYuan.setText(zhu * bei * 2 * mons + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
     /*   RadioGroupGameCenter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        });*/
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
                                listIds = new ArrayList<>();
                                SendGameNum.setText("0");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                listIds = (List<Ids>) data.getSerializableExtra("ids");

                SendGameNum.setText(listIds.size() + "");
                break;
        }
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
                startActivityForResult(intent, REQUESTCODE);
                break;
            case R.id.AddGameNumBtn:
                SelectNums();
                break;
        }
    }

    public void SelectNums() {
        if (
                code.equals("star_5_duplex")
                        || code.equals("2min_star_5_duplex")
                ) {
            final int[] count1 = {0};
            final int[] count2 = {0};
            final int[] count3 = {0};
            final int[] count4 = {0};
            final int[] count5 = {0};
            String wan = "";
            String qian = "";
            String bai = "";
            String shi = "";
            String ge = "";

            LinearLayout lin1 = (LinearLayout) inte.findViewById(R.id.LinearWan);
            LinearLayout lin2 = (LinearLayout) inte.findViewById(R.id.LinearQian);
            LinearLayout lin3 = (LinearLayout) inte.findViewById(R.id.LinearBai);
            LinearLayout lin4 = (LinearLayout) inte.findViewById(R.id.LinearShi);
            LinearLayout lin5 = (LinearLayout) inte.findViewById(R.id.LinearGe);
            for (int i = 1; i < lin1.getChildCount(); i++) {
                if (((CheckBox) lin1.getChildAt(i)).isChecked()) {
                    count1[0]++;
                    if (wan == "") {
                        wan = ((CheckBox) lin1.getChildAt(i)).getText().toString();
                    } else {
                        wan = wan + ((CheckBox) lin1.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin2.getChildCount(); i++) {
                if (((CheckBox) lin2.getChildAt(i)).isChecked()) {
                    count2[0]++;
                    if (qian == "") {
                        qian = ((CheckBox) lin2.getChildAt(i)).getText().toString();
                    } else {
                        qian = qian + ((CheckBox) lin2.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin3.getChildCount(); i++) {
                if (((CheckBox) lin3.getChildAt(i)).isChecked()) {
                    count3[0]++;
                    if (bai == "") {
                        bai = ((CheckBox) lin3.getChildAt(i)).getText().toString();
                    } else {
                        bai = bai + ((CheckBox) lin3.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin4.getChildCount(); i++) {
                if (((CheckBox) lin4.getChildAt(i)).isChecked()) {
                    count4[0]++;
                    if (shi == "") {
                        shi = ((CheckBox) lin4.getChildAt(i)).getText().toString();
                    } else {
                        shi = shi + ((CheckBox) lin4.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin5.getChildCount(); i++) {
                if (((CheckBox) lin5.getChildAt(i)).isChecked()) {
                    count5[0]++;
                    if (ge == "") {
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
        if (
                code.equals("star_5_group_120")
                        || code.equals("2min_star_5_group_120")
                        || code.equals("star_4_group_24")
                        || code.equals("2min_star_4_group_24")
                        || code.equals("star_4_group_6")
                        || code.equals("2min_star_4_group_6")

                ) {
            int[] count120 = {0};
            String text120 = "";
            LinearLayout linear120 = null;
            if (
                    code.equals("star_5_group_120")
                            || code.equals("2min_star_5_group_120")
                    ) {
                linear120 = (LinearLayout) inte.findViewById(R.id.Linear120);
            }
            if (
                    code.equals("star_4_group_24")
                            || code.equals("2min_star_4_group_24")
                            || code.equals("star_4_group_6")
                            || code.equals("2min_star_4_group_6")
                    ) {
                linear120 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            }
            for (int i = 1; i < linear120.getChildCount(); i++) {
                if (((CheckBox) linear120.getChildAt(i)).isChecked()) {
                    count120[0]++;
                    if (text120 == "") {
                        text120 = ((CheckBox) linear120.getChildAt(i)).getText().toString();
                    } else {
                        text120 = text120 + "," + ((CheckBox) linear120.getChildAt(i)).getText().toString();
                    }
                }
            }
            Log.d("GameCenter120", text120);
            pickedNumber = text120;
            if (
                    code.equals("star_5_group_120")
                            || code.equals("2min_star_5_group_120")
                    ) {
                if (count120[0] > 4) {
                    int nns = RxUtils.getInstance().JieCheng(count120[0]) / (RxUtils.getInstance().JieCheng(5) * RxUtils.getInstance().JieCheng(count120[0] - 5));
                    Log.d("GameCenter120注", nns + "");
                    nums = nns;
                }
            } else if (
                    code.equals("star_4_group_24")
                            || code.equals("2min_star_4_group_24")
                    ) {
                if (count120[0] > 3) {
                    int nns = RxUtils.getInstance().JieCheng(count120[0]) / (RxUtils.getInstance().JieCheng(4) * RxUtils.getInstance().JieCheng(count120[0] - 4));
                    Log.d("GameCenter24注", nns + "");
                    nums = nns;
                }
            } else if (
                    code.equals("star_4_group_6")
                            || code.equals("2min_star_4_group_6")
                    ) {
                if (count120[0] > 1) {
                    int nns = count120[0] * (count120[0] - 1) / 2;
                    Log.d("GameCenter6注", nns + "");
                    nums = nns;
                }
            }


        }
        if (
                code.equals("star_5_group_30")
                        || code.equals("2min_star_5_group_30")
                        || code.equals("star_5_group_20")
                        || code.equals("2min_star_5_group_20")
                        || code.equals("star_4_group_12")
                        || code.equals("2min_star_4_group_12")
                ) {
            int[] count30_1 = {0};
            int[] count30_2 = {0};
            String text30_1 = "";
            String text30_2 = "";
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            for (int i = 1; i < linear1.getChildCount(); i++) {
                if (((CheckBox) linear1.getChildAt(i)).isChecked()) {
                    count30_1[0]++;
                    if (text30_1 == "") {
                        text30_1 = ((CheckBox) linear1.getChildAt(i)).getText().toString();
                    } else {
                        text30_1 = text30_1 + ((CheckBox) linear1.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < linear2.getChildCount(); i++) {
                if (((CheckBox) linear2.getChildAt(i)).isChecked()) {
                    count30_2[0]++;
                    if (text30_2 == "") {
                        text30_2 = ((CheckBox) linear2.getChildAt(i)).getText().toString();
                    } else {
                        text30_2 = text30_2 + ((CheckBox) linear2.getChildAt(i)).getText().toString();
                    }
                }
            }
            int k = 0;
            for (int i = 0; i < text30_1.length(); i++) {
                Log.d("Char30_1", text30_1.charAt(i) + "");
                for (int i1 = 0; i1 < text30_2.length(); i1++) {
                    Log.d("Char30_2", text30_2.charAt(i1) + "");
                    if (text30_1.charAt(i) == text30_2.charAt(i1)) {
                        k++;
                    }
                }
            }
            Log.d("相同的个数", k + "");
            pickedNumber = text30_1 + "," + text30_2;
            if (
                    code.equals("star_5_group_30")
                            || code.equals("2min_star_5_group_30")
                    ) {
                nums = count30_1[0] * (count30_1[0] - 1) * count30_2[0] / 2 - (count30_1[0] - 1) * k;
            } else if (
                    code.equals("star_5_group_20")
                            || code.equals("2min_star_5_group_20")
                    ) {
                nums = count30_2[0] * (count30_2[0] - 1) * count30_1[0] / 2 - (count30_2[0] - 1) * k;
            }
        }
        if (
                code.equals("star_5_one") ||
                        code.equals("2min_star_5_one") ||
                        code.equals("star_5_two") ||
                        code.equals("2min_star_5_two") ||
                        code.equals("star_5_three") ||
                        code.equals("2min_star_5_three") ||
                        code.equals("star_5_four") ||
                        code.equals("2min_star_5_four")
                ) {
            int[] count5 = {0};
            String text5 = "";
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            for (int i = 1; i < linear1.getChildCount(); i++) {
                if (((CheckBox) linear1.getChildAt(i)).isChecked()) {
                    count5[0]++;
                    if (text5 == "") {
                        text5 = ((CheckBox) linear1.getChildAt(i)).getText().toString();
                    } else {
                        text5 = text5 + "," + ((CheckBox) linear1.getChildAt(i)).getText().toString();
                    }
                }
            }
            pickedNumber = text5;
            nums = count5[0];
        }
        if (
                code.equals("star_4_duplex")
                        || code.equals("2min_star_4_duplex")
                ) {

            final int[] count1 = {0};
            final int[] count2 = {0};
            final int[] count3 = {0};
            final int[] count4 = {0};

            String wan = "";
            String qian = "";
            String bai = "";
            String shi = "";


            LinearLayout lin1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout lin2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            LinearLayout lin3 = (LinearLayout) inte.findViewById(R.id.LinearThree);
            LinearLayout lin4 = (LinearLayout) inte.findViewById(R.id.LinearFour);

            for (int i = 1; i < lin1.getChildCount(); i++) {
                if (((CheckBox) lin1.getChildAt(i)).isChecked()) {
                    count1[0]++;
                    if (wan == "") {
                        wan = ((CheckBox) lin1.getChildAt(i)).getText().toString();
                    } else {
                        wan = wan + ((CheckBox) lin1.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin2.getChildCount(); i++) {
                if (((CheckBox) lin2.getChildAt(i)).isChecked()) {
                    count2[0]++;
                    if (qian == "") {
                        qian = ((CheckBox) lin2.getChildAt(i)).getText().toString();
                    } else {
                        qian = qian + ((CheckBox) lin2.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin3.getChildCount(); i++) {
                if (((CheckBox) lin3.getChildAt(i)).isChecked()) {
                    count3[0]++;
                    if (bai == "") {
                        bai = ((CheckBox) lin3.getChildAt(i)).getText().toString();
                    } else {
                        bai = bai + ((CheckBox) lin3.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin4.getChildCount(); i++) {
                if (((CheckBox) lin4.getChildAt(i)).isChecked()) {
                    count4[0]++;
                    if (shi == "") {
                        shi = ((CheckBox) lin4.getChildAt(i)).getText().toString();
                    } else {
                        shi = shi + ((CheckBox) lin4.getChildAt(i)).getText().toString();
                    }
                }
            }

            Log.d("GameCenterrCount==", count1[0] + "   " + count2[0] + "   " + count3[0] + "   " + count4[0] + "   ");
            pickedNumber = wan + "," + qian + "," + bai + "," + shi;
            Log.d("GameCenterrText==", pickedNumber);
            nums = count1[0] * count2[0] * count3[0] * count4[0];
        }
        //三星直选复式
        if (
                code.equals("star_3_next_duplex")
                        || code.equals("2min_star_3_next_duplex")
                        || code.equals("star_3_prev_duplex")
                        || code.equals("2min_star_3_prev_duplex")
                        || code.equals("star_3_midd_duplex")
                        || code.equals("2min_star_3_midd_duplex")
                        || code.equals("sequence_star_3_duplex")
                        || code.equals("3D_star_3_duplex")
                        || code.equals("3D_fix")

                ) {
            final int[] count1 = {0};
            final int[] count2 = {0};
            final int[] count3 = {0};


            String wan = "";
            String qian = "";
            String bai = "";


            LinearLayout lin1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout lin2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            LinearLayout lin3 = (LinearLayout) inte.findViewById(R.id.LinearThree);


            for (int i = 1; i < lin1.getChildCount(); i++) {
                if (((CheckBox) lin1.getChildAt(i)).isChecked()) {
                    count1[0]++;
                    if (wan == "") {
                        wan = ((CheckBox) lin1.getChildAt(i)).getText().toString();
                    } else {
                        wan = wan + ((CheckBox) lin1.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin2.getChildCount(); i++) {
                if (((CheckBox) lin2.getChildAt(i)).isChecked()) {
                    count2[0]++;
                    if (qian == "") {
                        qian = ((CheckBox) lin2.getChildAt(i)).getText().toString();
                    } else {
                        qian = qian + ((CheckBox) lin2.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin3.getChildCount(); i++) {
                if (((CheckBox) lin3.getChildAt(i)).isChecked()) {
                    count3[0]++;
                    if (bai == "") {
                        bai = ((CheckBox) lin3.getChildAt(i)).getText().toString();
                    } else {
                        bai = bai + ((CheckBox) lin3.getChildAt(i)).getText().toString();
                    }
                }
            }


            Log.d("GameCenterrCount==", count1[0] + "   " + count2[0] + "   " + count3[0] + "   ");
            pickedNumber = wan + "," + qian + "," + bai;
            Log.d("GameCenterrText==", pickedNumber);
            if ("3D_fix".equals(code)) {
                nums = count1[0] + count2[0] + count3[0];
            } else {
                nums = count1[0] * count2[0] * count3[0];
            }
        }
        //三星直选和值
        if (
                code.equals("star_3_next_sum")
                        || code.equals("2min_star_3_next_sum")
                        || code.equals("star_3_prev_sum")
                        || code.equals("2min_star_3_prev_sum")
                        || code.equals("star_3_midd_sum")
                        || code.equals("2min_star_3_midd_sum")
                        || code.equals("sequence_star_3_sum")
                        || code.equals("3D_star_3_sum")
                ) {
            LinearLayout lin1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout lin2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            LinearLayout lin3 = (LinearLayout) inte.findViewById(R.id.LinearThree);
            List<Integer> ns = new ArrayList<>();
            List<String> str = new ArrayList<>();
            for (int i = 0; i < lin1.getChildCount(); i++) {
                if (((CheckBox) lin1.getChildAt(i)).isChecked()) {
                    int i1 = Integer.parseInt(((CheckBox) lin1.getChildAt(i)).getText().toString());
                    ns.add(i1);
                    str.add(((CheckBox) lin1.getChildAt(i)).getText().toString());
                }
            }

            for (int i = 0; i < lin2.getChildCount(); i++) {
                if (((CheckBox) lin2.getChildAt(i)).isChecked()) {
                    int i1 = Integer.parseInt(((CheckBox) lin2.getChildAt(i)).getText().toString());
                    ns.add(i1);
                    str.add(((CheckBox) lin2.getChildAt(i)).getText().toString());

                }

            }
            for (int i = 0; i < lin3.getChildCount(); i++) {
                if (((CheckBox) lin3.getChildAt(i)).isChecked()) {
                    int i1 = Integer.parseInt(((CheckBox) lin3.getChildAt(i)).getText().toString());
                    ns.add(i1);
                    str.add(((CheckBox) lin3.getChildAt(i)).getText().toString());
                }
            }
            nums = getNum(ns);
            pickedNumber = getheNums(str);
            Log.d("3星和值和注", getheNums(str) + "            " + nums);
        }
        //三星直选跨度
        if (
                code.equals("star_3_next_sub")
                        || code.equals("2min_star_3_next_sub")
                        || code.equals("star_3_prev_sub")
                        || code.equals("2min_star_3_prev_sub")
                        || code.equals("star_3_midd_sub")
                        || code.equals("2min_star_3_midd_sub")
                ) {
            LinearLayout linear = (LinearLayout) inte.findViewById(R.id.LinearOne);
            final int[] count1 = {0};
            List<String> kua = new ArrayList<>();
            List<Integer> ns = new ArrayList<>();
            for (int i = 1; i < linear.getChildCount(); i++) {
                if (((CheckBox) linear.getChildAt(i)).isChecked()) {
                    count1[0]++;
                    ns.add(Integer.parseInt(((CheckBox) linear.getChildAt(i)).getText().toString()));
                    kua.add(((CheckBox) linear.getChildAt(i)).getText().toString());
                }
            }
            nums = getNumKua(ns);
            pickedNumber = getheNums(kua);
            Log.d("3星和值跨", getheNums(kua) + "            " + nums);
        }
        //三星复式 3 6
        if (
                code.equals("star_3_next_group_duplex")
                        || code.equals("2min_star_3_next_group_duplex")
                        || code.equals("star_3_next_group_duplex_6")
                        || code.equals("2min_star_3_next_group_duplex_6")
                        || code.equals("star_3_midd_group_duplex")
                        || code.equals("2min_star_3_midd_group_duplex")
                        || code.equals("star_3_prev_group_duplex")
                        || code.equals("2min_star_3_prev_group_duplex")
                        || code.equals("star_3_prev_group_duplex_6")
                        || code.equals("2min_star_3_prev_group_duplex_6")
                        || code.equals("star_3_midd_group_duplex_6")
                        || code.equals("2min_star_3_midd_group_duplex_6")


                ) {
            final int[] count1 = {0};
            String str = "";
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            for (int i = 1; i < linear1.getChildCount(); i++) {
                if (((CheckBox) linear1.getChildAt(i)).isChecked()) {
                    count1[0]++;
                    if (str == "") {
                        str = ((CheckBox) linear1.getChildAt(i)).getText().toString();
                    } else {
                        str = str + "," + ((CheckBox) linear1.getChildAt(i)).getText().toString();
                    }
                }
            }
            pickedNumber = str;
            if (
                    code.equals("star_3_next_group_duplex")
                            || code.equals("2min_star_3_next_group_duplex")
                            || code.equals("star_3_prev_group_duplex")
                            || code.equals("2min_star_3_prev_group_duplex")
                            || code.equals("star_3_midd_group_duplex")
                            || code.equals("2min_star_3_midd_group_duplex")

                    ) {
                if (count1[0] < 2) {
                    nums = 0;
                } else {
                    nums = count1[0] * (count1[0] - 1);
                }
            } else if (
                    code.equals("star_3_next_group_duplex_6")
                            || code.equals("2min_star_3_next_group_duplex_6")
                            || code.equals("star_3_prev_group_duplex_6")
                            || code.equals("2min_star_3_prev_group_duplex_6")
                            || code.equals("star_3_midd_group_duplex_6")
                            || code.equals("2min_star_3_midd_group_duplex_6")
                            || "star_5_three_no_fix".equals(code)
                            || "2min_star_5_three_no_fix".equals(code)
                    ) {
                if (count1[0] < 3) {
                    nums = 0;
                } else {
                    int n1 = RxUtils.getInstance().JieCheng(count1[0]);
                    int n2 = RxUtils.getInstance().JieCheng(3);
                    int n3 = RxUtils.getInstance().JieCheng(count1[0] - 3);
                    nums = (n2 * n3 <= 0 ? 0 : n1 / (n2 * n3));
                }
            }

        }
        //三星组选包胆
        if (
                code.equals("star_3_next_group_any")
                        || code.equals("2min_star_3_next_group_any")
                        || code.equals("star_3_prev_group_any")
                        || code.equals("2min_star_3_prev_group_any")
                        || code.equals("star_3_midd_group_any")
                        || code.equals("2min_star_3_midd_group_any")
                ) {
            RadioGroup radiog1 = (RadioGroup) inte.findViewById(R.id.RadioG1);
            for (int i = 0; i < radiog1.getChildCount(); i++) {
                if (((RadioButton) radiog1.getChildAt(i)).isChecked()) {
                    pickedNumber = ((RadioButton) radiog1.getChildAt(i)).getText().toString();
                }
            }
            nums = 54;
        }
        //三星组选和值
        if (
                code.equals("star_3_next_group_sum")
                        || code.equals("2min_star_3_next_group_sum")
                        || code.equals("star_3_prev_group_sum")
                        || code.equals("2min_star_3_prev_group_sum")
                        || code.equals("star_3_midd_group_sum")
                        || code.equals("2min_star_3_midd_group_sum")
                        || code.equals("sequence_star_3_group_sum")
                        || code.equals("3D_star_3_group_sum")
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.Linear1);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.Linear2);
            LinearLayout linear3 = (LinearLayout) inte.findViewById(R.id.Linear3);
            final int[] count1 = {0};
            List<Integer> ns = new ArrayList<>();
            List<String> str = new ArrayList<>();
            for (int i = 0; i < linear1.getChildCount(); i++) {
                if (((CheckBox) linear1.getChildAt(i)).isChecked()) {
                    String s = ((CheckBox) linear1.getChildAt(i)).getText().toString();
                    ns.add(Integer.parseInt(s));
                    str.add(s);
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                if (((CheckBox) linear2.getChildAt(i)).isChecked()) {
                    String s = ((CheckBox) linear2.getChildAt(i)).getText().toString();
                    ns.add(Integer.parseInt(s));
                    str.add(s);
                }
            }
            for (int i = 0; i < linear3.getChildCount(); i++) {
                if (((CheckBox) linear3.getChildAt(i)).isChecked()) {
                    String s = ((CheckBox) linear3.getChildAt(i)).getText().toString();
                    ns.add(Integer.parseInt(s));
                    str.add(s);
                }
            }
            nums = getNumZuHe(ns);
            pickedNumber = getheNums(str);
        }
        //二星直选复式
        if (
                "star_2_next_duplex".equals(code)
                        || "2min_star_2_next_duplex".equals(code)
                        || "star_2_prev_duplex".equals(code)
                        || "2min_star_2_prev_duplex".equals(code)
                        || "sequence_star_2_prev_duplex".equals(code)
                        || "sequence_star_2_next_duplex".equals(code)
                        || "3D_star_2_prev_duplex".equals(code)
                        || "3D_star_2_next_duplex".equals(code)
                ) {
            int count1 = 0;
            int count2 = 0;
            String str1 = "";
            String str2 = "";
            LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            for (int i = 1; i < LinearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                if (at.isChecked()) {
                    count1++;
                    if (str1 == "") {
                        str1 = at.getText().toString();
                    } else {
                        str1 = str1 + at.getText().toString();
                    }
                }
            }
            for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                if (at.isChecked()) {
                    count2++;
                    if (str2 == "") {
                        str2 = at.getText().toString();
                    } else {
                        str2 = str2 + at.getText().toString();
                    }
                }
            }
            nums = count1 * count2;
            pickedNumber = str1 + "," + str2;
        }
        //二星直选和值
        if (
                code.equals("star_2_next_sum")
                        || code.equals("2min_star_2_next_sum")
                        || code.equals("star_2_prev_sum")
                        || code.equals("2min_star_2_prev_sum")
                ) {
            List<Integer> ns = new ArrayList<>();
            List<String> nss = new ArrayList<>();
            LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            for (int i = 1; i < LinearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                if (at.isChecked()) {
                    int i1 = Integer.parseInt(at.getText().toString().trim());
                    ns.add(i1);
                    nss.add(at.getText().toString().trim());
                }
            }
            for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                if (at.isChecked()) {
                    int i1 = Integer.parseInt(at.getText().toString().trim());
                    ns.add(i1);
                    nss.add(at.getText().toString().trim());
                }
            }
            nums = getNumHe2(ns);
            pickedNumber = getheNums(nss);

        }
        //二星直选跨度
        if (
                "star_2_next_sub".equals(code)
                        || "2min_star_2_next_sub".equals(code)
                        || "star_2_prev_sub".equals(code)
                        || "2min_star_2_prev_sub".equals(code)
                ) {
            List<Integer> ns = new ArrayList<>();
            List<String> nss = new ArrayList<>();
            LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            for (int i = 1; i < LinearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                if (at.isChecked()) {
                    ns.add(Integer.parseInt(at.getText().toString()));
                    nss.add(at.getText().toString());
                }
            }
            nums = getNumKua2(ns);
            pickedNumber = getheNums(nss);
        }
        //二星组选复式
        if (
                "star_2_next_group_duplex".equals(code)
                        || "2min_star_2_next_group_duplex".equals(code)
                        || "star_2_prev_group_duplex".equals(code)
                        || "2min_star_2_prev_group_duplex".equals(code)
                        || "eleven_star_2_prev_group_duplex".equals(code)
                        || "sequence_star_3_group_3_duplex".equals(code)
                        || "3D_star_3_group_3_duplex".equals(code)
                        || "sequence_star_2_prev_group_duplex".equals(code)
                        || "3D_star_2_next_group_duplex".equals(code)
                        || "3D_star_2_prev_group_duplex".equals(code)
                        || "sequence_star_2_next_group_duplex".equals(code)
                ) {
            int count1 = 0;
            String str = "";
            LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            for (int i = 1; i < LinearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                if (at.isChecked()) {
                    count1++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                    } else {
                        str = str + "," + at.getText().toString().trim();
                    }
                }

            }
            if (
                    "sequence_star_3_group_3_duplex".equals(code)
                            || "3D_star_3_group_3_duplex".equals(code)
                    ) {
                nums = count1 * (count1 - 1);
            } else {

                nums = count1 * (count1 - 1) / 2;
            }
            pickedNumber = str;
        }
        //二星组选和值
        if (
                "star_2_next_group_sum".equals(code)
                        || "2min_star_2_next_group_sum".equals(code)
                        || "star_2_prev_group_sum".equals(code)
                        || "2min_star_2_prev_group_sum".equals(code)
                ) {
            List<Integer> ns = new ArrayList<>();
            List<String> nns = new ArrayList<>();
            LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            for (int i = 1; i < LinearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                if (at.isChecked()) {
                    ns.add(Integer.parseInt(at.getText().toString().trim()));
                    nns.add(at.getText().toString().trim());
                }
            }
            for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                if (at.isChecked()) {
                    ns.add(Integer.parseInt(at.getText().toString().trim()));
                    nns.add(at.getText().toString().trim());
                }
            }
            pickedNumber = getheNums(nns);
            nums = getNumHe22(ns);
        }
        //二星组选包胆
        if (
                "star_2_next_group_any".equals(code)
                        || "2min_star_2_next_group_any".equals(code)
                        || "star_2_prev_group_any".equals(code)
                        || "2min_star_2_prev_group_any".equals(code)
                ) {
            RadioGroup radiog1 = (RadioGroup) inte.findViewById(R.id.RadioG1);
            for (int i = 0; i < radiog1.getChildCount(); i++) {
                if (((RadioButton) radiog1.getChildAt(i)).isChecked()) {
                    pickedNumber = ((RadioButton) radiog1.getChildAt(i)).getText().toString();
                }
            }
            nums = 9;
        }
        if (
                "fix".equals(code)
                        || "2min_fix".equals(code)
                        || "star_2_any_duplex".equals(code)
                        || "2min_star_2_any_duplex".equals(code)
                        || "star_3_any_duplex".equals(code)
                        || "2min_star_3_any_duplex".equals(code)
                        || "star_4_any_duplex".equals(code)
                        || "2min_star_4_any_duplex".equals(code)
                        || "sequence_fix".equals(code)

                ) {
            final int[] m1 = {0};
            final int[] m2 = {0};
            final int[] m3 = {0};
            final int[] m4 = {0};
            final int[] m5 = {0};
            String wan = "";
            String qian = "";
            String bai = "";
            String shi = "";
            String ge = "";

            LinearLayout lin1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout lin2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            LinearLayout lin3 = (LinearLayout) inte.findViewById(R.id.LinearThree);
            LinearLayout lin4 = (LinearLayout) inte.findViewById(R.id.LinearFour);
            LinearLayout lin5 = (LinearLayout) inte.findViewById(R.id.LinearFive);
            for (int i = 1; i < lin1.getChildCount(); i++) {
                if (((CheckBox) lin1.getChildAt(i)).isChecked()) {
                    m1[0]++;
                    if (wan == "") {
                        wan = ((CheckBox) lin1.getChildAt(i)).getText().toString();
                    } else {
                        wan = wan + ((CheckBox) lin1.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin2.getChildCount(); i++) {
                if (((CheckBox) lin2.getChildAt(i)).isChecked()) {
                    m2[0]++;
                    if (qian == "") {
                        qian = ((CheckBox) lin2.getChildAt(i)).getText().toString();
                    } else {
                        qian = qian + ((CheckBox) lin2.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin3.getChildCount(); i++) {
                if (((CheckBox) lin3.getChildAt(i)).isChecked()) {
                    m3[0]++;
                    if (bai == "") {
                        bai = ((CheckBox) lin3.getChildAt(i)).getText().toString();
                    } else {
                        bai = bai + ((CheckBox) lin3.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin4.getChildCount(); i++) {
                if (((CheckBox) lin4.getChildAt(i)).isChecked()) {
                    m4[0]++;
                    if (shi == "") {
                        shi = ((CheckBox) lin4.getChildAt(i)).getText().toString();
                    } else {
                        shi = shi + ((CheckBox) lin4.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin5.getChildCount(); i++) {
                if (((CheckBox) lin5.getChildAt(i)).isChecked()) {
                    m5[0]++;
                    if (ge == "") {
                        ge = ((CheckBox) lin5.getChildAt(i)).getText().toString();
                    } else {
                        ge = ge + ((CheckBox) lin5.getChildAt(i)).getText().toString();
                    }
                }
            }

            pickedNumber = wan + "," + qian + "," + bai + "," + shi + "," + ge;
            Log.d("GameCenterrText==", pickedNumber);
            if (
                    "fix".equals(code)
                            || "2min_fix".equals(code)
                            || "sequence_fix".equals(code)
                    ) {
                nums = m1[0] + m2[0] + m3[0] + m4[0] + m5[0];
            }
            if (
                    "star_2_any_duplex".equals(code)
                            || "2min_star_2_any_duplex".equals(code)
                    ) {
                nums = m1[0] * (m2[0] + m3[0] + m4[0] + m5[0]) + m2[0] * (m3[0] + m4[0] + m5[0]) + m3[0] * (m4[0] + m5[0]) + m4[0] * m5[0];
            }
            if (
                    "star_3_any_duplex".equals(code)
                            || "2min_star_3_any_duplex".equals(code)
                    ) {
                nums = m1[0] * m2[0] * m3[0] + m1[0] * m2[0] * m4[0] + m1[0] * m2[0] * m5[0] + m1[0] * m3[0] * m4[0] + m1[0] * m3[0] * m5[0] + m1[0] * m4[0] * m5[0] + m2[0] * m3[0] * m4[0] + m2[0] * m3[0] * m5[0] + m2[0] * m4[0] * m5[0] + m3[0] * m4[0] * m5[0];
            }
            if (
                    "star_4_any_duplex".equals(code)
                            || "2min_star_4_any_duplex".equals(code)
                    ) {
                nums = m1[0] * m2[0] * m3[0] * m4[0] + m1[0] * m2[0] * m3[0] * m5[0] + m1[0] * m2[0] * m4[0] * m5[0] + m1[0] * m3[0] * m4[0] * m5[0] + m2[0] * m3[0] * m4[0] * m5[0];
            }
        }
        if (
                "star_3_next_one_no_fix".equals(code)
                        || "2min_star_3_next_one_no_fix".equals(code)
                        || "star_3_prev_one_no_fix".equals(code)
                        || "2min_star_3_prev_one_no_fix".equals(code)
                        || "star_4_one_no_fix".equals(code)
                        || "2min_star_4_one_no_fix".equals(code)
                        || "eleven_star_3_prev_no_fix".equals(code)
                        || "sequence_choose_1_no_fix".equals(code)
                        || "3D_choose_1_no_fix".equals(code)
                        || "3D_choose_2_no_fix".equals(code)

                        || "star_3_next_two_no_fix".equals(code)
                        || "2min_star_3_next_two_no_fix".equals(code)
                        || "star_3_prev_two_no_fix".equals(code)
                        || "2min_star_3_prev_two_no_fix".equals(code)
                        || "star_4_two_no_fix".equals(code)
                        || "2min_star_4_two_no_fix".equals(code)
                        || "star_5_two_no_fix".equals(code)
                        || "2min_star_5_two_no_fix".equals(code)
                        || "star_5_three_no_fix".equals(code)
                        || "2min_star_5_three_no_fix".equals(code)

                ) {
            String str = "";
            int count = 0;
            LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            for (int i = 1; i < LinearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                    } else {
                        str = str + "," + at.getText().toString().toString();
                    }
                }
            }
            pickedNumber = str;
            if (
                    "3D_choose_2_no_fix".equals(code)
                            || "star_3_next_two_no_fix".equals(code)
                            || "2min_star_3_next_two_no_fix".equals(code)
                            || "star_3_prev_two_no_fix".equals(code)
                            || "2min_star_3_prev_two_no_fix".equals(code)
                            || "star_4_two_no_fix".equals(code)
                            || "2min_star_4_two_no_fix".equals(code)
                            || "star_5_two_no_fix".equals(code)
                            || "2min_star_5_two_no_fix".equals(code)

                    ) {
                nums = count * (count - 1) / 2;
            } else {
                nums = count;
            }
        }
        //11 选五三码直选
        if (
                "eleven_star_3_prev_duplex".equals(code)

                ) {
            String gn1[] = new String[11];
            int i1 = 0;
            String gn2[] = new String[11];
            int i2 = 0;
            String gn3[] = new String[11];
            int i3 = 0;
            int coun = 0;
            String str1 = "";
            String str2 = "";
            String str3 = "";
            LinearLayout lin1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout lin2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            LinearLayout lin3 = (LinearLayout) inte.findViewById(R.id.LinearThree);
            for (int i = 1; i < lin1.getChildCount(); i++) {
                CheckBox at = (CheckBox) lin1.getChildAt(i);
                if (at.isChecked()) {
                    gn1[i1] = at.getText().toString().trim();
                    if (str1 == "") {
                        str1 = at.getText().toString().trim();
                    } else {
                        str1 = str1 + " " + at.getText().toString().trim();
                    }
                    i1++;
                }
            }
            for (int i = 1; i < lin2.getChildCount(); i++) {
                CheckBox at = (CheckBox) lin2.getChildAt(i);
                if (at.isChecked()) {
                    gn2[i2] = at.getText().toString().trim();
                    if (str2 == "") {
                        str2 = at.getText().toString().trim();
                    } else {
                        str2 = str2 + " " + at.getText().toString().trim();
                    }
                    i2++;
                }
            }
            for (int i = 1; i < lin3.getChildCount(); i++) {
                CheckBox at = (CheckBox) lin3.getChildAt(i);
                if (at.isChecked()) {
                    gn3[i3] = at.getText().toString().trim();
                    if (str3 == "") {
                        str3 = at.getText().toString().trim();
                    } else {
                        str3 = str3 + " " + at.getText().toString().trim();
                    }
                    i3++;
                }
            }
            int c1 = 0;
            int c2 = 0;
            int c3 = 0;

            for (int i = 0; i < gn1.length; i++) {
                if (gn1[i] != null) {
                    Log.d("11选五三码直选1", gn1[i] + "");
                    c1++;
                }
            }
            for (int i = 0; i < gn2.length; i++) {
                if (gn2[i] != null) {
                    Log.d("11选五三码直选2", gn2[i] + "");
                    c2++;
                }
            }
            for (int i = 0; i < gn3.length; i++) {
                if (gn3[i] != null) {
                    Log.d("11选五三码直选3", gn3[i] + "");
                    c3++;
                }
            }

            for (int i = 0; i < c1; i++) {
                for (int j = 0; j < c2; j++) {
                    if (gn1[i] == gn2[j]) continue;
                    else {
                        for (int k = 0; k < c3; k++) {
                            if (gn1[i] == gn3[k] || gn2[j] == gn3[k]) continue;
                            else coun++;
                        }
                    }
                }
            }
            Log.d("11选5的注数", coun + "");
            nums = coun;
            pickedNumber = str1 + "," + str2 + "," + str3;
        }
        if ("eleven_star_3_prev_group_duplex".equals(code)) {
            String str = "";
            int n = 0;
            LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            for (int i = 1; i < LinearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                if (at.isChecked()) {
                    n++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                    } else {
                        str = str + "," + at.getText().toString().trim();
                    }
                }
            }
            nums = n * (n - 1) * (n - 2) / 6;
            pickedNumber = str;
        }
        if ("eleven_star_2_prev_duplex".equals(code)) {
            String gn1[] = new String[11];
            int i1 = 0;
            String gn2[] = new String[11];
            int i2 = 0;

            int coun = 0;
            String str1 = "";
            String str2 = "";

            LinearLayout lin1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout lin2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            LinearLayout lin3 = (LinearLayout) inte.findViewById(R.id.LinearThree);
            for (int i = 1; i < lin1.getChildCount(); i++) {
                CheckBox at = (CheckBox) lin1.getChildAt(i);
                if (at.isChecked()) {
                    gn1[i1] = at.getText().toString().trim();
                    if (str1 == "") {
                        str1 = at.getText().toString().trim();
                    } else {
                        str1 = str1 + " " + at.getText().toString().trim();
                    }
                    i1++;
                }
            }
            for (int i = 1; i < lin2.getChildCount(); i++) {
                CheckBox at = (CheckBox) lin2.getChildAt(i);
                if (at.isChecked()) {
                    gn2[i2] = at.getText().toString().trim();
                    if (str2 == "") {
                        str2 = at.getText().toString().trim();
                    } else {
                        str2 = str2 + " " + at.getText().toString().trim();
                    }
                    i2++;
                }
            }
            int c1 = 0;
            int c2 = 0;

            for (int i = 0; i < gn1.length; i++) {
                if (gn1[i] != "") {
                    Log.d("11选五三码直选1", gn1[i]);
                    c1++;
                }
            }
            for (int i = 0; i < gn2.length; i++) {
                if (gn2[i] != "") {
                    Log.d("11选五三码直选2", gn2[i]);
                    c2++;
                }
            }
            for (int i = 0; i < c1; i++) {
                for (int j = 0; j < c2; j++) {
                    if (gn1[i] == gn2[j]) continue;
                    coun += 1;
                }
            }
            nums = coun;
            pickedNumber = str1 + "," + str2;
        }
        if ("eleven_fix".equals(code)) {
            LinearLayout lin1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout lin2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            LinearLayout lin3 = (LinearLayout) inte.findViewById(R.id.LinearThree);
            int count = 0;
            String str1 = "";
            String str2 = "";
            String str3 = "";
            for (int i = 1; i < lin1.getChildCount(); i++) {
                CheckBox at = (CheckBox) lin1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str1 == "") {
                        str1 = at.getText().toString().trim();
                    } else {
                        str1 = str1 + " " + at.getText().toString().trim();
                    }

                }
            }
            for (int i = 1; i < lin2.getChildCount(); i++) {
                CheckBox at = (CheckBox) lin2.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str2 == "") {
                        str2 = at.getText().toString().trim();
                    } else {
                        str2 = str2 + " " + at.getText().toString().trim();
                    }

                }
            }
            for (int i = 1; i < lin3.getChildCount(); i++) {
                CheckBox at = (CheckBox) lin3.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str3 == "") {
                        str3 = at.getText().toString().trim();
                    } else {
                        str3 = str3 + " " + at.getText().toString().trim();
                    }
                }
            }
            nums = count;
            pickedNumber = str1 + "," + str2 + "," + str3;
        }
        if ("eleven_even_or_odd".equals(code)) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            String str = "";
            String strt1 = "";
            int count = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                        strt1 = get11_5(at.getText().toString().trim());
                    } else {
                        str = str + "," + at.getText().toString().trim();
                        strt1 = strt1 + "," + get11_5(at.getText().toString().trim());
                    }
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                        strt1 = get11_5(at.getText().toString().trim());
                    } else {
                        str = str + "," + at.getText().toString().trim();
                        strt1 = strt1 + "," + get11_5(at.getText().toString().trim());
                    }
                }
            }
            pickedNumber = strt1;
            pickedText = str;
            nums = count;
        }
        if ("eleven_middle".equals(code)) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            String str = "";
            int count = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                    } else {
                        str = str + "," + at.getText().toString().trim();
                    }
                }
            }
            pickedNumber = str;
            nums = count;
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
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            String str = "";
            int count = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                    } else {
                        str = str + "," + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                    } else {
                        str = str + "," + at.getText().toString().trim();
                    }
                }
            }
            if ("eleven_any_one_duplex".equals(code)) {
                if (count != 1) {
                    //Toasty.error(GameCenterActivity.this, "投注个数不正确,请重新投注.", 2000).show();
                    return;
                }
            }
            if ("eleven_any_two_duplex".equals(code)) {
                if (count != 2) {
                    //Toasty.error(GameCenterActivity.this, "投注个数不正确,请重新投注.", 2000).show();
                    return;
                }
            }
            if ("eleven_any_three_duplex".equals(code)) {
                if (count != 3) {
                    //Toasty.error(GameCenterActivity.this, "投注个数不正确,请重新投注.", 2000).show();
                    return;
                }
            }
            if ("eleven_any_four_duplex".equals(code)) {
                if (count != 4) {
                    //Toasty.error(GameCenterActivity.this, "投注个数不正确,请重新投注.", 2000).show();
                    return;
                }
            }
            if ("eleven_any_five_duplex".equals(code)) {
                if (count != 5) {
                    //Toasty.error(GameCenterActivity.this, "投注个数不正确,请重新投注.", 2000).show();
                    return;
                }
            }
            if ("eleven_any_six_duplex".equals(code)) {
                if (count != 6) {
                    //Toasty.error(GameCenterActivity.this, "投注个数不正确,请重新投注.", 2000).show();
                    return;
                }
            }
            if ("eleven_any_seven_duplex".equals(code)) {
                if (count != 7) {
                    //Toasty.error(GameCenterActivity.this, "投注个数不正确,请重新投注.", 2000).show();
                    return;
                }
            }
            if ("eleven_any_eight_duplex".equals(code)) {
                if (count != 8) {
                    //Toasty.error(GameCenterActivity.this, "投注个数不正确,请重新投注.", 2000).show();
                    return;
                }
            }
            pickedNumber = str;
            nums = 1;
        }
        if ("PK10_1st_duplex".equals(code)) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            String str = "";
            int count = 0;
            for (int i = 1; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                    } else {
                        str = str + "," + at.getText().toString().trim();
                    }
                }
            }
            pickedNumber = str;
            nums = count;
        }
        if ("PK10_1st_2nd_duplex".equals(code)) {
            LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            int n = 0;//冠军选号数;
            int m = 0;//亚军选号数;
            String str1 = "";
            String str2 = "";
            List<String> n1 = new ArrayList<>();
            List<String> n2 = new ArrayList<>();
            int k = 0;//冠亚军重复选号数;
            for (int i = 1; i < LinearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                if (at.isChecked()) {
                    n++;
                    if (str1 == "") {
                        str1 = at.getText().toString().trim();
                    } else {
                        str1 = str1 + " " + at.getText().toString().trim();
                    }
                    n1.add(at.getText().toString().trim());
                }
            }
            for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                if (at.isChecked()) {
                    m++;
                    if (str2 == "") {
                        str2 = at.getText().toString().trim();
                    } else {
                        str2 = str2 + " " + at.getText().toString().trim();
                    }
                    n2.add(at.getText().toString().trim());
                }
            }

            for (int i = 0; i < n1.size(); i++) {
                Log.d("冠军_1", n1.get(i) + "");
                for (int i1 = 0; i1 < n2.size(); i1++) {
                    Log.d("冠军_2", n2.get(i1) + "");
                    if (n1.get(i) == n2.get(i1)) {
                        k++;
                    }
                }
            }
            nums = n * m - k;
            pickedNumber = str1 + "," + str2;
            Log.d("相同的个数", k + "");
        }
        if ("PK10_1st_2nd_3th_duplex".equals(code)) {
            LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);
            int n = 0;//冠军选号个数
            int m = 0;//亚军选号个数
            int k = 0;//第三名选号个数

            int a = 0;//冠军亚军重复个数
            int b = 0;//亚军季军重复个数
            int c = 0;//冠军季军重复个数
            int d = 0;//冠亚季军重复个数

            List<String> ns = new ArrayList<>();
            List<String> ms = new ArrayList<>();
            List<String> ks = new ArrayList<>();
            String str1 = "";
            String str2 = "";
            String str3 = "";
            for (int i = 1; i < LinearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                if (at.isChecked()) {
                    n++;
                    ns.add(at.getText().toString().trim());
                    if (str1 == "") {
                        str1 = at.getText().toString().trim();
                    } else {
                        str1 = str1 + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 1; i < LinearTwo.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                if (at.isChecked()) {
                    m++;
                    ms.add(at.getText().toString().trim());
                    if (str2 == "") {
                        str2 = at.getText().toString().trim();
                    } else {
                        str2 = str2 + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 1; i < LinearThree.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                if (at.isChecked()) {
                    k++;
                    ks.add(at.getText().toString().trim());
                    if (str3 == "") {
                        str3 = at.getText().toString().trim();
                    } else {
                        str3 = str3 + " " + at.getText().toString().trim();
                    }
                }
            }
            //冠军亚军重复个数
            for (int i = 0; i < ns.size(); i++) {
                Log.d("冠军_1", ns.get(i) + "");
                for (int i1 = 0; i1 < ms.size(); i1++) {
                    Log.d("冠军_2", ms.get(i) + "");
                    if (ns.get(i) == ms.get(i1)) {
                        a++;
                    }
                }
            }
            //冠军季军重复个数
            for (int i = 0; i < ns.size(); i++) {
                Log.d("冠军_1", ns.get(i) + "");
                for (int i1 = 0; i1 < ks.size(); i1++) {
                    Log.d("冠军_3", ks.get(i1) + "");
                    if (ns.get(i) == ks.get(i1)) {
                        b++;
                    }
                }
            }

            //亚军季军重复个数
            for (int i = 0; i < ms.size(); i++) {
                Log.d("冠军_2", ms.get(i) + "");
                for (int i1 = 0; i1 < ks.size(); i1++) {
                    Log.d("冠军_3", ks.get(i1) + "");
                    if (ms.get(i) == ks.get(i1)) {
                        c++;
                    }
                }
            }
            //冠军亚军季军重复个数
            for (int i = 0; i < ns.size(); i++) {
                Log.d("冠军_1", ns.get(i) + "");
                for (int i1 = 0; i1 < ms.size(); i1++) {
                    Log.d("冠军_2", ms.get(i1) + "");
                    if (ns.get(i) == ms.get(i1)) {
                        for (int i2 = 0; i2 < ks.size(); i2++) {
                            Log.d("冠军_3", ks.get(i2) + "");
                            if (ns.get(i) == ks.get(i2)) {
                                d++;
                            }
                        }
                    }
                }
            }
            nums = n * m * k - a * k - b * n - c * m + d * 2;
            pickedNumber = str1 + "," + str2 + "," + str3;

        }
        if (
                "PK10_1to5_duplex".equals(code)
                        || "PK10_6to10_duplex".equals(code)
                ) {
            LinearLayout lin1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout lin2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            LinearLayout lin3 = (LinearLayout) inte.findViewById(R.id.LinearThree);
            LinearLayout lin4 = (LinearLayout) inte.findViewById(R.id.LinearFour);
            LinearLayout lin5 = (LinearLayout) inte.findViewById(R.id.LinearFive);
            int count = 0;
            String wan = "";
            String qian = "";
            String bai = "";
            String shi = "";
            String ge = "";


            for (int i = 1; i < lin1.getChildCount(); i++) {
                if (((CheckBox) lin1.getChildAt(i)).isChecked()) {
                    count++;
                    if (wan == "") {
                        wan = ((CheckBox) lin1.getChildAt(i)).getText().toString();
                    } else {
                        wan = wan + " " + ((CheckBox) lin1.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin2.getChildCount(); i++) {
                if (((CheckBox) lin2.getChildAt(i)).isChecked()) {
                    count++;
                    if (qian == "") {
                        qian = ((CheckBox) lin2.getChildAt(i)).getText().toString();
                    } else {
                        qian = qian + " " + ((CheckBox) lin2.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin3.getChildCount(); i++) {
                if (((CheckBox) lin3.getChildAt(i)).isChecked()) {
                    count++;
                    if (bai == "") {
                        bai = ((CheckBox) lin3.getChildAt(i)).getText().toString();
                    } else {
                        bai = bai + " " + ((CheckBox) lin3.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin4.getChildCount(); i++) {
                if (((CheckBox) lin4.getChildAt(i)).isChecked()) {
                    count++;
                    if (shi == "") {
                        shi = ((CheckBox) lin4.getChildAt(i)).getText().toString();
                    } else {
                        shi = shi + " " + ((CheckBox) lin4.getChildAt(i)).getText().toString();
                    }
                }
            }
            for (int i = 1; i < lin5.getChildCount(); i++) {
                if (((CheckBox) lin5.getChildAt(i)).isChecked()) {
                    count++;
                    if (ge == "") {
                        ge = ((CheckBox) lin5.getChildAt(i)).getText().toString();
                    } else {
                        ge = ge + " " + ((CheckBox) lin5.getChildAt(i)).getText().toString();
                    }
                }
            }
            nums = count;
            pickedNumber = wan + "," + qian + "," + bai + "," + shi + "," + ge;
        }
        if ("PK10_1to10_duplex".equals(code)) {
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
            String str1 = "";
            String str2 = "";
            String str3 = "";
            String str4 = "";
            String str5 = "";
            String str6 = "";
            String str7 = "";
            String str8 = "";
            String str9 = "";
            String str10 = "";
            int count = 0;
            for (int i = 1; i < Linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) Linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str1 == "") {
                        str1 = at.getText().toString().trim();
                    } else {
                        str1 = str1 + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 1; i < Linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) Linear2.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str2 == "") {
                        str2 = at.getText().toString().trim();
                    } else {
                        str2 = str2 + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 1; i < Linear3.getChildCount(); i++) {
                CheckBox at = (CheckBox) Linear3.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str3 == "") {
                        str3 = at.getText().toString().trim();
                    } else {
                        str3 = str3 + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 1; i < Linear4.getChildCount(); i++) {
                CheckBox at = (CheckBox) Linear4.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str4 == "") {
                        str4 = at.getText().toString().trim();
                    } else {
                        str4 = str4 + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 1; i < Linear5.getChildCount(); i++) {
                CheckBox at = (CheckBox) Linear5.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str5 == "") {
                        str5 = at.getText().toString().trim();
                    } else {
                        str5 = str5 + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 1; i < Linear6.getChildCount(); i++) {
                CheckBox at = (CheckBox) Linear6.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str6 == "") {
                        str6 = at.getText().toString().trim();
                    } else {
                        str6 = str6 + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 1; i < Linear7.getChildCount(); i++) {
                CheckBox at = (CheckBox) Linear7.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str7 == "") {
                        str7 = at.getText().toString().trim();
                    } else {
                        str7 = str7 + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 1; i < Linear8.getChildCount(); i++) {
                CheckBox at = (CheckBox) Linear8.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str8 == "") {
                        str8 = at.getText().toString().trim();
                    } else {
                        str8 = str8 + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 1; i < Linear9.getChildCount(); i++) {
                CheckBox at = (CheckBox) Linear9.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str9 == "") {
                        str9 = at.getText().toString().trim();
                    } else {
                        str9 = str9 + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 1; i < Linear10.getChildCount(); i++) {
                CheckBox at = (CheckBox) Linear10.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str10 == "") {
                        str10 = at.getText().toString().trim();
                    } else {
                        str10 = str1 + " " + at.getText().toString().trim();
                    }
                }
            }
            nums = count;
            pickedNumber = str1 + "," + str2 + "," + str3 + "," + str4 + "," + str5 + "," + str6 + "," + str7 + "," + str8 + "," + str9 + "," + str10;

        }
        if (
                "PK10_1st_special".equals(code)
                        || "PK10_2nd_special".equals(code)
                        || "PK10_3th_special".equals(code)
                        || "PK10_1st_odd_even".equals(code)
                        || "PK10_2nd_odd_even".equals(code)
                        || "PK10_3th_odd_even".equals(code)

                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            int count = 0;
            String str = "";
            String strt1 = "";
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                        strt1 = getBigCamp(at.getText().toString().trim());
                    } else {
                        str = str + "," + at.getText().toString().trim();
                        strt1 = strt1 + "," + getBigCamp(at.getText().toString().trim());
                    }
                }
            }
            pickedText = str;
            pickedNumber = strt1;
            nums = count;
        }
        if (

                "PK10_1st_2nd_special".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            int count = 0;
            String str = "";
            String strt1 = "";
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                        strt1 = getBigCampHe(at.getText().toString().trim());
                    } else {
                        str = str + "," + at.getText().toString().trim();
                        strt1 = strt1 + "," + getBigCampHe(at.getText().toString().trim());
                    }
                }
            }
            pickedText = str;
            pickedNumber = strt1;
            nums = count;
        }
        if (
                "PK10_1st_2nd_sum".equals(code)
                        || "k3_sum".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            String str = "";
            int count = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                    } else {
                        str = str + "," + at.getText().toString().trim();
                    }
                }

            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                    } else {
                        str = str + "," + at.getText().toString().trim();
                    }
                }

            }
            pickedNumber = str;
            nums = count;
        }
        if ("PK10_toradora".equals(code)) {
            final RadioGroup rg1 = (RadioGroup) inte.findViewById(R.id.Rg1);
            final RadioGroup rg2 = (RadioGroup) inte.findViewById(R.id.Rg2);
            final RadioGroup rg3 = (RadioGroup) inte.findViewById(R.id.Rg3);
            final RadioGroup rg4 = (RadioGroup) inte.findViewById(R.id.Rg4);
            final RadioGroup rg5 = (RadioGroup) inte.findViewById(R.id.Rg5);
            final String[] str1 = {""};
            final String[] str2 = {""};
            final String[] str3 = {""};
            final String[] str4 = {""};
            final String[] str5 = {""};
            String strt1 = "";
            String strt2 = "";
            String strt3 = "";
            String strt4 = "";
            String strt5 = "";
            final int[] count = {0};

            for (int i = 0; i < rg1.getChildCount(); i++) {
                if (((RadioButton) rg1.getChildAt(i)).isChecked()) {
                    count[0]++;
                    str1[0] = ((RadioButton) rg1.getChildAt(i)).getText().toString();
                    strt1 = getDragon(((RadioButton) rg1.getChildAt(i)).getText().toString())
                    ;
                }
            }
            for (int i = 0; i < rg2.getChildCount(); i++) {
                if (((RadioButton) rg2.getChildAt(i)).isChecked()) {
                    count[0]++;
                    str2[0] = ((RadioButton) rg2.getChildAt(i)).getText().toString();
                    strt2 = getDragon(((RadioButton) rg2.getChildAt(i)).getText().toString());
                }
            }
            for (int i = 0; i < rg3.getChildCount(); i++) {
                if (((RadioButton) rg3.getChildAt(i)).isChecked()) {
                    count[0]++;
                    str3[0] = ((RadioButton) rg3.getChildAt(i)).getText().toString();
                    strt3 = getDragon(((RadioButton) rg3.getChildAt(i)).getText().toString());
                }
            }
            for (int i = 0; i < rg4.getChildCount(); i++) {
                if (((RadioButton) rg4.getChildAt(i)).isChecked()) {
                    count[0]++;
                    str4[0] = ((RadioButton) rg4.getChildAt(i)).getText().toString();
                    strt4 = getDragon(((RadioButton) rg4.getChildAt(i)).getText().toString());
                }
            }
            for (int i = 0; i < rg5.getChildCount(); i++) {
                if (((RadioButton) rg5.getChildAt(i)).isChecked()) {
                    count[0]++;
                    str5[0] = ((RadioButton) rg5.getChildAt(i)).getText().toString();
                    strt5 = getDragon(((RadioButton) rg5.getChildAt(i)).getText().toString());
                }
            }
            nums = count[0];
            pickedText = str1[0] + "," + str2[0] + "," + str3[0] + "," + str4[0] + "," + str5[0];
            pickedNumber = strt1 + "," + strt2 + "," + strt3 + "," + strt4 + "," + strt5;
        }
        if (
                "k3_triple_all".equals(code)

                        || "k3_consecutives_3_all".equals(code)

                ) {
            LinearLayout linear = (LinearLayout) inte.findViewById(R.id.LinearOne);
            int count = 0;
            String str = "";

            for (int i = 0; i < linear.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                    } else {
                        str = str + "," + at.getText().toString().trim();
                    }
                }
            }
            pickedNumber = str;
            nums = count;
        }
        if (
                "k3_triple_all".equals(code)
                        || "k3_consecutives_3_all".equals(code)

                ) {
            LinearLayout linear = (LinearLayout) inte.findViewById(R.id.LinearOne);
            int count = 0;
            String str = "";
            String strt1 = "";
            for (int i = 0; i < linear.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                        strt1 = getTongXuan(code);
                    } else {
                        str = str + "," + at.getText().toString().trim();
                    }
                }
            }
            pickedNumber = strt1;
            pickedText = str;
            nums = count;
        }
        if (
                "k3_double_standard".equals(code)


                ) {
            final LinearLayout linear = (LinearLayout) inte.findViewById(R.id.LinearOne);
            final LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            int count = 0;
            int count2 = 0;
            String str = "";
            String str2 = "";
            for (int i = 0; i < linear.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString().trim();
                    } else {
                        str = str + " " + at.getText().toString().trim();
                    }
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    count2++;
                    if (str2 == "") {
                        str2 = at.getText().toString().trim();
                    } else {
                        str2 = str2 + at.getText().toString().trim();
                    }
                }
            }
            pickedNumber = str + "," + str2;
            nums = count * count2;
        }
        if (
                "k3_different_3_standard".equals(code)
                        || "k3_different_3_sum".equals(code)
                        || "k3_different_2_standard".equals(code)
                ) {
            LinearLayout linear = (LinearLayout) inte.findViewById(R.id.LinearOne);
            int count = 0;
            String str = "";
            List<String> ins = new ArrayList<>();
            for (int i = 0; i < linear.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    ins.add(at.getText().toString().trim());
                    if (str == "") {

                        str = at.getText().toString().trim();
                    } else {
                        str = str + "," + at.getText().toString().trim();
                    }
                }
            }
            pickedNumber = str;
            if ("k3_different_3_standard".equals(code)) {
                nums = count * (count - 1) * (count - 2) / 6;
            }
            if ("k3_different_3_sum".equals(code)) {
                nums = ThreeDifferent(ins);
            }
            if ("k3_different_2_standard".equals(code)) {
                nums = count * (count - 1) / 2;
            }
        }
        if (
                "kl8_sum_even_odd".equals(code)
                        || "kl8_sum_even_odd_2".equals(code)
                        || "kl8_sum_max_min".equals(code)
                        || "kl8_sum_max_min_2".equals(code)
                        || "kl8_parity_disk".equals(code)
                        || "kl8_parity_disk_2".equals(code)
                        || "kl8_up_down".equals(code)
                        || "kl8_up_down_2".equals(code)
                        || "kl8_special".equals(code)
                        || "kl8_special_2".equals(code)
                        || "toradora_wq".equals(code)
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
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            String str = "";
            String strt1 = "";
            int count = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        if (
                                "kl8_sum_even_odd".equals(code)
                                        || "kl8_sum_even_odd_2".equals(code)
                                        || "kl8_sum_max_min".equals(code)
                                        || "kl8_sum_max_min_2".equals(code)
                                        || "kl8_parity_disk".equals(code)
                                        || "kl8_parity_disk_2".equals(code)
                                        || "kl8_up_down".equals(code)
                                        || "kl8_up_down_2".equals(code)
                                ) {
                            str = String.valueOf(at.getText().toString().charAt(0));
                            strt1 = getBigCampHe(String.valueOf(at.getText().toString().charAt(0)));
                        }
                        if (
                                "kl8_special".equals(code)
                                        || "kl8_special_2".equals(code)
                                        || "toradora_wq".equals(code)
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
                            str = at.getText().toString();
                            if ("kl8_special".equals(code)
                                    || "kl8_special_2".equals(code)) {
                                strt1 = getBigCampHe(at.getText().toString());
                            } else {
                                strt1 = getDragon(at.getText().toString());
                            }
                        }
                    } else {
                        if (
                                "kl8_sum_even_odd".equals(code)
                                        || "kl8_sum_even_odd_2".equals(code)
                                        || "kl8_sum_max_min".equals(code)
                                        || "kl8_sum_max_min_2".equals(code)
                                        || "kl8_parity_disk".equals(code)
                                        || "kl8_parity_disk_2".equals(code)
                                        || "kl8_up_down_2".equals(code)
                                        || "kl8_up_down".equals(code)
                                ) {
                            str = str + "," + String.valueOf(at.getText().toString().charAt(0));
                            strt1 = strt1 + "," + getBigCampHe(String.valueOf(at.getText().toString().charAt(0)));
                        }
                        if (
                                "kl8_special".equals(code)
                                        || "kl8_special_2".equals(code)
                                        || "toradora_wq".equals(code)
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
                            str = str + "," + at.getText().toString();
                            if ("kl8_special".equals(code)
                                    || "kl8_special_2".equals(code)) {
                                strt1 = strt1 + "," + getBigCampHe(at.getText().toString());
                            } else {
                                strt1 = strt1 + "," + getDragon(at.getText().toString());
                            }
                        }
                    }
                }
            }
            nums = count;
            pickedNumber = strt1;
            pickedText = str;
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
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.Linear1);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.Linear2);
            LinearLayout linear3 = (LinearLayout) inte.findViewById(R.id.Linear3);
            LinearLayout linear4 = (LinearLayout) inte.findViewById(R.id.Linear4);
            LinearLayout linear5 = (LinearLayout) inte.findViewById(R.id.Linear5);
            LinearLayout linear6 = (LinearLayout) inte.findViewById(R.id.Linear6);
            LinearLayout linear7 = (LinearLayout) inte.findViewById(R.id.Linear7);
            LinearLayout linear8 = (LinearLayout) inte.findViewById(R.id.Linear8);
            LinearLayout linear9 = (LinearLayout) inte.findViewById(R.id.Linear9);
            LinearLayout linear10 = (LinearLayout) inte.findViewById(R.id.Linear10);

            String str = "";
            int count = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear3.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear3.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear4.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear4.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear5.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear5.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear6.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear6.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear7.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear7.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear8.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear8.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear9.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear9.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear10.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear10.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            if ("kl8_any_two".equals(code)
                    || "kl8_any_three".equals(code)
                    || "kl8_any_four".equals(code)
                    || "kl8_any_five".equals(code)
                    || "kl8_any_six".equals(code)
                    || "kl8_any_seven".equals(code)) {
                if (count > 8) {
                    //Toasty.info(GameCenterActivity.this, "最多只能选择8个号码位", 2000).show();
                    return;
                }
            }
            if ("kl8_any_one".equals(code)) {
                if (count < 1) {
                    //Toasty.info(GameCenterActivity.this, "投注个数不够，请重新投注", 2000).show();
                    return;
                }
                nums = count;
            }
            if ("kl8_any_two".equals(code)) {
                if (count < 2) {
                    //Toasty.info(GameCenterActivity.this, "投注个数不够，请重新投注", 2000).show();
                    return;
                }
                nums = count * (count - 1) / 2;
            }
            if ("kl8_any_three".equals(code)) {
                if (count < 3) {
                    //Toasty.info(GameCenterActivity.this, "投注个数不够，请重新投注", 2000).show();
                    return;
                }
                nums = count * (count - 1) * (count - 2) / 6;
            }
            if ("kl8_any_four".equals(code)) {
                if (count < 4) {
                    //Toasty.info(GameCenterActivity.this, "投注个数不够，请重新投注", 2000).show();
                    return;
                }
                nums = count * (count - 1) * (count - 2) * (count - 3) / 24;
            }
            if ("kl8_any_five".equals(code)) {
                if (count < 5) {
                    //Toasty.info(GameCenterActivity.this, "投注个数不够，请重新投注", 2000).show();
                    return;
                }
                nums = count * (count - 1) * (count - 2) * (count - 3) * (count - 4) / 120;
            }
            if ("kl8_any_six".equals(code)) {
                if (count < 6) {
                    //Toasty.info(GameCenterActivity.this, "投注个数不够，请重新投注", 2000).show();
                    return;
                }
                nums = count * (count - 1) * (count - 2) * (count - 3) * (count - 4) * (count - 5) / 720;
            }
            if ("kl8_any_seven".equals(code)) {
                if (count < 7) {
                    //Toasty.info(GameCenterActivity.this, "投注个数不够，请重新投注", 2000).show();
                    return;
                }
                nums = RxUtils.getInstance().JieCheng(count) / RxUtils.getInstance().JieCheng(7);
            }
            pickedNumber = str;

        }
        if ("kl8_five_elements".equals(code)) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            String str = "";
            String strt1 = "";
            int count = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = String.valueOf(at.getText().toString().charAt(0));
                        strt1 = get5_Elements(String.valueOf(at.getText().toString().charAt(0)));
                    } else {
                        str = str + "," + String.valueOf(at.getText().toString().charAt(0));
                        strt1 = strt1 + "," + get5_Elements(String.valueOf(at.getText().toString().charAt(0)));
                    }
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = String.valueOf(at.getText().toString().charAt(0));
                        strt1 = get5_Elements(String.valueOf(at.getText().toString().charAt(0)));
                    } else {
                        str = str + "," + String.valueOf(at.getText().toString().charAt(0));
                        strt1 = strt1 + "," + get5_Elements(String.valueOf(at.getText().toString().charAt(0)));
                    }
                }
            }
            pickedNumber = strt1;
            pickedText = str;
            nums = count;
        }
        if (
                "sequence_star_3_group_6_duplex".equals(code)
                        || "sequence_choose_2_no_fix".equals(code)
                        || "3D_star_3_group_6_duplex".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            String str = "";
            int n = 0;
            for (int i = 1; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    n++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            pickedNumber = str;
            if ("sequence_choose_2_no_fix".equals(code)) {
                nums = n * (n - 1) / 2;
            }
            if (
                    "sequence_star_3_group_6_duplex".equals(code)
                            || "3D_star_3_group_6_duplex".equals(code)
                    ) {
                nums = n * (n - 1) * (n - 2) / 6;
            }

        }
        if (
                "sequence_next_special".equals(code)
                        || "sequence_prev_special".equals(code)
                        || "3D_prev_special".equals(code)
                        || "3D_next_special".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);

            String str = "";
            String str2 = "";
            String strt1 = "";
            String strt2 = "";
            int n1 = 0;
            int n2 = 0;

            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    n1++;
                    if (str == "") {
                        str = at.getText().toString();
                        strt1 = getBig(at.getText().toString());
                    } else {
                        str = str + at.getText().toString();
                        strt1 = strt1 + getBig(at.getText().toString());
                    }
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    n2++;
                    if (str2 == "") {
                        str2 = at.getText().toString();
                        strt2 = getBig(at.getText().toString());
                    } else {
                        str2 = str2 + at.getText().toString();
                        strt2 = strt1 + getBig(at.getText().toString());
                    }
                }
            }
            pickedText = str + "," + str2;
            pickedNumber = strt1 + "," + strt2;
            nums = n1 * n2;

        }
        if (
                "lhc_orthocode".equals(code)
                        || "lhc_special".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.Linear1);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.Linear2);
            LinearLayout linear3 = (LinearLayout) inte.findViewById(R.id.Linear3);
            LinearLayout linear4 = (LinearLayout) inte.findViewById(R.id.Linear4);
            LinearLayout linear5 = (LinearLayout) inte.findViewById(R.id.Linear5);
            LinearLayout linear6 = (LinearLayout) inte.findViewById(R.id.Linear6);
            String str = "";
            int count = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear3.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear3.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear4.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear4.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear5.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear5.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear6.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear6.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            pickedNumber = str;
            nums = count;
        }
        if (
                "lhc_special_max_min".equals(code)
                        || "lhc_special_odd_even".equals(code)
                        || "lhc_special_sum_max_min".equals(code)
                        || "lhc_special_sum_odd_even".equals(code)
                        || "lhc_special_last_max_min".equals(code)
                        || "lhc_special_color".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            String str = "";
            String strt1 = "";
            int count = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                        strt1 = getBigLHC(at.getText().toString());
                    } else {
                        str = str + "," + at.getText().toString();
                        strt1 = strt1 + "," + getBigLHC(at.getText().toString());
                    }
                }
            }
            pickedNumber = strt1;
            pickedText = str;
            nums = count;
        }
        if (
                "lhc_special_max_min_odd_even".equals(code)
                        || "lhc_special_zodiacs".equals(code)

                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            String str = "";
            String strt1 = "";
            int count = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                        if ("lhc_special_max_min_odd_even".equals(code)) {
                            strt1 = getTELHC(at.getText().toString());
                        } else {
                            strt1 = getSXLHC(at.getText().toString());
                        }

                    } else {
                        str = str + "," + at.getText().toString();
                        if ("lhc_special_max_min_odd_even".equals(code)) {
                            strt1 = strt1 + "," + getTELHC(at.getText().toString());
                        } else {
                            strt1 = strt1 + "," + getSXLHC(at.getText().toString());
                        }

                    }
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                        if ("lhc_special_max_min_odd_even".equals(code)) {
                            strt1 = getTELHC(at.getText().toString());
                        } else {
                            strt1 = getSXLHC(at.getText().toString());
                        }
                    } else {
                        str = str + "," + at.getText().toString();
                        if ("lhc_special_max_min_odd_even".equals(code)) {
                            strt1 = strt1 + "," + getTELHC(at.getText().toString());
                        } else {
                            strt1 = strt1 + "," + getSXLHC(at.getText().toString());
                        }
                    }
                }
            }
            pickedText = str;
            pickedNumber = strt1;
            nums = count;

        }
        if (
                "lhc_special_color_max_min".equals(code)
                        || "lhc_special_color_odd_even".equals(code)
                        || "lhc_special_color_max_min_odd_even".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.Linear1);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.Linear2);
            LinearLayout linear3 = (LinearLayout) inte.findViewById(R.id.Linear3);
            String str = "";
            String strt1 = "";
            int count = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                        strt1 = getBigLHC(at.getText().toString());
                    } else {
                        str = str + "," + at.getText().toString();
                        strt1 = strt1 + "," + getBigLHC(at.getText().toString());
                    }
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                        strt1 = getBigLHC(at.getText().toString());
                    } else {
                        str = str + "," + at.getText().toString();
                        strt1 = strt1 + "," + getBigLHC(at.getText().toString());
                    }
                }
            }
            for (int i = 0; i < linear3.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear3.getChildAt(i);
                if (at.isChecked()) {
                    count++;
                    if (str == "") {
                        str = at.getText().toString();
                        strt1 = getBigLHC(at.getText().toString());
                    } else {
                        str = str + "," + at.getText().toString();
                        strt1 = strt1 + "," + getBigLHC(at.getText().toString());
                    }
                }
            }
            pickedNumber = strt1;
            pickedText = str;
            nums = count;
        }
        if (
                "star_5_group_60".equals(code)
                        || "2min_star_5_group_60".equals(code)
                        || "star_5_group_10".equals(code)
                        || "2min_star_5_group_10".equals(code)
                        || "star_4_group_4".equals(code)
                        || "2min_star_4_group_4".equals(code)
                        || "star_4_group_12".equals(code)
                        || "2min_star_4_group_12".equals(code)
                        || "2min_star_5_group_5".equals(code)
                        || "star_5_group_5".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            String str = "";
            String str2 = "";
            int m = 0;
            int n = 0;
            int k = 0; //重复个数
            List<String> ns = new ArrayList<>();
            List<String> ms = new ArrayList<>();

            for (int i = 1; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    n++;
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + at.getText().toString();
                    }
                    ms.add(at.getText().toString());
                }
            }
            for (int i = 1; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    m++;
                    if (str2 == "") {
                        str2 = at.getText().toString();
                    } else {
                        str2 = str2 + at.getText().toString();
                    }
                    ns.add(at.getText().toString());
                }
            }
            for (int i = 0; i < ns.size(); i++) {

                for (int i1 = 0; i1 < ms.size(); i1++) {
                    if (ns.get(i) == ms.get(i1)) {
                        k++;
                    }
                }
            }
            pickedNumber = str + "," + str2;
            if (
                    "star_5_group_10".equals(code)
                            || "star_5_group_5".equals(code)
                            || "star_4_group_4".equals(code)
                            || "2min_star_5_group_5".equals(code)
                            || "2min_star_5_group_10".equals(code)
                            || "2min_star_4_group_4".equals(code)
                    ) {
                nums = n * m - k;
            } else {
                nums = (n - 1) * (n - 2) * (m * n - 3 * k) / 6;
            }
            if (
                    "star_4_group_12".equals(code)
                            || "2min_star_4_group_12".equals(code)
                    ) {
                nums = n * (n - 1) * m / 2 - (n - 1) * k;
            }
        }
        if (
                "star_2_prev_special".equals(code)
                        || "2min_star_2_prev_special".equals(code)
                        || "star_2_next_special".equals(code)
                        || "2min_star_2_next_special".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            String str = "";
            String str2 = "";
            String strt1 = "";
            String strt2 = "";
            int c1 = 0;
            int c2 = 0;

            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {

                    c1++;
                    if (str == "") {
                        str = getBig(at.getText().toString());
                        strt1 = at.getText().toString();
                    } else {
                        str = str + getBig(at.getText().toString());
                        strt1 = strt1 + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    c2++;
                    if (str2 == "") {
                        str2 = getBig(at.getText().toString());
                        strt2 = at.getText().toString();
                    } else {
                        str2 = str2 + getBig(at.getText().toString());
                        strt2 = strt2 + at.getText().toString();
                    }
                }
            }
            nums = c1 * c2;
            pickedNumber = str + "," + str2;
            pickedText = strt1 + "," + strt2;
        }
        if (
                "star_3_prev_special".equals(code)
                        || "2min_star_3_prev_special".equals(code)
                        || "star_3_next_special".equals(code)
                        || "2min_star_3_next_special".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linear2 = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            LinearLayout linear3 = (LinearLayout) inte.findViewById(R.id.LinearThree);
            String str = "";
            String str2 = "";
            String str3 = "";
            String strt1 = "";
            String strt2 = "";
            String strt3 = "";
            int c1 = 0;
            int c2 = 0;
            int c3 = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    c1++;
                    if (str == "") {
                        str = at.getText().toString();
                        strt1 = getBig(at.getText().toString());
                    } else {
                        str = str + at.getText().toString();
                        strt1 = strt1 + getBig(at.getText().toString());
                    }
                }
            }
            for (int i = 0; i < linear2.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear2.getChildAt(i);
                if (at.isChecked()) {
                    c2++;
                    if (str2 == "") {
                        str2 = at.getText().toString();
                        strt2 = getBig(at.getText().toString());
                    } else {
                        str2 = str2 + at.getText().toString();
                        strt2 = strt2 + getBig(at.getText().toString());
                    }
                }
            }
            for (int i = 0; i < linear3.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear3.getChildAt(i);
                if (at.isChecked()) {
                    c3++;
                    if (str3 == "") {
                        str3 = at.getText().toString();
                        strt3 = getBig(at.getText().toString());
                    } else {
                        str3 = str3 + at.getText().toString();
                        strt3 = strt3 + getBig(at.getText().toString());
                    }
                }
            }
            nums = c1 * c2 * c3;
            pickedText = str + "," + str2 + "," + str3;
            pickedNumber = strt1 + "," + strt2 + "," + strt3;
        }
        if (
                "banker_player".equals(code)
                        || "2min_banker_player".equals(code)
                        || "equal".equals(code)
                        || "2min_equal".equals(code)
                        || "same_two".equals(code)
                        || "2min_same_two".equals(code)
                        || "same_three".equals(code)
                        || "2min_same_three".equals(code)
                        || "heavenly_king".equals(code)
                        || "2min_heavenly_king".equals(code)
                        || "sum_special".equals(code)
                        || "2min_sum_special".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearOne);
            String str = "";
            String strt1 = "";
            int c1 = 0;

            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    c1++;
                    if (str == "") {
                        str = at.getText().toString();
                        if ("sum_special".equals(code)
                                || "2min_sum_special".equals(code)) {
                            strt1 = getBig(at.getText().toString());
                        } else {
                            strt1 = getBaccarat(at.getText().toString());
                        }
                    } else {
                        str = str + "," + at.getText().toString();
                        if ("sum_special".equals(code)
                                || "2min_sum_special".equals(code)) {
                            strt1 = strt1 + "," + getBig(at.getText().toString());
                        } else {
                            strt1 = strt1 + "," + getBaccarat(at.getText().toString());
                        }
                    }
                }
            }
            pickedNumber = strt1;
            pickedText = str;
            nums = c1;
        }
        if (
                "star_5_single".equals(code)
                        || "2min_star_5_single".equals(code)
                        || "star_4_single".equals(code)
                        || "2min_star_4_single".equals(code)
                        || "star_3_next_single".equals(code)
                        || "2min_star_3_next_single".equals(code)

                        || "star_3_next_group_single".equals(code)
                        || "2min_star_3_next_group_single".equals(code)
                        || "star_3_next_group_single_6".equals(code)
                        || "2min_star_3_next_group_single_6".equals(code)
                        || "star_3_next_group_diverse".equals(code)
                        || "2min_star_3_next_group_diverse".equals(code)
                        || "star_3_prev_group_single".equals(code)
                        || "2min_star_3_prev_group_single".equals(code)
                        || "star_3_prev_group_single_6".equals(code)
                        || "2min_star_3_prev_group_single_6".equals(code)
                        || "star_3_prev_single".equals(code)
                        || "2min_star_3_prev_single".equals(code)

                        || "star_3_prev_group_diverse".equals(code)
                        || "2min_star_3_prev_group_diverse".equals(code)
                        || "star_3_midd_single".equals(code)
                        || "2min_star_3_midd_single".equals(code)
                        || "star_3_midd_group_single".equals(code)
                        || "2min_star_3_midd_group_single".equals(code)
                        || "star_3_midd_group_single_6".equals(code)
                        || "2min_star_3_midd_group_single_6".equals(code)
                        || "star_3_midd_group_diverse".equals(code)
                        || "2min_star_3_midd_group_diverse".equals(code)
                        || "star_2_next_group_single".equals(code)
                        || "2min_star_2_next_group_single".equals(code)
                        || "star_2_prev_single".equals(code)
                        || "2min_star_2_prev_single".equals(code)
                        || "star_2_prev_group_single".equals(code)
                        || "2min_star_2_prev_group_single".equals(code)


                ) {
            final EditText g2_editText = (EditText) inte.findViewById(R.id.g2_EditText);
            String s = g2_editText.getText().toString().trim();
            if (s.equals("")) {
                //Toasty.error(GameCenterActivity.this, "请输入内容", 2000).show();
                return;
            }
            if (s.substring(s.length() - 1, s.length()).equals(",")) {
                s = s.substring(s.length() - 1, s.length());
            }

            int seleNum = 0;
            if ("star_5_single".equals(code)
                    || "2min_star_5_single".equals(code)) {
                seleNum = 5;
            }
            if ("star_4_single".equals(code)
                    || "2min_star_4_single".equals(code)) {
                seleNum = 4;
            }
            if ("star_3_next_single".equals(code)
                    || "2min_star_3_next_single".equals(code)
                    || "star_3_next_group_single".equals(code)
                    || "2min_star_3_next_group_single".equals(code)

                    || "star_3_next_group_single_6".equals(code)
                    || "2min_star_3_next_group_single_6".equals(code)
                    || "star_3_prev_group_single_6".equals(code)
                    || "2min_star_3_prev_group_single_6".equals(code)
                    || "star_3_prev_group_single".equals(code)
                    || "2min_star_3_prev_group_single".equals(code)

                    || "star_3_next_group_diverse".equals(code)
                    || "2min_star_3_next_group_diverse".equals(code)
                    || "star_3_prev_group_diverse".equals(code)
                    || "2min_star_3_prev_group_diverse".equals(code)

                    || "star_3_prev_single".equals(code)
                    || "2min_star_3_prev_single".equals(code)
                    || "star_3_midd_single".equals(code)
                    || "2min_star_3_midd_single".equals(code)
                    || "star_3_midd_group_single".equals(code)
                    || "2min_star_3_midd_group_single".equals(code)
                    || "star_3_midd_group_single_6".equals(code)
                    || "2min_star_3_midd_group_single_6".equals(code)
                    || "star_3_midd_group_diverse".equals(code)
                    || "2min_star_3_midd_group_diverse".equals(code)


                    ) {
                seleNum = 3;
            }
            if (
                    "star_2_next_single".equals(code)
                            || "2min_star_2_next_single".equals(code)
                            || "star_2_next_group_single".equals(code)
                            || "2min_star_2_next_group_single".equals(code)
                            || "star_2_prev_single".equals(code)
                            || "2min_star_2_prev_single".equals(code)
                            || "star_2_prev_group_single".equals(code)
                            || "2min_star_2_prev_group_single".equals(code)
                    ) {
                seleNum = 2;
            }
            if (s.contains(",")) {
                String[] sp = s.split(",");
                nums = sp.length;
                for (int i = 0; i < sp.length; i++) {
                    if (sp[i].length() != seleNum) {
                        //Toasty.error(GameCenterActivity.this, "请至少输入一注进行投注", 2000).show();
                        return;
                    }
                    if ("star_3_next_group_diverse".equals(code)
                            || "2min_star_3_next_group_diverse".equals(code)
                            || "star_3_prev_group_diverse".equals(code)
                            || "2min_star_3_prev_group_diverse".equals(code)
                            || "star_3_midd_group_diverse".equals(code)
                            || "2min_star_3_midd_group_diverse".equals(code)) {
                        String s1 = sp[i].substring(0, 1);
                        String s2 = sp[i].substring(1, 2);
                        String s3 = sp[i].substring(2, 3);
                        if (s1.equals(s2) && s1.equals(s3) && s3.equals(s2)) {
                            //Toasty.error(GameCenterActivity.this, "三个号码不能完全相同", 2000).show();
                            return;
                        }
                    }
                    if ("star_2_next_group_single".equals(code)
                            || "2min_star_2_next_group_single".equals(code)
                            || "star_2_prev_group_single".equals(code)
                            || "2min_star_2_prev_group_single".equals(code)) {
                        String s1 = sp[i].substring(0, 1);
                        String s2 = sp[i].substring(1, 2);
                        if (s1.equals(s2)) {
                            //Toasty.error(GameCenterActivity.this, "两个号码不能完全相同", 2000).show();
                            return;
                        }
                    }

                }
            } else {
                nums = 1;
                if (s.length() != seleNum) {
                    //Toasty.error(GameCenterActivity.this, "请至少输入一注进行投注", 2000).show();
                    return;

                }
                       /* pickedNumber = s;
                        Nums = Integer.parseInt(edit1.getText().toString().trim());
                        nums = 1;
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
                        if (nums == 0) {
                            return;
                        }
                        AddLottery();*/
            }
            locationText = "";
            location = "";


            pickedNumber = s;

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
            if (nums == 0) {
                return;
            }
            AddLottery();

            return;
        }
        if (
                "star_2_any_single".equals(code)
                        || "2min_star_2_any_single".equals(code)
                        || "star_2_any_group_single".equals(code)
                        || "2min_star_2_any_group_single".equals(code)
                )

        {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearCheck);
            EditText editT = (EditText) inte.findViewById(R.id.optional2_editText);
            String s = editT.getText().toString().trim();
            int n = 0;
            List<String> ns = new ArrayList<>();
            if ("".equals(s) || " ".equals(s)) {
                //Toasty.error(GameCenterActivity.this, "未输入投注", 2000).show();
                return;
            }
            if (s.substring(s.length() - 1, s.length()).equals(",")) {
                s = s.substring(s.length() - 1, s.length());
            }

            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    n++;
                    ns.add(at.getText().toString().substring(0, 1));
                }
            }
                    /*for (int j = 0; j < sp.length; j++) {
                        int n = 0;
                        String l = "";
                        String ltext = "";
                        List<String> ns = new ArrayList<>();
                        for (int i = 0; i < linear1.getChildCount(); i++) {
                            CheckBox at = (CheckBox) linear1.getChildAt(i);

                            if (at.isChecked()) {
                                ns.add(at.getText().toString().substring(0, 1));
                                n++;
                                if (ltext == "") {

                                    ltext = at.getText().toString().substring(0, 1);
                                } else {
                                    ltext = ltext + "," + at.getText().toString().substring(0, 1);
                                }
                                if (l == "") {
                                    l = "1";
                                } else {
                                    l = l + "," + "1";
                                }
                            } else {
                                if (l == "") {
                                    l = "0";
                                } else {
                                    l = l + "," + "0";
                                }
                            }
                        }*/

            if (n < 2) {
                //Toasty.error(GameCenterActivity.this, "请至少选择一注,请重新投注.", 2000).show();
                return;
            }
            if (s.contains(",")) {
                String[] sp = s.split(",");
                for (int j = 0; j < sp.length; j++) {
                    if (sp[j].length() != 2) {
                        //Toasty.error(GameCenterActivity.this, "投注个数不正确,请重新投注.", 2000).show();
                        return;
                    }
                    if ("star_2_any_group_single".equals(code)
                            || "2min_star_2_any_group_single".equals(code)) {
                        String s1 = sp[j].substring(0, 1);
                        String s2 = sp[j].substring(1, 2);
                        if (s1.equals(s2)) {
                            //Toasty.error(GameCenterActivity.this, "两个号码不能完全相同", 2000).show();
                            return;
                        }
                    }
                }
            } else {
                if (s.length() != 2) {
                    //Toasty.error(GameCenterActivity.this, "投注个数不正确,请重新投注.", 2000).show();
                    return;
                }
                if ("star_2_any_group_single".equals(code)
                        || "2min_star_2_any_group_single".equals(code)) {
                    String s1 = s.substring(0, 1);
                    String s2 = s.substring(1, 2);
                    if (s1.equals(s2)) {
                        //Toasty.error(GameCenterActivity.this, "两个号码不能完全相同", 2000).show();
                        return;
                    }
                }


            }
            List<List<String>> check = getCheck(ns);
            for (int i = 0; i < check.size(); i++) {
                List<String> list = check.get(i);
                locationText = list.get(0);
                location = list.get(1);
                nums = 1;

                pickedNumber = s;
                GameTypeName = GameTypeName + "-" + list.get(0);
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
                if (nums == 0) {
                    return;
                }
                AddLottery();
                GameTypeName = name.get(position);
            }


            return;
        }
        if (
                "star_3_any_single".equals(code)
                        || "2min_star_3_any_single".equals(code)
                        || "star_3_any_group_single".equals(code)
                        || "2min_star_3_any_group_single".equals(code)
                        || "star_3_any_group_single_6".equals(code)
                        || "2min_star_3_any_group_single_6".equals(code)
                        || "star_3_any_group_diverse".equals(code)
                        || "2min_star_3_any_group_diverse".equals(code)

                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearCheck);
            EditText editT = (EditText) inte.findViewById(R.id.optional2_editText);
            String s = editT.getText().toString().trim();
            if ("".equals(s) || " ".equals(s)) {
                //Toasty.error(GameCenterActivity.this, "未输入投注", 2000).show();
                return;
            }
            String str = "";
            int n = 0;
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    n++;
                }
            }
            if (n < 3) {
                //Toasty.error(GameCenterActivity.this, "至少选择三个", 2000).show();
                return;

            }
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    if (str == "") {
                        str = at.getText().toString().substring(0, 1);
                    } else {
                        str = str + at.getText().toString().substring(0, 1);
                    }
                }
            }
            List<List<String>> check3 = getCheck3(str);
            if ("".equals(s) || " ".equals(s)) {
                //Toasty.error(GameCenterActivity.this, "未输入投注", 2000).show();
                return;
            } else {
                if (s.substring(s.length() - 1, s.length()).equals(",")) {
                    s = s.substring(0, s.length() - 1);
                }
            }

            if (s.contains(",")) {
                String[] sp = s.split(",");
                nums = sp.length;
                for (int i = 0; i < sp.length; i++) {
                    if (sp[i].contains(" ")) {
                        //Toasty.error(GameCenterActivity.this, "不能存在空格", 2000).show();
                        return;
                    }
                    if (sp[i].length() != 3) {
                        //Toasty.error(GameCenterActivity.this, "三个号码位一注", 2000).show();
                        return;
                    } else {
                        if ("star_3_any_group_single".equals(code)
                                || "2min_star_3_any_group_single".equals(code)) {
                            String s1 = sp[i].substring(0, 1);
                            String s2 = sp[i].substring(1, 2);
                            String s3 = sp[i].substring(2, 3);
                            if (!s1.equals(s2) && !s1.equals(s3) && !s2.equals(s3)) {
                                //Toasty.error(GameCenterActivity.this, "必须有两个号码相同", 2000).show();
                                return;
                            }
                            if (s1.equals(s2) && s1.equals(s3)) {
                                //Toasty.error(GameCenterActivity.this, "三个号码不能同时相同", 2000).show();
                                return;
                            }
                        }
                        if ("star_3_any_group_single_6".equals(code)
                                || "2min_star_3_any_group_single_6".equals(code)) {
                            String s1 = sp[i].substring(0, 1);
                            String s2 = sp[i].substring(1, 2);
                            String s3 = sp[i].substring(2, 3);
                            if (s1.equals(s2) || s1.equals(s3) || s2.equals(s3)) {
                                //Toasty.error(GameCenterActivity.this, "不能有相同的号码", 2000).show();
                                return;
                            }
                        }
                        if ("star_3_any_group_diverse".equals(code)
                                || "2min_star_3_any_group_diverse".equals(code)) {
                            String s1 = sp[i].substring(0, 1);
                            String s2 = sp[i].substring(1, 2);
                            String s3 = sp[i].substring(2, 3);
                            if (!s1.equals(s2) && !s1.equals(s3) && !s2.equals(s3)) {
                                //Toasty.error(GameCenterActivity.this, "至少需要两个相同的号码", 2000).show();
                                return;
                            }
                        }


                    }
                }
            } else {
                if (s.contains(" ")) {
                    //Toasty.error(GameCenterActivity.this, "不能存在空格", 2000).show();
                    return;
                }
                if (s.length() != 3) {
                    //Toasty.error(GameCenterActivity.this, "三个号码位一注", 2000).show();
                    return;
                } else {
                    if ("star_3_any_group_single".equals(code)
                            || "2min_star_3_any_group_single".equals(code)) {
                        String s1 = s.substring(0, 1);
                        String s2 = s.substring(1, 2);
                        String s3 = s.substring(2, 3);
                        if (!s1.equals(s2) && !s1.equals(s3) && !s2.equals(s3)) {
                            //Toasty.error(GameCenterActivity.this, "必须有两个号码相同", 2000).show();
                            return;
                        }
                        if (s1.equals(s2) && s1.equals(s3)) {
                            //Toasty.error(GameCenterActivity.this, "三个号码不能同时相同", 2000).show();
                            return;
                        }
                    }
                    if ("star_3_any_group_single_6".equals(code)
                            || "2min_star_3_any_group_single_6".equals(code)) {
                        String s1 = s.substring(0, 1);
                        String s2 = s.substring(1, 2);
                        String s3 = s.substring(2, 3);
                        if (s1.equals(s2) || s1.equals(s3) || s2.equals(s3)) {
                            //Toasty.error(GameCenterActivity.this, "不能有相同的号码", 2000).show();
                            return;
                        }
                    }
                    if ("star_3_any_group_diverse".equals(code)
                            || "2min_star_3_any_group_diverse".equals(code)) {
                        String s1 = s.substring(0, 1);
                        String s2 = s.substring(1, 2);
                        String s3 = s.substring(2, 3);
                        if (!s1.equals(s2) && !s1.equals(s3) && !s2.equals(s3)) {
                            //Toasty.error(GameCenterActivity.this, "至少需要两个相同的号码", 2000).show();
                            return;
                        }
                    }

                }
            }
            pickedNumber = s;
            for (int i = 0; i < check3.size(); i++) {
                List<String> list = check3.get(i);
                locationText = list.get(0);
                location = list.get(1);
                nums = 1;

                pickedNumber = s;
                Log.d("个十百千万选择", list.get(0));
                GameTypeName = GameTypeName + "-" + list.get(0);
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
                if (nums == 0) {
                    return;
                }
                AddLottery();
                GameTypeName = GameTypeName.substring(0, GameTypeName.length() - 6);
            }
            return;
        }
        if (
                "eleven_star_3_prev_single".equals(code)
                        || "eleven_star_3_prev_group_single".equals(code)
                        || "eleven_star_2_prev_single".equals(code)
                        || "eleven_star_2_prev_group_single".equals(code)


                        || "eleven_any_one_single".equals(code)
                        || "eleven_any_two_single".equals(code)
                        || "eleven_any_three_single".equals(code)
                        || "eleven_any_four_single".equals(code)
                        || "eleven_any_five_single".equals(code)
                        || "eleven_any_six_single".equals(code)
                        || "eleven_any_seven_single".equals(code)
                        || "eleven_any_eight_single".equals(code)

                        || "PK10_1st_2nd_single".equals(code)
                        || "PK10_1st_2nd_3th_single".equals(code)

                ) {
            final EditText g2_editText = (EditText) inte.findViewById(R.id.g2_EditText);
            String s = g2_editText.getText().toString().trim();
            if ("".equals(s) || " ".equals(s)) {
                //Toasty.error(GameCenterActivity.this, "未输入投注", 2000).show();
                return;
            }
            int co = 0;
            int ns = 11;

            if (
                    "PK10_1st_2nd_single".equals(code)
                            || "PK10_1st_2nd_3th_single".equals(code)
                    ) {
                ns = 10;
            }
            if ("eleven_any_one_single".equals(code)) {
                co = 1;
            }
            if (
                    "eleven_star_3_prev_single".equals(code)
                            || "eleven_star_3_prev_group_single".equals(code)
                            || "eleven_any_three_single".equals(code)
                            || "PK10_1st_2nd_3th_single".equals(code)

                    ) {
                co = 3;
            }
            if (
                    "eleven_star_2_prev_single".equals(code)
                            || "eleven_star_2_prev_group_single".equals(code)
                            || "eleven_any_two_single".equals(code)
                            || "PK10_1st_2nd_single".equals(code)

                    ) {
                co = 2;
            }
            if ("eleven_any_four_single".equals(code)) {
                co = 4;
            }
            if ("eleven_any_five_single".equals(code)) {
                co = 5;
            }
            if ("eleven_any_six_single".equals(code)) {
                co = 6;
            }
            if ("eleven_any_seven_single".equals(code)) {
                co = 7;
            }
            if ("eleven_any_eight_single".equals(code)) {
                co = 8;
            }
            if (s.contains(",")) {
                String[] split = s.split(",");
                nums = split.length;
                for (int i = 0; i < split.length; i++) {
                    String s1 = split[i];
                    if (!"eleven_any_one_single".equals(code)) {
                        if (s1.contains(" ")) {
                            String[] sp1 = s1.split(" ");
                            if (sp1.length != co) {
                                //Toasty.error(GameCenterActivity.this, "投注内容不正确", 2000).show();
                                return;
                            } else {
                                for (int i1 = 0; i1 < sp1.length; i1++) {
                                    if (sp1[i1].length() != 2) {
                                        //Toasty.error(GameCenterActivity.this, "投注内容位数不正确", 2000).show();
                                        return;
                                    }
                                    int i2 = Integer.parseInt(sp1[i1]);
                                    if (i2 < 1 || i2 > ns) {
                                        //Toasty.error(GameCenterActivity.this, "有投注内容不在范围内", 2000).show();
                                        return;
                                    }
                                }
                            }
                        } else {
                            //Toasty.error(GameCenterActivity.this, "投注内容不足", 2000).show();
                            return;
                        }
                    }

                }
            } else {
                nums = 1;
                if (!"eleven_any_one_single".equals(code)) {
                    if (s.contains(" ")) {
                        String[] sp1 = s.split(" ");
                        if (sp1.length != co) {
                            //Toasty.error(GameCenterActivity.this, "投注内容不正确", 2000).show();
                            return;
                        } else {
                            for (int i1 = 0; i1 < sp1.length; i1++) {
                                if (sp1[i1].length() != 2) {
                                    //Toasty.error(GameCenterActivity.this, "投注内容位数不正确", 2000).show();
                                    return;
                                }
                                int i2 = Integer.parseInt(sp1[i1]);
                                if (i2 < 1 || i2 > ns) {
                                    //Toasty.error(GameCenterActivity.this, "有投注内容不在范围内", 2000).show();
                                    return;
                                }
                            }
                        }
                    }/* else {
                                //Toasty.error(GameCenterActivity.this, "投注内容不足", 2000).show();
                                return;
                            }*/
                }

            }
            pickedNumber = s;

            int i = Integer.parseInt("05");

        }
        if (
                "k3_double_single".equals(code)
                        || "k3_different_3_single".equals(code)
                ) {
            final EditText g2_editText = (EditText) inte.findViewById(R.id.g2_EditText);
            String s = g2_editText.getText().toString().trim();

            if (",".equals(s.substring(s.length() - 1, s.length()))) {
                s = s.substring(0, s.length() - 1);
            }
            Log.d("江苏快三单", s + "");
            String s1 = "";
            String s2 = "";
            String s3 = "";
            if (s.contains(",")) {
                String[] sp1 = s.split(",");
                nums = sp1.length;

                for (int i = 0; i < sp1.length; i++) {
                    String ss = sp1[i];
                    if (ss.length() != 3) {
                        //Toasty.error(GameCenterActivity.this, "投注内容位数不正确", 2000).show();
                        return;
                    } else {
                        s1 = ss.substring(0, 1);
                        s2 = ss.substring(1, 2);
                        s3 = ss.substring(2, 3);
                        if (s1.equals(s2)) {
                            if (s1.equals(s3)) {
                                //Toasty.error(GameCenterActivity.this, "三个号码不能完全相同", 2000).show();
                                return;
                            }
                        }
                        if ("k3_double_single".equals(code)) {
                            if (!s3.equals(s2) && !s3.equals(s1) && !s2.equals(s1)) {
                                //Toasty.error(GameCenterActivity.this, "必须有两个同号", 2000).show();
                                return;

                            }
                        }
                        if ("k3_different_3_single".equals(code)) {
                            if (s3.equals(s2) || s3.equals(s1) || s2.equals(s1)) {
                                //Toasty.error(GameCenterActivity.this, "不能存在相同的号码", 2000).show();
                                return;

                            }
                        }


                    }
                    int i1 = Integer.parseInt(s1);
                    int i2 = Integer.parseInt(s2);
                    int i3 = Integer.parseInt(s3);
                    if (i1 < 1 || i1 > 6 || i2 < 1 || i2 > 6 || i3 < 1 || i3 > 6) {
                        //Toasty.error(GameCenterActivity.this, "选取号码在1-6之间", 2000).show();
                        return;
                    }
                }
            } else {


                nums = 1;
                if (s.length() != 3) {
                    //Toasty.error(GameCenterActivity.this, "投注内容位数不正确", 2000).show();
                    return;
                } else {
                    s1 = s.substring(0, 1);
                    s2 = s.substring(1, 2);
                    s3 = s.substring(2, 3);
                    if (s1.equals(s2) && s1.equals(s3)) {

                        //Toasty.error(GameCenterActivity.this, "三个号码不能完全相同", 2000).show();
                        return;

                    }

                    if ("k3_double_single".equals(code)) {
                        if (!s3.equals(s2) && !s3.equals(s1) && !s2.equals(s1)) {
                            //Toasty.error(GameCenterActivity.this, "必须有两个同号", 2000).show();
                            return;

                        }
                    }
                    if ("k3_different_3_single".equals(code)) {
                        if (s3.equals(s2) || s3.equals(s1) || s2.equals(s1)) {
                            //Toasty.error(GameCenterActivity.this, "不能存在相同的号码", 2000).show();
                            return;

                        }
                    }

                }
                int i1 = Integer.parseInt(s1);
                int i2 = Integer.parseInt(s2);
                int i3 = Integer.parseInt(s3);
                if (i1 < 1 || i1 > 6 || i2 < 1 || i2 > 6 || i3 < 1 || i3 > 6) {
                    //Toasty.error(GameCenterActivity.this, "选取号码在1-6之间", 2000).show();
                    return;
                }
            }
            pickedNumber = s;

        }
        if ("k3_different_2_single".equals(code)) {
            final EditText g2_editText = (EditText) inte.findViewById(R.id.g2_EditText);
            String s = g2_editText.getText().toString().trim();

            if (",".equals(s.substring(s.length() - 1, s.length()))) {
                s = s.substring(0, s.length() - 1);
            }
            Log.d("江苏快三二不同号单", s + "");
            String s1 = "";
            String s2 = "";
            if (s.contains(",")) {
                String[] sp1 = s.split(",");
                nums = sp1.length;

                for (int i = 0; i < sp1.length; i++) {
                    String ss = sp1[i];
                    if (ss.length() != 2) {
                        //Toasty.error(GameCenterActivity.this, "投注内容位数不正确", 2000).show();
                        return;
                    } else {
                        s1 = ss.substring(0, 1);
                        s2 = ss.substring(1, 2);

                        if (s1.equals(s2)) {
                            //Toasty.error(GameCenterActivity.this, "两个号码不能完全相同", 2000).show();
                            return;
                        }
                    }


                    int i1 = Integer.parseInt(s1);
                    int i2 = Integer.parseInt(s2);

                    if (i1 < 1 || i1 > 6 || i2 < 1 || i2 > 6) {
                        //Toasty.error(GameCenterActivity.this, "选取号码在1-6之间", 2000).show();
                        return;
                    }
                }
            } else {
                nums = 1;
                if (s.length() != 2) {
                    //Toasty.error(GameCenterActivity.this, "投注内容位数不正确", 2000).show();
                    return;
                } else {
                    s1 = s.substring(0, 1);
                    s2 = s.substring(1, 2);

                    if (s1.equals(s2)) {

                        //Toasty.error(GameCenterActivity.this, "两个号码不能完全相同", 2000).show();
                        return;

                    }


                }
                int i1 = Integer.parseInt(s1);
                int i2 = Integer.parseInt(s2);

                if (i1 < 1 || i1 > 6 || i2 < 1 || i2 > 6) {
                    //Toasty.error(GameCenterActivity.this, "选取号码在1-6之间", 2000).show();
                    return;
                }
            }
            pickedNumber = s;
        }
        if (
                "sequence_star_3_single".equals(code)
                        || "sequence_star_3_group_3_single".equals(code)
                        || "sequence_star_3_group_6_single".equals(code)
                        || "sequence_star_3_group_diverse".equals(code)
                        || "3D_star_3_single".equals(code)
                        || "3D_star_3_group_3_single".equals(code)
                        || "3D_star_3_group_6_single".equals(code)
                        || "3D_star_3_group_diverse".equals(code)

                ) {
            final EditText g2_editText = (EditText) inte.findViewById(R.id.g2_EditText);
            String s = g2_editText.getText().toString().trim();
            if ("".equals(s) || " ".equals(s)) {
                //Toasty.error(GameCenterActivity.this, "未输入投注", 2000).show();
                return;
            }
            if (",".equals(s.substring(s.length() - 1, s.length()))) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.contains(",")) {

                String[] sp = s.split(",");
                nums = sp.length;
                for (int i = 0; i < sp.length; i++) {
                    if (sp[i].length() != 3) {
                        //Toasty.error(GameCenterActivity.this, "投注内容位数不正确", 2000).show();
                        return;
                    } else {
                        if (
                                "sequence_star_3_group_3_single".equals(code)
                                        || "3D_star_3_group_3_single".equals(code)
                                ) {
                            String s1 = sp[i].substring(0, 1);
                            String s2 = sp[i].substring(1, 2);
                            String s3 = sp[i].substring(2, 3);
                            if (s1.equals(s2) && s1.equals(s3)) {
                                //Toasty.error(GameCenterActivity.this, "三个号码不能完全相同", 2000).show();
                                return;
                            }
                            if (!s1.equals(s2) && !s1.equals(s3) && !s2.equals(s3)) {
                                //Toasty.error(GameCenterActivity.this, "需要两个相同的号码", 2000).show();
                                return;
                            }
                        }
                        if (
                                "sequence_star_3_group_6_single".equals(code)
                                        || "3D_star_3_group_6_single".equals(code)
                                ) {
                            String s1 = sp[i].substring(0, 1);
                            String s2 = sp[i].substring(1, 2);
                            String s3 = sp[i].substring(2, 3);
                            if (s1.equals(s2) && s1.equals(s3)) {
                                //Toasty.error(GameCenterActivity.this, "不能存在相同的号码", 2000).show();
                                return;
                            }
                            if (s1.equals(s2) || s1.equals(s3) || s2.equals(s3)) {
                                //Toasty.error(GameCenterActivity.this, "不能存在相同的号码", 2000).show();
                                return;
                            }
                        }
                        if (
                                "sequence_star_3_group_diverse".equals(code)
                                        || "3D_star_3_group_diverse".equals(code)
                                ) {
                            String s1 = sp[i].substring(0, 1);
                            String s2 = sp[i].substring(1, 2);
                            String s3 = sp[i].substring(2, 3);
                            if (s1.equals(s2) && s1.equals(s3)) {
                                //Toasty.error(GameCenterActivity.this, "三个号码不能完全相同", 2000).show();
                                return;
                            }

                        }

                    }
                }
            } else {
                nums = 1;
                if (s.length() != 3) {
                    //Toasty.error(GameCenterActivity.this, "投注内容位数不正确", 2000).show();
                    return;
                } else {
                    if (
                            "sequence_star_3_group_3_single".equals(code) ||
                                    "3D_star_3_group_3_single".equals(code)) {
                        String s1 = s.substring(0, 1);
                        String s2 = s.substring(1, 2);
                        String s3 = s.substring(2, 3);
                        if (s1.equals(s2) && s1.equals(s3)) {
                            //Toasty.error(GameCenterActivity.this, "三个号码不能完全相同", 2000).show();
                            return;
                        }
                        if (!s1.equals(s2) && !s1.equals(s3) && !s2.equals(s3)) {
                            //Toasty.error(GameCenterActivity.this, "需要两个相同的号码", 2000).show();
                            return;
                        }
                    }
                    if (
                            "sequence_star_3_group_6_single".equals(code)
                                    || "3D_star_3_group_6_single".equals(code)
                            ) {
                        String s1 = s.substring(0, 1);
                        String s2 = s.substring(1, 2);
                        String s3 = s.substring(2, 3);
                        if (s1.equals(s2) && s1.equals(s3)) {
                            //Toasty.error(GameCenterActivity.this, "不能存在相同的号码", 2000).show();
                            return;
                        }
                        if (s1.equals(s2) || s1.equals(s3) || s2.equals(s3)) {
                            //Toasty.error(GameCenterActivity.this, "不能存在相同的号码", 2000).show();
                            return;
                        }
                    }
                    if (
                            "sequence_star_3_group_diverse".equals(code)
                                    || "3D_star_3_group_diverse".equals(code)
                            ) {
                        String s1 = s.substring(0, 1);
                        String s2 = s.substring(1, 2);
                        String s3 = s.substring(2, 3);
                        if (s1.equals(s2) && s1.equals(s3)) {
                            //Toasty.error(GameCenterActivity.this, "三个号码不能完全相同", 2000).show();
                            return;
                        }

                    }
                }
            }
            pickedNumber = s;
        }
        if (
                "sequence_star_2_prev_single".equals(code)
                        || "sequence_star_2_next_single".equals(code)
                        || "sequence_star_2_prev_group_single".equals(code)
                        || "sequence_star_2_next_group_single".equals(code)

                        || "3D_star_2_prev_single".equals(code)
                        || "3D_star_2_next_single".equals(code)
                        || "3D_star_2_prev_group_single".equals(code)
                        || "3D_star_2_next_group_single".equals(code)

                ) {
            final EditText g2_editText = (EditText) inte.findViewById(R.id.g2_EditText);
            String s = g2_editText.getText().toString().trim();
            if ("".equals(s) || " ".equals(s)) {
                //Toasty.error(GameCenterActivity.this, "未输入投注", 2000).show();
                return;
            }
            if (",".equals(s.substring(s.length() - 1, s.length()))) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.contains(",")) {

                String[] sp = s.split(",");
                nums = sp.length;
                for (int i = 0; i < sp.length; i++) {
                    if (sp[i].length() != 2) {
                        //Toasty.error(GameCenterActivity.this, "投注内容位数不正确", 2000).show();
                        return;
                    } else {
                        if (
                                "sequence_star_2_prev_group_single".equals(code)
                                        || "sequence_star_2_next_group_single".equals(code)
                                        || "3D_star_2_prev_group_single".equals(code)
                                        || "3D_star_2_next_group_single".equals(code)
                                ) {
                            String s1 = sp[i].substring(0, 1);
                            String s2 = sp[i].substring(1, 2);

                            if (s1.equals(s2)) {
                                //Toasty.error(GameCenterActivity.this, "两个号码不能完全相同", 2000).show();
                                return;
                            }
                        }

                    }
                }
            } else {
                nums = 1;
                if (s.length() != 2) {
                    //Toasty.error(GameCenterActivity.this, "投注内容位数不正确", 2000).show();
                    return;
                } else {
                    if (
                            "sequence_star_2_prev_group_single".equals(code)
                                    || "sequence_star_2_next_group_single".equals(code)
                                    || "3D_star_2_prev_group_single".equals(code)
                                    || "3D_star_2_next_group_single".equals(code)
                            ) {
                        String s1 = s.substring(0, 1);
                        String s2 = s.substring(1, 2);

                        if (s1.equals(s2)) {
                            //Toasty.error(GameCenterActivity.this, "两个号码不能完全相同", 2000).show();
                            return;
                        }
                    }
                }
            }
            pickedNumber = s;


        }
        if (
                "star_3_any_sum".equals(code)
                        || "2min_star_3_any_sum".equals(code)
                        || "star_3_any_group_sum".equals(code)
                        || "2min_star_3_any_group_sum".equals(code)
                ) {
            LinearLayout LinearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearCheck);
            LinearLayout LinearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            LinearLayout LinearThree = (LinearLayout) inte.findViewById(R.id.LinearThree);

            String str = "";
            String strc = "";
            int n = 0;


            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    n++;
                    if (strc == "") {
                        strc = at.getText().toString().substring(0, 1);
                    } else {
                        strc = strc + at.getText().toString().substring(0, 1);
                    }
                }
            }
            if (n < 3) {
                //Toasty.error(GameCenterActivity.this, "至少选择三个", 2000).show();
                return;

            }
            List<List<String>> check3 = getCheck3(strc);
            List<Integer> ns = new ArrayList<>();
            for (int i = 0; i < LinearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearOne.getChildAt(i);
                if (at.isChecked()) {
                    ns.add(Integer.parseInt(at.getText().toString()));
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < LinearTwo.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearTwo.getChildAt(i);
                if (at.isChecked()) {
                    ns.add(Integer.parseInt(at.getText().toString()));
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }
            for (int i = 0; i < LinearThree.getChildCount(); i++) {
                CheckBox at = (CheckBox) LinearThree.getChildAt(i);
                if (at.isChecked()) {
                    ns.add(Integer.parseInt(at.getText().toString()));
                    if (str == "") {
                        str = at.getText().toString();
                    } else {
                        str = str + "," + at.getText().toString();
                    }
                }
            }

            for (int i = 0; i < check3.size(); i++) {
                List<String> list = check3.get(i);
                locationText = list.get(0);
                location = list.get(1);
                if ("star_3_any_sum".equals(code)
                        || "2min_star_3_any_sum".equals(code)) {
                    nums = getNum(ns) * check3.size();
                }
                if ("star_3_any_group_sum".equals(code)
                        || "2min_star_3_any_group_sum".equals(code)) {
                    nums = getNumZuHe(ns) * check3.size();
                }


                pickedNumber = str;
                Log.d("个十百千万选择", list.get(0));
                GameTypeName = GameTypeName + "-" + list.get(0);
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
                if (nums == 0) {
                    return;
                }
                AddLottery();
                GameTypeName = GameTypeName.substring(0, GameTypeName.length() - 6);
            }
            return;
        }
        if (
                "star_4_any_single".equals(code)
                        || "2min_star_4_any_single".equals(code)
                ) {
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearCheck);
            EditText editT = (EditText) inte.findViewById(R.id.optional2_editText);
            String s = editT.getText().toString().trim();
            if ("".equals(s) || " ".equals(s)) {
                //Toasty.error(GameCenterActivity.this, "未输入投注", 2000).show();
                return;
            }
            String str = "";
            int n = 0;
            int ns = 0;

            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    n++;
                    if (str == "") {
                        str = at.getText().toString().substring(0, 1);
                    } else {
                        str = str + at.getText().toString().substring(0, 1);
                    }
                }
            }
            if (n < 4) {
                //Toasty.error(GameCenterActivity.this, "至少选择四个", 2000).show();
                return;

            }
            List<List<String>> check4 = getCheck4(str);
            if ("".equals(s) || " ".equals(s)) {
                //Toasty.error(GameCenterActivity.this, "未输入投注", 2000).show();
                return;
            } else {
                if (s.substring(s.length() - 1, s.length()).equals(",")) {
                    s = s.substring(0, s.length() - 1);
                }
            }

            if (s.contains(",")) {
                String[] sp = s.split(",");
                ns = sp.length;
                for (int i = 0; i < sp.length; i++) {
                    if (sp[i].contains(" ")) {
                        //Toasty.error(GameCenterActivity.this, "不能存在空格", 2000).show();
                        return;
                    }
                    if (sp[i].length() != 4) {
                        //Toasty.error(GameCenterActivity.this, "四个号码位一注", 2000).show();
                        return;
                    } else {

                    }
                }
            } else {
                ns = 1;
                if (s.contains(" ")) {
                    //Toasty.error(GameCenterActivity.this, "不能存在空格", 2000).show();
                    return;
                }
                if (s.length() != 4) {
                    //Toasty.error(GameCenterActivity.this, "四个号码位一注", 2000).show();
                    return;
                } else {

                }
            }
            pickedNumber = s;
            for (int i = 0; i < check4.size(); i++) {
                List<String> list = check4.get(i);
                locationText = list.get(0);
                location = list.get(1);
                nums = check4.size() * ns;

                pickedNumber = s;
                Log.d("个十百千万选择", list.get(0));
                GameTypeName = GameTypeName + "-" + list.get(0);
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
                amount = amount * Nums / check4.size();
                classCode = code;
                multiple = Nums;
                if (nums == 0) {
                    return;
                }
                AddLottery();
                GameTypeName = GameTypeName.substring(0, 4);
            }
            return;
        }
        if (
                "star_4_any_group_24".equals(code)
                        || "2min_star_4_any_group_24".equals(code)
                        || "star_4_any_group_6".equals(code)
                        || "2min_star_4_any_group_6".equals(code)
                ) {
            String str = "";
            String strNum = "";
            int n = 0;
            int ns = 0;
            int num_count = 0;
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearCheck);
            LinearLayout linearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    n++;
                    if (str == "") {
                        str = at.getText().toString().substring(0, 1);
                    } else {
                        str = str + at.getText().toString().substring(0, 1);
                    }
                }
            }
            if (n < 4) {
                //Toasty.error(GameCenterActivity.this, "至少选择四个", 2000).show();
                return;

            }
            for (int i = 1; i < linearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) linearOne.getChildAt(i);
                if (at.isChecked()) {
                    num_count++;
                    if (strNum == "") {
                        strNum = at.getText().toString();
                    } else {
                        strNum = strNum + "," + at.getText().toString();
                    }
                }
            }
            if (num_count < 4) {
                //Toasty.error(GameCenterActivity.this, "至少选择四个号码", 2000).show();
                return;
            }
            List<List<String>> check4 = getCheck4(str);
            for (int i = 0; i < check4.size(); i++) {
                List<String> list = check4.get(i);
                locationText = list.get(0);
                location = list.get(1);
                if ("star_4_any_group_24".equals(code)
                        || "2min_star_4_any_group_24".equals(code)) {
                    int jn = RxUtils.getInstance().JieCheng(num_count);
                    int j4 = RxUtils.getInstance().JieCheng(4);
                    int jn4 = RxUtils.getInstance().JieCheng(num_count - 4);
                    nums = jn / (j4 * jn4);
                } else if ("star_4_any_group_6".equals(code)
                        || "2min_star_4_any_group_6".equals(code)) {
                    nums = num_count * (num_count - 1) / 2;
                }

                Log.d("个十百千万Nums", nums + "");
                pickedNumber = strNum;
                Log.d("个十百千万选择", list.get(0));
                GameTypeName = GameTypeName + "-" + list.get(0);
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
                if (nums == 0) {
                    return;
                }
                AddLottery();
                GameTypeName = GameTypeName.substring(0, 4);
            }
            return;
        }
        if (
                "star_4_any_group_12".equals(code)
                        || "2min_star_4_any_group_12".equals(code)
                        || "star_4_any_group_4".equals(code)
                        || "2min_star_4_any_group_4".equals(code)
                ) {
            String str = "";
            String strNum1 = "";
            String strNum2 = "";
            int n = 0;
            int ns = 0;
            int c1 = 0;
            int c2 = 0;
            List<String> nns = new ArrayList<>();
            List<String> mms = new ArrayList<>();
            LinearLayout linear1 = (LinearLayout) inte.findViewById(R.id.LinearCheck);
            LinearLayout linearOne = (LinearLayout) inte.findViewById(R.id.LinearOne);
            LinearLayout linearTwo = (LinearLayout) inte.findViewById(R.id.LinearTwo);
            for (int i = 0; i < linear1.getChildCount(); i++) {
                CheckBox at = (CheckBox) linear1.getChildAt(i);
                if (at.isChecked()) {
                    n++;

                    if (str == "") {
                        str = at.getText().toString().substring(0, 1);
                    } else {
                        str = str + at.getText().toString().substring(0, 1);
                    }
                }
            }
            if (n < 4) {
                //Toasty.error(GameCenterActivity.this, "至少选择四个", 2000).show();
                return;

            }
            for (int i = 1; i < linearOne.getChildCount(); i++) {
                CheckBox at = (CheckBox) linearOne.getChildAt(i);
                if (at.isChecked()) {
                    c1++;
                    nns.add(at.getText().toString());
                    strNum1 = strNum1 + at.getText().toString();

                }
            }
            for (int i = 1; i < linearTwo.getChildCount(); i++) {
                CheckBox at = (CheckBox) linearTwo.getChildAt(i);
                if (at.isChecked()) {
                    c2++;
                    strNum2 = strNum2 + at.getText().toString();
                    mms.add(at.getText().toString());
                }
            }
            if (c1 > 0 && c2 > 2) {
                int d = 0;
                for (int i = 0; i < nns.size(); i++) {
                    Log.d("重号", nns.get(i) + "");
                    for (int i1 = 0; i1 < mms.size(); i1++) {
                        Log.d("单号", mms.get(i) + "");
                        if (nns.get(i) == mms.get(i1)) {
                            d++;
                        }
                    }
                }
                if ("star_4_any_group_12".equals(code)
                        || "2min_star_4_any_group_12".equals(code)) {
                    nums = c1 * c2 * (c2 - 1) / 2 - d * (c2 - 1);
                    Log.d("组选注数", nums + "");
                }
                if ("star_4_any_group_4".equals(code)
                        || "2min_star_4_any_group_4".equals(code)) {
                    nums = c1 * c2 - d;
                    Log.d("组选注数", nums + "");
                }

            } else {
                if ("star_4_any_group_12".equals(code)
                        || "2min_star_4_any_group_12".equals(code)) {
                    if (c1 < 0 || c1 == 0) {
                        //Toasty.error(GameCenterActivity.this, "重号个数不够", 2000).show();
                        return;
                    }
                    if (c2 < 2 || c2 == 2) {
                        //Toasty.error(GameCenterActivity.this, "单号个数不够", 2000).show();
                        return;
                    }
                }

            }
            List<List<String>> check4 = getCheck4(str);
            for (int i = 0; i < check4.size(); i++) {
                List<String> list = check4.get(i);
                locationText = list.get(0);
                location = list.get(1);


                Log.d("个十百千万Nums", nums + "");
                pickedNumber = strNum1 + "," + strNum2;
                Log.d("个十百千万选择", list.get(0));
                GameTypeName = GameTypeName + "-" + list.get(0);
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
                if (nums == 0) {
                    return;
                }
                AddLottery();
                GameTypeName = GameTypeName.substring(0, 4);
            }
            return;
        }
    /*    if (nums == 0)

        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            //Toasty.error(GameCenterActivity.this, "投注注数为0,请重新投注", 2000).show();
                        }
                    });

                }
            }).start();

            return;
        }*/

        Nums = Integer.parseInt(edit1.getText().

                toString().

                trim());
        amount = nums * 2;
        if (PriceUnit == 2)

        {
            amount = amount / 10;
        }
        if (PriceUnit == 3)

        {
            amount = amount / 100;
        }
        if (PriceUnit == 4)

        {
            amount = amount / 1000;
        }
        Log.d("总注数", nums + "");
        amount = amount * Nums;
        classCode = code;
        multiple = Nums;
        priceType = SpinnerMoney.getSelectedItemPosition();
        
       /* TextView Zhu = (TextView) contentView.findViewById(R.id.Zhu);
        TextView Amounts = (TextView) contentView.findViewById(R.id.GameAmounts);
        TextView TouZhuContent = (TextView) contentView.findViewById(R.id.TouZhuContent);
        TextView GameType = (TextView) contentView.findViewById(R.id.GameType);
        GameType.setText(GameTypeName);
        if (pickedText != "") {
            TouZhuContent.setText(pickedText);
        } else {

            TouZhuContent.setText(pickedNumber);
        }
        Zhu.setText(nums + "");
        Amounts.setText(amount + "");
        if (nums > 0)

        {
            alertView.show();
        }*/
    }

    public List<List<String>> getCheck3(String string) {

        List<List<String>> check3 = new ArrayList<>();
        int z, y;
        int length = string.length();
        for (int i = 0; i < length; i++) {
            string.charAt(i);
//            System.out.println(string.charAt(i));
            String s = String.valueOf(string.charAt(i));
            for (int j = i + 1; j < length; j++) {
//                stringBuilder.append(string.charAt(j));
                String s1 = String.valueOf(string.charAt(j));
//                System.out.println(""+s+s1);
                for (z = j + 1; z < length; z++) {
                    List<String> ns = new ArrayList<>();
                    String s2 = String.valueOf(string.charAt(z));
                    String x = s + "," + s1 + "," + s2;
                    System.out.println(x);
                    ns.add(x);
                    Log.d("万千百十个文字", x);
                    String nns = "0,0,0,0,0";
                    StringBuilder sb = new StringBuilder(nns);
                    for (int i1 = 0; i1 < x.length(); i1 = i1 + 2) {
                        String sx = x.substring(i1, i1 + 1);

                        if (sx.equals("万")) {
                            sb.replace(0, 1, "1");
                        }
                        if (sx.equals("千")) {
                            sb.replace(2, 3, "1");
                        }
                        if (sx.equals("百")) {
                            sb.replace(4, 5, "1");
                        }
                        if (sx.equals("十")) {
                            sb.replace(6, 7, "1");
                        }
                        if (sx.equals("个")) {
                            sb.replace(8, 9, "1");
                        }
                    }
                    Log.d("万千百十个数字", sb.toString());
                    ns.add(sb.toString());
                  /*  for (y=z+1;y<length;y++){
                        String s3 = String.valueOf(string.charAt(y));
                        System.out.println(""+s+s1+s2+s3);
                    }*/
                    check3.add(ns);
                }
            }

        }
        return check3;
    }

    public List<List<String>> getCheck4(String string) {

        List<List<String>> check4 = new ArrayList<>();
        int z, y;
        int length = string.length();
        for (int i = 0; i < length; i++) {
            string.charAt(i);
//            System.out.println(string.charAt(i));
            String s = String.valueOf(string.charAt(i));
            for (int j = i + 1; j < length; j++) {
//                stringBuilder.append(string.charAt(j));
                String s1 = String.valueOf(string.charAt(j));
//                System.out.println(""+s+s1);
                for (z = j + 1; z < length; z++) {
                    List<String> ns = new ArrayList<>();
                    String s2 = String.valueOf(string.charAt(z));

                    for (y = z + 1; y < length; y++) {
                        String s3 = String.valueOf(string.charAt(y));
                        System.out.println(s + "," + s1 + "," + s2 + "," + s3);
                        String x = s + "," + s1 + "," + s2 + "," + s3;
                        System.out.println(x);
                        ns.add(x);
                        Log.d("万千百十个文字", x);
                        String nns = "0,0,0,0,0";
                        StringBuilder sb = new StringBuilder(nns);
                        for (int i1 = 0; i1 < x.length(); i1 = i1 + 2) {
                            String sx = x.substring(i1, i1 + 1);

                            if (sx.equals("万")) {
                                sb.replace(0, 1, "1");
                            }
                            if (sx.equals("千")) {
                                sb.replace(2, 3, "1");
                            }
                            if (sx.equals("百")) {
                                sb.replace(4, 5, "1");
                            }
                            if (sx.equals("十")) {
                                sb.replace(6, 7, "1");
                            }
                            if (sx.equals("个")) {
                                sb.replace(8, 9, "1");
                            }
                        }
                        Log.d("万千百十个数字", sb.toString());
                        ns.add(sb.toString());
                        check4.add(ns);
                    }

                }
            }

        }
        return check4;
    }

    public List<List<String>> getCheck(List<String> ns) {

        List<List<String>> ListN = new ArrayList<>();
        for (int i = 0; i < ns.size(); i++) {
            for (int j = i + 1; j < ns.size(); j++) {
                List<String> n = new ArrayList<>();
                String s = ns.get(i) + "," + ns.get(j);
                Log.d("万千百十个", s);
                n.add(s);
                String[] sp = s.split(",");
                String[] str1 = new String[2];
                for (int i1 = 0; i1 < sp.length; i1++) {
                    String nns = "";
                    if (sp[i1].equals("万")) {
                        if (nns == "") {
                            nns = "1";
                        } else {
                            nns = nns + "," + "1";
                        }

                    } else {
                        if (nns == "") {
                            nns = "0";
                        } else {
                            nns = nns + "," + "0";
                        }
                    }
                    if (sp[i1].equals("千")) {
                        if (nns == "") {
                            nns = "1";
                        } else {
                            nns = nns + "," + "1";
                        }

                    } else {
                        if (nns == "") {
                            nns = "0";
                        } else {
                            nns = nns + "," + "0";
                        }
                    }
                    if (sp[i1].equals("百")) {
                        if (nns == "") {
                            nns = "1";
                        } else {
                            nns = nns + "," + "1";
                        }

                    } else {
                        if (nns == "") {
                            nns = "0";
                        } else {
                            nns = nns + "," + "0";
                        }
                    }
                    if (sp[i1].equals("十")) {
                        if (nns == "") {
                            nns = "1";
                        } else {
                            nns = nns + "," + "1";
                        }

                    } else {
                        if (nns == "") {
                            nns = "0";
                        } else {
                            nns = nns + "," + "0";
                        }
                    }
                    if (sp[i1].equals("个")) {
                        if (nns == "") {
                            nns = "1";
                        } else {
                            nns = nns + "," + "1";
                        }

                    } else {
                        if (nns == "") {
                            nns = "0";
                        } else {
                            nns = nns + "," + "0";
                        }
                    }
                    str1[i1] = nns;
                    Log.d("万千百十个Number", str1[i1]);

                }
                String s1 = "";
                String[] spp1 = str1[0].split(",");
                String[] spp2 = str1[1].split(",");
                for (int i1 = 0; i1 < spp1.length; i1++) {
                    if (spp1[i1].equals(spp2[i1])) {
                        if (s1 == "") {
                            s1 = "0";
                        } else {
                            s1 = s1 + "," + "0";
                        }
                    } else {
                        if (s1 == "") {
                            s1 = "1";
                        } else {
                            s1 = s1 + "," + "1";
                        }
                    }
                }
                Log.d("万千百十个Split", s1);
                n.add(s1);
                ListN.add(n);
            }
        }
        for (int i = 0; i < ListN.size(); i++) {
            List<String> list = ListN.get(i);
            String s = list.get(0);
            String s1 = list.get(1);
            Log.d("万千百十个List", s + s1);
        }
        return ListN;
    }

    public void setCheckLin(List<View> vs, List<String> str) {
        LinearLayout lin0 = (LinearLayout) vs.get(0);
        LinearLayout lin1 = (LinearLayout) vs.get(1);
        LinearLayout lin2 = (LinearLayout) vs.get(2);
        LinearLayout lin3 = (LinearLayout) vs.get(3);
        LinearLayout lin4 = (LinearLayout) vs.get(4);
        LinearLayout lin5 = (LinearLayout) vs.get(5);
        for (int i = 0; i < lin0.getChildCount(); i++) {
            CheckBox at = (CheckBox) lin0.getChildAt(i);
            at.setChecked(false);
            for (int i1 = 0; i1 < str.size(); i1++) {
                if (at.getText().toString().trim().equals(str.get(i1))) {
                    at.setChecked(true);
                }
            }
        }
        for (int i = 0; i < lin1.getChildCount(); i++) {
            CheckBox at = (CheckBox) lin1.getChildAt(i);
            at.setChecked(false);
            for (int i1 = 0; i1 < str.size(); i1++) {
                if (at.getText().toString().trim().equals(str.get(i1))) {
                    at.setChecked(true);
                }
            }
        }
        for (int i = 0; i < lin2.getChildCount(); i++) {
            CheckBox at = (CheckBox) lin2.getChildAt(i);
            at.setChecked(false);
            for (int i1 = 0; i1 < str.size(); i1++) {
                if (at.getText().toString().trim().equals(str.get(i1))) {
                    at.setChecked(true);
                }
            }
        }
        for (int i = 0; i < lin3.getChildCount(); i++) {
            CheckBox at = (CheckBox) lin3.getChildAt(i);
            at.setChecked(false);
            for (int i1 = 0; i1 < str.size(); i1++) {
                if (at.getText().toString().trim().equals(str.get(i1))) {
                    at.setChecked(true);
                }
            }
        }
        for (int i = 0; i < lin4.getChildCount(); i++) {
            CheckBox at = (CheckBox) lin4.getChildAt(i);
            at.setChecked(false);
            for (int i1 = 0; i1 < str.size(); i1++) {
                if (at.getText().toString().trim().equals(str.get(i1))) {
                    at.setChecked(true);
                }
            }
        }
        for (int i = 0; i < lin5.getChildCount(); i++) {
            CheckBox at = (CheckBox) lin5.getChildAt(i);
            at.setChecked(false);
            for (int i1 = 0; i1 < str.size(); i1++) {
                if (at.getText().toString().trim().equals(str.get(i1))) {
                    at.setChecked(true);
                }
            }
        }
    }

    public List<Object> getLinearC(LinearLayout view) {
        String str = "";
        int c = 0;
        for (int i = 1; i < view.getChildCount(); i++) {
            CheckBox at = (CheckBox) view.getChildAt(i);
            if (at.isChecked()) {
                c++;
                if (str == "") {
                    str = at.getText().toString().trim();
                } else {
                    str = str + "," + at.getText().toString().trim();
                }
            }
        }
        List<Object> lin = new ArrayList<>();
        lin.add(str);
        lin.add(c);
        return lin;
    }

    public int getNumHe2(List<Integer> ns) {
        int nss = 0;
        for (int i = 0; i < ns.size(); i++) {
            Integer n = ns.get(i);
            if (n == 0 || n == 18) {
                nss = nss + 1;
            }
            if (n == 1 || n == 17) {
                nss = nss + 2;
            }
            if (n == 2 || n == 16) {
                nss = nss + 3;
            }
            if (n == 3 || n == 15) {
                nss = nss + 4;
            }
            if (n == 4 || n == 14) {
                nss = nss + 5;
            }
            if (n == 5 || n == 13) {
                nss = nss + 6;
            }
            if (n == 6 || n == 12) {
                nss = nss + 7;
            }
            if (n == 7 || n == 11) {
                nss = nss + 8;
            }
            if (n == 8 || n == 10) {
                nss = nss + 9;
            }
            if (n == 9) {
                nss = nss + 10;
            }

        }
        return nss;
    }

    public int getNumHe2Zhi(int n) {

        if (n == 0 || n == 18) {
            return 1;
        }
        if (n == 1 || n == 17) {
            return 2;
        }
        if (n == 2 || n == 16) {
            return 3;
        }
        if (n == 3 || n == 15) {
            return 4;
        }
        if (n == 4 || n == 14) {
            return 5;
        }
        if (n == 5 || n == 13) {
            return 6;
        }
        if (n == 6 || n == 12) {
            return 7;
        }
        if (n == 7 || n == 11) {
            return 8;
        }
        if (n == 8 || n == 10) {
            return 9;
        }
        if (n == 9) {
            return 10;
        }


        return 0;
    }

    public int TwoHe1(int n) {
        int nss = 0;

        if (n == 0 || n == 18) {
            return 1;
        }
        if (n == 1 || n == 17) {
            return 2;
        }
        if (n == 2 || n == 16) {
            return 3;
        }
        if (n == 3 || n == 15) {
            return 4;
        }
        if (n == 4 || n == 14) {
            return 5;
        }
        if (n == 5 || n == 13) {
            return 6;
        }
        if (n == 6 || n == 12) {
            return 7;
        }
        if (n == 7 || n == 11) {
            nss = nss + 8;
        }
        if (n == 8 || n == 10) {
            nss = nss + 9;
        }
        if (n == 9) {
            nss = nss + 10;
        }


        return nss;
    }

    public int getNumHe22(List<Integer> ns) {
        int nss = 0;
        for (int i = 0; i < ns.size(); i++) {
            Integer n = ns.get(i);

            if (n == 1 || n == 17) {
                nss = nss + 1;
            }
            if (n == 2 || n == 16) {
                nss = nss + 1;
            }
            if (n == 3 || n == 15) {
                nss = nss + 2;
            }
            if (n == 4 || n == 14) {
                nss = nss + 2;
            }
            if (n == 5 || n == 13) {
                nss = nss + 3;
            }
            if (n == 6 || n == 12) {
                nss = nss + 3;
            }
            if (n == 7 || n == 11) {
                nss = nss + 4;
            }
            if (n == 8 || n == 10) {
                nss = nss + 4;
            }
            if (n == 9) {
                nss = nss + 5;
            }

        }
        return nss;
    }

    public int getNumHe22Fu(int n) {


        if (n == 1 || n == 17) {
            return 1;
        }
        if (n == 2 || n == 16) {
            return 1;
        }
        if (n == 3 || n == 15) {
            return 2;
        }
        if (n == 4 || n == 14) {
            return 2;
        }
        if (n == 5 || n == 13) {
            return 3;
        }
        if (n == 6 || n == 12) {
            return 3;
        }
        if (n == 7 || n == 11) {
            return 4;
        }
        if (n == 8 || n == 10) {
            return 4;
        }
        if (n == 9) {
            return 5;
        }

        return 0;
    }

    public int TwoZuHe(int n) {
        int nss = 0;


        if (n == 1 || n == 17) {
            return 1;
        }
        if (n == 2 || n == 16) {
            return 1;
        }
        if (n == 3 || n == 15) {
            return 2;
        }
        if (n == 4 || n == 14) {
            return 2;
        }
        if (n == 5 || n == 13) {
            return 3;
        }
        if (n == 6 || n == 12) {
            return 3;
        }
        if (n == 7 || n == 11) {
            return 4;
        }
        if (n == 8 || n == 10) {
            return 4;
        }
        if (n == 9) {
            return 5;
        }
        return nss;
    }

    public void PK10_deplex(int n, int m, int k, List<Integer> ns, List<Integer> ms, List<Integer> ks) {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        //冠军亚军重复个数
        for (int i = 0; i < ns.size(); i++) {
            Log.d("冠军_1", ns.get(i) + "");
            for (int i1 = 0; i1 < ms.size(); i1++) {
                Log.d("冠军_2", ms.get(i1) + "");
                if (ns.get(i) == ms.get(i1)) {
                    a++;
                }
            }
        }
        //冠军季军重复个数
        for (int i = 0; i < ns.size(); i++) {
            Log.d("冠军_1", ns.get(i) + "");
            for (int i1 = 0; i1 < ks.size(); i1++) {
                Log.d("冠军_3", ks.get(i1) + "");
                if (ns.get(i) == ks.get(i1)) {
                    b++;
                }
            }
        }

        //亚军季军重复个数
        for (int i = 0; i < ms.size(); i++) {
            Log.d("冠军_2", ms.get(i) + "");
            for (int i1 = 0; i1 < ks.size(); i1++) {
                Log.d("冠军_3", ks.get(i1) + "");
                if (ms.get(i) == ks.get(i1)) {
                    c++;
                }
            }
        }
        //冠军亚军季军重复个数
        for (int i = 0; i < ns.size(); i++) {
            Log.d("冠军_1", ns.get(i) + "");
            for (int i1 = 0; i1 < ms.size(); i1++) {
                Log.d("冠军_2", ms.get(i1) + "");
                if (ns.get(i) == ms.get(i1)) {
                    for (int i2 = 0; i2 < ks.size(); i2++) {
                        Log.d("冠军_3", ks.get(i2) + "");
                        if (ns.get(i) == ks.get(i2)) {
                            d++;
                        }
                    }
                }
            }
        }
        Log.d("冠亚季军重复以及个数", a + " " + b + " " + c + " " + d + " " + n + " " + m + " " + k + " ");
        setGameMoney(n * m * k - a * k - b * n - c * m + d * 2);
    }

    public void AnyOne(String code, int count) {
        if ("eleven_any_one_duplex".equals(code)) {
            if (count == 1) {
                setGameMoney(1);
                return;
            } else {
                setGameMoney(0);
            }
        }
        if ("eleven_any_two_duplex".equals(code)) {
            if (count == 2) {
                setGameMoney(1);
                return;
            } else {
                setGameMoney(0);
            }
        }
        if ("eleven_any_three_duplex".equals(code)) {
            if (count == 3) {
                setGameMoney(1);
                return;
            } else {
                setGameMoney(0);
            }
        }
        if ("eleven_any_four_duplex".equals(code)) {
            if (count == 4) {
                setGameMoney(1);
                return;
            } else {
                setGameMoney(0);
            }
        }
        if ("eleven_any_five_duplex".equals(code)) {
            if (count == 5) {
                setGameMoney(1);
                return;
            } else {
                setGameMoney(0);
            }
        }
        if ("eleven_any_six_duplex".equals(code)) {
            if (count == 6) {
                setGameMoney(1);
                return;
            } else {
                setGameMoney(0);
            }
        }
        if ("eleven_any_seven_duplex".equals(code)) {
            if (count == 7) {
                setGameMoney(1);
                return;
            }
        }
        if ("eleven_any_eight_duplex".equals(code)) {
            if (count == 8) {
                setGameMoney(1);
                return;
            } else {
                setGameMoney(0);
            }
        }
    }

    public int getNumKua2(List<Integer> ns) {
        int nss = 0;
        for (int i = 0; i < ns.size(); i++) {
            Integer n = ns.get(i);
            if (n == 0) {
                nss = nss + 10;
            }
            if (n == 1) {
                nss = nss + 18;
            }
            if (n == 2) {
                nss = nss + 16;
            }
            if (n == 3) {
                nss = nss + 14;
            }
            if (n == 4) {
                nss = nss + 12;
            }
            if (n == 5) {
                nss = nss + 10;
            }
            if (n == 6) {
                nss = nss + 8;
            }
            if (n == 7) {
                nss = nss + 6;
            }
            if (n == 8) {
                nss = nss + 4;
            }
            if (n == 9) {
                nss = nss + 2;
            }

        }
        return nss;
    }

    public int TwoKua(int n) {
        int nss = 0;

        if (n == 0) {
            nss = nss + 10;
        }
        if (n == 1) {
            nss = nss + 18;
        }
        if (n == 2) {
            return 16;
        }
        if (n == 3) {
            return 14;
        }
        if (n == 4) {
            return 12;
        }
        if (n == 5) {
            return 10;
        }
        if (n == 6) {
            return 8;
        }
        if (n == 7) {
            return 6;
        }
        if (n == 8) {
            return 4;
        }
        if (n == 9) {
            return 2;
        }
        return nss;
    }

    public String getheNums(List<String> str) {
        String strs = "";
        for (int i = 0; i < str.size(); i++) {
            if (i == 0) {
                strs = str.get(0);
            } else {
                strs = strs + "," + str.get(i);
            }
        }
        return strs;
    }

    public int getNum(List<Integer> listn) {
        int isint = 0;

        for (int i = 0; i < listn.size(); i++) {
            int n = listn.get(i);
            if (n == 0 || n == 27) {
                isint = isint + 1;
            }
            if (n == 1 || n == 26) {
                isint = isint + 3;
            }
            if (n == 2 || n == 25) {
                isint = isint + 6;
            }
            if (n == 3 || n == 24) {
                isint = isint + 10;
            }
            if (n == 4 || n == 23) {
                isint = isint + 15;
            }
            if (n == 5 || n == 22) {
                isint = isint + 21;
            }
            if (n == 6 || n == 21) {
                isint = isint + 28;
            }
            if (n == 7 || n == 20) {
                isint = isint + 36;
            }
            if (n == 8 || n == 19) {
                isint = isint + 45;
            }
            if (n == 9 || n == 18) {
                isint = isint + 55;
            }
            if (n == 10 || n == 17) {
                isint = isint + 63;
            }
            if (n == 11 || n == 16) {
                isint = isint + 69;
            }
            if (n == 12 || n == 15) {
                isint = isint + 73;
            }
            if (n == 13 || n == 14) {
                isint = isint + 75;
            }
        }
        return isint;
    }

    public int get3_5Num(String ns) {
        Log.d("排列35NS+ Int  NS", ns + "   " + Integer.parseInt(ns));
        int isint = 0;
        int n = Integer.parseInt(ns);
        if (n == 0 || n == 27) {
            return 1;
        }
        if (n == 1 || n == 26) {
            return 3;
        }
        if (n == 2 || n == 25) {
            return 6;
        }
        if (n == 3 || n == 24) {
            return 10;
        }
        if (n == 4 || n == 23) {
            return 15;
        }
        if (n == 5 || n == 22) {
            return 21;
        }
        if (n == 6 || n == 21) {
            return 28;
        }
        if (n == 7 || n == 20) {
            return 36;
        }
        if (n == 8 || n == 19) {
            return 45;
        }
        if (n == 9 || n == 18) {
            return 55;
        }
        if (n == 10 || n == 17) {
            return 63;
        }
        if (n == 11 || n == 16) {
            return 69;
        }
        if (n == 12 || n == 15) {
            return 73;
        }
        if (n == 13 || n == 14) {
            return 75;

        }
        return isint;
    }

    public int ThreeStarHe(int n) {
        int isint = 0;

        if (n == 0 || n == 27) {
            return 1;
        }
        if (n == 1 || n == 26) {
            return 3;
        }
        if (n == 2 || n == 25) {
            return 6;
        }
        if (n == 3 || n == 24) {
            return 10;
        }
        if (n == 4 || n == 23) {
            return 15;
        }
        if (n == 5 || n == 22) {
            return 21;
        }
        if (n == 6 || n == 21) {
            return 28;
        }
        if (n == 7 || n == 20) {
            return 36;
        }
        if (n == 8 || n == 19) {
            return 45;
        }
        if (n == 9 || n == 18) {
            return 55;
        }
        if (n == 10 || n == 17) {
            return 63;
        }
        if (n == 11 || n == 16) {
            return 69;
        }
        if (n == 12 || n == 15) {
            return 73;
        }
        if (n == 13 || n == 14) {
            return 75;
        }

        return isint;
    }

    public int getNumKua(List<Integer> listn) {
        int isint = 0;

        for (int i = 0; i < listn.size(); i++) {
            int n = listn.get(i);
            if (n == 0) {
                isint = isint + 10;
            }
            if (n == 1 || n == 9) {
                isint = isint + 54;
            }
            if (n == 2 || n == 8) {
                isint = isint + 96;
            }
            if (n == 3 || n == 7) {
                isint = isint + 126;
            }
            if (n == 4 || n == 6) {
                isint = isint + 144;
            }
            if (n == 5) {
                isint = isint + 150;
            }

        }
        return isint;
    }

    public int ThreeKua(int n) {
        int isint = 0;


        if (n == 0) {
            return 10;
        }
        if (n == 1 || n == 9) {
            return 54;
        }
        if (n == 2 || n == 8) {
            return 96;
        }
        if (n == 3 || n == 7) {
            return 126;
        }
        if (n == 4 || n == 6) {
            return 144;
        }
        if (n == 5) {
            return 150;
        }
        return isint;
    }

    public int getNumZuHe(List<Integer> listn) {
        int isint = 0;
        for (int i = 0; i < listn.size(); i++) {
            int n = listn.get(i);

            if (n == 1 || n == 26) {
                isint = isint + 1;
            }
            if (n == 2 || n == 25) {
                isint = isint + 2;
            }
            if (n == 3 || n == 24) {
                isint = isint + 2;
            }
            if (n == 4 || n == 23) {
                isint = isint + 4;
            }
            if (n == 5 || n == 22) {
                isint = isint + 5;
            }
            if (n == 6 || n == 21) {
                isint = isint + 6;
            }
            if (n == 7 || n == 20) {
                isint = isint + 8;
            }
            if (n == 8 || n == 19) {
                isint = isint + 10;
            }
            if (n == 9 || n == 18) {
                isint = isint + 11;
            }
            if (n == 10 || n == 17) {
                isint = isint + 13;
            }
            if (n == 11 || n == 16) {
                isint = isint + 14;
            }
            if (n == 12 || n == 15) {
                isint = isint + 14;
            }
            if (n == 13 || n == 14) {
                isint = isint + 15;
            }

        }
        return isint;
    }

    public int get3_5NumZuHe(String ns) {
        int isint = 0;
        int n = Integer.parseInt(ns);

        if (n == 1 || n == 26) {
            isint = isint + 1;
        }
        if (n == 2 || n == 25) {
            return 2;
        }
        if (n == 3 || n == 24) {
            return 2;
        }
        if (n == 4 || n == 23) {
            return 4;
        }
        if (n == 5 || n == 22) {
            return 5;
        }
        if (n == 6 || n == 21) {
            return 6;
        }
        if (n == 7 || n == 20) {
            return 8;
        }
        if (n == 8 || n == 19) {
            return 10;
        }
        if (n == 9 || n == 18) {
            isint = isint + 11;
        }
        if (n == 10 || n == 17) {
            return 13;
        }
        if (n == 11 || n == 16) {
            return 14;
        }
        if (n == 12 || n == 15) {
            return 14;
        }
        if (n == 13 || n == 14) {
            return 15;
        }


        return isint;
    }

    public int ThreeDifferent(List<String> ns) {
        int isint = 0;
        for (int i = 0; i < ns.size(); i++) {
            int n = Integer.parseInt(ns.get(i));
            if (n == 6 || n == 7 || n == 14 || n == 15) {
                isint += 1;
            }
            if (n == 8 || n == 13) {
                isint += 2;
            }
            if (n == 9 || n == 10 || n == 11 || n == 12) {
                isint += 3;
            }
        }
        return isint;
    }

    public int ThreeDifferentHe(String ns) {
        int isint = 0;
        int n = Integer.parseInt(ns);
        if (n == 6 || n == 7 || n == 14 || n == 15) {
            return 1;
        }
        if (n == 8 || n == 13) {
            return 2;
        }
        if (n == 9 || n == 10 || n == 11 || n == 12) {
            return 3;
        }

        return isint;
    }

    public int ThreeZuHe(int n) {
        int isint = 0;


        if (n == 1 || n == 26) {
            return 1;
        }
        if (n == 2 || n == 25) {
            return 2;
        }
        if (n == 3 || n == 24) {
            return 2;
        }
        if (n == 4 || n == 23) {
            return 4;
        }
        if (n == 5 || n == 22) {
            return 5;
        }
        if (n == 6 || n == 21) {
            return 6;
        }
        if (n == 7 || n == 20) {
            return 8;
        }
        if (n == 8 || n == 19) {
            isint = isint + 10;
        }
        if (n == 9 || n == 18) {
            return 11;
        }
        if (n == 10 || n == 17) {
            return 13;
        }
        if (n == 11 || n == 16) {
            return 14;
        }
        if (n == 12 || n == 15) {
            return 14;
        }
        if (n == 13 || n == 14) {
            return 15;
        }
        return isint;
    }

    @Override
    public void onItemClick(Object o, int position) {
        if (position != AlertView.CANCELPOSITION) {
            AddLottery();
        }
    }

    public void AddLottery() {
        Ids ids = new Ids();
        ids.setPickedNumber(pickedNumber);
        ids.setPickedText(pickedText);
        ids.setLocation(location);
        ids.setLocationText(locationText);
        ids.setNum(nums);
        ids.setClassCode(classCode);
        ids.setPriceUnit(PriceUnit);
        ids.setPriceType(priceType);
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
        // SendGameNum.setText(num + 1 + "");

    }

    public String get11_5(String n) {
        if ("5单0双".equals(n)) {
            return "odd_5_even_0";
        }
        if ("4单1双".equals(n)) {
            return "odd_4_even_1";
        }
        if ("3单2双".equals(n)) {
            return "odd_3_even_2";
        }
        if ("2单3双".equals(n)) {
            return "odd_2_even_3";
        }
        if ("1单4双".equals(n)) {
            return "odd_1_even_4";
        }
        if ("0单5双".equals(n)) {
            return "odd_0_even_5";
        }
        return "";
    }

    public String getBig(String n) {
        if ("大".equals(n)) {
            return "56789";
        }
        if ("小".equals(n)) {
            return "01234";
        }
        if ("单".equals(n)) {
            return "13579";
        }
        if ("双".equals(n)) {
            return "02468";
        }
        return "";
    }

    public String getBigLHC(String n) {
        if ("特大".equals(n) || "特和大".equals(n) || "特尾大".equals(n)) {
            return "max";
        }
        if ("特小".equals(n) || "特和小".equals(n) || "特尾小".equals(n)) {
            return "min";
        }
        if ("特单".equals(n) || "特和单".equals(n)) {
            return "odd";
        }
        if ("特双".equals(n) || "特和双".equals(n)) {
            return "even";
        }
        if ("红波".equals(n)) {
            return "red";
        }
        if ("绿波".equals(n)) {
            return "green";
        }
        if ("蓝波".equals(n)) {
            return "blue";
        }
        if ("红大".equals(n)) {
            return "redmax";
        }
        if ("红小".equals(n)) {
            return "redmin";
        }
        if ("绿大".equals(n)) {
            return "greenmax";
        }
        if ("绿小".equals(n)) {
            return "greenmin";
        }
        if ("蓝大".equals(n)) {
            return "bluemax";
        }
        if ("蓝小".equals(n)) {
            return "bluemin";
        }

        if ("红单".equals(n)) {
            return "redodd";
        }
        if ("红双".equals(n)) {
            return "redeven";
        }
        if ("绿单".equals(n)) {
            return "greenodd";
        }
        if ("绿双".equals(n)) {
            return "greeneven";
        }
        if ("蓝单".equals(n)) {
            return "blueodd";
        }
        if ("蓝双".equals(n)) {
            return "blueeven";
        }


        if ("红大单".equals(n)) {
            return "redmaxodd";
        }
        if ("红大双".equals(n)) {
            return "redmaxeven";
        }
        if ("红小单".equals(n)) {
            return "redminodd";
        }
        if ("红小双".equals(n)) {
            return "redmineven";
        }
        if ("绿大单".equals(n)) {
            return "greenmaxodd";
        }
        if ("绿大双".equals(n)) {
            return "greenmaxeven";
        }
        if ("绿小单".equals(n)) {
            return "greenminodd";
        }
        if ("绿小双".equals(n)) {
            return "greenmineven";
        }
        if ("蓝大单".equals(n)) {
            return "bluemaxodd";
        }
        if ("蓝大双".equals(n)) {
            return "bluemaxeven";
        }
        if ("蓝小单".equals(n)) {
            return "blueminodd";
        }
        if ("蓝小双".equals(n)) {
            return "bluemineven";
        }


        return "";
    }

    public String getTELHC(String n) {
        if ("特大单".equals(n)) {
            return "maxodd";
        }
        if ("特小单".equals(n)) {
            return "minodd";
        }
        if ("特大双".equals(n)) {
            return "maxeven";
        }
        if ("特小双".equals(n)) {
            return "mineven";
        }
        return "";
    }

    public String getSXLHC(String n) {
        if ("鼠".equals(n)) {
            return "rat";
        }
        if ("牛".equals(n)) {
            return "cow";
        }
        if ("虎".equals(n)) {
            return "tiger";
        }
        if ("兔".equals(n)) {
            return "rabbit";
        }
        if ("龙".equals(n)) {
            return "dragon";
        }
        if ("蛇".equals(n)) {
            return "snake";
        }
        if ("马".equals(n)) {
            return "horse";
        }
        if ("羊".equals(n)) {
            return "sheep";
        }
        if ("猴".equals(n)) {
            return "monkey";
        }
        if ("鸡".equals(n)) {
            return "chicken";
        }
        if ("狗".equals(n)) {
            return "dog";
        }
        if ("猪".equals(n)) {
            return "pig";
        }
        return "";
    }

    public String get5_Elements(String n) {
        if ("金".equals(n)) {
            return "golden";
        }
        if ("木".equals(n)) {
            return "wood";
        }
        if ("水".equals(n)) {
            return "water";
        }
        if ("火".equals(n)) {
            return "fire";
        }
        if ("土".equals(n)) {
            return "soil";
        }
        return "";
    }

    public String getBigCamp(String n) {
        if ("大".equals(n)) {
            return "06,07,08,09,10";
        }
        if ("小".equals(n)) {
            return "01,02,03,04,05";
        }
        if ("单".equals(n)) {
            return "01,03,05,07,09";
        }
        if ("双".equals(n)) {
            return "02,04,06,08,10";
        }
        return "";
    }

    public String getTongXuan(String n) {
        if ("k3_triple_all".equals(n)) {
            return "111,222,333,444,555,666";
        }
        if ("k3_consecutives_3_all".equals(n)) {
            return "123,234,345,456";
        }
        return "";
    }

    public String getBigCampHe(String n) {
        if ("大".equals(n)) {
            return "max";
        }
        if ("小".equals(n)) {
            return "min";
        }
        if ("单".equals(n) || "奇".equals(n)) {
            return "odd";
        }
        if ("双".equals(n) || "偶".equals(n)) {
            return "even";
        }
        if ("和".equals(n)) {
            return "eq";
        }
        if ("大单".equals(n)) {
            return "maxodd";
        }
        if ("大双".equals(n)) {
            return "maxeven";
        }
        if ("小单".equals(n)) {
            return "minodd";
        }
        if ("小双".equals(n)) {
            return "mineven";
        }
        if ("上".equals(n)) {
            return "up";
        }
        if ("中".equals(n)) {
            return "middle";
        }
        if ("下".equals(n)) {
            return "down";
        }
        return "";
    }

    public String getDragon(String n) {
        if ("龙".equals(n)) {
            return "dragon";
        }
        if ("虎".equals(n)) {
            return "tiger";
        }
        if ("和".equals(n)) {
            return "equal";
        }
        return "";
    }

    public String getBaccarat(String n) {
        if ("庄".equals(n) || "平".equals(n)) {
            return "0";
        }
        if ("闲".equals(n)) {
            return "1";
        }
        return "";
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