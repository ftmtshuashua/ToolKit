#基础混淆配置




#在APP发布时，直接移除日志相关代码
-assumenosideeffects class com.acap.toolkit.log.LogUtils {
 public static void l(...);
 public static void fl(...);
 public static void flt(...);
 public static void v(...);
 public static void fv(...);
 public static void fvt(...);
 public static void d(...);
 public static void fd(...);
 public static void fdt(...);
 public static void w(...);
 public static void fw(...);
 public static void fwt(...);
 public static void a(...);
 public static void fa(...);
 public static void fat(...);
 public static void i(...);
 public static void fi(...);
 public static void fit(...);
 public static void e(...);
 public static void fe(...);
 public static void fet(...);
 }