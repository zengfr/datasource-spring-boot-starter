package com.github.zengfr.project.data.datasource.config;

public class Db {
	public String  name;
	public DbCategory dbType;
	public Integer shardId;
	
	public String url;
	public String userName;
	public String pwd;
	public Db(String name,DbCategory dbType, Integer shardId) {
		super();
		this.name = name;
		this.dbType=  dbType;
		this.shardId = shardId;
	}
	public Db(String name,DbCategory dbType, Integer shardId, String url, String userName, String pwd) {
		super();
		this.name = name;
		this.dbType=  dbType;
		this.shardId = shardId;
		this.url = url;
		this.userName = userName;
		this.pwd = pwd;
	}
	
}
