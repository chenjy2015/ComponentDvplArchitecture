## ComponentDvplArchitecture (组件化开发架构demo)

### 基于kotlin为开发语言 引入阿里云Arouter框架 自定义组件化开发模式架构 

* app（主工程） 
 * 做一些初始化操作
 * 主界面ui与固定不可变的界面实现
* baselib （基础包）
 * 所有工程中所需要的共同依赖库
* clienta（基础业务层）
 * 包含工程中主要的基础业务逻辑抽象实现 例如：登录，退出等。
* clientb（基础网络层）
 * 包含一些网络基础库的封装
 ---
### 基础类 ###

* config.gradle
 * 基础配置 统一所有module配置 在根目录gradle.gradle中引入 其他module统一调用
 
&emsp; 示例:   
&emsp;&emsp;&emsp;&emsp;`apply from : "config.gradle"` 

&emsp;&emsp;&emsp;&emsp;`def config = rootProject.ext`

&emsp;&emsp;&emsp;&emsp;`implementation config.android.constraintlayout`

 		
* BaseApplication 
 * 自定义实现类继承自Application 存储共享数据如context对象等 方便其他module调用
* BaseActivity
 * 基础Activity类 用于初始化一些共同数据如第三方sdk等
* BaseUIActivity
 * 基础ui控制类 初始化viewmodel和databinding对象 
