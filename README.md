# master
## 描述
- core 核心模块
   - aop定义（aop包）
      - 日志切面
      - 异常切面
   - 抽象（base包）
      - controller抽象，封装返回结果对象|controler异常通知器
      - service抽象，为业务模块提供通用的业务逻辑，如增、删、改、查等
      - mapper抽象，为业务模块提供通用的持久化逻辑，如增、删、改、查等。通过反射技术结合Mybatis的注解，提供通用的SQL
         - 对mysql的支持
         - 对mongodb的支持（支持继承和灵活性较高的组合方式）
      - entity抽象，定义通用的字段，如创建人、创建时间、修改人、修改时间、删除标识等
   - 通用工具
      - Spring上下文工具
      - Spring属性文件工具
      - 随机数生成工具
      - UUID生成工具
      - 日期工具类
   - 配置
      - Http请求过滤器条件注入，根据配置文件中定义的spring.http.encoding的配置，动态创建CharacterEncodingFilter
      - 通用配置
         - druid数据源、监视器配置
         - 动态数据源注册器 引入
         - bean扫描目录的定义，扫描范围是com.wt 下属的所有包
   - 多数据源（datasource包）
      - 核心代码是DynamicDataSource，通过继承Springboot提供的DynamicDataSource，来实现多数据源
      - 定义了aop切面DynamicDattaSourceInterceptor，拦截方法调用，发现有指定的@TargetDataSource注解，就会将当前线程的数据源指定为注解指定的数据源
      - 多数据源相关的配置类，利用了Springboot的动态配置特性，定义spring.factories文件指定DynamicDataSourceConfiguration配置类，根据配置文件中的slave.enable的值决定是否加载动态数据源的相关配置
   - 反射工具
   - 缓存
      - 将redis、ehcache的配置，抽象到框架中
      - 结合Springboot的条件注解，判断只有当配置文件中定义了响应的缓存，对应的配置才会加载，使用com.wt.framework.config.cache.enable[true|false]|type[redis|ehcache]
   - json处理工具
- j2ee 依赖管理，添加必要的web项目依赖
- root maven构建方式定义
- version 管理依赖的版本

- 创建表的公共字段
   ```
   alter table FanKpiT
      	add founderId varchar(36) null comment '创建人ID';
      
      alter table FanKpiT
      	add founderName varchar(50) null comment '创建人姓名';
      
      alter table FanKpiT
      	add createDateTime datetime null comment '创建时间';
      
      alter table FanKpiT
      	add modifierId datetime null comment '修改人ID';
      
      alter table FanKpiT
      	add modifierName varchar(50) null comment '修改人姓名';
      
      alter table FanKpiT
      	add modifyDateTime datetime null comment '修改时间';
      
      alter table FanKpiT
      	add deleteFlag tinyint default 0 null comment '删除标识';
   ```

- 配置
   ```
   com:
     wt:
       framework:
         config:
           cache:
             #开启缓存
             enable: true
             #类型 目前支持redis|ehcache
             type: redis
           #swagger开关，默认关闭
           swagger:
             enable: true
           #跨域配置
           crossDomain:
             enable: true
             allowedOrgin: '*'
             allowedHeader: '*'
             allowedMethod: '*'
           #切换持久层（需要应用端的配置文件支持，扫描不同的持久层目录）
           dao:
               type: center
   ```