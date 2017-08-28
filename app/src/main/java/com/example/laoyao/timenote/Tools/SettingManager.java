package com.example.laoyao.timenote.Tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Laoyao on 2017/8/27.
 */

public  class SettingManager
{
    private static final String IsMonthModeKey = "MonthMode" ;
    private static final String IsOpenNotificationKey = "Notification" ;
    private static final String IsLEDKey = "LED" ;
    private static final String IsDateChangeKey = "DateChange" ;
    private static final String IsAPPOpenKey = "APPOpen" ;

    //保存setting数据的share Predereneces 文件名
    private static final String SPreferencesFileName = "SettingData" ;

    private Context GiveContext ;
    private SharedPreferences SP ;

    public SettingManager(Context giveContext)
    {
        GiveContext = giveContext ;
        SP = GiveContext.getSharedPreferences(SPreferencesFileName , Context.MODE_PRIVATE) ;
    }

    public boolean IsMonthMode()
    {
        //默认为false , 不打开month mode
        return SP.getBoolean(IsMonthModeKey , false) ;
    }

    public boolean IsOpenNotification()
    {
        //默认打开提醒
        return SP.getBoolean(IsOpenNotificationKey , true) ;
    }

    public boolean IsLED()
    {
        //默认打开LED提醒
        return SP.getBoolean(IsLEDKey , true) ;
    }

    public boolean IsDateChangeNotification()
    {
        //默认打开日期改变时提醒
        return SP.getBoolean(IsDateChangeKey , true) ;
    }

    public boolean IsAppOpenNotification()
    {
        //默认打开APP时提醒
        return SP.getBoolean(IsAPPOpenKey , true) ;
    }

    public void SetMonthMode(boolean set)
    {
        SharedPreferences.Editor editor = SP.edit() ;

        editor.putBoolean(IsMonthModeKey , set) ;
        editor.commit() ;
    }

    public void SetOpenNotification(boolean set)
    {
        SharedPreferences.Editor editor = SP.edit() ;

        editor.putBoolean(IsOpenNotificationKey , set) ;
        editor.commit() ;
    }

    public void SetLED(boolean set)
    {
        SharedPreferences.Editor editor = SP.edit() ;

        editor.putBoolean(IsLEDKey , set) ;
        editor.commit() ;
    }

    public void SetDateChangeNotification(boolean set)
    {
        SharedPreferences.Editor editor = SP.edit() ;

        editor.putBoolean(IsDateChangeKey , set) ;
        editor.commit() ;
    }

    public void SetAppOpenNotification(boolean set)
    {
        SharedPreferences.Editor editor = SP.edit() ;

        editor.putBoolean(IsAPPOpenKey , set) ;
        editor.commit() ;
    }

}
