package com.marchah.uicomponent.Tools;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by marcha on 03/02/15.
 */
public class Utils {

    public static boolean hasCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
}
