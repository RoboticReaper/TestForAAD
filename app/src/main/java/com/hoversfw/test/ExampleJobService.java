package com.hoversfw.test;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.media.MediaPlayer;
import android.util.Log;

public class ExampleJobService extends JobService {
    private static final String TAG="ExampleJobService";
    private boolean jobCanceled=false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "run: " + i);
                    MediaPlayer player=MediaPlayer.create(getApplicationContext(),R.raw.shortbeep);
                    player.start();
                    if (jobCanceled) {
                        jobFinished(params,true);
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCanceled = true;
        return true;
    }
}
