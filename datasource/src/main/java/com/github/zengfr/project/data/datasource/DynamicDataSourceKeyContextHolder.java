package com.github.zengfr.project.data.datasource;

import com.github.zengfr.project.data.datasource.config.DbCategory;
import com.github.zengfr.project.data.datasource.util.KeyUtil;
/**
 * zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 * https://github.com/zengfr/stepchain-spring-boot-starter
 * 
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 *         https://github.com/zengfr/stepchain-spring-boot-starter
 */
public class DynamicDataSourceKeyContextHolder {
	private static ThreadLocal<String> contextKey = new ThreadLocal<String>();

	public static String getContextKey() {
		String key = contextKey.get();
		return key;
	}

	public static void setContextKey(String name, DbCategory dbType, int shardId) {
		String key = buildKey(name, dbType, shardId);
		contextKey.set(key);
	}

	public static void removeContextKey() {
		contextKey.remove();
	}

	protected static String buildKey(DynamicDataSourceKey key) {
		return KeyUtil.buildKey(key);
	}

	protected static String buildKey(String name, DbCategory dbType, int shardId) {
		return KeyUtil.buildKey(name, dbType, shardId);
	}
}
