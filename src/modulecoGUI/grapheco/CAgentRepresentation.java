/* Source File Name:   Canevas.java
* Copyright:    Copyright (c)enst-bretagne
* @author denis.phan@enst-bretagne.fr
* created may 2000 - Interface graphique compatible jdk 1.1
* @version 1.2  august,5, 2002
*/

package modulecoGUI.grapheco;
import java.awt.Dimension;
import modulecoFramework.modeleco.CAgent;
import modulecoGUI.grapheco.CentralControl;

/**
 * This interface is implemented by classes which represents a CAgent.
 * An implementing class can be added to a CAgentRepresentationContainer
 * like CentralControl.
 * @see modulecoGUI.grapheco.CAgentRepresentationContainer
 * @see modulecoGUI.grapheco.CentralControl
 */
public interface CAgentRepresentation {
	/**
	 * This method will be used to set which agent should be represented.
	 * @param cAgent The CAgent that should be represented
	 */
	public void setCAgent(CAgent cAgent);

	/**
	 * Should return the size of the representation.
	 * @return The dimension of the representation.
	 */
	public Dimension getSize();

	/**
	 * Should set the size of the representation.
	 * @param dimension The dimension to be adopted by the representation
	 */
	public void setSize(Dimension dimension);

	/**
	 * Should set the size of the representation.
	 * @param x The x size to be adopted by the representation
	 * @param y The y size to be adopted by the representation
	 */
	public void setSize(int width, int height);

	/**
	 * Should update the representation. Called usualy after each world 
	 * iteration.
	 */
	public void updateImage();

	/**
	 * Should reset the representation.
	 */
	public void resetImage();

	/**
	 * Set the central control. Usefule in some cases. Called at the creation
	 * of the CAgentRepresentation
	 */
	public void setCentralControl(CentralControl centralControl);

	/**
	 * return the name of the representation as a String.
	 */
	public String getName();
	/**
	 * set the name of the representation as a String.
	*/
	public void setName(String s);

}
