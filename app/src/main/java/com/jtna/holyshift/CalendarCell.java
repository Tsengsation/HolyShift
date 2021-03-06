package com.jtna.holyshift;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Jonathan Tseng on 11/16/2014.
 */
public class CalendarCell extends Button {
    private int myDay;
    private int myHour;

    public CalendarCell(Context context, int day, int hour) {
        super(context);
        setText("");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        layoutParams.setMargins(2,2,2,2);
        setLayoutParams(layoutParams);
        myDay = day;
        myHour = hour;
    }

    public int getMyDay() {
        return myDay;
    }

    public int getMyHour() {
        return myHour;
    }

}
