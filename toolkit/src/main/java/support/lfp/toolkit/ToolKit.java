package support.lfp.toolkit;

import android.content.Context;

/**
 * <pre>
 * Tip:
 *      ToolKit 统一初始化
 *
 * Function:
 *      init()      :初始化工具集
 *
 *
 * Created by LiFuPing on 2018/10/31 13:46
 * </pre>
 */
public class ToolKit {

    /**
     * Toolkit初始化
     *
     * @param context The context
     */
    public static final void init(final Context context) {
        LogUtils.init(context);

    }

}
