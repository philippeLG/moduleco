/**
 * Title: Moduleco - modulecoFramework.abstractModels.WorldGame.java <p>
 * Description:  Abstract Generic Game World Class <p>
 * Copyright : (c)enst-bretagne
 * @author Denis.Phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

package modulecoFramework.abstractModels;

//import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.ENeighbourWorld;

import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.InfoDescriptor;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
//import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
//import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoGUI.grapheco.statManager.CalculatedVar;
/**
 * 
 * @author Denis.Phan@enst-bretagne.fr
 *
 */
public abstract class EWorldGame extends ENeighbourWorld {
	public int s1AgainstS1, s2AgainstS1, s1AgainstS2, s2AgainstS2;
	public int[][] payoffMatrix;
	protected String revisionRuleIndex;

	public EWorldGame(int length) {
		super(length);
	}

	/**
	* method getInfo() Invoked by ENeighbourWorld.populateAll(nsClass)  
	* to be executed first, before the end of this world's constructor
	* during each world's creation process.
	*/
	public void getInfo() {
		payoffMatrix = new int[2][2];
		s1AgainstS1 = 10;
		s2AgainstS1 = 10;
		s1AgainstS2 = 10;
		s2AgainstS2 = 10;
	}

	public void setDefaultValues() {
		// payoffMatrix : Matrix constructed by this.getInfo()
		// during all world's creation process

		payoffMatrix[0][0] = s1AgainstS1;
		payoffMatrix[1][0] = s2AgainstS1;
		payoffMatrix[0][1] = s1AgainstS2;
		payoffMatrix[1][1] = s2AgainstS2;
		revisionRuleIndex = "Last neighbourhood best payoff";
	}

	public void init() {

		try {
			statManager.add(
				new CalculatedVar(
					"FalseState",
					Class.forName(this.pack() + ".Agent").getMethod(
						"getState",
						null),
					CalculatedVar.NUMBER,
					new Boolean(false)));
			statManager.add(
				new CalculatedVar(
					"Changes",
					Class.forName(this.pack() + ".Agent").getMethod(
						"hasChanged",
						null),
					CalculatedVar.NUMBER,
					new Boolean(true)));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}

	}

	// === Data management ===

	public int getS1AgainstS1() {
		return s1AgainstS1;
	}
	public int getS1AgainstS2() {
		return s1AgainstS2;
	}
	public int getS2AgainstS1() {
		return s2AgainstS1;
	}
	public int getS2AgainstS2() {
		return s2AgainstS2;
	}
	public void setS1AgainstS1(int s1AgainstS1) {
		this.s1AgainstS1 = s1AgainstS1;
		payoffMatrix[0][0] = s1AgainstS1;
	}
	public void setS2AgainstS1(int s2AgainstS1) {
		this.s2AgainstS1 = s2AgainstS1;
		payoffMatrix[1][0] = s2AgainstS1;
	}

	public void setS1AgainstS2(int s1AgainstS2) {
		this.s1AgainstS2 = s1AgainstS2;
		payoffMatrix[0][1] = s1AgainstS2;
	}

	public void setS2AgainstS2(int s2AgainstS2) {
		this.s2AgainstS2 = s2AgainstS2;
		payoffMatrix[1][1] = s2AgainstS2;
	}

	public String getRevisionRuleIndex() {
		return revisionRuleIndex;
	}

	public void setRevisionRuleIndex(String s) {
		revisionRuleIndex = s;
	}

	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"S1 against S1",
				"s1AgainstS1",
				s1AgainstS1,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"S1 against S2",
				"s1AgainstS2",
				s1AgainstS2,
				true));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"S2 against S1",
				"s2AgainstS1",
				s2AgainstS1,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"S2 against S2",
				"s2AgainstS2",
				s2AgainstS2,
				true));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"Strategy revision rule",
				"revisionRuleIndex",
				new String[] {
					"Last neighbourhood best payoff",
					"Last neighbourhood best average payoff" },
				revisionRuleIndex,
				true));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(new InfoDescriptor(""));
		return descriptors;
	}
}