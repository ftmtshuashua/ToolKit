package com.weather.utils;

import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.IdRes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 * Tip:
 *      RadioGroup控制器
 *
 *      使任何形式的布局实现RadioGroup的效果
 *
 *  Function:
 *      setOnRadioChangeListener()  :设置Radio改变监听器
 *      addRadio()                  :添加Radio项
 *      checkByIndex()              :通过Index选中Radio
 *      checkById()                 :通过Id选中Radio
 *      check()                     :选中Radio
 *      getCheckedRadio()           :获得选中的Radio
 *      getCheckedId()              :获得选中的Radio的资源ID
 *
 *      onRadioChange()             :Radio改变或点击回调
 *
 * Created by LiFuPing on 2018/6/4.
 * </pre>
 */
public class RadioGroupControl<T extends RadioGroupControl.RadioItem> {

    /* 被控制Radio孩子集合 */
    private final List<T> mItemArray = new ArrayList<>();
    /* 当前被选中的Radio */
    private T mCheckedRadio;
    /*  监听Radio状态改变,在某些时候非常有用 */
    private OnRadioChangeListener mOnCheckedChangeListener;

    /**
     * 设置Radio状态改变监听器,当Radio改变的时候回调
     *
     * @param l 监听器
     */
    public void setOnRadioChangeListener(OnRadioChangeListener l) {
        mOnCheckedChangeListener = l;
    }


    /**
     * 添加一个Radio
     *
     * @param radio the radio
     */
    public final void addRadio(T radio) {
        mItemArray.add(radio);
        radio.attach(this);
    }

    /**
     * 添加Radio集合
     *
     * @param radioarray radio集合
     */
    public final void addRadio(Collection<T> radioarray) {
        Iterator<T> arrays = radioarray.iterator();
        while (arrays.hasNext()) {
            addRadio(arrays.next());
        }
    }

    /**
     * 通过下表来改变选中的Radio
     *
     * @param index radio 下标
     */
    public void checkByIndex(int index) {
        setCheck(mItemArray.get(index));
    }

    /**
     * 遍历Radio View到ID,当找到对应View当时候选中这个View
     *
     * @param id 这个View的资源ID
     */
    public void checkById(@IdRes int id) {
        for (T radio : mItemArray) {
            if (id == radio.getView().getId()) {
                setCheck(radio);
                return;
            }
        }
        throw new NullPointerException("在RadioGroup中的未找到该ID对应的Radio");
    }

    /**
     * 选中Radio
     *
     * @param radio radio
     */
    public void check(T radio) {
        setCheck(radio);
    }

    /* 切换逻辑 */
    private void setCheck(T radio) {
        T current = mCheckedRadio;

        boolean isCheck = radio.setCheck(true);
        if (isCheck) {
            mCheckedRadio = radio;
            if (current != null && current != radio)
                current.setCheck(false);
            notifyRadioChange(radio);
        } else {
            mCheckedRadio = current;
        }
    }

    /**
     * 获得当前选中的Radio
     *
     * @return 选中的Radio
     */
    public T getCheckedRadio() {
        return mCheckedRadio;
    }

    /**
     * 获得当前选中Radio的View资源ID
     *
     * @return View资源ID
     */
    @IdRes
    public int getCheckedId() {
        return getCheckedRadio().getView().getId();
    }

    /**
     * 通知状态改变
     *
     * @param radio 当前被选中当radio
     */
    private void notifyRadioChange(T radio) {
        onRadioChange(radio);
        if (mOnCheckedChangeListener != null) mOnCheckedChangeListener.onRadioChange(radio);
    }

    /**
     * 交给子类实现,当radio改变或者被重复选中当时候调用
     *
     * @param radio 当前被选中当radio
     */
    protected void onRadioChange(T radio) {

    }


    /**
     * RadioItem 在任何View外套一个的壳。以实现类似RadioGroup的效果
     * <p>
     * 简单实现可以使用{@link SimpleRadioItem}
     *
     * @param <V> extends View
     */
    public static abstract class RadioItem<V extends View> implements View.OnClickListener {
        RadioGroupControl mControl;
        V mView;


        public RadioItem(V view) {
            mView = view;
            view.setOnClickListener(this);
        }

        /*关联RadioGroupControl*/
        protected void attach(RadioGroupControl control) {
            mControl = control;
        }

        /**
         * 被Radio包裹控制的View
         *
         * @return V
         */
        public V getView() {
            return mView;
        }

        /**
         * 拦截点击事件，在一些特殊情况下通过拦截事件来阻止RadioGroup切换选中状态
         *
         * @param check boolean
         * @return boolean
         */
        public boolean onInterceptCheck(boolean check) {
            return false;
        }

        /**
         * 准备改变这个Raido的选中状态
         *
         * @param check 准备改变到 isCheck 状态
         * @return 改变之后的选中状态
         */
        protected boolean setCheck(boolean check) {
            boolean isIntercept = onInterceptCheck(check);
            if (!isIntercept) {
                onChange(getView(), check);
            }
            return isCheck();
        }

        /**
         * 获得这个radio的选中状态
         * 在一些特殊场景,如被内存被回收之后还能获得正确当状态
         *
         * @return 当前选中状态
         */
        public abstract boolean isCheck();

        /**
         * 根据根据最终结果改变View显示状态
         *
         * @param v     被radio包裹当View
         * @param check 准备改变为当选中状态
         */
        public abstract void onChange(V v, boolean check);

        @Override
        public void onClick(View v) {
            if (v == mView) mControl.check(this);
        }
    }

    /**
     * RadioItem简单实现
     * 如果View是RadioButton则通过setChecked()方法改变他的状态
     * 如果是其他View则通过setSelected()方法来改变状态
     */
    public static class SimpleRadioItem<V extends View> extends RadioItem<V> {

        public SimpleRadioItem(V view) {
            super(view);
        }

        @Override
        public boolean isCheck() {
            View v = getView();
            if (v instanceof RadioButton) {
                return ((RadioButton) v).isChecked();
            } else return v.isSelected();
        }

        @Override
        public boolean onInterceptCheck(boolean isCheck) {
            return isCheck() == isCheck;
        }

        @Override
        public void onChange(V v, boolean isCheck) {
            if (v instanceof RadioButton) {
                ((RadioButton) v).setChecked(isCheck);
            } else v.setSelected(isCheck);
        }
    }

    /**
     * Radio切换监听
     */
    public interface OnRadioChangeListener<T extends RadioItem> {

        void onRadioChange(T radio);
    }
}
