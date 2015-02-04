package com.marchah.example.uicomponent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.marchah.uicomponent.CameraView;
import com.marchah.uicomponent.Listener.CameraListener;

/**
 * Created by marcha on 28/01/15.
 */
public class CameraView_Demo_Activity extends Activity {

    CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameraview_demo);
        cameraView = (CameraView)findViewById(R.id.basicCV);
        try {
            cameraView.attachCamera(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SwitchCamera(View view) {
        try {
            cameraView.switchCamera();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TakePicture(View view) {
        cameraView.takePicture(new CameraListener() {
            @Override
            public void onPictureTaken(Bitmap picture) {
                ((ImageView)findViewById(R.id.pictureIV)).setImageBitmap(picture);
            }
        });
    }

    private boolean isFullScreen = false;
    public void ResizePicture(View v) {
        if (isFullScreen) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(dpToPx(60), dpToPx(60));
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp.setMargins(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
            findViewById(R.id.pictureIV).setLayoutParams(lp);
            isFullScreen = false;
            findViewById(R.id.switchCameraB).setVisibility(View.VISIBLE);
            findViewById(R.id.takePictureB).setVisibility(View.VISIBLE);
            findViewById(R.id.closeFullScreenB).setVisibility(View.GONE);
        }
        else {
            findViewById(R.id.pictureIV).setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            isFullScreen = true;
            findViewById(R.id.switchCameraB).setVisibility(View.INVISIBLE);
            findViewById(R.id.takePictureB).setVisibility(View.INVISIBLE);
            findViewById(R.id.closeFullScreenB).setVisibility(View.VISIBLE);
        }
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
