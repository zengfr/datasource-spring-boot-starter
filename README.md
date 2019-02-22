# datasource-spring-boot-starter
datasource-spring-boot-starter. AbstractRoutingDataSource dynamic datasource for springboot,Multiple &Routing&Sharding&RW&Master/Slave
https://github.com/zengfr/datasource-spring-boot-starter/

[Repositories Central Sonatype Mvnrepository](https://mvnrepository.com/search?q=com.github.zengfr.project)
```
Maven Dependency:
Not Use Spring Boot:
<dependency>
 <groupId>com.github.zengfr.project</groupId>
 <artifactId>datasource</artifactId>
 <version>0.0.1</version>
<dependency>
Use Spring Boot :
<dependency>
 <groupId>com.github.zengfr.project</groupId>
 <artifactId>datasource-spring-boot-starter</artifactId>
 <version>0.0.1</version>
<dependency>
Gradle:
compile group: 'com.github.zengfr.project', name: 'datasource', version: '0.0.1'
compile group: 'com.github.zengfr.project', name: 'datasource-spring-boot-starter', version: '0.0.1'
```
[javadoc api](https://oss.sonatype.org/service/local/repositories/releases/archive/com/github/zengfr/project/datasource/0.0.1/datasource-0.0.1-javadoc.jar/!/index.html)
```dbconfig.xml
<dbconfig>
	<dbsets>
		<dbset name="productdb" provider="SqlServer">
			<db name="productdbm" dbtype="Master" shardid="0" />
			<db name="productdbs" dbtype="Slave" shardid="0" />
		</dbset>
		<dbset name="orderdb" provider="MySql" shardstrategy="">
			<db name="orderdb_S0_W" dbtype="Master" shardid="0" />
			<db name="orderdb_S1_W" dbtype="Master" shardid="1" />
			<db name="orderdb_S2_W" dbtype="Master" shardid="2" />
			<db name="orderdb_S3_W" dbtype="Master" shardid="3" />
			<db name="orderdb00_R" dbtype="Slave" shardid="0" />
			<db name="orderdb01_R" dbtype="Slave" shardid="1" />
			<db name="orderdb02_R" dbtype="Slave" shardid="2" />
			<db name="orderdb03_R" dbtype="Slave" shardid="3" />
			<db name="orderdb03_R1" dbtype="Slave" shardid="3" />
			<db name="orderdb03_R2" dbtype="Slave" shardid="3" />
		</dbset>
	</dbsets>
</dbconfig> 
```
