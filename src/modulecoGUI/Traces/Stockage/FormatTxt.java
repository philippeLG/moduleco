/*
 * FormatTxt.java
 * 
 * Created on 28 avril 2002, 13:57
 */
package modulecoGUI.Traces.Stockage;
/**
 * @author infolab
 * @version
 */
import modulecoGUI.Traces.Experience;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Enumeration;
public class FormatTxt implements Stockage {
	/** Creates new FormatTxt */
	protected Experience experience;
	protected char separator;
	public FormatTxt(Experience experience) {
		this.experience = experience;
		separator = ' ';
		printHeader(experience.getHeader());
	}
	public void printHeader(Hashtable header) {
		try {
			File output = new File(experience.getPathname());
			if (!output.getParentFile().isDirectory())
				output.getParentFile().mkdir();
			if (!output.isFile())
				output.createNewFile();
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(
					new FileWriter(experience.getPathname(), true), 4096));
			writeModelParameter(header, printWriter);
			printWriter.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	/** Creates new Stockage */
	public void writeData() {
	}
	public void writeModelParameter(Hashtable header, PrintWriter printWriter) {
		Enumeration enum = header.keys();
		while (enum.hasMoreElements()) {
			String key = (String) enum.nextElement();
			printWriter.print(key + " = ");
			printWriter.print(separator);
			printWriter.print(header.get(key));
			printWriter.print(separator);
			printWriter.println();
		}
	}
}