package support.lfp.toolkit;

import android.app.Application;

import support.lfp.toolkit.controller.ActivityStackManager;

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
        ActivityStackManager.init(this);
        ToolKit.init(this);
    }
}
