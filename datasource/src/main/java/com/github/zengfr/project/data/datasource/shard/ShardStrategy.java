package com.github.zengfr.project.data.datasource.shard;

public interface ShardStrategy {
	ShardCategory getShardCategory(String tabelName);

	String getShardDBName(String tabelName);

	String getShardTableName(String tabelName);

	String getShardTableSeparator();
}
