package com.acap.toolkit.adapter

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics


/**
 * <pre>
 * Tip:
 *      字节跳动 - 屏幕适配方案
 *
 *
 * Created by A·Cap on 2021/10/25 11:18
 * </pre>
 */
object ScreenAdapterFromByteDance {
    private lateinit var mApplication: Application
    private var sNoncompatDensity: Float = 0F
    private var sNoncompatScaledDensity: Float = 0F
    private var mInited = false
    private var mWidth: Float = 360f


    /** 初始化 屏幕适配器
     * @param width 指定竖屏时候屏幕的宽度,单位 dp
     */
    fun init(application: Application, width: Float) {
        if (mInited) throw RuntimeException("The screen adapter is inited !")
        mInited = true
        mApplication = application
        mWidth = width


        val appDisplayMetrics = getAppDisplayMetrics()
        sNoncompatDensity = appDisplayMetrics.density
        sNoncompatScaledDensity = appDisplayMetrics.scaledDensity
        mApplication.registerComponentCallbacks(mCallbacks)
        mApplication.registerActivityLifecycleCallbacks(mActivityCallbacks)
    }

    private fun getAppDisplayMetrics() = mApplication.resources.displayMetrics

    // 配置改变监听
    private val mCallbacks by lazy {
        object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                if (newConfig != null && newConfig.fontScale > 0) {
                    sNoncompatScaledDensity = getAppDisplayMetrics().scaledDensity
                }
            }

            override fun onLowMemory() {
            }
        }
    }

    // Activity 监听
    private val mActivityCallbacks by lazy {
        object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                setCustomDensity(activity)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

        }
    }

    /** Activity 屏幕适配 */
    private fun setCustomDensity(activity: Activity) {
        if (!mInited) return
        val appDisplayMetrics: DisplayMetrics = getAppDisplayMetrics()

        val targetDensity: Float = (appDisplayMetrics.widthPixels.coerceAtMost(appDisplayMetrics.heightPixels)) / mWidth
        val targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity)
        val targetDensityDpi: Int = (160 * targetDensity).toInt()

        appDisplayMetrics.density = targetDensity
        appDisplayMetrics.scaledDensity = targetScaledDensity
        appDisplayMetrics.densityDpi = targetDensityDpi

        val activityDisplayMetrics: DisplayMetrics = activity.resources.displayMetrics
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.scaledDensity = targetScaledDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi


    }
}