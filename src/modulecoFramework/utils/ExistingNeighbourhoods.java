/**
 * ExistingNeighbourhoods.java
 * Copyright: Copyright (c)enst-bretagne
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 04-June-04
 */
package modulecoFramework.utils;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
/**
 * Get the name of the existing neighbourhoods in the JAR file, in
 * modulecoFramework.modeleco.zone
 */
public class ExistingNeighbourhoods {
	/**
	 * The ArrayList containing the name of the zone classes
	 */
	ArrayList directory;
	/**
	 * The number of zone classes
	 */
	int noNeighbourhoods;
	/**
	 * Constructor.
	 */
public ExistingNeighbourhoods() {
		/**
		 * Initialise the name of the jar file containing the directories. <br>
		 * e.g. jarFileName = xxx/modulecoMK/lib/moduleco.jar
		 */
		String jarFileName = System.getProperty("user.dir") + File.separator
				+ "lib" + File.separator + "moduleco.jar";
		directory = new ArrayList();
		/**
		 * Open the JAR file and get the entries. The structure of a compressed
		 * JAR is flat, with entries having names such as
		 * "modulecoFramework/modeleco/zone/Empty.class", etc. As a consequence,
		 * all the entries represent a file.
		 */
		try {
			ZipFile jar = new ZipFile(jarFileName);
			Enumeration e = jar.entries();
			while (e.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) e.nextElement();
				directory.add(ze.getName());
			}
			/**
			 * Get the directories from this list of entries.
			 */
			ArrayList newDirectory = new ArrayList();
			for (int i = 0; i < directory.size(); i++) {
				String tmpModel = (String) directory.get(i);
				/**
				 * Filter the entries that contain both the pathname and
				 * "World.class". There should be only one entry per model.
				 */
				if ((tmpModel.indexOf("modulecoFramework/modeleco/zone/") != -1)
						&& (tmpModel.indexOf(".class") != -1)) {
					/**
					 * Get the name of the zone. <br>
					 * e.g. "modulecoFramework/modeleco/zone/Empty.class" -->
					 * "Empty"
					 */
					//System.out
					//		.println(tmpModel + " is a directory to consider");
					String[] chunks = tmpModel.split("/");
					/**
					 * e.g. zone = Empty.java
					 */
					String zone = chunks[chunks.length - 1];
					/**
					 * Add the neighbourhood name. <br>
					 * e.g. Empty
					 */
					newDirectory.add(zone.substring(0, zone.length()-6));
				}
			}
			directory = newDirectory;
			/**
			 * Get the final number of models
			 */
			noNeighbourhoods = directory.size();
		} catch (NullPointerException e) {
			System.out.println("done." + e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	/**
		  * @return the number of zones
		  */
	public int getNoNeighbourhoods() {
		return noNeighbourhoods;
	}
	/**
	 * @param i
	 * @return the name of a directory
	 */
	public String getName(int i) {
		return (String) directory.get(i);
	}
	/**
	 * toString()
	 */
	public String toString() {
		String s = noNeighbourhoods + " neighbourhoods read:\n";
		for (int i = 0; i < directory.size(); i++)
			s += directory.get(i) + "\n";
		return s;
	}
}