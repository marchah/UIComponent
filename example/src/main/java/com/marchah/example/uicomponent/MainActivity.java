package com.marchah.example.uicomponent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SelectableTypeEditText(View view) {
        startActivity(new Intent(this, SelectableTypeEditText_Demo_Activity.class));
    }

    public void SelectableTypeEditTextPanel(View view) {
        startActivity(new Intent(this, SelectableTypeEditTextPanel_Demo_Activity.class));
    }

    public void DateTimePicker(View view) {
        startActivity(new Intent(this, DateTimePicker_Demo_Activity.class));
    }

    public void DateTimePickerDialog(View view) {
        startActivity(new Intent(this, DateTimePickerDialog_Demo_Activity.class));
    }

    public void OrientableTextView(View view) {
        startActivity(new Intent(this, OrientableTextView_Demo_Activity.class));
    }

    public void CameraView(View view) {
        startActivity(new Intent(this, CameraView_Demo_Activity.class));
    }

}
