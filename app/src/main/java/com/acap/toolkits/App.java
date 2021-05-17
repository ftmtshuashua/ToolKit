package com.acap.toolkits;

import android.app.Application;

import com.acap.toolkit.app.AppUtils;
import com.acap.toolkit.app.GlobalCrashIntercept;
import com.acap.toolkit.phone.DeviceUtils;

/**
 * <pre>
 * Tip:
 *
 * Function:
 *
 * Created by ACap on 2018/10/31 11:24
 * </pre>
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        GlobalCrashIntercept.init();

        DeviceUtils.init();
    }
}
