package support.lfp.toolkit;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * <pre>
 * Tip:
 *      线程调度工具，保证业务处理在正确的线程下进行
 *
 * Function:
 *
 * Created by LiFuPing on 2018/11/21 13:48
 * </pre>
 */
public class ThreadHelper {
    private final static Handler mHandler = new Handler(Looper.getMainLooper());
    private final static ExecutorService Pool_IO = Executors.newCachedThreadPool();
    private final static ExecutorService Pool_Computation = Executors.newFixedThreadPool(getCpuNumCores());

    /**
     * 在主线程执行业务逻辑
     *
     * @param runnable The runnable
     */
    public static final void main(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) runnable.run();
        else mHandler.post(runnable);
    }

    /**
     * 在主线程延时执行业务逻辑
     *
     * @param runnable    The runnable
     * @param delayMillis 等待时间
     */
    public static final void mainDelayed(Runnable runnable, long delayMillis) {
        mHandler.postDelayed(runnable, delayMillis);
    }

    /**
     * 在IO线程执行业务逻辑
     *
     * @param runnable The runnable
     */
    public static final void io(Runnable runnable) {
        Pool_IO.submit(runnable);
    }

    /**
     * 在CPU密集型线程中执行业务逻辑
     *
     * @param runnable The runnable
     */
    public static final void computation(Runnable runnable) {
        Pool_Computation.submit(runnable);
    }


    /**
     * 获得CPU核心数目
     *
     * @return int CPU核心数目,获取失败时返回 1
     */
    public static int getCpuNumCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    static final class CpuFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            //Check if filename is "cpu", followed by a single digit number
            if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                return true;
            }
            return false;
        }
    }
}
