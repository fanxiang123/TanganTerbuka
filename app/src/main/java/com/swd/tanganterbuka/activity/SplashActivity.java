package com.swd.tanganterbuka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swd.tanganterbuka.BaseActivity;
import com.swd.tanganterbuka.R;
import com.swd.tanganterbuka.http.RetrofitConfiguration;
import com.swd.tanganterbuka.modle.BaseResponse;
import com.swd.tanganterbuka.modle.GuId;
import com.swd.tanganterbuka.util.AdUtils;
import com.swd.tanganterbuka.util.BootReceiver;
import com.swd.tanganterbuka.util.LoadAppsUtil;
import com.swd.tanganterbuka.util.LogUtil;
import com.swd.tanganterbuka.util.ResUtils;
import com.swd.tanganterbuka.util.StorePreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;

public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SetGaid();
        initView();
        initData();
        loadMum();
    }

    private void initView() {
    }


    private void SetGaid() {
        if (StorePreferences.getInstance(SplashActivity.this).getGaid().isEmpty()) {
            try {
                AdUtils.AdInfo adInfo = AdUtils.getAdvertisingIdInfo(SplashActivity.this);
                if (null == adInfo) {
                    return;
                }
                String advertisingId = adInfo.getId();
                StorePreferences.getInstance(SplashActivity.this).setGaid(advertisingId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initData() {

    }


    private void loadMum() {
        if (StorePreferences.getInstance(SplashActivity.this).getGuid().isEmpty()) {
            Map<String, Object> rmap = new HashMap<>();
            String map = ResUtils.createRequestData(rmap, this);
            map = ResUtils.encryptedStr(map);
            RetrofitConfiguration.getService().getGuId(
                    RequestBody.create(MediaType.parse("text/plain"), map)
            ).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    try {
                    String decrStr = ResUtils.decryptStr(response.body());
                    JSONObject jsonObject =new JSONObject(decrStr);
                    String data = jsonObject.optString("data");
                    Gson gs = new Gson();
                    LogUtil.d("loadApps"+decrStr);
                    GuId guId = gs.fromJson(data, GuId.class);
                    StorePreferences.getInstance(SplashActivity.this).setGuid(guId.getGuid());
                    loadApps();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
//                finish();
                    Toast.makeText(SplashActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    LogUtil.e(t);
                }
            });
        }else{
            loadApps();
        }
    }

    private void loadApps(){
        List<String> list = LoadAppsUtil.loadApps(SplashActivity.this);
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("packageList",list);
        String map = ResUtils.createRequestData(rmap, this);
        map = ResUtils.encryptedStr(map);
        RetrofitConfiguration.getService().upApps(
                RequestBody.create(MediaType.parse("text/plain"), map)
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String decrStr = ResUtils.decryptStr(response.body());
                LogUtil.d("loadApps"+decrStr);
                Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                finish();
                Toast.makeText(SplashActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                LogUtil.e(t);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).withData(getIntent() != null ? getIntent().getData() : null).init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // if activity is in foreground (or in backstack but partially visible) launching the same
        // activity will skip onStart, handle this case with reInitSession
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).reInit();
    }

    private Branch.BranchReferralInitListener branchReferralInitListener = new Branch.BranchReferralInitListener() {
        @Override
        public void onInitFinished(JSONObject linkProperties, BranchError error) {

        }
    };
}
