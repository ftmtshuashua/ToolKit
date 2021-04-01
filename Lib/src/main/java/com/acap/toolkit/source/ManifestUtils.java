package com.acap.toolkit.source;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.acap.toolkit.app.AppUtils;
import com.acap.toolkit.log.LogUtils;

/**
 * <pre>
 * Tip:
 *      Manifest配置信息获取
 *
 * Created by ACap on 2020/12/28 16:44
 * </pre>
 */
public class ManifestUtils {

    /**
     * 获得Application的配置信息
     */
    public static final Bundle getApplicationMeta() {
        Bundle metaData = null;
        try {
            metaData = AppUtils.getApp().getPackageManager().getApplicationInfo(AppUtils.getPackageName(), PackageManager.GET_META_DATA).metaData;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e);
        }
        return metaData;
    }

    /**
     * 获得Activity的配置信息
     */
    public static final Bundle getActivityMeta(Activity activity) {
        Bundle metaData = null;
        try {
            metaData = activity.getPackageManager().getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA).metaData;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e);
        }
        return metaData;
    }

}
