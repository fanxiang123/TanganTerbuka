package com.swd.tanganterbuka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swd.tanganterbuka.BaseActivity;
import com.swd.tanganterbuka.R;
import com.swd.tanganterbuka.dialog.LoadProgressDialog;
import com.swd.tanganterbuka.dialog.SettingDialog;
import com.swd.tanganterbuka.util.CacheUtils;
import com.swd.tanganterbuka.util.DisplayUtil;
import com.swd.tanganterbuka.util.StorePreferences;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title,btn1;
    private Button login;
    private LinearLayout ll01;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
        getCacheSize();
    }

    private void initTitle() {
    }

    private void initView() {
        back = findViewById(R.id.back);
        btn1 = findViewById(R.id.btn1);
        title = findViewById(R.id.title);
        ll01 = findViewById(R.id.ll01);
        login = findViewById(R.id.login);
        back.setOnClickListener(this);
        ll01.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    private void initData() {
        title.setText("Pengaturan");
        if (!StorePreferences.getInstance(SettingActivity.this).getUserInfo().isEmpty()) {
            login.setVisibility(View.VISIBLE);
        }else{
            login.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        } else if (view.getId() == R.id.ll01) {
            LoadProgressDialog loadProgressDialog = new LoadProgressDialog(this,"Lodaing");
            CacheUtils.clearAllCache(this);
            getCacheSize();
            loadProgressDialog.dismiss();
        } else if (view.getId() == R.id.login) {
            SettingDialog settingDialog = new SettingDialog(this);
            Window window = settingDialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            //将宽度值dp转px
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //高度自适应(也可设置为固定值,同宽度设置方法)
            lp.height = DisplayUtil.dip2px(this,250);
            settingDialog.getWindow().setAttributes(lp);
            settingDialog.show();
        }
    }

    private void getCacheSize() {
        String cashSzize = "";
        try {
            cashSzize = CacheUtils.getTotalCacheSize(this);
            btn1.setText(cashSzize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
