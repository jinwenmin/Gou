package com.example.king.gou.ui.proxyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.adapters.GamePrizeAdapter;
import com.example.king.gou.bean.GamePrize;
import com.example.king.gou.bean.GameType;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GameReWardActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id.GameReWard_back)
    ImageView GameReWardBack;
    @BindView(R.id.GameReWardTop)
    RelativeLayout GameReWardTop;
    @BindView(R.id.SpinnerGame)
    Spinner SpinnerGame;
    @BindView(R.id.SpinnerGid)
    Spinner SpinnerGid;
    List<GameType> gameTypes1 = new ArrayList<>();
    List<GameType> gameTypes2 = new ArrayList<>();
    @BindView(R.id.GamePrizeListView)
    ListView GamePrizeListView;
    @BindView(R.id.Record)
    TextView Record;
    private ArrayAdapter<String> adapter;
    List<GamePrize> gp = new ArrayList<>();
    GamePrizeAdapter gamePrizeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_re_ward);
        ButterKnife.bind(this);
        gamePrizeAdapter = new GamePrizeAdapter(this);
        GamePrizeListView.setAdapter(gamePrizeAdapter);
        initCLick();
        RetrofitService.getInstance().getGame(this, 4, 0, 0, 0);
    }

    private void initCLick() {
        GameReWardBack.setOnClickListener(this);
        Record.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.GameReWard_back:
                finish();
                break;
            case R.id.Record:
                startActivity(new Intent(GameReWardActivity.this,RecordListActivity.class));

                break;
        }
    }

    private void initSpinner() {
        SpinnerGame.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().getGame(GameReWardActivity.this, 7, gameTypes1.get(i).getGid(), 0, 0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SpinnerGid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitService.getInstance().GetPrizeDetails(GameReWardActivity.this, 100, 1, "grid", "asc", gameTypes1.get(SpinnerGame.getSelectedItemPosition()).getGid(), gameTypes2.get(SpinnerGid.getSelectedItemPosition()).getGrid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_GAME4) {
            if (object != null) {
                gameTypes1 = (List<GameType>) object;
                List<String> types = new ArrayList<>();
                for (int i = 0; i < gameTypes1.size(); i++) {
                    types.add(gameTypes1.get(i).getName());
                }
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                SpinnerGame.setAdapter(adapter);
                RetrofitService.getInstance().getGame(GameReWardActivity.this, 7, gameTypes1.get(0).getGid(), 0, 0);
            }
        }
        if (apiId == RetrofitService.API_ID_GAME7) {
            if (object != null) {
                gameTypes2 = (List<GameType>) object;
                List<String> types = new ArrayList<>();
                for (int i = 0; i < gameTypes2.size(); i++) {
                    types.add(gameTypes2.get(i).getName());
                    Log.d("gameTypes2", gameTypes2.get(i).getName());
                }
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //第四步：将适配器添加到下拉列表上
                SpinnerGid.setAdapter(adapter);
                initSpinner();
            }
        }
        if (apiId == RetrofitService.API_ID_GAMEPRIZE) {
            if (object != null) {
                gp = (List<GamePrize>) object;
                gamePrizeAdapter.addList(gp);
            }
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
