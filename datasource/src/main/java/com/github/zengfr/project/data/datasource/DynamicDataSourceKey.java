package com.github.zengfr.project.data.datasource;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.zengfr.project.data.datasource.config.DbCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
/**
 * zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 * https://github.com/zengfr/stepchain-spring-boot-starter
 * 
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 *         https://github.com/zengfr/stepchain-spring-boot-starter
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicDataSourceKey {
	String value() default "";
	DbCategory dbType() default DbCategory.Master;
	String shardIdEls()  default "";
	int shardId() default 0;
}
