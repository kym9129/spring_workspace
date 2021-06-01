package com.kh.spring.common.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;


/**
 * String[]  -------> varchar2
 * 			setString
 * 			getString *3
 * 
 */
@MappedTypes(String[].class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType)
			throws SQLException {

		//i = index
		ps.setString(i, String.join(", ", parameter));
	}
	
	/**
	 * String[] <----- varchar2
	 */
	@Override
	public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		//resultSet에서 값을 받아온 후 쪼개줌
		String str = rs.getString(columnName);
		return str != null ? str.split(", ") : null;
	}

	/**
	 * String[] <----- varchar2
	 */
	@Override
	public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String str = rs.getString(columnIndex);
		return str != null ? str.split(", ") : null;
	}

	/**
	 * String[] <----- varchar2
	 */
	@Override
	public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String str = cs.getString(columnIndex);
		return str != null ? str.split(", ") : null;
	}

}
