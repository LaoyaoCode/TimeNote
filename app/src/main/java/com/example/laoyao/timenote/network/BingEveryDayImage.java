package com.example.laoyao.timenote.network;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.laoyao.timenote.Tools.* ;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by Laoyao on 2017/8/21.
 */

public class BingEveryDayImage
{
    private static final String TAG = "BingEveryDayImage";

    //获得图片信息的URL地址
    private static final String GetImageMessageUrl = "http://cn.bing.com/HPImageArchive.aspx?format=xml&idx=0&n=1" ;
    //图片地址头部
    private static final String ImageUrlHeader = "http://s.cn.bing.net" ;
    //图片存储文件夹名字
    private static final String ImageDirName = "DImage" ;
    //上下文
    private Context GiveContext = null ;
    //保存必应每日文件的share Predereneces 文件名
    private static final String SPreferencesFileName = "BEDImageData" ;
    //SP中保存图片文件名键
    private static final String SPImageNameKey = "ImageName" ;
    //SP中保时间键
    private static final String SPTimeKey = "Time" ;
    //缓冲大小
    private static final int CacheSize = 1024 ;
    //图片文件夹绝对地址 ， 没有分隔符
    private static String ImageDirPath = null ;

    public enum GettingResult
    {
        /**
         * 没有网络连接
         */
        NoNetConnect ,
        /**
         * 成功
         */
        Success ,
        /**
         * 获取失败
         */
        Failed ,
        /**
         * 文件早已下载
         */
        AlreadyDownlaoded
    }

    public enum BeginState
    {
        /**
         * 开始时候已经缓存了图片
         */
        HadImage ,
        /**
         * 开始的时候没有缓存图片
         */
        NotHave
    }

    public interface IBingImage
    {
        void Begin(BeginState state , String imagePath) ;
        void DownFinished(GettingResult state , String imagePath) ;
    }

    public BingEveryDayImage(Context context)
    {
        GiveContext = context ;

        try
        {
            ImageDirPath = GiveContext.getFilesDir().getAbsolutePath() + "/" + ImageDirName ;

            File imageDir = new File(ImageDirPath) ;
            if(!imageDir.exists())
            {
                imageDir.mkdir() ;
            }

            MLog.v(TAG , imageDir.getAbsolutePath());
            MLog.v(TAG , GiveContext.getFilesDir().getAbsolutePath());
        }
        catch (Exception e)
        {
            MLog.e(TAG , e.toString()) ;
        }
    }

    public void GetImage(final IBingImage iBing)
    {
        String name = null ;
        final SharedPreferences sp = GiveContext.getSharedPreferences(SPreferencesFileName , Context.MODE_PRIVATE) ;
        name = sp.getString(SPImageNameKey , null) ;

        if(name == null)
        {
            if(iBing != null)
            {
                iBing.Begin(BeginState.NotHave , null);
            }
        }
        //之间已经下载了图片
        else
        {
            String time = sp.getString(SPTimeKey , null) ;

            if(iBing != null)
            {
                iBing.Begin(BeginState.HadImage , name);
            }

            //今天已经下载了图片
            if(time != null && time.equals(DateAndTime.GetDateString()))
            {
                //通知为早已经下载
                if(iBing != null)
                {
                    iBing.DownFinished(GettingResult.AlreadyDownlaoded , null);
                }

                MLog.v(TAG , "Bing Image Already Downlaoded");
                //结束方法返回
                return ;
            }
        }


        if(!NetCondition.isNetworkAvailable(GiveContext))
        {
            //通知为无网络连接
            if(iBing != null)
            {
                iBing.DownFinished(GettingResult.NoNetConnect , null);
            }
            return;
        }

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String xmlData = null ;
                String url = null ;
                String imagePath = null ;

                try
                {
                    OkHttpClient client = new OkHttpClient() ;
                    Request request = new Request.Builder().url(GetImageMessageUrl).build() ;
                    Response response = client.newCall(request).execute() ;

                    xmlData = response.body().string() ;
                    url = XmlParser.GetBingImageMessage(xmlData) ;

                    if(url != null)
                    {
                        BufferedOutputStream writer = null ;
                        InputStream in = null ;
                        Boolean isSucess = false ;

                        url = ImageUrlHeader + url ;
                        MLog.v(TAG , url);

                        String[] values = url.split("\\.") ;
                        imagePath = ImageDirPath + '/' + MD5.md5(DateAndTime.GetDateString() + (new Random()).nextDouble()) + '.' + values[values.length - 1];
                        MLog.v(TAG , imagePath);

                        try
                        {
                            OkHttpClient downClent = new OkHttpClient() ;
                            Request downRequest = new Request.Builder().url(url).build() ;
                            Response downResponse = client.newCall(downRequest).execute() ;

                            if(downResponse != null)
                            {
                                byte[] cache = new byte[CacheSize] ;
                                in = downResponse.body().byteStream() ;
                                int length = 0 ;

                                File saveFile = new File(imagePath ) ;
                                if(!saveFile.exists())
                                {
                                    saveFile.createNewFile() ;
                                }

                                writer = new BufferedOutputStream(new FileOutputStream(saveFile));

                                while ((length = in.read(cache)) != -1)
                                {
                                    writer.write(cache , 0 , length) ;
                                }

                                isSucess = true ;
                            }
                        }
                        catch (Exception e)
                        {
                            if(iBing != null)
                            {
                                //获取失败
                                iBing.DownFinished(GettingResult.Failed , null);
                            }
                        }
                        finally
                        {
                            if(in != null)
                            {
                                in.close();
                            }

                            if(writer != null)
                            {
                                writer.close();
                            }

                            //成功获取新图片
                            if(isSucess)
                            {
                                String originalFilePath = null ;

                                //通知成功写入
                                if(iBing != null)
                                {
                                    iBing.DownFinished(GettingResult.Success , imagePath);
                                }

                                //删除原有文件
                                originalFilePath = sp.getString(SPImageNameKey,null) ;
                                if(originalFilePath != null)
                                {
                                    File file = new File(originalFilePath) ;

                                    if(file.exists())
                                    {
                                        file.delete();
                                    }
                                }

                                //写入图片地址和下载时间
                                SharedPreferences.Editor editor = sp.edit() ;
                                editor.putString(SPImageNameKey , imagePath) ;
                                editor.putString(SPTimeKey , DateAndTime.GetDateString()) ;
                                editor.apply();
                            }
                            //获取新图片失败
                            else
                            {
                                //删除已经创建的文件
                                File file = new File(imagePath) ;
                                if(file.exists())
                                {
                                    file.delete() ;
                                }

                                if(iBing != null)
                                {
                                    iBing.DownFinished(GettingResult.Failed , null) ;
                                }
                            }
                        }
                    }
                    else
                    {
                        if(iBing != null)
                        {
                            //获取失败
                            iBing.DownFinished(GettingResult.Failed , null);
                        }
                    }
                }
                catch (Exception e)
                {
                    if(iBing != null)
                    {
                        //获取失败
                        iBing.DownFinished(GettingResult.Failed , null);
                    }
                    MLog.e(TAG , e.toString());
                }

            }
        }).start();
    }
}


