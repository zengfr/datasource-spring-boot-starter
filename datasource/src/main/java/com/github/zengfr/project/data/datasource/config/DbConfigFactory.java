package com.github.zengfr.project.data.datasource.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.github.zengfr.project.data.datasource.util.DomUtil;
import com.google.common.collect.Sets;

public class DbConfigFactory {
	public static void merge(DbConfig dbConfig1, DbConfig dbConfig2) throws Exception {
		for (Entry<String, DbSet> dbSetEntry : dbConfig2.dbSets.entrySet()) {
			dbConfig1.dbSets.put(dbSetEntry.getKey(), dbSetEntry.getValue());
		}
	}

	public static DbConfig loadByConfigUrl(String configServiceUrl) throws Exception {
		DbConfig dbConfig = null;
		if (StringUtils.isEmpty(configServiceUrl)||configServiceUrl.length()<6) {
			dbConfig = DbConfigFactory.loadByLocal();
		} else if (configServiceUrl.startsWith("http")) {
			dbConfig = DbConfigFactory.loadByURL(new URL(configServiceUrl));
		} else {
			dbConfig = DbConfigFactory.loadByFile(configServiceUrl);
		}
		return dbConfig;
	}

	public static DbConfig loadByLocal() throws Exception {
		URL configUrl = getLocalUrl(DbConfigKeys.DBCONFIGFILE);
		if (configUrl == null)
			throw new IllegalStateException("Can not find " + DbConfigKeys.DBCONFIGFILE + " to initilize DbSets");

		return loadByURL(configUrl);
	}

	private static URL getLocalUrl(String resourceName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
			classLoader = DbConfigFactory.class.getClassLoader();
		URL url = classLoader.getResource(resourceName);
		return url;
	}

	public static DbConfig loadByResource(String resourceName) throws Exception {
		return loadByURL(getLocalUrl(resourceName));
	}

	public static DbConfig loadByURL(URL url) throws Exception {
		return load(url.openStream());
	}

	public static DbConfig loadByFile(String path) throws Exception {
		return loadByFile(new File(path));
	}

	public static DbConfig loadByFile(File file) throws Exception {
		return load(new FileInputStream(file));
	}

	private static DbConfig load(InputStream in) throws Exception {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
			DbConfig def = getFromDocument(doc);
			in.close();
			return def;
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Throwable e1) {

				}
		}
	}

	private static DbConfig getFromDocument(Document doc) throws Exception {
		DbConfig dbConfig = new DbConfig();
		Element root = doc.getDocumentElement();
		Map<String, DbSet> dbSetMap = readDbSetMap(DomUtil.getChildNode(root, DbConfigKeys.DBSETS));
		dbConfig.dbSets = dbSetMap;

		return dbConfig;
	}

	private static Map<String, DbSet> readDbSetMap(Node dbSetsNode) throws Exception {
		List<Node> dbSetNodes = DomUtil.getChildNodes(dbSetsNode, DbConfigKeys.DBSET);
		Map<String, DbSet> ddSetMap = new HashMap<>();
		for (int i = 0; i < dbSetNodes.size(); i++) {
			DbSet dbSet = readDbSet(dbSetNodes.get(i));
			ddSetMap.put(dbSet.name, dbSet);
		}
		return ddSetMap;
	}

	private static void checkAttribte(Node node, String... validNames) {
		NamedNodeMap map = node.getAttributes();
		if (map == null)
			return;

		for (int i = 0; i < map.getLength(); i++) {
			String name = map.item(i).getNodeName();
			boolean found = false;
			for (String candidate : validNames)
				if (name.equals(candidate)) {
					found = true;
					break;
				}

			if (!found)
				throw new IllegalStateException("checkAttribte:" + name);
		}
	}

	private static Db readDb(Node dbNode) {
		checkAttribte(dbNode, DbConfigKeys.NAME, DbConfigKeys.DBTYPE, DbConfigKeys.SHARDID, DbConfigKeys.URL, DbConfigKeys.USERNAME, DbConfigKeys.PWD);
		String name = DomUtil.getAttribute(dbNode, DbConfigKeys.NAME);
		String dbType = DomUtil.getAttribute(dbNode, DbConfigKeys.DBTYPE);
		String shardId = DomUtil.getAttribute(dbNode, DbConfigKeys.SHARDID);
		String url = DomUtil.getAttribute(dbNode, DbConfigKeys.URL);
		String userName = DomUtil.getAttribute(dbNode, DbConfigKeys.USERNAME);
		String password = DomUtil.getAttribute(dbNode, DbConfigKeys.PWD);
		shardId = shardId == null ? "-1" : shardId;
		Db db = new Db(name, DbCategory.valueOf(dbType), Integer.valueOf(shardId), url, userName, password);

		return db;
	}

	private static DbSet readDbSet(Node dbSetNode) throws Exception {
		checkAttribte(dbSetNode, DbConfigKeys.NAME, DbConfigKeys.PROVIDER, DbConfigKeys.DRIVERCLASS, DbConfigKeys.SHARDSTRATEGY);
		String shardingStrategy = "";

		if (DomUtil.hasAttribute(dbSetNode, DbConfigKeys.SHARDSTRATEGY))
			shardingStrategy = DomUtil.getAttribute(dbSetNode, DbConfigKeys.SHARDSTRATEGY);

		shardingStrategy = shardingStrategy.trim();

		List<Node> dbList = DomUtil.getChildNodes(dbSetNode, DbConfigKeys.DB);
		Map<Integer, Db> dbs1 = new HashMap<>();
		Map<Integer, Set<Db>> dbs2 = new HashMap<>();
		for (int i = 0; i < dbList.size(); i++) {
			Db db = readDb(dbList.get(i));
			if (db.dbType != null) {
				switch (db.dbType) {
				case Master:
					dbs1.put(db.shardId, db);
					break;
				case Slave:
					if (!dbs2.containsKey(db.shardId)) {
						dbs2.put(db.shardId, Sets.newHashSet());
					}
					Set<Db> dbs = dbs2.get(db.shardId);
					dbs.add(db);
					break;
				default:
					break;
				}
			}
		}
		DbSet dbSet = new DbSet();
		dbSet.name = DomUtil.getAttribute(dbSetNode, DbConfigKeys.NAME);
		dbSet.provider = DomUtil.getAttribute(dbSetNode, DbConfigKeys.PROVIDER);
		dbSet.driverClass = DomUtil.getAttribute(dbSetNode, DbConfigKeys.DRIVERCLASS);
		dbSet.masters = dbs1;
		dbSet.slaves = dbs2;
		return dbSet;
	}
}
