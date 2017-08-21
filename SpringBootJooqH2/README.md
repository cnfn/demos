# Spring Boot + jOOQ + H2 示例项目
## 测试验证步骤
```bash
mvn spring-boot:run # 使用 `schemal.sql` 初始化 H2 数据文件 
mvn jooq-codegen:generate -Ph2 # 根据 H2 数据文件生成 POJOs
```