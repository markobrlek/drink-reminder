package com.gg.brlek_pc.prva;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private Button benefits;
    private FloatingActionButton mL250;
    private FloatingActionButton mL500;
    private FloatingActionButton L1;
    private Float current;
    TextView rdn;
    private Button restart;
    ProgressBar progress;
    TextView congrats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  (Toolbar) findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        benefits=(Button) findViewById(R.id.button2);
        benefits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain2Activity();
            }
        });
        restart=(Button)findViewById(R.id.button4);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        final SharedPreferences.Editor editor = mSharedPreference.edit();
        String goal=(mSharedPreference.getString("litre","2"));
        final Float fgoal=(mSharedPreference.getFloat("flitre",2));
        mL250=(FloatingActionButton)findViewById(R.id.mL250);
        mL500=(FloatingActionButton)findViewById(R.id.mL500);
        L1=(FloatingActionButton)findViewById(R.id.L1);
        congrats=(TextView)findViewById(R.id.textView2);
        progress=(ProgressBar)findViewById(R.id.progressBar);
        progress.setMax(Math.round(fgoal*100));
        progress.setScaleY(3f);
        mL250.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                current+=0.250f;
                if (current>=fgoal){
                    congrats.setText(R.string.congrats);
                }
                editor.putFloat("current", current);
                editor.apply();
                rdn.setText(String.valueOf(current)+" "+"L"+" "+"/"+" "+String.valueOf(fgoal)+" "+"L");
                progress.setMax(Math.round(fgoal*100));
                progress.setProgress(Math.round(current*100));
            }
        });
        mL500.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                current+=0.5f;
                if (current>=fgoal){
                    congrats.setText(R.string.congrats);
                }
                editor.putFloat("current", current);
                editor.apply();
                rdn.setText(String.valueOf(current)+" "+"L"+" "+"/"+" "+String.valueOf(fgoal)+" "+"L");
                progress.setMax(Math.round(fgoal*100));
                progress.setProgress(Math.round(current*100));
            }
        });
        L1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                current+=1;
                if (current>=fgoal){
                    congrats.setText(R.string.congrats);
                }
                editor.putFloat("current", current);
                editor.apply();
                rdn.setText(String.valueOf(current)+" "+"L"+" "+"/"+" "+String.valueOf(fgoal)+" "+"L");
                progress.setMax(Math.round(fgoal*100));
                progress.setProgress(Math.round(current*100));
            }
        });
        current=(Float)mSharedPreference.getFloat("current",0.0f);
        if (current>=fgoal){
            congrats.setText(R.string.congrats);
        }
        rdn=(TextView)findViewById(R.id.textView3);
        rdn.setText(String.valueOf(current)+" "+"L"+" "+"/"+" "+String.valueOf(fgoal)+" "+"L");
        progress.setProgress(Math.round(current*100));
        restart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alrtdialog = new AlertDialog.Builder(MainActivity.this);
                alrtdialog.setMessage("Are you sure you want to reset your today's progress?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        current = 0.0f;
                        editor.putFloat("current", current);
                        editor.apply();
                        rdn.setText(String.valueOf(current) + " " + "L" + " " + "/" + " " + String.valueOf(fgoal) + " " + "L");
                        congrats.setText("");
                        progress.setMax(Math.round(fgoal * 100));
                        progress.setProgress(Math.round(current * 100));

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert  = alrtdialog.create();
                alert.setTitle("Are you sure?");
                alert.show();
            }

        });
    }
    public boolean openMain2Activity(){
        Intent intent=new Intent(this,Main2Activity.class);
    startActivity(intent);
        return false;
    }
    public boolean openMain3Activity(){
        Intent intent=new Intent(this,Main3Activity.class);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return openMain3Activity();
    }
}
