package com.acap.toolkit.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * <pre>
 * Tip:
 *      画布工具
 *
 * Function:
 *
 *
 * Created by ACap on 2018/10/31 09:55
 * </pre>
 */
public class CanvasUtils {

    /**
     * 控制点:*
     * <pre>
     * ┎┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┓
     * ┊       *       ┊
     * ┖┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┚
     * </pre>
     *
     * @param canvas 画布
     * @param text   文本
     * @param cx     控制点 X
     * @param cy     控制点 Y
     * @param paint  画笔
     */
    public static final void drawTextAtCenter(Canvas canvas, String text, float cx, float cy, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, cx, cy - (fontMetrics.bottom + fontMetrics.ascent) / 2F, paint);
    }

    /**
     * 控制点:*
     * <pre>
     * ┎┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┓
     * *               ┊
     * ┖┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┚
     * </pre>
     *
     * @param canvas 画布
     * @param text   文本
     * @param cx     控制点 X
     * @param cy     控制点 Y
     * @param paint  画笔
     */
    public static final void drawTextAtCenterLeft(Canvas canvas, String text, float cx, float cy, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, cx, cy - (fontMetrics.bottom + fontMetrics.ascent) / 2F, paint);
    }

    /**
     * 控制点:*
     * <pre>
     * ┎┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┓
     * ┊               *
     * ┖┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┚
     * </pre>
     *
     * @param canvas 画布
     * @param text   文本
     * @param cx     控制点 X
     * @param cy     控制点 Y
     * @param paint  画笔
     */
    public static final void drawTextAtCenterRight(Canvas canvas, String text, float cx, float cy, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(text, cx, cy - (fontMetrics.bottom + fontMetrics.ascent) / 2F, paint);
    }

    /**
     * 控制点:*
     * <pre>
     *  ┎┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┓
     *  ┊               ┊
     *  ┖┄┄┄┄┄┄┄*┄┄┄┄┄┄┄┚
     * </pre>
     *
     * @param canvas 画布
     * @param text   文本
     * @param cx     控制点 X
     * @param cy     控制点 Y
     * @param paint  画笔
     */
    public static final void drawTextAtCenterBottom(Canvas canvas, String text, float cx, float cy, Paint paint) {
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, cx, cy, paint);
    }

    /**
     * 控制点:*
     * <pre>
     * ┎┄┄┄┄┄┄┄*┄┄┄┄┄┄┄┓
     * ┊               ┊
     * ┖┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┚
     * </pre>
     *
     * @param canvas 画布
     * @param text   文本
     * @param cx     控制点 X
     * @param cy     控制点 Y
     * @param paint  画笔
     */
    public static final void drawTextAtCenterTop(Canvas canvas, String text, float cx, float cy, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, cx, cy - (fontMetrics.bottom + fontMetrics.ascent), paint);
    }

    /**
     * 控制点:*
     * <pre>
     * ┎┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┓
     * ┊               ┊
     * *┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┚
     * </pre>
     *
     * @param canvas 画布
     * @param text   文本
     * @param cx     控制点 X
     * @param cy     控制点 Y
     * @param paint  画笔
     */
    public static final void drawTextAtLeftBottom(Canvas canvas, String text, float cx, float cy, Paint paint) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, cx, cy, paint);
    }

    /**
     * 控制点:*
     * <pre>
     * ┎┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┓
     * ┊               ┊
     * ┖┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄*
     * </pre>
     *
     * @param canvas 画布
     * @param text   文本
     * @param cx     控制点 X
     * @param cy     控制点 Y
     * @param paint  画笔
     */
    public static final void drawTextAtRightBottom(Canvas canvas, String text, float cx, float cy, Paint paint) {
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(text, cx, cy, paint);
    }

    /**
     * 控制点:*
     * <pre>
     * ┎┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┓
     * ┊               ┊
     * ┊       *       ┊
     * ┊               ┊
     * ┖┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┚
     * </pre>
     *
     * @param canvas 画布
     * @param bitmap 图片
     * @param cx     控制点 X
     * @param cy     控制点 Y
     * @param paint  画笔
     */
    public static final void drawBitmapAtCenter(Canvas canvas, Bitmap bitmap, float cx, float cy, Paint paint) {
        canvas.drawBitmap(bitmap, cx - bitmap.getWidth() / 2F, cy - bitmap.getHeight() / 2F, paint);
    }

    /**
     * 控制点:*
     * <pre>
     * ┎┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┓
     * ┊               ┊
     * ┊               *
     * ┊               ┊
     * ┖┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┚
     * </pre>
     *
     * @param canvas 画布
     * @param bitmap 图片
     * @param cx     控制点 X
     * @param cy     控制点 Y
     * @param paint  画笔
     */
    public static final void drawBitmapAtCenterRight(Canvas canvas, Bitmap bitmap, float cx, float cy, Paint paint) {
        canvas.drawBitmap(bitmap, cx - bitmap.getWidth(), cy - bitmap.getHeight() / 2F, paint);
    }

    /**
     * 控制点:*
     * <pre>
     * ┎┄┄┄┄┄┄┄*┄┄┄┄┄┄┄┓
     * ┊               ┊
     * ┊               ┊
     * ┊               ┊
     * ┖┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┚
     * </pre>
     *
     * @param canvas 画布
     * @param bitmap 图片
     * @param cx     控制点 X
     * @param cy     控制点 Y
     * @param paint  画笔
     */
    public static final void drawBitmapAtCenterTop(Canvas canvas, Bitmap bitmap, float cx, float cy, Paint paint) {
        canvas.drawBitmap(bitmap, cx - bitmap.getWidth() / 2F, cy, paint);
    }

    /**
     * <pre>
     * 给定三个点，获取穿过这三个点的外切圆
     *
     * Android中圆的角度与方向:
     *
     *        270°
     *         │
     * 180° ───│───>  0°
     *         │
     *        90°
     * </pre>
     *
     * @return 外切圆的外切矩形
     */
    public static final RectF getExcircle(PointF start, PointF center, PointF end) {
        double x1 = start.x;
        double y1 = start.y;
        double x2 = center.x;
        double y2 = center.y;
        double x3 = end.x;
        double y3 = end.y;

        double e = 2 * (x2 - x1);
        double f = 2 * (y2 - y1);
        double g = x2 * x2 - x1 * x1 + y2 * y2 - y1 * y1;
        double a = 2 * (x3 - x2);
        double b = 2 * (y3 - y2);
        double c = x3 * x3 - x2 * x2 + y3 * y3 - y2 * y2;

        //圆心与半径
        double X = (g * b - c * f) / (e * b - a * f);
        double Y = (a * g - c * e) / (a * f - b * e);
        double R = Math.sqrt((X - x1) * (X - x1) + (Y - y1) * (Y - y1));

        float left = (float) (X - R);
        float top = (float) (Y - R);
        float right = (float) (X + R);
        float bottom = (float) (Y + R);
        return new RectF(left, top, right, bottom);
    }

    /**
     * <pre>
     * 获得圆上某点的角度
     *
     * Android中圆的角度与方向:
     *
     *        270°
     *         │
     * 180° ───│───>  0°
     *         │
     *        90°
     * </pre>
     *
     * @return 角度
     */
    public static final double getAngleInCircle(PointF center, PointF p) {
        double angle = Math.asin(Math.abs(p.y - center.y) / getDistance(center, p)) * 180 / Math.PI;
        int pointAtQuadrant = getQuadrant(center, p);
        switch (pointAtQuadrant) {
            case 1:
                return 360 - angle;
            case 2:
                return 180 + angle;
            case 3:
                return 180 - angle;
            default:
                return angle;
        }
    }

    /**
     * 获得 A -> B 两点的距离
     */
    public static final double getDistance(PointF A, PointF B) {
        return Math.sqrt(Math.pow(A.x - B.x, 2) + Math.pow(A.y - B.y, 2));
    }

    /**
     * <pre>
     * 获得点所在象限
     *
     *      2  │  1
     *      ───│───>
     *      3  │  4
     * </pre>
     *
     * @return 1 | 2 | 3 | 4
     */
    public static final int getQuadrant(PointF center, PointF point) {
        float x = point.x - center.x;
        float y = point.y - center.y;
        if (x >= 0 && y >= 0) return 1;
        if (x < 0 && y > 0) return 2;
        if (x <= 0 && y <= 0) return 3;
        return 4;
    }

}
