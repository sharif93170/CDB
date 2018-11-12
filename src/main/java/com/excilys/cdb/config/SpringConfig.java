package com.excilys.cdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ ServiceConfig.class, DataSourceConfig.class })

public class SpringConfig {

}