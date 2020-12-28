package com.acap.toolkit.phone;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import com.acap.toolkit.app.AppUtils;
import com.acap.toolkit.transform.ArraysUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.WRITE_SETTINGS;

/**
 * <pre>
 * Tip:
 *      设备屏幕工具
 *
 * Created by ACap on 2017/9/29.
 * </pre>
 */
public class ScreenUtils {
    private ScreenUtils() {
    }

    /**
     * PX 转 DP
     *
     * @param px px
     * @return int
     */
    public static int px2dip(float px) {
        final float sale = AppUtils.getResources().getDisplayMetrics().density;
        return (int) ((px - 0.5f) / sale);
    }

    /**
     * DP 转 PX
     *
     * @param dp dp
     * @return int
     */
    public static int dip2px(float dp) {
        return (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, AppUtils.getResources().getDisplayMetrics());
    }

    /**
     * SP 转 PX
     *
     * @param sp sp
     * @return int
     */
    public static int sp2px(float sp) {
        return (int) applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, AppUtils.getResources().getDisplayMetrics());
    }

    /**
     * PX 转 SP
     *
     * @param px sp
     * @return int
     */
    public static int px2sp(final float px) {
        final float fontScale = AppUtils.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    /**
     * 获得屏幕的宽度
     *
     * @return int
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) AppUtils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return AppUtils.getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获得屏幕的高度
     *
     * @return int
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) AppUtils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return AppUtils.getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * Converts an unpacked complex data value holding a dimension to its final floating
     * point value. The two parameters <var>unit</var> and <var>value</var>
     * are as in {@link TypedValue#TYPE_DIMENSION}.
     *
     * @param unit    The unit to convert from.
     * @param value   The value to apply the unit to.
     * @param metrics Current display metrics to use in the conversion --
     *                supplies display density and scaling information.
     * @return The complex floating point value multiplied by the appropriate
     * metrics depending on its unit.
     */
    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        return TypedValue.applyDimension(unit, value, metrics);
    }

    /**
     * 设置全屏
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 设置横屏显示
     */
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置竖屏显示
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断当前是否为横屏
     */
    public static boolean isLandscape() {
        return AppUtils.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断当前是否为竖屏
     */
    public static boolean isPortrait() {
        return AppUtils.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获得屏幕旋转的角度
     *
     * @return 0|90|180|270
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            case Surface.ROTATION_0:
            default:
                return 0;
        }
    }

    /**
     * 截屏
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, false);
    }

    /**
     * 截屏
     *
     * @param activity          The activity.
     * @param isDeleteStatusBar 是否包含状态栏
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight);
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * 判断是否锁屏
     */
    public static boolean isScreenLock() {
        KeyguardManager km = (KeyguardManager) AppUtils.getApp().getSystemService(Context.KEYGUARD_SERVICE);
        return km != null && km.inKeyguardRestrictedInputMode();
    }

    /**
     * 设置休眠时间
     * <p>需要写入设置的权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration The duration.
     */
    @RequiresPermission(WRITE_SETTINGS)
    public static void setSleepDuration(final int duration) {
        Settings.System.putInt(AppUtils.getApp().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
    }

    /**
     * 获得屏幕休眠时间
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(AppUtils.getApp().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获得屏幕信息
     */
    public static String getScreenDetail() {
        DisplayMetrics dm = AppUtils.getResources().getDisplayMetrics();
        List<String> array = new ArrayList<>();
        array.add(MessageFormat.format("分辨率:{0,number,0.##}*{1,number,0.##}", dm.widthPixels, dm.heightPixels));
        array.add(MessageFormat.format("density:{0,number,0.##}", dm.density));
        array.add(MessageFormat.format("densityDpi:{0,number,0.##}", dm.densityDpi));
        array.add(MessageFormat.format("scaledDensity:{0,number,0.##}", dm.scaledDensity));
        array.add(MessageFormat.format("xdpi:{0,number,0.##}", dm.xdpi));
        array.add(MessageFormat.format("ydpi:{0,number,0.##}", dm.ydpi));
        return ArraysUtils.join(array, "\n");
    }


    /**
     * 判断是否开启自动调节亮度
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAutoBrightnessEnabled() {
        try {
            int mode = Settings.System.getInt(
                    AppUtils.getApp().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE
            );
            return mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置是否开启自动调节亮度
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     * 并得到授权
     *
     * @param enabled {@code true}: 打开<br>{@code false}: 关闭
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean setAutoBrightnessEnabled(final boolean enabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.System.canWrite(AppUtils.getApp())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + AppUtils.getApp().getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppUtils.getApp().startActivity(intent);
            return false;
        }
        return Settings.System.putInt(
                AppUtils.getApp().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                enabled ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                        : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        );
    }

    /**
     * 获取屏幕亮度
     *
     * @return 屏幕亮度 0-255
     */
    public static int getBrightness() {
        try {
            return Settings.System.getInt(
                    AppUtils.getApp().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 设置屏幕亮度
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     * 并得到授权
     *
     * @param brightness 亮度值
     * @return 成功与否
     */
    public static boolean setBrightness(@IntRange(from = 0, to = 255) final int brightness) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.System.canWrite(AppUtils.getApp())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + AppUtils.getApp().getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppUtils.getApp().startActivity(intent);
            return false;
        }
        ContentResolver resolver = AppUtils.getApp().getContentResolver();
        boolean b = Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        resolver.notifyChange(Settings.System.getUriFor("screen_brightness"), null);
        return b;
    }

    /**
     * 设置窗口亮度
     *
     * @param window     窗口
     * @param brightness 亮度值
     */
    public static void setWindowBrightness(@NonNull final Window window, @IntRange(from = 0, to = 255) final int brightness) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255f;
        window.setAttributes(lp);
    }

    /**
     * 获取窗口亮度
     *
     * @param window 窗口
     * @return 屏幕亮度 0-255
     */
    public static int getWindowBrightness(final Window window) {
        WindowManager.LayoutParams lp = window.getAttributes();
        float brightness = lp.screenBrightness;
        if (brightness < 0) return getBrightness();
        return (int) (brightness * 255);
    }


}
