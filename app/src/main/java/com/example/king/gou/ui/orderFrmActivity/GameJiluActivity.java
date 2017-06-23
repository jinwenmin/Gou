package com.example.king.gou.ui.orderFrmActivity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.gou.R;
import com.example.king.gou.adapters.MyFrmPageAdapter;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.ui.MainActivity;
import com.example.king.gou.ui.gameAcVpFrms.AllFragment;
import com.example.king.gou.ui.gameAcVpFrms.KillOrderFragment;
import com.example.king.gou.ui.gameAcVpFrms.NoDrawFragment;
import com.example.king.gou.ui.gameAcVpFrms.NoWinFragment;
import com.example.king.gou.ui.gameAcVpFrms.WinFragment;
import com.example.king.gou.utils.DateUtil;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class GameJiluActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.gamejl_back)
    ImageView gamejlBack;
    @BindView(R.id.RechargeFirmTop)
    RelativeLayout RechargeFirmTop;
    @BindView(R.id.timetext)
    TextView timetext;
    @BindView(R.id.relateTime1)
    RelativeLayout relateTime1;
    @BindView(R.id.timetext2)
    TextView timetext2;
    @BindView(R.id.relateTime2)
    RelativeLayout relateTime2;
    @BindView(R.id.timetext3)
    TextView timetext3;
    @BindView(R.id.relateBank3)
    RelativeLayout relateBank3;
    @BindView(R.id.gamejl_tablayout)
    TabLayout gamejlTablayout;
    @BindView(R.id.gamejl_viewpager)
    ViewPager gamejlViewpager;
    MyFrmPageAdapter myFrmPageAdapter;
    @BindView(R.id.gameLotteryType)
    Spinner gameLotteryType;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_jilu);
        ButterKnife.bind(this);
        myFrmPageAdapter = new MyFrmPageAdapter(getSupportFragmentManager());
        gamejlTablayout.setupWithViewPager(gamejlViewpager);
        gamejlViewpager.setAdapter(myFrmPageAdapter);
        relateTime1.setClickable(true);

        initClick();
        initViewpager();
        initDateDialog();
        initSpinner();
    }

    private void initClick() {
        relateTime1.setOnClickListener(this);
        relateTime2.setOnClickListener(this);
        relateBank3.setOnClickListener(this);
        gamejlBack.setOnClickListener(this);
    }

    private void initSpinner() {
        List<String> list = new ArrayList<>();
        list.add("第一");
        list.add("第一");
        list.add("第一");
        list.add("第一");
        list.add("第一");
        list.add("第一");
        list.add("第一");
        list.add("第一");
        //将可选内容与adapter链接
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameLotteryType.setAdapter(adapter);
        gameLotteryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setVisibility(View.VISIBLE);
            }
        });


    }

    private void initDateDialog() {
        int year;
        int month;
        int day;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        timetext.setText(year + "-" + month + "-" + day);
        //获取当前所有的毫秒数
        long times = System.currentTimeMillis();
        //加上一天的毫秒数就是明天的时间
        long hou = times + 86400000;
        Date date = new Date(hou);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println();
        Log.e("times", sdf.format(date));
        timetext2.setText(sdf.format(date));
        //第一个日期选择器
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                timetext.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                Long getdaytime = DateUtil.getInstance().toDate(year + "-" + (month + 1) + "-" + dayOfMonth) / 1000;
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(timetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(timetext.getText().toString()) / 1000;
                if (l2 - l1 > 864000) {
                    Toasty.warning(getApplicationContext(), "你的选择期限超过了10天", Toast.LENGTH_SHORT, true).show();
                }
                Log.e("现在时长", l2 + "  " + l1);
            }
        }, year, month, day);
        //第二个日期选择器
        datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                timetext2.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                //第二个日期的时间
                long l2 = DateUtil.getInstance().toDate(timetext2.getText().toString()) / 1000;
                //第一个日期的时间
                long l1 = DateUtil.getInstance().toDate(timetext.getText().toString()) / 1000;
                if (l2 - l1 > 864000) {
                    Toasty.warning(getApplicationContext(), "你的选择期限超过了10天", Toast.LENGTH_SHORT, true).show();
                }
            }
        }, year, month, day);

    }

    //实例化 Viewpager
    private void initViewpager() {
        List<BaseFragment> fragments = new ArrayList<>();
        List<String> tits = new ArrayList<>();
        fragments.add(AllFragment.newInstance());
        fragments.add(NoDrawFragment.newInstance());
        fragments.add(WinFragment.newInstance());
        fragments.add(NoWinFragment.newInstance());
        fragments.add(KillOrderFragment.newInstance());
        tits.add("全部");
        tits.add("未开奖");
        tits.add("已中奖");
        tits.add("未中奖");
        tits.add("已撤单");
        myFrmPageAdapter.addFrmList(fragments, tits);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relateTime1:
                Toast.makeText(this, "点击了第一个", Toast.LENGTH_SHORT).show();
                datePickerDialog.show();
                break;
            case R.id.relateTime2:
                datePickerDialog2.show();
                break;
            case R.id.gamejl_back:
                finish();
        }
    }
}
