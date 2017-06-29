package com.example.king.gou.ui.settingfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.king.gou.R;
import com.example.king.gou.bean.MapsIdAndValue;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SaveCardActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.Top)
    RelativeLayout Top;
    @BindView(R.id.SpinnnerBank)
    Spinner SpinnnerBank;
    @BindView(R.id.SpinnerPrivince)
    Spinner SpinnerPrivince;
    @BindView(R.id.SpinnerCity)
    Spinner SpinnerCity;
    @BindView(R.id.EditTextZhiHang)
    EditText EditTextZhiHang;
    @BindView(R.id.EditTextUserName)
    EditText EditTextUserName;
    @BindView(R.id.EditTextCardNum)
    EditText EditTextCardNum;
    @BindView(R.id.BindCardBtn)
    Button BindCardBtn;
    List<List<MapsIdAndValue>> maps;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_card);
        ButterKnife.bind(this);
        RetrofitService.getInstance().getCardDatas(this);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        BindCardBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.BindCardBtn:

        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_CARDDATAS) {
            if (object != null) {
                maps = (List<List<MapsIdAndValue>>) object;
                List<MapsIdAndValue> mapsIdAndValues = maps.get(0);
                List<String> banks = new ArrayList<>();
                for (int i = 0; i < mapsIdAndValues.size(); i++) {
                    banks.add(mapsIdAndValues.get(i).getValues());
                }
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, banks);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                SpinnnerBank.setAdapter(adapter);
                List<MapsIdAndValue> mapsIdAndValues1 = maps.get(1);
                String values = mapsIdAndValues.get(3).getValues();
                String values1 = mapsIdAndValues1.get(5).getValues();
                Log.d("获取银行卡所需的数据返回2", values + "    " + values1);
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
