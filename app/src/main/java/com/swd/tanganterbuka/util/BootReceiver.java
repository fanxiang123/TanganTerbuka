package com.swd.tanganterbuka.util;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.swd.tanganterbuka.activity.MainActivity;
import com.swd.tanganterbuka.activity.SplashActivity;
import com.swd.tanganterbuka.http.RetrofitConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            LogUtil.d("安装了:" + packageName + "包名的程序");
            loadApps(context);
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            LogUtil.d("卸载了:" + packageName + "包名的程序");

        }
    }

    private void loadApps(final Context context){
        List<String> list = LoadAppsUtil.loadApps(context);
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("packageList",list);
        String map = ResUtils.createRequestData(rmap, context);
        map = ResUtils.encryptedStr(map);
        RetrofitConfiguration.getService().upApps(
                RequestBody.create(MediaType.parse("text/plain"), map)
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String decrStr = ResUtils.decryptStr(response.body());
                LogUtil.d("BootReceiver loadApps"+decrStr);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                finish();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                LogUtil.e(t);
            }
        });
    }
}
