package com.example.king.gou.ui.settingfragment;

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

import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.bean.CardsData;
import com.example.king.gou.bean.MapsIdAndValue;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class SaveCardActivity extends AutoLayoutActivity implements View.OnClickListener, HttpEngine.DataListener {

    @BindView(R.id.SaveCard_back)
    ImageView Back;
    @BindView(R.id.Top)
    RelativeLayout Top;
    @BindView(R.id.SpinnnerBank)
    Spinner SpinnnerBank;
    @BindView(R.id.SpinnerPrivince)
    Spinner SpinnerPrivince;
    @BindView(R.id.SpinnerCity)
    Spinner SpinnerCity;

    @BindView(R.id.EditTextUserName)
    EditText EditTextUserName;
    @BindView(R.id.EditTextCardNum)
    EditText EditTextCardNum;
    @BindView(R.id.BindCardBtn)
    Button BindCardBtn;
    List<List<MapsIdAndValue>> maps;
    @BindView(R.id.EditTextBranch)
    EditText EditTextBranch;
    private ArrayAdapter<String> adapterBank;
    private ArrayAdapter<String> adapterPrivince;
    private ArrayAdapter<String> adapterCity;
    List<String> banks = new ArrayList<String>();
    List<String> Prinvince = new ArrayList<String>();
    List<MapsIdAndValue> mapsPrivinces;
    List<MapsIdAndValue> Citys;
    int BankId;
    int PrivinceId;
    String PrivinceName;
    int CityId;
    String CityName;
    String Branch;
    String UserName;
    String CardNum;
    List<List<CardsData>> cs = new ArrayList<>();
    List<CardsData> bankss = new ArrayList<>();
    List<CardsData> provincess = new ArrayList<>();
    List<CardsData> cityss = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_card);
        ButterKnife.bind(this); MyApp.getInstance().addActivitys(this);
        RetrofitService.getInstance().getCardDatas(this);
        initClick();
        initOnItemClick();
    }

    private void initOnItemClick() {
        SpinnnerBank.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BankId = bankss.get(position).getBankid();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpinnerPrivince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RetrofitService.getInstance().getPrivens(SaveCardActivity.this, provincess.get(position).getProvince_id());
                Log.d("城市id==", provincess.get(position).getProvince_id() + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpinnerCity.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CityId = cityss.get(position).getCity_id();
                CityName = cityss.get(position).getCityName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initClick() {
        Back.setOnClickListener(this);
        BindCardBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveCard_back:
                finish();
                break;
            case R.id.BindCardBtn:
                Branch = EditTextBranch.getText().toString().trim();
                UserName = EditTextUserName.getText().toString().trim();
                CardNum = EditTextCardNum.getText().toString().trim();
                if ("".equals(Branch) || "".equals(UserName) || "".equals(CardNum)) {
                    Toasty.error(this, "不可为空", 2000).show();
                    return;
                }
                //RetrofitService.getInstance().getCheckCardNum(this,UserName,CardNum);
                RetrofitService.getInstance().getSaveBankCard(this, BankId, provincess.get(SpinnerPrivince.getSelectedItemPosition()).getProvince_id(), provincess.get(SpinnerPrivince.getSelectedItemPosition()).getProvincename(), CityId, CityName, Branch, UserName, CardNum);
                break;
        }
    }

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_CARDDATAS) {
            if (object != null) {
                cs = (List<List<CardsData>>) object;
                bankss = cs.get(1);
                for (int i = 0; i < bankss.size(); i++) {
                    Log.d("银行卡的id", bankss.get(i).toString());
                }
                provincess = cs.get(2);
                List<String> bankname = new ArrayList<>();
                List<String> province = new ArrayList<>();
                for (int i = 0; i < bankss.size(); i++) {
                    bankname.add(bankss.get(i).getBankname());
                }
                for (int i = 0; i < provincess.size(); i++) {
                    province.add(provincess.get(i).getProvincename());
                }
                adapterBank = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bankname);
                //第三步：为适配器设置下拉列表下拉时的菜单样式。
                adapterBank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnnerBank.setAdapter(adapterBank);

                adapterPrivince = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, province);
                adapterPrivince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerPrivince.setAdapter(adapterPrivince);


               /* List<MapsIdAndValue> mapsBank = new ArrayList<MapsIdAndValue>();
                mapsPrivinces = new ArrayList<MapsIdAndValue>();
                mapsBank = maps.get(0);
                mapsPrivinces = maps.get(1);
                for (int i = 0; i < mapsBank.size(); i++) {
                    Log.d("银行列表1==", mapsBank.get(i).getValues());
                    banks.add(mapsBank.get(i).getValues());
                }
                for (int i = 0; i < mapsPrivinces.size(); i++) {
                    Prinvince.add(mapsPrivinces.get(i).getValues());
                    Log.d("省份列表1==", mapsPrivinces.get(i).getValues());
                }
                //NewArrAdapter newArrAdapter=new NewArrAdapter(this,R.layout.spinner_item,banks);
                // newArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




                List<MapsIdAndValue> mapsIdAndValues1 = maps.get(1);
                String values = mapsBank.get(3).getValues();
                String values1 = mapsIdAndValues1.get(5).getValues();
                Log.d("获取银行卡所需的数据返回2", values + "    " + values1);*/

            }
        }
        if (apiId == RetrofitService.API_ID_GETCITYS) {
            if (object != null) {
                cityss = (List<CardsData>) object;
                List<String> Cits = new ArrayList<>();
                for (int i = 0; i < cityss.size(); i++) {
                    Cits.add(cityss.get(i).getCityName());
                    Log.d("城市名称", cityss.get(i).getCityName());
                }
                adapterCity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Cits);
                adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerCity.setAdapter(adapterCity);
            }
        }
        if (apiId == RetrofitService.API_ID_SAVECARD) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    Toasty.success(this, restultInfo.getMsg(), 2000).show();
                    finish();
                } else {
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
}
