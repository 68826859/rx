package com.rx.pub.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;


@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(value = String[].class)
public class ArrayTypeHandler extends BaseTypeHandler<String[]> {

	public ArrayTypeHandler() {
		super();
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType)
			throws SQLException {
		if(parameter != null && parameter.length > 0) {
			ps.setString(i,String.join(",", parameter));
		}
		
	}
	
	@Override
	public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String res = rs.getString(columnName);
		if(res != null) {
			return res.split(",");
		}
		return null;
	}
	
	@Override
	public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String res = rs.getString(columnIndex);
		if(res != null) {
			return res.split(",");
		}
		return null;
	}
	
	@Override
	public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String res = cs.getString(columnIndex);
		if(res != null) {
			return res.split(",");
		}
		return null;
	}

  

}
