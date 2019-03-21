package support.lfp.toolkit;

/**
 * <pre>
 * Tip:
 *      防抖动工具
 *      保证一段时间之内只允许一次事件通过
 *
 * Function:
 *
 * Created by LiFuPing on 2018/12/28 16:07
 * </pre>
 */
public class ThrottleUtils {

    private static ThrottleUtils mInstance;

    private long mThrottleTime; //节流时间
    private long mTimeSignal;

    public ThrottleUtils() {
        this(300);
    }

    public ThrottleUtils(long throttleTime) {
        mThrottleTime = throttleTime;
    }

    /**
     * 防止事件连续执行
     * <p>
     * 如果返回true表示上一次事件刚刚通过了，不应该允许当前事件继续执行
     */
    public boolean isIntercept() {
        long time = System.currentTimeMillis();
        if (time - mTimeSignal > mThrottleTime) {
            mTimeSignal = time;
            return false;
        } else {
            return true;
        }
    }


    public static final ThrottleUtils getDefault() {
        if (mInstance == null) {
            mInstance = new ThrottleUtils();
        }
        return mInstance;
    }

}
