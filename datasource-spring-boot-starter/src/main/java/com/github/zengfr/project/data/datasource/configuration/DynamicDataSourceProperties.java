package com.github.zengfr.project.data.datasource.configuration;

import com.github.zengfr.project.data.datasource.config.DbConfigKeys;
import com.github.zengfr.project.data.datasource.vote.impl.LoadBalanceVoteStrategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by zengfr on 2018/4/10.
 */
//@Configuration
//@Component
@ConfigurationProperties(prefix="datasource")
public class DynamicDataSourceProperties {
	@Value("${" + DbConfigKeys.CONFIGSERVICEKEY + "}")
	public String configServiceUrl;
	@Value("${" + DbConfigKeys.CONNSERVICEKEY + "}")
	public String connServiceUrl;
	@Value("${" + DbConfigKeys.VOTESTRATEGYKEY + "}")
	public String votestrategy=LoadBalanceVoteStrategy.class.getName();
}
