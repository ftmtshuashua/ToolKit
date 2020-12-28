package com.acap.toolkit.source;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 * Tip:
 *      Raw目录访问工具 （资源文件只能读不能写）
 *
 *      访问文件使用ID值：R.raw.xxx；
 *
 * Created by ACap on 2020/12/21 17:01
 * </pre>
 */
public class RawUtils {

    /**
     * 获得Raw中的Bitmap对象
     */
    public static Bitmap getBitmap(Context context, int rawid) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = getInputStream(context, rawid);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 从Raw文件夹中获取二进制数据
     */
    public static byte[] getBytes(Context context, int rawid) {
        byte[] buffer = null;
        try {
            InputStream in = getInputStream(context, rawid);
            buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 从Raw文件夹中获取字符串
     */
    public static String getString(Context context, int rawid) {
        byte[] bytes = getBytes(context, rawid);
        try {
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得Raw中的文件的InputStream
     */
    public static InputStream getInputStream(Context context, int rawid) throws IOException {
        return context.getResources().openRawResource(rawid);
    }
}
