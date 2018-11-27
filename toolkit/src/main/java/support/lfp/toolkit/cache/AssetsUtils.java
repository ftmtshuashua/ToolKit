package support.lfp.toolkit.cache;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import support.lfp.toolkit.ArraysUtils;

/**
 * <pre>
 *     未完成
 * desc:
 *      Assets目录访问工具
 *
 * function:
 *      getAssetPicPath()       :获得Asset目录列表
 *      getAssetsBitmap()       :获得Assets中的图片
 *
 * Created by LiFuPing on 2018/7/26.
 * </pre>
 */
public class AssetsUtils {

    public static List<String> getAssetPicPath(Context context) {
        return getAssetPicPath(context, "");
    }

    public static List<String> getAssetPicPath(Context context, String path) {
        String[] paths = null;
        try {
            paths = context.getAssets().list(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ArraysUtils.asList(paths == null ? new String[]{} : paths);
    }

    /**
     * 根据路径获取Bitmap图片
     *
     * @param context Context
     * @param path    String
     * @return Bitmap
     */
    public static Bitmap getAssetsBitmap(Context context, String path) {
        AssetManager am = context.getAssets();
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            inputStream = am.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (inputStream != null) bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}

