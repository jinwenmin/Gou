package com.example.king.gou.ui.frmMyActivity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewMessageActivity extends AutoLayoutActivity implements OnItemClickListener, View.OnClickListener {

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
    @BindView(R.id.Linearshoujian)
    LinearLayout Linearshoujian;
    @BindView(R.id.SendMsg)
    Button SendMsg;
    @BindView(R.id.editText)
    EditText editText;
    private AlertView alertView;
    ; // 一个自定义的布局，作为显示的内容
    View contentView;
    private EditText editText_select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        ButterKnife.bind(this);
        alertView = new AlertView(null, null, "取消", null, new String[]{"完成"}, this, AlertView.Style.Alert, this);
        contentView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.mess_select_user, null);
        alertView.addExtView(contentView);
        initRadioGroup();
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
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
                    alertView.show();
                }
                if (i == R.id.NewMessRadioBtn3) {
                    Linearshoujian.setVisibility(View.GONE);
                }

            }
        });
        NewMessRadioBtn1.setChecked(true);

    }


    @Override
    public void onItemClick(Object o, int position) {
        if (o == alertView && position != AlertView.CANCELPOSITION) {
            editText_select = ((EditText) contentView.findViewById(R.id.mess_select_edit));
            editText.setText(editText_select.getText().toString().trim());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._back:
                finish();
                break;
        }
    }
}
