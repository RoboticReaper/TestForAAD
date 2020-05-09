package com.hoversfw.test;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String CHANNEL_1="channel1";
    private static final String TAG="MainActivity";

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
                    broadcastIntent.putExtra("Dismissed","Notification has been dismissed");
                    broadcastIntent.putExtra("id","1");
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
        Button cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        Button custom=findViewById(R.id.custom);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CustomActivity.class));
            }
        });
    }
    public void scheduleJob(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("When you click OK, this app will make beep sound for 10 times, repeat every 15 minutes, when device is connected to wifi, even after reboot. Killing app process won't work. You can click CANCEL JOB button to stop.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ComponentName componentName = new ComponentName(MainActivity.this, ExampleJobService.class);
                JobInfo info = new JobInfo.Builder(123, componentName)
                        //.setRequiresCharging(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                        .setPersisted(true)
                        .setPeriodic(15 * 60 * 1000)
                        .build();

                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                int resultCode = scheduler.schedule(info);
                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Log.d(TAG, "Job scheduled");
                    Toast.makeText(MainActivity.this, "Job scheduled", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Job scheduling failed");
                    Toast.makeText(MainActivity.this, "Job scheduling failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();

    }

    public void cancelJob(View v){
        JobScheduler scheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG,"Job cancelled");
        Toast.makeText(this, "Job cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);
        updateTimeText(c);
        startAlarm(c);
    }

    private void updateTimeText(Calendar c){
        String timeText="Alarm set for ";
        timeText+= DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        TextView time=findViewById(R.id.time);
        time.setText(timeText);
    }

    private void startAlarm(Calendar c){
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,3,intent,0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }

    private void cancel(){
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,3,intent,0);
        alarmManager.cancel(pendingIntent);

        TextView textView=findViewById(R.id.time);
        textView.setText("Canceled");
    }
}
