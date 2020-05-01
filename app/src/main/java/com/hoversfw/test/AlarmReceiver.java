package com.hoversfw.test;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String CHANNEL_2="2";
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel2=new NotificationChannel(CHANNEL_2,"Channel 2",NotificationManager.IMPORTANCE_HIGH);
            channel2.setDescription("This channel is for alarm");
            NotificationManager managers=context.getSystemService(NotificationManager.class);
            managers.createNotificationChannel(channel2);
            Intent broadcastIntent=new Intent(context,AlarmNotification.class);
            broadcastIntent.putExtra("Dismissed","Alarm stopped");
            broadcastIntent.putExtra("id","2");
            Intent activity=new Intent(context,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent action=PendingIntent.getBroadcast(context,0,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent contentIntent=PendingIntent.getActivity(context, 0,activity,0);
            NotificationCompat.Builder notification=new NotificationCompat.Builder(context,CHANNEL_2)
                    .setContentText("Alarm is working")
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .setColor(Color.BLUE)
                    .addAction(R.mipmap.ic_launcher,"Stop",action)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Alarm");
            NotificationManagerCompat manager=NotificationManagerCompat.from(context);
            manager.notify(2,notification.build());
            MediaPlayer player=MediaPlayer.create(context,R.raw.alarmbgm);
            player.start();
        }
    }
}
