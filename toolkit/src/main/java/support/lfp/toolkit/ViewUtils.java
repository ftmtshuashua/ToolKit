package support.lfp.toolkit;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import support.lfp.toolkit.action.Action2;


/**
 * <pre>
 * Tip:
 *      View工具方法集合
 * Function:
 *      getViewSizeByBy**()     :获取View的高度和宽度
 *      setVisibilitySwitch()   :切换View显示状态
 *      setVisibility()         :设置View的显示状态
 *      setImageResource()      :设置ImageView的显示内容
 *      setBackgroundResource() :设置View的背景
 *      setTextColor()          :设置TextView的字体颜色
 *      getInt()                :获得Int类型数据
 *      getIntByHint()          :获得Int类型数据,来自Hint值
 *      getFloat()              :获得Float类型数据
 *      getFloatByHint()        :获得Float类型数据,来自Hint值
 *      getDouble()             :获得Double类型数据
 *      getDoubleByHint()       :获得Double类型数据,来自Hint值
 *      getBoolean()            :获得Boolean类型数据
 *      getBooleanByHint()      :获得Boolean类型数据,来自Hint值
 *      getLong()               :获得Long类型数据
 *      getLongByHint()         :获得Long类型数据,来自Hint值
 *
 * Created by LiFuPing on 2018/5/30.
 * </pre>
 */
public class ViewUtils {

