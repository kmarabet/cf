package com.cf.config;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages= "com.cf", excludeFilters={ @ComponentScan.Filter(Configuration.class)} )
@ImportResource(value = "classpath*:META-INF/spring/security-beans.xml")
public class ComponentConfig {
}
