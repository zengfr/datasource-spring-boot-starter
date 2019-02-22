package com.github.zengfr.project.data.datasource.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
/**
 * zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 * https://github.com/zengfr/stepchain-spring-boot-starter
 * 
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 *         https://github.com/zengfr/stepchain-spring-boot-starter
 */
@EnableAspectJAutoProxy
//@EnableLoadTimeWeaving
@Configuration
@ComponentScan("com.github.zengfr.project.data.datasource")
public class DynamicDataSourceAspectJConfiguration {
	@Bean
	public DynamicDataSourceAspectJAround dynamicDataSourceAspectJAround() {
		return new DynamicDataSourceAspectJAround();
	}
}
