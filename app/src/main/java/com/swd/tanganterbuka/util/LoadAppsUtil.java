package com.swd.tanganterbuka.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

public class LoadAppsUtil {
    /**
     * 查看手机所有包名
     * @param context
     */
    public static List<String> loadApps(Context context) {
        List<ResolveInfo> apps = new ArrayList<>();
        List<String> appload = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        apps = context.getPackageManager().queryIntentActivities(intent, 0);
        //for循环遍历ResolveInfo对象获取包名和类名
        for (int i = 0; i < apps.size(); i++) {
            ResolveInfo info = apps.get(i);
            appload.add(info.activityInfo.packageName);
        }
        return appload;
    }


}
