package com.example.laoyao.timenote;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import com.kyleduo.switchbutton.SwitchButton ;
import com.example.laoyao.timenote.Tools.* ;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener
{

    private SwitchButton MonthModeButton;
    private SwitchButton NotificationOpenButton ;
    private SwitchButton LedButton  ;
    private SwitchButton DateChangeButton ;
    private SwitchButton AppOpenButton ;
    private SettingManager Manager ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        MonthModeButton = (SwitchButton)findViewById(R.id.monthModeSwitchButton) ;
        NotificationOpenButton = (SwitchButton)findViewById(R.id.openNotificationSwitchButton) ;
        LedButton = (SwitchButton)findViewById(R.id.openLEDSwitchButton) ;
        DateChangeButton = (SwitchButton)findViewById(R.id.dateChangeSwitchButton) ;
        AppOpenButton = (SwitchButton)findViewById(R.id.appOpenSwitchButton) ;
        Manager = new SettingManager(this) ;

        MonthModeButton.setOnCheckedChangeListener(this);
        NotificationOpenButton.setOnCheckedChangeListener(this);
        LedButton.setOnCheckedChangeListener(this);
        DateChangeButton.setOnCheckedChangeListener(this);
        AppOpenButton.setOnCheckedChangeListener(this);

        MonthModeButton.setChecked(Manager.IsMonthMode());

        boolean isOpenNotification = Manager.IsOpenNotification() ;
        NotificationOpenButton.setChecked(isOpenNotification);

        LedButton.setChecked(Manager.IsLED());
        DateChangeButton.setChecked(Manager.IsDateChangeNotification());
        AppOpenButton.setChecked(Manager.IsAppOpenNotification());

        //没有打开notification按钮则直接关闭禁用所有的附加选项
        if(!isOpenNotification)
        {
            SetNotificationClose();
        }
        else
        {
            SetNotificationOpen();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        switch (compoundButton.getId())
        {
            case R.id.monthModeSwitchButton:
                Manager.SetMonthMode(b);
                break ;
            case R.id.openNotificationSwitchButton:
                Manager.SetOpenNotification(b);
                if(!b)
                {
                    SetNotificationClose();
                }
                else
                {
                    SetNotificationOpen();
                }
                break ;
            case R.id.openLEDSwitchButton:
                Manager.SetLED(b);
                break;
            case R.id.dateChangeSwitchButton:
                Manager.SetDateChangeNotification(b) ;
                break;
            case R.id.appOpenSwitchButton:
                Manager.SetAppOpenNotification(b);
                break ;
        }
    }

    private void SetNotificationClose()
    {
        LedButton.setChecked(false);
        LedButton.setEnabled(false);
        Manager.SetLED(false);

        DateChangeButton.setChecked(false);
        DateChangeButton.setEnabled(false);
        Manager.SetDateChangeNotification(false);

        AppOpenButton.setChecked(false);
        AppOpenButton.setEnabled(false);
        Manager.SetAppOpenNotification(false);
    }

    private void SetNotificationOpen()
    {
        LedButton.setEnabled(true);

        DateChangeButton.setEnabled(true);

        AppOpenButton.setEnabled(true);
    }
}
