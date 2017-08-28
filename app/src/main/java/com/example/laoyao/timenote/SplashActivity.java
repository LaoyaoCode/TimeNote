package com.example.laoyao.timenote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.laoyao.timenote.Tools.DateAndTime;
import com.example.laoyao.timenote.Tools.MLog;
import com.example.laoyao.timenote.Tools.NotificationBulider;
import com.example.laoyao.timenote.Tools.SettingManager;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                /*
                try
                {
                    Thread.sleep(WaitTime);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }*/

                SettingManager manager = new SettingManager(SplashActivity.this) ;

                if(manager.IsAppOpenNotification())
                {
                    //显示notification
                    NotificationBulider.OpenApp(SplashActivity.this);
                }

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
