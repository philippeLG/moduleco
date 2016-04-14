/**
 * ExistingModels.java
 * Copyright: Copyright (c)enst-bretagne
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 13-May-04
 * @version 1.1, 04-June-04
 */
package modulecoFramework.utils;
import java.io.File;
/**
 * Get the name of the directories in a specified path in a JAR file. Used to
 * initialise the models, when Moduleco is launched.
 *  
 */
public class ExistingModels {
	/**
	 * The list of models
	 */
	String[] models;
	/**
	 * The number of directories
	 */
	int noModels;
	/**
	 * Constructor.
	 */
	public ExistingModels() {
		/**
		 * Initialise the path to the directory containing the models. <br>
		 * e.g. path = xxx/modulecoMK/models
		 */
		String path = System.getProperty("user.dir") + File.separator
				+ "models";
		File filesLocation = new File(path);
		models = filesLocation.list();
		noModels = models.length;
	}
	/**
	 * Check if a given model name exists in the list of available models.
	 * 
	 * @param modelName
	 * @return true if the model exists
	 */
	public boolean exists(String modelName) {
		for (int i = 0; i < noModels; i++)
			if (modelName.compareTo(models[i]) == 0)
				return true;
		return false;
	}
	/**
	 * @return the number of directories
	 */
	public int getNoModels() {
		return noModels;
	}
	/**
	 * @param i
	 * @return the name of a directory
	 */
	public String getName(int i) {
		return (String) models[i];
	}
	/**
	 * toString()
	 */
	public String toString() {
		String s = noModels + " models read:\n";
		for (int i = 0; i < noModels; i++)
			s += models[i] + "\n";
		return s;
	}
}