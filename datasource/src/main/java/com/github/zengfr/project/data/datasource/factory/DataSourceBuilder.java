package com.github.zengfr.project.data.datasource.factory;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.github.zengfr.project.data.datasource.config.Db;
import com.github.zengfr.project.data.datasource.config.DbConfig;
import com.github.zengfr.project.data.datasource.config.DbConfigFactory;
import com.github.zengfr.project.data.datasource.config.DbConfigKeys;
import com.github.zengfr.project.data.datasource.config.DbProvider;
import com.github.zengfr.project.data.datasource.config.DbSet;
import com.github.zengfr.project.data.datasource.impl.DataSourceWrapper;
import com.google.common.collect.Maps;
/**
 * zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 * https://github.com/zengfr/stepchain-spring-boot-starter
 * 
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 *         https://github.com/zengfr/stepchain-spring-boot-starter
 */
public class DataSourceBuilder {
	private static Logger logger = LoggerFactory.getLogger(DataSourceBuilder.class);

	public static Map<String, DataSourceWrapper> buildDataSourcesMap(String configServiceUrl, String connServiceUrl,
			Boolean isAddTestDB) throws Exception {
		Map<String, DataSourceWrapper> dataSources = Maps.newHashMap();
		DbConfig dbConfig = DbConfigFactory.loadByConfigUrl(configServiceUrl);
		DbConfig dbConfig2 = DbConfigFactory.loadByResource(DbConfigKeys.TESTDBCONFIGFILE);
		if (isAddTestDB) {
			DbConfigFactory.merge(dbConfig, dbConfig2);
		}
		for (Entry<String, DbSet> dbSetEntry : dbConfig.dbSets.entrySet()) {
			DbSet dbSet = dbSetEntry.getValue();
			for (Entry<Integer, Db> dbEntry : dbSet.masters.entrySet()) {
				Db db = dbEntry.getValue();
				DataSourceWrapper ds = createDataSource(dbSet, db, connServiceUrl);
				if (ds != null) {
					dataSources.put(db.name, ds);
				}
			}
			for (Entry<Integer, Set<Db>> dbEntry : dbSet.slaves.entrySet()) {
				Set<Db> dbs = dbEntry.getValue();
				for (Db db : dbs) {
					DataSourceWrapper ds = createDataSource(dbSet, db, connServiceUrl);
					if (ds != null) {
						dataSources.put(db.name, ds);
					}
				}
			}
		}
		return dataSources;
	}

	private static DataSourceWrapper createDataSource(DbSet dbSet, Db db, String connServiceUrl) {
		DataSource ds = null;
		logger.info(String.format("createDataSource:%s,%s,%s,%s,%s,%s,%s", dbSet.name, dbSet.provider,
				dbSet.driverClass, db.dbType, db.name, db.shardId, connServiceUrl));
		DbProvider dbProvider = DbProvider.valueOf(dbSet.provider);
		switch (dbProvider) {
		case H2:
			ds = createDataSource4EmbeddedEmbedded(dbSet.provider);
			break;
		case HSQL:
			ds = createDataSource4EmbeddedEmbedded(dbSet.provider);
			break;
		case Derby:
			ds = createDataSource4EmbeddedEmbedded(dbSet.provider);
			break;
		default:
			ds = createDataSourceViaServiceLoader(dbSet, db, connServiceUrl);
			break;
		}

		return new DataSourceWrapper(dbSet, db, ds);

	}

	private static DataSource createDataSource4EmbeddedEmbedded(String embeddedDatabaseTypeName) {
		EmbeddedDatabaseType embeddedDatabaseType = EmbeddedDatabaseType.valueOf(embeddedDatabaseTypeName);
		return new EmbeddedDatabaseBuilder().setType(embeddedDatabaseType).build();
	}

	private static DataSource createDataSourceViaServiceLoader(DbSet dbSet, Db db, String connServiceUrl) {
		ServiceLoader<DataSourceFactory> serviceLoader = ServiceLoader.load(DataSourceFactory.class);
		Iterator<DataSourceFactory> it = serviceLoader.iterator();
		DataSource ds = null;
		while (it.hasNext()) {
			DataSourceFactory service = it.next();
			ds = service.create(dbSet.provider, db.name, dbSet.driverClass, db.url, db.userName, db.pwd);
			if (ds != null) {
				return ds;
			}
			ds = service.create(dbSet.provider, db.name, connServiceUrl);
			if (ds != null) {
				return ds;
			}
		}
		return ds;
	}

}
