package com.example.laoyao.timenote.Tools;

import android.util.Log;

import java.net.PortUnreachableException;

/**
 * Created by Laoyao on 2017/8/22.
 */

public class MLog
{
    public static final int VERBOSE = 1 ;
    public static final int DEBUG = 2 ;
    public static final int INFO = 3 ;
    public static final int WARN = 4 ;
    public static final int ERROR = 5 ;
    public static final int NOTHING = 6 ;
    public static final int MinDisplayLevel = VERBOSE ;

    public static void v(String tag , String msg)
    {
        if(MinDisplayLevel <= VERBOSE)
        {
            Log.v(tag , msg) ;
        }
    }

    public static void d(String tag , String msg)
    {
        if(MinDisplayLevel <= DEBUG)
        {
            Log.d(tag , msg) ;
        }
    }

    public static void i(String tag , String msg)
    {
        if(MinDisplayLevel <= INFO)
        {
            Log.i(tag , msg) ;
        }
    }

    public static void w(String tag , String msg)
    {
        if(MinDisplayLevel <= WARN)
        {
            Log.w(tag , msg) ;
        }
    }

    public static void e(String tag , String msg)
    {
        if(MinDisplayLevel <= ERROR)
        {
            Log.e(tag , msg) ;
        }
    }
}
