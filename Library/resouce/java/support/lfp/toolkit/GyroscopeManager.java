package com.weather.utils;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import support.lfp.toolkit.AppUtils;
import support.lfp.toolkit.ScreenUtils;

import static android.content.Context.SENSOR_SERVICE;

/**
 * 陀螺仪
 */
public class GyroscopeManager implements SensorEventListener {

    private static volatile GyroscopeManager mInstance;
    private final SensorManager mSensorManager;
    private Sensor mGyroscope;

    private List<GyroscopeEntity> mArray = new ArrayList<>();

    private GyroscopeManager(Context context) {
        this.mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        if (isPerformance()) {
            this.mGyroscope = this.mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }
    }


    public static final GyroscopeManager getInstance() {
        if (mInstance == null) {
            synchronized (GyroscopeManager.class) {
                if (mInstance == null) {
                    mInstance = new GyroscopeManager(AppUtils.getApp());
                }
            }
        }
        return mInstance;
    }

    //判断是否有陀螺仪
    public boolean isHasGyroscope() {
        return mGyroscope != null;
    }


    public void start() {
        if (isHasGyroscope()) {
            mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_GAME);
//            mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void stop() {
        if (isHasGyroscope()) {
            mSensorManager.unregisterListener(this, mGyroscope);
        }
    }

    private long mCacheLastTimestamp;
    private int mCacheIndex;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            if (this.mCacheLastTimestamp != 0) {
                mCacheIndex = mArray.size() - 1;
                for (; mCacheIndex >= 0; mCacheIndex--) {
                    GyroscopeEntity next = mArray.get(mCacheIndex);
                    next.onChange(
                            ((double) (event.values[0] * (((float) (event.timestamp - this.mCacheLastTimestamp))) * 1.000000E-09f * 1f))
                            , ((double) (event.values[1] * (((float) (event.timestamp - this.mCacheLastTimestamp))) * 1.000000E-09f * 1f))
                    );
                }
            }
            this.mCacheLastTimestamp = event.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void register(GyroscopeEntity entity) {
        if (entity != null && !mArray.contains(entity)) {
            mArray.add(entity);
            onEntityChange();
        }
    }

    public void unregister(GyroscopeEntity entity) {
        if (entity != null) {
            mArray.remove(entity);
            onEntityChange();
        }
    }

    private void onEntityChange() {
        if (mArray.isEmpty()) {
            stop();
        } else {
            start();
        }
    }

    /**
     * 陀螺仪绑定的对象
     */
    public interface GyroscopeEntity {

        void onChange(double angleX, double angleY);
    }


    //判断手机性能
    private static boolean isPerformance() {
        boolean result = true;

        float height = ScreenUtils.getScreenHeight();
        float width = ScreenUtils.getScreenWidth();
        long memory = getMemory();
        if (720f > width) {
            return false;
        }

        width = (((float) memory)) / 1048576f; //1024*1024
        if (width < 3f || Build.VERSION.SDK_INT < 24 || (Build.MODEL.contains("LG"))) {
            result = false;
        }

        return result;
    }

    private static long getMemory() {
        long v0_3;
        String v0 = "/proc/meminfo";
        try {
            BufferedReader v0_2 = new BufferedReader(new FileReader(v0), 0x800);
            String v1 = v0_2.readLine();
            v1 = v1.substring(v1.indexOf("MemTotal:"));
            v0_2.close();
            v0_3 = ((long) Integer.parseInt(v1.replaceAll("\\D+", "")));
        } catch (IOException v0_1) {
            v0_1.printStackTrace();
            v0_3 = 0;
        }
        return v0_3;
    }
}
