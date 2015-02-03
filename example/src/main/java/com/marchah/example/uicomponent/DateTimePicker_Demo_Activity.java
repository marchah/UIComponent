package com.marchah.example.uicomponent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.marchah.uicomponent.DateTimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by marcha on 28/01/15.
 */
public class DateTimePicker_Demo_Activity extends Activity {

    DateTimePicker basicDTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetimepicker_demo);

        basicDTP = (DateTimePicker)findViewById(R.id.basicDTP);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2000, Calendar.DECEMBER, 31, 23, 59);
        Date date = cal.getTime();

        basicDTP.setDateTime(date);
    }

    public void GetValue(View view) {
        ((TextView)findViewById(R.id.basicTV)).setText("getDateTime()=" + basicDTP.getDateTime().toString() /*+ ", getDate()=" +basicDTP.getDate() + ", getTime()=" + basicDTP.getTime()*/);
    }
}
