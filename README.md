# ComponentDvplArchitecture

# 组件化开发架构测试demo
### 基于kotlin为开发语言 引入阿里云Arouter框架 自定义组件化开发模式架构 
    根目录gradle配置自定义布尔值变量 
    1. 在主工程gradle判断是否用于引入该module 
    2. module中配置两个androidmanifest.xml 一个为当前工程为module时引用 另一个为当前工程为独立程序时引用
    
### 概念    
#### app 主工程用于引入对应module 本身空实现
#### baselib 基础工具层
#### clienta 基础业务层
#### clientb 基础路由层
