package com.github.zengfr.project.data.datasource.factory;

import javax.sql.DataSource;
/**
 * zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 * https://github.com/zengfr/stepchain-spring-boot-starter
 * 
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 *         https://github.com/zengfr/stepchain-spring-boot-starter
 */
public interface DataSourceFactory {
	DataSource create(String provider, String name,String connServiceUrl);

	DataSource create(String provider, String name, String driverClass, String url, String userName, String pwd);
}
