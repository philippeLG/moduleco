/**
 * EditorManager control Probes and graphic data interfaces
 * @see modulecoGUI.grapheco.descriptor
 * Copyright: (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 * created may 29 2000
 * Created may, 12 2004
 * @version 1.4.2  june 2004
 **/
package modulecoGUI.grapheco;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Font;
import modulecoFramework.modeleco.CAgent;
/**
 * @author denis.phan@enst-bretagne.fr
 *  
 */
public class EditorManager {
	public CentralControl control;
	protected EAgentEditor worldAE = null;
	protected EAgentEditor worldFixed_ae;
	protected ArrayList aeList = null;
	protected ArrayList faeList = null;
	protected ArrayList aleList = null;
	public EditorManager(CentralControl cc) {
		this.control = cc;
	}
	public void buildAdditionalPanels(CAgent wca, CAgent[] extraAgents) {
		control.getEPanel().resetAdditionalPanels();
		worldFixed_ae = new EAgentEditor(wca);
		worldFixed_ae.build(4);
		control.getEPanel().addAdditionalPanel(worldFixed_ae);
		if (extraAgents.length > 0) {
			for (int i = 0; i < extraAgents.length; i++) {
				buildFixed_Editor((CAgent) extraAgents[i]);
			}
		}
	}
	/**
	 * Pop-up menu representing an agent or the world.
	 * 
	 * @param ag
	 * @param title
	 */
	public void buildPopUpEditor(CAgent ag, String title) {
		EAgentEditor ae;
		if (aeList == null)
			aeList = new ArrayList();
		try {
			System.out.println("buildPopUpEditor for " + title);
			ae = new EAgentEditor(ag);
			ae.build(1);
			AEFrame aeFrame = new AEFrame(ae, title);
			aeFrame.ae.setPopUpFrame(aeFrame);
			ae.setVisible(true);
			aeFrame.pack();
			aeList.add(ae);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void editAgentLinks(CAgent ag, String title) {
		EAgentLinksEditor ale;
		if (aleList == null)
			aleList = new ArrayList();
		//introduire test
		//System.out.println("AgentLinks already edited");
		try {
			ale = new EAgentLinksEditor(ag);
			ale.build(1);
			AEFrame aeFrame = new AEFrame(ale, title);
			aeFrame.setFont(new Font(null, Font.ITALIC, 5));
			aeFrame.setTitle("Agent : " + ag.getAgentID() + " Neighbours");
			aeFrame.setVisible(true);
			aleList.add(ale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void buildFixed_Editor(CAgent ca) {
		EAgentEditor fixed_ae;
		if (faeList == null)
			faeList = new ArrayList();
		try {
			fixed_ae = new EAgentEditor(ca);
			fixed_ae.build(5);
			faeList.add(fixed_ae);
			control.getEPanel().addAdditionalPanel(fixed_ae);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Activated at each step from simulationControl.step() by the way of
	 * worldListener.setDataToInterface() update values in the descriptors for
	 * all CAgents
	 * 
	 * @see modulecoFramework.WorldListener.setDataToInterface()
	 */
	public void setDataToInterface() {
		// System.out.println("EditorManager.setDataToInterface()");
		// vérifier dans quelle mesure une partie des update()
		// n'est pas redondante ?
		// En fait, cela sert principalement aux données vers
		// l'interafce (voir les Get ?)
		if (worldAE != null) {
			worldAE.update();
		}
		if (worldFixed_ae != null) {
			worldFixed_ae.update();
		}
		if (aeList != null)
			for (Iterator i = aeList.iterator(); i.hasNext();) {
				EAgentEditor ae = (EAgentEditor) i.next();
				ae.update();
			}
		if (faeList != null)
			for (Iterator i = faeList.iterator(); i.hasNext();) {
				EAgentEditor fae = (EAgentEditor) i.next();
				fae.update();
			}
		if (aleList != null)
			for (Iterator i = aleList.iterator(); i.hasNext();) {
				EAgentLinksEditor ale = (EAgentLinksEditor) i.next();
				ale.update();
			}
	}
	/**
	 * @return boolean aeExist = true if ae Exist (false elsewhere)
	 */
	public boolean worldAEexist() {
		boolean worldAEexist;
		if (worldAE != null)
			worldAEexist = true;
		else
			worldAEexist = false;
		return worldAEexist;
	}
	public boolean worldFixed_aeExist() {
		boolean wfaeExist;
		if (worldAE != null)
			wfaeExist = true;
		else
			wfaeExist = false;
		return wfaeExist;
	}
	/**
	 * Close all existing PopUpFrame
	 * 
	 * @see modulecoFramework.modeleco.WorlListener.terminate();
	 */
	public void ClosePopUp() {
		if (worldAEexist())
			(worldAE.getPopUpFrame()).closedAE();
		if (aeList != null) {
			for (Iterator i = aeList.iterator(); i.hasNext();) {
				(((EAgentEditor) i.next()).getPopUpFrame()).closedAE();
			}
			aeList.clear();
			aeList = null;
		}
		if (aleList != null) {
			for (Iterator i = aleList.iterator(); i.hasNext();) {
				(((EAgentEditor) i.next()).getPopUpFrame()).closedAE();
			}
			aleList.clear();
			aleList = null;
		}
	}
	public void CloseFAEList() {
		/**
		 * Close the ExtraAgent ArrayList faeList
		 * 
		 * @see modulecoFramework.modeleco.WorlListener.terminate();
		 */
		if (faeList != null)
			faeList.clear();
		faeList = null;
		//for (Iterator i = faeList.iterator(); i.hasNext();) {
		//EAgentEditor fae = (EAgentEditor) i.next();
	}
}