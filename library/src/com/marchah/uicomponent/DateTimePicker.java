package com.marchah.uicomponent;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by marcha on 02/02/15.
 */
public class DateTimePicker extends LinearLayout implements View.OnClickListener {

    private Button switchBT;
    private DatePicker dateDP;
    private TimePicker timeTP;

    private boolean is24HourView;

    public DateTimePicker(Context context) {
        super(context);
        init(null);
    }

    public DateTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DateTimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.uic_datetimepicker, this);

        switchBT = (Button)findViewById(R.id.uic_switch_date_time_button);
        switchBT.setOnClickListener(this);

        dateDP = (DatePicker)findViewById(R.id.uic_dateDP);
        timeTP = (TimePicker)findViewById(R.id.uic_timeTP);

        is24HourView = false;

        if (attrs != null)
            setAttrs(attrs);

        timeTP.setIs24HourView(is24HourView);
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.uic_stylable);

        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.uic_stylable_is24HourView) {
                String is24HourViewString = a.getString(R.styleable.uic_stylable_is24HourView);
                if (is24HourViewString != null && is24HourViewString.equalsIgnoreCase("true"))
                    is24HourView = true;
            }

        }
        a.recycle();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.uic_switch_date_time_button) {
            if (switchBT.getText().toString().equals(getResources().getString(R.string.uic_date))) {
                switchBT.setText(getResources().getString(R.string.uic_hour));
                timeTP.setVisibility(INVISIBLE);
                dateDP.setVisibility(VISIBLE);
            } else if (switchBT.getText().toString().equals(getResources().getString(R.string.uic_hour))) {
                switchBT.setText(getResources().getString(R.string.uic_date));
                dateDP.setVisibility(INVISIBLE);
                timeTP.setVisibility(VISIBLE);
            }
        }
    }

    public void setDateTime(Date dateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);

        dateDP.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        if (!is24HourView)
            timeTP.setIs24HourView(true);
        timeTP.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
        timeTP.setCurrentMinute(cal.get(Calendar.MINUTE));
        timeTP.setIs24HourView(is24HourView);
    }

    public Date getDateTime() {
        int day = dateDP.getDayOfMonth();
        int month = dateDP.getMonth();
        int year =  dateDP.getYear();

        if (!is24HourView)
            timeTP.setIs24HourView(true);
        int hour = timeTP.getCurrentHour();
        int min = timeTP.getCurrentMinute();
        timeTP.setIs24HourView(is24HourView);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, min);

        return calendar.getTime();
    }

    public void setIs24HourView(boolean is24HourView) {
        this.is24HourView = is24HourView;
        timeTP.setIs24HourView(is24HourView);
    }

    public boolean is24HourView() {
        return is24HourView;
    }
}

