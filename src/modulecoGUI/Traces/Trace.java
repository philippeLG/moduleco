/*
 * Trace.java
 * 
 * Created on 18 avril 2002, 15:06
 */
package modulecoGUI.Traces;
/**
 * @author infolab
 * @version
 */
import java.util.ArrayList;
public class Trace {
	protected int step;
	protected ArrayList etats;
	/** Creates new Trace */
	public Trace() {
	}
	public Trace(int step) {
		this.step = step;
		this.etats = new ArrayList();
	}
	public void add(Etat etat) {
		etats.add(etat);
	}
	public int getStep() {
		return this.step;
	}
	public void setEtats(ArrayList etats) {
		this.etats = etats;
	}
	public ArrayList getEtats() {
		return this.etats;
	}
	public void remove(Etat etat) {
		etats.remove(etat);
	}
	public void setStep(int step) {
		this.step = step;
	}
}