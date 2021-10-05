package com.example.filedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.Thread;

public class MainActivity extends AppCompatActivity {
    private static final String Tag = "MainActivity";
    private Button startBtn;
    private volatile boolean stopThread  = false;
    private TextView display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = (Button) findViewById(R.id.start_btn);
        display = (TextView) findViewById(R.id.display);

    }
    public void mockFileDownloader(){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startBtn.setText("Downloading...");
                }
            });


        for (int downloadProgres = 0; downloadProgres <= 100; downloadProgres += 10){
            if(stopThread){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startBtn.setText("Start");
                        display.setText("no progress");
                    }
                });
                return;
            }

            Log.d(Tag,"Download Progress: "+downloadProgres+"%");
            String newText = "Download Progress:" +downloadProgres+"%";
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    display.setText(newText);
                }
            });




            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startBtn.setText("Start");
            }
        });
    }
    public void startDownload(View view){
        stopThread = false;
        ExampleRunnable runnable  = new ExampleRunnable();
        new Thread(runnable).start();
    }
    public void StopDownload(View view){
        stopThread = true;
    }




    public class ExampleRunnable  implements  Runnable{
        @Override
        public void run() {
            mockFileDownloader();
        }
    }
}