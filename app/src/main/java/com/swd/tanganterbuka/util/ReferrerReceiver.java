package com.swd.tanganterbuka.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;


/**
 * Google监听广播
 */
public class ReferrerReceiver extends BroadcastReceiver {

    //https://play.google.com/store/apps/details?id=com.id.cashsaku&referrer=utm_source=N6GlE0Z9&utm_medium=渠道
    //utm_source=google-play&utm_medium=organic

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                String referrerString = extras.getString("referrer");
                if (null != referrerString && !TextUtils.isEmpty(referrerString)) {
                    LogUtil.d("referrerString: " + referrerString);
                    StorePreferences.getInstance(context).setRef(referrerString);

                }
            }
        }
    }

}
