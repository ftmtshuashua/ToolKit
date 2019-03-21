package support.lfp.toolkit.action;

/**
 * <pre>
 * Tip:
 *
 * Function:
 *
 * Created by LiFuPing on 2019/3/21 13:45
 * </pre>
 */
@FunctionalInterface
public interface Callback2<T1, T2, R> {
    R call(T1 t1, T2 t2);
}
