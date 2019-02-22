package com.github.zengfr.project.data.datasource.key;

import java.util.Map;

import com.github.zengfr.project.data.datasource.impl.DataSourceWrapper;
/**
 * zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 * https://github.com/zengfr/stepchain-spring-boot-starter
 * 
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 *         https://github.com/zengfr/stepchain-spring-boot-starter
 */
public interface KeyStrategy {
	public void init(Map<String, DataSourceWrapper> dbs);

	public String getName(String key);
}
