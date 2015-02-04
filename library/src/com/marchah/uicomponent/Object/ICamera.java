package com.marchah.uicomponent.Object;

import com.marchah.uicomponent.Listener.CameraListener;

import java.io.IOException;

/**
 * Created by marcha on 04/02/15.
 */
public interface ICamera {

    public int getCurrentCameraId();

    public void startPreview() throws IOException;
    public void stopPreview();
    public void release();
    public void setCameraDisplayOrientation();
    public void switchCamera() throws Exception;
    public void setCurrentCamera(int currentCameraId) throws Exception;
    public void takePicture(CameraListener listener);
    public boolean isZoomSupported();
    public int getMaxZoom();
    public void setZoom(int zoomLevel);
}
