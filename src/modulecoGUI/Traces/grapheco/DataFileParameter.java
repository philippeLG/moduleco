/*
 * DataFileParameter.java
 * 
 * Created on 22 avril 2002, 11:38
 */
package modulecoGUI.Traces.grapheco;
/**
 * @author infolab
 * @version
 */
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import modulecoGUI.Traces.FindFiles;
import java.util.Vector;
//import modulecoGUI.grapheco.CentralControl;
/**
 * In the data recording window, handle the type, path and name of the file used
 * to store data.
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 05-May-2004
 */
public class DataFileParameter implements ItemListener, ActionListener {
	protected String filepath;
	protected JTextField filePath;
	protected JComboBox dataFormats;
	protected JPanel dataFilePanel;
	protected JTextField dataFilePath;
	protected JButton browse;
	/**
	 * Creates new DataFileParameter
	 * 
	 * @param filepath
	 */
	public DataFileParameter(String filepath) {
		this.filepath = filepath;
		initComponents();
	}
	public DataFileParameter() {
		initComponents();
	}
	/**
	 * Build the GUI.
	 *  
	 */
	public void initComponents() {
		dataFilePanel = new JPanel();
		dataFilePanel.setLayout(null);
		dataFilePanel.setBorder(new TitledBorder(null, "Data file parameters"));
		JLabel lbfname = new JLabel();
		lbfname.setText("File path");
		dataFilePanel.add(lbfname);
		lbfname.setBounds(20, 30, 80, 17);
		JLabel lbfor = new JLabel();
		lbfor.setText("Data format");
		dataFilePanel.add(lbfor);
		lbfor.setBounds(20, 80, 120, 17);
		filePath = new JTextField();
		dataFilePanel.add(filePath);
		filePath.setBounds(150, 30, 380, 21);
		browse = new JButton("Browse");
		browse.setToolTipText("Browse files");
		browse.addActionListener(this);
		dataFilePanel.add(browse);
		browse.setBounds(540, 27, 80, 27);
		dataFormats = new JComboBox();
		dataFormats.addItem(new String(".csv"));
		/**
		 * Uncomment the following to propose other file types, such as .txt or
		 * .doc. Those file type will have to be properly implemented in
		 * modulecoGUI.Stockage, though.
		 */
		//dataFormats.addItem(new String(".txt"));
		//dataFormats.addItem(new String(".doc"));
		dataFormats.addItemListener(this);
		dataFilePanel.add(dataFormats);
		dataFormats.setBounds(150, 70, 120, 27);
		filePath.setText(filepath + dataFormats.getSelectedItem());
		setDataFilePath((String) dataFormats.getSelectedItem());
	}
	public void refresh(String pathname) {
		filePath.setText(pathname);
		this.filepath = pathname;
		dataFormats.setSelectedIndex(0);
		setDataFilePath((String) dataFormats.getSelectedItem());
	}
	/**
	 * Set the the name and path of the file used to store the simulation
	 * results.
	 * 
	 * @param dataFormat
	 *            (e.g. .csv, .txt or .doc)
	 */
	public void setDataFilePath(String dataFormat) {
		/**
		 * Get the filename
		 */
		String filename = filePath.getText();
		/**
		 * If there is a filename
		 */
		if (filename.length() > 0) {
			/**
			 * If there is a point . in the filename, remove it and everything
			 * after.
			 */
			if (filename.lastIndexOf(".") != -1)
				filename = filename.substring(0, filename.lastIndexOf("."));
			/**
			 * If there is a parenthesis ( in the filename, remove it and
			 * everything after.
			 */
			if (filename.lastIndexOf('(') != -1)
				filename = filename.substring(0, filename.lastIndexOf('('));
			/**
			 * Set the complete filename.
			 * <p>
			 * e.g. GCMG.csv
			 */
			String path = filename + dataFormat;
			File output = new File(path);
			File directory = output.getParentFile();
			if (directory.isDirectory()) {
				FindFiles findFiles = new FindFiles(output);
				if (findFiles.getMaxFile() > 0) {
					String directoryName = directory.getPath();
					String name = output.getName();
					//String extension = name.substring(name.lastIndexOf('.'));
					/**
					 * Set the final filename.
					 * <p>
					 * e.g. GCMG-1.csv, GCMG-2.csv
					 */
					String filepath = name.substring(0, name.lastIndexOf('.'))
							+ '(' + Integer.toString(findFiles.getMaxFile())
							+ ')' + dataFormat;
					path = directoryName + File.separator + filepath;
				}
			}
			filePath.setText(path);
		}
	}
	public Vector getFormats() {
		Vector v = new Vector();
		if (dataFormats.getModel().getSize() > 0) {
			int index = dataFormats.getSelectedIndex();
			for (int i = 0; i < dataFormats.getModel().getSize(); i++) {
				dataFormats.setSelectedIndex(i);
				v.addElement(dataFormats.getSelectedItem());
			}
			dataFormats.setSelectedIndex(index);
		}
		return v;
	}
	public JPanel getPanel() {
		return dataFilePanel;
	}
	public void itemStateChanged(ItemEvent itemEvent) {
		if (itemEvent.getSource().equals(dataFormats)) {
			setDataFilePath((String) dataFormats.getSelectedItem());
		}
	}
	public String getPathName() {
		return filePath.getText();
	}
	public String getDataFormat() {
		return (String) dataFormats.getSelectedItem();
	}
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource().equals(browse)) {
			JFileChooser dialogue = new JFileChooser(new File(
					modulecoGUI.grapheco.CentralControl.getModulecoPathRoot()));
			File fichier;
			if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				fichier = dialogue.getSelectedFile();
				filePath.setText(fichier.getPath());
			}
			return;
		}
	}
	public void resetFilePath() {
		filePath.setText(filepath);
		setDataFilePath((String) dataFormats.getSelectedItem());
	}
	/**
	 * Check that the output file path & name provided are correct.
	 * 
	 * @return
	 */
	public boolean dataFilePathValidate() {
		/**
		 * Reconstruct the path to the output file.
		 * <p>
		 * e.g. pathRoot = xxx/modulecoMK/outputs/
		 *  
		 */
		//String pathRoot = CentralControl.getModulecoPathRoot() + "outputs"
		//		+ File.separator;
		String pathRoot = System.getProperty("user.dir") + File.separator
				+ "outputs" + File.separator;
		/**
		 * Get the string provided by the user.
		 * <p>
		 * e.g. path=xxx/modulecoMK/outputs/GCMG/GCMG.csv
		 */
		String path = filePath.getText();
		/**
		 * Return false if there is no extension.
		 */
		if (path.lastIndexOf('.') == -1)
			return false;
		/**
		 * Get the extension.
		 * <p>
		 * e.g. extension = csv
		 */
		String extension = path.substring(path.lastIndexOf('.'));
		if ((filePath.getText()
				.regionMatches(0, pathRoot, 0, pathRoot.length()))
				&& (this.getFormats().contains(extension)))
			return true;
		File output = new File(path);
		if ((output.getParentFile().isDirectory())
				&& (this.getFormats().contains(extension)))
			return true;
		return false;
	}
}