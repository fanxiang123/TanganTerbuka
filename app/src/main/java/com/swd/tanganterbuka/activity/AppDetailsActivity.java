package com.swd.tanganterbuka.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.swd.tanganterbuka.BaseActivity;
import com.swd.tanganterbuka.R;
import com.swd.tanganterbuka.adapter.AppAdapterMain;
import com.swd.tanganterbuka.http.RetrofitConfiguration;
import com.swd.tanganterbuka.modle.AppDetailsModle;
import com.swd.tanganterbuka.modle.BaseResponse;
import com.swd.tanganterbuka.modle.GuId;
import com.swd.tanganterbuka.util.GoogleSkip;
import com.swd.tanganterbuka.util.LogUtil;
import com.swd.tanganterbuka.util.ResUtils;
import com.swd.tanganterbuka.util.StorePreferences;
import com.swd.tanganterbuka.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppDetailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back, download, icon;
    private TextView title, name, num, mm_money, mm_day, money, day,
            num1, num2, num3,num4;
    private int id;

    private String packName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);
        Intent intent = getIntent();
        id = Integer.valueOf(intent.getAction());
        initView();
        initData();
        loadAppDetails();
    }

    private void initView() {
        back = findViewById(R.id.back);
        icon = findViewById(R.id.icon);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);

        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        num = findViewById(R.id.num);
        mm_money = findViewById(R.id.mm_money);
        mm_day = findViewById(R.id.mm_day);
        download = findViewById(R.id.download);
        day = findViewById(R.id.day);
        money = findViewById(R.id.money);
        back.setOnClickListener(this);
        download.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        } else if (view.getId() == R.id.download) {
            if (packName !=""&&!packName.isEmpty()){
                Utils.trackEventApp(Utils.eventName + "_clickdown",AppDetailsActivity.this);
                Utils.trackBranchApp(AppDetailsActivity.this,"_clickdown");
                GoogleSkip.rateNow(this, packName);
            }
        }
    }

    private void loadAppDetails() {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("pid", id);
        String map = ResUtils.createRequestData(rmap, this);
        map = ResUtils.encryptedStr(map);
        RetrofitConfiguration.getService().appsInfo(
                RequestBody.create(MediaType.parse("text/plain"), map)
        ).enqueue(new Callback<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    String decrStr = ResUtils.decryptStr(response.body());
                    LogUtil.d("loadAppDetails" + decrStr);
                    JSONObject jsonObject = new JSONObject(decrStr);
                    String data = jsonObject.optString("data");
                    Gson gs = new Gson();
                    AppDetailsModle appDetailsModle = gs.fromJson(data, AppDetailsModle.class);
                    Glide.with(getApplicationContext()).
                            load(appDetailsModle.getIcon())
                            .asBitmap().fitCenter() //刷新后变形问题
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(icon);
                    name.setText(appDetailsModle.getProduct_name());
                    title.setText(appDetailsModle.getProduct_name());
                    num1.setText("Rp " + appDetailsModle.getInterest_template().getRepay_amount());
                    num2.setText("Rp " + appDetailsModle.getInterest_template().getLoan_amount());
                    num3.setText("Rp " + appDetailsModle.getInterest_template().getInterest_admin());
                    num4.setText("Pinjam "+appDetailsModle.getInterest_template().getLoan_amount()+" mudah disetujui");

                    num.setText(appDetailsModle.getPass_rate_score());
                    money.setText("Rp " + appDetailsModle.getInterest_template().getLoan_amount());
                    day.setText(appDetailsModle.getInterest_template().getCycle() + " hari");
                    mm_money.setText("(Rp +" + appDetailsModle.getPrice_min() + "~" +
                            appDetailsModle.getPrice_max() + ")");
                    mm_day.setText("(" + appDetailsModle.getInterest_template().getCycle() + "~" +
                            appDetailsModle.getInterest_template().getMax_cycle() + " hari)");
                    packName = appDetailsModle.getPackage_name();
                    Utils.trackBranchApp(AppDetailsActivity.this,"_detailview");
                    Utils.trackEventApp(Utils.eventName + "_detailview",AppDetailsActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.e(t);
            }
        });
    }
}
