package com.example.king.gou.ui.frmMyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MessageActivity extends AutoLayoutActivity {

    @BindView(R.id.gamejl_back)
    ImageView gamejlBack;
    @BindView(R.id.MessIn)
    RadioButton MessIn;
    @BindView(R.id.MessOut)
    RadioButton MessOut;
    @BindView(R.id.newMess)
    TextView newMess;
    @BindView(R.id.MessageTop)
    RelativeLayout MessageTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        newMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this,NewMessageActivity.class));
                finish();
            }
        });
    }
}
