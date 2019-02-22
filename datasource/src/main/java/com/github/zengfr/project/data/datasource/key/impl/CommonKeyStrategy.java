package com.github.zengfr.project.data.datasource.key.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.zengfr.project.data.datasource.impl.DataSourceWrapper;
import com.github.zengfr.project.data.datasource.key.KeyStrategy;
import com.github.zengfr.project.data.datasource.util.KeyUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
public class CommonKeyStrategy implements KeyStrategy {
	private static Logger logger = LoggerFactory.getLogger(CommonKeyStrategy.class);
	private Map<String, List<String>> nameMap = Maps.newHashMap();
	private Map<String, Integer> indexMap = Maps.newHashMap();

	@Override
	public void init(Map<String, DataSourceWrapper> dataSourcesMap) {
		for (Entry<String, DataSourceWrapper> dataSourceEntry : dataSourcesMap.entrySet()) {
			DataSourceWrapper dataSource = dataSourceEntry.getValue();
			String keyStr = KeyUtil.buildKey(dataSource.getDbSet(), dataSource.getDb());
			if (!nameMap.containsKey(keyStr)) {
				nameMap.put(keyStr, Lists.newArrayList());
			}
			nameMap.get(keyStr).add(dataSourceEntry.getKey());
		}
	}

	@Override
	public String getName(String keyStr) {
		String key = keyStr;
		if (keyStr != null && nameMap.containsKey(keyStr)) {
			Integer size = nameMap.get(keyStr).size();
			Integer index = 0;
			if (size > 1) {
				if (!indexMap.containsKey(keyStr)) {
					indexMap.put(keyStr, 0);
				}
				index = indexMap.get(keyStr) + 1;
				if (index >= size)
					index = 0;
				indexMap.put(keyStr, index);
			}
			key = nameMap.get(keyStr).get(index);
		}
		logger.info(String.format("getKey:%s,%s", keyStr, key));
		return key;
	}

}
