package com.example.king.gou.ui.gameAcVpFrms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.GameCertAdapter;
import com.example.king.gou.adapters.MakeZhuiHaoAdapter;
import com.example.king.gou.bean.Ids;
import com.example.king.gou.bean.ZhuiHaoCNum;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.CNumberActivity;
import com.example.king.gou.ui.GameCenterActivity;
import com.example.king.gou.utils.HttpEngine;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLayoutActivity;

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
    @BindView(R.id.ZhuiHao)
    TextView ZhuiHao;
    @BindView(R.id.GameCertTop)
    RelativeLayout GameCertTop;
    @BindView(R.id.ToBettingAuto)
    TextView ToBettingAuto;
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
        ZhuiHaoCheck = ((CheckBox) contentView.findViewById(R.id.ZhuiHaoCheck));
        listZhuiH.setAdapter(Zhadapter);
        ZhuiHaoMake.setOnClickListener(this);
        alertView.addExtView(contentView);

        Intent intent = getIntent();
        listids = (ArrayList<Ids>) intent.getSerializableExtra("listids");
        gid = intent.getIntExtra("gid", 0);
        initClick();
        period = intent.getStringExtra("period");
        for (int i = 0; i < listids.size(); i++) {
            Log.d("购彩单的数据=", listids.get(i).toString());
            Amounts = Amounts + listids.get(i).getAmounts();
        }
        adapter = new GameCertAdapter(this, listids);
        GameCartList.setAdapter(adapter);

    }

    private void initClick() {
        Back.setOnClickListener(this);
        ToSendGame.setOnClickListener(this);
        ZhuiHao.setOnClickListener(this);
        ToBettingAuto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
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
            case R.id.ZhuiHao:
                Intent intent = new Intent(GameCartActivity.this, CNumberActivity.class);
                intent.putExtra("period", period);
                intent.putExtra("gid", gid);
                startActivity(intent);
                break;
            case R.id.ToBettingAuto:
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
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_BEETING_AUTO) {
            if (object != null) {
                zhCNum = (List<ZhuiHaoCNum>) object;
                adapterData = new ArrayList<>();
                for (int i = 0; i < zhCNum.size(); i++) {
                    Log.d("追号详情", zhCNum.get(i).toString());
                    ZhuiHaoCNum zh = new ZhuiHaoCNum();
                    zh.setPeriod(zhCNum.get(i).getPeriod());
                    zh.setBei(Integer.parseInt(bei));
                    zh.setAmounts(Amounts);
                    adapterData.add(zh);
                }
                Zhadapter.addList(adapterData);
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
            List<Map<String, Object>> ars = new ArrayList<>();
            for (int i = 0; i < zhCNum.size(); i++) {
                Map<String, Object> map = new HashMap();
                LinearLayout v = (LinearLayout) listZhuiH.getChildAt(i);
                if (((CheckBox) v.getChildAt(0)).isChecked()) {

                    map.put("period", ((TextView) v.getChildAt(1)).getText().toString());
                    map.put("multiple", Integer.parseInt(((EditText) v.getChildAt(2)).getText().toString()));
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
}
