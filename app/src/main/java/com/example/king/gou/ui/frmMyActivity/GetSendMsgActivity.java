package com.example.king.gou.ui.frmMyActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.king.gou.MyApp;
import com.example.king.gou.R;
import com.example.king.gou.adapters.IconGridViewAdapter;
import com.example.king.gou.bean.Icons;
import com.example.king.gou.bean.Message;
import com.example.king.gou.bean.RestultInfo;
import com.example.king.gou.service.RetrofitService;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.RxUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class GetSendMsgActivity extends AutoLayoutActivity implements HttpEngine.DataListener, View.OnClickListener, OnItemClickListener {

    @BindView(R.id._back)
    ImageView Back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.Top)
    RelativeLayout Top;
    @BindView(R.id.SendLinear)
    LinearLayout SendLinear;
    @BindView(R.id.getSendMsgListView)
    ListView getSendMsgListView;
    // MessageGetAdapter adapter;

    @BindView(R.id.EditMsg)
    EditText EditMsg;
    int id = 0;
    @BindView(R.id.GetSendMsgLinear)
    LinearLayout GetSendMsgLinear;
    @BindView(R.id.getSendScroll)
    ScrollView getSendScroll;
    String editMsg;
    @BindView(R.id.AddIcon)
    ImageView AddIcon;
    @BindView(R.id.sendMessage)
    ImageView sendMessage;
    private Timer timer;
    List<Message> msgs = new ArrayList<>();
    String[] iconName = new String[]{"[/大笑]", "[/抓狂]", "[/大哭]", "[/拜托]", "[/鄙视]", "[/不屑]", "[/愕然]", "[/眼红]", "[/很拽]", "[/好色]",
            "[/开心]", "[/窃喜]", "[/钦羡]", "[/难过]", "[/喜极]", "[/香吻]", "[/睡觉]", "[/失落]", "[/大骂]", "[/亲亲]",
            "[/悲哀]", "[/惊愕]", "[/害羞]", "[/流泪]", "[/调皮]", "[/闭嘴]", "[/失望]"};
    int[] icon = new int[]{R.drawable.i01, R.drawable.i02, R.drawable.i03, R.drawable.i04, R.drawable.i05, R.drawable.i06, R.drawable.i07, R.drawable.i08, R.drawable.i09, R.drawable.i10,
            R.drawable.i11, R.drawable.i12, R.drawable.i13, R.drawable.i14, R.drawable.i15, R.drawable.i16, R.drawable.i17, R.drawable.i18, R.drawable.i19, R.drawable.i20,
            R.drawable.i21, R.drawable.i22, R.drawable.i23, R.drawable.i24, R.drawable.i25, R.drawable.i26, R.drawable.i27,};
    private AlertView alertView;
    // 一个自定义的布局，作为显示的内容
    View contentView;
    IconGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_send_msg);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivitys(this);
        getSendMsgListView.setDividerHeight(0);
        GetSendMsgLinear.setFocusable(true);
        getSendScroll.setVerticalScrollBarEnabled(false);
        getSendScroll.fullScroll(ScrollView.FOCUS_DOWN);
        initAlert();


        //   adapter = new MessageGetAdapter(this);
        //  getSendMsgListView.setAdapter(adapter);
        initClick();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        title.setText("与" + name + "聊天中");
        String dates = RxUtils.getInstance().Dates(0);
        String dates1 = RxUtils.getInstance().Dates(System.currentTimeMillis());
        RetrofitService.getInstance().getChatList(GetSendMsgActivity.this, 1, 100, "chat_date", "desc", id, dates, dates1);
        initTimer();
    }

    private void initAlert() {
        alertView = new AlertView(null, null, "取消", null, null, this, AlertView.Style.Alert, this);
        contentView = LayoutInflater.from(this).inflate(
                R.layout.item_icons, null);
        alertView.addExtView(contentView);

        GridView iconGrid = (GridView) contentView.findViewById(R.id.icon_View);

        iconGrid.setNumColumns(9);
        adapter = new IconGridViewAdapter(this);
        iconGrid.setAdapter(adapter);
        List<Icons> iconses = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            Icons ic = new Icons();
            ic.setIconImg(icon[i]);
            ic.setIconName(iconName[i]);
            iconses.add(ic);
        }
        adapter.addList(iconses);
        iconGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditMsg.setText(EditMsg.getText().toString() + iconName[position]);
                EditMsg.setSelection(EditMsg.getText().toString().length());
                Log.d("表情名称为", iconName[position]);

                //alertView.dismiss();
            }
        });
    }

    private void initClick() {
        sendMessage.setOnClickListener(this);
        Back.setOnClickListener(this);
        AddIcon.setOnClickListener(this);
    }

    private void initTimer() {
        timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                RetrofitService.getInstance().getNewMsg(GetSendMsgActivity.this, id);


            }
        };
        timer.schedule(timerTask, 0, 1000);

    }

    final Html.ImageGetter imageGetter = new Html.ImageGetter() {

        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            int rId = Integer.parseInt(source);
            drawable = getResources().getDrawable(rId);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            return drawable;
        }
    };

    @Override
    public void onReceivedData(int apiId, Object object, int errorId) {
        if (apiId == RetrofitService.API_ID_CHATLIST) {
            if (object != null) {
                List<Message> m = (List<Message>) object;
                for (int i = 0; i < m.size(); i++) {
                    Message ms = m.get(i);
                    if (ms.getSname().equals(MyApp.getInstance().getUserName())) {
                        View view = LayoutInflater.from(this).inflate(R.layout.item_msg_send, null, false);
                        TextView sendMsg = (TextView) view.findViewById(R.id.SendMsg);
                        TextView SendTime = (TextView) view.findViewById(R.id.SendMsgTimer);
                        String msg = ms.getContent();
                        for (int k = 0; k < iconName.length; k++) {
                            if (msg.contains(iconName[k])) {
                                msg = msg.replace(iconName[k], "<img src=\"" + icon[k] + "\" />");
                            }
                        }

                        sendMsg.setText(Html.fromHtml(msg, imageGetter, null));
                        SendTime.setText(ms.getChat_date());
                        view.setFocusable(true);
                        view.setFocusableInTouchMode(true);
                        view.requestFocus();
                        GetSendMsgLinear.addView(view);
                    }
                    if (ms.getUname().equals(MyApp.getInstance().getUserName())) {
                        View view = LayoutInflater.from(this).inflate(R.layout.item_msg_get, null, false);
                        TextView getmsg = (TextView) view.findViewById(R.id.GetMsg);
                        TextView getMsgTime = (TextView) view.findViewById(R.id.GetMsgTimer);
                        String msg = ms.getContent();

                        for (int k = 0; k < iconName.length; k++) {
                            if (msg.contains(iconName[k])) {
                                msg = msg.replace(iconName[k], "<img src=\"" + icon[k] + "\" />");
                            }
                        }

                        getmsg.setText(Html.fromHtml(msg, imageGetter, null));
                        getMsgTime.setText(ms.getChat_date());
                        view.setFocusable(true);
                        view.setFocusableInTouchMode(true);
                        view.requestFocus();
                        GetSendMsgLinear.addView(view);
                    }
                }
                //      adapter.addList(m);
            }
        }
        if (apiId == RetrofitService.API_ID_SENDMSG) {
            if (object != null) {
                RestultInfo restultInfo = (RestultInfo) object;
                if (restultInfo.isRc()) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_msg_send, null, false);
                    TextView sendMsg = (TextView) view.findViewById(R.id.SendMsg);
                    TextView SendTime = (TextView) view.findViewById(R.id.SendMsgTimer);

                    for (int k = 0; k < iconName.length; k++) {
                        if (editMsg.contains(iconName[k])) {
                            editMsg = editMsg.replace(iconName[k], "<img src=\"" + icon[k] + "\" />");
                        }
                    }

                    sendMsg.setText(Html.fromHtml(editMsg, imageGetter, null));
                    Date date = new Date();
                    RxUtils.getInstance().FormatDate(date);
                    SendTime.setText(RxUtils.getInstance().FormatDate(date));
                    //scroll的最后一个子控件获得焦点
                    view.setFocusable(true);
                    view.setFocusableInTouchMode(true);
                    view.requestFocus();
                    GetSendMsgLinear.addView(view);
                }
                if (!restultInfo.isRc()) {
                    Toasty.error(this, "发送失败", 2000).show();
                    return;
                }
            }
        }
        if (apiId == RetrofitService.API_ID_GETNEWMSG) {
            if (object != null) {
                msgs = (List<Message>) object;
                for (int i = 0; i < msgs.size(); i++) {
                    Message ms = msgs.get(i);
                    View view = LayoutInflater.from(this).inflate(R.layout.item_msg_get, null, false);
                    TextView getmsg = (TextView) view.findViewById(R.id.GetMsg);
                    TextView getMsgTime = (TextView) view.findViewById(R.id.GetMsgTimer);
                    String msg = ms.getContent();
                    for (int k = 0; k < iconName.length; k++) {
                        if (msg.contains(iconName[k])) {
                            msg = msg.replace(iconName[k], "<img src=\"" + icon[k] + "\" />");
                        }
                    }

                    getmsg.setText(Html.fromHtml(msg, imageGetter, null));
                    getMsgTime.setText(ms.getChat_date());
                    view.setFocusable(true);
                    view.setFocusableInTouchMode(true);
                    view.requestFocus();
                    ms.getContent();
                    GetSendMsgLinear.addView(view);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessage:
                editMsg = EditMsg.getText().toString();
                if (editMsg == null || "".equals(editMsg)) {
                    Toasty.error(this, "请输入聊天信息", 2000).show();
                    return;
                }
                EditMsg.setText("");
                RetrofitService.getInstance().getSendMsg(this, id, "普通聊天消息", editMsg);
                break;
            case R.id._back:
                finish();
                break;
            case R.id.AddIcon:
                alertView.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onItemClick(Object o, int position) {

    }
}
