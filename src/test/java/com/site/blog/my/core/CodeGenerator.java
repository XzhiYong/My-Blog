package com.site.blog.my.core;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.site.blog.my.core.controller.BaseController;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

/**
 * @author xiazy
 * @since 2021年11月15日 20:41
 */
@ActiveProfiles("dev")
@SpringBootTest()
public class CodeGenerator {

    /** 开发main库 */
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/my_blog_db?useUnicode=true&allowMultiQueries=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        generator();
    }

    public static void generator() {
        // 表名，多个英文逗号分割
        String tableNames = "tb_user_music";

        String projectPath = System.getProperty("user.dir");
        // 如果是父工程下的模块在此处填入模块名
        String modelName = "";

        String mapperXmlPath = projectPath + "/" + modelName + "/src/main/resources/mapper/";

        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
            .globalConfig(builder -> {
                builder.author("夏志勇") // 设置作者
                    //.fileOverride() // 覆盖已生成文件
                    .disableOpenDir() // 禁止打开输出目录
                    .dateType(DateType.ONLY_DATE) // 使用java.util.Date
                    .outputDir(projectPath + "/" + modelName + "/src/main/java"); // 指定输出目录
            })
            .packageConfig(builder -> {
                builder.parent("com.site.blog.my.core") // 设置父包名
                    // .moduleName("system") // 设置父包模块名
                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperXmlPath)); // 设置mapperXml生成路径
            })
            .strategyConfig(builder -> {
                builder.addInclude(tableNames.split(",")) // 设置需要生成的表名
                    .addTablePrefix("tb_"); // 设置过滤表前缀

                builder.controllerBuilder()
                    .enableRestStyle()
                    // .enableHyphenStyle() //驼峰转连字符
                    .superClass(BaseController.class);

                builder.entityBuilder()
                    .naming(NamingStrategy.underline_to_camel) //数据库表映射到实体的命名策略
                    .columnNaming(NamingStrategy.no_change) //数据库表字段映射到实体的命名策略
                    .enableLombok() // 开启lombok
                    .logicDeleteColumnName("is_deleted") //逻辑删除字段名
                    .enableRemoveIsPrefix() //去掉布尔值的is_前缀（确保tinyint(1)）
                // .superClass(BaseEntity.class)
                // .addSuperEntityColumns("id", "create_time", "update_time", "is_deleted")
                ;

                builder.serviceBuilder()
                    .formatServiceFileName("%sService")
                // .superServiceClass(BaseService.class)
                // .superServiceImplClass(BaseServiceImpl.class)
                ;
            })
            .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
            .execute();
    }

}
