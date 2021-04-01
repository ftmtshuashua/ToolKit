package com.acap.toolkit.codec;

import android.util.Base64;

/**
 * <pre>
 * Tip:
 *      Base64编码工具
 *
 * Created by ACap on 2020/12/22 10:31
 * </pre>
 */
public class Base64Utils {

    /**
     * Base64编码
     */
    public static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * Base64解码
     */
    public static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }



}
