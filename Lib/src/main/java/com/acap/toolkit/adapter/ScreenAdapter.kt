package com.acap.toolkit.adapter

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics


/**
 * <pre>
 * Tip:
 *      基于 字节跳动 的屏幕适配方案 ：https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
 *      优化：
 *          1.适配横竖屏切换
 *          2.适配系统字体和显示大小更改 - API24及以上有效
 *          3.无入侵式全局配置
 *
 *
 * Created by A·Cap on 2021/10/25 11:18
 * </pre>
 */
object ScreenAdapter {
    
    private lateinit var mApplication: Application

    // 字体缩放比例 = ScaledDensity / Density
    private var mScaledRatio = 1F

    // 整体缩放比例 = noncompatDensity / Density
    private var mDensityRatio = 1F

    private var mInited = false
    private var mWidth: Float = 360f

    // 当配置发生改变时
    private fun onConfigChange() {
        val dm = getAppDisplayMetrics()

        mScaledRatio = dm.scaledDensity / dm.density

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDensityRatio = dm.density / (DisplayMetrics.DENSITY_DEVICE_STABLE / DisplayMetrics.DENSITY_DEFAULT.toFloat())
        } else {
            mDensityRatio = 1F
        }
    }

    /**
     * 给定一个竖屏时单位为dp的宽度来初始化屏幕,之后系统将会假装配置的宽度就是真实的宽度
     *
     * @param width 指定竖屏时屏幕的宽度,单位 dp
     */
    fun init(application: Application, width: Float) {
        if (mInited) throw RuntimeException("The screen adapter is inited !")
        mInited = true
        mApplication = application
        mWidth = width


        onConfigChange()
        mApplication.registerComponentCallbacks(mCallbacks)
        mApplication.registerActivityLifecycleCallbacks(mActivityCallbacks)
    }

    private fun getAppDisplayMetrics() = mApplication.resources.displayMetrics

//    fun DisplayMetrics.print() {
//        val sb = StringBuffer("DisplayMetrics:")
//        sb.append("density=").append(density).append(",")
//        sb.append("densityDpi=").append(densityDpi).append(",")
////        sb.append("noncompatDensity=").append(getField("noncompatDensity")).append(",")
//        sb.append("scaledDensity=").append(scaledDensity).append(",")
////        sb.append("noncompatScaledDensity=").append(getField("noncompatScaledDensity")).append(",")
//        sb.append("whPixels=").append("${widthPixels}*${heightPixels}").append(",")
//        sb.append("xyDpi=").append("${xdpi}*${ydpi}").append(",")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            sb.append("DENSITY_DEVICE=").append("${DisplayMetrics.DENSITY_DEVICE_STABLE / DisplayMetrics.DENSITY_DEFAULT.toFloat()}").append(",")
//        } else {
//
//        }
//        LogUtils.e(sb.toString())
//    }

    // 配置改变监听
    private val mCallbacks by lazy {
        object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                onConfigChange()
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
        val dm_app: DisplayMetrics = getAppDisplayMetrics()


        val targetDensity: Float = (dm_app.widthPixels.coerceAtMost(dm_app.heightPixels)) / mWidth * mDensityRatio
        val targetScaledDensity = targetDensity * mScaledRatio
        val targetDensityDpi: Int = (DisplayMetrics.DENSITY_DEFAULT * targetDensity).toInt()

        dm_app.density = targetDensity
        dm_app.scaledDensity = targetScaledDensity
        dm_app.densityDpi = targetDensityDpi

        val dm_activity: DisplayMetrics = activity.resources.displayMetrics
        dm_activity.density = targetDensity
        dm_activity.scaledDensity = targetScaledDensity
        dm_activity.densityDpi = targetDensityDpi


    }
}