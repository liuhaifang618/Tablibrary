package com.safewaychina.tabmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Looper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;



public class TabTextView extends View {

    private static final int DEFAULT_FILTER_COLOR = 0xFF45C01A;
    private static final float DEFAULT_TEXT_SIZE = 12f;

    private TextPaint mTextPaint;
    private String mText = "";
    private int textDefaultColor;
    private int textSelectColor;
    private float mTextSize;
    private Point mPoint;
    private float mAlpha = 0f;
    private Rect mTextBound = new Rect();
    private boolean isSlect;


    public TabTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public TabTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TabTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        textSelectColor = DEFAULT_FILTER_COLOR;
        textDefaultColor = Color.BLACK;
        mTextSize =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE,getResources().getDisplayMetrics());
        if (attrs != null) {
            final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.tab_view, defStyle, 0);
            textDefaultColor = a.getColor(R.styleable.tab_view_tab_text_default_color, textDefaultColor);
            textSelectColor = a.getColor(R.styleable.tab_view_tab_text_select_color, textSelectColor);
            mTextSize =a.getDimension(R.styleable.tab_view_tab_text_size,mTextSize);
            a.recycle();
        }
        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(textDefaultColor);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(mTextBound.width()+2, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mTextBound.height()+2, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(textDefaultColor);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        mPoint = new Point(getMeasuredWidth() / 2 - mTextBound.width() / 2, baseline);

        int alpha = (int) Math.ceil((255 * mAlpha));
        drawHideText(canvas, alpha);
        drawShowetText(canvas, alpha);
    }

    private void drawHideText(Canvas canvas, int alpha) {
        mTextPaint.setAlpha(255 - alpha);
        canvas.drawText(mText, mPoint.x, mPoint.y, mTextPaint);
    }

    private void drawShowetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(textSelectColor);
        mTextPaint.setAlpha(alpha);
        canvas.drawText(mText, mPoint.x,mPoint.y,mTextPaint);

    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public String getText() {
        return mText;
    }

    public  void setText(String text){
        this.mText = text;
        invalidateView();
    }

    public void setTextAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    public  void setIsSelect(boolean isSelect) {
        this.isSlect = isSelect;
        if(isSelect)
            mAlpha=1.0f;
        else
            mAlpha=0.0f;
        invalidateView();
    }

    public float getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getTextDefaultColor() {
        return textDefaultColor;
    }

    public void setTextDefaultColor(int textDefaultColor) {
        this.textDefaultColor = textDefaultColor;
    }

    public int getTextSelectColor() {
        return textSelectColor;
    }

    public void setTextSelectColor(int textSelectColor) {
        this.textSelectColor = textSelectColor;
    }
}
