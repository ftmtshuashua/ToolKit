package com.acap.toolkit.cache;

import java.util.LinkedList;

/**
 * <pre>
 * Tip:
 *      线程安全的对象复用工具
 *
 * Function:
 *      obtain()    :获得一个实例
 *      recycle()   :回收实例
 *      onCreate()    :创建实例(重构)
 *
 * Created by ACap on 2018/6/26.
 *
 * @param <R> 关联对象，某些时候我们创建一个缓存对象需要通过其他对象的参数来创建的时候就可以利用该参数
 * @param <W> 输出对象，也就是被缓存的对象
 *
 *
 * </pre>
 */
public class ObjectCacheUtils<R, W> {
    final Object sPoolSync = new Object();
    final LinkedList<W> mScrapHeap = new LinkedList<>();

    private IObjectProvider<R, W> mObjectProvider;
    private OnObjectRecycleListener<W> mOnObjectRecycle;
    private OnObjectActiveListener<R, W> mOnObjectActive;

    /**
     * @param provider
     */
    public ObjectCacheUtils(IObjectProvider<R, W> provider) {
        this.mObjectProvider = provider;
    }

    /**
     * 对象回收监听
     */
    public ObjectCacheUtils<R, W> setOnObjectRecycleListener(OnObjectRecycleListener<W> mOnObjectRecycle) {
        this.mOnObjectRecycle = mOnObjectRecycle;
        return this;
    }

    /**
     * 对象使用监听
     */
    public ObjectCacheUtils<R, W> setOnObjectActiveListener(OnObjectActiveListener<R, W> onActive) {
        this.mOnObjectActive = onActive;
        return this;
    }

    /**
     * 如果缓存中存在对象,则从缓存获取.否则创建一个新对象
     *
     * @param r 关联对象
     * @return 对象实例
     */
    public W obtain(R... r) {
        W obj;
        synchronized (sPoolSync) {
            if (!mScrapHeap.isEmpty()) {
                obj = mScrapHeap.removeFirst();
            } else {
                obj = onCreate(r);
            }
        }
        if (mOnObjectActive != null) {
            mOnObjectActive.onActive(obj, r);
        }
        return obj;
    }

    /**
     * 回收实例,请确保被回收对象不在其他地方引用
     *
     * @param obj 被回收的对象
     */
    public void recycle(W obj) {
        synchronized (sPoolSync) {
            mScrapHeap.add(obj);
            onRecycle(obj);
        }
    }

    /**
     * 当未缓存任何对象的时候,会回调此方法来创建一个新的实例
     *
     * @param r 关联对象
     * @return 对象实例
     */
    protected W onCreate(R... r) {
        if (mObjectProvider != null) {
            return mObjectProvider.create(r);
        }
        return null;
    }

    /**
     * 当对象被回收的时候会回调该方法，用于处理一些回收操作
     *
     * @param obj 被回收的对象
     */
    protected void onRecycle(W obj) {
        if (mOnObjectRecycle != null) mOnObjectRecycle.onRecycle(obj);
    }

    //数据提供者
    public interface IObjectProvider<R, W> {
        W create(R... r);
    }

    //对象回收监听
    public interface OnObjectRecycleListener<W> {
        void onRecycle(W obj);
    }

    //当对象被使用的监听
    public interface OnObjectActiveListener<R, W> {
        void onActive(W w, R... r);
    }

}
