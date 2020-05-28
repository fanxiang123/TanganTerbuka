package com.swd.tanganterbuka.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swd.tanganterbuka.BaseActivity;
import com.swd.tanganterbuka.R;
import com.swd.tanganterbuka.adapter.AppAdapterMain;
import com.swd.tanganterbuka.http.RetrofitConfiguration;
import com.swd.tanganterbuka.modle.AppModle;
import com.swd.tanganterbuka.modle.BaseResponse;
import com.swd.tanganterbuka.modle.Debit;
import com.swd.tanganterbuka.modle.GuId;
import com.swd.tanganterbuka.modle.PhoneResult;
import com.swd.tanganterbuka.util.AdUtils;
import com.swd.tanganterbuka.util.LoadAppsUtil;
import com.swd.tanganterbuka.util.LogUtil;
import com.swd.tanganterbuka.util.ResUtils;
import com.swd.tanganterbuka.util.StorePreferences;
import com.swd.tanganterbuka.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<AppModle> debits;
    private  SwipeRefreshLayout mSwipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.trackBranchApp(this,"_main");
        Utils.trackEventApp(Utils.eventName + "_main",this);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.list_view);
        mSwipeLayout = findViewById(R.id.swipe_ly);
    }

    private void initData() {
        mSwipeLayout.setRefreshing(true);
        // 设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //设置在listview上下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMun();
            }
        });
        loadMun();
    }

    private void loadMun(){
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("start",0);
        rmap.put("sort","");
        rmap.put("limit",100);
        String map = ResUtils.createRequestData(rmap, this);
        map = ResUtils.encryptedStr(map);
        RetrofitConfiguration.getService().listApps(
                RequestBody.create(MediaType.parse("text/plain"), map)
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String decrStr = ResUtils.decryptStr(response.body());
                try {
                LogUtil.d("loadMun"+decrStr);
                JSONObject jsonObject = new JSONObject(decrStr);
                String data = jsonObject.optString("data");
                Gson gs = new Gson();
                debits= gs.fromJson(data,
                        new TypeToken<List<AppModle>>() {}.getType());
                debits.add(0, null);
                Utils.trackBranchApp(MainActivity.this,"_homeview");
                Utils.trackEventApp(Utils.eventName + "_homeview",MainActivity.this);
                AppAdapterMain appAdapterMain = new AppAdapterMain(MainActivity.this,debits);
                mRecyclerView.setAdapter(appAdapterMain);
                mSwipeLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                finish();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                LogUtil.e(t);
                mSwipeLayout.setRefreshing(false);
            }
        });
    }

}
