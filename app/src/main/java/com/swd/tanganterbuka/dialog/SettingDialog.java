package com.swd.tanganterbuka.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.swd.tanganterbuka.R;
import com.swd.tanganterbuka.activity.SettingActivity;
import com.swd.tanganterbuka.util.ResUtils;
import com.swd.tanganterbuka.util.StorePreferences;

import androidx.annotation.NonNull;

public class SettingDialog  extends Dialog {

    private TextView batal,yakin;

    public SettingDialog(@NonNull final Context context) {
        super(context);
        View view = View.inflate(context, R.layout.dialog_setting,null);
        setContentView(view);

        batal = view.findViewById(R.id.batal);
        yakin = view.findViewById(R.id.yakin);
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        yakin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorePreferences.getInstance(context).setUserInfo(null);
                ResUtils.logout(context);
                Activity activity = (Activity) context;
                activity.finish();
                dismiss();
            }
        });
    }
}
