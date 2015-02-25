package com.marchah.uicomponent;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.MissingResourceException;

/**
 * Created by marcha on 09/02/15.
 */
public class ImagePreview extends ImageView implements View.OnClickListener {

    private boolean isFullScreen;

    private float width;
    private float height;

    private Drawable thumb;
    private Drawable image;

    public ImagePreview(Context context) {
        super(context);

        init(null);
    }

    public ImagePreview(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setId(R.id.uic_imagepreview);
        setOnClickListener(this);
        isFullScreen = false;

        thumb = null;
        image = null;
        if (attrs != null)
            setAttrs(attrs);
        if (thumb == null || image == null)
            throw new MissingResourceException("Thumb or/and image missing", getClass().getName(), "");
        setImageDrawable(thumb);
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.uic_stylable);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.uic_stylable_thumb) {
                thumb = a.getDrawable(attr);
            }
            else if (attr == R.styleable.uic_stylable_image) {
                image = a.getDrawable(attr);
            }
           /* if (attr == R.styleable.uic_stylable_android_layout_height) {
                hasHeight = true;
                //try {
                    height = a.getDimension(attr, -1);
                //} catch (Exception e) {
                  //  hasHeight = false;
                //}
            }
            else if (attr == R.styleable.uic_stylable_android_layout_width) {
                hasWidth = true;
                //try {
                    width = a.getDimension(attr, -1);
                //} catch (Exception e) {
                  //  hasWidth = false;
                //}

            }*/
        }
        a.recycle();
    }

    @Override
    public void onClick(View v) {
        if (getId() == R.id.uic_imagepreview) {
            if (isFullScreen) {
                v.setLayoutParams(new LinearLayout.LayoutParams((int)width, (int)height));
                isFullScreen = false;
                setImageDrawable(thumb);
            }
            else {
                height = getHeight();
                width = getWidth();
                v.bringToFront();
                WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)//HONEYCOMB_MR2)
                    v.setLayoutParams(new LinearLayout.LayoutParams(display.getWidth(), display.getHeight()));
                else {
                    Point size = new Point();
                    display.getRealSize(size);
                    v.setLayoutParams(new LinearLayout.LayoutParams(size.x, size.y));
                }
                isFullScreen = true;
                setImageDrawable(image);
            }
        }
    }
}
