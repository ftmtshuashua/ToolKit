package com.acap.toolkit.transform;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * <pre>
 * Tip:
 *      Context 相关工具集
 *
 * Created by ACap on 2020/12/28 16:56
 * </pre>
 */
public class ContextUtils {

    /**
     * Context 转 Activity
     *
     * @param context
     * @return
     */
    public static final Activity getActivity(Context context) {

        if (context instanceof Activity) {
            return (Activity) context;
        }
        int limit = 15;
        int tryCount = 0;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            if (tryCount > limit) {
                return null;
            }
            context = ((ContextWrapper) context).getBaseContext();
            tryCount++;
        }
        return null;
    }

    /**
     * 获得生命周期管理器
     *
     * @param context
     * @return
     */
    public static final Lifecycle getLifecycle(Context context) {
        Activity activity = getActivity(context);
        if (activity != null && activity instanceof LifecycleOwner) {
            return ((LifecycleOwner) activity).getLifecycle();
        }
        return null;
    }

    /**
     * 获得Application
     *
     * @param context
     * @param <T>
     * @return
     */
    public static final <T extends Application> T getApplication(Context context) {
        if (context != null) {
            if (context instanceof Application) {
                return (T) context;
            }
            context.getApplicationContext();
        }
        return null;
    }

}
