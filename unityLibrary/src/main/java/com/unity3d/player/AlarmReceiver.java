package com.unity3d.player;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //throw new UnsupportedOperationException("Not yet implemented");

        //Intent mAlarmIntent = new Intent("com.test.alarmtestous.ALARM_START");
        Intent intent2 = new Intent(context, AlarmService.class);
        PendingIntent mPendingIntent2= PendingIntent.getBroadcast(context, 1, intent2, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long triggerTime = SystemClock.elapsedRealtime()+1000*60;
        manager.set(AlarmManager.RTC_WAKEUP, triggerTime, mPendingIntent2);
    }
}
