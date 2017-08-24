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


    public static class WrongStringFormatException extends Exception
    {

    }

    public enum CompareResult
    {
        Bigger ,
        Equal ,
        Smaller ,
        Error
    }

    public static class StructedDate
    {
        private int Year = 0 ;
        private int Month = 0 ;
        private int Day = 0 ;

        public StructedDate(String date) throws WrongStringFormatException
        {
            String[] values = date.split("-") ;

            if(values.length != 3)
            {
                throw new WrongStringFormatException() ;
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
                throw new WrongStringFormatException() ;
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

        /**
         *
         * @param a 比较对象A
         * @param b 比较对象B
         * @return Bigger A > B , Equal A = B  ,Smaller A < B , Error出错了
         */
        public static CompareResult Compare(StructedDate a , StructedDate b)
        {
            if(a.getYear() > b.getYear())
            {
                return CompareResult.Bigger ;
            }
            else if(a.getYear() < b.getYear())
            {
                return CompareResult.Smaller;
            }
            else
            {
                if(a.getMonth() > b.getMonth())
                {
                    return CompareResult.Bigger ;
                }
                else if(a.getMonth() < b.getMonth())
                {
                    return CompareResult.Smaller;
                }
                else
                {
                    if(a.getDay() > b.getDay())
                    {
                        return CompareResult.Bigger ;
                    }
                    else if(a.getDay() < b.getDay())
                    {
                        return CompareResult.Smaller;
                    }
                    else
                    {
                        return CompareResult.Equal ;
                    }
                }
            }
        }

        /**
         *
         * @param a 比较日期字符串A
         * @param b 比较日期字符串B
         * @return Bigger A > B , Equal A = B  ,Smaller A < B , Error出错了
         */
        public static CompareResult Compare(String a , String b)
        {
            try
            {
                StructedDate dateA = new StructedDate(a) ;
                StructedDate dateB = new StructedDate(b) ;

                return StructedDate.Compare(dateA , dateB) ;
            }
            //字符串解析出错则返回错误枚举体
            catch (WrongStringFormatException e)
            {
                return CompareResult.Error ;
            }
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
    }

    public static class StructedTime
    {
        private int Hour ;
        private int Minute ;

        public StructedTime(String time) throws WrongStringFormatException
        {
            String[] values = time.split(":") ;

            if(values.length != 2)
            {
                throw new WrongStringFormatException() ;
            }

            try
            {
                Hour = Integer.parseInt(values[0]) ;
                Minute = Integer.parseInt(values[1]) ;

                MLog.v(TAG , Hour + ":" + Minute );
            }
            catch (Exception e)
            {
                throw new WrongStringFormatException() ;
            }
        }

        public int getHour() {
            return Hour;
        }

        public int getMinute() {
            return Minute;
        }

        /**
         *
         * @param a 比较对象A
         * @param b 比较对象B
         * @return Bigger A > B , Equal A = B  ,Smaller A < B , Error出错了
         */
        public static CompareResult Compare(StructedTime a , StructedTime b)
        {
            if(a.getHour() > b.getHour())
            {
                return CompareResult.Bigger ;
            }
            else if(a.getHour() < b.getHour())
            {
                return CompareResult.Smaller ;
            }
            else
            {
                if(a.getMinute() > b.getMinute())
                {
                    return CompareResult.Bigger ;
                }
                else if(a.getMinute() < b.getMinute())
                {
                    return CompareResult.Smaller ;
                }
                else
                {
                    return CompareResult.Equal ;
                }
            }
        }

        /**
         *
         * @param a 比较字符串A
         * @param b 比较字符串B
         * @return Bigger A > B , Equal A = B  ,Smaller A < B , Error出错了
         */
        public static CompareResult Compare(String a , String b)
        {
            try
            {
                StructedTime timeA = new StructedTime(a) ;
                StructedTime timeB = new StructedTime(b) ;

                return StructedTime.Compare(timeA , timeB) ;
            }
            catch (WrongStringFormatException e)
            {
                return CompareResult.Error ;
            }
        }
    }

    //test success , 2017.8.22 laoyao
    public static String GetDateString()
    {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String GetTime()
    {
        Date date=new Date();
        DateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
}


