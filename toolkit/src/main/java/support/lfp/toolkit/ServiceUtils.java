package support.lfp.toolkit;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * Tip:
 *      服务相关类工具
 * Function:
 *      getAllRunningServices()     :获得所有运行中的服务
 *      startService()              :开始服务
 *      stopService()               :停止服务
 *      bindService()               :绑定服务
 *      unbindService()             :解绑服务
 *      isServiceRunning()          :判断服务运行状态
 *
 * Created by LiFuPing on 2018/6/28.
 * </pre>
 */
public class ServiceUtils {

    private ServiceUtils() {

    }

    /**
     * Return all of the services are running.
     *
     * @param context The context
     * @return all of the services are running
     */
    public static Set getAllRunningServices(final Context context) {
        ActivityManager am =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return Collections.emptySet();
        List<ActivityManager.RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (info == null || info.size() == 0) return null;
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }

    /**
     * Start the service.
     *
     * @param context   The context
     * @param className The name of class
     */
    public static void startService(final Context context, final String className) {
        try {
            startService(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the service.
     *
     * @param context   The context
     * @param cls The service class.
     */
    public static void startService(final Context context, final Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startService(intent);
    }

    /**
     * Stop the service.
     *
     * @param context   The context
     * @param className The name of class.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean stopService(final Context context, final String className) {
        try {
            return stopService(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Stop the service.
     *
     * @param context The context
     * @param cls     The name of class.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean stopService(final Context context, final Class<?> cls) {
        Intent intent = new Intent(context, cls);
        return context.stopService(intent);
    }

    /**
     * Bind the service.
     *
     * @param context   The context
     * @param className The name of class.
     * @param conn      The ServiceConnection object.
     * @param flags     Operation options for the binding.
     *                  <ul>
     *                  <li>0</li>
     *                  <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                  <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                  <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                  <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                  <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                  <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                  </ul>
     */
    public static void bindService(final Context context,
                                   final String className,
                                   final ServiceConnection conn,
                                   final int flags) {
        try {
            bindService(context, Class.forName(className), conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bind the service.
     *
     * @param context The context
     * @param cls     The service class.
     * @param conn    The ServiceConnection object.
     * @param flags   Operation options for the binding.
     *                <ul>
     *                <li>0</li>
     *                <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                </ul>
     */
    public static void bindService(final Context context,
                                   final Class<?> cls,
                                   final ServiceConnection conn,
                                   final int flags) {
        Intent intent = new Intent(context, cls);
        context.bindService(intent, conn, flags);
    }

    /**
     * Unbind the service.
     *
     * @param context The context
     * @param conn    The ServiceConnection object.
     */
    public static void unbindService(final Context context, final ServiceConnection conn) {
        context.unbindService(conn);
    }

    /**
     * Return whether service is running.
     *
     * @param context The context
     * @param cls     The service class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isServiceRunning(final Context context, final Class<?> cls) {
        return isServiceRunning(context, cls.getName());
    }

    /**
     * Return whether service is running.
     *
     * @param context   The context
     * @param className The name of class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isServiceRunning(final Context context, final String className) {
        ActivityManager am =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }
}
