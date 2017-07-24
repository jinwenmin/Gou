package com.example.king.gou.ui.frmMyActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.king.gou.R;
import com.example.king.gou.bean.CardsData;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.bean.WithDraw;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class WithDrawActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.WithDrawTop)
    RelativeLayout WithDrawTop;
    @BindView(R.id.SpinnerWithDarw)
    Spinner SpinnerWithDarw;
    @BindView(R.id.WithDrawAmount)
    EditText WithDrawAmount;
    @BindView(R.id.TiJiao)
    Button TiJiao;
    List<List<CardsData>> cs = new ArrayList<>();
    private ArrayAdapter<String> adapterCard;
    List<String> cardData = new ArrayList<>();
    List<CardsData> cardsDatas = new ArrayList<>();

    String amounts;
    List<WithDraw> wd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);
        ButterKnife.bind(this);
        amounts = getIntent().getStringExtra("amounts");
        wd = (List<WithDraw>) getIntent().getSerializableExtra("banks");
        WithDrawAmount.setHint("最大取款金额为:" + amounts);
        RetrofitService.getInstance().getCardDatas(this);
        initClick();
        initSpinner();
    }

    private void initSpinner() {
        List<String> UserBank = new ArrayList<>();
        for (int i = 0; i < wd.size(); i++) {
            String cardinfo = wd.get(i).getCardNumber();
            cardinfo = cardinfo.substring(cardinfo.indexOf("银行卡信息: ") + 7, cardinfo.length());
            UserBank.add(cardinfo);
        }
        adapterCard = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, UserBank);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapterCard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerWithDarw.setAdapter(adapterCard);
        initSpinnerSelect();
    }

    private void initClick() {
        TiJiao.setOnClickListener(this);
        Back.setOnClickListener(this);
    }

    private void initSpinnerSelect() {
        SpinnerWithDarw.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_WITHDRAWCREATE) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                Log.d("WithDarwMsg", restultInfo.getMsg()+"");
                if (restultInfo.isRc()) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                    finish();
                }
                if (!restultInfo.isRc()) {
                    Toasty.error(this, restultInfo.getMsg(), 2000).show();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TiJiao:
                String m = WithDrawAmount.getText().toString().trim();
                BigDecimal money = BigDecimal.valueOf(Double.parseDouble(m));
                if (Double.parseDouble(money + "") < 0 || Double.parseDouble(money + "") > Double.parseDouble(amounts)) {
                    Toasty.error(this, "提现金额不符合取款范围", 2000).show();
                    return;
                }
                RetrofitService.getInstance().getWithDrawCreates(this, wd.get(SpinnerWithDarw.getSelectedItemPosition()).getAid(), money);
        }
    }
}
