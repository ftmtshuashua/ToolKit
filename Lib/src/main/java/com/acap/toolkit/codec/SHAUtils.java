package com.acap.toolkit.codec;

import com.acap.toolkit.transform.BytesUtils;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * <pre>
 * Tip:
 *      SHA加密算法
 *
 * Created by ACap on 2020/12/22 11:58
 * </pre>
 */
public class SHAUtils {


    /**
     * 返回SHA1加密的十六进制字符串。
     */
    public static String sha1(final String data) {
        if (data == null || data.length() == 0) return "";
        return sha1(data.getBytes());
    }

    /**
     * 返回SHA1加密的十六进制字符串。
     */
    public static String sha1(final byte[] data) {
        return BytesUtils.bytes2HexString(sha1ToBytes(data));
    }

    /**
     * 返回SHA1加密的字节。
     */
    public static byte[] sha1ToBytes(final byte[] data) {
        return hashTemplate(data, "SHA1");
    }

    /**
     * 返回SHA224加密的十六进制字符串。
     */
    public static String sha224(final String data) {
        if (data == null || data.length() == 0) return "";
        return sha224(data.getBytes());
    }

    /**
     * 返回SHA224加密的十六进制字符串。
     */
    public static String sha224(final byte[] data) {
        return BytesUtils.bytes2HexString(sha224ToBytes(data));
    }

    /**
     * 返回SHA224加密的字节。
     */
    public static byte[] sha224ToBytes(final byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    /**
     * 返回SHA256加密的十六进制字符串。
     */
    public static String sha256(final String data) {
        if (data == null || data.length() == 0) return "";
        return sha256(data.getBytes());
    }

    /**
     * 返回SHA256加密的十六进制字符串。
     */
    public static String sha256(final byte[] data) {
        return BytesUtils.bytes2HexString(sha256ToBytes(data));
    }

    /**
     * 返回SHA256加密的字节。
     */
    public static byte[] sha256ToBytes(final byte[] data) {
        return hashTemplate(data, "SHA256");
    }

    /**
     * 返回SHA384加密的十六进制字符串。
     */
    public static String sha384(final String data) {
        if (data == null || data.length() == 0) return "";
        return sha384(data.getBytes());
    }

    /**
     * 返回SHA384加密的十六进制字符串。
     */
    public static String sha384(final byte[] data) {
        return BytesUtils.bytes2HexString(sha384ToBytes(data));
    }

    /**
     * 返回SHA384加密的字节。
     */
    public static byte[] sha384ToBytes(final byte[] data) {
        return hashTemplate(data, "SHA384");
    }

    /**
     * 返回SHA512加密的十六进制字符串。
     */
    public static String sha512(final String data) {
        if (data == null || data.length() == 0) return "";
        return sha512(data.getBytes());
    }

    /**
     * 返回SHA512加密的十六进制字符串。
     */
    public static String sha512(final byte[] data) {
        return BytesUtils.bytes2HexString(sha512ToBytes(data));
    }

    /**
     * 返回SHA512加密的字节。
     */
    public static byte[] sha512ToBytes(final byte[] data) {
        return hashTemplate(data, "SHA512");
    }


    /**
     * 返回HmacSHA1加密的十六进制字符串。
     */
    public static String hmacSha1(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return hmacSha1(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA1加密的十六进制字符串。
     */
    public static String hmacSha1(final byte[] data, final byte[] key) {
        return BytesUtils.bytes2HexString(hmacSha1ToBytes(data, key));
    }

    /**
     * 返回HmacSHA1加密的字节数。
     */
    public static byte[] hmacSha1ToBytes(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA1");
    }

    /**
     * 返回HmacSHA224加密的十六进制字符串。
     */
    public static String hmacSha224(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return hmacSha224(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA224加密的十六进制字符串。
     */
    public static String hmacSha224(final byte[] data, final byte[] key) {
        return BytesUtils.bytes2HexString(hmacSha224ToBytes(data, key));
    }

    /**
     * 返回HmacSHA224加密的字节。
     */
    public static byte[] hmacSha224ToBytes(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA224");
    }

    /**
     * 返回HmacSHA256加密的十六进制字符串。
     */
    public static String hmacSha256(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return hmacSha256(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA256加密的十六进制字符串。
     */
    public static String hmacSha256(final byte[] data, final byte[] key) {
        return BytesUtils.bytes2HexString(hmacSha256ToBytes(data, key));
    }

    /**
     * 返回HmacSHA256加密的字节。
     */
    public static byte[] hmacSha256ToBytes(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA256");
    }

    /**
     * 返回HmacSHA384加密的十六进制字符串。
     */
    public static String hmacSha384(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return hmacSha384(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA384加密的十六进制字符串。
     */
    public static String hmacSha384(final byte[] data, final byte[] key) {
        return BytesUtils.bytes2HexString(hmacSha384ToBytes(data, key));
    }

    /**
     * 返回HmacSHA384加密的十六进制字符串。
     */
    public static byte[] hmacSha384ToBytes(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA384");
    }

    /**
     * 返回HmacSHA384加密的十六进制字符串。
     */
    public static String hmacSha512(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return hmacSha512(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA384加密的十六进制字符串。
     */
    public static String hmacSha512(final byte[] data, final byte[] key) {
        return BytesUtils.bytes2HexString(hmacSha512ToBytes(data, key));
    }

    /**
     * 返回HmacSHA512加密的字节。
     */
    public static byte[] hmacSha512ToBytes(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA512");
    }


    /**
     * Return the bytes of hash encryption.
     *
     * @param data      The data.
     * @param algorithm The name of hash encryption.
     * @return the bytes of hash encryption
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Return the bytes of hmac encryption.
     *
     * @param data      The data.
     * @param key       The key.
     * @param algorithm The name of hmac encryption.
     * @return the bytes of hmac encryption
     */
    private static byte[] hmacTemplate(final byte[] data, final byte[] key, final String algorithm) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
