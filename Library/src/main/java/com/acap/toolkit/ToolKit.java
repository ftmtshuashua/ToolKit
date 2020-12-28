package com.acap.toolkit;

import android.app.Application;

import com.acap.toolkit.app.AppUtils;
import com.acap.toolkit.log.LogUtils;

/**
 * <pre>
 * Tip:
 *      ToolKit 统一初始化
 *
 * Function:
 *      init()      :初始化工具集
 *
 *
 * Created by ACap on 2018/10/31 13:46
 * </pre>
 */
public class ToolKit {

    /**
     * Toolkit初始化
     *
     * @param application The application
     */
    public static final void init(final Application application) {
        boolean debug = AppUtils.isDebug(application);
        LogUtils.setDebug(debug);
        AppUtils.init(application);
    }

}
