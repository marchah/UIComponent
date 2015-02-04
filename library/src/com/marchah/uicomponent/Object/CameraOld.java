package com.marchah.uicomponent.Object;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.marchah.uicomponent.Listener.CameraListener;

import java.io.IOException;

/**
 * Created by marcha on 04/02/15.
 */
public class CameraOld extends ACamera {

    private Camera mCamera;
    private Activity activity;
    private SurfaceHolder holder;


    private CameraOld(Activity activity, SurfaceHolder holder) throws Exception {
        this.activity = activity;
        this.holder = holder;
        currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        mCamera = null;
        mCamera = Camera.open(currentCameraId);
    }

    public void startPreview() throws IOException {
        if (mCamera != null) {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }
    }

    public void stopPreview() {
        mCamera.stopPreview();
    }

    public void release() {
        if (mCamera != null)
            mCamera.release();
    }

    private int getPictureRotation() {
        // TODO: in landscape: one orientation ins't good
        // http://stackoverflow.com/questions/4697631/android-screen-orientation
        // http://developer.android.com/reference/android/hardware/Camera.html#setDisplayOrientation%28int%29

        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(currentCameraId, info);
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    public void setCameraDisplayOrientation() {
        if (mCamera != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setRotation(getPictureRotation());
                mCamera.setParameters(parameters);
            } else {
                mCamera.setDisplayOrientation(getPictureRotation());
            }
        }
    }

    public void switchCamera() throws Exception {
        if(currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        else {
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        setCurrentCamera(currentCameraId);
    }

    public void setCurrentCamera(int cameraId) throws Exception {
        if (holder != null) {
            stopPreview();
            release();
            this.currentCameraId = cameraId;
            mCamera = Camera.open(currentCameraId);
            setCameraDisplayOrientation();
            startPreview();
        }
    }

    public boolean isZoomSupported() {
        Camera.Parameters parameters = mCamera.getParameters();
        return parameters.isZoomSupported();
    }

    public int getMaxZoom() {
        Camera.Parameters parameters = mCamera.getParameters();
        return parameters.getMaxZoom();
    }

    public void setZoom(int zoomLevel) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setZoom(zoomLevel);
        mCamera.setParameters(parameters);
    }

    private CameraListener mListener = null;

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            // TODO: When camera front
            // 1) device in portrait mode the picture is upside down
            // 2) mirroring effect
            matrix.postRotate(getPictureRotation());
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            mListener.onPictureTaken(bitmap);
            mListener = null;
            try {
                startPreview();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void takePicture(CameraListener listener) {
        if (mListener == null) {
            mListener = listener;
            mCamera.takePicture(null, null, mPicture);
        }
    }

    public static ICamera getCameraInstance(Activity activity, SurfaceHolder holder){
        CameraOld c = null;
        try {
            c = new CameraOld(activity, holder);
        }
        catch (Exception e){
            e.printStackTrace();
            // TODO: log err
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

}
