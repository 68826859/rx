package com.rx.pub.mybatis.attribute;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public class ColumnAttributeProvider extends MapperTemplate{

	public ColumnAttributeProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	public String getRxAttribute(MappedStatement ms) {
		return null;
	}

	public String getRxAttributes(MappedStatement ms) {
		return null;
	}

	public String setRxAttribute(MappedStatement ms) {
		
		/*
		Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, null, true, isNotEmpty()));
        sql.append(SqlHelper.wherePKColumns(entityClass, true));
        return sql.toString();
        */
		return null;
	}

	public String setRxAttributes(MappedStatement ms) {
		return null;
	}

}
