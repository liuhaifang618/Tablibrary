package com.safewaychina.tabmenu;

import android.view.View;

/**
 * @author liu_haifang
 * @version 1.0
 * @Title:SAFEYE@
 * @Description:
 * @date 2015-07-07
 */
public class TabItem {

    private String tabText;
    private int imageBg = View.NO_ID;
    private boolean isClickAble;
    private boolean vaild;
    private boolean showBubble;

    public TabItem(String tabText) {
        this(true, tabText, false, false);
    }

    public TabItem(String tabText, boolean vaild) {
        this(true, tabText, false, vaild);
    }

    public TabItem(String tabText, boolean showBubble, boolean vaild) {
        this(true, tabText, showBubble, vaild);
    }


    public TabItem(boolean isClickAble, String tabText) {
        this(isClickAble, tabText, false, false);
    }


    public TabItem(boolean isClickAble, String tabText, boolean showBubble, boolean vaild) {
        this.tabText = tabText;
        this.isClickAble = isClickAble;
        this.showBubble = showBubble;
        this.vaild = vaild;
    }

    public TabItem(String tabText, int imageBg) {
        this(true, tabText, imageBg, false);
    }

    public TabItem(String tabText, int imageBg, boolean showBubble) {
        this(true, tabText, imageBg, showBubble);
    }

    public TabItem(String tabText, int imageBg, boolean showBubble, boolean vaild) {
        this(true, tabText, imageBg, showBubble, vaild);
    }

    public TabItem(boolean isClickAble, String tabText, int imageBg) {
        this(isClickAble, tabText, imageBg, false, false);
    }

    public TabItem(boolean isClickAble, String tabText, int imageBg, boolean vaild) {
        this(isClickAble, tabText, imageBg, false, vaild);
    }

    public TabItem(boolean isClickAble, String tabText, int imageBg, boolean showBubble, boolean vaild) {
        this.tabText = tabText;
        this.imageBg = imageBg;
        this.isClickAble = isClickAble;
        this.showBubble = showBubble;
        this.vaild = vaild;
    }

    public String getTabText() {
        return tabText;
    }

    public void setTabText(String tabText) {
        this.tabText = tabText;
    }

    public int getImageBg() {
        return imageBg;
    }

    public void setImageBg(int imageBg) {
        this.imageBg = imageBg;
    }

    public boolean isClickAble() {
        return isClickAble;
    }

    public void setVaild(boolean isVaild) {
        this.vaild = isVaild;
    }

    public boolean isVaild() {
        return vaild;
    }

    public boolean isShowBubble() {
        return showBubble;
    }

    public void setShowBubble(boolean showBubble) {
        this.showBubble = showBubble;
    }
}
