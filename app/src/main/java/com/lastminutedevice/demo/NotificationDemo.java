package com.lastminutedevice.demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.NotificationCompat;

import static com.lastminutedevice.demo.DemoBroadcastReceiver.REMOTE_INPUT_KEY;

/**
 * Create notifications on demand.
 */

public class NotificationDemo {

    private final int mRequestCode;
    private final Context mAppContext;

    public NotificationDemo(Context context) {
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
                showAppLaunchNotification();
                break;
            case R.id.button_remote_input:
                showRemoteInput();
                break;
            case R.id.button_web:
                showWebAddressNotification();
                break;
        }
    }

    /**
     * And interior Activity with synthetic stack.
     */
    private void showDeepLinkNotification() {
        NotificationCompat.Builder builder = buildNotification(R.string.button_deep_link);
        CharSequence actionName = "Go somewhere neat";
        Intent intent = new Intent(mAppContext, InteriorActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mAppContext, mRequestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.addAction(R.drawable.ic_stat_link, actionName, pendingIntent);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        showNotification(R.id.button_deep_link, builder.build());
    }

    private void showBundledNotifications() {
        NotificationCompat.Builder builder = buildNotification(R.string.button_bundle);

        /**
         * The first group.
         */
        builder.setGroup("group one");

        String[] contents = new String[] {"Message One", "Message Two", "Summary One"};
        for (String content : contents) {
            builder.setContentText(content);
            builder.setGroupSummary("Summary One".equals(content));
            showNotification(content.hashCode(), builder.build());
        }

        /**
         * The second group is a different color.
         */
        builder.setGroup("key two");
        int color = mAppContext.getResources().getColor(R.color.colorAccent);
        builder.setColor(color);

        contents = new String[] {"Message Three", "Message Four", "Summary Two"};
        for (String content : contents) {
            builder.setContentText(content);
            builder.setGroupSummary("Summary Two".equals(content));
            showNotification(content.hashCode(), builder.build());
        }
    }

    /**
     * Launch a web address.
     */
    private void showWebAddressNotification() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://wikipedia.org"));
        PendingIntent pendingIntent = PendingIntent.getActivity(mAppContext, mRequestCode, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = buildNotification(R.string.button_web);
        builder.setContentIntent(pendingIntent);
        showNotification(R.id.button_launch, builder.build());
    }

    /**
     * Just launch the application's MainActivity.
     */
    private void showAppLaunchNotification() {
        Intent intent = new Intent(mAppContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mAppContext, mRequestCode, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = buildNotification(R.string.button_launch);
        builder.setContentIntent(pendingIntent);
        showNotification(R.id.button_launch, builder.build());
    }

    /**
     * Show a notification with an input field.
     */
    private void showRemoteInput() {
        NotificationCompat.Builder builder = buildNotification(R.string.button_remote_input);
        CharSequence actionName = "Type something RIGHT HERE";
        Intent intent = new Intent(mAppContext, DemoBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mAppContext, mRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput remoteInput = new RemoteInput.Builder(REMOTE_INPUT_KEY)
                .setLabel("Hint Text Goes Here")
                .build();

        NotificationCompat.Action compatAction = new NotificationCompat.Action.Builder(R.drawable.ic_smiley, actionName, pendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        builder.addAction(compatAction);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        showNotification(R.id.button_deep_link, builder.build());
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
