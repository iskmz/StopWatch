package com.iskandar.android.stopwatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {


    Context context;
    ImageButton btnFlag,btnPlayPause,btnRestart,btnAbout,btnQuit;
    TextView txtTime, txtLaps;

    final int TICK_TIME_MSEC = TimeDisplayMSec.MSEC_EIGHTH_TICK;
    TimeDisplayMSec stopwatch;
    int lapsCounter;
    boolean playON, cancelAndRestart;

    UtilsHiddenTaps taps;
    LinearLayout layoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPointers();
        setListeners();
    }

    private void setListeners() {

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playON=!playON; // switcher , toggle ... change play / pause status
                cancelAndRestart=false; // a needed fix ! after so many issues & failures ! //
                playPause();
            }

            @SuppressLint("StaticFieldLeak")
            private void playPause() {
                if(playON && !cancelAndRestart)
                {
                    new AsyncTask<TimeDisplayMSec, TimeDisplayMSec, TimeDisplayMSec>() {
                        @Override
                        protected TimeDisplayMSec doInBackground(TimeDisplayMSec... timeDisplays) {
                            while(true)
                            {
                                SystemClock.sleep(TICK_TIME_MSEC);
                                stopwatch.tick();
                                publishProgress(stopwatch);

                                // both of the following lines , will direct to postExecute
                                // for different tasks ! // one for RESET & one for PAUSE //
                                if(cancelAndRestart) break;
                                if(!playON) break;
                            }

                            return stopwatch;
                        }


                        @Override
                        protected void onPostExecute(TimeDisplayMSec timeDisplay) {
                            if(!cancelAndRestart) { // on PAUSE //
                                TimeDisplayMSec tmp = new TimeDisplayMSec(timeDisplay);
                                stopwatch = tmp; // to keep time record when paused //

                            }
                            else // on CANCEL & RESTART //
                            {
                               clearAllTimeFields();
                            }
                            // reset value of booleans
                            cancelAndRestart = false; // make sure it become TRUE on next RESTART click //
                            playON=false; // to make sure it becomes TRUE on next PLAY click //
                        }

                        @Override
                        protected void onProgressUpdate(TimeDisplayMSec... values) {
                            if(!cancelAndRestart) {
                                txtTime.setText(values[0].toString());
                            }
                        }
                    }.execute(stopwatch);
                }
            }
        });

        btnFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!stopwatch.equals(new TimeDisplayMSec(TICK_TIME_MSEC))) {
                    lapsCounter += 1;
                    String previous = txtLaps.getText().toString();
                    txtLaps.setText(new StringBuilder(lapsCounter + " " + stopwatch.toString() +
                            "\n" + previous));
                }
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CANCEL & RESTART option in postExecute //
                cancelAndRestart=true;

                // in case clicked when PAUSED
                // i.e. no access to AsyncTask above ! ~ playOn=false //
                clearAllTimeFields();
            }
        });



        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taps.creditsOnClick();
            }
        });


        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taps.creditsOnClick(10111011);
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stop all first // same as in CANCEL & RESTART button //
                cancelAndRestart=true;
                clearAllTimeFields();
                // now QUIT //
                finish();
                // Good-Bye MSG
                Toast.makeText(context, "Good-Bye!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void clearAllTimeFields() {
        // clear values
        stopwatch = new TimeDisplayMSec(TICK_TIME_MSEC);
        lapsCounter=0;
        // clear fields
        txtLaps.setText("");
        txtTime.setText("00:00:00");
    }


    private void setPointers() {
        this.context = this;
        // buttons
        btnFlag = findViewById(R.id.btnFlag);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnRestart = findViewById(R.id.btnRestart);
        btnAbout = findViewById(R.id.btnAbout);
        btnQuit = findViewById(R.id.btnExit);
        // txt views
        txtTime = findViewById(R.id.txtTime);
        txtLaps = findViewById(R.id.txtLaps);
        // layout
        layoutMain = findViewById(R.id.layoutMain);
        // variables & objects initialization ! //
        stopwatch = new TimeDisplayMSec(TICK_TIME_MSEC);
        lapsCounter=0;
        playON = false; // onCreate: all is paused
        cancelAndRestart=false;
        taps = new UtilsHiddenTaps(context,layoutMain);
    }
}