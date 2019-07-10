> 基于springBoot的template项目

依赖公共pom (`kbase-pom`) 公用组件 (`kbase-common`) 统一版本的模版项目

>
[![Build Status](https://travis-ci.org/Yogurt-lei/kbase-template.svg?branch=develop)](https://travis-ci.org/Yogurt-lei/kbase-template)
![license](https://img.shields.io/github/license/mashape/apistatus.svg)
![SpringBoot 2.1.5-RELEASE](https://img.shields.io/badge/SpringBoot-2.1.5--RELEASE-green.svg)
![Java v1.8](https://img.shields.io/badge/Java-v1.8.0__162-blue.svg)
![Maven v3.5.3](https://img.shields.io/badge/Maven-v3.5.3-blue.svg)


### 1. 初始化项目

1. `git clone {remote-url}` 本仓库, 
2. 删除.git文件夹 修改`template`为项目包名 修改`SwaggerConfig`中的描述
3. `git init` 初始化仓库 
4. `git add .`暂存所有提交 
5. `git commit -m 'msg'` 提交本次暂存 
6. `git remote add origin {remote-url}` 关联你项目的仓库
7. `git push` 推送本次提交
8. 修改 `log4j-spring.xml` 中 `LOG_HOME` 为项目名
9. 修改 `application.properties` 中的一些配置(例如 扫描的包路径, 数据库连接等)


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
│  │                  ├─dao                                     ---dao层
│  │                  │  ├─mapper                               ---Mybatis mapper interface
│  │                  │  ├─repository                           ---jpa
│  │                  ├─exception                               ---全局异常捕获及定义异常定义
│  │                  ├─model                                   ---模型层
│  │                  │  ├─dto                                  ---数据传输对象: 必须实现序列化接口(Serializable)如果多模块交互 对象包装为dto,内部流转使用类实例对象传输 外部流转toString为Json传输
│  │                  │  ├─entity                               ---数据库实体映射对象: 封装查询结果及数据库交互的CRUD操作
│  │                  │  └─vo                                   ---视图对象: Controller传入对象,必须实现序列化接口(Serializable) 使用hibernate-validater校验通过后才能在Service层使用
│  │                  ├─plugin                                  ---插件层, 易于被切换的工具, 对第三方jar的扩展, 可以被其他项目共用以至抽取到kbase-common包的内容,相比当前项目中的util更加抽象
│  │                  ├─service                                 ---服务层, 业务层 接口定义 所有方法必须有拥有非null的返回参数(或Optional), 根据面向对象请面向接口编程而非面向实现
│  │                  │  └─impl                                 ---服务层实现, 单一方法禁止超过80行. 如果拥有各个方法共有使用代码块抽取为当前内中的private方法, 如果有其他更多业务模块使用 抽取为Util公共方法
│  │                  ├─util                                    ---工具类
│  │                  └─web                                    
│  │                     ├─controller                           ---controller控制器层,逻辑对接前端,所有方法不允许为void, 必须有返回参数, 所有参数遵从`Swagger`接口定义      
│  │                     └─servlet                              ---filter listener servlet
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

#### 2.1 bean相互转换的工具

> BeanConverter中按照例子定义方法即可，相关demo在test中有例子

### 3. Spring Security with JWT
修改`KbsUserDetailsService` 中数据用户接入部分
> 登录接口 `curl -X POST "http://localhost:8000/${context-path}/login?username=&password="` 

> 登出接口 `curl -X POST "http://localhost:8000/${context-path}/logout" -H "Authorization: {Bearer token}"` 

**在未通过登录接口获取token之前,调用其他任何接口都是未授权,登录成功后token作为后续请求中Header的Authorization:{token}部分携带**

> 登录鉴权过程中的响应码说明 （其他响应码说明见ResultCode）


| 响应码  | 响应码含义                                                   |
| :-----: | :----------------------------------------------------------- |
|   0100   | 暂未使用,保留为单点登录使用                                  |
|   0101   | 用户未授权(应该先登录/login获取到token)                      |
|   0102   | 用户不存在                                                   |
|   0103   | 用户密码校验失败                                             |
|   0104   | 用户账号异常(账号锁定,账号失效等)                            |
| **0105** | 用户已经登出(该token不再有效)                                |
| **0106** | token快到期,续签token(前端在请求结果中全局处理,如果得到该响应码,替换cookie中的原有token) |
| **0107** | token非法(已过期,不正确等)                                   |

### 4. Mybatis Plus & Code Generator

> `src/test/main 中的 CodeGenerator` 类用于方便的生成Mybatis所需的一些基础代码 Controller, Service, Mapper, mapper.xml, Entity, 让我们直接变成业务驱动吧~

### 5. JPA

> 简单CRUD sample

```java
    public interface CategoryDao extends BaseRepository<Kbcategory, String> {
        /**
         * 查询一级分类 专业知识点下的分类
         */
        @Query("from Kbcategory where parent_id=(select id from Kbcategory where name='专业知识点') and name=?1")
        Kbcategory findCateByName(String name);
    
        @Query("from Kbcategory where bh like %:bh%")
        List<Kbcategory> findCateByBH(@Param("bh") String bh);
    }
    
```
> 自定义复杂查询实现
**JPA接口 RaPhysicalFileDao extends JpaRepository<RaphysicalFile, String> {加上你的自定义方法}**
定制实现 RaPhysicalFileDaoImpl(jpa默认会找实现类,xx+Impl)

> **使用default方法(推荐)**

> **使用Mybatis（推荐）**

* 注意:
1. @Query(nativeQuery=true) 可以指定为原生sql查询
2. @Query(countQuery) 设定统计分页数量的hql or sql, 分页参数必须为Pageable pageable, (see PageUtil.ofSingle等方法)
当携带此参数时,from Kbcategory where xxx 如果未指定countQuery则必须指定别名 
否则自动生成count查询 为 select count(where) from Kbcategory where xxx会抛异常
3. update and delete方法为 @Query+@Modifying+@Transactional
4. 可以直接findByIdAndCreateTimeLt这种方式 但是参数名太长可能一下看不明白, 可以考虑改成@Query

