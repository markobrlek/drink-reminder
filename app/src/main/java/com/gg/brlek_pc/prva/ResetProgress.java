package com.gg.brlek_pc.prva;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class ResetProgress extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        final SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putFloat("current",0.0f);
        editor.apply();
    }
}
