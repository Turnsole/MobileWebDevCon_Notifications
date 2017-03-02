package com.lastminutedevice.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    /**
     * Click listener to launch Notifications.
     */
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new NotificationDemo(MainActivity.this).createNotification(view.getId());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] buttonIds = {
                R.id.button_bundle,
                R.id.button_deep_link,
                R.id.button_launch,
                R.id.button_remote_input,
                R.id.button_web
        };
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(mOnClickListener);
        }
    }
}
