package com.example.laoyao.timenote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.laoyao.timenote.Tools.NotificationBulider;
import com.example.laoyao.timenote.Tools.SettingManager;

public class EveryDaySendMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        SettingManager manager = new SettingManager(context) ;

        //如果开启了每日提醒 ， 则 ， 发出notification
        if(manager.IsDateChangeNotification())
        {
            NotificationBulider.DateChange(context);
        }
    }
}
