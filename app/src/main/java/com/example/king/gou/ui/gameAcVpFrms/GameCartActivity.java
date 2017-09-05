package com.example.king.gou.ui.gameAcVpFrms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.adapters.GameCertAdapter;
import com.example.king.gou.bean.Ids;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GameCartActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_cart);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        listids = (ArrayList<Ids>) intent.getSerializableExtra("listids");
        gid = intent.getIntExtra("gid", 0);
        initClick();
        period = intent.getStringExtra("period");
        for (int i = 0; i < listids.size(); i++) {
            Log.d("购彩单的数据=", listids.get(i).toString());
        }
        adapter = new GameCertAdapter(this, listids);
        GameCartList.setAdapter(adapter);

    }

    private void initClick() {
        Back.setOnClickListener(this);
        ToSendGame.setOnClickListener(this);
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
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {

    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
