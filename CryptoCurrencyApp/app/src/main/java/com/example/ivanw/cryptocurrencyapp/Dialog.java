package com.example.ivanw.cryptocurrencyapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class Dialog extends AppCompatDialogFragment {

    String DialogTitle="";
    String DialogBody="";

    public void setTitleBody(String DialogTitle, String DialogBody)
    {
        this.DialogTitle = DialogTitle;
        this.DialogBody = DialogBody;
    }



    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        TextView DH = view.findViewById(R.id.informationdialog_heading);
        TextView DB = view.findViewById(R.id.informationdialog_body);

        DH.setText(DialogTitle);
        DB.setText(DialogBody);

        builder.setView(view)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }


                });


        return builder.create();


    }
}
