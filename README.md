ToolKit
=====
![](https://img.shields.io/badge/android-4.0%2B-blue)
[![](https://jitpack.io/v/ftmtshuashua/ToolKit.svg)](https://jitpack.io/#ftmtshuashua/ToolKit)
[![License Apache2.0](http://img.shields.io/badge/license-Apache2.0-brightgreen.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)


## 简介

> Android开发基础工具集，收集了一些常用工具

- 引入Android默认混淆配置
    - proguard_log_class.txt ：列出apk包内所有class的内部结构
    - proguard_log_seeds.txt ：列的出未混淆类和成员
    - proguard_log_unused.txt ：列出从apk中删除的代码
    - proguard_log_mapping.txt ：列出混淆前后的映射

## 包与类说明

- [x] action:适配<kbd>Lambda</kbd>语法的<kbd>CallBack</kbd>和<kbd>Call</kbd>的接口定义

- [x] adapter:各种适配器
    - ScreenAdapter:字节跳动的屏幕适配方案

- [x] analysis:辅助分析类助手相关

- [x] app:应用程序相关工具
    - AppUtils:应用管理
    - BarUtils:状态栏管理
    - ClipboardUtils:剪切板管理
    - IntentUtils:公用跳转
    - ServiceUtils:服务管理

- [x] cache:数据缓存相关
    - LruCache:Lru缓存算法
    - ObjectCacheUtils:对象复用工具

- [x] codec:编解码相关
    - Base64Utils:<kbd>Base64</kbd>编解码
    - DESUtils:<kbd>DES</kbd>编解码
    - MD5Utils:<kbd>MD5</kbd>编码
    - RSAUtils:<kbd>RSA</kbd>编解码
    - SHAUtils:<kbd>SHA</kbd>编码
    - UrlUtils:<kbd>Url</kbd>编解码
    - ZipUtils:文件压缩与解压

- [x] log:
    - LogUtils:Android日志工具

- [x] phone:设备相关
    - CpuUtils:Cpu信息
    - DeviceUtils:设备信息
    - FlashlightUtils:手电筒管理
    - GpsUtils:GPS管理
    - KeyboardUtils:虚拟键盘
    - NetworkUtils:网络管理
    - ProcessUtils:进程管理
    - SdkUtils:系统版本检查
    - ShellUtils:<kbd>Shell</kbd>脚本运行
    - ScreenUtils:屏幕相关

- [x] source:数据源
    - AssetsUtils:读取<kbd>Assets</kbd>文件夹中的数据
    - RawUtils:读取Raw文件夹中的数据
    - InternalFileUtils:读写(./data/data/包名)目录的数据
    - ExternalFileUtils:读写<kbd>sdcard</kbd>目录的数据

- [x] structure:数据结构
    - DataGroup:数据分组工具
    - KeyValue:<kbd>Key-value</kbd>的数据结构

- [x] thread:线程工具
    - ThreadHelper:线程切换工具

- [x] transform:数据转换和变换相关
    - ArraysUtils:数组转化
    - BitmapUtils:图像处理
    - BytesUtils:字节数组转化
    - ColorUtils:颜色转化
    - GpsCoordinateTransformUtils:Gps坐标转换
    - LunarUtils:日历相关
    - PinyinUtils:汉字转拼音
    - TimeUtils:时间转化
    - StringUtils:字符串相关

- [x] view:View相关
    - CanvasUtils:画布工具
    - FpsMonitor:Fps监听
    - RUtils:R文件相关工具
    - ViewSavedStateUtils:View缓存辅助工具
    - ViewUtils:View相关工具
    - XPaint:画笔工具

- [x] widget:自定义View
    - DrawableCenterTextView:文本与Drawable同时剧中
    - LazyViewPager:ViewPager懒加载
    - MarqueeTextView:跑马灯TextView
    - TestRulerView:View尺寸测量工具

## 配置依赖

```
allprojects {
    repositories {
        maven { url 'https://www.jitpack.io' }
    }
}
```

在Model的build.gradle中添加

```
dependencies {
    implementation 'com.github.ftmtshuashua:ToolKit:3.0.0-beta'
}
```


## 混淆配置
``` 

# 在APP发布时，直接移除日志相关代码
-assumenosideeffects class com.acap.toolkit.log.LogUtils {
 public static void l(...);
 public static void l_f(...);
 public static void l_ft(...);
 public static void v(...);
 public static void v_f(...);
 public static void v_ft(...);
 public static void d(...);
 public static void d_f(...);
 public static void d_ft(...);
 public static void w(...);
 public static void w_f(...);
 public static void w_ft(...);
 public static void a(...);
 public static void a_f(...);
 public static void a_ft(...);
 public static void i(...);
 public static void i_f(...);
 public static void i_ft(...);
 public static void e(...);
 public static void e_f(...);
 public static void e_ft(...);
 }
```