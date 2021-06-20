package com.gg.brlek_pc.prva;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;

public class Notification_reciever3 extends BroadcastReceiver {
    public static String code = "convalue";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeating_intent = new Intent(context, Repeating_activity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int code = intent.getIntExtra("convalue", 1);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, code, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Start of your daily reminders";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
            mChannel.setDescription("Start of your daily reminders");
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build();
            mChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), audioAttributes);
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.glassicon);
            Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.glassicon)
                    .setLargeIcon(largeIcon)
                    .setContentTitle(context.getString(R.string.notifstarttitle))
                    .setContentText(context.getString(R.string.notifstart))
                    .setAutoCancel(true);
            notificationManager.notify(notifyID, builder.build());
        } else {
            Notification.Builder builder = new Notification.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.glassicon)
                    .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.mipmap.glassicon)).getBitmap())
                    .setContentTitle(context.getString(R.string.notifstarttitle))
                    .setContentText(context.getString(R.string.notifstart))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            notificationManager.notify(100, builder.build());
        }
    }
}