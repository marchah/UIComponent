package com.marchah.uicomponent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AndroidException;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ZoomControls;

import com.marchah.uicomponent.Listener.CameraListener;

/**
 * Created by marcha on 03/02/15.
 */
public class CameraView  extends FrameLayout {

    private CameraPreview mPreview;

    public CameraView(Context context) {
        super(context);

        init(null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    private void init(AttributeSet attrs)  {

    }

    public void attachCamera(Activity activity) throws AndroidException {
        removeAllViews();

        mPreview = new CameraPreview(activity);
        addView(mPreview);
    }

    public void switchCamera() throws Exception {
        mPreview.switchCamera();
    }

    public void takePicture(CameraListener listener) {
        mPreview.takePicture(listener);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("cameraId", mPreview.getCurrentCameraId());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState (Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            try {
                mPreview.setCurrentCamera(bundle.getInt("cameraId"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }
}
