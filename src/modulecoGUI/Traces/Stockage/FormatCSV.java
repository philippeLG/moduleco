/*
 * @(#)FormatUniv.java 0.1 5-Apr-04
 */
package modulecoGUI.Traces.Stockage;
/**
 * Universal Output Format.
 * <p>
 * The aim is to have a simulation output file easily usable by external
 * applications, such as Matlab or Excel.
 * 
 * Format:
 * 
 * <pre>
 * 
 *   timeStep World World Agent1 Agent1 Agent1 Agent2 ...
 *   t ...... var1 .var2 .var1 ..var2 ..var3 ..var1
 *   0
 *   1
 *   3
 *   ...             
 *  
 * </pre>
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 0.1, 5-Apr-04
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
/**
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 05-Apr-2004
 */
public class FormatCSV implements Stockage {
	protected Experience experience;
	protected char separator;
	/**
	 * Creates new FormatCVS
	 */
	public FormatCSV(Experience experience) {
		this.experience = experience;
		separator = ',';
		printHeader(experience.getHeader());
	}
	/**
	 * Print out the headers.
	 * 
	 * @param header
	 */
	public void printHeader(Hashtable header) {
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
			 * Prepare the printWriter.
			 */
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(
					new FileWriter(experience.getPathname(), true), 4096));
			/**
			 * Include the parameters.
			 */
			//writeModelParameter(header, printWriter);
			/**
			 * Include the agents names.
			 */
			printAgentsNames(printWriter);
			/**
			 * Include the constants.
			 */
			//printConstantsValues(printWriter);
			printAgentsConstants(printWriter);
			/**
			 * Include the variable names.
			 */
			printVarsNames(printWriter);
			/**
			 * End.
			 */
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
		//System.out.println("[FormatCSV.writeData()]");
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
			 * For each trace i
			 */
			//System.out.println("[FormatCSV.writeData] noTraces = " +
			// experience.getTraces().size());
			for (Iterator i = experience.getTraces().iterator(); i.hasNext();) {
				/**
				 * Get the next trace (experience.getTraces returns an ArrayList
				 * of traces Trace *made of only 1 trace*)
				 */
				Trace trace = (Trace) i.next();
				/**
				 * Print out the time step
				 */
				printWriter.print(trace.getStep());
				//System.out.println("[FormatCSV.writeData] trace.getStep = "
				//		+ trace.getStep());
				printWriter.print(separator);
				/**
				 * For each agent j.
				 * <p>
				 * Here agent == world || simple agent || extra agent.
				 */
				for (Iterator j = trace.getEtats().iterator(); j.hasNext();) {
					Etat etat = (Etat) j.next();
					//if ((etat.getAgentType().equals("Extra Agent"))
					//		|| (etat.getAgentType().equals("World")))
					//	  printWriter.print(etat.getAgentType());
					//	else
					//	   printWriter.print(etat.getAgentID());
					//	printWriter.print(separator);
					/**
					 * For each descriptor d
					 */
					for (Iterator k = etat.getDescriptors().iterator(); k
							.hasNext();) {
						DataDescriptor d = (DataDescriptor) k.next();
						d.printCVS(printWriter);
					}
				}
				printWriter.println();
			}
			/**
			 * Close the file
			 */
			printWriter.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	/**
	 * Print out the parameters names.
	 * 
	 * @param header
	 * @param printWriter
	 */
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
	/**
	 * Print out the agents names.
	 * <p>
	 * e.g. "World,Agent0,Agent1,Agent2,Agent3"
	 * 
	 * @param printWriter
	 */
	public void printAgentsNames(PrintWriter printWriter) {
		/**
		 * Time step
		 */
		printWriter.print("t");
		/**
		 * World variables
		 */
		int noWorldVariables = experience.getWNConstantNames().size();
		for (int i = 0; i < noWorldVariables; i++) {
			printWriter.print(separator);
			printWriter.print("World");
		}
		/**
		 * Agents variables.
		 * 
		 * <pre>
		 * 
		 *   noAgents is the number of agents in the world.
		 *   noAgentVariables is the number of variables per agent.
		 *  
		 * </pre>
		 */
		int noAgents = (int) Math.pow(experience.getModelCapacite(), 2);
		int noAgentVariables = experience.getANConstantNames().size();
		for (int agentNumber = 0; agentNumber < noAgents; agentNumber++) {
			for (int i = 0; i < noAgentVariables; i++) {
				printWriter.print(separator);
				printWriter.print("Agent" + agentNumber);
			}
		}
		/**
		 * Extra agents variables
		 */
		int noExtraAgentVariables = experience.getEANConstantNames().size();
		for (int i = 0; i < noExtraAgentVariables; i++) {
			printWriter.print(separator);
			printWriter.print("ExtraAgent");
		}
		printWriter.println();
	}
	/**
	 * Print out the variable names.
	 * <p>
	 * e.g. "excessDemand,wealth,wealth,wealth,wealth"
	 * 
	 * @param printWriter
	 */
	public void printVarsNames(PrintWriter printWriter) {
		/**
		 * World variables
		 */
		if (experience.getWNConstantNames().size() > 0) {
			//printWriter.println("World descriptors names:");
			//printWriter.print("World");
			//printWriter.print(separator);
			for (Iterator i = experience.getWNConstantNames().iterator(); i
					.hasNext();) {
				printWriter.print(separator);
				printWriter.print(i.next());
			}
		}
		/**
		 * Agents variables
		 */
		int noAgents = (int) Math.pow(experience.getModelCapacite(), 2);
		for (int agentNumber = 0; agentNumber < noAgents; agentNumber++) {
			for (Iterator i = experience.getANConstantNames().iterator(); i
					.hasNext();) {
				printWriter.print(separator);
				printWriter.print(i.next());
			}
		}
		/**
		 * Extra agents variables
		 */
		if (experience.getEANConstantNames().size() > 0) {
			//printWriter.println("Extra agents descriptors names:");
			//printWriter.print("Extra Agent");
			//printWriter.print(separator);
			for (Iterator i = experience.getEANConstantNames().iterator(); i
					.hasNext();) {
				printWriter.print(separator);
				printWriter.print(i.next());
			}
		}
		printWriter.println();
	}
	/**
	 * Get the constants.
	 * <p>
	 * e.g. "Memory,Memory,Memory" <br>
	 * "2,5,10"
	 * 
	 * @param printWriter
	 */
	public void printConstantsValues(PrintWriter printWriter) {
		if (experience.getConstantsValues().size() > 0) {
			/**
			 * The contstants are stored in an Array constantValues.
			 */
			ArrayList constantValues = experience.getConstantsValues();
			/**
			 * Constants for agents
			 */
			if (experience.getAConstantNames().size() > 0)
				printConstants("Agent", printWriter, constantValues);
			/**
			 * Constants for extra agents
			 */
			//if (experience.getEAConstantNames().size() > 0)
			//	printConstants("Extra Agent", printWriter, constantValues);
			/**
			 * Constants for the world
			 */
			//if (experience.getWConstantNames().size() > 0)
			//	printConstants("World", printWriter, constantValues);
		}
	}
	/**
	 * Print out the constants.
	 * 
	 * @param agentType
	 * @param printWriter
	 * @param constantValues
	 */
	public void printConstants(String agentType, PrintWriter printWriter,
			ArrayList constantValues) {
		//printWriter.println(agentType + " constants values");
		//printWriter.println();
		/**
		 * For each constant value
		 */
		for (Iterator i = constantValues.iterator(); i.hasNext();) {
			/**
			 * Define whether it is a World constant value or an Agent one.
			 */
			Etat etat = (Etat) i.next();
			if (etat.getAgentType().equals(agentType)) {
				/**
				 * For each descriptor of this World or Agent
				 */
				for (Iterator j = etat.getDescriptors().iterator(); j.hasNext();) {
					DataDescriptor d = (DataDescriptor) j.next();
					int noAgents = (int) Math.pow(
							experience.getModelCapacite(), 2);
					/**
					 * "memorySize,memorySize,memorySize"
					 */
					for (int agentNumber = 0; agentNumber < noAgents; agentNumber++) {
						printWriter.print(separator);
						printWriter.print(d.getDataName());
					}
					printWriter.println();
					/**
					 * "2,5,10"
					 */
					for (int agentNumber = 0; agentNumber < noAgents; agentNumber++) {
						d.printCVS(printWriter);
					}
					printWriter.println();
				}
				//printWriter.println();
				return;
			}
		}
	}
	/**
	 * Print the Agents constant values
	 * <p>
	 * e.g. "Memory,Memory,Memory" <br>
	 * "2,5,10"
	 * 
	 * @param printWriter
	 */
	public void printAgentsConstants(PrintWriter printWriter) {
		//System.out.println("[FormatCSV.printAgentsConstants()]");
		ArrayList constants = experience.getAConstantNames();
		int noConstants = constants.size();
		for (int tmpConstant = 0; tmpConstant < noConstants; tmpConstant++) {
			/**
			 * Get the total number of agents
			 */
			int noAgents = (int) Math.pow(experience.getModelCapacite(), 2);
			/**
			 * "memorySize,memorySize,memorySize"
			 */
			for (int agentNumber = 0; agentNumber < noAgents; agentNumber++) {
				printWriter.print(separator);
				printWriter.print(constants.get(tmpConstant));
			}
			printWriter.println();
			/**
			 * "2,5,10"
			 */
			/**
			 * For each agent j.
			 * <p>
			 * Here agent == world || simple agent || extra agent.
			 */
			/*
			 * System.out.println("[FormatCSV.printAgentsConstants()] seize = " +
			 * experience.getTraces().size()); Trace trace = (Trace)
			 * experience.getTraces().get(0);
			 * System.out.println("[FormatCSV.printAgentsConstants()] step = " +
			 * trace.getStep()); for (Iterator j = trace.getEtats().iterator();
			 * j.hasNext();) { Etat etat = (Etat) j.next(); for (Iterator k =
			 * etat.getDescriptors().iterator(); k.hasNext();) { DataDescriptor
			 * d = (DataDescriptor) k.next(); printWriter.print(separator);
			 * printWriter.print(d.getDataName()); //d.printCVS(printWriter); } }
			 * printWriter.println();
			 */
		}
	}
}