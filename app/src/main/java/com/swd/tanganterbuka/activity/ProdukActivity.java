package com.swd.tanganterbuka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swd.tanganterbuka.BaseActivity;
import com.swd.tanganterbuka.R;
import com.swd.tanganterbuka.adapter.AppAdapterMain;
import com.swd.tanganterbuka.adapter.ProdukAdapterMain;
import com.swd.tanganterbuka.http.RetrofitConfiguration;
import com.swd.tanganterbuka.modle.AppModle;
import com.swd.tanganterbuka.modle.Debit;
import com.swd.tanganterbuka.modle.Produk;
import com.swd.tanganterbuka.util.LogUtil;
import com.swd.tanganterbuka.util.ResUtils;
import com.swd.tanganterbuka.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title;
    private RecyclerView mRecyclerView;
    private ArrayList<AppModle> produks;
    private SwipeRefreshLayout mSwipeLayout;
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);
        Intent intent = getIntent();
        String action = intent.getAction();
        type=(action.equals("0"))?0:1;
        initView();
        initData();
    }

    private void initView() {
        mSwipeLayout = findViewById(R.id.swipe_ly);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        back.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.list_view);
        if (type != 0)
            title.setText("Produkterbaru");
        else
            title.setText("Mudahdisetujui");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back){
            finish();
        }
    }

    private void initData() {
        mSwipeLayout.setRefreshing(true);
        // 设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        ProdukAdapterMain appAdapterMain = new ProdukAdapterMain(this,produks,type);
        mRecyclerView.setAdapter(appAdapterMain);
        //设置在listview上下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMunProduk();
            }
        });
        loadMunProduk();
    }

    private void loadMunProduk(){
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("start",0);
        if (type == 0)
            rmap.put("sort","fast_sort");
        else
            rmap.put("sort","new_sort");
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
                    LogUtil.d("loadMunProduk"+decrStr);
                    JSONObject jsonObject = new JSONObject(decrStr);
                    String data = jsonObject.optString("data");
                    Gson gs = new Gson();
                    produks= gs.fromJson(data,
                            new TypeToken<List<AppModle>>() {}.getType());
                    produks.add(0, null);
                    ProdukAdapterMain appAdapterMain = new ProdukAdapterMain(ProdukActivity.this,produks,type);
                    mRecyclerView.setAdapter(appAdapterMain);
                    mSwipeLayout.setRefreshing(false);
                    if (type == 0) {
                        Utils.trackBranchApp(ProdukActivity.this,"_fastview");
                        Utils.trackEventApp(Utils.eventName + "_fastview", ProdukActivity.this);
                    }else {
                        Utils.trackEventApp(Utils.eventName + "_newview", ProdukActivity.this);
                        Utils.trackBranchApp(ProdukActivity.this,"_newview");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                finish();
                Toast.makeText(ProdukActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                LogUtil.e(t);
                mSwipeLayout.setRefreshing(false);
            }
        });
    }
}
