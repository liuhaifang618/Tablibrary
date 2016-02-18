package com.safewaychina.tabmenu;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * @author liu_haifang
 * @version 1.0
 * @Title：SAFEYE@
 * @Description：
 * @date 2015-07-31
 */
public interface ViewpagerHelper {

    public void setAdapter(PagerAdapter adapter);

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener);

    public  int getCurrentItem();

    public void setCurrentItem(int currentPosition);
}
