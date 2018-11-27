package support.lfp.toolkit;


import java.io.Closeable;
import java.io.IOException;

/**
 * <pre>
 * Tip:
 *      IO.close 方法封装,少写几行代码
 * Function:
 *      closeIO()   :关闭IO
 *
 * Created by LiFuPing on 2018/6/27.
 * </pre>
 */
public class CloseUtils {

    private CloseUtils() {
    }

    /**
     * Close the io stream.
     *
     * @param closeables closeables
     */
    public static void closeIO(final Closeable... closeables) {
        closeIO(true, closeables);
    }

    /**
     * Close the io stream.
     *
     * @param showlog    is show log
     * @param closeables closeables
     */
    public static void closeIO(boolean showlog, final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    if (showlog) e.printStackTrace();
                }
            }
        }
    }
}
