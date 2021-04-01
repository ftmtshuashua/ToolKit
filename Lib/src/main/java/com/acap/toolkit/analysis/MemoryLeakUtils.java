package com.acap.toolkit.analysis;

import com.acap.toolkit.log.LogUtils;
import com.acap.toolkit.structure.DataGroup;
import com.acap.toolkit.transform.ArraysUtils;

import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * <pre>
 * Tip:
 *      内存泄漏检查
 *
 * Function:
 *
 * Created by ACap on 2020/12/28.
 * </pre>
 */
public class MemoryLeakUtils {

    private static final String TAG = "内存泄漏监控";

    private static MemoryLeakUtils mInstance;
    private OnReferenceListener mOnReferenceListener;
    private final Timer mTimer = new Timer();

    private List<WeakReference<Object>> mRefer = new ArrayList<>();

    public static final void init() {
        init(object -> new WeakReference<>(object));
    }

    public static final void init(OnReferenceListener listener) {
        if (mInstance != null) throw new RuntimeException("内存泄漏监测已初始化");
        mInstance = new MemoryLeakUtils(listener);
    }

    private MemoryLeakUtils(OnReferenceListener listener) {
        mOnReferenceListener = listener;

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<String> look = look();
                if (look != null && !look.isEmpty()) {
                    LogUtils.i(TAG, MessageFormat.format("未释放 -> {1}", look.size(), ArraysUtils.join(look, " , ")));
                }
            }
        }, 1000, 3000);
    }

    private synchronized List<String> look() {
        List<ObjectInfo> array = new ArrayList<>();
        for (int i = mRefer.size() - 1; i >= 0; i--) {
            WeakReference<Object> objectPhantomReference = mRefer.get(i);
            Object o = objectPhantomReference.get();
            if (o == null) mRefer.remove(i);
            else {
                ObjectInfo id = getId(o);
                if (id != null) array.add(id);
            }
        }

        if (array.isEmpty()) {
            return null;
        } else {

            List<String> stringarray = new ArrayList<>();
            //组合
            DataGroup<String, ObjectInfo> utils = new DataGroup<>();

            for (ObjectInfo objectInfo : array) {
                utils.put(objectInfo.name, objectInfo);
            }
            ArrayList<String> groups = utils.getGroups();
            for (String group : groups) {
                ArrayList<ObjectInfo> ids = utils.getArray(group);
                ArrayList<String> strings = new ArrayList<>();
                for (ObjectInfo id : ids) {
                    strings.add(id.hashCode);
                }
                stringarray.add(MessageFormat.format("{0}({1})", group, ArraysUtils.join(strings, ",")));
            }


            return stringarray;
        }
    }


    //获得一个引用
    private WeakReference<Object> get(Object object) {
        WeakReference<Object> objectWeakReference = null;
        if (mOnReferenceListener != null) {
            objectWeakReference = mOnReferenceListener.get(object);
        }
        return objectWeakReference;
    }

    private void watchs(Object object) {
        mRefer.add(get(object));
    }

    public static final void watch(Object object) {
        if (mInstance != null) mInstance.watchs(object);
    }


    /*创建引用*/
    public interface OnReferenceListener {
        WeakReference<Object> get(Object object);
    }


    //获得对象ID
    private static ObjectInfo getId(Object obj) {
        if (obj == null) return null;
        return new ObjectInfo(obj);
    }

    private static final class ObjectInfo {
        String name;
        String hashCode;

        public ObjectInfo(Object obj) {
            name = obj.getClass().getSimpleName();
            hashCode = Integer.toHexString(System.identityHashCode(obj));
        }
    }

}
