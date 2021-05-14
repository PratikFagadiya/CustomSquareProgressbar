package com.pratik.customsquareprogressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pratik.customsquareprogress.CustomSquareProgress;

public class MainActivity extends AppCompatActivity {

    CustomSquareProgress squareProgress;
    private int progressStatus = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        squareProgress = findViewById(R.id.squareProgress);
        squareProgress.setCornerRadius(25);
        squareProgress.setStrokeWidth(15);
        squareProgress.setPaintColor("#000000");
        squareProgress.setShouldShowBackground(true);
        squareProgress.setProgress(5);

        doWorkWithProgress();
    }

    private void doWorkWithProgress() {
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            squareProgress.setProgress(progressStatus);

                            if (progressStatus == 100) {
                                progressStatus = 0;
                                doWorkWithProgress();
                            }
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}