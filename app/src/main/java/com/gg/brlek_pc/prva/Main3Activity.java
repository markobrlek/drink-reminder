package com.gg.brlek_pc.prva;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static java.lang.String.format;

public class Main3Activity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    EditText edit1;
    String litre;
    Float flitre;
    private Button apply;
    private Button change1;
    private Button change2;
    private Button change3;
    private String flag;
    private int startHours;
    private int startMinutes;
    private int endHours;
    private int endMinutes;
    private int currentHours;
    private int currentMinutes;
    private TextView startTime;
    private TextView endTime;
    private int strtHrs;
    private int strtMns;
    private int endHrs;
    private int endMns;
    private int rstHrs;
    private int rstMns;
    private NotificationManagerCompat notificationManagerCompat;
    private TextView resetTime;
    Float current;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar =  (Toolbar) findViewById(R.id.actionbar2);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        notificationManagerCompat=NotificationManagerCompat.from(this);
        edit1=(EditText)findViewById(R.id.editText2);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Float flitre1=prefs.getFloat("flitre",2);
        String litre1=prefs.getString("litre","2");
        edit1.setText(""+flitre1);
        apply=(Button)findViewById(R.id.button5);
        change1=(Button)findViewById(R.id.changeStarttime);
        change2=(Button)findViewById(R.id.changeEndtime);
        change3=(Button)findViewById(R.id.changeResettime);
        startTime = (TextView)findViewById(R.id.displayStarttime);
        endTime = (TextView)findViewById(R.id.displayEndtime);
        resetTime=(TextView)findViewById(R.id.displayResettime);
        strtHrs=prefs.getInt("startHours",9);
        strtMns=prefs.getInt("startMinutes",0);
        endHrs=prefs.getInt("endHours",23);
        endMns=prefs.getInt("endMinutes",0);
        rstHrs=prefs.getInt("resetHours",0);
        rstMns=prefs.getInt("resetMinutes",0);
        startTime.setText(String.format("%02d", strtHrs)+":"+String.format("%02d", strtMns));
        endTime.setText(String.format("%02d", endHrs)+":"+String.format("%02d", endMns));
        resetTime.setText(String.format("%02d", rstHrs)+":"+String.format("%02d", rstMns));
        flag=(String)null;
        change1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag="change 1";
                DialogFragment timePicker1 = new TimePickerFragment();
                timePicker1.show(getSupportFragmentManager(),"time picker1");
            }
        });
        change2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag="change 2";
                DialogFragment timePicker2 = new TimePickerFragment();
                timePicker2.show(getSupportFragmentManager(),"time picker2");
            }
        });
        change3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag="change 3";
                DialogFragment timePicker3 = new TimePickerFragment();
                timePicker3.show(getSupportFragmentManager(),"time picker3");
            }
        });
        apply.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                flitre = Float.parseFloat(edit1.getText().toString());
                litre = edit1.getText().toString();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
                SharedPreferences.Editor editor = prefs.edit();
                Intent intent = new Intent(getApplicationContext(),Notification_reciever.class);
                Intent intent3 = new Intent(getApplicationContext(),Notification_reciever2.class);
                Intent intent4 = new Intent(getApplicationContext(),Notification_reciever3.class);
                Intent intent5 = new Intent(getApplicationContext(),ResetProgress.class);
                AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                editor.putFloat("flitre", flitre);
                editor.putString("litre", litre);
                editor.putInt("startHours",strtHrs);
                editor.putInt("startMinutes",strtMns);
                editor.putInt("endHours",endHrs);
                editor.putInt("endMinutes",endMns);
                editor.putInt("resetHours",rstHrs);
                editor.putInt("resetMinutes",rstMns);
                editor.apply();
                current = prefs.getFloat("current",0.0f);
                alarmManager.cancel(PendingIntent.getBroadcast(getApplicationContext(),300,intent,PendingIntent.FLAG_UPDATE_CURRENT));
                for (int e=24;e>=0;e--){
                    alarmManager.cancel(PendingIntent.getBroadcast(getApplicationContext(),e,intent,PendingIntent.FLAG_UPDATE_CURRENT));
                }
                int brojalarma= 0;
                if ((strtHrs>endHrs)||((strtHrs==endHrs)&&(strtMns>endMns))){
                    brojalarma=((endHrs+24)-strtHrs);
                } else {
                    brojalarma = (endHrs - strtHrs);
                }
                if (endMns>=endHrs){
                    brojalarma+=1;}

                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,endHrs);
                calendar.set(Calendar.MINUTE,endMns);
                if (calendar.getTime().compareTo(new Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1);
                intent3.putExtra("convalue",200);
                PendingIntent pendingIntent2=PendingIntent.getBroadcast(getApplicationContext(),200,intent3,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent2);

                calendar.set(Calendar.HOUR_OF_DAY,strtHrs);
                calendar.set(Calendar.MINUTE,strtMns);
                if (calendar.getTime().compareTo(new Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1);
                intent4.putExtra("convalue",100);
                PendingIntent pendingIntent3=PendingIntent.getBroadcast(getApplicationContext(),100,intent4,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent3);

                calendar.set(Calendar.HOUR_OF_DAY,rstHrs);
                calendar.set(Calendar.MINUTE,rstMns);
                if (calendar.getTime().compareTo(new Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1);
                intent5.putExtra("convalue",300);
                PendingIntent pendingIntent4=PendingIntent.getBroadcast(getApplicationContext(),300,intent5,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent4);

                int forbrojalarma=brojalarma-1;
                Intent intent2 = new Intent(Main3Activity.this, MainActivity.class);
                intent2.putExtra("litre", litre);
                intent2.putExtra("flitre", flitre);
                for(int i=forbrojalarma-1;i>0;i--){
                    Calendar calendar2=Calendar.getInstance();
                    int forstrtHrs=strtHrs+i;
                    if (forstrtHrs>=24){
                        forstrtHrs-=24;
                    }
                    calendar2.set(Calendar.HOUR_OF_DAY,forstrtHrs);
                    calendar2.set(Calendar.MINUTE,strtMns);
                    if (calendar2.getTime().compareTo(new Date()) < 0) calendar2.add(Calendar.DAY_OF_MONTH, 1);
                    intent.putExtra("convalue",i);
                    PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),i,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                }
                Toast.makeText(getApplicationContext(),"Changes applied!",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hr, int min) {
        if(flag!=null && flag.equals("change 1")) {
            startTime.setText(String.format("%02d", hr) + ":" + String.format("%02d", min));
            strtHrs = hr;
            strtMns = min;
            flag = null;
        }
        if(flag!=null && flag.equals("change 2")) {
            endTime.setText(String.format("%02d", hr) + ":" + String.format("%02d", min));
            endHrs = hr;
            endMns = min;
            flag = null;
        }
        if(flag!=null && flag.equals("change 3")) {
            resetTime.setText(String.format("%02d", hr) + ":" + String.format("%02d", min));
            rstHrs = hr;
            rstMns = min;
            flag = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home){
            startActivity(new Intent(this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}