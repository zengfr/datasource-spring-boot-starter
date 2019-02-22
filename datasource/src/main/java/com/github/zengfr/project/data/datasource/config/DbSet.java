package com.github.zengfr.project.data.datasource.config;

import java.util.Map;
import java.util.Set;

import com.github.zengfr.project.data.datasource.config.Db;
import com.github.zengfr.project.data.datasource.shard.ShardStrategy;
import com.google.common.collect.Maps;

public class DbSet {
	public String name;
	public String provider;
	public String driverClass;
	public ShardStrategy shardingStrategy;
	public Map<Integer,Db> masters=Maps.newHashMap();
	public Map<Integer,Set<Db>> slaves=Maps.newHashMap();
	 
}
