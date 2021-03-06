package com.jtna.holyshift;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Nishad Agrawal on 12/26/14.
 */
public class CreateGroupDialogFragment extends DialogFragment {

    private DialogListener myListener;
    private EditText groupNameEditText;
    private EditText passwordEditText;

    public void setDialogListener(DialogListener listener) {
        myListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        View myView = inflater.inflate(R.layout.dialog_create_group, null);
        groupNameEditText = (EditText) myView.findViewById(R.id.group_name_edit_text);
        passwordEditText = (EditText) myView.findViewById(R.id.group_password_edit_text);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(myView)
                // Add action buttons
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        myListener.onDialogPositiveClick(CreateGroupDialogFragment.this);
                        CreateGroupDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myListener.onDialogNegativeClick(CreateGroupDialogFragment.this);
                        CreateGroupDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public EditText getGroupNameEditText() {
        return groupNameEditText;
    }

    public EditText getPasswordEditText() {
        return passwordEditText;
    }
}
