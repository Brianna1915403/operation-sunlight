package com.example.operationsunlight;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavDeepLinkBuilder;

public class NotificationService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent destination = new NavDeepLinkBuilder(context)
                                .setGraph(R.navigation.mobile_navigation)
                                .setDestination(R.id.nav_greenhouse_view)
                                .createPendingIntent();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Plant Care")
            .setContentTitle("Time To Take Care of Your Plants!")
            .setContentText("Some might need watering others some love.")
                .setContentIntent(destination)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(0, builder.build());
    }
}
