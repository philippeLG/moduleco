/*
 * FileFilter.java
 * 
 * Created on 19 avril 2002, 14:55
 */
package modulecoGUI.Traces;
/**
 * @author infolab
 * @version
 */
import java.io.File;
import java.io.FilenameFilter;
public class FileFilter implements FilenameFilter {
	/** Creates new FileFilter */
	String fileName;
	public FileFilter() {
	}
	public FileFilter(String fileName) {
		this.fileName = fileName;
	}
	public boolean accept(File dir, String name) {
		if (fileName == null)
			fileName = dir.getName();
		if (fileName.lastIndexOf('.') != -1)
			return research(name, fileName.substring(fileName.lastIndexOf('.')));
		else
			return research(name);
	}
	public boolean research(String name, String extension) {
		if (name.lastIndexOf('.') != -1) {
			if (name.substring(name.lastIndexOf('.')).equals(extension)) {
				String filename = fileName.substring(0, fileName
						.lastIndexOf('.'));
				if ((name.substring(0, filename.length()).equals(filename))
						&& (name.substring(filename.length()).startsWith("(")))
					return true;
				if (name.substring(0, name.lastIndexOf('.')).equals(filename))
					return true;
			}
			return false;
		} else
			return false;
	}
	public boolean research(String name) {
		if (name.lastIndexOf('.') != -1)
			return false;
		String filename = fileName.substring(0, fileName.lastIndexOf('.'));
		if ((name.substring(0, filename.length()).equals(filename))
				&& (name.substring(filename.length()).startsWith("(")))
			return true;
		if (name.substring(0, name.lastIndexOf('.')).equals(filename))
			return true;
		return false;
	}
}