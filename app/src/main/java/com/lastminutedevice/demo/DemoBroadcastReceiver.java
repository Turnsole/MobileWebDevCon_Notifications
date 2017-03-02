package com.lastminutedevice.demo;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.widget.Toast;

public class DemoBroadcastReceiver extends BroadcastReceiver {
    public static final String REMOTE_INPUT_KEY = "remote_input_key";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle results = RemoteInput.getResultsFromIntent(intent);
        if (results != null) {
            CharSequence quickReplyResult = results.getCharSequence(REMOTE_INPUT_KEY);
            Toast.makeText(context, quickReplyResult, Toast.LENGTH_LONG).show();

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancelAll();
        }
    }
}
