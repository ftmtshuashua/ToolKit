package lfp.support.toolkit;

import android.app.Application;

import lfp.support.toolkit.control.ActivityStackManagerUtils;

/**
 * <pre>
 * Tip:
 *
 * Function:
 *
 * Created by LiFuPing on 2018/10/31 11:24
 * </pre>
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActivityStackManagerUtils.init(this);
        ToolKit.init(this);
    }
}
