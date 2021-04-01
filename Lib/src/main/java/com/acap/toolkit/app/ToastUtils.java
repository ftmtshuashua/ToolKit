package com.acap.toolkit.app;

import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.IntDef;

import com.acap.toolkit.thread.ThreadHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 * Tip:
 *      Toast工具
 *
 *
 * Created by ACap on 2020/12/28 16:25
 * </pre>
 */
public class ToastUtils {

    public static final void toast(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    public static final void toast(final String msg, @Duration final int duration) {
        if (!TextUtils.isEmpty(msg)) {
            ThreadHelper.main(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AppUtils.getApp(), msg, duration).show();
                }
            });
        }
    }

    @IntDef(value = {Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

}
