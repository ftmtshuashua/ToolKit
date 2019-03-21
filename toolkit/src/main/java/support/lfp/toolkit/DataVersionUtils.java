package support.lfp.toolkit;

import java.util.HashMap;

/**
 * <pre>
 * Tip:
 *      数据版本控制，将页面数据的版本保存在内存中。可以在其他任何地方改变该属性。
 *      当回到该页面的时候判断页面数据版本与内存中的版本是否一致，如果不一致表示应该更新它们。
 *
 * Function:
 *
 * Created by LiFuPing on 2019/1/28 15:50
 * </pre>
 */
public final class DataVersionUtils {
    private static final HashMap<Integer, Long> version = new HashMap<>();

    private DataVersionUtils() {
    }

    /** 获得当前内存中的数据版本号 */
    public static final long getVersion(int type) {
        final Long aLong = version.get(type);
        if (aLong == null) return 0;
        return aLong;
    }

    /** 改变内存中的数据版本号 */
    public static final void setChange(int type) {
        long old = getVersion(type);
        old++;
        if (old == Long.MAX_VALUE) old = 0;
        version.put(type, old + 1);
    }

    /** 判断页面数据版本与内存中的数据版本是否相同，如果版本不同则表示我们应该更新页面的数据 */
    public static final boolean isChange(int type, long version) {
        return getVersion(type) != version;
    }

}
