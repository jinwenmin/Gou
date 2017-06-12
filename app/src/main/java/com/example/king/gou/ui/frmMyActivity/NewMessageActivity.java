package com.example.king.gou.ui.frmMyActivity;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;

import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.example.king.gou.utils.BasePopupWindow;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewMessageActivity extends AutoLayoutActivity {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.NewMessTop)
    RelativeLayout NewMessTop;
    @BindView(R.id.NewMessRadioBtn1)
    RadioButton NewMessRadioBtn1;
    @BindView(R.id.NewMessRadioBtn2)
    RadioButton NewMessRadioBtn2;
    @BindView(R.id.NewMessRadioBtn3)
    RadioButton NewMessRadioBtn3;
    @BindView(R.id.NewMessGadioG)
    RadioGroup NewMessGadioG;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.Linearshoujian)
    LinearLayout Linearshoujian;
    @BindView(R.id.SendMsg)
    Button SendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        ButterKnife.bind(this);
        initRadioGroup();
    }

    private void initRadioGroup() {
        NewMessGadioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.NewMessRadioBtn1) {
                    Linearshoujian.setVisibility(View.GONE);
                }
                if (i == R.id.NewMessRadioBtn2) {
                    Linearshoujian.setVisibility(View.VISIBLE);
                    View view = getLayoutInflater().inflate(R.layout.item_cpbb, null, false);
                    showPopupWindow(view);
                }
                if (i == R.id.NewMessRadioBtn3) {
                    Linearshoujian.setVisibility(View.GONE);
                }

            }
        });
        NewMessRadioBtn1.setChecked(true);

    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.item_cpbb, null);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                AutoFrameLayout.LayoutParams.WRAP_CONTENT, AutoFrameLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.fenxiang));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }

}
