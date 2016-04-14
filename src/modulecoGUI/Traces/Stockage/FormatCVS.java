/*
 * FormatCVS.java
 * 
 * Created on 22 avril 2002, 22:29
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Enumeration;
import modulecoGUI.Traces.Trace;
import modulecoGUI.Traces.Etat;
import modulecoGUI.grapheco.descriptor.DataDescriptor;
public class FormatCVS implements Stockage {
	protected Experience experience;
	protected char separator;
	/** Creates new FormatCVS */
	public FormatCVS(Experience experience) {
		this.experience = experience;
		separator = ',';
		//printHeader(experience.getHeader());
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
			printConstantsValues(printWriter);
			printVarsNames(printWriter);
			printWriter.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	/**
	 * Generate a new line for the iteration.
	 *  
	 */
	public void writeData() {
		try {
			/**
			 * Make sure both the directory and the file exist. Create them
			 * otherwise.
			 */
			File output = new File(experience.getPathname());
			if (!output.getParentFile().isDirectory())
				output.getParentFile().mkdir();
			if (!output.isFile())
				output.createNewFile();
			/**
			 * Prepare the printWriter
			 */
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(
					new FileWriter(experience.getPathname(), true), 4096));
			/**
			 * For each time step i
			 */
			for (Iterator i = experience.getTraces().iterator(); i.hasNext();) {
				Trace trace = (Trace) i.next();
				//printWriter.print("Step");
				//printWriter.print(separator);
				//printWriter.println(trace.getStep());
				/**
				 * For each agent j
				 */
				for (Iterator j = trace.getEtats().iterator(); j.hasNext();) {
					Etat etat = (Etat) j.next();
					//if ( (etat.getAgentType().equals("Extra Agent")) ||
					// (etat.getAgentType().equals("World")))
					//  printWriter.print(etat.getAgentType());
					//else
					//   printWriter.print(etat.getAgentID());
					//printWriter.print(separator);
					/**
					 * For each descriptor d
					 */
					for (Iterator k = etat.getDescriptors().iterator(); k
							.hasNext();) {
						DataDescriptor d = (DataDescriptor) k.next();
						d.printCVS(printWriter);
					}
					//printWriter.println();
				}
				printWriter.println();
			}
			printWriter.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	public void writeModelParameter(Hashtable header, PrintWriter printWriter) {
		Enumeration enum = header.keys();
		while (enum.hasMoreElements()) {
			String key = (String) enum.nextElement();
			printWriter.print(key);
			printWriter.print(separator);
			printWriter.print(header.get(key));
			printWriter.print(separator);
			printWriter.println();
		}
		printWriter.println();
		printWriter.println();
	}
	public void printVarsNames(PrintWriter printWriter) {
		if (experience.getANConstantNames().size() > 0) {
			printWriter.println("Agents descriptors names:");
			printWriter.print("AgentID");
			printWriter.print(separator);
			for (Iterator i = experience.getANConstantNames().iterator(); i
					.hasNext();) {
				printWriter.print(i.next());
				printWriter.print(separator);
			}
			printWriter.println();
			printWriter.println();
		}
		if (experience.getEANConstantNames().size() > 0) {
			printWriter.println("Extra agents descriptors names:");
			printWriter.print("Extra Agent");
			printWriter.print(separator);
			for (Iterator i = experience.getEANConstantNames().iterator(); i
					.hasNext();) {
				printWriter.print(i.next());
				printWriter.print(separator);
			}
			printWriter.println();
			printWriter.println();
		}
		if (experience.getWNConstantNames().size() > 0) {
			printWriter.println("World descriptors names:");
			printWriter.print("World");
			printWriter.print(separator);
			for (Iterator i = experience.getWNConstantNames().iterator(); i
					.hasNext();) {
				printWriter.print(i.next());
				printWriter.print(separator);
			}
			printWriter.println();
			printWriter.println();
		}
	}
	public void printConstantsValues(PrintWriter printWriter) {
		if (experience.getConstantsValues().size() > 0) {
			ArrayList constantValues = experience.getConstantsValues();
			if (experience.getAConstantNames().size() > 0)
				printConstants("Agent", printWriter, constantValues);
			if (experience.getEAConstantNames().size() > 0)
				printConstants("Extra Agent", printWriter, constantValues);
			if (experience.getWConstantNames().size() > 0)
				printConstants("World", printWriter, constantValues);
		}
	}
	public void printConstants(String agentType, PrintWriter printWriter,
			ArrayList constantValues) {
		printWriter.println(agentType + " constants values");
		printWriter.println();
		for (Iterator i = constantValues.iterator(); i.hasNext();) {
			Etat etat = (Etat) i.next();
			if (etat.getAgentType().equals(agentType)) {
				for (Iterator j = etat.getDescriptors().iterator(); j.hasNext();) {
					DataDescriptor d = (DataDescriptor) j.next();
					printWriter.print(d.getDataName());
					printWriter.print(separator);
					d.printCVS(printWriter);
					printWriter.println();
				}
				printWriter.println();
				return;
			}
		}
	}
}