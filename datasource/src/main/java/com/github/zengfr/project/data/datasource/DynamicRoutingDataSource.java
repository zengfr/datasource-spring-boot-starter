package com.github.zengfr.project.data.datasource;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.github.zengfr.project.data.datasource.impl.DataSourceWrapper;
import com.github.zengfr.project.data.datasource.key.KeyStrategy;
import com.google.common.collect.Maps;

/**
 * 
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

	private KeyStrategy keyStrategy;

	public DynamicRoutingDataSource(KeyStrategy keyStrategy, Map<String, DataSourceWrapper> targetDataSources) {
		this.keyStrategy = keyStrategy;
		this.keyStrategy.init(targetDataSources);
		super.setTargetDataSources(getDataSourcesObjectMap(targetDataSources));
	}

	@Override
	protected Object determineCurrentLookupKey() {
		String keyStr = DynamicDataSourceKeyContextHolder.getContextKey();
		return keyStrategy.getName(keyStr);
	}

	protected Map<Object, Object> getDataSourcesObjectMap(Map<String, DataSourceWrapper> dataSourcesMap) {
		Map<Object, Object> dataSourcesObjectMap = Maps.newHashMap();

		for (Entry<String, DataSourceWrapper> element : dataSourcesMap.entrySet()) {
			dataSourcesObjectMap.put(element.getKey(), element.getValue());
		}
		return dataSourcesObjectMap;
	}
}