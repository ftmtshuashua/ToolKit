package support.lfp.toolkit.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import support.lfp.toolkit.AppUtils;
import support.lfp.toolkit.BarUtils;

/**
 * <pre>
 * Tip:
 *      Activity栈管理器
 * Function:
 *      init()                                  :初始化管理器
 *      exitApp()                               :退出App
 *      registerAppStatusChangedListener()      :注册App前后台状态变化监听
 *      unregisterAppStatusChangedListener()    :移除App前后台状态变化监听
 *      getTopActivityOrApp()                   :获得顶部Activity获得Application
 *      getActivityList()                       :获得Activity栈列表
 *
 * Created by LiFuPing on 2018/10/31 11:09
 * </pre>
 */
public class ActivityStackManager {
    private static final boolean isDebug = false;
    private static final String TAG = "ASMUtils";

    /**
     * 初始化Activity栈管理器
     *
     * @param application The application
     */
    public static final void init(Application application) {
        application.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
    }

    /**
     * Exit the application.
     */
    public static void exitApp() {
        List<Activity> activityList = getActivityList();
        for (int i = activityList.size() - 1; i >= 0; --i) {
            Activity activity = activityList.get(i);
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /*------- Activity管理 -------*/
    static final ActivityLifecycleImpl ACTIVITY_LIFECYCLE = new ActivityLifecycleImpl();

    /**
     * Register the status of application changed listener.
     *
     * @param key      The object.
     * @param listener The status of application changed listener
     */
    public static void registerAppStatusChangedListener(@NonNull final Object key, @NonNull final OnAppStatusChangedListener listener) {
        ACTIVITY_LIFECYCLE.addListener(key, listener);
    }

    /**
     * Unregister the status of application changed listener.
     *
     * @param key The object.
     */
    public static void unregisterAppStatusChangedListener(@NonNull final Object key) {
        ACTIVITY_LIFECYCLE.removeListener(key);
    }

    /**
     * 获得栈中Activity列表
     *
     * @return 栈中Activity列表
     */
    public static LinkedList<Activity> getActivityList() {
        return ACTIVITY_LIFECYCLE.mActivityList;
    }

    /**
     * 获得栈顶Activity或者App
     *
     * @return 栈顶Activity或者App
     */
    public static Context getTopActivityOrApp() {
        if (AppUtils.isAppForeground()) {
            Activity topActivity = ACTIVITY_LIFECYCLE.getTopActivity();
            return topActivity == null ? AppUtils.getApp() : topActivity;
        } else {
            return AppUtils.getApp();
        }
    }

    static class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

        final LinkedList<Activity> mActivityList = new LinkedList<>();
        final HashMap<Object, OnAppStatusChangedListener> mStatusListenerMap = new HashMap<>();

        private int mForegroundCount = 0;
        private int mConfigCount = 0;

        void addListener(final Object key, final OnAppStatusChangedListener listener) {
            mStatusListenerMap.put(key, listener);
        }

        void removeListener(final Object key) {
            mStatusListenerMap.remove(key);
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (isDebug) Log.i(TAG, "onActivityCreated:" + activity.toString());
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (isDebug) Log.i(TAG, "onActivityStarted:" + activity.toString());
            setTopActivity(activity);
            if (mForegroundCount <= 0) {
                postStatus(true);
            }
            if (mConfigCount < 0) {
                ++mConfigCount;
            } else {
                ++mForegroundCount;
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            if (isDebug) Log.i(TAG, "onActivityResumed:" + activity.toString());
            setTopActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            if (isDebug) Log.i(TAG, "onActivityPaused:" + activity.toString());

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (isDebug) Log.i(TAG, "onActivityStopped:" + activity.toString());
            if (activity.isChangingConfigurations()) {
                --mConfigCount;
            } else {
                --mForegroundCount;
                if (mForegroundCount <= 0) {
                    postStatus(false);
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            if (isDebug) Log.i(TAG, "onActivitySaveInstanceState:" + activity.toString());

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (isDebug) Log.i(TAG, "onActivityDestroyed:" + activity.toString());
            mActivityList.remove(activity);
        }

        private void postStatus(final boolean isForeground) {
            if (mStatusListenerMap.isEmpty()) return;
            for (OnAppStatusChangedListener onAppStatusChangedListener : mStatusListenerMap.values()) {
                if (onAppStatusChangedListener == null) return;
                if (isForeground) {
                    onAppStatusChangedListener.onForeground();
                } else {
                    onAppStatusChangedListener.onBackground();
                }
            }
        }

        private void setTopActivity(final Activity activity) {
//            if (activity.getClass() == PermissionUtils.PermissionActivity.class) return;
            if (mActivityList.contains(activity)) {
                if (!mActivityList.getLast().equals(activity)) {
                    mActivityList.remove(activity);
                    mActivityList.addLast(activity);
                }
            } else {
                mActivityList.addLast(activity);
            }
        }

        Activity getTopActivity() {
            if (!mActivityList.isEmpty()) {
                final Activity topActivity = mActivityList.getLast();
                if (topActivity != null) {
                    return topActivity;
                }
            }
            // using reflect to get top activity
            try {
                @SuppressLint("PrivateApi")
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
                Field activitiesField = activityThreadClass.getDeclaredField("mActivityList");
                activitiesField.setAccessible(true);
                Map activities = (Map) activitiesField.get(activityThread);
                if (activities == null) return null;
                for (Object activityRecord : activities.values()) {
                    Class activityRecordClass = activityRecord.getClass();
                    Field pausedField = activityRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        Field activityField = activityRecordClass.getDeclaredField("activity");
                        activityField.setAccessible(true);
                        Activity activity = (Activity) activityField.get(activityRecord);
                        setTopActivity(activity);
                        return activity;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public interface OnAppStatusChangedListener {
        void onForeground();

        void onBackground();
    }


}
