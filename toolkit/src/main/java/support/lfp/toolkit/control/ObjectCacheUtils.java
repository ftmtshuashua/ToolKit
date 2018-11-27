package support.lfp.toolkit.control;

import java.util.LinkedList;

/**
 * <pre>
 * Tip:
 *      线程安全的对象复用工具
 *
 * Function:
 *      obtain()    :获得一个实例
 *      recycle()   :回收实例
 *      create()    :创建实例
 *
 * Created by LiFuPing on 2018/6/26.
 *
 * @param <R> 关联对象，某些时候我们创建一个缓存对象需要通过其他对象的参数来创建的时候就可以利用该参数
 * @param <W> 输出对象，也就是被缓存的对象
 *
 *
 * </pre>
 */
public abstract class ObjectCacheUtils<R, W> {
    final Object sPoolSync = new Object();
    final LinkedList<W> mScrapHeap = new LinkedList<>();

    public ObjectCacheUtils() {

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
                obj = create(r);
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
        }
    }

    /**
     * 当未缓存任何对象的时候,会回调此方法来创建一个新的实例
     *
     * @param r 关联对象
     * @return 对象实例
     */
    public abstract W create(R... r);


}
