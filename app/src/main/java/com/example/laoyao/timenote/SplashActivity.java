package com.example.laoyao.timenote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.laoyao.timenote.Tools.DateAndTime;
import com.example.laoyao.timenote.Tools.MLog;
import com.example.laoyao.timenote.network.* ;

public class SplashActivity extends AppCompatActivity
{
    private static final String TAG = "SplashActivity";

    //暂停的时间
    public final static int WaitTime = 2000 ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(WaitTime);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                //切换到主显示界面
                Intent beginMainTask = new Intent( SplashActivity.this, NoteDisplayActivity.class) ;
                startActivity(beginMainTask);

                //结束当前活动
                finish();
            }
        }).start(); ;
    }
}
