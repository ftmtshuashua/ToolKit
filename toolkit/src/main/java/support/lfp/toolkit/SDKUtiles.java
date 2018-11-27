package support.lfp.toolkit;

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
 * Created by LiFuPing on 2018/6/27.
 * </pre>
 */
public class SDKUtiles {

    /**
     * 获取当前SDK的版本号
     * @return int
     */
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 判断API兼容性
     * 这个方法判断当前SDK版本 是否大于等于传入的版本等级 <br>
     *
     * @param lv leve
     * @return boolean
     */
    public static boolean compatible(int lv) {
        return Build.VERSION.SDK_INT >= lv;
    }

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
     * N is for ¯\_(ツ)_/¯. Android 7.0
     */
    public static final int N = 24;
    /**
     * N_1 is for ¯\_(ツ)_/¯. Android 7.1.1
     */
    public static final int N_1 = 25;
    /**
     * O is for ¯\_(ツ)_/¯. Android 8.0
     */
    public static final int O = 26;
    /**
     * O_1 is for ¯\_(ツ)_/¯. Android 8.1
     */
    public static final int O_1 = 27;
    /**
     * P is for ¯\_(ツ)_/¯. Android 9.0
     */
    public static final int P = 28;

    /**
     * @return 获得SDK版本名称
     */
    public static String getSdkVersionName() {
        switch (getSdkVersion()) {
            case BASE:
                return "Android_1.0";
            case BASE_1_1:
                return "Android_1.1";
            case CUPCAKE:
                return "Android_1.5";
            case DONUT:
                return "Android_1.6";
            case ECLAIR:
                return "Android_2.0";
            case ECLAIR_0_1:
                return "Android_2.0.1";
            case ECLAIR_MR1:
                return "Android_2.1";
            case FROYO:
                return "Android_2.2";
            case GINGERBREAD:
                return "Android_2.3";
            case GINGERBREAD_MR1:
                return "Android_2.3.3";
            case HONEYCOMB:
                return "Android_3.0";
            case HONEYCOMB_MR1:
                return "Android_3.1";
            case HONEYCOMB_MR2:
                return "Android_3.2";
            case ICE_CREAM_SANDWICH:
                return "Android_4.0";
            case ICE_CREAM_SANDWICH_MR1:
                return "Android_4.0.3";
            case JELLY_BEAN:
                return "Android_4.1";
            case JELLY_BEAN_MR1:
                return "Android_4.2";
            case JELLY_BEAN_MR2:
                return "Android_4.3";
            case KITKAT:
                return "Android_4.4";
            case KITKAT_WATCH:
                return "Android_4.4W";
            case LOLLIPOP:
                return "Android_5.0";
            case LOLLIPOP_MR1:
                return "Android_5.1";
            case M:
                return "Android_6.0";
            case N:
                return "Android_7.0";
            case N_1:
                return "Android_7.1.1";
            case O:
                return "Android_9.0";
            case O_1:
                return "Android_8.1";
            case P:
                return "Android_9.0";
        }
        return "unknown";
    }
}
