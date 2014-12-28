package com.jtna.holyshift;

import android.support.v4.app.DialogFragment;

/**
 * Created by Nishad Agrawal on 12/27/14.
 */
public interface DialogListener {
    public void onDialogPositiveClick(DialogFragment dialog);
    public void onDialogNegativeClick(DialogFragment dialog);
}
