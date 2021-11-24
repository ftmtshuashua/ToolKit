package com.acap.toolkit.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 * Tip:
 *      字节屏幕适配方案
 *
 * @author A·Cap
 * @date 2021/11/24 13:49
 * </pre>
 */
public class ScreenAdapter {
    private static ScreenAdapter mInstance;

    private Application mApplication;
    //字体缩放比例 = ScaledDensity / Density
    private float mScaledRatio = 1;
    // 整体缩放比例 = noncompatDensity / Density
    private float mDensityRatio = 1;
    private float mWidth = 360f;

    public ScreenAdapter(Application mApplication) {
        this.mApplication = mApplication;
        mApplication.registerComponentCallbacks(mCallbacks);
        mApplication.registerActivityLifecycleCallbacks(mActivityCallbacks);
    }

    public static ScreenAdapter getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new ScreenAdapter(application);
        }
        return mInstance;
    }

    /**
     * 设置设备屏幕的宽度的DP值
     *
     * @param width 屏幕宽度，单位DP
     */
    public void setWidth(int width) {
        mWidth = width;
        onConfigChange();
    }

    private void onConfigChange() {
        DisplayMetrics dm = mApplication.getResources().getDisplayMetrics();

        mScaledRatio = dm.scaledDensity / dm.density;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDensityRatio = dm.density / (DisplayMetrics.DENSITY_DEVICE_STABLE / (float) DisplayMetrics.DENSITY_DEFAULT);
        } else {
            mDensityRatio = 1F;
        }
    }

    private void setCustomDensity(Activity activity) {
        DisplayMetrics dm_app = mApplication.getResources().getDisplayMetrics();

        float targetDensity = (Math.min(dm_app.widthPixels, dm_app.heightPixels)) / mWidth * mDensityRatio;
        float targetScaledDensity = targetDensity * mScaledRatio;
        int targetDensityDpi = (int) (DisplayMetrics.DENSITY_DEFAULT * targetDensity);

        dm_app.density = targetDensity;
        dm_app.scaledDensity = targetScaledDensity;
        dm_app.densityDpi = targetDensityDpi;

        DisplayMetrics dm_activity = activity.getResources().getDisplayMetrics();
        dm_activity.density = targetDensity;
        dm_activity.scaledDensity = targetScaledDensity;
        dm_activity.densityDpi = targetDensityDpi;
    }

    private ComponentCallbacks mCallbacks = new ComponentCallbacks() {
        @Override
        public void onConfigurationChanged(@NonNull Configuration newConfig) {
            onConfigChange();
        }

        @Override
        public void onLowMemory() {

        }
    };

    private Application.ActivityLifecycleCallbacks mActivityCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            setCustomDensity(activity);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {

        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }
    };


}
