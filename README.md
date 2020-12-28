# ToolKit
[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)


`ToolKit`是Android开发基础工具集，收集了一些常用工具 

###包与类说明
action:     适配Lambda语法的CallBack和Call的接口定义

analysis:   辅助分析类助手相关

app:        应用程序相关工具
    AppUtils:应用管理
    BarUtils:StateBar管理
    ClipboardUtils:剪切板管理
    IntentUtils:公用跳转
    ServiceUtils:服务管理

cache:      数据缓存相关

codec:      编解码相关
    Base64Utils:Base64编解码
    DESUtils:DES编解码
    MD5Utils:MD5编码
    RSAUtils:RSA编解码
    SHAUtils:SHA编码
    UrlUtils:Url编解码
    ZipUtils:文件压缩与解压

log:

phone:      设备相关
    CpuUtils:Cpu信息
    DeviceUtils:设备信息
    FlashlightUtils:手电筒管理
    GpsUtils:GPS管理
    KeyboardUtils:虚拟键盘
    NetworkUtils:网络管理
    ProcessUtils:进程管理
    SdkUtils:系统版本检查
    ShellUtils:Shell脚本运行
    ScreenUtils:屏幕相关

source:     数据源 
    AssetsUtils:读取Assets文件夹中的数据
    RawUtils:读取Raw文件夹中的数据
    InternalFileUtils:读写(./data/data/包名)目录的数据
    ExternalFileUtils:读写(Sdcard)目录的数据
    
structure:  数据结构
    DataGroup:数据分组工具
    KeyValue:Key-value的数据结构

thread:     线程工具
    ThreadHelper:线程切换工具

transform:  数据转换和变换相关
    ArraysUtils:数组转化
    BitmapUtils:图像处理
    BytesUtils:字节数组转化
    ColorUtils:颜色转化
    GpsCoordinateTransformUtils:Gps坐标转换
    LunarUtils:日历相关
    PinyinUtils:汉字转拼音
    TimeUtils:时间转化
    StringUtils:字符串相关
    
view:       View相关



ToolKit:工具集初始化入口


## 配置依赖

在项目的build.gradle中添加
```
allprojects {
    repositories {
        maven { url 'https://www.jitpack.io' }
    }
}
```
在Model的build.gradle中添加 [![](https://jitpack.io/v/ftmtshuashua/ToolKit.svg)](https://jitpack.io/#ftmtshuashua/ToolKit)
```
dependencies {
    implementation 'com.github.ftmtshuashua:ToolKit:version'
}
```
该项目在Suppert基础上搭建,需要以下库
```
implementation 'com.android.support:appcompat-v7:version'
```



#### 问题反馈

如果你在使用ToolKit中遇到任何问题可以提[Issues](https://github.com/ftmtshuashua/ToolKit/issues)出来。另外欢迎大家为ToolKit贡献智慧，欢迎大家[Fork and Pull requests](https://github.com/ftmtshuashua/ToolKit)。

如果觉得对你有用的话，点一下右上的星星赞一下吧。

## LICENSE

```
Copyright (c) 2018-present, ToolKit Contributors.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