    /**
     * 获得View的宽度和高度
     *
     * @param v     The view
     * @param acion The call
     */
    public static void getViewSizeByGlobal(final View v, final Action2<Integer, Integer> acion) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16)
                    v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                acion.call(v.getWidth(), v.getHeight());
            }
        });
    }

    /**
     * 获得View的宽度和高度
     *
     * @param v     The view
     * @param acion The call
     */
    public static void getViewSizeByPreDraw(final View v, final Action2<Integer, Integer> acion) {
        v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                v.getViewTreeObserver().removeOnPreDrawListener(this);
                acion.call(v.getWidth(), v.getHeight());
                return false;
            }
        });

    }

    /**
     * 大部分时候可以正确的获取宽度和高度
     *
     * @param v     The view
     * @param acion The calll
     */
    public static void getViewSizeByPost(final View v, final Action2<Integer, Integer> acion) {
        v.post(new Runnable() {
            @Override
            public void run() {
                acion.call(v.getWidth(), v.getHeight());
            }
        });
    }

    /**
     * 通过监视LayoutChange来获取宽度和高度
     *
     * @param v     The view
     * @param acion The call
     */
    public static void getViewSizeByLayoutChange(final View v, final Action2<Integer, Integer> acion) {
        v.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                acion.call(v.getWidth(), v.getHeight());
            }
        });
    }

    /**
     * 通过判断布局是否加载来获取宽度和高度
     * 特点：如果View还未被加载则不会回调
     *
     * @param v     The view
     * @param acion The call
     */
    public static void getViewSizeByLaidOut(final View v, final Action2<Integer, Integer> acion) {
        if (ViewCompat.isLaidOut(v)) {
            acion.call(v.getWidth(), v.getHeight());
        }
    }


    /*--------------  性能优化的方法，通过缓存来判断是否需要改变View的状态  ---------------*/

    /**
     * View显示状态切换
     * View.GONE | View.VISIBLE
     *
     * @param view View
     */
    public static final void setVisibilitySwitch(View view) {
        setVisibility(view, view.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置View显示状态
     *
     * @param view       View
     * @param visibility View.GONE | View.VISIBLE
     */
    public static final void setVisibility(View view, int visibility) {
        if (view == null) return;
        if (view.getVisibility() != visibility) view.setVisibility(visibility);
    }

    /**
     * 设置ImageView的显示图片
     *
     * @param view  ImageView
     * @param resid resouceId
     */
    public static final void setImageResource(ImageView view, int resid) {
        if (view == null) return;
        int key = 3 << 24;
        Object value = view.getTag(key);
        if (value != null && (int) value == resid) return;
        view.setImageResource(resid);
        view.setTag(key, resid);
    }

    /**
     * 设置ImageView的显示图片
     *
     * @param view  View
     * @param resid resouceId
     */
    public static final void setBackgroundResource(View view, int resid) {
        if (view == null) return;
        int key = 4 << 24;
        Object value = view.getTag(key);
        if (value != null && (int) value == resid) return;
        view.setBackgroundResource(resid);
        view.setTag(key, resid);
    }

    /**
     * 设置文本字体颜色
     *
     * @param view  TextView
     * @param color textColor
     */
    public static final void setTextColor(TextView view, int color) {
        if (view == null) return;
        int key = 5 << 24;
        Object value = view.getTag(key);
        if (value != null && (int) value == color) return;
        view.setTextColor(color);
        view.setTag(key, color);
    }

    /**
     * 获得View中的数据
     *
     * @param view The view
     * @return View中的数据
     */
    public static final String getString(View view) {
        Utils.requireNonNull(view);
        if (view instanceof TextView) {
            return ((TextView) view).getText().toString();
        }
        return view.toString();
    }

    /**
     * 获得View中的数据,并转换为Int
     *
     * @param view The view
     * @return View中的数据
     */
    public static final int getInt(View view) {
        Utils.requireNonNull(view);
        String str = getString(view);
        return Utils.isEmpty(str) ? 0 : Integer.parseInt(str);
    }

    /**
     * 获得View中的数据,并转换为Float
     *
     * @param view The view
     * @return View中的数据
     */
    public static final float getFloat(View view) {
        Utils.requireNonNull(view);
        String str = getString(view);
        return Utils.isEmpty(str) ? 0 : Float.parseFloat(str);
    }

    /**
     * 获得View中的数据,并转换为Double
     *
     * @param view The view
     * @return View中的数据
     */
    public static final double getDouble(View view) {
        Utils.requireNonNull(view);
        String str = getString(view);
        return Utils.isEmpty(str) ? 0 : Double.parseDouble(str);
    }

    /**
     * 获得View中的数据,并转换为Boolean
     *
     * @param view The view
     * @return View中的数据
     */
    public static final boolean getBoolean(View view) {
        Utils.requireNonNull(view);
        String str = getString(view);
        return Utils.isEmpty(str) ? false : Boolean.parseBoolean(str);
    }

    /**
     * 获得View中的数据,并转换为Lone
     *
     * @param view The view
     * @return View中的数据
     */
    public static final long getLone(View view) {
        Utils.requireNonNull(view);
        String str = getString(view);
        return Utils.isEmpty(str) ? 0 : Long.parseLong(str);
    }


    /**
     * 获得View中Hint的数据
     *
     * @param view The view
     * @return View中的数据
     */
    public static final String getStringByHint(View view) {
        Utils.requireNonNull(view);
        if (view instanceof TextView) {
            return ((TextView) view).getHint().toString();
        }
        return view.toString();
    }

    /**
     * 获得View中Hint的数据,并转换为Int
     *
     * @param view The view
     * @return View中的数据
     */
    public static final int getIntByHint(View view) {
        Utils.requireNonNull(view);
        String str = getStringByHint(view);
        return Utils.isEmpty(str) ? 0 : Integer.parseInt(str);
    }

    /**
     * 获得View中Hint的数据,并转换为Float
     *
     * @param view The view
     * @return View中的数据
     */
    public static final float getFloatByHint(View view) {
        Utils.requireNonNull(view);
        String str = getStringByHint(view);
        return Utils.isEmpty(str) ? 0 : Float.parseFloat(str);
    }

    /**
     * 获得View中Hint的数据,并转换为Double
     *
     * @param view The view
     * @return View中的数据
     */
    public static final double getDoubleByHint(View view) {
        Utils.requireNonNull(view);
        String str = getStringByHint(view);
        return Utils.isEmpty(str) ? 0 : Double.parseDouble(str);
    }

    /**
     * 获得View中Hint的数据,并转换为Boolean
     *
     * @param view The view
     * @return View中的数据
     */
    public static final boolean getBooleanByHint(View view) {
        Utils.requireNonNull(view);
        String str = getStringByHint(view);
        return Utils.isEmpty(str) ? false : Boolean.parseBoolean(str);
    }

    /**
     * 获得View中Hint的数据,并转换为Lone
     *
     * @param view The view
     * @return View中的数据
     */
    public static final long getLoneByHint(View view) {
        Utils.requireNonNull(view);
        String str = getStringByHint(view);
        return Utils.isEmpty(str) ? 0 : Long.parseLong(str);
    }


}
