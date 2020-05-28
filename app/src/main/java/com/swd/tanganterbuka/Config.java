package com.swd.tanganterbuka;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Config {
    public static final String APP_PACKAGE = "com.swd.tanganterbuka";
    public static final String APP_VERSION = "1.0";
    public static final String REF = "";
    public static final String APP_TYPE = "android";
    public static final String CHANNEL = "App";

    public static String getSign(){
        return "";
    }
    public static String getGuid(){
        return "";
    }

    public static final String POSITION = "0.0";

    public static String getUserId(){
        return "";
    }
    public static String getVersion(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionName="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
