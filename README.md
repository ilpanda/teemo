### Teemo
提莫快跑！我的日常 Android 开发库。


---
### 基础库

文件 | 说明
---| ---
Dimensions.kt | Context、View、Fragment 扩展，用于 dp、sp 和 px 之间的转换。
Activity.kt |  Activity、Fragment 从 Intent 中获取参数的扩展。
Context.kt | Activity 跳转到其他页面的扩展。
Async.kt | 异步执行任务，主线程回调。
File.kt | 对 Okio 进一步封装，可获取文件的 md5、sha1、sha256 或 sha512 值。
Reflect.kt | 对反射的进一步封装。
String.kt | 可求 String 的 Base64 编码、哈希值等。
Toast.kt | 对 Toast 进一步封装，处理 API 25 的异常。
View.kt | View 的扩展，防止用户连续点击，方便控制 View 的显示和隐藏。
