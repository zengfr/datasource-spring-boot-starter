package com.github.zengfr.project.data.datasource.factory.impl;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.github.zengfr.project.data.datasource.factory.DataSourceFactory;
/**
 * zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 * https://github.com/zengfr/stepchain-spring-boot-starter
 * 
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 *         https://github.com/zengfr/stepchain-spring-boot-starter
 */
public class DataSourceFactoryImpl implements DataSourceFactory {

	@Override
	public DataSource create(String provider, String name, String connServiceUrl) {
		DataSource ds = null;

		return ds;
	}

	@Override
	public DataSource create(String provider, String name, String driverClass, String url, String userName,
			String pwd) {
		DataSource ds = null;
		if (StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(driverClass)) {
			createDataSourceViaDriverManager(driverClass, url, userName, pwd);
		}
		return ds;
	}

	private static DataSource createDataSourceViaDriverManager(String driverClass, String url, String userName,
			String pwd) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(pwd);
		return dataSource;
	}

}
