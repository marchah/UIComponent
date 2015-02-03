package com.marchah.example.uicomponent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.marchah.uicomponent.DateTimePickerDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by marcha on 28/01/15.
 */
public class DateTimePickerDialog_Demo_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetimepickerdialog_demo);
    }

    public void DateTimePicker1(final View view) {

        final DateTimePickerDialog mDateTimeDialog = new DateTimePickerDialog(this);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2000, Calendar.DECEMBER, 31, 23, 59);
        Date date = cal.getTime();

        mDateTimeDialog.setIs24HourView(true);
        mDateTimeDialog.setDateTime(date);

        mDateTimeDialog.setOnCancelClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DateTimePickerDialog_Demo_Activity.this);
                builder.setTitle("Cancellation")
                        .setMessage("Are you sure to cancel?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDateTimeDialog.cancel();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
            }
        });

        mDateTimeDialog.setOnValideClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String dateTime = mDateTimeDialog.getDateTime().toString();
                ((TextView)findViewById(R.id.basicTV)).setText(dateTime);
                mDateTimeDialog.cancel();
            }
        });

        mDateTimeDialog.show();
    }
}
