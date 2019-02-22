package com.github.zengfr.project.data.datasource.configuration;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.github.zengfr.project.data.datasource.DynamicRoutingDataSource;
import com.github.zengfr.project.data.datasource.factory.DataSourceBuilder;
import com.github.zengfr.project.data.datasource.impl.DataSourceWrapper;
import com.github.zengfr.project.data.datasource.key.KeyStrategy;

@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@ComponentScan("com.github.zengfr.project.data.datasource")
public class DynamicDataSourceAutoConfiguration {
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceProperties.class);
	@Autowired
	private Environment env;
	@Autowired
	private DynamicDataSourceProperties dynamicDataSourceSettings;

	@Bean
	public DynamicRoutingDataSource dynamicRoutingDataSource(KeyStrategy keyStrategy) throws Exception {
		Map<String, DataSourceWrapper> dataSourceMap = getDataSourcesMap();
		DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource(keyStrategy, dataSourceMap);
		dynamicRoutingDataSource.setDefaultTargetDataSource(dataSourceMap.values().iterator().next());
		return dynamicRoutingDataSource;
	}

	public Map<String, DataSourceWrapper> getDataSourcesMap() throws Exception {
		String configServiceUrl = dynamicDataSourceSettings.configServiceUrl;
		String connServiceUrl = dynamicDataSourceSettings.connServiceUrl;
		Map<String, DataSourceWrapper> dataSources = DataSourceBuilder.buildDataSourcesMap(configServiceUrl,
				connServiceUrl, true);
		return dataSources;
	}
}
