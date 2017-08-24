package com.example.laoyao.timenote.DbMode;

/**
 * Created by Laoyao on 2017/8/24.
 */

public class NoteRecord
{
    private int DYear ;
    private int DMonth ;
    private int DDay ;
    private int DHour ;
    private int DMinute ;


    private int WYear ;
    private int WMonth ;
    private int WDay ;


    private String ShortTag ;
    private String TotalMessage ;

    public NoteRecord(int DYear, int DMonth, int DDay,
                      int DHour, int DMinute,
                      int WYear, int WMonth, int WDay,
                      String shortTag, String totalMessage)
    {
        this.DYear = DYear;
        this.DMonth = DMonth;
        this.DDay = DDay;
        this.DHour = DHour;
        this.DMinute = DMinute;
        this.WYear = WYear;
        this.WMonth = WMonth;
        this.WDay = WDay;
        ShortTag = shortTag;
        TotalMessage = totalMessage;
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

    public int getDHour() {
        return DHour;
    }

    public int getDMinute() {
        return DMinute;
    }

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

    public String getTotalMessage() {
        return TotalMessage;
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

    public void setDHour(int DHour) {
        this.DHour = DHour;
    }

    public void setDMinute(int DMinute) {
        this.DMinute = DMinute;
    }

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

    public void setTotalMessage(String totalMessage) {
        TotalMessage = totalMessage;
    }
}
