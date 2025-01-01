/*
 * Copyright (c) 2013-2025 Hutool Team and hutool.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.hutool.db.sql;

import org.dromara.hutool.core.array.ArrayUtil;
import org.dromara.hutool.core.collection.iter.ArrayIter;
import org.dromara.hutool.core.lang.wrapper.SimpleWrapper;
import org.dromara.hutool.core.lang.Assert;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

/**
 * {@link PreparedStatement} 包装类，用于添加拦截方法功能<br>
 * 拦截方法包括：
 *
 * <pre>
 * 1. 提供参数注入
 * 2. 提供SQL打印日志拦截
 * </pre>
 *
 * @author Looly
 * @since 4.1.0
 */
public class StatementWrapper extends SimpleWrapper<PreparedStatement> implements PreparedStatement {

	/**
	 * 构建StatementWrapper
	 *
	 * @param raw {@link PreparedStatement}
	 * @return StatementWrapper
	 */
	public static StatementWrapper of(final PreparedStatement raw) {
		return raw instanceof StatementWrapper ? (StatementWrapper) raw :
			new StatementWrapper(raw);
	}

	/**
	 * 构造
	 *
	 * @param raw {@link PreparedStatement}
	 */
	public StatementWrapper(final PreparedStatement raw) {
		super(raw);
	}

	// region ----- override methods
	@Override
	public ResultSet executeQuery(final String sql) throws SQLException {
		return this.raw.executeQuery(sql);
	}

	@Override
	public int executeUpdate(final String sql) throws SQLException {
		return this.raw.executeUpdate(sql);
	}

	@Override
	public void close() throws SQLException {
		this.raw.close();
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		return this.raw.getMaxFieldSize();
	}

	@Override
	public void setMaxFieldSize(final int max) throws SQLException {
		this.raw.setMaxFieldSize(max);
	}

	@Override
	public int getMaxRows() throws SQLException {
		return this.raw.getMaxRows();
	}

	@Override
	public void setMaxRows(final int max) throws SQLException {
		this.raw.setMaxRows(max);
	}

	@Override
	public void setEscapeProcessing(final boolean enable) throws SQLException {
		this.raw.setEscapeProcessing(enable);
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return this.raw.getQueryTimeout();
	}

	@Override
	public void setQueryTimeout(final int seconds) throws SQLException {
		this.raw.setQueryTimeout(seconds);
	}

	@Override
	public void cancel() throws SQLException {
		this.raw.cancel();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return this.raw.getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		this.raw.clearWarnings();
	}

	@Override
	public void setCursorName(final String name) throws SQLException {
		this.raw.setCursorName(name);
	}

	@Override
	public boolean execute(final String sql) throws SQLException {
		return this.raw.execute(sql);
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return this.raw.getResultSet();
	}

	@Override
	public int getUpdateCount() throws SQLException {
		return this.raw.getUpdateCount();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		return this.raw.getMoreResults();
	}

	@Override
	public void setFetchDirection(final int direction) throws SQLException {
		this.raw.setFetchDirection(direction);
	}

	@Override
	public int getFetchDirection() throws SQLException {
		return this.raw.getFetchDirection();
	}

	@Override
	public void setFetchSize(final int rows) throws SQLException {
		this.raw.setFetchSize(rows);
	}

	@Override
	public int getFetchSize() throws SQLException {
		return this.raw.getFetchSize();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		return this.raw.getResultSetConcurrency();
	}

	@Override
	public int getResultSetType() throws SQLException {
		return this.raw.getResultSetType();
	}

	@Override
	public void addBatch(final String sql) throws SQLException {
		this.raw.addBatch(sql);
	}

	@Override
	public void clearBatch() throws SQLException {
		this.raw.clearBatch();
	}

	@Override
	public int[] executeBatch() throws SQLException {
		return this.raw.executeBatch();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return this.raw.getConnection();
	}

	@Override
	public boolean getMoreResults(final int current) throws SQLException {
		return this.raw.getMoreResults(current);
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		return this.raw.getGeneratedKeys();
	}

