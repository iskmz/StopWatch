package com.iskandar.android.stopwatch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.FitWindowsLinearLayout;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


public class UtilsHiddenTaps {

    Context context;
    private int creditsCounter;
    private View view;

    private final int TAP_TARGET=11;
    private final String DIALOG_MSG_Title = "1011 1011 1011010";
    private final String DIALOG_MSG_TXT = "Iskandar Mazzawi \u00a9";
    private final String SNACKBAR_ACTION_TXT =  "??!?!?!";
    private final String TOAST_MSG_TXT = "Keep Tapping";

    public UtilsHiddenTaps(Context context,View v)  // c'tor
    {
        this.context = context;
        this.creditsCounter = 0;
        this.view = v;

        // to make snackbar LTR direction by FORCE //
        ((MainActivity)context).getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        ((MainActivity)context).getWindow().getDecorView().setTextDirection(View.TEXT_DIRECTION_LTR);
    }

    public void initializeCounter() // to put inside other onClickListeners to initialize ! //
    {
        this.creditsCounter = 0;
    }


    public void creditsOnClick() // on each desired click: displays msg & increments counter until TARGET is reached
    {
        creditsCounter+=1;

        Snackbar.make(view,"Tap Number "+creditsCounter,Snackbar.LENGTH_LONG)
                .setAction(SNACKBAR_ACTION_TXT, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, TOAST_MSG_TXT, Toast.LENGTH_SHORT).show();
                    }
                })
                .setActionTextColor(Color.GREEN)
                .setDuration(1500)
                .show();

        if(creditsCounter==TAP_TARGET)
        {
            genCreditsDialog();
            initializeCounter();
        }
    }


    public void creditsOnClick(long specialKey)
    {
        Long s = Long.parseLong(DIALOG_MSG_Title.replace(" ","").substring(0,8));
        String ss = Long.toHexString(s).toUpperCase();
        String sss = Long.toHexString(specialKey).toUpperCase();
        if(ss.equals(sss))
        {
            genCreditsDialog();
            initializeCounter();
        }
    }

    private void genCreditsDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        AlertDialog alert = builder.setTitle(DIALOG_MSG_Title)
                                    .setMessage(DIALOG_MSG_TXT)
                                    .setIcon(R.drawable.title_alternate)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create();

        alert.setCanceledOnTouchOutside(false);

        alert.show();
    }
}
