package com.example.lufamily.stopwatch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;

import java.util.logging.LogRecord;


public class MainActivity extends ActionBarActivity {

    private int seconds = 0;
    private boolean wasRunning = false;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null)
        {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        isRunning = wasRunning;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        isRunning = wasRunning;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) //SO THAT THE STOPWATCH WILL NOT RESTART WHEN SCREEN WAS ROTATED
    {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("isRunning", isRunning);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    public void onClickStart(View view) //CLICK TO START
    {
        isRunning = true;
    }

    public void onClickStop(View view) //CLICK TO STOP
    {
        isRunning = false;
    }

    public void onClickReset(View view) //CLICK TO RESET
    {
        isRunning = false;
        seconds = 0;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.textViewStopwatch);
        final android.os.Handler handler = new android.os.Handler();
        handler.post(new Runnable()
        {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);

                if (isRunning)
                {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
