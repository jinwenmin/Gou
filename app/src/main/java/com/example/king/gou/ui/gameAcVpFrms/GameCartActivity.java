package com.example.king.gou.ui.gameAcVpFrms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.GameCertAdapter;
import com.example.king.gou.adapters.MakeZhuiHaoAdapter;
import com.example.king.gou.bean.Ids;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.ZhuiHaoCNum;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class GameCartActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener, OnItemClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.GameCartList)
    ListView GameCartList;
    List<Ids> listids = new ArrayList<>();
    int gid = 0;
    String period = null;
    GameCertAdapter adapter;
    @BindView(R.id.ToSendGame)
    TextView ToSendGame;

    @BindView(R.id.GameCertTop)
    RelativeLayout GameCertTop;
    @BindView(R.id.ToBettingAuto)
    TextView ToBettingAuto;
    @BindView(R.id.AmountSum)
    TextView AmountSum;
    @BindView(R.id.ClearGameCart)
    TextView ClearGameCart;
    private AlertView alertView;
    // 一个自定义的布局，作为显示的内容
    View contentView;
    private Button ZhuiHaoMake;
    private EditText editBeiNum;
    private EditText editQiNum;
    double Amounts = 0;
    List<ZhuiHaoCNum> zhCNum;
    List<ZhuiHaoCNum> adapterData;
    String bei;
    MakeZhuiHaoAdapter Zhadapter;
    private ListView listZhuiH;
    private CheckBox ZhuiHaoCheck;
    private CheckBox ZhuiHaoListCheck;
    private Spinner SelectPeriodsSpinner;
    private Broadcast broad;
    ArrayAdapter selectPeriodAdapter;
    LinearLayout ZhuiHaoLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_cart);
        Zhadapter = new MakeZhuiHaoAdapter(this);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        alertView = new AlertView(null, null, "取消", new String[]{"确认追号"}, null, this, AlertView.Style.Alert, this);
        contentView = LayoutInflater.from(this).inflate(
                R.layout.item_zhuihao_check, null);
        ZhuiHaoMake = ((Button) contentView.findViewById(R.id.ZhuiHaoMake));
        editBeiNum = ((EditText) contentView.findViewById(R.id.EditBeiNum));
        editQiNum = ((EditText) contentView.findViewById(R.id.EditQiNum));
        listZhuiH = ((ListView) contentView.findViewById(R.id.ZhuiHaoList));
        ZhuiHaoCheck = ((CheckBox) contentView.findViewById(R.id.ZhuiHaoStop));
        ZhuiHaoLinear = (LinearLayout) contentView.findViewById(R.id.ZhuiHaoLinear);
        ZhuiHaoListCheck = ((CheckBox) contentView.findViewById(R.id.ZhuiHaoListCheck));
        SelectPeriodsSpinner = ((Spinner) contentView.findViewById(R.id.SelectPeriodsSpinner));

        listZhuiH.setAdapter(Zhadapter);
        ZhuiHaoMake.setOnClickListener(this);
        alertView.addExtView(contentView);
        Intent intent = getIntent();
        listids = (ArrayList<Ids>) intent.getSerializableExtra("listids");
        gid = intent.getIntExtra("gid", 0);
        initSpinner();
        initClick();
        period = intent.getStringExtra("period");
        for (int i = 0; i < listids.size(); i++) {
            Log.d("购彩单的数据=", listids.get(i).toString());
            Amounts = Amounts + listids.get(i).getAmounts();
        }
        AmountSum.setText(RxUtils.getInstance().getDouble2(Amounts) + "");
        adapter = new GameCertAdapter(this);
        GameCartList.setAdapter(adapter);
        adapter.addList(listids);
        broad = new Broadcast();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DeleteAmounts");
        this.registerReceiver(broad, intentFilter);
    }

    private void initSpinner() {
        final List<String> periods = new ArrayList<>();
        periods.add("0");
        periods.add("5");
        periods.add("10");
        periods.add("15");
        periods.add("20");
        periods.add("25");
        selectPeriodAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, periods);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        selectPeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        SelectPeriodsSpinner.setAdapter(selectPeriodAdapter);
        SelectPeriodsSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editQiNum.setText(periods.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initClick() {
        Back.setOnClickListener(this);
        ToSendGame.setOnClickListener(this);
        ToBettingAuto.setOnClickListener(this);
        ClearGameCart.setOnClickListener(this);
        listZhuiH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("点击了" + i + "个");
            }
        });
        ZhuiHaoListCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean flag = false;
                if (b) {
                    flag = true;
                } else {
                    flag = false;
                }
                for (int i = 0; i < ZhuiHaoLinear.getChildCount(); i++) {
                    LinearLayout linear1 = (LinearLayout) ZhuiHaoLinear.getChildAt(i);
                    ((CheckBox) linear1.getChildAt(0)).setChecked(flag);
                }
            }
        });
    }

    private class Broadcast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("DeleteAmounts")) {
                double amounts = intent.getDoubleExtra("amounts", 0);
                int position = intent.getIntExtra("position", 0);
                String trim = AmountSum.getText().toString().trim();
                double as = Double.parseDouble(trim);
                if (as - amounts < 0) {
                    AmountSum.setText("0.0");
                } else {
                    AmountSum.setText(as - amounts + "");
                }
                listids.remove(position);
                adapter.addList(listids);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                Intent intent1 = new Intent();
                intent1.putExtra("ids", (Serializable) listids);
                setResult(1, intent1);
                finish();
                break;
            case R.id.ToSendGame:
                List<Map<String, Object>> ids = new ArrayList<>();
                double Amounts = 0;
                for (int i = 0; i < listids.size(); i++) {
                    Amounts = Amounts + listids.get(i).getAmount();
                    Map<String, Object> map = new HashMap();
                    map.put("pickedNumber", listids.get(i).getPickedNumber());
                    map.put("multiples", listids.get(i).getMultiple());
                    map.put("locationText", listids.get(i).getLocationText());
                    map.put("priceUnit", listids.get(i).getPriceUnit());
                    map.put("amount", listids.get(i).getAmount());
                    map.put("priceType", listids.get(i).getPriceType());
                    map.put("amounts", listids.get(i).getAmount());
                    map.put("pickedText", listids.get(i).getPickedText());
                    map.put("multiple", listids.get(i).getMultiple());
                    map.put("classCode", listids.get(i).getClassCode());
                    map.put("location", listids.get(i).getLocation());
                    map.put("num", listids.get(i).getNum());
                    map.put("vcode", "");
                    ids.add(map);
                }
                Gson gson = new Gson();
                String idsString = gson.toJson(ids);
                RetrofitService.getInstance().getSendBetting(this, gid, "", idsString, period, "", Amounts, 1);
                break;
          /*  case R.id.ZhuiHao:
                Intent intent = new Intent(GameCartActivity.this, CNumberActivity.class);
                intent.putExtra("period", period);
                intent.putExtra("gid", gid);
                startActivity(intent);
                break;*/
            case R.id.ToBettingAuto:
                if (gid == 9 || gid == 10 || gid == 28) {
                    Toasty.info(GameCartActivity.this, "该游戏没有追号功能", 2000).show();
                    return;
                }
                alertView.show();
                break;
            case R.id.ZhuiHaoMake:
                String qi = editQiNum.getText().toString().trim();
                if (qi == "" || "".equals(qi)) {
                    Toasty.error(GameCartActivity.this, "期数不可为空", 2000).show();
                    return;
                }
                bei = editBeiNum.getText().toString().trim();
                if (bei == "" || "".equals(bei)) {
                    Toasty.error(GameCartActivity.this, "倍数不可为空", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getBettingAutoPurchase(GameCartActivity.this, gid, period, Integer.parseInt(qi));
                break;
            case R.id.ClearGameCart:
                listids = new ArrayList<>();
                adapter.addList(listids);
                AmountSum.setText("0.0");
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_BEETING_AUTO) {
            if (object != null) {
                zhCNum = (List<ZhuiHaoCNum>) object;
                adapterData = new ArrayList<>();
                for (int i = 0; i < zhCNum.size(); i++) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_zhuihao_item, null);
                    TextView ZhuiHaoAmount = (TextView) view.findViewById(R.id.ZhuiHaoAmount);
                    EditText ZhuihaoBei = (EditText) view.findViewById(R.id.ZhuiHaoBei1);
                    TextView ZhuiHaoPeriod = (TextView) view.findViewById(R.id.ZhuiHaoPeriod);
                    ZhuiHaoAmount.setText(Amounts + "");
                    ZhuihaoBei.setText(Integer.parseInt(bei) + "");
                    ZhuiHaoPeriod.setText(zhCNum.get(i).getPeriod());
                   /* Log.d("追号详情", zhCNum.get(i).toString());
                    ZhuiHaoCNum zh = new ZhuiHaoCNum();
                    zh.setPeriod(zhCNum.get(i).getPeriod());
                    zh.setBei(Integer.parseInt(bei));
                    zh.setAmounts(Amounts);
                    adapterData.add(zh);*/
                    ZhuiHaoLinear.addView(view);
                }
                //   Zhadapter.addList(adapterData);
            }
        }
        if (apiId == RetrofitService.API_ID_SENDBETTING) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    listids = new ArrayList<>();
                    adapter.addList(listids);
                    AmountSum.setText("0.0");
                    Toasty.success(GameCartActivity.this, restultInfo.getMsg(), 2000).show();
                    Intent intent1 = new Intent();
                    intent1.putExtra("ids", (Serializable) listids);
                    setResult(1, intent1);
                    finish();
                } else {
                    if (restultInfo.getMsg().contains("<")) {
                        Toasty.error(GameCartActivity.this, "投注失败", 2000).show();
                    } else {
                        Toasty.error(GameCartActivity.this, restultInfo.getMsg()+"", 2000).show();
                    }
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
    public void onBackPressed() {
        Intent intent1 = new Intent();
        intent1.putExtra("ids", (Serializable) listids);
        setResult(1, intent1);
        super.onBackPressed();
    }

    @Override
    public void onItemClick(Object o, int position) {
        if (position != AlertView.CANCELPOSITION) {
            List<Map<String, Object>> ids = new ArrayList<>();
            double Amounts = 0;
            for (int i = 0; i < listids.size(); i++) {
                Amounts = Amounts + listids.get(i).getAmount();
                Map<String, Object> map = new HashMap();
                map.put("pickedNumber", listids.get(i).getPickedNumber());
                map.put("multiples", listids.get(i).getMultiple());
                map.put("locationText", listids.get(i).getLocationText());
                map.put("priceUnit", listids.get(i).getPriceUnit());
                map.put("amount", listids.get(i).getAmount());
                map.put("priceType", listids.get(i).getPriceType());
                map.put("amounts", listids.get(i).getAmount());
                map.put("pickedText", listids.get(i).getPickedText());
                map.put("multiple", listids.get(i).getMultiple());
                map.put("classCode", listids.get(i).getClassCode());
                map.put("location", listids.get(i).getLocation());
                map.put("num", listids.get(i).getNum());
                map.put("vcode", "");
                ids.add(map);
            }

            if (zhCNum == null) {
                Toasty.info(GameCartActivity.this, "没有追号列表", 2000).show();
                return;
            }

            List<Map<String, Object>> ars = new ArrayList<>();
            for (int i1 = 0; i1 < ZhuiHaoLinear.getChildCount(); i1++) {
                Map<String, Object> map = new HashMap();
                LinearLayout linear1 = (LinearLayout) ZhuiHaoLinear.getChildAt(i1);
                Log.d("追号列表", ((CheckBox) linear1.getChildAt(0)).isChecked() + "   "
                        + ((TextView) linear1.getChildAt(1)).getText().toString() + "   " + Integer.parseInt(((EditText) linear1.getChildAt(2)).getText().toString()));
                if (((CheckBox) linear1.getChildAt(0)).isChecked()) {
                    map.put("period", ((TextView) linear1.getChildAt(1)).getText().toString());
                    map.put("multiple", Integer.parseInt(((EditText) linear1.getChildAt(2)).getText().toString()));
                    ars.add(map);
                }
            }
            if (ars.size() == 0) {
                Toasty.error(GameCartActivity.this, "没有选择任何期数", 2000).show();
                return;
            }
            Gson gson = new Gson();
            String idsString = gson.toJson(ids);
            String arstring = gson.toJson(ars);
            int stop = 0;
            if (ZhuiHaoCheck.isChecked()) {
                stop = 1;
            }
            RetrofitService.getInstance().getSendBetting(this, gid, "", idsString, period, arstring, Amounts, stop);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broad);
    }
}
