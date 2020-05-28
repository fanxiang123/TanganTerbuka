package com.swd.tanganterbuka;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;

import io.branch.referral.Branch;
import io.branch.referral.BuildConfig;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        FacebookSdk.setApplicationId(String.valueOf(R.string.facebook_app_id));
        //初始化Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());
        if(BuildConfig.DEBUG){
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

            //正式版需要移除
            Branch.enableDebugMode();
        }

        // Branch logging for debugging
        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);

        super.onCreate();

    }

}
