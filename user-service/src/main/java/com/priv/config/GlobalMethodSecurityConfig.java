package com.priv.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author fenghen
 * Spring Security注解
 * @EnableGlobalMethodSecurity(prePostEnabled = true) 开启基于方法的安全认证机制，也就是说在web层的controller启用注解机制的安全确认
 * prePostEnabled
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalMethodSecurityConfig extends WebSecurityConfigurerAdapter {
}
