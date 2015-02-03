package com.marchah.uicomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

import android.widget.TextView;

/**
 * Created by marcha on 03/02/15.
 */
public class OrientableTextView extends TextView {
    int angle;

    public OrientableTextView(Context context) {
        super(context);

        init(null);
    }

    public OrientableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    private void init(AttributeSet attrs) {
        angle = 0;

        if (attrs != null)
            setAttrs(attrs);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
        translate = getWidth();
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.uic_stylable);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.uic_stylable_angle) {
                angle = a.getInt(R.styleable.uic_stylable_angle, 0);
            }

        }
        a.recycle();
    }

    private int width;
    private int height;
    private int translate;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){

        switch (angle) {
            case 0:
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                break;
            case 90:
                super.onMeasure(heightMeasureSpec, widthMeasureSpec);
                setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
                break;
            case -90:
                super.onMeasure(heightMeasureSpec, widthMeasureSpec);
                setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
                break;
            case 180:
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                break;
            case -180:
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                break;
            default:
                Log.w("Warning", " angle != 0 && != 180 && != -180 && != -90 && != 90 experimental");
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);

                width = getMeasuredWidth();
                height = getMeasuredHeight();
                Log.v("Debug", "width: " + width + ", height: " + height);
                double radian = Math.toRadians(angle);
                double Ay = Math.cos(radian) * width;

                double Bx = Math.cos(radian) * height;
                double By = Ay + Math.sin(radian) * height;

                double Cx = Bx + Math.cos(radian) * width;
                double Cy = By + Math.sin(radian) * width;

                double newWidth = Cx;
                double newHeight = Cy;
                //translate = (int)Ay;
                super.onMeasure((int) newWidth, (int) newHeight);
                setMeasuredDimension((int) newWidth, (int) newHeight);
                Log.v("Debug", "newWidth: " + newWidth + ", newHeight: " + newHeight);
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
        canvas.save();

        if (angle == 90)
            canvas.translate(getWidth(), 0);
        else if (angle == -90)
            canvas.translate(0, getHeight());
        else if (Math.abs(angle) == 180)
            canvas.translate(getWidth(), getHeight());
        canvas.rotate(angle);

        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
        getLayout().draw(canvas);
        canvas.restore();
    }
}