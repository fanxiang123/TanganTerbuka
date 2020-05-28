package com.swd.tanganterbuka.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.BranchEvent;
import io.branch.referral.util.LinkProperties;

public class Utils {

    public static final String eventName="TanganTerbuka";


    public static boolean isLocalApp(Context context, String appPkgName) {
        assert (context != null);
        if (TextUtils.isEmpty(appPkgName))
            return false;

        boolean flag = false;
        try {
            if (context.getPackageManager().getLaunchIntentForPackage(appPkgName) != null) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void trackEventApp(String value,Context context) {
        AppEventsLogger.newLogger(context).logEvent(value);
    }


    public static void trackBranchApp(Context context,String Key){
        new BranchEvent(eventName+Key)
//                .setDescription(eventName+Key)
                .logEvent(context);
        LogUtil.d("Branch埋点事件:"+Key);
    }

}
