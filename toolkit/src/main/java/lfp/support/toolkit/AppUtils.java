package lfp.support.toolkit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * desc:
 *      APP工具包
 * function:
 *      getApp()                                :获得Application
 *      getResources()                          :获得Resources
 *      getPackageInfo()                        :获得包信息
 *      registerAppStatusChangedListener()      :注册App前后台状态变化监听
 *      unregisterAppStatusChangedListener()    :移除App前后台状态变化监听
 *      installApp()                            :安装App
 *      installAppSilent()                      :静默安装App
 *      uninstallApp()                          :卸载App
 *      uninstallAppSilent()                    :静默卸载App
 *      isAppInstalled()                        :判断APP是否安装
 *      isAppRoot()                             :判断App是否有Root权限
 *      isAppDebug()                            :判断App是否为Debug版
 *      isAppSystem()                           :判断App是否为系统App
 *      isAppForeground()                       :判断App是否在前台
 *      launchApp()                             :启动App
 *      relaunchApp()                           :重新启动App
 *      launchAppDetailsSettings()              :跳转App详细设置
 *      exitApp()                               :退出App
 *      getAppPackageDataPath()                 :获得包存放数据的位置
 *      getAppIcon()                            :获得App图标
 *      getAppPackageName()                     :获得App包名
 *      getAppName()                            :获得App名称
 *      getAppPath()                            :获得App路径
 *      getAppVersionName()                     :获得App版本名
 *      getAppVersionCode()                     :获得App版本Code
 *      getAppSignature()                       :获得App签名
 *      getAppSignatureSHA1()                   :获得App签名SHA1码
 *      getAppInfo()                            :获得App信息
 *      getAppsInfo()                           :获得设备安装App列表
 *
 * Created by LiFuPing on 2016/1/18.
 * </pre>
 */
public class AppUtils {
    static Application app;

    private AppUtils() {
    }

    /**
     * 不依赖APP或者Context获得Application
     *
     * @param <T> 项目中配置的Application类
     * @return Application
     */
    public static <T extends Application> T getApp() {
        if (app == null) {
            try {
                Class<?> activityThread = Class.forName("android.app.ActivityThread");
                Object at = activityThread.getMethod("currentActivityThread").invoke(null);
                Object app = activityThread.getMethod("getApplication").invoke(at);
                AppUtils.app = (Application) app;

            } catch (Exception e) {
            }
        }
        return (T) app;
    }

    /**
     * 获得包名
     *
     * @return 包名
     */
    public static String getPackageName() {
        return getApp().getPackageName();
    }

    public static Resources getResources() {
        return getApp().getResources();
    }
}
