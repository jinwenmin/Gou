package com.example.king.gou.ui.settingfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.CardAdapter;
import com.example.king.gou.bean.CardsData;
import com.example.king.gou.bean.MapsIdAndValue;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.ui.CheckBankCardActivity;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.example.king.gou.utils.SlideCutListView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class BankCardManActivity extends AutoLayoutActivity implements SlideCutListView.RemoveListener, View.OnClickListener, HttpEngine.DataListener, OnItemClickListener {

    @BindView(R.id.nocard_list)
    SlideCutListView nocardList;
    @BindView(R.id._back)
    ImageView Back;
    CardAdapter cardAdapter;
    @BindView(R.id.CardLock)
    TextView CardLock;
    @BindView(R.id.Top)
    RelativeLayout Top;
    @BindView(R.id.BindCardBtn)
    Button BindCardBtn;
    List<List<CardsData>> cs = new ArrayList<>();
    private AlertView alertViewSafe;
    // 一个自定义的布局，作为显示的内容
    View contentViewSafe;

    private AlertView alertViewLock;
    // 一个自定义的布局，作为显示的内容
    View contentViewLock;
    EditText safepwd;
    List<CardsData> cardsDatas;
    String show = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_man);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);

        alertViewSafe = new AlertView(null, null, "取消", new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentViewSafe = LayoutInflater.from(this).inflate(
                R.layout.item_safepwd, null);
        alertViewSafe.addExtView(contentViewSafe);
        safepwd = (EditText) contentViewSafe.findViewById(R.id.AnswerQues);

        alertViewLock = new AlertView(null, null, "取消", new String[]{"确认"}, null, this, AlertView.Style.Alert, this);
        contentViewLock = LayoutInflater.from(this).inflate(
                R.layout.item_bankcardlock, null);
        alertViewLock.addExtView(contentViewLock);


        RetrofitService.getInstance().getCardDatas(this);
        cardAdapter = new CardAdapter(this);
        nocardList.setAdapter(cardAdapter);
        initClick();
    }


    private void initClick() {
        nocardList.setRemoveListener(this);
        CardLock.setOnClickListener(this);
        BindCardBtn.setOnClickListener(this);
        Back.setOnClickListener(this);
    }

    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CardLock:
                alertViewLock.show();
                show = "1";
                break;
            case R.id.BindCardBtn:
                alertViewSafe.show();
                show = "2";

                break;
            case R.id._back:
                finish();
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_CARDLOCK) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc() == true) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                    BindCardBtn.setVisibility(View.GONE);
                    CardLock.setVisibility(View.GONE);
                } else {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                }
            }
        }
        if (apiId == RetrofitService.API_ID_CARDDATAS) {
            if (object != null) {

                cs = (List<List<CardsData>>) object;
                cardsDatas = cs.get(0);
                List<CardsData> locked = cs.get(3);
                for (int i = 0; i < locked.size(); i++) {
                    if (locked.get(i).isLocked()) {
                        BindCardBtn.setVisibility(View.GONE);
                        CardLock.setVisibility(View.GONE);
                    } else {
                        BindCardBtn.setVisibility(View.VISIBLE);
                        CardLock.setVisibility(View.VISIBLE);
                    }
                }
                cardAdapter.addCards(cardsDatas);
            }
        }
        if (apiId == RetrofitService.API_ID_SAFEPWD) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    safepwd.setText("");
                    if (cardsDatas.size() == 0) {
                        startActivity(new Intent(BankCardManActivity.this, SaveCardActivity.class));
                    } else {
                        startActivity(new Intent(BankCardManActivity.this, CheckBankCardActivity.class));
                    }
                }
                if (!restultInfo.isRc()) {
                    safepwd.setText("");
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                    return;
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
    protected void onRestart() {
        super.onRestart();
        RetrofitService.getInstance().getCardDatas(this);
    }

    @Override
    public void onItemClick(Object o, int position) {
        if (show.equals("2")) {
            if (position != AlertView.CANCELPOSITION) {

                String pwd = safepwd.getText().toString().trim();
                if ("".equals(pwd)) {
                    Toasty.error(this, "安全密码不可为空", 2000).show();
                    return;
                }
                String hmacsha256 = RxUtils.getInstance().HMACSHA256(pwd, MyApp.getInstance().getUserName());
                RetrofitService.getInstance().getCheckSafePwd(this, hmacsha256);
            }
        }
        if (show.equals("1")) {
            if (position!= AlertView.CANCELPOSITION) {
                RetrofitService.getInstance().getBingCardLock(this);
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cs = null;
    }
}
