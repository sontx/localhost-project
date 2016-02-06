package in.sontx.web.shared.dao;

import java.io.Closeable;
import java.sql.SQLException;

import com.sun.istack.internal.NotNull;

public interface ResultSetAdapter extends Closeable {

	boolean next() throws SQLException;

	String getString(int columnIndex) throws SQLException;

	int getInt(int columnIndex) throws SQLException;

	long getLong(int columnIndex) throws SQLException;

	double getDouble(int columnIndex) throws SQLException;

	String getString(@NotNull String columnLabel) throws SQLException;

	int getInt(@NotNull String columnLabel) throws SQLException;

	long getLong(@NotNull String columnLabel) throws SQLException;

	double getDouble(@NotNull String columnLabel) throws SQLException;

	Object getObject(int columnIndex) throws SQLException;

	Object getObject(@NotNull String columnLabel) throws SQLException;
}
