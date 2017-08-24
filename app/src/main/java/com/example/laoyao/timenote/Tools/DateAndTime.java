package com.example.laoyao.timenote.Tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Laoyao on 2017/8/22.
 */

public class DateAndTime
{
    private static final String TAG = "DateAndTime";

    /*
    public static class WrongDateStringFormatException extends Exception
    {

    }

    public static class StructedDate
    {
        private int Year = 0 ;
        private int Month = 0 ;
        private int Day = 0 ;

        public StructedDate(String date) throws WrongDateStringFormatException
        {
            String[] values = date.split("-") ;

            if(values.length != 3)
            {
                throw new WrongDateStringFormatException() ;
            }

            try
            {
                Year = Integer.parseInt(values[0]) ;
                Month = Integer.parseInt(values[1]) ;
                Day = Integer.parseInt(values[2]) ;

                MLog.v(TAG , Year + "-" + Month + "-" + Day);
            }
            catch (Exception e)
            {
                throw new WrongDateStringFormatException() ;
            }
        }

        public int getYear()
        {
            return Year;
        }

        public int getMonth()
        {
            return Month;
        }

        public int getDay()
        {
            return Day;
        }

        @Override
        public boolean equals(Object obj)
        {
            if(obj instanceof StructedDate)
            {
                StructedDate date = (StructedDate)obj ;
                if(date.getYear() == getYear() &&
                        date.getMonth() == getMonth() &&
                        date.getDay() == getDay())
                {
                    return true ;
                }
                else
                {
                    return super.equals(obj) ;
                }
            }
            else
            {
                return super.equals(obj) ;
            }
        }
    }*/

    //test success , 2017.8.22 laoyao
    public static String GetDateString()
    {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}


