package com.kh.spring.menu.model.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.kh.spring.menu.model.vo.MenuType;

public class MenuTypeHandler extends BaseTypeHandler<MenuType> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, MenuType parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getValue()); // kr
	}

	@Override
	public MenuType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return MenuType.menuTypeValueOf(rs.getString(columnName));
	}

	@Override
	public MenuType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return MenuType.menuTypeValueOf(rs.getString(columnIndex));
	}

	@Override
	public MenuType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return MenuType.menuTypeValueOf(cs.getString(columnIndex));
	}
	
}
