package com.marchah.uicomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
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

        /*Animation animation;

        //Initialize the Animation object
        animation = new RotateAnimation(angle, angle, 0, 0);
        //"Save" the results of the animation
        animation.setFillAfter(true);
        //Set the animation duration to zero, just in case
        animation.setDuration(0);
        setAnimation(animation);*/
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

       /* int Ax = getMeasuredWidth();
        int Ay = getMeasuredHeight();
        int A2x = (int)( (Ax * Math.cos(-angle)) + (Ay * Math.sin(-angle)));
        int A2y = (int)(-(Ax * Math.sin(-angle)) + (Ay * Math.cos(-angle)));

        int Bx = getMeasuredWidth();
        int By = 0;
        int B2x = (int)( (Bx * Math.cos(-angle)) + (By * Math.sin(-angle)));
        int B2y = (int)(-(Bx * Math.sin(-angle)) + (By * Math.cos(-angle)));*/

        /*
        ***********************************************
        *  Rotation gravity center
        * *********************************************

        int Ax = getMeasuredWidth() / 2;
        int Ay = getMeasuredHeight() / 2;
        Log.v("Debug", "angle: " + angle + ", initWidth: " + Ax + ", initHeight: " + Ay);
        int A2x = (int)( (Ax * Math.cos(-angle)) + (Ay * Math.sin(-angle)));
        int A2y = (int)(-(Ax * Math.sin(-angle)) + (Ay * Math.cos(-angle)));
        Log.v("Debug", "rotation width: " + A2x + ", rotation height: " + A2y);

        width = Math.abs(A2x) * 2;
        height = Math.abs(A2y) * 2;


        Log.v("Debug", "width: " + width + ", height: " + height);

        super.onMeasure(width, height);
        setMeasuredDimension(width, height);
*/
                /*

                ***********************************************
                *  Rotation single point
                * *********************************************

                int Ax = getMeasuredWidth();
                int Ay = getMeasuredHeight();

                int A2x = (int)( (Ax * Math.cos(-angle)) + (Ay * Math.sin(-angle)));
                int A2y = (int)(-(Ax * Math.sin(-angle)) + (Ay * Math.cos(-angle)));

                width = Math.abs(A2x);
                height = Math.abs(A2y);

                Log.v("Debug", "angle: " + angle + ", initWidth: " + Ax + ", initHeight: " + Ay);
                Log.v("Debug", "width: " + width + ", height: " + height);
*/
                super.onMeasure(width, height);
                setMeasuredDimension(width, height);

                width = getMeasuredWidth();
                height = getMeasuredHeight();
                //Log.v("Debug", "width: " + width + ", height: " + height);
                double radian = Math.toRadians(angle);
                double Ay = Math.cos(radian) * width;

                double Bx = Math.cos(radian) * height;
                double By = Ay + Math.sin(radian) * height;
                //Log.v("Debug", "Bx: " + Bx + ", By: " + By);
                double Cx = Bx + Math.cos(radian) * width;
                double Cy = By + Math.sin(radian) * width;
                //Log.v("Debug", "Cx: " + Cx + ", Cy: " + Cy);
                width = (int)Math.abs(Cx);
                height = (int)Math.abs(Cy);
                //translate = (int)Ay;
                super.onMeasure(width, height);
                setMeasuredDimension(width, height);
                //Log.v("Debug", "angle: " + angle + ", newWidth: " + width + ", newHeight: " + height);
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
        canvas.save();

        //Log.v("Debug", "angle: " + angle + ", width: " + getWidth());

        if (angle == 90)
            canvas.translate(getWidth(), 0);
        else if (angle == -90)
            canvas.translate(0, getHeight());
        else if (Math.abs(angle) == 180)
            canvas.translate(getWidth(), getHeight());
        else if (angle < 0)
            canvas.translate(0, getHeight());
        /*else if (angle < 0)
            canvas.translate(0, height);*/

        canvas.rotate(angle);

        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
        getLayout().draw(canvas);
        canvas.restore();
    }
}