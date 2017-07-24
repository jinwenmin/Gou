package com.example.king.gou.ui.frmMyActivity;

import android.os.Bundle;
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
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    List<CardsData> cardsDatas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);
        ButterKnife.bind(this);
        RetrofitService.getInstance().getCardDatas(this);
initClick();

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
        if (apiId == RetrofitService.API_ID_CARDDATAS) {
            if (object != null) {

                cs = (List<List<CardsData>>) object;
                cardsDatas= cs.get(0);
                for (int i = 0; i < cardsDatas.size(); i++) {
                    String account_number = cardsDatas.get(i).getAccount_number();
                    String start = account_number.substring(0, 4);
                    String end = account_number.substring(account_number.length() - 4, account_number.length());
                    String num = start + "***********" + end;
                    cardData.add(num);
                }


                adapterCard = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cardData);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapterCard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerWithDarw.setAdapter(adapterCard);
                initSpinnerSelect();
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
                RetrofitService.getInstance().getWithDrawCreates(this,cardsDatas.get(SpinnerWithDarw.getSelectedItemPosition()).getCardid(),money);
        }
    }
}
