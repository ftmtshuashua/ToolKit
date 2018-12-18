package support.lfp.toolkit.controller;

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
 * Created by LiFuPing on 2018/6/26.
 *
 * @param <R> 关联对象，某些时候我们创建一个缓存对象需要通过其他对象的参数来创建的时候就可以利用该参数
 * @param <W> 输出对象，也就是被缓存的对象
 *
 *
 * </pre>
 */
public abstract class ObjectCacheManager<R, W> {
    final Object sPoolSync = new Object();
    final LinkedList<W> mScrapHeap = new LinkedList<>();

    public ObjectCacheManager() {

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
                obj = onCache(mScrapHeap.removeFirst());
            } else {
                obj = onCreate(r);
            }
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
    protected abstract W onCreate(R... r);

    /**
     * 当对象被回收的时候会回调该方法，用于处理一些回收操作
     *
     * @param obj 被回收的对象
     */
    protected void onRecycle(W obj) {

    }

    /**
     * 当一个对象从缓存中被提取出来的时候会回调该方法，可以在这里做一些缓存对象的初始化操作
     *
     * @param cache 从缓存中获取的对象
     * @param r     当前事件发生时候的参数列表
     * @return 缓存对象
     */
    protected W onCache(W cache, R... r) {
        return cache;
    }

}
