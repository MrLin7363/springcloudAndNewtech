/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */

package com.lin.multi.database.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 第二种方式配置多数据库
 *
 * @since 未知
 */
@Configuration
@MapperScan(basePackages = "com.lin.multi.database.mapper.mult", sqlSessionTemplateRef = "MultSqlSessionTemplate")
public class MultDbConfig {
    @Autowired
    private PaginationInterceptor paginationInterceptor;

    @Value("${mybatis-plus.enable-log}")
    private boolean enableLog;

    /**
     * 获取数据源  自定义路径
     *
     * @return 数据源
     */
    @Bean(name = "MultDataSource")
    @ConfigurationProperties(prefix = "spring.multdb.datasource")
    @Primary
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 获取sqlSession工厂
     *
     * @param dataSource 数据源
     * @return sqlSession工厂
     * @throws Exception 异常
     */
    @Bean(name = "MultSqlSessionFactory")
    @Primary
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("MultDataSource") DataSource dataSource)
        throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.addInterceptor(paginationInterceptor); // 设置分页插件
        if (enableLog) {
            configuration.setLogImpl(StdOutImpl.class); // 打印sql
        }
        bean.setConfiguration(configuration);
        // 设置数据源
        bean.setDataSource(dataSource);
        // 设置mapping文件映射
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/mult/*.xml"));
        // 设置驼峰映射
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    /**
     * 获取事务管理
     *
     * @param dataSource 数据源
     * @return 事务管理
     */
    @Bean(name = "MultTransactionManager")
    @Primary
    public DataSourceTransactionManager getTransactionManager(@Qualifier("MultDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 获取Template
     *
     * @param sqlSessionFactory 工厂
     * @return Template
     * @throws Exception 异常
     */
    @Bean(name = "MultSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate getSqlSessionTemplate(
        @Qualifier("MultSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}