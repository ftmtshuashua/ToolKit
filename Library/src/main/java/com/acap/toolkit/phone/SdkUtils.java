package com.acap.toolkit.phone;

import android.os.Build;

/**
 * <pre>
 * Tip:
 *      SDK版本查询工具
 * Function:
 *      getSdkVersion()     :获得当前版本号
 *      compatible()        :API兼容性判断
 *      getSdkVersionName() :获得版本名称
 *
 *      Name                VersionName    API
 *      BASE                    1.0         1
 *      BASE_1_1                1.1         2
 *      CUPCAKE                 1.5         3
 *      DONUT                   1.6         4
 *      ECLAIR                  2.0         5
 *      ECLAIR_0_1              2.0.1       6
 *      ECLAIR_MR1              2.1         7
 *      FROYO                   2.2         8
 *      GINGERBREAD             2.3         9
 *      GINGERBREAD_MR1         2.3.3       10
 *      HONEYCOMB               3.0         11
 *      HONEYCOMB_MR1           3.1         12
 *      HONEYCOMB_MR2           3.2         13
 *      ICE_CREAM_SANDWICH      4.0         14
 *      ICE_CREAM_SANDWICH_MR1  4.0.3       15
 *      JELLY_BEAN              4.1         16
 *      JELLY_BEAN_MR1          4.2         17
 *      JELLY_BEAN_MR2          4.3         18
 *      KITKAT                  4.4         19
 *      KITKAT_WATCH            4.4W        20
 *      LOLLIPOP                5.0         21
 *      LOLLIPOP_MR1            5.1         22
 *      M                       6.0         23
 *      N                       7.0         24
 *      N_1                     7.1.1       25
 *      O                       8.0         26
 *      O_1                     8.1         27
 *      P                       9.0         28
 *
 *
 * Created by ACap on 2018/6/27.
 * </pre>
 */
public class SdkUtils {

    /**
     * October 2008: The original, first, version of Android.  Yay!
     */
    public static final int BASE = 1;

    /**
     * February 2009: First Android update, officially called 1.1.
     */
    public static final int BASE_1_1 = 2;

    /**
     * May 2009: Android 1.5.
     */
    public static final int CUPCAKE = 3;

    /**
     * September 2009: Android 1.6.
     */
    public static final int DONUT = 4;

    /**
     * November 2009: Android 2.0
     */
    public static final int ECLAIR = 5;

    /**
     * December 2009: Android 2.0.1
     */
    public static final int ECLAIR_0_1 = 6;

    /**
     * January 2010: Android 2.1
     */
    public static final int ECLAIR_MR1 = 7;

    /**
     * June 2010: Android 2.2
     */
    public static final int FROYO = 8;

    /**
     * November 2010: Android 2.3
     */
    public static final int GINGERBREAD = 9;

    /**
     * February 2011: Android 2.3.3.
     */
    public static final int GINGERBREAD_MR1 = 10;

    /**
     * February 2011: Android 3.0.
     */
    public static final int HONEYCOMB = 11;

    /**
     * May 2011: Android 3.1.
     */
    public static final int HONEYCOMB_MR1 = 12;

    /**
     * June 2011: Android 3.2.
     */
    public static final int HONEYCOMB_MR2 = 13;

    /**
     * October 2011: Android 4.0.
     */
    public static final int ICE_CREAM_SANDWICH = 14;

    /**
     * December 2011: Android 4.0.3.
     */
    public static final int ICE_CREAM_SANDWICH_MR1 = 15;

    /**
     * June 2012: Android 4.1.
     */
    public static final int JELLY_BEAN = 16;

    /**
     * Android 4.2: Moar jelly beans!
     */
    public static final int JELLY_BEAN_MR1 = 17;

    /**
     * Android 4.3: Jelly Bean MR2, the revenge of the beans.
     */
    public static final int JELLY_BEAN_MR2 = 18;

    /**
     * Android 4.4: KitKat, another tasty treat.
     */
    public static final int KITKAT = 19;

    /**
     * Android 4.4W: KitKat for watches, snacks on the run.
     */
    public static final int KITKAT_WATCH = 20;

    /**
     * Lollipop. A flat one with beautiful shadows. But still tasty.  Android 5.0
     */
    public static final int LOLLIPOP = 21;

    /**
     * Lollipop with an extra sugar coating on the outside!  Android 5.1
     */
    public static final int LOLLIPOP_MR1 = 22;

    /**
     * M is for Marshmallow! Android 6.0
     */
    public static final int M = 23;

    /**
     * N is for Android 7.0
     */
    public static final int N = 24;
    /**
     * N_1 is for Android 7.1.1
     */
    public static final int N_1 = 25;
    /**
     * O is for Android 8.0
     */
    public static final int O = 26;
    /**
     * O_1 is for Android 8.1
     */
    public static final int O_1 = 27;
    /**
     * P is for Android 9.0
     */
    public static final int P = 28;


    /**
     * 获取当前SDK的版本号
     */
    public static int getCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获得SDK版本名称
     */
    public static String getName() {
        return getName("Android ");
    }

    /**
     * 获得SDK版本名称
     *
     * @param prefix 版本名前缀
     */
    public static String getName(String prefix) {
        if (prefix == null) prefix = "";
        String name = "";
        switch (getCode()) {
            case BASE:
                name = "1.0";
                break;
            case BASE_1_1:
                name = "1.1";
                break;
            case CUPCAKE:
                name = "1.5";
                break;
            case DONUT:
                name = "1.6";
                break;
            case ECLAIR:
                name = "2.0";
                break;
            case ECLAIR_0_1:
                name = "2.0.1";
                break;
            case ECLAIR_MR1:
                name = "2.1";
                break;
            case FROYO:
                name = "2.2";
                break;
            case GINGERBREAD:
                name = "2.3";
                break;
            case GINGERBREAD_MR1:
                name = "2.3.3";
                break;
            case HONEYCOMB:
                name = "3.0";
                break;
            case HONEYCOMB_MR1:
                name = "3.1";
                break;
            case HONEYCOMB_MR2:
                name = "3.2";
                break;
            case ICE_CREAM_SANDWICH:
                name = "4.0";
                break;
            case ICE_CREAM_SANDWICH_MR1:
                name = "4.0.3";
                break;
            case JELLY_BEAN:
                name = "4.1";
                break;
            case JELLY_BEAN_MR1:
                name = "4.2";
                break;
            case JELLY_BEAN_MR2:
                name = "4.3";
                break;
            case KITKAT:
                name = "4.4";
                break;
            case KITKAT_WATCH:
                name = "4.4W";
                break;
            case LOLLIPOP:
                name = "5.0";
                break;
            case LOLLIPOP_MR1:
                name = "5.1";
                break;
            case M:
                name = "6.0";
                break;
            case N:
                name = "7.0";
                break;
            case N_1:
                name = "7.1.1";
                break;
            case O:
                name = "9.0";
                break;
            case O_1:
                name = "8.1";
                break;
            case P:
                name = "9.0";
                break;
        }

        return prefix + name;
    }

    /**
     * 判断API兼容性
     * 这个方法判断当前SDK版本 是否大于等于传入的版本等级 <br>
     */
    public static boolean isCompatible(int lv) {
        return Build.VERSION.SDK_INT >= lv;
    }


}
