package com.github.zengfr.project.data.datasource.impl;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.github.zengfr.project.data.datasource.config.Db;
import com.github.zengfr.project.data.datasource.config.DbSet;
/**
 * zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 * https://github.com/zengfr/stepchain-spring-boot-starter
 * 
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 *         https://github.com/zengfr/stepchain-spring-boot-starter
 */
public class DataSourceWrapper implements DataSource {
	protected DataSource dataSource;
	protected DbSet dbSet;
	protected Db db;

	public DbSet getDbSet() {
		return this.dbSet;
	}

	public Db getDb() {
		return this.db;
	}

	public DataSourceWrapper(DbSet dbSet, Db db, DataSource dataSource) {
		this.dbSet = dbSet;
		this.db = db;
		this.dataSource = dataSource;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {

		return this.dataSource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.dataSource.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		this.dataSource.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return this.dataSource.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return this.dataSource.getParentLogger();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.dataSource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.dataSource.isWrapperFor(iface);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return this.dataSource.getConnection(username, password);
	}

}
