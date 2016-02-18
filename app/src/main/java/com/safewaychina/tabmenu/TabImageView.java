package com.safewaychina.tabmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class TabImageView extends ImageView {

    private static final int DEFAULT_TAB_SLIDING_MODE = 1;
    private static final int DEFAULT_FILTER_COLOR = 0xFF45C01A;
    private static final int MODE_SELECT = 0;
    private static final int MODE_COLOR_FILTER = 1;
    private static final int[] TAB_SELECT_TRUE = {R.attr.tab_select};

    private boolean isSelect;
    private int tabSlidingMode;
    private int tabFilterColor;
    private float mAlpha = 0f;

    public TabImageView(Context context) {
        super(context);
        init(null, 0);
    }

    public TabImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TabImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        setScaleType(ScaleType.CENTER_CROP);
        tabSlidingMode = DEFAULT_TAB_SLIDING_MODE;
        tabFilterColor = DEFAULT_FILTER_COLOR;

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.tab_view, defStyle, 0);
            tabSlidingMode = getSlidingMode(a.getInt(R.styleable.tab_view_tab_silidingMode, tabSlidingMode));
            tabFilterColor = a.getColor(R.styleable.tab_view_tab_filter_color, tabFilterColor);
            a.recycle();
        }


    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = setBitmapColorFilter();
        canvas.drawBitmap(bitmap, 0, 0, new Paint());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setIconAlpha(float alpha) {
        if (tabSlidingMode == MODE_COLOR_FILTER) {
            this.mAlpha = alpha;
            invalidateView();
        }
    }

    /**
     * �����Ƿ�ѡ��
     *
     * @param isSelect
     */
    public void setIconSelect(final boolean isSelect) {
        if (this.isSelect != isSelect) {
            this.isSelect = isSelect;
            switch (tabSlidingMode) {
                case MODE_SELECT:
                    refreshDrawableState();
                    break;
                case MODE_COLOR_FILTER:
                    if (isSelect)
                        mAlpha = 1.0f;
                    else
                        mAlpha = 0.0f;
                    invalidateView();
                    break;
            }

        }
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    private Bitmap setBitmapColorFilter() {
        Bitmap mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();
        mPaint.setColor(tabFilterColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha((int) Math.ceil((255 * mAlpha)));
        mCanvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        Bitmap bitmap = getCacheBitmap();
        mCanvas.drawBitmap(bitmap, 0, 0, mPaint);
//        setImageBitmap(mBitmap);
        return mBitmap;
    }


    public Bitmap getCacheBitmap() {
        //setDrawingCacheEnabled(true);
//        buildDrawingCache();
//        return getDrawingCache();

        return drawableToBitmap(getBackground());
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(/*drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()*/getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isSelect && tabSlidingMode == MODE_SELECT) {
            mergeDrawableStates(drawableState, TAB_SELECT_TRUE);
        }
        return drawableState;
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

    public int getTabSlidingMode() {
        return tabSlidingMode;
    }

    public void setTabSlidingMode(int tabSlidingMode) {
        this.tabSlidingMode = tabSlidingMode;
    }

    public int getTabFilterColor() {
        return tabFilterColor;
    }

    public void setTabFilterColor(int tabFilterColor) {
        this.tabFilterColor = tabFilterColor;
    }

    /**
     * @author Anders
     * @Title: ����״̬.
     */
    static class SaveState extends BaseSavedState {
        boolean isSelect;

        public SaveState(Parcel in) {
            super(in);
            isSelect = (Boolean) in.readValue(null);
        }

        public SaveState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeValue(isSelect);
        }

        public static final Parcelable.Creator<SaveState> CREATOR = new Creator<TabImageView.SaveState>() {

            @Override
            public SaveState[] newArray(int size) {
                return new SaveState[size];
            }

            @Override
            public SaveState createFromParcel(Parcel source) {
                return createFromParcel(source);
            }
        };
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superParcelable = super.onSaveInstanceState();
        SaveState ss = new SaveState(superParcelable);
        ss.isSelect = isSelected();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SaveState ss = (SaveState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setIconSelect(ss.isSelect);
    }


}
