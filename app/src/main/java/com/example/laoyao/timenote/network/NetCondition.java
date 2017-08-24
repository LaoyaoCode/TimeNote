package com.example.laoyao.timenote.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Laoyao on 2017/8/22.
 */

public class NetCondition
{
    //判断网络是否连接
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        if(mNetworkInfo != null)
        {

            return mNetworkInfo.isAvailable();
        }

        return false;
    }
}
