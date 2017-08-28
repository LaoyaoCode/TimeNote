package com.example.laoyao.timenote.Tools;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.NotificationCompat;
import com.example.laoyao.timenote.DbMode.NoteRecord;
import com.example.laoyao.timenote.R;

import org.litepal.crud.DataSupport;

import java.util.Collections;
import java.util.List;
import com.example.laoyao.timenote.* ;

/**
 * Created by Laoyao on 2017/8/27.
 */

public class NotificationBulider
{
    private static final int OpenAppNotificationID = 1 ;
    private static final int DateChangeNotificationID = 2 ;

    public static void OpenApp(Context context)
    {

        DisplayANotification(NotificationCompat.PRIORITY_MAX , context , OpenAppNotificationID) ;
    }

    public static void DateChange(Context context)
    {
        DisplayANotification(NotificationCompat.PRIORITY_HIGH , context , DateChangeNotificationID) ;
    }

    private static void DisplayANotification(int priority , Context context , int id)
    {
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE) ;
        SettingManager settingManager = new SettingManager(context) ;
        Notification notification ;

        List<NoteRecord> records = DataSupport.findAll(NoteRecord.class) ;
        int deadNumber = 0 ;
        int warningNumber = 0 ;
        int outOfTimeNumber = 0 ;

        manager.cancel(id);

        for(NoteRecord record : records)
        {
            String wDate = record.getWYear() + "-" + String.format("%02d", record.getWMonth())
                    + "-" + String.format("%02d", record.getWDay());
            String dDate = record.getDYear() + "-" + String.format("%02d", record.getDMonth())
                    + "-" + String.format("%02d", record.getDDay());

            DateAndTime.CompareResult wDateResult = DateAndTime.StructedDate.Compare(wDate, DateAndTime.GetDateString());
            DateAndTime.CompareResult dDateResult = DateAndTime.StructedDate.Compare(dDate, DateAndTime.GetDateString());

            //已经到了提醒日期
            if (wDateResult == DateAndTime.CompareResult.Smaller || wDateResult == DateAndTime.CompareResult.Equal)
            {
                //已经到了提醒期间，但是没有到终结日期
                if (dDateResult == DateAndTime.CompareResult.Bigger) {
                    warningNumber++;
                }
                //已经到了提醒期间，正好到了终结日期
                else if (dDateResult == DateAndTime.CompareResult.Equal) {
                    deadNumber++;
                }
                //已经到了提醒日期，超过了终结日期
                else {
                    outOfTimeNumber++;
                }
            }
            //还没有到提醒日期
            else
            {

            }
        }

        Intent action = null ;

        //高等级 ， date change  ， 打开APP
        if(priority == NotificationCompat.PRIORITY_HIGH)
        {
            action = new Intent(context , SplashActivity.class) ;
        }
        //其余的直接显示 display 场景
        else
        {
            action = new Intent(context , NoteDisplayActivity.class) ;
        }

        PendingIntent pi = PendingIntent.getActivities(context , 0 , new Intent[]{action} , 0) ;

        if(settingManager.IsLED())
        {
            int ledColor  ;

            if(deadNumber > 0)
            {
                ledColor = Color.RED;
            }
            else if(warningNumber > 0)
            {
                ledColor = Color.YELLOW ;
            }
            else
            {
                ledColor = Color.GREEN ;
            }

            notification = new  NotificationCompat.Builder(context)
                    .setContentTitle(DateAndTime.GetDateString())
                    .setContentText("Dead" + "<" + deadNumber + ">" +"   "
                            + "Warning" +  "<" + warningNumber + ">" +"   "
                            + "Out" + "<" + outOfTimeNumber + ">")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.notification_small_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources() , R.drawable.notification_big_icon))
                    .setPriority(priority)
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .setLights(ledColor , 1000 , 1000)
                    .build() ;
        }
        else
        {
            notification = new  NotificationCompat.Builder(context)
                    .setContentTitle(DateAndTime.GetDateString())
                    .setContentText("Dead" + "<" + deadNumber + ">" +"   "
                            + "Warning" +  "<" + warningNumber + ">" +"   "
                            + "Out" + "<" + outOfTimeNumber + ">")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.notification_small_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources() , R.drawable.notification_big_icon))
                    .setPriority(priority)
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .build() ;
        }

        manager.notify(id , notification) ;
    }
}
