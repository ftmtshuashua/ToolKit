package com.acap.toolkit.phone;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.RequiresPermission;

import com.acap.toolkit.app.AppUtils;
import com.acap.toolkit.codec.MD5Utils;
import com.acap.toolkit.log.LogUtils;
import com.acap.toolkit.structure.KeyValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * <pre>
 * Tip:
 *      手机设备信息相关工具
 * Function:
 *      isPhone()               :
 *      isTablet()              :
 *      getDeviceId()           :获得唯一的设备id
 *      getSerial()             :获得设备的序列号
 *      getIMEI()               :
 *      getMEID()               :
 *      getIMSI()               :
 *      getPhoneType()          :
 *      isSimCardReady()        :
 *      getSimOperatorName()    :
 *      getSimOperatorByMnc()   :
 *      getPhoneStatus()        :
 *      dial()                  :
 *      call()                  :直接拨号
 *      sendSms()               :转到发送短信
 *      sendSmsSilent()         :直接发送短信
 *      getPhoneOnlyTag()       :通过设备信息计算的唯一标识
 *
 * Created by ACap on 2018/6/1.
 * </pre>
 */
public class DeviceUtils {

    private DeviceUtils() {
    }

    /**
     * 判断设备是否为手机
     */
    public static boolean isPhone() {
        TelephonyManager tm = (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 判断设备是否为平板
     */
    public static boolean isTablet() {
        return (AppUtils.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获得设备ID
     * <p>需要获取权限
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     * {@code <uses-permission android:name="android.permission.READ_PRIVILEGED_PHONE_STATE" />}</p>
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(allOf = {READ_PHONE_STATE, "android.permission.READ_PRIVILEGED_PHONE_STATE"})
    public static String getDeviceId() {
        try {
            TelephonyManager tm = (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (tm == null) return "";
                String imei = tm.getImei();
                if (!TextUtils.isEmpty(imei)) return imei;
                String meid = tm.getMeid();
                return TextUtils.isEmpty(meid) ? "" : meid;

            }
            return tm != null ? tm.getDeviceId() : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 返回设备序列号
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(allOf = {READ_PHONE_STATE, "android.permission.READ_PRIVILEGED_PHONE_STATE"})
    public static String getSerial() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Build.getSerial();
            } else {
                return Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获得设备的IMEI
     * <p>需要获取权限
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     * {@code <uses-permission android:name="android.permission.READ_PRIVILEGED_PHONE_STATE" />}</p>
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(allOf = {READ_PHONE_STATE, "android.permission.READ_PRIVILEGED_PHONE_STATE"})
    public static String getIMEI() {
        try {
            TelephonyManager tm = (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return tm != null ? tm.getImei() : "";
            }
            return tm != null ? tm.getDeviceId() : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得设备的MEID
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     * {@code <uses-permission android:name="android.permission.READ_PRIVILEGED_PHONE_STATE" />}</p>
     *
     * @return the MEID
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(allOf = {READ_PHONE_STATE, "android.permission.READ_PRIVILEGED_PHONE_STATE"})
    public static String getMEID() {
        try {
            TelephonyManager tm = (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return tm != null ? tm.getMeid() : "";
            } else {
                return tm != null ? tm.getDeviceId() : "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得设备的IMSI
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     * {@code <uses-permission android:name="android.permission.READ_PRIVILEGED_PHONE_STATE" />}</p>
     *
     * @return the IMSI
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(allOf = {READ_PHONE_STATE, "android.permission.READ_PRIVILEGED_PHONE_STATE"})
    public static String getIMSI() {
        try {
            TelephonyManager tm = (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getSubscriberId() : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得设备类型
     *
     * @return the current phone type
     * <ul>
     * <li>{@link TelephonyManager#PHONE_TYPE_NONE}</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_GSM }</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_CDMA}</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_SIP }</li>
     * </ul>
     */
    public static int getPhoneType() {
        TelephonyManager tm = (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }

    /**
     * 判断sim卡状态是否就绪
     */
    public static boolean isSimCardReady() {
        TelephonyManager tm = (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * 获得sim操作符名称
     */
    public static String getSimOperatorName() {
        TelephonyManager tm =
                (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSimOperatorName() : "";
    }

    /**
     * 用mnc获得sim操作符
     */
    public static String getSimOperatorByMnc() {
        TelephonyManager tm =
                (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm != null ? tm.getSimOperator() : null;
        if (operator == null) return null;
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
                return "中国移动";
            case "46001":
                return "中国联通";
            case "46003":
                return "中国电信";
            default:
                return operator;
        }
    }

    /**
     * 获得设备状态
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     * {@code <uses-permission android:name="android.permission.READ_PRIVILEGED_PHONE_STATE" />}</p>
     *
     * @return DeviceId = 99000311726612<br>
     * DeviceSoftwareVersion = 00<br>
     * Line1Number =<br>
     * NetworkCountryIso = cn<br>
     * NetworkOperator = 46003<br>
     * NetworkOperatorName = 中国电信<br>
     * NetworkType = 6<br>
     * PhoneType = 2<br>
     * SimCountryIso = cn<br>
     * SimOperator = 46003<br>
     * SimOperatorName = 中国电信<br>
     * SimSerialNumber = 89860315045710604022<br>
     * SimState = 5<br>
     * SubscriberId(IMSI) = 460030419724900<br>
     * VoiceMailNumber = *86<br>
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(allOf = {READ_PHONE_STATE, "android.permission.READ_PRIVILEGED_PHONE_STATE"})
    public static String getPhoneStatus() {
        try {
            TelephonyManager tm = (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) return "";
            String str = "";
            str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
            str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
            str += "Line1Number = " + tm.getLine1Number() + "\n";
            str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
            str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
            str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
            str += "NetworkType = " + tm.getNetworkType() + "\n";
            str += "PhoneType = " + tm.getPhoneType() + "\n";
            str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
            str += "SimOperator = " + tm.getSimOperator() + "\n";
            str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
            str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
            str += "SimState = " + tm.getSimState() + "\n";
            str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
            str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得设备唯一标记
     *
     * @return 设备唯一标记
     */
    public static String getPhoneOnlyTag() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(Build.BOARD);
        buffer.append(Build.BRAND);
        buffer.append(Build.CPU_ABI);
        buffer.append(Build.DEVICE);
        buffer.append(Build.DISPLAY);
        buffer.append(Build.HOST);
        buffer.append(Build.ID);
        buffer.append(Build.MANUFACTURER);
        buffer.append(Build.MODEL);
        buffer.append(Build.PRODUCT);
        buffer.append(Build.TAGS);
        buffer.append(Build.TYPE);
        buffer.append(Build.USER);
        return MD5Utils.md5(buffer.toString());
    }

    /**
     * 获得设备信息
     *
     * @return String
     */
    public static String getPhoneInfo() {

        List<KeyValue<String, String>> array = new ArrayList<>();
        if (isTablet()) {
            array.add(new KeyValue<>("厂商信息", MessageFormat.format("{0} - {1} : {2} (平板)", Build.MANUFACTURER, Build.DEVICE, Build.MODEL)));
        } else {
            array.add(new KeyValue<>("厂商信息", MessageFormat.format("{0} - {1} : {2}", Build.MANUFACTURER, Build.DEVICE, Build.MODEL)));
        }
        array.add(new KeyValue<>("OS", MessageFormat.format("API{0} , {1}", SdkUtils.getCode(), SdkUtils.getName())));
        array.add(new KeyValue<>("ROM", getRom().name()));

        ActivityManager am = (ActivityManager) AppUtils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        array.add(new KeyValue<>("内存信息", MessageFormat.format("{0,number,0.##}MB , Large:{1,number,0.##}MB", am.getMemoryClass(), am.getLargeMemoryClass())));

        int sw = ScreenUtils.getScreenWidth();
        int sh = ScreenUtils.getScreenHeight();
        int cd = getCommonDivisor(sw, sh);
        String str1 = MessageFormat.format("{0,number,0.##}:{1,number,0.##}", sw / cd, sh / cd);
        String screenInfo = MessageFormat.format("{0,number,0}x{1,number,0}({2})", sw, sh, str1);
        array.add(new KeyValue<>("屏幕信息", screenInfo));

        array.add(new KeyValue<>("CPU信息", MessageFormat.format("{0},核心:{1}", CpuUtils.getCpuName(), CpuUtils.getNumCores())));


        StringBuffer SB = new StringBuffer(" ");
        List<String> lumpFormat = LogUtils.getLogLumpFormat(array);
        for (String s : lumpFormat) {
            SB.append("\n").append(s);
        }
        return SB.toString();
    }


    /*---- 系统检测 ---*/


    /**
     * 系统ROM
     */
    public enum ROM {
        /**
         * 未知设备
         */
        UNKNOWN,
        /**
         * 小米
         */
        MIUI
        /** 索尼 */
        , SONY
        /** 三星 */
        , SAMSUNG
        /** LG */
        , LG
        /** HTC */
        , HTC
        /** 华为 NOVA */
        , NOVA
        /** OPPO */
        , OPPO
        /** 乐视 */
        , LEMOBILE
        /** VIVO */
        , VIVO
        /** 华为 */
        , HUAWEI
        /** 魅族 */
        , Flyme

    }

    /**
     * 获得Rom
     *
     * @return the Rom
     */
    public static ROM getRom() {
        if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {
            return ROM.MIUI;
        } else if (Build.MANUFACTURER.equalsIgnoreCase("sony")) {
            return ROM.SONY;
        } else if (Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
            return ROM.SAMSUNG;
        } else if (Build.MANUFACTURER.equalsIgnoreCase("lg")) {
            return ROM.LG;
        } else if (Build.MANUFACTURER.equalsIgnoreCase("htc")) {
            return ROM.HTC;
        } else if (Build.MANUFACTURER.equalsIgnoreCase("nova")) {
            return ROM.NOVA;
        } else if (Build.MANUFACTURER.equalsIgnoreCase("OPPO")) {
            return ROM.OPPO;
        } else if (Build.MANUFACTURER.equalsIgnoreCase("LeMobile")) {
            return ROM.LEMOBILE;
        } else if (Build.MANUFACTURER.equalsIgnoreCase("vivo")) {
            return ROM.VIVO;
        } else if (Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") || Build.BRAND.equals("Huawe")) {
            return ROM.HUAWEI;
        } else if (Build.BRAND.equalsIgnoreCase("meizu")) {
            return ROM.Flyme;
        }



        return ROM.UNKNOWN;
    }


    /*获得Rom版本*/
    private static String getRomVersion(String name) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + name);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    //求最大公约数
    private static final int getCommonDivisor(int x, int y) {
        int a, b, c;
        a = x;
        b = y;
        /* 余数不为0，继续相除，直到余数为0 */
        while (b != 0) {
            c = a % b;
            a = b;
            b = c;
        }
        return a;
    }


    public static void init() {
        String key = "ro.product.board";
        String prop = getProp(key);
        LogUtils.e(MessageFormat.format("{0} = {1}" , key,prop));
    }

    private static String getProp(String prop) {
        ShellUtils.CommandResult result = ShellUtils.execCmd(MessageFormat.format("getprop {0}", prop), false);
        if (result.result == 0 && result.successMsg != null) {
            return result.successMsg;
        }
        return "";
    }


}
