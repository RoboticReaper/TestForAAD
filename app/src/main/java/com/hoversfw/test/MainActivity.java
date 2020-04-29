package com.hoversfw.test;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String CHANNEL_1="channel1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=findViewById(R.id.button);

        Button but=findViewById(R.id.notif);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    NotificationChannel channel1=new NotificationChannel(
                            CHANNEL_1,
                            "This is Notification Channel 1",
                            NotificationManager.IMPORTANCE_HIGH
                    );
                    channel1.setDescription("This is channel 1");
                    NotificationManager manager=getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel1);
                    Intent activity=new Intent(MainActivity.this,MainActivity.class);
                    PendingIntent contentIntent=PendingIntent.getActivity(MainActivity.this, 0,activity,0);
                    Intent broadcastIntent=new Intent(MainActivity.this,NotificationReceiver.class);
                    broadcastIntent.putExtra("Dismissed","Notification has been dismisssed");
                    PendingIntent actionIntent=PendingIntent.getBroadcast(MainActivity.this,0,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationManagerCompat notificationManager= NotificationManagerCompat.from(MainActivity.this);
                    Notification notification=new NotificationCompat.Builder(MainActivity.this,CHANNEL_1)
                            .setContentTitle("This is Notification's Title")
                            .setContentText("This is Description")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentIntent(contentIntent)
                            .setAutoCancel(true)
                            .setOnlyAlertOnce(true)
                            .setColor(Color.GREEN)
                            .addAction(R.mipmap.ic_launcher,"Dismiss",actionIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH).build();
                    notificationManager.notify(1,notification);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               Snackbar o=Snackbar.make(v,"Hello!!!",Snackbar.LENGTH_LONG);
                o.setAction("What?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view,"NOOOOO",Snackbar.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        Button b=findViewById(R.id.alarm);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker=new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView=findViewById(R.id.time);
        textView.setText("Time: "+hourOfDay+" hour "+minute+" minute ");
    }
}
