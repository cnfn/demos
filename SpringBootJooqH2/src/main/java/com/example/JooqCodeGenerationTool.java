package com.example;

import org.jooq.util.GenerationTool;
import org.jooq.util.jaxb.Configuration;
import org.jooq.util.jaxb.Database;
import org.jooq.util.jaxb.ForcedType;
import org.jooq.util.jaxb.Generate;
import org.jooq.util.jaxb.Generator;
import org.jooq.util.jaxb.Jdbc;
import org.jooq.util.jaxb.Target;

/**
 * @author zhixiao.mzx
 * @date 2017/08/31
 */
public class JooqCodeGenerationTool {
    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration()
            .withJdbc(new Jdbc()
                .withDriver("org.h2.Driver")
                .withUrl("jdbc:h2:file:./h2db;FILE_LOCK=NO")
                .withUser("sa")
                .withPassword(""))
            .withGenerator(new Generator()

                // 使用 JSR-310 时间类型: https://stackoverflow.com/questions/31748327/jooq-support-for-jsr310
                .withGenerate(new Generate()
                    .withJavaTimeTypes(true))

                .withDatabase(new Database()
                    .withName("org.jooq.util.h2.H2Database")
                    .withIncludes(".*")
                    .withExcludes("")
                    .withInputSchema("PUBLIC")
                    .withForcedTypes(new ForcedType()
                            .withUserType("com.alibaba.fastjson.JSONArray")
                            .withBinding("com.alibaba.jooq.type.binding.DbJson2JsonArrayBinding")
                            .withExpression(".*s$")
                            .withTypes("json"),
                        new ForcedType()
                            .withUserType("com.alibaba.fastjson.JSONObject")
                            .withBinding("com.alibaba.jooq.type.binding.DbJson2JsonObjectBinding")
                            .withExpression(".*[^s]$")
                            .withTypes("json")))
                .withTarget(new Target()
                    .withPackageName("jooq.generated")
                    .withDirectory("src/main/java")));

        GenerationTool.generate(configuration);
    }
}
