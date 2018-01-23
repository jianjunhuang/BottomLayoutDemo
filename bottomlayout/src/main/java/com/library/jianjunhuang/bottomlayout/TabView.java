package com.library.jianjunhuang.bottomlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by jianjunhuang on 18-1-23.
 */

public class TabView extends View {

    private int imgId;
    private int selectedImgId;

    private String text;

    private Paint imgPaint;
    private Paint textPaint;

    private Bitmap mBitmap;
    private Bitmap selectedBitmap;

    private float imgWidth;
    private float imgHeight;

    private int textColor;
    private int textSelectedColor;

    private float textSize;

    private RectF imgRect;
    private Rect textRect = new Rect();

    private float mAlpha = 0.0f;

    private int position;

    private float textPadding;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TabView);
        if (typedArray != null) {
            text = typedArray.getText(R.styleable.TabView_text).toString();
            imgId = typedArray.getResourceId(R.styleable.TabView_img, -1);
            selectedImgId = typedArray.getResourceId(R.styleable.TabView_selectedImg, -1);
            imgWidth = typedArray.getDimension(R.styleable.TabView_imgWidth, dpToPx(getContext(), 32));
            imgHeight = typedArray.getDimension(R.styleable.TabView_imgHeight, dpToPx(getContext(), 32));
            textColor = typedArray.getColor(R.styleable.TabView_textColor, Color.DKGRAY);
            textSelectedColor = typedArray.getColor(R.styleable.TabView_selectedTextColor, Color.BLACK);
            textSize = typedArray.getDimension(R.styleable.TabView_textSize, dpToPx(getContext(), 15));
            typedArray.recycle();
        }

        imgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.MONOSPACE);

        if (imgId != -1) {
            mBitmap = BitmapFactory.decodeResource(getResources(), imgId);
        }
        if (selectedImgId != -1) {
            selectedBitmap = BitmapFactory.decodeResource(getResources(), selectedImgId);
        }

        textPaint.getTextBounds(text, 0, text.length(), textRect);

        textPadding = dpToPx(getContext(), 3);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public float getAlpha() {
        return mAlpha;
    }

    public void setAlpha(float mAlpha) {
        this.mAlpha = mAlpha;
        notifyDataChange();
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId, int selectedImgId) {
        this.imgId = imgId;
        this.selectedImgId = selectedImgId;
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        mBitmap = BitmapFactory.decodeResource(getResources(), imgId);
        if (selectedBitmap != null) {
            selectedBitmap.recycle();
        }
        selectedBitmap = BitmapFactory.decodeResource(getResources(), selectedImgId);
        notifyDataChange();
    }

    public int getSelectedImgId() {
        return selectedImgId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setText(int textId) {
        this.text = getContext().getResources().getString(textId);
    }

    public float getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(float imgHeight) {
        this.imgHeight = imgHeight;
        notifyDataChange();
    }

    public float getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(float imgWidth) {
        this.imgWidth = imgWidth;
        notifyDataChange();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        notifyDataChange();
    }

    public int getTextSelectedColor() {
        return textSelectedColor;
    }

    public void setTextSelectedColor(int textSelectedColor) {
        this.textSelectedColor = textSelectedColor;
        notifyDataChange();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        notifyDataChange();
    }

    private void notifyDataChange() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            //draw in ui thread
            invalidate();
        } else {
            //draw in no ui thread
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float bitmapWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();

        float bitmapHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - textRect.height() - textPadding;

        if (bitmapHeight < bitmapWidth) {
            bitmapWidth = mBitmap.getWidth() * bitmapHeight / mBitmap.getHeight();
        } else {
            bitmapHeight = mBitmap.getHeight() * bitmapWidth / mBitmap.getWidth();
        }

        float left = getMeasuredWidth() / 2 - bitmapWidth / 2;
        float top = (getMeasuredHeight() - textRect.height() - textPadding) / 2 - bitmapHeight / 2;

        imgRect = new RectF(left, top, left + bitmapWidth, top + bitmapHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int alpha = (int) Math.ceil(255 * mAlpha);
        drawSourceText(canvas, alpha);
        drawSelectedText(canvas, alpha);
        drawSourceBitmap(canvas, alpha);
        drawSelectedBitmap(canvas, alpha);
    }

    private void drawSourceText(Canvas canvas, int alpha) {
        textPaint.setColor(textColor);
        textPaint.setAlpha(255 - alpha);
        canvas.drawText(text, getWidth() / 2 - textPaint.measureText(text) / 2,
                imgRect.bottom + textRect.height() + textPadding, textPaint);
    }

    private void drawSelectedText(Canvas canvas, int alpha) {
        textPaint.setColor(textSelectedColor);
        textPaint.setAlpha(alpha);
        canvas.drawText(text, getWidth() / 2 - textPaint.measureText(text) / 2,
                imgRect.bottom + textRect.height() + textPadding, textPaint);
    }

    private void drawSourceBitmap(Canvas canvas, int alpha) {
        if (mBitmap != null) {
            imgPaint.setAlpha(255 - alpha);
            canvas.drawBitmap(mBitmap, null, imgRect, imgPaint);
        }
    }

    private void drawSelectedBitmap(Canvas canvas, int alpha) {
        if (selectedBitmap != null) {
            imgPaint.setAlpha(alpha);
            canvas.drawBitmap(selectedBitmap, null, imgRect, imgPaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        if (selectedBitmap != null) {
            selectedBitmap.recycle();
        }
    }

    public static float dpToPx(Context context, float dp) {
        return dp * (getDisplayMetrics(context).densityDpi / 160F);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics;
    }
}
