package com.jtna.holyshift;

/**
 * Created by Nishad Agrawal on 12/26/14.
 */
public abstract class CalendarListener {

    /**
     *
     *
     * @param cal
     */
    public abstract void onSaveClicked(CalendarFragment cal);

    public abstract void onCellClicked(CalendarFragment cal, CalendarCell cell);
}
