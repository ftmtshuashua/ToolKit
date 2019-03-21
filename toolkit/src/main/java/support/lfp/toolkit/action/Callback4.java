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
public interface Callback4<T1, T2, T3, T4, R> {
    R call(T1 t1, T2 t2, T3 t3, T4 t4);
}
