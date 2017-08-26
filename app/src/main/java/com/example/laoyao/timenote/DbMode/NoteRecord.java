package com.example.laoyao.timenote.DbMode;

import android.support.annotation.NonNull;

import com.example.laoyao.timenote.Tools.DateAndTime;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Laoyao on 2017/8/24.
 */

public class NoteRecord extends DataSupport implements Comparable
{
    private int id ;

    private int DYear ;
    private int DMonth ;
    private int DDay ;
    //private int DHour ;
    //private int DMinute ;


    private int WYear ;
    private int WMonth ;
    private int WDay ;


    private String ShortTag ;

    public NoteRecord(int DYear, int DMonth, int DDay,
                      //int DHour, int DMinute,
                      int WYear, int WMonth, int WDay,
                      String shortTag)
    {
        this.DYear = DYear;
        this.DMonth = DMonth;
        this.DDay = DDay;
        //this.DHour = DHour;
        //this.DMinute = DMinute;
        this.WYear = WYear;
        this.WMonth = WMonth;
        this.WDay = WDay;
        ShortTag = shortTag;
    }

    public int getDYear() {
        return DYear;
    }

    public int getDMonth() {
        return DMonth;
    }

    public int getDDay() {
        return DDay;
    }

    /*
    public int getDHour() {
        return DHour;
    }

    public int getDMinute() {
        return DMinute;
    }*/

    public int getWYear() {
        return WYear;
    }

    public int getWMonth() {
        return WMonth;
    }

    public int getWDay() {
        return WDay;
    }

    public String getShortTag() {
        return ShortTag;
    }


    public void setDYear(int DYear) {
        this.DYear = DYear;
    }

    public void setDMonth(int DMonth) {
        this.DMonth = DMonth;
    }

    public void setDDay(int DDay) {
        this.DDay = DDay;
    }

    /*
    public void setDHour(int DHour) {
        this.DHour = DHour;
    }

    public void setDMinute(int DMinute) {
        this.DMinute = DMinute;
    }*/

    public void setWYear(int WYear) {
        this.WYear = WYear;
    }

    public void setWMonth(int WMonth) {
        this.WMonth = WMonth;
    }

    public void setWDay(int WDay) {
        this.WDay = WDay;
    }

    public void setShortTag(String shortTag) {
        ShortTag = shortTag;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(@NonNull Object o)
    {
        NoteRecord record = (NoteRecord)o ;
        int myselfDiff ;
        int recordDiff ;
        int myselfWDiff ;
        int recordWDiff ;

        try
        {
            myselfDiff = daysBetween(DateAndTime.GetDateString() ,
                    getDYear() + "-" + getDMonth() + "-" + getDDay() ) ;
            recordDiff = daysBetween(DateAndTime.GetDateString() ,
                    record.getDYear() + "-" + record.getDMonth() + "-" + record.getDDay()) ;

            myselfWDiff = daysBetween(DateAndTime.GetDateString() ,
                    getWYear() + "-" + getWMonth() + "-" + getWDay() ) ;
            recordWDiff = daysBetween(DateAndTime.GetDateString() ,
                    record.getWYear() + "-" + record.getWMonth() + "-" + record.getWDay()) ;

            if(myselfWDiff < 0 && recordWDiff >= 0)
            {
                return -1 ;
            }

            if(myselfWDiff >= 0 && recordWDiff < 0)
            {
                return 1 ;
            }

            if(myselfDiff > 0 && recordDiff < 0)
            {
                return -1 ;
            }

            else if(myselfDiff <0 && recordDiff >0)
            {
                return 1 ;
            }

            else
            {
                if(myselfDiff > recordDiff)
                {
                    return 1 ;
                }
                else
                {
                    return -1 ;
                }
            }
        }
        catch (ParseException e)
        {
            return 0 ;
        }
    }

    public static int daysBetween(String smdate,String bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time1 - time2)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