	@Override
	public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
		return this.raw.executeUpdate(sql, autoGeneratedKeys);
	}

	@Override
	public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
		return this.raw.executeUpdate(sql, columnIndexes);
	}

	@Override
	public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
		return this.raw.executeUpdate(sql, columnNames);
	}

	@Override
	public boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException {
		return this.raw.execute(sql, autoGeneratedKeys);
	}

	@Override
	public boolean execute(final String sql, final int[] columnIndexes) throws SQLException {
		return this.raw.execute(sql, columnIndexes);
	}

	@Override
	public boolean execute(final String sql, final String[] columnNames) throws SQLException {
		return this.raw.execute(sql, columnNames);
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return this.raw.getResultSetHoldability();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return this.raw.isClosed();
	}

	@Override
	public void setPoolable(final boolean poolable) throws SQLException {
		this.raw.setPoolable(poolable);
	}

	@Override
	public boolean isPoolable() throws SQLException {
		return this.raw.isPoolable();
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		this.raw.closeOnCompletion();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		return this.raw.isCloseOnCompletion();
	}

	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		return this.raw.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		return this.raw.isWrapperFor(iface);
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		return this.raw.executeQuery();
	}

	@Override
	public int executeUpdate() throws SQLException {
		return this.raw.executeUpdate();
	}

	@Override
	public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
		this.raw.setNull(parameterIndex, sqlType);
	}

	@Override
	public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
		this.raw.setBoolean(parameterIndex, x);
	}

	@Override
	public void setByte(final int parameterIndex, final byte x) throws SQLException {
		this.raw.setByte(parameterIndex, x);
	}

	@Override
	public void setShort(final int parameterIndex, final short x) throws SQLException {
		this.raw.setShort(parameterIndex, x);
	}

	@Override
	public void setInt(final int parameterIndex, final int x) throws SQLException {
		this.raw.setInt(parameterIndex, x);
	}

	@Override
	public void setLong(final int parameterIndex, final long x) throws SQLException {
		this.raw.setLong(parameterIndex, x);
	}

	@Override
	public void setFloat(final int parameterIndex, final float x) throws SQLException {
		this.raw.setFloat(parameterIndex, x);
	}

	@Override
	public void setDouble(final int parameterIndex, final double x) throws SQLException {
		this.raw.setDouble(parameterIndex, x);
	}

	@Override
	public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
		this.raw.setBigDecimal(parameterIndex, x);
	}

	@Override
	public void setString(final int parameterIndex, final String x) throws SQLException {
		this.raw.setString(parameterIndex, x);
	}

	@Override
	public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
		this.raw.setBytes(parameterIndex, x);
	}

	@Override
	public void setDate(final int parameterIndex, final Date x) throws SQLException {
		this.raw.setDate(parameterIndex, x);
	}

	@Override
	public void setTime(final int parameterIndex, final Time x) throws SQLException {
		this.raw.setTime(parameterIndex, x);
	}

	@Override
	public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
		this.raw.setTimestamp(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		this.raw.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	@Deprecated
	public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		this.raw.setUnicodeStream(parameterIndex, x, length);
	}

	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		this.raw.setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public void clearParameters() throws SQLException {
		this.raw.clearParameters();
	}

	@Override
	public void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException {
		this.raw.setObject(parameterIndex, x, targetSqlType, targetSqlType);
	}

	@Override
	public void setObject(final int parameterIndex, final Object x) throws SQLException {
		this.raw.setObject(parameterIndex, x);
	}

	@Override
	public boolean execute() throws SQLException {
		return this.raw.execute();
	}

	@Override
	public void addBatch() throws SQLException {
		this.raw.addBatch();
	}

	@Override
	public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException {
		this.raw.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setRef(final int parameterIndex, final Ref x) throws SQLException {
		this.raw.setRef(parameterIndex, x);
	}

	@Override
	public void setBlob(final int parameterIndex, final Blob x) throws SQLException {
		this.raw.setBlob(parameterIndex, x);
	}

	@Override
	public void setClob(final int parameterIndex, final Clob x) throws SQLException {
		this.raw.setClob(parameterIndex, x);
	}

	@Override
	public void setArray(final int parameterIndex, final Array x) throws SQLException {
		this.raw.setArray(parameterIndex, x);
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return this.raw.getMetaData();
	}

	@Override
	public void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException {
		this.raw.setDate(parameterIndex, x, cal);
	}

	@Override
	public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
		this.raw.setTime(parameterIndex, x, cal);
	}

	@Override
	public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
		this.raw.setTimestamp(parameterIndex, x, cal);
	}

	@Override
	public void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
		this.raw.setNull(parameterIndex, sqlType, typeName);
	}

	@Override
	public void setURL(final int parameterIndex, final URL x) throws SQLException {
		this.raw.setURL(parameterIndex, x);
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return this.raw.getParameterMetaData();
	}

	@Override
	public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
		this.raw.setRowId(parameterIndex, x);
	}

	@Override
	public void setNString(final int parameterIndex, final String value) throws SQLException {
		this.raw.setNString(parameterIndex, value);
	}

	@Override
	public void setNCharacterStream(final int parameterIndex, final Reader value, final long length) throws SQLException {
		this.raw.setCharacterStream(parameterIndex, value, length);
	}

	@Override
	public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
		this.raw.setNClob(parameterIndex, value);
	}

	@Override
	public void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
		this.raw.setClob(parameterIndex, reader, length);
	}

	@Override
	public void setBlob(final int parameterIndex, final InputStream inputStream, final long length) throws SQLException {
		this.raw.setBlob(parameterIndex, inputStream, length);
	}

	@Override
	public void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
		this.raw.setNClob(parameterIndex, reader, length);
	}

	@Override
	public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
		this.raw.setSQLXML(parameterIndex, xmlObject);
	}

	@Override
	public void setObject(final int parameterIndex, final Object x, final int targetSqlType, final int scaleOrLength) throws SQLException {
		this.raw.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}

	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
		this.raw.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
		this.raw.setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public void setCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException {
		this.raw.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException {
		this.raw.setAsciiStream(parameterIndex, x);
	}

	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException {
		this.raw.setBinaryStream(parameterIndex, x);
	}

	@Override
	public void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException {
		this.raw.setCharacterStream(parameterIndex, reader);
	}

	@Override
	public void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException {
		this.raw.setNCharacterStream(parameterIndex, value);
	}

	@Override
	public void setClob(final int parameterIndex, final Reader reader) throws SQLException {
		this.raw.setClob(parameterIndex, reader);
	}

	@Override
	public void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException {
		this.raw.setBlob(parameterIndex, inputStream);
	}

	@Override
	public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
		this.raw.setNClob(parameterIndex, reader);
	}

	// endregion

	// region ----- setParam

	/**
	 * 填充数组类型的SQL的参数。
	 *
	 * @param params SQL参数
	 * @return this
	 * @throws SQLException SQL执行异常
	 */
	public StatementWrapper fillArrayParam(final Object... params) throws SQLException {
		if (ArrayUtil.isEmpty(params)) {
			return this;
		}
		return fillParams(new ArrayIter<>(params));
	}

	/**
	 * 填充SQL的参数。<br>
	 * 对于日期对象特殊处理：传入java.util.Date默认按照Timestamp处理
	 *
	 * @param params SQL参数
	 * @return this
	 * @throws SQLException SQL执行异常
	 */
	public StatementWrapper fillParams(final Iterable<?> params) throws SQLException {
		return fillParams(params, null);
	}

	/**
	 * 填充SQL的参数。<br>
	 * 对于日期对象特殊处理：传入java.util.Date默认按照Timestamp处理
	 *
	 * @param params        SQL参数
	 * @param nullTypeCache null参数的类型缓存，避免循环中重复获取类型
	 * @return this
	 * @throws SQLException SQL执行异常
	 * @since 4.6.7
	 */
	public StatementWrapper fillParams(final Iterable<?> params, final Map<Integer, Integer> nullTypeCache) throws SQLException {
		if (null == params) {
			return this;// 无参数
		}

		int paramIndex = 1;//第一个参数从1计数
		for (final Object param : params) {
			setParam(paramIndex++, param, nullTypeCache);
		}
		return this;
	}

	/**
	 * 为{@link PreparedStatement} 设置单个参数
	 *
	 * @param paramIndex 参数位置，从1开始
	 * @param param      参数
	 * @throws SQLException SQL异常
	 * @since 4.6.7
	 */
	public void setParam(final int paramIndex, final Object param) throws SQLException {
		setParam(paramIndex, param, null);
	}

	// endregion

	/**
	 * 获取null字段对应位置的数据类型<br>
	 * 有些数据库对于null字段必须指定类型，否则会插入报错，此方法用于获取其类型，如果获取失败，使用默认的{@link Types#VARCHAR}
	 *
	 * @param paramIndex 参数位置，第一位从1开始
	 * @return 数据类型，默认{@link Types#VARCHAR}
	 * @since 4.6.7
	 */
	public int getTypeOfNull(final int paramIndex) {
		Assert.notNull(this.raw, "ps PreparedStatement must be not null in (getTypeOfNull)!");

		int sqlType = Types.VARCHAR;

		final ParameterMetaData pmd;
		try {
			pmd = this.raw.getParameterMetaData();
			sqlType = pmd.getParameterType(paramIndex);
		} catch (final SQLException ignore) {
			// ignore
			// log.warn("Null param of index [{}] type get failed, by: {}", paramIndex, e.getMessage());
		}

		return sqlType;
	}

	/**
	 * 为{@link PreparedStatement} 设置单个参数
	 *
	 * @param paramIndex    参数位置，从1开始
	 * @param param         参数，不能为{@code null}
	 * @param nullTypeCache 用于缓存参数为null位置的类型，避免重复获取
	 * @throws SQLException SQL异常
	 * @since 4.6.7
	 */
	private void setParam(final int paramIndex, final Object param, final Map<Integer, Integer> nullTypeCache) throws SQLException {
		if (null == param) {
			Integer type = (null == nullTypeCache) ? null : nullTypeCache.get(paramIndex);
			if (null == type) {
				type = getTypeOfNull(paramIndex);
				if (null != nullTypeCache) {
					nullTypeCache.put(paramIndex, type);
				}
			}
			this.raw.setNull(paramIndex, type);
		}

		// 日期特殊处理，默认按照时间戳传入，避免毫秒丢失
		if (param instanceof java.util.Date) {
			if (param instanceof java.sql.Date) {
				this.raw.setDate(paramIndex, (java.sql.Date) param);
			} else if (param instanceof java.sql.Time) {
				this.raw.setTime(paramIndex, (java.sql.Time) param);
			} else {
				this.raw.setTimestamp(paramIndex, SqlUtil.toSqlTimestamp((java.util.Date) param));
			}
			return;
		}

		// 针对大数字类型的特殊处理
		if (param instanceof Number) {
			if (param instanceof BigDecimal) {
				// BigDecimal的转换交给JDBC驱动处理
				this.raw.setBigDecimal(paramIndex, (BigDecimal) param);
				return;
			}
			if (param instanceof BigInteger) {
				// BigInteger转为BigDecimal
				this.raw.setBigDecimal(paramIndex, new BigDecimal((BigInteger) param));
				return;
			}
			// 忽略其它数字类型，按照默认类型传入
		}

		//Oracle中stream和Blob不能直接通过setObject转换，单独处理
		//InputStream，解决oracle情况下setObject(inputStream)报错问题，java.sql.SQLException: 无效的列类型
		if(param instanceof InputStream){
			this.raw.setBinaryStream(paramIndex, (InputStream) param);
			return;
		}

		//java.sql.Blob
		if(param instanceof Blob){
			this.raw.setBlob(paramIndex, (Blob) param);
		}

		// 其它参数类型
		this.raw.setObject(paramIndex, param);
	}
}
