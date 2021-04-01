package com.acap.toolkit.codec;

import com.acap.toolkit.Utils;
import com.acap.toolkit.transform.BytesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * Tip:
 *      MD5编码工具
 *
 * Created by ACap on 2020/12/22 10:29
 * </pre>
 */
public class MD5Utils {

    /**
     * 返回MD2加密的十六进制字符串。
     */
    public static String md2(final String data) {
        if (data == null || data.length() == 0) return "";
        return md2(data.getBytes());
    }

    /**
     * 返回MD2加密的十六进制字符串。
     */
    public static String md2(final byte[] data) {
        return BytesUtils.bytes2HexString(md2bytes(data));
    }

    /**
     * 返回MD2加密的字节。
     */
    public static byte[] md2bytes(final byte[] data) {
        return hashTemplate(data, "MD2");
    }

    /**
     * 返回MD5加密的十六进制字符串。
     */
    public static String md5(final String data) {
        if (Utils.isEmpty(data)) return "";
        return md5(data.getBytes());
    }

    /**
     * 返回MD5加密的十六进制字符串。
     */
    public static String md5(final String data, final String salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return BytesUtils.bytes2HexString(md5Bytes(data.getBytes()));
        if (data == null) return BytesUtils.bytes2HexString(md5Bytes(salt.getBytes()));
        return BytesUtils.bytes2HexString(md5Bytes((data + salt).getBytes()));
    }

    /**
     * 返回MD5加密的十六进制字符串。
     */
    public static String md5(final byte[] data) {
        return BytesUtils.bytes2HexString(md5Bytes(data));
    }

    /**
     * 返回MD5加密的十六进制字符串。
     */
    public static String md5(final byte[] data, final byte[] salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return BytesUtils.bytes2HexString(md5Bytes(data));
        if (data == null) return BytesUtils.bytes2HexString(md5Bytes(salt));
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return BytesUtils.bytes2HexString(md5Bytes(dataSalt));
    }

    /**
     * 返回MD5加密的字节。
     */
    public static byte[] md5Bytes(final byte[] data) {
        return hashTemplate(data, "MD5");
    }

    /**
     * 返回文件MD5加密的十六进制字符串。
     */
    public static String file2md5(final String filePath) {
        File file = Utils.isSpace(filePath) ? null : new File(filePath);
        return file2md5(file);
    }

    /**
     * 返回文件MD5加密的字节数。
     */
    public static byte[] file2md5Bytes(final String filePath) {
        File file = Utils.isSpace(filePath) ? null : new File(filePath);
        return file2md5Bytes(file);
    }

    /**
     * 返回文件MD5加密的十六进制字符串。
     */
    public static String file2md5(final File file) {
        return BytesUtils.bytes2HexString(file2md5Bytes(file));
    }

    /**
     * 返回文件MD5加密的字节数。
     */
    public static byte[] file2md5Bytes(final File file) {
        if (file == null) return null;
        FileInputStream fis = null;
        DigestInputStream digestInputStream;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(fis, md);
            byte[] buffer = new byte[256 * 1024];
            while (true) {
                if (!(digestInputStream.read(buffer) > 0)) break;
            }
            md = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


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
}
