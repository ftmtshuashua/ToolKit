package com.acap.toolkit.analysis;

import androidx.appcompat.app.AppCompatActivity;

/**
 * <pre>
 * Tip:
 *      分析器工厂,创建各种分析器的入口
 *
 * Created by ACap on 2021/5/27 15:52
 * </pre>
 */
public class Analysis {


    private Analysis() {
    }

    /**
     * <pre>
     * 获得布局耗时分析器,它能检查View的创建时间.
     * 通常用于监测创建耗时的View
     * </pre>
     *
     * @param mActivity
     * @return
     */
    public static LayoutAnalysis getLayoutAnalysis(AppCompatActivity mActivity) {
        return new LayoutAnalysis(mActivity);
    }

}
