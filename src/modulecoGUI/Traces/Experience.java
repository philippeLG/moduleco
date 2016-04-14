/*
 * Experience.java
 * Created on 18 avril 2002, 15:08
 * @author infolab
 */
package modulecoGUI.Traces;
import java.util.ArrayList;
import java.util.Hashtable;
/**
 * Experience is the object used to store all the details of a simulation.
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 06-May-2004
 */
public class Experience {
	protected String modelName;
	protected String neighbourName;
	protected String zoneName;
	protected String schedulerType;
	protected boolean extraAgentCommitFirst;
	protected int modelCapacite;
	protected String pathname;
	protected String dataFormat;
	protected int stepInit, stepEnd, stepByX;
	protected boolean allStep;
	/**
	 * Agents constants and non-constants (i.e. variables)
	 */
	protected ArrayList aconstantNames, anConstantNames;
	/**
	 * Extra-Agents constants and non-constants (i.e. variables)
	 */
	protected ArrayList eaconstantNames, eanConstantNames;
	/**
	 * World constants, non-constants (i.e. variables) and values
	 */
	protected ArrayList wconstantNames, wnConstantNames, constantsValues;
	protected ArrayList traces;
	protected boolean allAgents;
	protected ArrayList agentIds;
	protected int stepNumber;
	/**
	 * 
	 *
	 */
	public Experience() {
		//System.out.println("[Experience()]");
		aconstantNames = new ArrayList();
		anConstantNames = new ArrayList();
		eaconstantNames = new ArrayList();
		eanConstantNames = new ArrayList();
		wconstantNames = new ArrayList();
		wnConstantNames = new ArrayList();
		constantsValues = new ArrayList();
		agentIds = new ArrayList();
		traces = new ArrayList();
	}
	public Experience(String modelName, int modelCapacite,
			String neighbourName, String zoneName, String schedulerType,
			boolean extraAgentCommitFirst) {
		//System.out.println("[Experience(blah, blah)]");
		this.modelName = modelName;
		this.modelCapacite = modelCapacite;
		this.neighbourName = neighbourName;
		this.zoneName = zoneName;
		this.schedulerType = schedulerType;
		this.extraAgentCommitFirst = extraAgentCommitFirst;
		aconstantNames = new ArrayList();
		anConstantNames = new ArrayList();
		eaconstantNames = new ArrayList();
		eanConstantNames = new ArrayList();
		wconstantNames = new ArrayList();
		wnConstantNames = new ArrayList();
		constantsValues = new ArrayList();
		agentIds = new ArrayList();
		this.traces = new ArrayList();
	}
	public void setDataFileParameters(String pathname, String dataFormat) {
		//System.out.println("[Experience.setDataFileParameters()]");
		this.pathname = pathname;
		this.dataFormat = dataFormat;
	}
	public void setANames(ArrayList constantNames, ArrayList nConstantNames) {
		//System.out.println("[Experience.setANames()]");
		this.aconstantNames = constantNames;
		this.anConstantNames = nConstantNames;
	}
	public void setEANames(ArrayList constantNames, ArrayList nConstantNames) {
		//System.out.println("[Experience.setEANames()]");
		this.eaconstantNames = constantNames;
		this.eanConstantNames = nConstantNames;
	}
	public void setWNames(ArrayList constantNames, ArrayList nConstantNames) {
		//System.out.println("[Experience.setWNames()]");
		this.wconstantNames = constantNames;
		this.wnConstantNames = nConstantNames;
	}
	public ArrayList getANConstantNames() {
		//System.out.println("[Experience.getANConstantNames()]");
		return this.anConstantNames;
	}
	public ArrayList getEANConstantNames() {
		//System.out.println("[Experience.getEANConstantNames()]");
		return this.eanConstantNames;
	}
	public ArrayList getWNConstantNames() {
		//System.out.println("[Experience.getWNConstantNames()]");
		return this.wnConstantNames;
	}
	public ArrayList getAConstantNames() {
		//System.out.println("[Experience.getAConstantNames()]");
		return this.aconstantNames;
	}
	public ArrayList getEAConstantNames() {
		//System.out.println("[Experience.getEAConstantNames()]");
		return this.eaconstantNames;
	}
	public ArrayList getWConstantNames() {
		//System.out.println("[Experience.getWConstantNames()]");
		return this.wconstantNames;
	}
	public void setStep(boolean allStep) {
		//System.out.println("[Experience.setStep()]");
		this.allStep = allStep;
	}
	public void setStep(int stepInit, int stepEnd) {
		this.stepInit = stepInit;
		this.stepEnd = stepEnd;
	}
	public void setTrace(ArrayList traces) {
		//System.out.println("[Experience.setTrace()]");
		this.traces = traces;
	}
	public ArrayList getTraces() {
		return this.traces;
	}
	public void add(Trace trace) {
		traces.add(trace);
	}
	public boolean getAllStep() {
		return allStep;
	}
	public void remove(Trace trace) {
		traces.remove(trace);
	}
	public void remove() {
		traces.clear();
	}
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	public String getPathname() {
		return this.pathname;
	}
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
	public String getDataFormat() {
		return dataFormat;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelName() {
		return this.modelName;
	}
	public String getNeighbourName() {
		return this.neighbourName;
	}
	public void setNeighbourName(String neighbourName) {
		this.neighbourName = neighbourName;
	}
	public String getZoneName() {
		return this.zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public void setschedulerType(String schedulerType) {
		this.schedulerType = schedulerType;
	}
	public String getschedulerType() {
		return this.schedulerType;
	}
	public void setModelCapacite(int modelCapacite) {
		this.modelCapacite = modelCapacite;
	}
	public int getModelCapacite() {
		return this.modelCapacite;
	}
	public void setStepInit(int stepInit) {
		this.stepInit = stepInit;
	}
	public int getStepInit() {
		return this.stepInit;
	}
	public void setStepEnd(int stepEnd) {
		this.stepEnd = stepEnd;
	}
	public int getStepEnd() {
		return this.stepEnd;
	}
	public void setConstantsValues(ArrayList des) {
		this.constantsValues = des;
	}
	public ArrayList getConstantsValues() {
		return this.constantsValues;
	}
	public Hashtable getHeader() {
		//System.out.println("[Experience.getHeaders()]");
		Hashtable header = new Hashtable();
		header.put(new String("Model name"), this.modelName);
		header.put(new String("World size"), new Integer(this.modelCapacite));
		header.put(new String("Neighbour name"), this.neighbourName);
		header.put(new String("Zone name"), this.zoneName);
		header.put(new String("Scheduler type"), this.schedulerType);
		header.put(new String("Extra agent commit type"), new Boolean(
				this.extraAgentCommitFirst));
		return header;
	}
	public void setAgentIds(ArrayList agentIds) {
		this.agentIds = agentIds;
	}
	public void setAllAgents(boolean allAgents) {
		this.allAgents = allAgents;
	}
	public ArrayList getAgentIds() {
		return agentIds;
	}
	public boolean getAllAgents() {
		return this.allAgents;
	}
	public void setStepNumber(int n) {
		this.stepNumber = n;
	}
	public int getStepNumber() {
		//System.out.println("[Experience.getStepNumber()]");
		return this.stepNumber;
	}
	public void setStepByX(int stepByX) {
		this.stepByX = stepByX;
	}
	public int getStepByX() {
		return this.stepByX;
	}
}