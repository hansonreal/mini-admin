package com.github.mini.gen;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.sql.Types;
import java.util.Collections;

public class MiniCodeGen {

    public static final String THIS_MODULE_NAME = "mini-admin-codegen"; //当前项目名称

    public static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/mini-admin?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    // 多个用,  拼接
    public static final String TABLE_NAMES = "sys_dict,sys_dict_item,sys_permission,sys_role,sys_role_permission,sys_user,sys_user_auth,sys_user_role,sys_config,sys_log";

    public static final String TABLE_NAMES_PREFIX = "c_,a_,sys_";

    public static void main(String[] args) {
        autoGen();
    }


    public static void autoGen() {
        // 当前项目路径
        final String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create(
                        new DataSourceConfig.Builder(JDBC_URL, USERNAME, PASSWORD)
                                .typeConvert(
                                        new MySqlTypeConvert() //OracleTypeConvert
                                )
                )
                .globalConfig(// 全局配置
                        builder -> {
                            builder.author("hansonreal") // 设置作者
                                    .enableSwagger() // 开启 swagger 模式
                                    .fileOverride() // 覆盖已生成文件
                                    .outputDir(projectPath + "/src/main/java")// 指定输出目录
                                    .dateType(DateType.ONLY_DATE)//
                                    .disableOpenDir()//不打开资源管理器
                            ;
                        })
                .dataSourceConfig( // 数据源相关配置
                        builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                                    // 自定义类型转换
                                    if (typeCode == Types.SMALLINT) {
                                        return DbColumnType.INTEGER;
                                    }
                                    if (typeCode == Types.TINYINT) {
                                        return DbColumnType.BYTE;
                                    }
                                    return typeRegistry.getColumnType(metaInfo);
                                }
                        )
                )
                .packageConfig( // 包配置
                        builder -> {
                            builder.parent("ls.ov") // 设置父包名
                                    .moduleName("ipay") // 设置父包模块名,系统表不需要父包模块名称ls.ov.ipay.thirdpay.wx.entity
                                    .entity("core.entity")//实体包名
                                    .mapper("core.mapper")// Mapper接口目录
                                    .xml("core.mapper") //xml目录
                                    .service("core.service")//service 目录
                                    .serviceImpl("core.service.impl")//service impl 目录
                                    .controller("core.web.ctrl") //ctrl 目录
                            ;
                        }
                )
                .strategyConfig( //策略配置
                        builder -> {
                            builder.addInclude(TABLE_NAMES.split(",")) // 设置需要生成的表名
                                    .addTablePrefix(TABLE_NAMES_PREFIX.split(","))
                                    .entityBuilder() // 实体构建器
                                    .idType(IdType.NONE)//主键生成策略，默认的雪花算法
                                    .columnNaming(NamingStrategy.underline_to_camel) // 下划线到驼峰的命名方式
                                    .naming(NamingStrategy.underline_to_camel) // 下划线到驼峰的命名方式
                                    .enableLombok() // 开启lombok
                                    .enableTableFieldAnnotation() // 自动添加 field注解
                                    .build()
                                    .mapperBuilder() //mapper构建器
                                    .enableBaseResultMap() //生成resultMap
                                    .enableBaseColumnList() //XML中生成基础列
                                    .build()
                                    .serviceBuilder() //service构建器
                                    .formatServiceImplFileName("%sService")// IUserService接口实现类UserServiceImpl 改为 UserService
                                    .build()
                                    .controllerBuilder() //controller构建器
                                    .enableRestStyle()
                                    .enableHyphenStyle()
                            ; // 设置过滤表前缀
                        })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}