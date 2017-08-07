package com.example.king.gou.ui.settingfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.king.gou.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AboutUsActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.Top)
    RelativeLayout Top;
    @BindView(R.id.bb)
    TextView bb;
    @BindView(R.id.Version)
    TextView Version;
    @BindView(R.id.VersionInfo)
    TextView VersionInfo;
    @BindView(R.id.About1)
    TextView About1;
    @BindView(R.id.About2)
    TextView About2;
    @BindView(R.id.About3)
    TextView About3;
    @BindView(R.id.About4)
    TextView About4;
    String[] webs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        webs = new String[]{"http://apprules.oss-cn-beijing.aliyuncs.com/aboutus.html",
                "http://apprules.oss-cn-beijing.aliyuncs.com/disclaimer.html",
                "http://apprules.oss-cn-beijing.aliyuncs.com/duty.html",
                "http://apprules.oss-cn-beijing.aliyuncs.com/rule.html"};
        ButterKnife.bind(this);
        initClick();
    }

    private void initClick() {
        Back.setOnClickListener(this);
        About1.setOnClickListener(this);
        About2.setOnClickListener(this);
        About3.setOnClickListener(this);
        About4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._back:
                finish();
                break;
            case R.id.About1:
                Intent intent1 = new Intent(AboutUsActivity.this, AboutUsWebActivity.class);
                intent1.putExtra("title", About1.getText().toString().trim());
                intent1.putExtra("web", webs[0]);
                startActivity(intent1);
                break;
            case R.id.About2:
                Intent intent2 = new Intent(AboutUsActivity.this, AboutUsWebActivity.class);
                intent2.putExtra("title", About2.getText().toString().trim());
                intent2.putExtra("web", webs[1]);
                startActivity(intent2);
                break;
            case R.id.About3:
                Intent intent3 = new Intent(AboutUsActivity.this, AboutUsWebActivity.class);
                intent3.putExtra("title", About2.getText().toString().trim());
                intent3.putExtra("web", webs[2]);
                startActivity(intent3);
                break;
            case R.id.About4:
                Intent intent4 = new Intent(AboutUsActivity.this, AboutUsWebActivity.class);
                intent4.putExtra("title", About2.getText().toString().trim());
                intent4.putExtra("web", webs[3]);
                startActivity(intent4);
                break;
        }
    }
}
