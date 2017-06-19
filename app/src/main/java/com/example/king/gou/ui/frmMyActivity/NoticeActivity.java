package com.example.king.gou.ui.frmMyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.king.gou.R;
import com.example.king.gou.adapters.NoticeAdapter;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.PinnedHeaderListView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoticeActivity extends AutoLayoutActivity implements HttpEngine.DataListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.NoticeTop)
    RelativeLayout NoticeTop;
    @BindView(R.id.NoticeListView)
    ListView NoticeListView;
    ArrayList<String[]> Notices;
    NoticeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        adapter = new NoticeAdapter(this);
        NoticeListView.setAdapter(adapter);
        RetrofitService.getInstance().GetNotices(this);
        NoticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = Notices.get(position)[0];
                String substring = s.substring(0, s.indexOf("."));
                int parseInt = Integer.parseInt(substring);
                System.out.println("这个数字是==" + parseInt + "   " + s);
                Intent intent=new Intent(NoticeActivity.this,NoticeContentActivity.class);
                intent.putExtra("uid",parseInt);
                startActivity(intent);
                finish();
                RetrofitService.getInstance().getNoticesContent(NoticeActivity.this, parseInt);
            }
        });
    }


    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_NOTICECONTENT) {
            Notices = (ArrayList<String[]>) object;
            adapter.addNotice(Notices);
            Log.d("公告的内容", Notices.get(0)[0]);
        }
    }

    @Override
    public void onRequestStart(int apiId) {

    }

    @Override
    public void onRequestEnd(int apiId) {

    }
}
