package support.lfp.toolkit.action;

/**
 * <pre>
 * Tip:
 *      只有一个参数的回调
 *
 * Created by LiFuPing on 2018/10/29 14:36
 * </pre>
 */
@FunctionalInterface
public interface Action1<T> {
    void call(T t);
}
