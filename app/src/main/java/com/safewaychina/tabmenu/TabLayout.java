package com.safewaychina.tabmenu;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.safewaychina.basecommon.base.widget.listener.ListenerValidator;
import com.safewaychina.basecommon.base.widget.listener.OnClickListenerWraper;

import java.util.List;


public class TabLayout extends LinearLayout {

    private static final int DEFAULT_SCOPE = 2;
    private static final int DEFAULT_FILTER_COLOR = 0xFF45C01A;
    private static final float DEFAULT_TEXT_SIZE = 12f;
    private static final int MODE_SELECT = 0;
    private static final int MODE_COLOR_FILTER = 1;
    private static final int DEFAULT_TAB_SLIDING_MODE = 1;

    private BaseTabAdapter adapter;
    private ViewpagerHelper viewPager;
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener;
    private AttributeSet attrs;
    private int tabSlidingMode;
    private float scope;
    private int textDefaultColor;
    private float textSize;
    private int tabFilterColor;
    private TabConfig tabConfig;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private List<Fragment> fragments;
    private int layoutId;


    private OnSelectListener onSelectListener;
    private ListenerValidator selectValidator;


    public TabLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        this.attrs = attrs;
        // Load attributes
        textDefaultColor = Color.BLACK;
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE, getResources().getDisplayMetrics());
        ;
        tabSlidingMode = DEFAULT_TAB_SLIDING_MODE;
        tabFilterColor = DEFAULT_FILTER_COLOR;
        scope = DEFAULT_SCOPE;

        if (attrs != null) {
            final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.tab_view, defStyle, 0);
            textDefaultColor = a.getColor(R.styleable.tab_view_tab_text_default_color, textDefaultColor);
            scope = a.getDimension(R.styleable.tab_view_tab_scope, scope);
            tabSlidingMode = getSlidingMode(a.getInt(R.styleable.tab_view_tab_silidingMode, tabSlidingMode));
            textSize = a.getDimension(R.styleable.tab_view_tab_text_size, textSize);
            tabFilterColor = a.getColor(R.styleable.tab_view_tab_filter_color, tabFilterColor);
            a.recycle();
        }
        initLayout();

        tabConfig = new TabConfig(textDefaultColor, tabFilterColor, scope, textSize, tabSlidingMode);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    private void initLayout() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    private int getSlidingMode(int mode) {
        switch (mode) {
            case 0:
                return MODE_SELECT;
            case 1:
                return MODE_COLOR_FILTER;
            default:
                throw new IllegalArgumentException("Unknown id: " + mode);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getChildCount() <= 0) {
            generateFragmentViews();
        }
    }

    private void generateViewPagesViews() {
        if (adapter != null) {
            int count = adapter.getCount();
            for (int i = 0; i < count; i++) {
                BubbleTabItemLayout tabItemLayout = adapter.getView(tabConfig, i);
                tabItemLayout.measure(getMeasuredWidth() / 4, getMeasuredHeight());
                addView(tabItemLayout);
                tabItemLayout.setOnClickListener(new TabPagerClickListener());
            }
            setDeaultSelect();
        }
    }


    private void generateFragmentViews() {
        if (adapter != null) {
            int count = adapter.getCount();
            for (int i = 0; i < count; i++) {
                TabItem tabItem = adapter.getItem(i);
                LayoutParams params = new LayoutParams(0, -1);
                View view = adapter.getView(tabConfig, i);
                params.weight = 1;
                view.setLayoutParams(params);
//                int makeMeasureWidth = MeasureSpec.makeMeasureSpec(getMeasuredWidth() / 4, MeasureSpec.UNSPECIFIED);
//                int makeMeasureHeight = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.AT_MOST);
//                view.measure(makeMeasureWidth,makeMeasureHeight);
                addView(view);
                //view.measure(getMeasuredWidth()/4,getMeasuredHeight());
                view.setOnClickListener(tabItem != null && tabItem.isVaild() ? new TabFragmentVaildListener(selectValidator) : new TabFragmentClickListener());
            }
        }
    }


    private void setDeaultSelect() {
        final BaseTabItemLayout tabItemLayout = viewPager != null ? getViewPagerCurrentItem() : getDefaultItem();
        if (tabItemLayout == null) {
            return;
        }
        tabItemLayout.setIsSelect(true);
    }

    private BaseTabItemLayout getViewPagerCurrentItem() {
        int currentItem = viewPager.getCurrentItem();
        return (BaseTabItemLayout) getChildAt(currentItem);
    }

    private BaseTabItemLayout getDefaultItem() {
        if (getChildCount() > 0) {
            return (BaseTabItemLayout) getChildAt(0);
        }
        return null;
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            if (positionOffset > 0) {
                BaseTabItemLayout left = (BaseTabItemLayout) getChildAt(position);
                BaseTabItemLayout right = (BaseTabItemLayout) getChildAt(position + 1);
                if (left != null) {
                    left.setAlpha(1 - positionOffset);
                }
                if (right != null) {
                    right.setAlpha(positionOffset);
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < getChildCount(); i++) {
                BaseTabItemLayout tabItemLayout = (BaseTabItemLayout) getChildAt(i);
                if (i == position) {
                    tabItemLayout.setIsSelect(true);
                    continue;
                }
                tabItemLayout.setIsSelect(false);
            }

            for (int i = 0, size = getChildCount(); i < size; i++) {
                BaseTabItemLayout tabLayout = (BaseTabItemLayout) getChildAt(i);
                tabLayout.setSelected(position == i);
            }
            if (viewPagerPageChangeListener != null) {
                viewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    public BaseTabAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseTabAdapter adapter) {
        this.adapter = adapter;
        if (viewPager != null) {
            generateViewPagesViews();
        }
    }

    public void setFragmentAdapter(BaseTabAdapter adapter, FragmentManager manager, List<Fragment> fragments, int layoutId) {
        this.fragments = fragments;
        this.fragmentManager = manager;
        this.layoutId = layoutId;
        this.adapter = adapter;
        requestLayout();
//        if (adapter != null) {
//            generateFragmentViews();
//        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setCurrentFragment(final int position) {
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                BubbleTabItemLayout tabItemLayout = (BubbleTabItemLayout) getChildAt(position);
                if (tabItemLayout != null) {
                    Fragment to = fragments.get(position);
                    FragmentUtils.switchFragment(currentFragment, to, fragmentManager, layoutId);
                    currentFragment = to;
                    tabItemLayout.setIsSelect(true);
                }
            }
        });


    }

    public ViewpagerHelper getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewpagerHelper viewPager) {
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(new InternalViewPagerListener());
    }


    public ViewPager.OnPageChangeListener getViewPagerPageChangeListener() {
        return viewPagerPageChangeListener;
    }

    public void setViewPagerPageChangeListener(ViewPager.OnPageChangeListener viewPagerPageChangeListener) {
        this.viewPagerPageChangeListener = viewPagerPageChangeListener;
    }

    private class TabPagerClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < getChildCount(); i++) {
                BaseTabItemLayout tabItemLayout = (BaseTabItemLayout) getChildAt(i);
                if (v == tabItemLayout) {
                    if (viewPager != null) {
                        viewPager.setCurrentItem(i);
                    }
                    tabItemLayout.setIsSelect(true);
                    continue;
                }
                tabItemLayout.setIsSelect(false);
            }
        }
    }

    private class TabFragmentVaildListener extends OnClickListenerWraper {

        public TabFragmentVaildListener() {
            super(null);
        }

        public TabFragmentVaildListener(ListenerValidator validator) {
            super(validator);
        }

        @Override
        protected void onClickWrap(View view) {
            selectAction(view);
        }
    }

    private class TabFragmentClickListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            selectAction(view);
        }
    }


    /**
     * 切换fragment
     *
     * @param position
     */
    public void swith(int position) {

        BubbleTabItemLayout tabItemLayout = (BubbleTabItemLayout) getChildAt(position);
        if (tabItemLayout.isClickAble()) {
            Fragment to = fragments.get(position);
            FragmentUtils.switchFragment(currentFragment, to, fragmentManager, layoutId);
            currentFragment = to;
        }
        tabItemLayout.setIsSelect(tabItemLayout.isClickAble());
    }


    /**
     * fragmeng选择操作
     *
     * @param view
     */
    private void selectAction(View view) {
        for (int i = 0; i < getChildCount(); i++) {
            BubbleTabItemLayout tabItemLayout = (BubbleTabItemLayout) getChildAt(i);
            if (view == tabItemLayout) {
                if (tabItemLayout.isClickAble()) {
                    Fragment to = fragments.get(i);
                    FragmentUtils.switchFragment(currentFragment, to, fragmentManager, layoutId);
                    currentFragment = to;
                }
                tabItemLayout.setIsSelect(tabItemLayout.isClickAble());
                if (onSelectListener != null) {
                    onSelectListener.onSelect(i, view);
                }
                if (tabItemLayout.isClickAble())
                    continue;
                else
                    break;
            }
            tabItemLayout.setIsSelect(false);
        }
    }


    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    /**
     * 设置bubble的数量
     *
     * @param index
     * @param text
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBubbleText(final int index, final int text) {
        final BubbleTabItemLayout bubbleTabItemLayout = (BubbleTabItemLayout) getChildAt(index);
        if (bubbleTabItemLayout == null) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    final BubbleTabItemLayout bubbleTabItemLayout = (BubbleTabItemLayout) getChildAt(index);
                    if (bubbleTabItemLayout != null) {
                        bubbleTabItemLayout.setBubbleText(String.valueOf(text));
                    }
                }
            });
        } else {
            if (bubbleTabItemLayout != null) {
                bubbleTabItemLayout.setBubbleText(String.valueOf(text));
            }


        }


    }

    /**
     * 设置拖动事件
     *
     * @param index
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBubbleDragListener(final int index, final BubbleTabItemLayout.DragBubbleListener dragBubbleListener) {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                BubbleTabItemLayout bubbleTabItemLayout = (BubbleTabItemLayout) getChildAt(index);
                if (bubbleTabItemLayout != null) {
                    bubbleTabItemLayout.setDragBubbleListener(dragBubbleListener);
                }
            }
        });

    }

    /**
     * 隐藏bubble的数量
     *
     * @param index
     */
    public void dissmisBubble(int index) {
        BubbleTabItemLayout bubbleTabItemLayout = (BubbleTabItemLayout) getChildAt(index);
        bubbleTabItemLayout.dismissBubble();
    }


    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setSelectValidator(ListenerValidator selectValidator) {
        this.selectValidator = selectValidator;
    }

}
