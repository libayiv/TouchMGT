package com.security.common.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 设置数据源
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

	// 数据源名称线程池
	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();
	
	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceKey.get();
	}
	
	public static void putDataSource(String datasource) {
		dataSourceKey.set(datasource);
	}

	public static String getDataSource() {
		return dataSourceKey.get();
	}

	public static void clear() {
		dataSourceKey.remove();
	}

}
