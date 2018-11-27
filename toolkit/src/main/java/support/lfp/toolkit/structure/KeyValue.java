package support.lfp.toolkit.structure;

/**
 * <pre>
 * Tip:
 *      KeyValue数据结构
 *
 * Function:
 *      getKey()        :获得Key
 *      getValue()      :获得Value
 *
 * Created by LiFuPing on 2018/8/6.
 * </pre>
 */
public class KeyValue<K, V> {
    public final K key;
    public final V value;

    /**
     * 创建一个KeyView结构的数据
     *
     * @param key   The key
     * @param value The value
     */
    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获得Key
     *
     * @return The key
     */
    public K getKey() {
        return key;
    }

    /**
     * 获得Value
     *
     * @return The value
     */
    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyValue keyValue = (KeyValue) o;

        return key == null ? keyValue.key == null : key.equals(keyValue.key);

    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "KeyValue{" + "key='" + key + '\'' + ", value=" + value + '}';
    }
}
