package com.swd.tanganterbuka;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.swd.tanganterbuka.util.BootReceiver;
import com.swd.tanganterbuka.util.StatusBarUtil;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private BootReceiver installedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (installedReceiver == null) {
            installedReceiver = new BootReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.PACKAGE_ADDED");
            filter.addAction("android.intent.action.PACKAGE_REMOVED");
            filter.addDataScheme("package");
            this.registerReceiver(installedReceiver, filter);

        }
        setStatusBar();
    }

    protected void setStatusBar() {
        //这里做了两件事情，1.使状态栏透明并使contentView填充到状态栏 2.预留出状态栏的位置，防止界面上的控件离顶部靠的太近。这样就可以实现开头说的第二种情况的沉浸式状态栏了
        StatusBarUtil.setTransparent(this);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //点击其他地方软键盘小时方法
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.getCurrentFocus() != null) {
                if (this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onDestroy() {
        if (installedReceiver != null) {
            this.unregisterReceiver(installedReceiver);
            installedReceiver = null;
        }

        super.onDestroy();
    }
}
