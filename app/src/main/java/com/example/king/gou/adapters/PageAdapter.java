package com.example.king.gou.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.jude.rollviewpager.RollPagerView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/1.
 */

public class PageAdapter extends PagerAdapter {

    private List<View> mListViews;

    public PageAdapter(List<View> mListViews) {
        this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container != null) {
            container.removeView(mListViews.get(position));//删除页卡
        }
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
        if (container != null) {
            container.removeView(mListViews.get(position));
        }
        container.addView(mListViews.get(position), 0);//添加页卡

        return mListViews.get(position);
    }

    @Override
    public int getCount() {
        return mListViews.size();//返回页卡的数量
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;//官方提示这样写
    }
}
