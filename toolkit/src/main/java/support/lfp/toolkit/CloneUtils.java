package support.lfp.toolkit;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * <pre>
 * Tip:
 *      克隆工具
 *
 * Function:
 *      deepClone()         :深拷贝
 *      shallowClone()      :浅拷贝
 *
 * Created by LiFuPing on 2018/6/27.
 * </pre>
 */
public class CloneUtils {


    /**
     * Deep clone.
     *
     * @param data The data.
     * @param <T>  The value type.
     * @return The object of cloned
     */
    public static <T> T deepClone(final Serializable data) {
        if (data == null) return null;
        return ConvertUtils.bytes2Object(ConvertUtils.serializable2Bytes(data));
    }

    /**
     * Shallow  clone.
     * <br>
     * ①对于数据类型是基本数据类型的成员变量，浅拷贝会直接进行值传递，也就是将该属性值复制一份给新的对象。因为是两份不同的数据，所以对其中一个对象的该成员变量值进行修改，不会影响另一个对象拷贝得到的数据。
     * <br>
     * ②对于数据类型是引用数据类型的成员变量，比如说成员变量是某个数组、某个类的对象等，那么浅拷贝会进行引用传递，也就是只是将该成员变量的引用值（内存地址）复制一份给新的对象。因为实际上两个对象的该成员变量都指向同一个实例。在这种情况下，在一个对象中修改该成员变量会影响到另一个对象的该成员变量值。
     *
     * @param data The data.
     * @param <T>  The value type.
     * @return The object of cloned
     */
    public static <T> T shallowClone(final T data) {
        if (data == null) return null;
        try {
            Class<?> orgClassType = data.getClass();
            T clone_obj = (T) orgClassType.newInstance();

            Field[] fields = orgClassType.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(clone_obj, field.get(data));
            }
            return clone_obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Shallow  clone.
     * <br>
     * ①对于数据类型是基本数据类型的成员变量，浅拷贝会直接进行值传递，也就是将该属性值复制一份给新的对象。因为是两份不同的数据，所以对其中一个对象的该成员变量值进行修改，不会影响另一个对象拷贝得到的数据。
     * <br>
     * ②对于数据类型是引用数据类型的成员变量，比如说成员变量是某个数组、某个类的对象等，那么浅拷贝会进行引用传递，也就是只是将该成员变量的引用值（内存地址）复制一份给新的对象。因为实际上两个对象的该成员变量都指向同一个实例。在这种情况下，在一个对象中修改该成员变量会影响到另一个对象的该成员变量值。
     *
     * @param from 拷贝源
     * @param to   拷贝目标
     * @param <T>  The value type.
     */
    public static <T> void shallowClone(final T from, final T to) {
        if (from == null || to == null) return;
        try {
            Class<?> orgClassType = from.getClass();
            Field[] fields = orgClassType.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(to, field.get(from));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
