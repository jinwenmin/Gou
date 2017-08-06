package com.zhy.autolayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

;

/**
 * Created by zhy on 15/11/19.
 */
public class AutoLayoutActivity extends AppCompatActivity {
    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    //private Broadcast broad;
    IntentFilter intentFilter;

    /*private class Broadcast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.Finish")) {
                context.unregisterReceiver(this);
                Log.d("退出=", "退出");
                //     finish();
                System.exit(0);
            }
        }
    }*/

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
       /* if (intentFilter == null) {
            broad = new Broadcast();
            intentFilter = new IntentFilter();
            intentFilter.addAction("action.Finish");
            registerReceiver(broad, intentFilter);
        }*/
     /*
         intentFilter = new IntentFilter();
        intentFilter.addAction("action.Finish");
        this.registerReceiver(broad, intentFilter);*/
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }

        if (view != null) return view;

        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // this.unregisterReceiver(broad);
    }
}
