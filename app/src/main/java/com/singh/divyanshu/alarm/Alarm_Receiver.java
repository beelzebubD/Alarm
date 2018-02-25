package com.singh.divyanshu.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by divyanshu on 20/2/18.
 */

public class Alarm_Receiver extends BroadcastReceiver{
    String S1="";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("inside the receiver", "cool");
        //String to find if you have pressed the alarm button extra number of times
        try {
            S1=intent.getExtras().getString("extra");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //create an intent to the ringtone service to start the ringtone
        Intent intent1=new Intent(context,RingtonePlayingService.class);
        //pass the extra String from Main Activity to the playing service
        intent1.putExtra("extra",S1);
        //start the service
        context.startService(intent1);

    }
}
