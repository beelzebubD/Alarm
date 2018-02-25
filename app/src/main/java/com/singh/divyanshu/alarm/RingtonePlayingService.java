package com.singh.divyanshu.alarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.app.Notification;
import java.util.Random;

/**
 * Created by divyanshu on 20/2/18.
 */

public class RingtonePlayingService extends Service {
   private MediaPlayer mp;
    Random rand=new Random();
   private boolean isRunning;
    private int startId;
   private String song[]={"angrejibeat","chull","onedrive","durex"};
    private Context context;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        //Setup the notification service


        final NotificationManager notify_Manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        //setup and intent that goes to the main acivity
        Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent_main_acitvity = PendingIntent.getActivity(this,0, intent_main_activity,0);
        //setup the notification parameters
        Notification notification_Popup  = new Notification.Builder(this)
                .setContentTitle("Alarm")
                .setContentText("Click me!")//subtext
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent_main_acitvity)
                .setAutoCancel(true)
                .build();


        //to decide what to do when a button is clicked

        String state=intent.getExtras().getString("extra");
        Log.e("extra",state);

        assert state!=null;
        if(state.equalsIgnoreCase("alarm on"))
        {
            this.startId=0;
        }
        if(state.equalsIgnoreCase("alarm off"))
        {
            this.startId=1;
        }
        Log.e("value of startid",Integer.toString(this.startId));
                    if(mp!=null)
            {
                this.isRunning=mp.isPlaying();
            }
            if(!this.isRunning)
            {
                //setup the notification call command
                notify_Manager.notify(0,notification_Popup);
            }
        if (!this.isRunning && this.startId==0)
        {
            Log.e("there is no music, ", "and you want start");
            int n=rand.nextInt(4);
            int k=getResources().getIdentifier(song[n],"raw",getPackageName());
            mp=MediaPlayer.create(this,k);
            mp.start();
            this.isRunning=true;
        }
        else if (!this.isRunning && this.startId==1)
        {
            Log.e("if there was no sound ", " and you want end");

            this.isRunning = false;
        }
        else if (this.isRunning && this.startId==0)
        {
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
        }
        else
        {
            Log.e("if there is sound ", " and you want end");
            mp.stop();
            mp.reset();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {


            Log.e("destroyes", "on destroy called");
            super.onDestroy();

            this.isRunning = false;
    }

 }
