package com.safewaychina.tabmenu;

import android.view.View;

/**
 * @author liu_haifang
 * @version 1.0
 * @TitleSAFEYE@
 * @Description
 * @date 2015-07-08
 */
public class TabConfig {

    private int textDefaultColor = View.NO_ID;
    private int textFilterColor = View.NO_ID;
    private float scope = View.NO_ID;
    private float tabTextSize = View.NO_ID;
    private int tabSlidingMode;

    public TabConfig(int textDefaultColor, int textFilterColor, float scope, float tabTextSize, int tabSlidingMode) {
        this.textDefaultColor = textDefaultColor;
        this.textFilterColor = textFilterColor;
        this.scope = scope;
        this.tabTextSize = tabTextSize;
        this.tabSlidingMode = tabSlidingMode;
    }

    public int getTextDefaultColor() {
        return textDefaultColor;
    }

    public void setTextDefaultColor(int textDefaultColor) {
        this.textDefaultColor = textDefaultColor;
    }

    public int getTextFilterColor() {
        return textFilterColor;
    }

    public void setTextFilterColor(int textFilterColor) {
        this.textFilterColor = textFilterColor;
    }

    public float getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public float getTabTextSize() {
        return tabTextSize;
    }

    public void setTabTextSize(float tabTextSize) {
        this.tabTextSize = tabTextSize;
    }

    public int getTabSlidingMode() {
        return tabSlidingMode;
    }

    public void setTabSlidingMode(int tabSlidingMode) {
        this.tabSlidingMode = tabSlidingMode;
    }
}
