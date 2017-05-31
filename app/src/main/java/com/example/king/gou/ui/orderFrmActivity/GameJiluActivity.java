package com.example.king.gou.ui.orderFrmActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.gou.R;
import com.example.king.gou.adapters.MyFrmPageAdapter;
import com.example.king.gou.fragment.BaseFragment;
import com.example.king.gou.ui.gameAcVpFrms.AllFragment;
import com.example.king.gou.ui.gameAcVpFrms.KillOrderFragment;
import com.example.king.gou.ui.gameAcVpFrms.NoDrawFragment;
import com.example.king.gou.ui.gameAcVpFrms.NoWinFragment;
import com.example.king.gou.ui.gameAcVpFrms.WinFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    private CalendarView calenderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_jilu);
        ButterKnife.bind(this);
        myFrmPageAdapter = new MyFrmPageAdapter(getSupportFragmentManager());
        gamejlTablayout.setupWithViewPager(gamejlViewpager);
        gamejlViewpager.setAdapter(myFrmPageAdapter);
        relateTime1.setClickable(true);
        relateTime1.setOnClickListener(this);
        relateTime2.setOnClickListener(this);
        relateBank3.setOnClickListener(this);
        initViewpager();
    }

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

    public void showSelectDate() {
        Log.i("弹出对话框", "");
    /*    AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择日期");
        //    设置Title的图标
        builder.setIcon(R.mipmap.ic_youxijilu);
        builder.setView(R.layout.timeselect);
        builder.show();*/
        PopupWindow mPopupWindow;
        View popupView = getLayoutInflater().inflate(R.layout.timeselect, null);
        mPopupWindow = new PopupWindow(popupView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

       /* View view = getLayoutInflater().inflate(R.layout.timeselect, null, false);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        calenderView = ((CalendarView) view.findViewById(R.id.calendarView));
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.fenxiang));
        popupWindow.showAtLocation(null, Gravity.CENTER, 0, 0);*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relateTime1:

                Toast.makeText(this, "点击了第一个", Toast.LENGTH_SHORT).show();
                //showSelectDate();
                PopupWindow mPopupWindow;
                View popupView = getLayoutInflater().inflate(R.layout.timeselect, null);
                mPopupWindow = new PopupWindow(popupView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
                mPopupWindow.setTouchable(true);
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
                break;
        }
    }
}
