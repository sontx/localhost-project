package in.sontx.web.shared.utils;

import java.io.File;
import java.io.IOException;

public final class FileManager {
	private final File root;
	
	public File createDirectory(String dir) {
		File file = new File(Path.combine(root.getPath(), dir));
		return file.isDirectory() ? file : (file.mkdirs() ? file : null);
	}
	
	public File openFile(String...names) {
		String[] array = new String[1 + names.length];
		array[0] = root.getPath();
		System.arraycopy(names, 0, array, 1, names.length);
		return new File(Path.combineByArray(array));
	}
	
	public boolean deleteFile(String name) {
		File file = new File(Path.combine(root.getPath(), name));
		return file.delete();
	}

	public FileManager(String root) throws IOException {
		this.root = new File(root);
		if (!this.root.isDirectory()) {
			if (!this.root.mkdirs())
				throw new IOException("Can not create root directory!");
		}
	}
}
