#基础混淆配置
-optimizationpasses 5                       # 设置混淆的压缩比率 0 ~ 7
-dontusemixedcaseclassnames                 # 混淆时不使用大小写混合，混淆后的类名为小写
-dontskipnonpubliclibraryclasses            # 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclassmembers       # 指定不去忽略非公共库的成员
-dontpreverify                              # 混淆时不做预校验
-verbose                                    # 混淆时不记录日志
-ignorewarnings                             # 忽略警告
-dontshrink                                 # 代码优化
#-dontoptimize                               # 不优化输入的类文件，会导致-assumenosideeffects失效
-keepattributes *Annotation*,InnerClasses,EnclosingMethod   # 保留注解不混淆
-keepattributes Signature                   # 避免混淆泛型
-keepattributes SourceFile,LineNumberTable  # 保留代码行号，方便异常信息的追踪
-renamesourcefileattribute SourceFile       # 将文件来源重命名为“SourceFile”字符串
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* # 淆采用的算法 -> !code/simplification/cast,!field/*,!class/merging/*
-useuniqueclassmembernames                  # 把混淆类中的方法名也混淆了
-allowaccessmodification

-dump proguard_log_class.txt                # class.txt文件列出apk包内所有class的内部结构
-printseeds proguard_log_seeds.txt          # seeds.txt文件列的出未混淆类和成员
-printusage proguard_log_unused.txt         # usage.txt文件列出从apk中删除的代码
-printmapping proguard_log_mapping.txt      # mapping.txt文件列出混淆前后的映射


# 四大组件与Android原生
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

# support库的类
-keep class android.support.** {*;}
-dontwarn android.support.**
-keep interface android.support.** { *; }
-keep class androidx.** {*;}
-keep interface androidx.** {*;}
-keep public class * extends androidx.**
-dontwarn androidx.**

# support v4/7库
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# support design库
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
-keep class com.google.android.material.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**

# 保持自定义View的构造方法
-keep public class * extends android.view.View{
     *** get*();
     void set*(***);
     public <init>(android.content.Context);
     public <init>(android.content.Context, android.util.AttributeSet);
     public <init>(android.content.Context, android.util.AttributeSet, int);
 }


-keep class **.R$* { *;}

#避免layout中onclick方法（android:onclick="onClick"）混淆
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保持Parcelable
-keep class * implements android.os.Parcelable {  public static final android.os.Parcelable$Creator *;}

# Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 保持Native
-keepclasseswithmembernames class * {native <methods>;}


# 保持枚举的valuse方法
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}
-keepattributes JavascriptInterface


# Kotlin相关混淆配置
-keep class kotlin.* { *; }
-keep class org.jetbrains.* { *; }


# 在APP发布时，直接移除日志相关代码
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