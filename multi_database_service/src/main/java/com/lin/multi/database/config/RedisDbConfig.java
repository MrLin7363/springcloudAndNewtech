
package com.lin.multi.database.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;

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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 主数据库配置加载类
 *
 * @since 2022/10/31
 */
@Configuration
@MapperScan(basePackages = "com.lin.redis",
    sqlSessionFactoryRef = "RedisSqlSessionFactory")
public class RedisDbConfig {
    private static final int POOLSIZE = 50;

//    @Autowired
//    private MybatisPlusInterceptor paginationInterceptor;

    @Value("${mybatis-plus.enable-log}")
    private boolean enableLog;

    /**
     * 获取数据源
     *
     * @return 数据源
     */
    @Bean(name = "RedisDataSource")
    @ConfigurationProperties(prefix = "spring.redisdb.datasource")
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
    @Bean(name = "RedisSqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("RedisDataSource") DataSource dataSource)
        throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.addInterceptor(paginationInterceptor); // 设置分页插件
        if (enableLog) {
            configuration.setLogImpl(StdOutImpl.class); // 打印sql
        }
        bean.setConfiguration(configuration);
        // 设置数据源
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).setMaximumPoolSize(POOLSIZE);
        }
        bean.setDataSource(dataSource);
        // 设置mapping文件映射
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/redis/**/*.xml"));
        SqlSessionFactory sqlSessionFactory = bean.getObject();
        // 设置驼峰映射
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactory;
    }

    /**
     * 获取事务管理
     *
     * @param dataSource 数据源
     * @return 事务管理
     */
    @Bean(name = "RedisTransactionManager")
    public DataSourceTransactionManager getTransactionManager(@Qualifier("RedisDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 获取Template
     *
     * @param sqlSessionFactory 工厂
     * @return Template
     * @throws Exception 异常
     */
    @Bean(name = "RedisSqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate(
        @Qualifier("RedisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
