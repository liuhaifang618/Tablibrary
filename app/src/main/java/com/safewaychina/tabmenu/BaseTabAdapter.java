package com.safewaychina.tabmenu;

/**
 * @author liu_haifang
 * @version 1.0
 * @TitleSAFEYE@
 * @Description
 * @date 2015-07-07
 */
public abstract class BaseTabAdapter {


    public abstract TabItem getItem(int position);

    public abstract int getCount();

    public abstract BubbleTabItemLayout getView(TabConfig tabConfig,int position);


}
