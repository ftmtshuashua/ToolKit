package com.acap.toolkit.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.LifecycleEventObserver;

import com.acap.toolkit.action.Action;


/**
 * <pre>
 * Tip:
 *      Home键监听器 , 监听用户点击了Home键
 *
 * Created by ACap on 2021/5/27 10:04
 * </pre>
 */
public class HomeKeyMonitor extends BroadcastReceiver {
    private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";       //表示按了Home键，程序到了后台
    private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";  //长按Home键，显示最近使用的程序列表

    private Action mAction;
    private Context mContext;
    //表示活跃状态 - 用户处于应用内
    private boolean mActive = true;

    private HomeKeyMonitor(Context context) {
        mContext = context;
        context.registerReceiver(this, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!mActive) return;
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (!TextUtils.isEmpty(reason)) {
                switch (reason) {
                    case SYSTEM_DIALOG_REASON_HOME_KEY://有出现按键时，home建是这个key
                    case SYSTEM_DIALOG_REASON_RECENT_APPS://有出现按键时，点击方块按键是这个key；或者手势导航时，也是这个key
                        if (mAction != null) {
                            mAction.call();
                        }
                        break;
                }
            }
        }
    }

    //设置回调监听
    public void setOnClickHomeKey(Action mAction) {
        this.mAction = mAction;
    }

    //释放
    public void release() {
        mContext.unregisterReceiver(this);
    }

    public static final ActivityBuild of(ComponentActivity activity) {
        return new ActivityBuild(activity);
    }


    public static class ActivityBuild {
        ComponentActivity activity;
        private Action mAction;

        public ActivityBuild(ComponentActivity activity) {
            this.activity = activity;
        }

        //设置回调监听
        public ActivityBuild setOnClickHomeKey(Action mAction) {
            this.mAction = mAction;
            return this;
        }

        public HomeKeyMonitor build() {
            HomeKeyMonitor homeKeyMonitor = new HomeKeyMonitor(activity);
            homeKeyMonitor.setOnClickHomeKey(mAction);


            activity.getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
                switch (event) {
                    case ON_DESTROY:
                        homeKeyMonitor.release();
                        break;
                    case ON_RESUME:
                        homeKeyMonitor.mActive = true;
                        break;
                    case ON_PAUSE:
                        homeKeyMonitor.mActive = false;
                        break;
                }
            });

            return homeKeyMonitor;
        }
    }

}
