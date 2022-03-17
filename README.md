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
Json.kt  |  Json 的扩展，对 Gson 做了进一步的封装。
Reflect.kt | Kotlin 反射的封装。
Random.kt | 开发过程中，快速生成随机颜色。
SharePreference.kt | 使用属性代理封装了 SharePreference 。

---

### 组件库

组件 | 说明
---| ---
NestedScrollableHost | ViewPage2 中嵌套 RecyclerView 时，处理同向滑动冲突。使用 NestedScrollableHost 作为 RecyclerView 的父布局。
HorizonTalToolView | TODO：水平滑动工具栏 View。
CustomPopWindow | TODO: PopWindow 的链式调用。

---

### 模块及其包名

模块名称 | 包名 | 描述 |  版本说明
---| --- | --- | ---
arch-java-base | teemo-java-base | java 基础库。 | `io.github.ilpanda.teemo:teemo-java-base:1.0.0`
arch-toast | teemo-android-toast |  android toast UI 库 | `io.github.ilpanda.teemo:teemo-android-toast:1.0.0`
arch-common | teemo-android-common |  android 基础库 | `io.github.ilpanda.teemo:teemo-android-common:1.0.0`
arch-debug | teemo-android-debug | android 调试库，包括 UI。 | `io.github.ilpanda.teemo:teemo-android-debug:0.0.9-SNAPSHOT`
arch-epic |  teemo-android-epic |  android epic Hook 框架。 | `io.github.ilpanda.teemo:teemo-android-epic:1.3.0`
arch-epic |  teemo-android-epic-no-op |  android epic Hook 框架。 | `io.github.ilpanda.teemo:teemo-android-epic-no-op:1.3.0`
arch-epic-demo |  teemo-android-epic-demo |  android epic Hook 示例框架。 | `io.github.ilpanda.teemo:teemo-android-epic-demo:1.3.0`



