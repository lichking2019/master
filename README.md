# master
## 描述
- core 核心模块
   - aop定义
      - 日志切面
      - 异常切面
   - 抽象
      - controller抽象，封装返回结果对象|controler异常通知器
      - service抽象，为业务模块提供通用的业务逻辑，如增、删、改、查等
      - mapper抽象，为业务模块提供通用的持久化逻辑，如增、删、改、查等。通过反射技术结合Mybatis的注解，提供通用的SQL
      - entity抽象，定义通用的字段，如创建人、创建时间、修改人、修改时间、删除标识等
   - 通用工具
      - Spring上下文工具
      - Spring属性文件工具
   - 多数据源
   - 反射工具
- j2ee 依赖管理，添加必要的web项目依赖
- root maven构建方式定义
- version 管理依赖的版本