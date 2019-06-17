package com.eastrobot.kbs.template.mybatis.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-06-17 11:11
 */
public class CodeGenerator {

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        String basePackage = "com.eastrobot.kbs.template";
        String projectPath = System.getProperty("user.dir");
        String currentUser = System.getProperty("user.name");
        // 全局配置
        mpg.setGlobalConfig(
                new GlobalConfig()
                        .setOutputDir(projectPath + "/src/test/generator")
                        .setAuthor(currentUser)
                        .setOpen(false)
                        .setSwagger2(true)
                        .setIdType(IdType.UUID)
                        .setBaseResultMap(true)
                        .setBaseColumnList(true)
        );

        // 数据源配置
        mpg.setDataSource(
                new DataSourceConfig()
                        .setUrl("jdbc:mysql://172.16.1.119:3306/philips?useUnicode=true&useSSL=false" +
                                "&characterEncoding=utf8")
                        // .setSchemaName("philips")
                        .setDriverName("com.mysql.jdbc.Driver")
                        .setUsername("philips")
                        .setPassword("philips")
        );

        // 包配置
        PackageConfig packageConfig = new PackageConfig().setModuleName(scanner("模块名")).setParent(basePackage);
        mpg.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/test/generator/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        mpg.setTemplate(new TemplateConfig());

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 这些字段来自父类
        strategy.setSuperEntityColumns("id", "createDate", "modifyDate", "createUser", "modifyUser", "delFlag");
        strategy.setSuperEntityClass(basePackage + ".model.entity.BaseEntity");
        strategy.setSuperControllerClass(basePackage + ".web.controller.BaseController");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 该表前缀是什么, 在生成实体时去除前缀
        strategy.setTablePrefix("DEV_");
        // 逻辑删除标示
        strategy.setLogicDeleteFieldName("delFlag");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
