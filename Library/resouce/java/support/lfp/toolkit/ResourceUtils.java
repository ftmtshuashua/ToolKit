package com.weather.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

import support.lfp.toolkit.AppUtils;

public class ResourceUtils {
    private static ResourceUtils mInstance;
    private Map<Integer, Bitmap> mResouceMap = new HashMap();

    private ResourceUtils() {

    }

    public static final ResourceUtils getInstance() {
        if (mInstance == null) {
            mInstance = new ResourceUtils();
        }
        return mInstance;
    }

    public Bitmap getBitmap(int id) {
        if (mResouceMap.get(id) == null) {
            mResouceMap.put(id, BitmapFactory.decodeResource(AppUtils.getResources(), id));
        }
        return mResouceMap.get(id);
    }

    public int getColor(int id) {
        return AppUtils.getResources().getColor(id);
    }

}
