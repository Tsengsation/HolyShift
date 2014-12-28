package com.jtna.holyshift;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

/**
 * Created by Nishad Agrawal on 12/26/14.
 */
public class PickerDialogFragment extends DialogFragment {

    private DialogListener myListener;
    NumberPicker numPicker;

    public void setDialogListener(DialogListener listener) {
        myListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        View myView = inflater.inflate(R.layout.dialog_number_required, null);

        numPicker = (NumberPicker) myView.findViewById(R.id.numberPicker);
        numPicker.setMaxValue(100);
        numPicker.setMinValue(1);

        builder.setTitle("Number Required for Shift");

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(myView)
                // Add action buttons
                .setPositiveButton("Assign", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        myListener.onDialogPositiveClick(PickerDialogFragment.this);
                        PickerDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PickerDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public NumberPicker getNumPicker() {
        return numPicker;
    }

}
