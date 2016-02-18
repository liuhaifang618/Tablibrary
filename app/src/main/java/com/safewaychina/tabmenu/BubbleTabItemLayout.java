package com.safewaychina.tabmenu;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.safewaychina.tabmenu.bubbleview.DropCover;
import com.safewaychina.tabmenu.bubbleview.WaterDrop;

/**
 * @author liu_haifang
 * @version 1.0
 * @Title：SAFEYE@
 * @Description：
 * @date 2015-10-28
 */
public class BubbleTabItemLayout extends RelativeLayout {

    private BaseTabItemLayout tabItemLayout;
    private WaterDrop waterDrop;
    private DragBubbleListener dragBubbleListener;
    private boolean isCanShowBulle;
    private boolean isShowing;


    public interface DragBubbleListener {
        void onDismiss();
    }


    public BubbleTabItemLayout(Context context) {
        super(context);
        initView();
    }

    public BubbleTabItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BubbleTabItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        tabItemLayout = new BaseTabItemLayout(getContext());
        tabItemLayout.setLayoutParams(layoutParams);
        addView(tabItemLayout);


        RelativeLayout.LayoutParams bubbleParams = new RelativeLayout.LayoutParams(dip2px(getResources().getInteger(R.integer.bubble_size)), dip2px(getResources().getInteger(R.integer.bubble_size)));
        bubbleParams.topMargin = dip2px(getResources().getInteger(R.integer.bubble_top_margin));
        bubbleParams.rightMargin = dip2px(getResources().getInteger(R.integer.bubble_right_margin));
        bubbleParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        waterDrop = new WaterDrop(getContext());
        waterDrop.setTextSize(getResources().getInteger(R.integer.bubble_text_size));
        waterDrop.setLayoutParams(bubbleParams);
        addView(waterDrop);
        waterDrop.bringToFront();

        waterDrop.setOnDragCompeteListener(new DropCover.OnDragCompeteListener() {
            @Override
            public void onDrag() {
                if (isCanShowBulle) {
                    if (dragBubbleListener != null) {
                        dragBubbleListener.onDismiss();
                    }
                }
            }
        });

        waterDrop.setVisibility(isCanShowBulle && isBulleEmpty() ? View.VISIBLE : View.GONE);
    }

    public boolean isBulleEmpty() {
        try {
            String text = waterDrop.getText();
            int anInt = Integer.parseInt(text);
            return !TextUtils.isEmpty(waterDrop.getText()) && anInt > 0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    public float getScope() {
        return tabItemLayout.getScope();
    }

    public void setScope(float scope) {
        tabItemLayout.setScope(scope);
    }

    public TabImageView getIconView() {
        return tabItemLayout.getIconView();
    }

    public void setIconView(TabImageView iconView) {
        tabItemLayout.setIconView(iconView);
    }

    public TabTextView getTextView() {
        return tabItemLayout.getTextView();
    }

    public int getTabSlidingMode() {
        return tabItemLayout.getTabSlidingMode();
    }

    public void setTabSlidingMode(int tabSlidingMode) {
        tabItemLayout.setTabSlidingMode(tabSlidingMode);
    }

    public boolean isClickAble() {
        return tabItemLayout.isClickAble();
    }

    public void setClickAble(boolean isClickAble) {
        tabItemLayout.setClickAble(isClickAble);
    }

    public void setImageBg(int imageBg) {
        tabItemLayout.setImageBg(imageBg);
    }

    public void setTabText(String tabText) {
        tabItemLayout.setTabText(tabText);
    }

    public void setIsSelect(boolean isSelect) {
        tabItemLayout.setIsSelect(isSelect);
    }

    public void isCanShowBubble(boolean vis) {
        isCanShowBulle = vis;
    }

    public void setBubbleText(String text) {
        if (isCanShowBulle) {
            waterDrop.setText(text);
            waterDrop.setVisibility(isBulleEmpty() ? View.VISIBLE : View.GONE);
        }
    }


    public void dismissBubble() {
        waterDrop.setText("");
        waterDrop.setVisibility(View.GONE);
    }

    public void setDragBubbleListener(DragBubbleListener dragBubbleListener) {
        this.dragBubbleListener = dragBubbleListener;
    }

    public boolean isShowing() {
        return waterDrop.getVisibility() == View.VISIBLE ? true : false;
    }

    public boolean isCanShowBulle() {
        return isCanShowBulle;
    }

    public void IsCanShowBulle(boolean isCanShowBulle) {
        this.isCanShowBulle = isCanShowBulle;
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
