package com.hoversfw.test;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.Toast;

public class AlarmNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("Dismissed");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.cancel(2);
            System.exit(0);
        }
    }
}
