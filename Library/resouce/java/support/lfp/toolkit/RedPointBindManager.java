package com.weather.utils;

import android.view.View;

import com.weather.cache.CacheUtils;

/**
 * 红点绑定管理
 */
public class RedPointBindManager {

    private View mV_RedPoint;
    private RedPointRule mRule;
    private RedPointAction mRedPointAction;


    public RedPointBindManager(View redpoint) {
        mV_RedPoint = redpoint;
    }

    //清理红点
    public void clear() {
        if (mRule == null) return;
        mRule.clear();
        change();
    }

    //设置红点规则
    public void setRedPointRule(RedPointRule rule) {
        mRule = rule;
        change();
    }

    //设置红点动作
    public void setRedPointAction(RedPointAction action) {
        mRedPointAction = action;
        change();
    }

    private void change() {
        if (mRule == null) return;

        if (mRedPointAction == null) {
            mRedPointAction = new RPActionVisibility();
        }
        mRedPointAction.action(mRule.isShow(), mV_RedPoint);
    }


    //规则
    private interface RedPointRule {
        //判断当前是否需要显示红点
        boolean isShow();

        //清理红点
        void clear();
    }


    //运行时有效
    public static final class RPRuleRuntime implements RedPointRule {
        boolean isShow = true;

        @Override
        public boolean isShow() {
            return isShow;
        }

        @Override
        public void clear() {
            isShow = false;
        }
    }

    //一天有效
    public static final class RPRuleOnyDay implements RedPointRule {
        private String key;

        public RPRuleOnyDay(String key) {
            this.key = key;
        }


        @Override
        public boolean isShow() {
            return !isSameDay(CacheUtils.getLong(key, 0), System.currentTimeMillis());
        }

        @Override
        public void clear() {
            if (isShow()) {
                CacheUtils.putLong(key, System.currentTimeMillis());
            }
        }


        public static boolean isSameDay(long millis1, long millis2) {
            long interval = millis1 - millis2;
            return interval < 86400000 && interval > -86400000 && millis2Days(millis1) == millis2Days(millis2);
        }

        private static long millis2Days(long millis) {
            return millis / 86400000;
        }

    }


    //动作 - 红点的动作
    private interface RedPointAction {
        void action(boolean isShow, View redPoint);
    }

    private static final class RPActionVisibility implements RedPointAction {

        @Override
        public void action(boolean isShow, View redPoint) {
            redPoint.setVisibility(isShow ? View.VISIBLE : View.GONE);

        }
    }

}
