package com.gg.brlek_pc.prva;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class Repeating_activity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent2=new Intent(Repeating_activity.this,MainActivity.class);
        startActivity(intent2);
    }
}
