package in.sontx.web.local.bo;

import com.sun.istack.internal.NotNull;

import in.sontx.web.local.bean.StorageEx;
import in.sontx.web.local.dao.FileDb;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.utils.Security;

public final class GuestFileMgr {
	private final FileDb fileDb;
	
	public GuestFileMgr(@NotNull ISQLConnection connection) {
		this.fileDb = new FileDb(connection);
	}
	
	public StorageEx getFile(String id) {
		if (!Security.isValidUUID(id))
			return null;
		StorageEx storage = fileDb.getFile(id);
		return storage != null ? (storage.sharealbe() ? storage : null) : null;
	}
}
