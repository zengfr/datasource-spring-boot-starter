package com.github.zengfr.project.data.datasource.util;

import org.springframework.stereotype.Component;

import com.github.zengfr.project.data.datasource.DynamicDataSourceKey;
import com.github.zengfr.project.data.datasource.config.Db;
import com.github.zengfr.project.data.datasource.config.DbCategory;
import com.github.zengfr.project.data.datasource.config.DbSet;

@Component
public class KeyUtil {
	public static String buildKey(String name, DbCategory dbType, int shardId) {
		return String.format("%s_%s_%s", name, dbType, shardId);
	}

	public static String buildKey(DynamicDataSourceKey key) {
		return buildKey(key.value(), key.dbType(), key.shardId());
	}

	public static String buildKey(DbSet dbSet, Db db) {
		return buildKey(dbSet.name, db.dbType,  db.shardId);
	}
}
