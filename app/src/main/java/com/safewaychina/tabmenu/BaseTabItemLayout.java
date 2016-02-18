package com.safewaychina.tabmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;


public class BaseTabItemLayout extends LinearLayout {

    private static final int DEFAULT_SCOPE = 2;
    private static final int DEFAULT_FILTER_COLOR = 0xFF45C01A;
    private static final float DEFAULT_TEXT_SIZE = 12f;
    private static final int MODE_SELECT = 0;
    private static final int MODE_COLOR_FILTER = 1;
    private static final int DEFAULT_TAB_SLIDING_MODE = 1;


    private int textDefaultColor;
    private int textSelectColor;
    private String tabText;
    private float textSize;
    private Drawable imageBg;
    private float scope;
    private boolean isSelect;
    private float mAlpha = 0f;
    private int tabSlidingMode;

    private TabImageView iconView;
    private TabTextView textView;
    private boolean isClickAble;

    public BaseTabItemLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public BaseTabItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BaseTabItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        setWillNotDraw(false);
        textDefaultColor = Color.BLACK;
        scope = DEFAULT_SCOPE;
        textSelectColor = DEFAULT_FILTER_COLOR;
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE,getResources().getDisplayMetrics());;
        tabSlidingMode = DEFAULT_TAB_SLIDING_MODE;

        if (attrs != null) {
            final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.tab_view, defStyle, 0);
            textDefaultColor = a.getColor(R.styleable.tab_view_tab_text_default_color, textDefaultColor);
            imageBg = a.getDrawable(R.styleable.tab_view_tab_image_bg);
            scope = a.getDimension(R.styleable.tab_view_tab_scope, scope);
            textSelectColor = a.getColor(R.styleable.tab_view_tab_text_select_color, textSelectColor);
            tabSlidingMode = getSlidingMode(a.getInt(R.styleable.tab_view_tab_silidingMode, tabSlidingMode));
            tabText = a.getString(R.styleable.tab_view_tab_scope);
            textSize =a.getDimension(R.styleable.tab_view_tab_text_size, textSize);
            a.recycle();
        }


        initLayout(attrs);
    }

    private int getSlidingMode(int mode){
        switch (mode) {
            case 0:
                return MODE_SELECT;
            case 1:
                return MODE_COLOR_FILTER;
            default:
                throw new IllegalArgumentException("Unknown id: " + mode);
        }
    }



    public void setAlpha(float alpha) {
        this.mAlpha = alpha;
        textView.setTextAlpha(alpha);
        iconView.setIconAlpha(alpha);
    }



    public  Bitmap getCacheBitmap(){
        buildDrawingCache();
        return getDrawingCache();
    }

    private void initLayout(AttributeSet attrs) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        iconView = new TabImageView(getContext(), attrs);
        if (imageBg == null) {
            imageBg = getResources().getDrawable(R.drawable.ic_menu_emoticons);
        }
        iconView.setBackgroundDrawable(imageBg);
        addView(iconView);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (iconView != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            meansureIconView(width,height);
        }
        if (textView != null) {
            meansureTextView();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void meansureIconView(int w, int h) {
        LinearLayout.LayoutParams iconParams = (LinearLayout.LayoutParams) iconView.getLayoutParams();
        int max = Math.min(w, h);
        iconParams.width = (int) (max * (textView != null ? 0.5f : 0.7f));
        iconParams.height = (int) (max * (textView != null ? 0.5f : 0.7f));
        iconParams.gravity = Gravity.CENTER;
        iconView.setLayoutParams(iconParams);
    }
    private void meansureTextView(){
        LinearLayout.LayoutParams textParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        textParams.gravity = Gravity.CENTER;
        textParams.setMargins(0, (int) scope, 0, 0);
        textView.setLayoutParams(textParams);
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
        switch (tabSlidingMode) {
            case MODE_SELECT:
                iconView.setIconSelect(isSelect);
                break;
            case MODE_COLOR_FILTER:
                if (isSelect)
                    mAlpha = 1.0f;
                else
                    mAlpha = 0.0f;
                break;
        }
        changeLayoutStates(isSelect);
    }




    private void changeLayoutStates(boolean isSelect) {
        if (textView != null) {
            textView.setIsSelect(isSelect);
        }
        iconView.setIconSelect(isSelect);
    }

    private int px2dip(int pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public int getTextSelectColor() {
        return textSelectColor;
    }

    public void setTextSelectColor(int textSelectColor) {
        this.textSelectColor = textSelectColor;
        if(isSelect){
//            textView.setTextColor(textSelectColor);
        }
    }

    public int getTextDefaultColor() {
        return textDefaultColor;
    }

    public void setTextDefaultColor(int textDefaultColor) {
        this.textDefaultColor = textDefaultColor;
        if(!isSelect){
//            textView.setTextColor(textDefaultColor);
        }
    }

    public String getTabText() {
        return tabText;
    }

    public void setTabText(String tabText) {
        this.tabText = tabText;
        if (!TextUtils.isEmpty(tabText) ) {
            if ( textView == null){
                textView = new TabTextView(getContext(), null);
                addView(textView);
            }
            textView.setText(tabText);
        }
    }

    public Drawable getImageBg() {
        return imageBg;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
//        textView.setTextSize(textSize);
    }

    public void setImageBg(int imageBg) {
        this.imageBg = getResources().getDrawable(imageBg);
        iconView.setBackgroundResource(imageBg);
    }

    public float getScope() {
        return scope;
    }

    public void setScope(float scope) {
        this.scope = scope;
        if (textView != null) {
            meansureTextView();
        }
    }
    public TabImageView getIconView() {
        return iconView;
    }

    public void setIconView(TabImageView iconView) {
        this.iconView = iconView;
    }

    public TabTextView getTextView() {
        return textView;
    }

    public int getTabSlidingMode() {
        return tabSlidingMode;
    }

    public void setTabSlidingMode(int tabSlidingMode) {
        this.tabSlidingMode = tabSlidingMode;
    }

    public boolean isClickAble() {
        return isClickAble;
    }

    public void setClickAble(boolean isClickAble) {
        this.isClickAble = isClickAble;
    }
}
