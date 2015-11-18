package com.example.janssen.stopwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private boolean isRunning;
    private boolean wasRunning;
    private int seconds;

    //Sets the number of seconds on the timer.
    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.textViewTime);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d",
                        hours, minutes, secs);
                timeView.setText(time);
                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }


    //Start the stopwatch running when the Start button is clicked.
    public void onClickStart(View view) {

        isRunning = true;
    }

    //Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop(View view) {
        isRunning = false;
    }

    //Reset the stopwatch when the Reset button is clicked.
    public void onClickReset(View view) {
        isRunning = false;
        seconds = 0;
    }

    // The activity is now visible (but not ready for user interaction)
    @Override
    protected void onStart() {
        super.onStart();
        isRunning = wasRunning;
    }

    // The activity is now in the foreground and ready for user interaction
    @Override
    protected void onResume() {
        super.onResume();
        isRunning = wasRunning;
    }

    // Counterpart to onResume(). The activity is about to go into the background and has stopped interacting with the user.
    // This can happen when another activity is launched in front of the current activity.
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    // Counterpart to onStart(). The activity is no longer visible to the user.
    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = isRunning;
        isRunning = false;
    }

    // Handle configuration changes by saving the Activity state variables
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("isRunning", isRunning);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }
}
