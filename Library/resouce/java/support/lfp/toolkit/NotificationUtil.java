package com.weather.utils;

import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import support.lfp.toolkit.LogUtils;

/**
 *
 */
public class NotificationUtil {

    public static boolean isNotificationsEnabled(Context context) {
        NotificationManagerCompat.from(context).areNotificationsEnabled();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return isEnableV19(context);
        } else {
            return isEnableV26(context);
        }
    }


    private static boolean isEnableV19(Context context) {
        final String CHECK_OP_NO_THROW = "checkOpNoThrow";
        final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null; /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (int) opPostNotificationValue.get(Integer.class);
            return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (NoSuchFieldException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        } catch (Exception e) {
        }
        return false;
    }


    private static boolean isEnableV26(Context context) {
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        try {
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            Method sServiceField = notificationManager.getClass().getDeclaredMethod("getService");
            sServiceField.setAccessible(true);
            Object sService = sServiceField.invoke(notificationManager);

            Method method = sService.getClass().getDeclaredMethod("areNotificationsEnabledForPackage"
                    , String.class, Integer.TYPE);
            method.setAccessible(true);
            return (boolean) method.invoke(sService, pkg, uid);
        } catch (Exception e) {
            return true;
        }
    }

    public static void toSetting(Context context) {
        if (true) {
            toSetNotificationPermission(context);
            return;
        }

        Intent localIntent = new Intent();
        //直接跳转到应用通知设置的代码：
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0及以上
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上到8.0以下
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            localIntent.putExtra("app_package", context.getPackageName());
            localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {//4.4
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
        } else {
            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
        }
        context.startActivity(localIntent);
    }


    @Deprecated
    public static void toSetNotificationPermission(Context arg6) {
        LogUtils.i("跳转通知权限页", "查询通知权限Activity");
        int arg7 = 0x10000000;
        ComponentName v1;
        Intent v0_1;
        String v0 = Build.MANUFACTURER;
        if (v0.toLowerCase().equals("xiaomi")) {
            LogUtils.i("跳转通知权限页", "系统 - 小米");
            v0_1 = new Intent();
            v1 = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationFilterActivity");
            Bundle v2 = new Bundle();
            v2.putString("appName", arg6.getResources().getString(arg6.getApplicationInfo().labelRes));
            v2.putString("packageName", arg6.getPackageName());
            v2.putString(":android:show_fragment", "NotificationAccessSettings");
            v0_1.putExtras(v2);
            v0_1.setComponent(v1);
            v0_1.setFlags(arg7);
            try {
                arg6.startActivity(v0_1);
            } catch (Exception v0_2) {
                v0_2.printStackTrace();
                toSetNotificationPermissionDefault(arg6, arg7);
            }

            return;
        }

        if (v0.toLowerCase().equals("oppo")) {
            LogUtils.i("跳转通知权限页", "系统 - Oppo");
            v0_1 = new Intent();
            v0_1.setComponent(new ComponentName("com.oppo.notification.center", "com.oppo.notification.center.AppDetailActivity"));
            v0_1.setAction("com.oppo.notification.center.app.detail");
            v0_1.setFlags(arg7);
            v0_1.putExtra("pkg_name", arg6.getPackageName());
            v0_1.putExtra("app_name", arg6.getApplicationInfo().name);
            try {
                arg6.startActivity(v0_1);
                LogUtils.i("跳转通知权限页", "跳转成功 - com.oppo.notification.center.AppDetailActivity");
            } catch (Exception v0_2) {
                LogUtils.i("跳转通知权限页", "没有 - com.oppo.notification.center.AppDetailActivity");
                v0_1 = new Intent();
                v1 = new ComponentName("com.oppo.notification.center", "com.oppo.notification.center.NotificationCenterActivity");
                v0_1.putExtras(new Bundle());
                v0_1.setComponent(v1);
                v0_1.setFlags(arg7);
                try {
                    arg6.startActivity(v0_1);
                    LogUtils.i("跳转通知权限页", "跳转成功 - com.oppo.notification.center.NotificationCenterActivity");
                } catch (Exception e) {
                    LogUtils.i("跳转通知权限页", "没有 - com.oppo.notification.center.NotificationCenterActivity");
                    toSetNotificationPermissionDefault(arg6, arg7);
                }
            }

            return;
        }

        if (v0.toLowerCase().equals("vivo")) {
            LogUtils.i("跳转通知权限页", "系统 - Vivo");
            v0_1 = new Intent();
            v1 = new ComponentName("com.android.systemui", "com.android.systemui.vivo.common.notification.settings.StatusbarSettingActivity");
            v0_1.putExtras(new Bundle());
            v0_1.setComponent(v1);
            v0_1.setFlags(arg7);
            try {
                arg6.startActivity(v0_1);
            } catch (Exception v0_2) {
                toSetNotificationPermissionDefault(arg6, arg7);
            }

            return;
        }

        if (v0.toLowerCase().equals("huawei")) {
            LogUtils.i("跳转通知权限页", "系统 - huawei");
            v0_1 = new Intent();
            v1 = new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity");
            v0_1.putExtras(new Bundle());
            v0_1.setComponent(v1);
            v0_1.setFlags(arg7);
            try {
                arg6.startActivity(v0_1);
            } catch (Exception v0_2) {
                toSetNotificationPermissionDefault(arg6, arg7);
            }
        } else {
            LogUtils.i("跳转通知权限页", "系统 - 其他");
            toSetNotificationPermissionDefault(arg6, arg7);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (arg6 != null) {
//                    arg6.startActivity(new Intent(arg6, UserStatisticsSettingOverlayActivity.class));
                }
            }
        }, 500);
    }

    private static void toSetNotificationPermissionDefault(Context arg4, int arg5) {
        String v3 = null;
        Intent v0 = new Intent();

        LogUtils.i("跳转通知权限页 - SDK_INI", String.valueOf(Build.VERSION.SDK_INT));
        if (Build.VERSION.SDK_INT >= 26) {
            v0.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            v0.putExtra("android.provider.extra.APP_PACKAGE", arg4.getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            v0.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            v0.putExtra("app_package", arg4.getPackageName());
            v0.putExtra("app_uid", arg4.getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT == 19) {
            v0.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            v0.addCategory("android.intent.category.DEFAULT");
            v0.setData(Uri.parse("package:" + arg4.getPackageName()));
        } else if (Build.VERSION.SDK_INT >= 9) {
            v0.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            v0.setData(Uri.fromParts("package", arg4.getPackageName(), v3));
        } else if (Build.VERSION.SDK_INT <= 8) {
            v0.setAction("android.intent.action.VIEW");
            v0.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            v0.putExtra("com.android.settings.ApplicationPkgName", arg4.getPackageName());
        }

        v0.setFlags(arg5);
        try {
            arg4.startActivity(v0);
            LogUtils.i("跳转通知权限页", "版本判断跳转成功");
        } catch (Exception v0_1) {
            LogUtils.i("跳转通知权限页", "版本判断跳转失败");
            v0_1.printStackTrace();
            v0 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            v0.setData(Uri.fromParts("package", arg4.getPackageName(), v3));
            v0.setFlags(arg5);
            try {
                arg4.startActivity(v0);
                LogUtils.i("跳转通知权限页", "跳转成功 - android.settings.APPLICATION_DETAILS_SETTINGS");
            } catch (Exception e) {
                LogUtils.i("跳转通知权限页", "没有 - 无可跳转的页面");
            }
        }

    }


}
