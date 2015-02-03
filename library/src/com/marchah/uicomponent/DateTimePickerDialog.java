package com.marchah.uicomponent;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import java.util.Date;

/**
 * Created by marcha on 03/02/15.
 */
public class DateTimePickerDialog extends Dialog{

    DateTimePicker mDateTime;

    public DateTimePickerDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.uic_datetimepicker_dialog);

        mDateTime = ((DateTimePicker)findViewById(R.id.uic_datetimeDTP));
    }


    public void setOnCancelClickListener(View.OnClickListener l) {
        findViewById(R.id.uic_cancelBT).setOnClickListener(l);
    }

    public void setOnValideClickListener(View.OnClickListener l) {
        findViewById(R.id.uic_okBT).setOnClickListener(l);
    }

    public boolean is24HourView() {
        return mDateTime.is24HourView();
    }

    public void setIs24HourView(boolean is24HourView) {
        mDateTime.setIs24HourView(is24HourView);
    }

    public Date getDateTime() {
        return mDateTime.getDateTime();
    }

    public void setDateTime(Date dateTime) {
        mDateTime.setDateTime(dateTime);
    }

}
