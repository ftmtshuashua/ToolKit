package support.lfp.toolkit;

import android.app.Application;

import com.acap.toolkit.app.GlobalCrashIntercept;
import com.acap.toolkit.log.LogUtils;
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
        LogUtils.setDebug(true);
        GlobalCrashIntercept.init();
        LogUtils.i("调试设备", DeviceUtils.getPhoneInfo());

    }
}
