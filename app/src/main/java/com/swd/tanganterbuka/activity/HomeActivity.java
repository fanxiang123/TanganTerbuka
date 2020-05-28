package com.swd.tanganterbuka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.oned.rss.RSSUtils;
import com.swd.tanganterbuka.BaseActivity;
import com.swd.tanganterbuka.R;
import com.swd.tanganterbuka.util.ResUtils;
import com.swd.tanganterbuka.util.StorePreferences;

public class HomeActivity extends BaseActivity implements View.OnClickListener{
    private ImageView back;
    private TextView title,login_tv,name;
    private Button login;
    private LinearLayout setting,ll1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }


    private void initView() {
        back = findViewById(R.id.back);
        ll1 = findViewById(R.id.ll1);
        back.setOnClickListener(this);
        ll1.setOnClickListener(this);
        name = findViewById(R.id.name);
        title = findViewById(R.id.title);
        setting = findViewById(R.id.setting);
        setting.setOnClickListener(this);
        login = findViewById(R.id.login);
        login_tv = findViewById(R.id.login_tv);
        login.setOnClickListener(this);
        login_tv.setOnClickListener(this);
    }

    private void initData() {
        title.setText("Home");
        if (StorePreferences.getInstance(HomeActivity.this).getUserInfo().isEmpty()) {
            login.setVisibility(View.VISIBLE);
            login_tv.setText("Anda Belum Login~");
            name.setText("Hai!");
        }else{
            login.setVisibility(View.GONE);
            login_tv.setText("");
            String mobile = ResUtils.getUser(this).getUser_mobile();
           String  phonenum = mobile.substring(0, 3) +
                   "***" + mobile.substring(mobile.length()-5, mobile.length()-1);
            name.setText(phonenum);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back){
            finish();
        }else if(view.getId() == R.id.login){
            finish();
            Intent intent=new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.setting){
            Intent intent=new Intent(this, SettingActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.ll1){
            Intent intent=new Intent(this, WebViewActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
