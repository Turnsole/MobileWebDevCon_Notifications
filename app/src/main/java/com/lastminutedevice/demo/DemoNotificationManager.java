package com.lastminutedevice.demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.NotificationCompat;

/**
 * Create notifications on demand.
 */

public class DemoNotificationManager {

    private final int mRequestCode;
    private final Context mAppContext;

    public DemoNotificationManager(Context context) {
        mAppContext = context.getApplicationContext();
        mRequestCode = 0;
    }

    public void createNotification(int notificationType) {
        switch (notificationType) {
            case R.id.button_deep_link:
                showDeepLinkNotification();
                break;
            case R.id.button_bundle:
                showBundledNotifications();
                break;
            case R.id.button_launch:
                break;
            case R.id.button_remote_input:
                break;
            case R.id.button_stack:
                break;
            case R.id.button_web:
                break;
        }
    }

    private void showDeepLinkNotification() {
        NotificationCompat.Builder builder = buildNotification(R.string.button_deep_link);
        CharSequence actionName = "Go somewhere neat";
        Intent intent = new Intent(mAppContext, DeepLinkReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mAppContext, mRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.ic_stat_link, actionName, pendingIntent);

        showNotification(R.id.button_deep_link, builder.build());
    }

    private void showBundledNotifications() {
        NotificationCompat.Builder builder = buildNotification(R.string.button_bundle);
        showNotification(R.id.button_bundle, builder.build());
    }

    private NotificationCompat.Builder buildNotification(int titleResourceId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mAppContext);
        builder.setContentTitle(mAppContext.getString(titleResourceId));
        builder.setSmallIcon(R.drawable.ic_smiley);
        builder.setAutoCancel(true);
        int color = mAppContext.getResources().getColor(R.color.colorPrimaryDark);
        builder.setColor(color);
        return builder;
    }

    private void showNotification(int id, Notification notification) {
        NotificationManager manager =  (NotificationManager) mAppContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, notification);
    }
}
