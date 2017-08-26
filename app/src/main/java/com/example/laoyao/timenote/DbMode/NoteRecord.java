package com.example.laoyao.timenote.DbMode;

import org.litepal.crud.DataSupport;

/**
 * Created by Laoyao on 2017/8/24.
 */

public class NoteRecord extends DataSupport
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
}
