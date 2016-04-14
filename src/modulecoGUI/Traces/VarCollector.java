/*
 * VarCollector.java
 * 
 * Created on 20 avril 2002, 19:32
 */
package modulecoGUI.Traces;
import modulecoFramework.modeleco.EWorld;
//import modulecoGUI.Traces.Trace;
import java.util.ArrayList;
import modulecoGUI.Traces.Stockage.Filtre;
/**
 * @author infolab
 * @version
 */
public class VarCollector {
	protected Experience experience;
	protected EWorld eWorld;
	protected Filtre filtre;
	protected ArrayList agentIds;
	protected int stepNumber, stepInit, stepEnd, stepOneByX;
	protected boolean allAgents;
	/** Creates new VarCollector */
	public VarCollector(EWorld eWorld, Experience experience) {
		this.eWorld = eWorld;
		this.experience = experience;
		agentIds = new ArrayList();
		allAgents = false;
		initStepParameter();
		initAgents();
		getConstantsValues();
		initFiltre();
		getInitValues();
	}
	public void initAgents() {
		if (this.experience.getAgentIds().size() > 0)
			this.agentIds = experience.getAgentIds();
		else
			this.allAgents = experience.getAllAgents();
	}
	public void initStepParameter() {
		stepNumber = experience.getStepNumber();
		if (stepNumber == 3)
			stepOneByX = experience.getStepByX();
		if (stepNumber == 4) {
			stepInit = experience.getStepInit();
			stepEnd = experience.getStepEnd();
		}
		if (stepNumber == 5) {
			stepInit = experience.getStepInit();
			stepEnd = experience.getStepEnd();
			stepOneByX = experience.getStepByX();
		}
	}
	public void initFiltre() {
		filtre = new Filtre(experience);
	}
	public void getConstantsValues() {
		ArrayList states = new ArrayList();
		if (allAgents) {
			states = (ArrayList) this.eWorld.getPState(experience
					.getAConstantNames(), experience.getEAConstantNames(),
					experience.getWConstantNames());
		} else if (agentIds.size() > 0) {
			states = (ArrayList) this.eWorld.getPState(agentIds, experience
					.getAConstantNames(), experience.getEAConstantNames(),
					experience.getWConstantNames());
		}
		experience.setConstantsValues(states);
	}
	public void getInitValues() {
		Trace trace = new Trace(0);
		trace.setEtats(getStates());
		experience.add(trace);
		filtre.writeData();
	}
	public void collectTrace(int iteration) {
		Trace trace = new Trace(iteration);
		switch (stepNumber) {
			case 1 :
				trace.setEtats(getStates());
				experience.add(trace);
				filtre.writeData();
				break;
			case 2 :
				if (iteration == 1) {
					trace.setEtats(getStates());
					experience.add(trace);
					filtre.writeData();
				}
				break;
			case 3 :
				if ((iteration % stepOneByX == 0) || (iteration == 1)) {
					trace.setEtats(getStates());
					experience.add(trace);
					filtre.writeData();
				}
				break;
			case 4 :
				if ((iteration >= stepInit) && (iteration <= stepEnd)) {
					trace.setEtats(getStates());
					experience.add(trace);
					filtre.writeData();
				}
				break;
			case 5 :
				if ((iteration == stepInit)
						|| (((iteration > stepInit) && (iteration <= stepEnd)) && (iteration
								% stepOneByX == 0))) {
					trace.setEtats(getStates());
					experience.add(trace);
					filtre.writeData();
				}
				break;
			default :
				System.out.println("\nErreur de step");
		}
	}
	public ArrayList getStates() {
		ArrayList states = new ArrayList();
		if (allAgents) {
			states = (ArrayList) this.eWorld.getPState(experience
					.getANConstantNames(), experience.getEANConstantNames(),
					experience.getWNConstantNames());
		} else if (agentIds.size() > 0) {
			states = (ArrayList) this.eWorld.getPState(agentIds, experience
					.getANConstantNames(), experience.getEANConstantNames(),
					experience.getWNConstantNames());
		}
		return states;
	}
	public void saveLastTrace(int iteration) {
		//System.out.println("[VarCollector.saveLastTrace()]");
		if (stepNumber == 2) {
			System.out
					.println("[VarCollector.saveLastTrace()] Step Number > 2");
			Trace trace = new Trace(iteration);
			trace.setEtats(getStates());
			experience.add(trace);
			filtre.writeData();
		}
	}
	public void removeTrace() {
		filtre.removeFile();
	}
}