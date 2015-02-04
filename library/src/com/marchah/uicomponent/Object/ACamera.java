package com.marchah.uicomponent.Object;

import android.app.Activity;
import android.os.Build;
import android.view.SurfaceHolder;

/**
 * Created by marcha on 04/02/15.
 */
public abstract class ACamera implements ICamera {

    protected int currentCameraId;

    public int getCurrentCameraId() {return currentCameraId;}

    public static ICamera getCameraInstance(Activity activity, SurfaceHolder holder){
        ICamera camera = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD)
            throw new UnsupportedOperationException("ICamera isn't supported under API 9.");
        else
            camera = CameraOld.getCameraInstance(activity, holder);
        return camera;
    }
}
