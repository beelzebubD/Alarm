package com.singh.divyanshu.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //alarm manager
    AlarmManager alarm_manager;

    //time picker to pick a time
    TimePicker alarm_timepicker;

    //text view to set the alarm
    TextView updateAlarm;

    //Context
    Context context;
   //buttons
    Button b1,b2,b3;

    //pending intent
    PendingIntent pendingIntent;
    //object of this class
    MainActivity inst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context=this;

        //initialize our alarm_manager
        alarm_manager=(AlarmManager)getSystemService(ALARM_SERVICE);

        //initialize our timepicker;
        alarm_timepicker=findViewById(R.id.timePicker);
        updateAlarm=findViewById(R.id.textView);

        //create and instance of the calender
        final Calendar calendar=Calendar.getInstance();
        b1=findViewById(R.id.setAlarm);
        b2=findViewById(R.id.unsetAlarm);
        b3=findViewById(R.id.reset);

        //create an intent to connect this and the alarm receiver class
        final Intent intent=new Intent(MainActivity.this,Alarm_Receiver.class);

        //create the listener for the set and unset button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY,alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE,alarm_timepicker.getMinute());
                updateAlarm.setText("alarm set to:  "+String.valueOf(alarm_timepicker.getHour()+":"+Integer.toString(alarm_timepicker.getMinute())));

                //put in extra string into my intent, tells the clock that you pressed the alarm on button
                intent.putExtra("extra","alarm on");

                //create pending intent to send the intent as a service broadcast
                pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                alarm_manager.set(alarm_manager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //put in extra string into my intent, tells the clock that you pressed the alarm on button
                intent.putExtra("extra","alarm off");

                //send the broadcast
                sendBroadcast(intent);
                if (pendingIntent!=null) {

                    //cancel the alarm
                    alarm_manager.cancel(pendingIntent);
                    updateAlarm.setText("Alarm off");
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm_timepicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                alarm_timepicker.setMinute(calendar.get(Calendar.MINUTE));
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("MyActivity", "on Destroy");
    }
}
