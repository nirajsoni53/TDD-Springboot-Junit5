package com.niraj53.Junittesting.tddtesting.user;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {
    @Bean
    public DatabaseConfigBean dbUnitDatabaseConfig() {
        DatabaseConfigBean dbUnitDbConfig = new DatabaseConfigBean();
        dbUnitDbConfig.setDatatypeFactory(new MySqlDataTypeFactory());
        dbUnitDbConfig.setSkipOracleRecyclebinTables(true);
        dbUnitDbConfig.setQualifiedTableNames(true);
        return dbUnitDbConfig;
    }

    @Bean
    public DatabaseDataSourceConnectionFactoryBean getConnectionFactoryBean(){
        DatabaseDataSourceConnectionFactoryBean factoryBean = new DatabaseDataSourceConnectionFactoryBean();
        factoryBean.setDatabaseConfig(dbUnitDatabaseConfig());
        factoryBean.setSchema("springboot_tdd");
        return factoryBean;
    }
}
