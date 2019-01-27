package com.interview.loganalysis.hsqldb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {

        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        final EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .setName("alertlogs")
                .addScript("db/sql/create-db.sql")
                .build();
        return db;
    }
}
