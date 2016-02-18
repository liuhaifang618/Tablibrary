package com.safewaychina.tabmenu;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * @author liu_haifang
 * @version 1.0
 * @Title：SAFEYE@
 * @Description：
 * @date 2015-07-31
 */
public class TabViewPager extends ViewPager implements ViewpagerHelper {
    public TabViewPager(Context context) {
        super(context);
    }

    public TabViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
