package com.acap.toolkit.source;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * <pre>
 *      Assets目录访问工具
 *
 *      * path参数使用相对路径 - 例子:"目录名/文件名.txt"
 * function:
 *      getAssetPicPath()       :获得Asset目录列表
 *      getAssetsBitmap()       :获得Assets中的图片
 *
 * Created by ACap on 2018/7/26.
 * </pre>
 */
public class AssetsUtils {

    /**
     * 获得Assets的根目录中的文件列表
     *
     * @注意 子目录中必须有文件，否则不会将子目录名称写入数组中
     */
    public static String[] getFilesList(Context context) {
        return getFilesList(context, "");
    }

    /**
     * 获得Assets的指定目录中的文件列表
     *
     * @注意 子目录中必须有文件，否则不会将子目录名称写入数组中
     */
    public static String[] getFilesList(Context context, String path) {
        String[] paths = null;
        try {
            paths = context.getAssets().list(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }

    /**
     * 获得Assets中的Bitmap对象
     */
    public static Bitmap getBitmap(Context context, String path) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = getInputStream(context, path);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 获得Assets中的文件的byte数据
     */
    public static byte[] getBytes(Context context, String path) {
        byte[] buff = null; // 结果字符串
        try {
            InputStream is = getInputStream(context, path);// 打开文件
            int ch = 0;
            ByteArrayOutputStream out = new ByteArrayOutputStream(); // 实现了一个输出流
            while ((ch = is.read()) != -1) {
                out.write(ch); // 将指定的字节写入此 byte 数组输出流
            }
            buff = out.toByteArray();// 以 byte 数组的形式返回此输出流的当前内容
            out.close(); // 关闭流
            is.close(); // 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buff;
    }

    /**
     * 获得Assets中的文件的String数据
     */
    public static String getString(Context context, String path) {
        byte[] bytes = getBytes(context, path);
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得Assets中的文件的InputStream
     */
    public static InputStream getInputStream(Context context, String path) throws IOException {
        return context.getResources().getAssets().open(path);
    }

}

