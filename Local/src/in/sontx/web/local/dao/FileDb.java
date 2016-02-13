package in.sontx.web.local.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.istack.internal.NotNull;

import in.sontx.web.local.bean.Storage;
import in.sontx.web.local.bean.StorageEx;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.dao.ResultSetAdapter;
import in.sontx.web.shared.utils.Log;
import in.sontx.web.shared.utils.SQLHepler;

public final class FileDb {
	private final ISQLConnection connection;
	
	public FileDb(ISQLConnection connection) {
		this.connection = connection;
	}
	
	public boolean saveFile(@NotNull String userId, @NotNull Storage storage) {
		final String sql = String.format(
				"INSERT INTO %s(id, origin_name, raw_name, type, size, created, state, user_id) VALUES(%s, %s, %s, %s, %d, %d, %d, %s)",
				TableInfo.FILE_TABLE_NAME, 
				SQLHepler.prepareString(storage.getId()),
				SQLHepler.prepareString(storage.getOriginName()),
				SQLHepler.prepareString(storage.getRawName()),
				SQLHepler.prepareString(storage.getType()),
				storage.getSize(),
				storage.getCreated(),
				storage.getState(),
				SQLHepler.prepareString(userId));
		try {
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}
	
	public boolean changeFileName(@NotNull String userId, @NotNull String id, @NotNull String newFileName) {
		final String sql = String.format(
				"UPDATE %s SET origin_name=%s WHERE user_id=%s AND id=%s",
				TableInfo.FILE_TABLE_NAME, 
				SQLHepler.prepareString(newFileName),
				SQLHepler.prepareString(userId),
				SQLHepler.prepareString(id));
		try {
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}
	
	public boolean changeFileState(@NotNull String userId, @NotNull String id, byte newState) {
		final String sql = String.format(
				"UPDATE %s SET state=%d WHERE user_id=%s AND id=%s",
				TableInfo.FILE_TABLE_NAME, 
				newState,
				SQLHepler.prepareString(userId),
				SQLHepler.prepareString(id));
		try {
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}
	
	private StorageEx getFileBySQLCommand(String sql, String id) {
		ResultSetAdapter set = connection.executeQuery(sql);
		if (set == null)
			return null;
		try {
			if (set.next()) {
				StorageEx storage = new StorageEx();
				storage.setId(id);
				storage.setOriginName(set.getString("origin_name"));
				storage.setRawName(set.getString("raw_name"));
				storage.setSize(set.getInt("size"));
				storage.setCreated(set.getInt("created"));
				storage.setState((byte) set.getInt("state"));
				storage.setOwner(set.getString("user_id"));
				return storage;
			}
		} catch (SQLException e) {
			Log.e(e);
		} finally {
			try {
				set.close();
			} catch (IOException e) {
			}
		}
		return null;
	}
	
	public Storage getFile(@NotNull String userId, @NotNull String id) {
		final String sql = String.format(
				"SELECT origin_name, raw_name, size, created, state, user_id WHERE user_id=%s AND id=%s LIMIT 1", 
				TableInfo.FILE_TABLE_NAME,
				SQLHepler.prepareString(userId),
				SQLHepler.prepareString(id));
		return getFileBySQLCommand(sql, id);
	}
	
	public StorageEx getFile(@NotNull String id) {
		final String sql = String.format(
				"SELECT origin_name, raw_name, size, created, state, user_id WHERE id=%s LIMIT 1", 
				TableInfo.FILE_TABLE_NAME,
				SQLHepler.prepareString(id));
		return getFileBySQLCommand(sql, id);
	}
	
	private List<Storage> getFiles(@NotNull String sql) {
		ResultSetAdapter set = connection.executeQuery(sql);
		if (set == null)
			return null;
		try {
			List<Storage> storages = new ArrayList<>();
			while (set.next()) {
				Storage storage = new Storage();
				storage.setId(set.getString("id"));
				storage.setOriginName(set.getString("origin_name"));
				storage.setRawName(set.getString("raw_name"));
				storage.setSize(set.getInt("size"));
				storage.setCreated(set.getInt("created"));
				storage.setState((byte) set.getInt("state"));
				storages.add(storage);
			}
			return storages;
		} catch (SQLException e) {
			Log.e(e);
		} finally {
			try {
				set.close();
			} catch (IOException e) {
			}
		}
		return null;
	}
	
	public List<Storage> getAllFilesSortByName(@NotNull String userId) {
		return getFiles(String.format(
				"SELECT id, origin_name, raw_name, size, created, state WHERE user_id=%s ORDER BY origin_name ASC", 
				SQLHepler.prepareString(userId)));
	}
	
	public List<Storage> getAllFilesSortBySize(@NotNull String userId) {
		return getFiles(String.format(
				"SELECT id, origin_name, raw_name, size, created, state WHERE user_id=%s ORDER BY size DESC", 
				SQLHepler.prepareString(userId)));
	}
	
	public List<Storage> getAllFilesSortByCreatedTime(@NotNull String userId) {
		return getFiles(String.format(
				"SELECT id, origin_name, raw_name, size, created, state WHERE user_id=%s ORDER BY created DESC", 
				SQLHepler.prepareString(userId)));
	}
	
	public List<Storage> searchFiles(@NotNull String userId, @NotNull String what) {
		return getFiles(String.format(
				"SELECT id, origin_name, raw_name, size, created, state WHERE (user_id=%s) AND (origin_name LIKE %s) ORDER BY origin_name ASC", 
				SQLHepler.prepareString(userId),
				SQLHepler.prepareSearchString(what)));
	}
	
	public List<StorageEx> getFilesSharedEveryone() {
		final String sql = String.format(
				"SELECT id, origin_name, raw_name, size, created, state, username FROM %s JOIN %s ON %s.state=%d AND %s.user_id=%s.id ORDER BY origin_name ASC",
				TableInfo.ACCOUNT_TABLE_NAME,
				TableInfo.FILE_TABLE_NAME,
				TableInfo.FILE_TABLE_NAME,
				Storage.SHARED_STATE_EVERYONE,
				TableInfo.FILE_TABLE_NAME,
				TableInfo.ACCOUNT_INFO_TABLE_NAME);
		ResultSetAdapter set = connection.executeQuery(sql);
		if (set == null)
			return null;
		try {
			List<StorageEx> storages = new ArrayList<>();
			while (set.next()) {
				StorageEx storage = new StorageEx();
				storage.setId(set.getString("id"));
				storage.setOriginName(set.getString("origin_name"));
				storage.setRawName(set.getString("raw_name"));
				storage.setSize(set.getInt("size"));
				storage.setCreated(set.getInt("created"));
				storage.setState((byte) set.getInt("state"));
				storage.setOwner(set.getString("username"));
				storages.add(storage);
			}
			return storages;
		} catch (SQLException e) {
			Log.e(e);
		} finally {
			try {
				set.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	public boolean deleteFile(@NotNull String userId, @NotNull String id) {
		final String sql = String.format(
				"DELETE FROM %s WHERE user_id=%s AND id=%s", 
				TableInfo.FILE_TABLE_NAME,
				SQLHepler.prepareString(userId),
				SQLHepler.prepareString(id));
		try {
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}
}
