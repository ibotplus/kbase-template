> 基于springBoot的template项目

依赖公共pom (`kbase-pom`) 公用组件 (`kbase-common`) 统一版本的模版项目


### 1. 依次执行以下操作初始化

1. `git clone {remote-url}` 本仓库, 
2. 删除.git文件夹 修改`template`为项目包名 修改`SwaggerConfig`中的描述
3. `git init` 初始化仓库 
4. `git add .`暂存所有提交 
5. `git commit -m 'msg'` 提交本次暂存 
6. `git remote add origin {remote-url}` 关联远程仓库
7. `git push` 推送本次提交

至此,完成项目的初始化工作.

### 2. 包结构说明
```markdown
src.
├─main
│  ├─java
│  │  └─com
│  │      └─eastrobot
│  │          └─kbs
│  │              └─template                                    ---该层为基本路径 clone 完更改为项目名
│  │                  ├─annotation                              ---自定义注解
│  │                  ├─aspect                                  ---切面
│  │                  ├─config                                  ---Configuration 及 Properties
│  │                  ├─exception                               ---全局异常捕获及定义异常定义
│  │                  ├─module                                  ---模块层 关联具体业务子包
│  │                  │  ├─base                                 ---基本模块将被其他模块继承扩展使用
│  │                  │  │  ├─dao                               ---数据层
│  │                  │  │  │  ├─mapper                         ---Mybatis基本包
│  │                  │  │  │  └─repository                     ---JPA基本包
│  │                  │  │  ├─model                             ---模型层
│  │                  │  │  │  ├─dto                            ---数据传输对象 必须实现序列化接口(Serializable)如果多模块交互 对象包装为dto,内部流转使用类实例对象传输 外部流转toString为Json传输
│  │                  │  │  │  ├─entity                         ---数据库实体映射对象 封装查询结果及数据库交互的CRUD操作
│  │                  │  │  │  └─vo                             ---视图对象 Controller传入对象,必须实现序列化接口(Serializable) 使用hibernate-validater校验通过后才能在Service层使用
│  │                  │  │  ├─service                           ---服务层, 业务层 接口定义 所有方法必须有拥有非null的返回参数(或Optional), 根据面向对象请面向接口编程而非面向实现
│  │                  │  │  │  └─impl                           ---服务层实现, 单一方法禁止超过80行. 如果拥有各个方法共有使用代码块抽取为当前内中的private方法, 如果有其他更多业务模块使用 抽取为Util公共方法
│  │                  │  │  └─controller                        ---控制器层,逻辑对接前端,所有方法不允许为void, 必须有返回参数, 所有参数遵从`Swagger`接口定义
│  │                  │  └─system                               ---实际业务模块                          
│  │                  │     └─controller                   
│  │                  ├─plugin                                  ---插件层, 易于被切换的工具, 对第三方jar的扩展, 可以被其他项目共用以至抽取到kbase-common包的内容,相比当前项目中的util更加抽象
│  │                  ├─util                                    ---工具类
│  │                  └─web                                     ---filter listener servlet
│  └─resources                                                  ---resources
│      ├─conf                                                   ---相关属性配置 
│      │  └─mybatis                                             ---mybatis-config
│      │      └─mapper                                          ---mybatis-mapper
│      ├─static                                                 ---静态资源 js css images等
│      └─templates                                              ---thymeleaf freemarker等
└─test                                                          ---JUnit测试
    └─java
        └─com
            └─eastrobot
                └─kbs
                    └─template
```