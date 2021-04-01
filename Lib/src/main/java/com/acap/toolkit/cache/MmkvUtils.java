package com.acap.toolkit.cache;

import android.content.Context;
import android.os.Parcelable;

import com.acap.toolkit.log.LogUtils;
import com.tencent.mmkv.MMKV;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * Tip:
 *      缓存工具
 *      用工具需要引入MMKV:implementation 'com.tencent:mmkv-static:1.2.6'
 *
 * Created by ACap on 2016/1/18.
 * </pre>
 */
public class MmkvUtils {

    private static MMKV mMMKV;

    public static final void init(Context context) {
        String initialize = MMKV.initialize(context);// 10ms
        LogUtils.i("MmkvUtils-MMKV-Init: {0}",initialize);
        mMMKV = MMKV.defaultMMKV();
    }

    /**
     * 释放MMKV
     */
    public static final void release() {
        MMKV.onExit();
    }

//---------------- String

    public static void putString(String key, String value) {
        mMMKV.encode(key, value);
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return mMMKV.decodeString(key, defaultValue);
    }

//---------------- long

    public static void putLong(String key, long value) {
        mMMKV.encode(key, value);
    }

    public static long getLong(String key, long defaultValue) {
        return mMMKV.decodeLong(key, defaultValue);
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

//---------------- int

    public static void putInt(String key, int value) {
        mMMKV.encode(key, value);
    }

    public static int getInt(String key, int defaultValue) {
        return mMMKV.decodeInt(key, defaultValue);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

//---------------- float

    public static void putFloat(String key, float value) {
        mMMKV.encode(key, value);
    }

    public static float getFloat(String key, float defaultValue) {
        return mMMKV.decodeFloat(key, defaultValue);
    }

    public static float getFloat(String key) {
        return getFloat(key, 0);
    }

//---------------- double

    public static void putDouble(String key, double value) {
        mMMKV.encode(key, value);
    }

    public static double getDouble(String key, double defaultValue) {
        return mMMKV.decodeDouble(key, defaultValue);
    }

    public static double getDouble(String key) {
        return getDouble(key, 0);
    }

//---------------- byte[]

    public static void putBytes(String key, byte[] value) {
        mMMKV.encode(key, value);
    }

    public static byte[] getBytes(String key, byte[] defaultValue) {
        return mMMKV.decodeBytes(key, defaultValue);
    }

    public static byte[] getBytes(String key) {
        return getBytes(key, null);
    }

//---------------- boolean

    public static void putBoolean(String key, boolean value) {
        mMMKV.encode(key, value);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return mMMKV.decodeBool(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

//---------------- Parcelable

    public static void putParcelable(String key, Parcelable value) {
        mMMKV.encode(key, value);
    }

    public static <T extends Parcelable> T getParcelable(String key, Class<T> tClass, T defaultValue) {
        return mMMKV.decodeParcelable(key, tClass, defaultValue);
    }

    public static <T extends Parcelable> T getParcelable(String key, Class<T> tClass) {
        return getParcelable(key, tClass, null);
    }

    //---------------- Set<String>
    public static void putStringSet(String key, Set<String> value) {
        mMMKV.encode(key, value);
    }

    public static Set<String> getStringSet(String key, Class<? extends Set> cls, Set<String> defaultValue) {
        return mMMKV.decodeStringSet(key, defaultValue, cls);
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValue) {
        return getStringSet(key, HashSet.class, defaultValue);
    }

    public static Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }


}
