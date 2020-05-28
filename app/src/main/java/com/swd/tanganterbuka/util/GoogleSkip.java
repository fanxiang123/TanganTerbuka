package com.swd.tanganterbuka.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class GoogleSkip {
    public static void rateNow(final Context context,String packageName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            intent.setPackage("com.android.vending");//这里对应的是谷歌商店，跳转别的商店改成对应的即可
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {//没有应用市场，通过浏览器跳转到Google Play
                Intent intent2 = new Intent(Intent.ACTION_VIEW);
                intent2.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                if (intent2.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent2);
                } else {
                    //没有Google Play 也没有浏览器
                }
            }
        } catch (ActivityNotFoundException activityNotFoundException1) {
            LogUtil.e("GoogleMarket Intent not found");
        }
    }
}
