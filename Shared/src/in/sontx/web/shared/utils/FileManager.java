package in.sontx.web.shared.utils;

import java.io.File;
import java.io.IOException;

public final class FileManager {
	private File root;
	
	public File createDirectory(String dir) {
		File file = new File(Path.combine(root.getPath(), dir));
		return file.isDirectory() ? file : (file.mkdirs() ? file : null);
	}

	public FileManager(String root) throws IOException {
		this.root = new File(root);
		if (!this.root.isDirectory()) {
			if (!this.root.mkdirs())
				throw new IOException("Can not create root directory!");
		}
	}
}
