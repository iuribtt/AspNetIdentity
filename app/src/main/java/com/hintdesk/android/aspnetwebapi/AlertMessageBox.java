package com.hintdesk.android.aspnetwebapi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ServusKevin on 07.10.2017.
 */

public class AlertMessageBox {
    public enum AlertMessageBoxIcon {
        Error,
        Info,


    }

    public static void Show(Context context, String title, String message, AlertMessageBoxIcon alertMessageBoxIcon) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        switch (alertMessageBoxIcon) {
            case Error:
                alertDialog.setIcon(R.drawable.error);
                break;
            case Info:
                alertDialog.setIcon(R.drawable.info);
                break;
        }
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }

    public static void Show(Context context, String title, String message, AlertMessageBoxIcon alertMessageBoxIcon, final AlertMessageBoxOkClickCallback okClickCallback) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        switch (alertMessageBoxIcon) {
            case Error:
                alertDialog.setIcon(R.drawable.error);
                break;
            case Info:
                alertDialog.setIcon(R.drawable.info);
                break;
        }
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                okClickCallback.run();
            }
        });

        alertDialog.show();
    }
}