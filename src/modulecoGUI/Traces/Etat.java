/*
 * Etat.java
 * 
 * Created on 18 avril 2002, 14:54
 */
package modulecoGUI.Traces;
/**
 * @author infolab
 * @version
 */
import java.util.ArrayList;
import modulecoGUI.grapheco.descriptor.DataDescriptor;
public class Etat {
	protected int agentID;
	protected String agentType;
	protected ArrayList descriptors;
	/** Creates new Etat */
	public Etat() {
		descriptors = new ArrayList();
		agentType = new String();
	}
	public Etat(int agentID) {
		this.agentID = agentID;
		descriptors = new ArrayList();
		agentType = new String();
	}
	public Etat(String agentType) {
		this.agentType = agentType;
		descriptors = new ArrayList();
	}
	public Etat(int agentID, String agentType) {
		this.agentID = agentID;
		this.agentType = agentType;
		this.descriptors = new ArrayList();
	}
	public Etat(String agentType, ArrayList descriptors) {
		this.agentType = agentType;
		this.descriptors = descriptors;
	}
	public Etat(int agentID, String agentType, ArrayList descriptors) {
		this.agentID = agentID;
		this.agentType = agentType;
		this.descriptors = descriptors;
	}
	public void add(DataDescriptor d) {
		descriptors.add(d);
	}
	public void remove(DataDescriptor d) {
		descriptors.remove(d);
	}
	public ArrayList getDescriptors() {
		return descriptors;
	}
	public int getAgentID() {
		return agentID;
	}
	public String getAgentType() {
		return agentType;
	}
	public void setDescriptors(ArrayList descriptors) {
		this.descriptors = descriptors;
	}
	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}
}