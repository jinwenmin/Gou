package com.example.king.gou.ui.settingfragment;

import android.content.Intent;
import android.os.Bundle;
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
    List<List<MapsIdAndValue>> MapsBank;
    List<List<CardsData>> cs = new ArrayList<>();
    private AlertView alertViewSafe;
    // 一个自定义的布局，作为显示的内容
    View contentViewSafe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_man);
        ButterKnife.bind(this); MyApp.getInstance().addActivitys(this);
        alertViewSafe = new AlertView(null, null, "取消", new String[]{"确认"}, null,this, AlertView.Style.Alert, this);
        contentViewSafe = LayoutInflater.from(this).inflate(
                R.layout.item_safepwd, null);
        alertViewSafe.addExtView(contentViewSafe);
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
                RetrofitService.getInstance().getBingCardLock(this);
                break;
            case R.id.BindCardBtn:
                alertViewSafe.show();
               // startActivity(new Intent(BankCardManActivity.this, SaveCardActivity.class));
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
                    // BindCardBtn.setVisibility(View.GONE);

                } else {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
                }
            }
        }
        if (apiId == RetrofitService.API_ID_CARDDATAS) {
            if (object != null) {
               /* MapsBank = (List<List<MapsIdAndValue>>) object;
                List<MapsIdAndValue> mapsBank = MapsBank.get(2);
                String locked = mapsBank.get(1).getLocked();
                if ("true".equals(locked)) {
                    //BindCardBtn.setVisibility(View.GONE);
                } else {
                    BindCardBtn.setVisibility(View.VISIBLE);
                }*/
                cs = (List<List<CardsData>>) object;
                List<CardsData> cardsDatas = cs.get(0);
                cardAdapter.addCards(cardsDatas);
            }
        }if (apiId == RetrofitService.API_ID_SAFEPWD) {
            if (object != null) {
                RestultInfo    restultInfo = (RestultInfo) object;
                if (restultInfo.isRc() == true) {
                    startActivity(new Intent(BankCardManActivity.this, SaveCardActivity.class));
                }if (restultInfo.isRc() == false) {
                    Toasty.error(this,restultInfo.getMsg(),2000).show();
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
    public void onItemClick(Object o, int position) {
        if (position != AlertView.CANCELPOSITION) {
            EditText safepwd = (EditText) contentViewSafe.findViewById(R.id.AnswerQues);
            String pwd = safepwd.getText().toString().trim();
            if ("".equals(pwd)) {
                Toasty.error(this,"安全密码不可为空",2000).show();
                return;
            }
            String hmacsha256 = RxUtils.getInstance().HMACSHA256(pwd, MyApp.getInstance().getUserName());
            RetrofitService.getInstance().getCheckSafePwd(this, hmacsha256);
        }

    }
}
