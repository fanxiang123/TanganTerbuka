package com.swd.tanganterbuka.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.swd.tanganterbuka.BaseActivity;
import com.swd.tanganterbuka.R;
import com.swd.tanganterbuka.dialog.LoadProgressDialog;
import com.swd.tanganterbuka.http.RetrofitConfiguration;
import com.swd.tanganterbuka.util.LogUtil;
import com.swd.tanganterbuka.util.ResUtils;
import com.swd.tanganterbuka.util.StorePreferences;
import com.swd.tanganterbuka.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title, xhx, code_btn;
    private Button login;
    private LinearLayout get_code;
    private EditText phone, code;
    private Timer timer;
    private int time = 60000;
    private TimerTask timerTask;
    private LoadProgressDialog loadProgressDialog;
    private int timess;
//    private LoginButton loginButton;
//    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
//        faceBookLogin();
    }

//    private void faceBookLogin() {
//        callbackManager = CallbackManager.Factory.create();
//        loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setReadPermissions("email", "public_profile");
//        // If using in a fragment
////        loginButton.setFragment(RegisterActivity.this);
//
//        // Callback registration
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                LogUtil.d("onSuccess");
//                // App code
//            }
//
//            @Override
//            public void onCancel() {
//                LogUtil.d("onCancel");
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                LogUtil.d("onError:"+exception);
//                // App code
//            }
//        });
//
//
//    }


    private void initView() {
        loadProgressDialog = new LoadProgressDialog(this, "Loging……");
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        xhx = findViewById(R.id.xhx);
        login = findViewById(R.id.login);
        phone = findViewById(R.id.phone);
        get_code = findViewById(R.id.get_code);
        code = findViewById(R.id.code);
        code_btn = findViewById(R.id.code_btn);
        code = findViewById(R.id.code);
        back.setOnClickListener(this);
        login.setOnClickListener(this);
        get_code.setOnClickListener(this);


        login.setEnabled(false);
//        get_code.setEnabled(false);
    }

    private void initData() {
        title.setText("");
        xhx.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        xhx.getPaint().setAntiAlias(true);//抗锯齿

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (String.valueOf(code.getText()).equals("")) {
                    login.setEnabled(false);
                    login.setBackgroundResource(R.drawable.shape_radius_login_btn_2_no);
                } else {
                    login.setEnabled(true);
                    login.setBackgroundResource(R.drawable.shape_radius_login_btn_2);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            finish();
        } else if (view.getId() == R.id.get_code) {
            if (String.valueOf(phone.getText()).equals(""))
                Toast.makeText(this, "phone Error", Toast.LENGTH_SHORT).show();
            else {
                countDown();
                getCode();
            }
        } else if (view.getId() == R.id.login) {
            loadProgressDialog.show();
            login();
        }
    }

    private void countDown() {
        get_code.setEnabled(false);
        get_code.setVisibility(View.GONE);
        timess = time / 1000;
        code_btn.setText(timess + "S");
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timess--;
                            code_btn.setText(timess + "S");
                            if (timess <= 0) {
                                stopTimer();
                                return;
                            }
                        }
                    });
                }
            };
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(timerTask, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        code_btn.setText("Kirim ulang kode");
        get_code.setEnabled(true);
        get_code.setVisibility(View.VISIBLE);
    }

    private void getCode() {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("mobile", String.valueOf(phone.getText()));
        String map = ResUtils.createRequestData(rmap, this);
        map = ResUtils.encryptedStr(map);
        RetrofitConfiguration.getService().getCode(
                RequestBody.create(MediaType.parse("text/plain"), map)
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    String decrStr = ResUtils.decryptStr(response.body());
                    LogUtil.d("decrStr:" + decrStr);
                    JSONObject jsonObject = new JSONObject(decrStr);
                    int code = jsonObject.optInt("code");
                    if (code != 0) {
                        Toast.makeText(RegisterActivity.this, "code Error", Toast.LENGTH_SHORT).show();
                        stopTimer();
                    } else if (code == 0) {
                        Utils.trackBranchApp(RegisterActivity.this,"_getcode");
                        Utils.trackEventApp(Utils.eventName + "_getcode", RegisterActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    stopTimer();
                    Toast.makeText(RegisterActivity.this, "code Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                finish();
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                LogUtil.e(t);
            }
        });
    }

    private void login() {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("mobile", String.valueOf(phone.getText()));
        rmap.put("code", String.valueOf(code.getText()));
        String map = ResUtils.createRequestData(rmap, this);
        map = ResUtils.encryptedStr(map);
        RetrofitConfiguration.getService().login(
                RequestBody.create(MediaType.parse("text/plain"), map)
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                loadProgressDialog.dismiss();
                try {
                    String decrStr = ResUtils.decryptStr(response.body());
                    JSONObject jsonObject = new JSONObject(decrStr);
                    LogUtil.d("login" + decrStr);
                    int code = jsonObject.optInt("code");
                    if (code == 0) {
                        Utils.trackBranchApp(RegisterActivity.this,"_login");
                        Utils.trackEventApp(Utils.eventName + "_login", RegisterActivity.this);
                        finish();
                        StorePreferences.getInstance(RegisterActivity.this).setUserInfo(jsonObject.optString("data"));
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(RegisterActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loadProgressDialog.dismiss();
//                finish();
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                LogUtil.e(t);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
