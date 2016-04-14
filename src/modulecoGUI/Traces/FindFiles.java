/*
 * FindFiles.java
 * 
 * Created on 19 avril 2002, 15:27
 */
package modulecoGUI.Traces;
/**
 * @author infolab
 * @version
 */
import java.io.File;
import java.io.FilenameFilter;
public class FindFiles {
	File files[];
	int maxFile;
	/** Creates new FindFiles */
	public FindFiles(File pathname) {
		FilenameFilter filenameFilter = new FileFilter(pathname.getName());
		File filesLocation = pathname.getParentFile();
		files = filesLocation.listFiles(filenameFilter);
		maxFile = files.length;
	}
	public int getMaxFile() {
		return maxFile;
	}
	public File[] getFiles() {
		return files;
	}
}