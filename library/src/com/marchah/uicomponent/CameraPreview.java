package com.marchah.uicomponent;

import android.app.Activity;
import android.os.Build;
import android.util.AndroidException;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.marchah.uicomponent.Listener.CameraListener;
import com.marchah.uicomponent.Object.ACamera;
import com.marchah.uicomponent.Object.ICamera;
import com.marchah.uicomponent.Tools.Utils;

import java.io.IOException;

/**
 * Created by marcha on 03/02/15.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private ICamera mCamera;

    private int currentZoomLevel;
    private final int maxZoomLevel;

    public CameraPreview(Activity activity) throws AndroidException {
        super(activity);

        if (!Utils.hasCameraHardware(getContext()))
            throw new AndroidException("No Camera found on the device");

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        if ((mCamera = ACamera.getCameraInstance(activity, mHolder)) == null)
            throw new AndroidException("Impossible to request camera access");

        currentZoomLevel = 0;

        if (mCamera.isZoomSupported())
            maxZoomLevel = mCamera.getMaxZoom();
        else
            maxZoomLevel = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "mode=DRAG");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_MOVE:

                if (mode == ZOOM) {
                    float newDist = spacing(event);
                    float scale = newDist - oldDist;

                    if (scale > 10) {
                        if(currentZoomLevel < maxZoomLevel) {
                            currentZoomLevel++;
                            mCamera.setZoom(currentZoomLevel);
                        }
                        oldDist = newDist;
                    }
                    else if (scale < -10) {
                        if(currentZoomLevel > 0){
                            currentZoomLevel--;
                            mCamera.setZoom(currentZoomLevel);
                        }
                        oldDist = newDist;
                    }
                }
                break;
        }
        return true;
    }

    private static final String TAG = "Touch";


    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    float oldDist = 1f;


    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    public void switchCamera() throws Exception {
        mCamera.switchCamera();
    }

    public int getCurrentCameraId() {
        return mCamera.getCurrentCameraId();
    }

    public void setCurrentCamera(int currentCameraId) throws Exception {
        mCamera.setCurrentCamera(currentCameraId);
    }

    public void takePicture(CameraListener listener) {
        mCamera.takePicture(listener);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewSize(getWidth(), getHeight());
            mCamera.setCameraDisplayOrientation();
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("Debug", "Error setting camera preview: " + e.getMessage());
        }
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.release();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.startPreview();

        } catch (Exception e){
            Log.d("Debug", "Error starting camera preview: " + e.getMessage());
        }
    }
}
